package ua.rd.pizza.repository;

import ua.rd.pizza.domain.Customer;

public interface CustomerRepository {
    Customer get(Integer id);
}
