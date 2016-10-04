package ua.rd.pizza;

import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.Order;
import ua.rd.pizza.infrastructure.ApplicationContext;
import ua.rd.pizza.infrastructure.Context;
import ua.rd.pizza.infrastructure.MapBasedConfig;
import ua.rd.pizza.service.OrderService;


public class AppRunner {
    public static void main(String[] args) {
        Customer customer = null;
        Order order;

        Context context = new ApplicationContext(new MapBasedConfig());
        OrderService orderService = context.getBean("orderService");
        order = orderService.placeNewOrder(customer, 1, 2, 3);
        System.out.println(order);
    }

}
