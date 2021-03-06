package ua.rd.pizza.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizza.domain.other.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository @Primary @Transactional
public class JpaCustomerRepository implements CustomerRepository {

    private @PersistenceContext EntityManager entityManager;

    @Override
    public Customer getById(Integer id) {
        return this.entityManager.find(Customer.class, id);
    }

    @Override
    public Customer save(Customer customer) {
        return entityManager.merge(customer);
    }
}
