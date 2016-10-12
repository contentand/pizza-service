package ua.rd.pizza.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.rd.pizza.domain.Order;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private List<Order> orders;

    public InMemoryOrderRepository() {
        this.orders = new ArrayList<>();
    }

    @Override
    public void saveOrder(Order newOrder) {

    }
}
