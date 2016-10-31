package ua.rd.pizza.repository;

import ua.rd.pizza.domain.product.Pizza;

public interface PizzaRepository {
    Pizza find(Integer id);
    Pizza save(Pizza pizza);
}
