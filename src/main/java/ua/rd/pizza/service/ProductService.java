package ua.rd.pizza.service;

import ua.rd.pizza.domain.product.Product;

public interface ProductService {
    Product getById(Long itemId);

}
