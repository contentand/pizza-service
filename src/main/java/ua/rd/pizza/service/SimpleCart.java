package ua.rd.pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.discount.Discount;
import ua.rd.pizza.domain.NewOrder;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.exception.NegativeQuantityException;
import ua.rd.pizza.exception.NoQuantityException;
import ua.rd.pizza.exception.ZeroQuantityException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Scope("prototype") // TODO: change scope to session once Spring MVC is configured
@SuppressWarnings("WeakerAccess")
public class SimpleCart implements Cart {

    private Customer customer;
    private OrderService orderService;
    private ProductService productService;
    private DiscountService discountService;
    private Map<Product, Integer> products;
    private Set<Discount> vouchers;

    public SimpleCart() {}

    @Autowired
    public SimpleCart(DiscountService discountService, ProductService productService,
                      OrderService orderService, Customer customer) {
        this.products = new HashMap<>();
        this.vouchers = new HashSet<>();
        this.discountService = discountService;
        this.productService = productService;
        this.orderService = orderService;
        this.customer = customer;
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null) throw new NoQuantityException();
        if (quantity == 0) throw new ZeroQuantityException();
        if (quantity < 0) throw new NegativeQuantityException();
    }

    @Override
    public void addItem(Long itemId, Integer quantity) {
        validateQuantity(quantity);
        Product product = productService.getById(itemId);
        products.merge(product, quantity, Integer::sum);
    }

    @Override
    public void removeItem(Long itemId, Integer quantity) {
        validateQuantity(quantity);
        Product product = productService.getById(itemId);
        products.computeIfPresent(product,
                (key, value) -> value >= quantity ? value - quantity : 0);
    }

    @Override
    public void addVoucher(String voucherName) {
        Discount discount = discountService.getByVoucher(voucherName);
        vouchers.add(discount);
    }

    @Override
    public void removeVoucher(String voucherName) {
        Discount discount = discountService.getByVoucher(voucherName);
        vouchers.remove(discount);
    }

    @Override
    public NewOrder getOrder() {
        Map<Discount, BigDecimal> discounts;
        discounts = discountService.compute(customer, products, vouchers);
        return new NewOrder(customer, products, discounts);
    }

    @Override
    public void cancel() {
        this.products = new HashMap<>();
        this.vouchers = new HashSet<>();
    }

    @Override
    public NewOrder buy() {
        NewOrder order = getOrder();
        this.orderService.place(order);
        this.products = new HashMap<>();
        this.vouchers = new HashSet<>();
        return order;
    }
}
