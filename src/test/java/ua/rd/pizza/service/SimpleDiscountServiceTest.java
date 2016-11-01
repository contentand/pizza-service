package ua.rd.pizza.service;

import org.junit.Test;
import org.mockito.Mockito;
import ua.rd.pizza.domain.discount.Discount;
import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.exception.NoSuchVoucherException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class SimpleDiscountServiceTest extends Mockito {

    private static final String VOUCHER_NAME = "any";

    DiscountService discountService;

    @Test
    public void hasDefaultConstructor() {
        new SimpleDiscountService();
    }

    @Test(expected = NullPointerException.class)
    public void noDiscountsPassedToConstructor_throwsNullPointerException() throws Exception {
        new SimpleDiscountService(null);
    }

    @Test(expected = NoSuchVoucherException.class)
    public void getByVoucher_throwsNoSuchVoucherException() throws Exception {
        this.discountService = new SimpleDiscountService(Collections.emptySet());
        this.discountService.getByVoucher(VOUCHER_NAME);
    }

    @Test
    public void compute_doesNotLetConcreteDiscountsModifyAnything() throws Exception {
        Customer customer = new Customer(1, "Sam", "Address");
        Discount modifiesCustomer = new Discount() {
            @Override
            public BigDecimal apply(Customer customer,
                                    Map<Product, Integer> products,
                                    Map<Discount, BigDecimal> assignedDiscounts) {
                customer.setAddress("Other Address");
                customer.setName("Other Name");
                customer.setId(2);
                return new BigDecimal(0);
            }
        };
        Discount modifiesProductMap = new Discount() {
            @Override
            public BigDecimal apply(Customer customer,
                                    Map<Product, Integer> products,
                                    Map<Discount, BigDecimal> assignedDiscounts) {
                try {
                    products.put(mock(Product.class), 1);
                    fail();
                } catch (Exception e) {
                    // Should throw exception
                }
                return new BigDecimal(0);
            }
        };
        Discount modifiesDiscountMap = new Discount() {
            @Override
            public BigDecimal apply(Customer customer,
                                    Map<Product, Integer> products,
                                    Map<Discount, BigDecimal> assignedDiscounts) {
                try {
                    assignedDiscounts.put(mock(Discount.class), new BigDecimal(123));
                    fail();
                } catch (Exception e) {
                    // Should throw exception
                }
                return new BigDecimal(0);
            }
        };

        DiscountService discountService = new SimpleDiscountService(Stream
                .of(modifiesCustomer, modifiesProductMap, modifiesDiscountMap)
                .collect(Collectors.toSet()));

        discountService.compute(customer, new HashMap<>(), new HashSet<>());

        assertEquals(customer.getId().intValue(), 1);
        assertEquals(customer.getName(), "Sam");
        assertEquals(customer.getAddress(), "Address");
    }
}