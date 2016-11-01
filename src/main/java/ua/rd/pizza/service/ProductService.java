package ua.rd.pizza.service;

import ua.rd.pizza.domain.product.Product;

import java.util.List;

public interface ProductService {
    Product getById(Long itemId);
    Product save(Product product);
    List<Product> getAllProducts();
}
