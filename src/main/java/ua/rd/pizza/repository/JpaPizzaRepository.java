package ua.rd.pizza.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizza.domain.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository @Primary
public class JpaPizzaRepository implements PizzaRepository {

    private @PersistenceContext EntityManager entityManager;

    @Override
    public Pizza find(Integer id) {
        return entityManager.find(Pizza.class, id);
    }

    @Override @Transactional
    public Pizza save(Pizza pizza) {
        Pizza p = entityManager.merge(pizza);
        return p;
    }


}
