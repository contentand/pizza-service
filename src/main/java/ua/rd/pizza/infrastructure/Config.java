package ua.rd.pizza.infrastructure;

public interface Config {
    Class<?> getImplementation(String beanName);
}
