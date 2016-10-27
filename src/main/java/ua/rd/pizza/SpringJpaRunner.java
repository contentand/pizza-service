package ua.rd.pizza;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.pizza.domain.Pizza;
import ua.rd.pizza.repository.JpaPizzaRepository;
import ua.rd.pizza.repository.PizzaRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class SpringJpaRunner {

    public static void main(String[] args) {
//        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
//        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"},
//                repoContext);
//
//        PizzaRepository repo = (PizzaRepository) context.getBean("jpaPizzaRepository");
//        Pizza p = new Pizza(null, "Mexicana", new BigDecimal("240.78"), Pizza.Type.MEAT);
//        Integer id = repo.save(p).getId();
//
//        p = repo.find(id);
//
//        System.out.println(p);
//
//        repoContext.close();
//        context.close();



        for (int i = 0; i < 99_999; i++) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(new Pizza(null, "Yummy", new BigDecimal("240.78"), Pizza.Type.MEAT));
            em.getTransaction().commit();
            em.close();
            emf.close();
        }



    }

}
