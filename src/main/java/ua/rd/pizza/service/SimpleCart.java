package ua.rd.pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.discount.Discount;
import ua.rd.pizza.domain.other.Order;
import ua.rd.pizza.domain.product.Pizza;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.exception.NegativeQuantityException;
import ua.rd.pizza.exception.NoQuantityException;
import ua.rd.pizza.exception.PizzaLimitExceededException;
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

    private static final int PIZZA_LIMIT = 10;

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

    private void validatePizzaLimit(Product product, Integer quantity) {
        if (product instanceof Pizza) {
            if ((quantity + countPizzas()) > PIZZA_LIMIT) {
                throw new PizzaLimitExceededException();
            }
        }
    }

    private int countPizzas() {
        return products.entrySet()
                .stream()
                .filter(entry -> entry.getKey() instanceof Pizza)
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    @Override
    public void addItem(Long itemId, Integer quantity) {
        validateQuantity(quantity);
        Product product = productService.getById(itemId);
        validatePizzaLimit(product, quantity);
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
    public Order getOrder() {
        Map<Discount, BigDecimal> discounts;
        discounts = discountService.compute(customer, products, vouchers);
        return new Order(customer, products, discounts);
    }

    @Override
    public void cancel() {
        this.products = new HashMap<>();
        this.vouchers = new HashSet<>();
    }

    @Override
    public Order buy() {
        Order order = getOrder();
        this.orderService.place(order);
        this.products = new HashMap<>();
        this.vouchers = new HashSet<>();
        return order;
    }
}
