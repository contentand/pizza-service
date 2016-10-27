package ua.rd.pizza.repository;

import ua.rd.pizza.domain.Pizza;

public interface PizzaRepository {
    Pizza find(Integer id);
    Pizza save(Pizza pizza);
}
