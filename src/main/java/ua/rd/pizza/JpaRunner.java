package ua.rd.pizza;

import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.MemberCard;
import ua.rd.pizza.domain.Order;
import ua.rd.pizza.domain.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class JpaRunner {
    public static void main(String[] args) {

        Pizza pizza = new Pizza();
        pizza.setId(null);
        pizza.setName("Bavaria");
        pizza.setPrice(new BigDecimal("123.23"));
        pizza.setType(Pizza.Type.MEAT);

        MemberCard card = new MemberCard(null, LocalDate.now(), new BigDecimal("0.00"));

        Customer customer = new Customer(null, "Sergey", "20 Milenko St", card);

        Order order = new Order(null, customer, Arrays.asList(pizza));

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        entityManager.clear();

        tx.begin();
        entityManager.persist(pizza);
        entityManager.persist(card);
        entityManager.persist(customer);
        entityManager.persist(order);
        tx.commit();
        entityManager.close();

        Integer id = pizza.getId();

        entityManager = entityManagerFactory.createEntityManager();

        Pizza pizzaRetrieved = entityManager.find(Pizza.class, id);
        System.out.println(pizzaRetrieved);
        entityManagerFactory.close();
    }
}
