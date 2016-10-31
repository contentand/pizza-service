package ua.rd.pizza.domain.product;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PRODUCT_TYPE")
public abstract class Product {

    @TableGenerator(
            name = "PRODUCT_GENERATOR",
            table = "IDS",
            pkColumnName = "NAME",
            pkColumnValue = "PRODUCTS",
            valueColumnName = "VALUE",
            allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PRODUCT_GENERATOR")
    private Long id;
    private BigDecimal unitPrice;

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
