package ua.rd.pizza.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "cards")
public class MemberCard {
    @TableGenerator(name = "ids", table = "ids", pkColumnValue = "cards")
    @Id @GeneratedValue(generator = "ids")
    private Integer id;
    private LocalDate date;
    private BigDecimal amount;

    public MemberCard() {
    }

    public MemberCard(Integer id, LocalDate date, BigDecimal amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
