package ua.rd.pizza.domain;

import java.math.BigDecimal;

public class MemberCard {
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
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
