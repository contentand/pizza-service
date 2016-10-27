package ua.rd.pizza.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.rd.pizza.domain.Pizza;

import javax.sql.DataSource;
import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:repoContextH2.xml")
public class JpaPizzaRepositoryIt {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DataSource dataSource;

    @Test
    public void savesPizza_idIsNotNull() throws Exception {
        Pizza p = new Pizza(null, "Yummy", new BigDecimal("240.78"), Pizza.Type.MEAT);
        Integer id = pizzaRepository.save(p).getId();
        assertNotNull(id);
    }

    @Test
    public void savesPizza_dataIsInDatabase() throws Exception {
        Pizza p = new Pizza(null, "Yummy", new BigDecimal("240.78"), Pizza.Type.MEAT);
        Integer id = pizzaRepository.save(p).getId();
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.queryForObject("SELECT * FROM pizzas WHERE id = ?", (rs, i) -> {
            assertEquals("Yummy", rs.getString("name"));
            assertEquals(new BigDecimal("240.78"), rs.getBigDecimal("price"));
            assertEquals(Pizza.Type.MEAT, Pizza.Type.valueOf(rs.getString("type")));
            return null;
        }, id);
    }

    @Test
    public void findsPizza() throws Exception {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.execute("INSERT INTO pizzas (id, name, price, type) " +
                "VALUES (123, 'Napolitana', 222.34, 'MEAT')");
        Pizza p = pizzaRepository.find(123);

        assertEquals("Napolitana", p.getName());
        assertEquals(new BigDecimal("222.34"), p.getPrice());
        assertEquals(Pizza.Type.MEAT, p.getType());

    }
}