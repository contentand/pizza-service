package ua.rd.pizza.service;

import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.discount.Discount;
import ua.rd.pizza.domain.product.Product;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface DiscountService {
    Discount getByVoucher(String voucherName);
    Map<Discount,BigDecimal> compute(Customer customer, Map<Product, Integer> products, Set<Discount> vouchers);
}
