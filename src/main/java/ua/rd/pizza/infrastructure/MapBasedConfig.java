package ua.rd.pizza.infrastructure;

import ua.rd.pizza.repository.InMemoryOrderRepository;
import ua.rd.pizza.repository.InMemoryPizzaRepository;
import ua.rd.pizza.service.SimpleOrderService;
import ua.rd.pizza.service.SimplePizzaService;

import java.util.HashMap;
import java.util.Map;

public class MapBasedConfig implements Config {

    private final Map<String, Class<?>> beans;

    public MapBasedConfig() {
        this.beans = new HashMap<>();
        this.beans.put("pizzaRepository", InMemoryPizzaRepository.class);
        this.beans.put("orderRepository", InMemoryOrderRepository.class);
        this.beans.put("pizzaService", SimplePizzaService.class);
        this.beans.put("orderService", SimpleOrderService.class);
    }

    public Class<?> getImplementation(String beanName) {
        return beans.get(beanName);
    }
}
