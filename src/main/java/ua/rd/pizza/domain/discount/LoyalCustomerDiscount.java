package ua.rd.pizza.domain.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.other.MemberCard;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.service.MemberCardService;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Map;

@Entity
public class LoyalCustomerDiscount extends Discount {

    @Transient private MemberCardService cardService;

    public LoyalCustomerDiscount() {
        super();
    }

    @Autowired
    public LoyalCustomerDiscount(MemberCardService cardService) {
        this.cardService = cardService;
    }

    private static final BigDecimal NO_DISCOUNT = new BigDecimal("0");
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");
    private static final BigDecimal DISCOUNT_RATE_LIMIT = new BigDecimal("0.30");

    @Override
    public BigDecimal apply(Customer customer,
                            Map<Product, Integer> products,
                            Map<Discount, BigDecimal> assignedDiscounts) {

        if (this.cardService == null) throw new UnsupportedOperationException();

        MemberCard memberCard = this.cardService.getByCustomer(customer);

        if (memberCard == null) return NO_DISCOUNT;

        BigDecimal total = products.entrySet().stream()
                .map((entry) -> entry.getKey().getUnitPrice().multiply(new BigDecimal(entry.getValue())))
                .reduce(NO_DISCOUNT, BigDecimal::add);

        BigDecimal discountAmount = memberCard.getAmount().multiply(DISCOUNT_RATE);
        BigDecimal limitAmount = total.multiply(DISCOUNT_RATE_LIMIT);

        return discountAmount.compareTo(limitAmount) >= 0 ? discountAmount : limitAmount;

    }
}
