package ua.rd.pizza;

import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.Order;
import ua.rd.pizza.service.SimpleOrderService;


public class AppRunner {
    public static void main(String[] args) {
        Customer customer = null;
        Order order;

        SimpleOrderService orderService = new SimpleOrderService();
        order = orderService.placeNewOrder(customer, 1, 2, 3);

        System.out.println(order);
    }

}
