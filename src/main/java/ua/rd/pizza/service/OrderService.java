package ua.rd.pizza.service;

import ua.rd.pizza.domain.other.Order;

public interface OrderService {
//    Order placeNewOrder(Customer customer, Integer ... pizzasID);
    void place(Order order);
}
