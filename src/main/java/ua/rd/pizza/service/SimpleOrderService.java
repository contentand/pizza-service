package ua.rd.pizza.service;

import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.Order;
import ua.rd.pizza.domain.Pizza;
import ua.rd.pizza.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;

    public SimpleOrderService(OrderRepository orderRepository, PizzaService pizzaService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
    }

    @Override
    public Order placeNewOrder(Customer customer, Integer... pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();

        for(Integer id : pizzasID){
            pizzas.add(pizzaService.getPizzaByID(id));
        }
        Order newOrder = new Order(customer, pizzas);

        orderRepository.saveOrder(newOrder);
        return newOrder;
    }
}