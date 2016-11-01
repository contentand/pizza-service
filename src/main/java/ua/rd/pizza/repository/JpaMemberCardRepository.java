package ua.rd.pizza.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.other.MemberCard;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

@Repository @Primary
public class JpaMemberCardRepository implements MemberCardRepository {

    private @PersistenceContext EntityManager entityManager;

    @Override
    public MemberCard getByCustomer(Customer customer) {
        return null;
    }

    @Override
    public void addAmount(Customer customer, BigDecimal amount) {

    }
}
