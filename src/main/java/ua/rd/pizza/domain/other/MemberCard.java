package ua.rd.pizza.domain.other;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "cards")
@TableGenerator(
        name = "CARD_GENERATOR",
        table = "IDS",
        pkColumnName = "NAME",
        pkColumnValue = "CARDS",
        valueColumnName = "VALUE",
        initialValue = 1,
        allocationSize = 1)
public class MemberCard {
    @Id @GeneratedValue(generator = "CARD_GENERATOR")
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
