package ua.rd.pizza.service;

import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.NewOrder;
import ua.rd.pizza.domain.Order;

public interface OrderService {
    Order placeNewOrder(Customer customer, Integer ... pizzasID);
    void place(NewOrder order);
}
