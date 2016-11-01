package ua.rd.pizza.repository;

import ua.rd.pizza.domain.product.Product;

import java.util.List;

public interface ProductRepository {
    Product getById(Long itemId);
    Product save(Product product);
    List<Product> getAllProducts();
}
