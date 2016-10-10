package ua.rd.pizza.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderTest {

    @Test
    public void canCalculatePrice() throws Exception {
        // given
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.40"), Pizza.Type.VEGETARIAN));
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.40"), Pizza.Type.VEGETARIAN));
        pizzas.add(new Pizza(2, "Hawaii", new BigDecimal("170.00"), Pizza.Type.MEAT));
        Order order = new Order(1L, pizzas);

        // when
        BigDecimal price = order.getPrice();

        // then
        assertEquals(new BigDecimal("416.80"), price);
    }
}