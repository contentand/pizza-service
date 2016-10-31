package ua.rd.pizza.service;

import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.MemberCard;


public interface MemberCardService {
    MemberCard getByCustomer(Customer customer);
}
