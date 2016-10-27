package ua.rd.pizza.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity  @Table(name = "pizzas")
public class Pizza implements Serializable {
    @TableGenerator(name = "ids", table = "ids", pkColumnValue = "pizzas", allocationSize = 2_000_000_000, initialValue = 2_000_000_000)
    @Id @GeneratedValue(generator = "ids")
    private Integer id;
    private String name;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        VEGETARIAN, SEA, MEAT
    }

    public Pizza() {}

    public Pizza(Integer id, String name, BigDecimal price, Type type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza)) return false;

        Pizza pizza = (Pizza) o;

        if (this.id != null && pizza.id != null && this.id.equals(pizza.id)) return true;

        if (getName() != null ? !getName().equals(pizza.getName()) : pizza.getName() != null) return false;
        if (getPrice() != null ? !getPrice().equals(pizza.getPrice()) : pizza.getPrice() != null) return false;
        return getType() == pizza.getType();

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }
}
