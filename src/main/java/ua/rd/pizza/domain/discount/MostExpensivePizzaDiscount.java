package ua.rd.pizza.domain.discount;

import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.product.Pizza;
import ua.rd.pizza.domain.product.Product;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MostExpensivePizzaDiscount extends Discount {

    private static final BigDecimal NO_DISCOUNT = new BigDecimal("0");
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.30");

    @Override
    public BigDecimal apply(Customer customer,
                            Map<Product, Integer> products,
                            Map<Discount, BigDecimal> assignedDiscounts) {

        Set<Pizza> pizzas = products
                .keySet()
                .stream()
                .filter(product -> product instanceof Pizza)
                .map(product -> (Pizza) product)
                .collect(Collectors.toSet());

        if (pizzas.size() <= 4) return NO_DISCOUNT;

        BigDecimal mostExpensivePizzaPrice = pizzas.stream()
                .map(Product::getUnitPrice)
                .reduce((price1, price2) -> price1.compareTo(price2) > 0 ? price1 : price2)
                .orElse(new BigDecimal("0"));

        return mostExpensivePizzaPrice.multiply(DISCOUNT_RATE);
    }
}
