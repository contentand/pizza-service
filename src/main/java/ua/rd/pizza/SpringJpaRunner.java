package ua.rd.pizza;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.rd.pizza.domain.other.Order;
import ua.rd.pizza.domain.product.Pizza;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.infrastructure.AppJavaConfig;
import ua.rd.pizza.service.Cart;
import ua.rd.pizza.service.ProductService;

import java.math.BigDecimal;

public class SpringJpaRunner {

    public static void main(String[] args) {

        ApplicationContext ct = new AnnotationConfigApplicationContext(AppJavaConfig.class);
        ProductService productService = ct.getBean(ProductService.class, "simpleProductService");

        Pizza p = new Pizza();
        p.setName("Margarita");
        p.setType(Pizza.Type.MEAT);
        p.setUnitPrice(new BigDecimal("232.12"));

        Product pr = productService.save(p);

        Cart c = ct.getBean(Cart.class);
        c.addItem(pr.getId(), 5);
        Order o = c.buy();
        System.out.println(o);




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


//
//        for (int i = 0; i < 99_999; i++) {
//            EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
//            EntityManager em = emf.createEntityManager();
//            em.getTransaction().begin();
//            em.persist(new Pizza(null, "Yummy", new BigDecimal("240.78"), Pizza.Type.MEAT));
//            em.getTransaction().commit();
//            em.close();
//            emf.close();
//        }



    }

}
