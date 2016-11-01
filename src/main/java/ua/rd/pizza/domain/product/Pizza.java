package ua.rd.pizza.domain.product;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "pizzas")
public class Pizza extends Product implements Serializable {

    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        VEGETARIAN, SEA, MEAT
    }

    public Pizza() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ",name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", type=" + type +
                '}';
    }
}
