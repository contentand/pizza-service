package ua.rd.pizza.domain.product;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PRODUCT_TYPE")
@TableGenerator(
        name = "PRODUCT_GENERATOR",
        table = "IDS",
        pkColumnName = "NAME",
        pkColumnValue = "PRODUCTS",
        valueColumnName = "VALUE",
        initialValue = 1,
        allocationSize = 1)
public abstract class Product {
    @Id
    @GeneratedValue(generator = "PRODUCT_GENERATOR")
    protected Long id;
    protected BigDecimal unitPrice;

    public Long getId() {
        return id;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

}
