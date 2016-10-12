package ua.rd.pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.Order;
import ua.rd.pizza.domain.Pizza;
import ua.rd.pizza.infrastructure.annotation.Benchmark;
import ua.rd.pizza.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;

    @Autowired
    public SimpleOrderService(OrderRepository orderRepository, PizzaService pizzaService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
    }

    @Override @Benchmark(true)
    public Order placeNewOrder(Customer customer, Integer... pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();

        for(Integer id : pizzasID){
            pizzas.add(pizzaService.getPizzaByID(id));
        }
        Order newOrder = createNewOrder();
        newOrder.setCustomer(customer);
        newOrder.setPizzas(pizzas);

        orderRepository.saveOrder(newOrder);
        return newOrder;
    }

    @Lookup("order")
    Order createNewOrder() {
        throw new IllegalStateException();
    }


}