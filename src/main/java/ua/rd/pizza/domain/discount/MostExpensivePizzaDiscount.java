package ua.rd.pizza.domain.discount;

import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.product.Pizza;
import ua.rd.pizza.domain.product.Product;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Map;

@Entity
public class MostExpensivePizzaDiscount extends Discount {

    @Transient private static final BigDecimal NO_DISCOUNT = new BigDecimal("0");
    @Transient private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.30");
    @Transient private static final int PIZZA_NUMBER_THRESHOLD = 4;

    public MostExpensivePizzaDiscount() {
        super();
    }

    @Override
    public BigDecimal apply(Customer customer,
                            Map<Product, Integer> products,
                            Map<Discount, BigDecimal> assignedDiscounts) {

        int numberOfPizzas = products.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof Pizza)
                .mapToInt(Map.Entry::getValue)
                .sum();

        if (numberOfPizzas <= PIZZA_NUMBER_THRESHOLD) return NO_DISCOUNT;

        BigDecimal mostExpensivePizzaPrice = products
                .keySet()
                .stream()
                .filter(product -> product instanceof Pizza)
                .map(product -> (Pizza) product)
                .distinct()
                .map(Product::getUnitPrice)
                .reduce((price1, price2) -> price1.compareTo(price2) > 0 ? price1 : price2)
                .orElse(new BigDecimal("0"));

        return mostExpensivePizzaPrice.multiply(DISCOUNT_RATE);
    }
}
