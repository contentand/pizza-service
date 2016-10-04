package ua.rd.pizza;

import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.Order;
import ua.rd.pizza.infrastructure.InitialContext;
import ua.rd.pizza.service.OrderService;
import ua.rd.pizza.service.SimpleOrderService;


public class AppRunner {
    public static void main(String[] args) {
        Customer customer = null;
        Order order;

        OrderService orderService = InitialContext.getInstance("orderService");
        order = orderService.placeNewOrder(customer, 1, 2, 3);

        System.out.println(order);
    }

}
