package ua.rd.pizza.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ua.rd.pizza.domain.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository @Primary
public class JpaCustomerRepository implements CustomerRepository {

    private @PersistenceContext EntityManager entityManager;

    @Override
    public Customer get(Integer id) {
        return this.entityManager.find(Customer.class, id);
    }
}
