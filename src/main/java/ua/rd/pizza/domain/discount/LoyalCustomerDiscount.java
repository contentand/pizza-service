package ua.rd.pizza.domain.discount;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.MemberCard;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.service.MemberCardService;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.Map;

public class LoyalCustomerDiscount extends Discount {

    private final MemberCardService cardService;

    public LoyalCustomerDiscount() {
        this.cardService = null;
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

        return discountAmount; // TODO PLEASE FINISH!!!

    }
}
