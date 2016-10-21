package ua.rd.pizza.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class MemberCard {
    @Id @GeneratedValue(strategy= GenerationType.TABLE)
    private Integer id;
    private BigDecimal amount;

    public MemberCard(BigDecimal amount) {
        this.amount = amount;
    }

    public MemberCard(Integer id, BigDecimal amount) {
        this(amount);
        if (amount == null) throw new NullPointerException();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount.stripTrailingZeros();
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
