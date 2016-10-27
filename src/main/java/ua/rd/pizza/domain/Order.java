package ua.rd.pizza.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component @Scope("prototype")
@Entity  @Table(name = "orders")
public class Order {

    @TableGenerator(name = "ids", table = "ids", pkColumnValue = "orders")
    @Id @GeneratedValue(generator = "ids")
    private Long id;

    @ManyToOne
    private Customer customer;

    @ElementCollection
    @CollectionTable
    @MapKeyClass(Pizza.class)
    @MapKeyColumn(name = "pizza_id")
    @Column(name="count")
    private Map<Pizza, Long> pizzas;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Order() {}

    public Order(Customer customer, List<Pizza> pizzas) {
        if (customer == null || pizzas == null) throw new NullPointerException();
        this.status = Status.NEW;
        this.customer = customer;
        this.pizzas = pizzas.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(pizzas.toString());
    }

    public Order(Long id, Customer customer, List<Pizza> pizzas) {
        this(customer, pizzas);
        this.id = id;
    }

    public void setPizzas(List<Pizza> pizzas) {

        Map<Pizza, Long> piz = pizzas.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Pizza p : pizzas) {
            this.pizzas.merge(p, piz.get(p), Long::sum);
        }
    }

    public enum Status {
        NEW("IN_PROGRESS", "CANCELLED", "DONE"),
        IN_PROGRESS("CANCELLED", "DONE"),
        CANCELLED(),
        DONE();

        private List<String> allowedStatuses;

        Status(String ... statuses) {
            this.allowedStatuses = Arrays.asList(statuses);
        }

        boolean canChangeTo(Status status) {
            return allowedStatuses.contains(status.name());
        }
    }

    public Status getStatus() {
        return status;
    }

    public boolean setStatus(Status newStatus) {
        if (this.status.canChangeTo(newStatus)) {
            if (Status.DONE.equals(newStatus)) {
                MemberCard card = customer.getMemberCard();
                if (card != null) {
                    card.setAmount(card.getAmount().add(getPrice()));
                }
            }
            this.status = newStatus;
            return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", pizzas=" + pizzas +
                '}';
    }

    public BigDecimal getPrice() {
        BigDecimal price = new BigDecimal(0);
        if (pizzas.size() > 4) {
            price = getPriceWithMostExpensivePizzaDiscounted();
        } else if (pizzas.size() > 0){
            price = getOrdinaryPrice();
        }
        return loyalCustomerDiscount(price).stripTrailingZeros();
    }

    private BigDecimal loyalCustomerDiscount(BigDecimal price) {
        MemberCard card = customer.getMemberCard();
        if (card != null) {
            BigDecimal tenPercentOfCard = percent("0.10", card.getAmount());
            BigDecimal thirtyPercentOfPrice = percent("0.30", price);
            if (thirtyPercentOfPrice.compareTo(tenPercentOfCard) < 0) {
                return price.subtract(thirtyPercentOfPrice);
            } else {
                return price.subtract(tenPercentOfCard);
            }
        }
        return price;
    }

    private BigDecimal percent(String rate, BigDecimal amount) {
        return amount.multiply(new BigDecimal(rate));
    }

    private BigDecimal getOrdinaryPrice() {
        BigDecimal price = new BigDecimal(0);
        for (Pizza pizza : pizzas.keySet()) {
            price = price.add(pizza.getPrice().multiply(new BigDecimal(pizzas.get(pizza))));
        }
        return price;
    }

    private BigDecimal getPriceWithMostExpensivePizzaDiscounted() {
        Pizza mostExpensivePizza = getMostExpensivePizza();
        BigDecimal price = getOrdinaryPrice();
        return price.subtract(discount("0.3", mostExpensivePizza));
    }

    private BigDecimal discount(String discountRate, Pizza mostExpensivePizza) {
        return mostExpensivePizza.getPrice().multiply(new BigDecimal(discountRate))
                .round(new MathContext(2, RoundingMode.UP));
    }

    private Pizza getMostExpensivePizza() {
        List<Pizza> pizzas = new ArrayList<>(this.pizzas.keySet());
        Pizza mostExpensivePizza = pizzas.get(0);
        for (Pizza pizza : pizzas) {
            if (pizza.getPrice().compareTo(mostExpensivePizza.getPrice()) > 0) {
                mostExpensivePizza = pizza;
            }
        }
        return mostExpensivePizza;
    }
}
