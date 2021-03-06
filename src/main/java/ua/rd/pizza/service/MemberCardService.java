package ua.rd.pizza.service;

import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.other.MemberCard;

import java.math.BigDecimal;


public interface MemberCardService {
    MemberCard getByCustomer(Customer customer);
    void addAmount(Customer customer, BigDecimal totalWithDiscount);
}
