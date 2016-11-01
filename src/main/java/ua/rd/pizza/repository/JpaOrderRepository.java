package ua.rd.pizza.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ua.rd.pizza.domain.other.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository @Primary
public class JpaOrderRepository implements OrderRepository {

    private @PersistenceContext EntityManager entityManager;

    @Override
    public Order save(Order order) {
        return this.entityManager.merge(order);
    }
}
