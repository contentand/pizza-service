package ua.rd.pizza.service;

import ua.rd.pizza.domain.Pizza;
import ua.rd.pizza.repository.PizzaRepository;

public class SimplePizzaService implements PizzaService {

    private final PizzaRepository pizzaRepository;

    public SimplePizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    public Pizza getPizzaByID(Integer id) {
        return pizzaRepository.get(id);
    }
}
