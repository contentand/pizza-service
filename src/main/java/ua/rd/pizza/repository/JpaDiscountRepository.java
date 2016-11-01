package ua.rd.pizza.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizza.domain.discount.Discount;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaDiscountRepository implements DiscountRepository {

    private @PersistenceContext
    EntityManager entityManager;

    @Override @Transactional
    public Discount save(Discount discount) {
        entityManager.merge(discount);
        return discount;
    }
}
