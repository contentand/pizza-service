package ua.rd.pizza.infrastructure;

public interface Context {
    <T> T getBean(String beanName);
    <T> T getBean(String beanName, Class<T> type);
}
