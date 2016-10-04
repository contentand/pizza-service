package ua.rd.pizza.repository;

import ua.rd.pizza.domain.Order;

public interface OrderRepository {
    void saveOrder(Order newOrder);
}
