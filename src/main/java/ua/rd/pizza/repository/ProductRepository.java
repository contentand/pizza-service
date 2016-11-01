package ua.rd.pizza.repository;

import ua.rd.pizza.domain.product.Product;

public interface ProductRepository {
    Product getById(Long itemId);
    Product save(Product product);
}
