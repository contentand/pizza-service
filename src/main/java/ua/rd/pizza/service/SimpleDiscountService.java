package ua.rd.pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.discount.Discount;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.exception.NoSuchVoucherException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("WeakerAccess")
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

        return nonZeroDiscounts(assignedDiscounts);
    }

    private Map<Discount,BigDecimal> nonZeroDiscounts(Map<Discount, BigDecimal> assignedDiscounts) {
        Set<Discount> zeroDiscounts = assignedDiscounts.entrySet().stream()
                .filter(entry -> entry.getValue().equals(new BigDecimal(0)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        //noinspection Convert2streamapi
        for (Discount discount : zeroDiscounts) assignedDiscounts.remove(discount);
        return assignedDiscounts;
    }
}
