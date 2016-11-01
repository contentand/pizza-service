package ua.rd.pizza.repository;

import ua.rd.pizza.domain.other.Customer;

public interface CustomerRepository {
    Customer getById(Integer id);
    Customer save(Customer customer);
}
