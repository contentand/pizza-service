package ua.rd.pizza.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.repository.ProductRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleProductServiceTest extends Mockito {

    private ProductService productService;
    private ProductRepository productRepository;

    @Before
    public void setup() {
        productRepository = mock(ProductRepository.class);
        productService = new SimpleProductService(productRepository);
    }

    @Test
    public void getById_getsById() throws Exception {
        Long ID = 1L;
        productService.getById(ID);
        verify(productRepository).getById(eq(ID));
    }

    @Test
    public void save_savesProduct() throws Exception {
        Product RESULT = mock(Product.class);
        Product PRODUCT = mock(Product.class);
        when(productRepository.save(PRODUCT)).thenReturn(RESULT);

        Product result = productService.save(PRODUCT);
        verify(productRepository).save(eq(PRODUCT));
        assertEquals(RESULT, result);
    }

    @Test
    public void getAllProducts_returnsAllProducts() throws Exception {
        List<Product> PRODUCTS = Collections.unmodifiableList(Collections.emptyList());
        when(productRepository.getAllProducts()).thenReturn(PRODUCTS);

        List<Product> result = productService.getAllProducts();
        assertEquals(PRODUCTS, result);
    }

}