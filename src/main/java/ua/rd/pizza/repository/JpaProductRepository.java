package ua.rd.pizza.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ua.rd.pizza.domain.product.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository @Primary
public class JpaProductRepository implements ProductRepository {

    private @PersistenceContext
    EntityManager entityManager;

    @Override
    public Product getById(Long itemId) {
        return entityManager.find(Product.class, itemId);
    }

    @Override
    public Product save(Product product) {
        entityManager.persist(product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }
}
