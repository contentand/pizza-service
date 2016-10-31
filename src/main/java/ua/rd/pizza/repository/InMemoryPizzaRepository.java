package ua.rd.pizza.repository;

import org.springframework.stereotype.Repository;
import ua.rd.pizza.domain.product.Pizza;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryPizzaRepository implements PizzaRepository {

    private final Map<Integer, Pizza> pizzaMap;

    public InMemoryPizzaRepository() {
        pizzaMap = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        pizzaMap.put(1, new Pizza(1, "Yummy Pizza", BigDecimal.valueOf(112.03), Pizza.Type.MEAT));
        pizzaMap.put(2, new Pizza(2, "Dummy Pizza", BigDecimal.valueOf(100.99), Pizza.Type.VEGETARIAN));
        pizzaMap.put(3, new Pizza(3, "Funny Pizza", BigDecimal.valueOf(130.89), Pizza.Type.SEA));
    }

    @Override
    public Pizza find(Integer id) {
        return pizzaMap.get(id);
    }

    @Override
    public Pizza save(Pizza pizza) {
        return pizza;
        // nothing : )
    }
}
