package ua.rd.pizza.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizza.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository @Primary
public class JpaOrderRepository implements OrderRepository {

    private @PersistenceContext EntityManager entityManager;

    @Override @Transactional
    public Order save(Order order) {
        return this.entityManager.merge(order);
    }
}
