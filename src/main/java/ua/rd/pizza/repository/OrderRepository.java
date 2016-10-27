package ua.rd.pizza.repository;

import ua.rd.pizza.domain.Order;

public interface OrderRepository {
    Order save(Order order);
}
