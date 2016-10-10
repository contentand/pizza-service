package ua.rd.pizza;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.Order;
import ua.rd.pizza.service.OrderService;

import java.util.Arrays;


public class AppRunner {
    public static void main(String[] args) {
        Customer customer = null;
        Order order;

        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"}, repoContext);
        OrderService orderService = context.getBean("orderService", OrderService.class);
        order = orderService.placeNewOrder(customer, 1, 2, 3);
        System.out.println(order);

        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        context.close();
        repoContext.close();
    }

}
