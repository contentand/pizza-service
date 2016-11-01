package ua.rd.pizza.repository;

import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.other.MemberCard;

import java.math.BigDecimal;

public interface MemberCardRepository {

    MemberCard getByCustomer(Customer customer);
    void addAmount(Customer customer, BigDecimal amount);
}
