package ua.rd.pizza.domain.discount;

import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.product.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "DISCOUNT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCOUNT_TYPE")
public abstract class Discount {

    @Id
    private String id;

    public abstract BigDecimal apply(Customer customer,
                               Map<Product, Integer> products,
                               Map<Discount, BigDecimal> assignedDiscounts);

    public Discount() {
        this.id = getClass().getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
