package ua.rd.pizza.controllerrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.rd.pizza.domain.product.Pizza;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.service.ProductService;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    private ProductService productService;

    @Autowired
    public PizzaController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Pizza> get(@PathVariable Long id) {
        Product product = productService.getById(id);
        if (product == null) return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        if (!(product instanceof Pizza)) return new ResponseEntity<Pizza>(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>((Pizza) product, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> create(@RequestBody Pizza pizza, UriComponentsBuilder builder) {
        pizza = (Pizza) productService.save(pizza);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/pizza/{id}").buildAndExpand(pizza.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
