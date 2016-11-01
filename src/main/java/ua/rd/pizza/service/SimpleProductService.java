package ua.rd.pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.repository.ProductRepository;

import java.util.List;

@Service
public class SimpleProductService implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public SimpleProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override @Transactional
    public Product getById(Long itemId) {
        return productRepository.getById(itemId);
    }

    @Override @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override @Transactional
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }
}
