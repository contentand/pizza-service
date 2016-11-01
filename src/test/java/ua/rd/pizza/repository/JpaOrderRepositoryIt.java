package ua.rd.pizza.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:repoContextH2.xml")
public class JpaOrderRepositoryIt {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DataSource dataSource;

    @Test
    public void savesOrder() throws Exception {
//        JdbcTemplate template = new JdbcTemplate(dataSource);
//        template.execute("INSERT INTO pizzas (id) VALUES (857)");
//        template.execute("INSERT INTO pizzas (id) VALUES (2)");
//        template.execute("INSERT INTO customers (id) VALUES (1312)");
//
//        Order order = new Order(new Customer(1312, null, null, null),
//                Arrays.asList(new Pizza(857, null, null, null),
//                        new Pizza(857, null, null, null), new Pizza(2, null, null, null)));
//        order = orderRepository.save(order);
//
//        assertNotNull(order.getId());
//        template.query("SELECT * FROM order_pizzas WHERE Order_id = " + order.getId() + " ORDER BY pizzas_KEY ASC",
//                rs -> {
//                    assertTrue(rs.next());
//                    assertEquals(857, rs.getInt("pizzas_KEY"));
//                    assertEquals(2, rs.getInt("count"));
//                    assertTrue(rs.next());
//                    assertEquals(2, rs.getInt("pizzas_KEY"));
//                    assertEquals(1, rs.getInt("count"));
//                    assertFalse(rs.next());
//                });
//        template.query("SELECT * FROM orders WHERE id = " + order.getId(),
//                rs -> {
//                    assertTrue(rs.next());
//                    assertEquals(1312, rs.getInt("customer_id"));
//                    assertFalse(rs.next());
//                });
    }

}