package ua.rd.pizza.domain;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private Long id;
    private Customer customer;
    private List<Pizza> pizzas;

    public Order() {}

    public Order(Long id, List<Pizza> pizzas) {
        this.id = id;
        this.pizzas = pizzas;
    }

    public Order(Customer customer, List<Pizza> pizzas) {
        this.customer = customer;
        this.pizzas = pizzas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", pizzas=" + pizzas +
                '}';
    }

    public BigDecimal getPrice() {
        BigDecimal price = new BigDecimal(0);
        for (Pizza pizza : pizzas) {
            price = price.add(pizza.getPrice());
        }
        return price;
    }
}
