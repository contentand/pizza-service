package ua.rd.pizza.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import ua.rd.pizza.domain.product.Pizza;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static ua.rd.pizza.domain.Order.Status.*;
import static ua.rd.pizza.domain.Order.Status;

@RunWith(Theories.class)
public class OrderTest {

    private Customer customer;

    @Before
    public void before() {
        customer = new Customer(1, "Sam Anderson", "7 Amelia St, Austin, TX, USA",
                new MemberCard(1, LocalDate.now(), new BigDecimal("0.00")));
    }

    @DataPoints("LST")
    public static List<Order.Status[]> legalStatusTransitions() {
        List<Order.Status[]> list = new ArrayList<>();
        list.add(new Status[]{NEW, IN_PROGRESS});
        list.add(new Status[]{NEW, CANCELLED});
        list.add(new Status[]{IN_PROGRESS, CANCELLED});
        list.add(new Status[]{IN_PROGRESS, DONE});
        return list;
    }

    @DataPoints("IST")
    public static List<Order.Status[]> illegalStatusTransitions() {
        List<Order.Status[]> list = new ArrayList<>();
        list.add(new Status[]{NEW, NEW});
        list.add(new Status[]{IN_PROGRESS, NEW});
        list.add(new Status[]{IN_PROGRESS, IN_PROGRESS});
        list.add(new Status[]{CANCELLED, NEW});
        list.add(new Status[]{CANCELLED, IN_PROGRESS});
        list.add(new Status[]{CANCELLED, CANCELLED});
        list.add(new Status[]{CANCELLED, DONE});
        list.add(new Status[]{DONE, NEW});
        list.add(new Status[]{DONE, IN_PROGRESS});
        list.add(new Status[]{DONE, CANCELLED});
        list.add(new Status[]{DONE, DONE});
        return list;
    }

    @Test
    public void canCalculatePrice() throws Exception {
        // given
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.40"), Pizza.Type.VEGETARIAN));
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.40"), Pizza.Type.VEGETARIAN));
        pizzas.add(new Pizza(2, "Hawaii", new BigDecimal("170.00"), Pizza.Type.MEAT));
        Order order = new Order(1L, customer, pizzas);

        // when
        BigDecimal price = order.getPrice();

        // then
        assertEquals(new BigDecimal("416.8"), price);
    }

    @Test
    public void canDiscountOneMostExpensivePizzaIfMoreThanFourPizzasOrdered() throws Exception {
        // given
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.4"), Pizza.Type.VEGETARIAN));
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.4"), Pizza.Type.VEGETARIAN));
        pizzas.add(new Pizza(2, "Hawaii", new BigDecimal("170"), Pizza.Type.MEAT));
        pizzas.add(new Pizza(2, "Hawaii", new BigDecimal("170"), Pizza.Type.MEAT));
        pizzas.add(new Pizza(2, "Hawaii", new BigDecimal("170"), Pizza.Type.MEAT));
        Order order = new Order(1L, customer, pizzas);

        // when
        BigDecimal price = order.getPrice();

        // then
        assertEquals(new BigDecimal("705.8"), price);
    }

    @Theory
    public void legalStatusChange_StatusChanges(@FromDataPoints("LST") Order.Status[] legalStatusTransition) {
        // given
        Status from = legalStatusTransition[0];
        Status to = legalStatusTransition[1];
        Order order = new Order(1L, customer, Collections.emptyList());
        order.setStatus(from);
        // when
        boolean result = order.setStatus(to);
        // then
        assertEquals(to, order.getStatus());
        assertTrue(result);
    }

    @Theory
    public void illegalStatusChange_StatusChanges(@FromDataPoints("IST") Order.Status[] illegalStatusTransition) {
        // given
        Status from = illegalStatusTransition[0];
        Status to = illegalStatusTransition[1];
        Order order = new Order(customer, Collections.emptyList());
        order.setStatus(from);
        // when
        boolean result = order.setStatus(to);
        // then
        if (!to.equals(from)) {
            assertNotEquals(to, order.getStatus());
        }
        assertFalse(result);
    }

    @Test
    public void orderAmountShouldGoToMemberCard() throws Exception {
        // given
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.4"), Pizza.Type.VEGETARIAN));
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.4"), Pizza.Type.VEGETARIAN));
        Order order = new Order(customer, pizzas);
        // when
        order.setStatus(DONE);
        // then
        MemberCard card = customer.getMemberCard();
        assertEquals(new BigDecimal("246.8"), card.getAmount());
    }

    @Test
    public void tenPercentOfMemberCardAmountShouldBeDiscounted() throws Exception {
        // given
        Customer loyalCustomer = new Customer(1, "Sam Rodgers", "12 Lucky St., Kyiv",
                new MemberCard(1, LocalDate.now(), new BigDecimal("100")));
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.4"), Pizza.Type.VEGETARIAN));
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.4"), Pizza.Type.VEGETARIAN));
        Order order = new Order(loyalCustomer, pizzas);
        // when
        BigDecimal amount = order.getPrice();
        // then
        assertEquals(new BigDecimal("236.8"), amount);
    }

    @Test
    public void tenPercentOfMemberCardAmountMoreThanThirtyPercentOfPrice_discounts30Percent() throws Exception {
        // given
        Customer loyalCustomer = new Customer(1, "Sam Rodgers", "12 Lucky St., Kyiv",
                new MemberCard(1, LocalDate.now(), new BigDecimal("10000")));
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.4"), Pizza.Type.VEGETARIAN));
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.4"), Pizza.Type.VEGETARIAN));
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.4"), Pizza.Type.VEGETARIAN));
        pizzas.add(new Pizza(1, "Margarita", new BigDecimal("123.4"), Pizza.Type.VEGETARIAN));
        Order order = new Order(loyalCustomer, pizzas);
        // when
        BigDecimal amount = order.getPrice();
        // then
        assertEquals(new BigDecimal("345.52"), amount);
    }
}