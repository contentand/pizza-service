package ua.rd.pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.discount.Discount;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.exception.NoSuchVoucherException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class SimpleDiscountService implements DiscountService {

    private Set<Discount> discounts;

    public SimpleDiscountService() {}

    @Autowired
    public SimpleDiscountService(Set<Discount> discounts) {
        if (discounts == null) throw new NullPointerException("Null discounts");
        this.discounts = discounts;
    }

    @Override
    public Discount getByVoucher(String voucherName) {
        throw new NoSuchVoucherException();
    }

    @Override
    public Map<Discount, BigDecimal> compute(Customer customer,
                                             Map<Product, Integer> products,
                                             Set<Discount> vouchers) {

        Map<Discount, BigDecimal> assignedDiscounts = new HashMap<>();
        products = Collections.unmodifiableMap(products);
        customer = new Customer(customer);

        for (Discount discount : vouchers) {
            Map<Discount, BigDecimal> discounts = Collections.unmodifiableMap(assignedDiscounts);
            assignedDiscounts.put(discount, discount.apply(customer, products, discounts));
        }

        for (Discount discount : discounts) {
            Map<Discount, BigDecimal> discounts = Collections.unmodifiableMap(assignedDiscounts);
            assignedDiscounts.put(discount, discount.apply(customer, products, discounts));
        }

        return assignedDiscounts;
    }
}
