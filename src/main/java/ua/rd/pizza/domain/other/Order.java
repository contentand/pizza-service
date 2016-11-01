package ua.rd.pizza.domain.other;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.rd.pizza.domain.discount.Discount;
import ua.rd.pizza.domain.product.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

@Component
@Scope("prototype")
@Entity
@Table(name = "orders")
@TableGenerator(
        name = "ORDER_GENERATOR",
        table = "IDS",
        pkColumnName = "NAME",
        pkColumnValue = "ORDERS",
        valueColumnName = "VALUE",
        initialValue = 1,
        allocationSize = 1)
public class Order {
    @Id
    @GeneratedValue(generator = "ORDER_GENERATOR")
    private Long id;
    private @ManyToOne Customer customer;
    private BigDecimal total;
    private BigDecimal discountAmount;
    private BigDecimal totalWithDiscount;

    @ElementCollection
    @CollectionTable
    @MapKeyClass(Product.class)
    @MapKeyColumn(name = "product_id")
    @Column(name="quantity")
    private Map<Product, Integer> products;
    @ElementCollection
    @CollectionTable
    @MapKeyClass(Discount.class)
    @MapKeyColumn(name = "discount_id")
    @Column(name="amount")
    private Map<Discount, BigDecimal> discounts;

    public Order() {}

    public Order(Customer customer, Map<Product, Integer> products, Map<Discount, BigDecimal> discounts) {
        this.customer = customer;
        this.products = products;
        this.discounts = discounts;
        this.total = products.entrySet().stream()
                .map((entry) ->
                    entry.getKey().getUnitPrice().multiply(new BigDecimal(entry.getValue()))
                ).reduce(new BigDecimal(0), BigDecimal::add);
        this.discountAmount = discounts.values().stream().reduce(new BigDecimal(0), BigDecimal::add);
        this.totalWithDiscount = this.total.subtract(this.discountAmount);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public Map<Discount, BigDecimal> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Map<Discount, BigDecimal> discounts) {
        this.discounts = discounts;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTotalWithDiscount() {
        return totalWithDiscount;
    }

    public void setTotalWithDiscount(BigDecimal totalWithDiscount) {
        this.totalWithDiscount = totalWithDiscount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", total=" + total +
                ", discountAmount=" + discountAmount +
                ", totalWithDiscount=" + totalWithDiscount +
                ", products=" + products +
                ", discounts=" + discounts +
                '}';
    }
}
