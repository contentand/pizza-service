package ua.rd.pizza;

import ua.rd.pizza.domain.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class JpaRunner {
    public static void main(String[] args) {

        Pizza pizza = new Pizza();
        pizza.setId(null);
        pizza.setName("Bavaria");
        pizza.setPrice(new BigDecimal("123.23"));
        pizza.setType(Pizza.Type.MEAT);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        entityManager.clear();

        tx.begin();
        entityManager.persist(pizza);
        tx.commit();
        entityManager.close();

        Integer id = pizza.getId();

        entityManager = entityManagerFactory.createEntityManager();

        Pizza pizzaRetrieved = entityManager.find(Pizza.class, id);
        System.out.println(pizzaRetrieved);
        entityManagerFactory.close();
    }
}
