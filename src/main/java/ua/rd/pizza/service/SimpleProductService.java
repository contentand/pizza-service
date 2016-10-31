package ua.rd.pizza.service;

import org.springframework.stereotype.Service;
import ua.rd.pizza.domain.product.Product;

@Service
public class SimpleProductService implements ProductService {
    @Override
    public Product getById(Long itemId) {
        return null;
    }
}
