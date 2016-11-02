package ua.rd.pizza.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.rd.pizza.domain.discount.Discount;
import ua.rd.pizza.domain.discount.LoyalCustomerDiscount;
import ua.rd.pizza.domain.discount.MostExpensivePizzaDiscount;
import ua.rd.pizza.repository.DiscountRepository;
import ua.rd.pizza.service.MemberCardService;

@Configuration
public class DiscountJavaConfig {

    @Autowired
    DiscountRepository discountRepository;

    @Bean
    public Discount loyalCustomerDiscount(MemberCardService cardService) {
        return this.discountRepository.save(new LoyalCustomerDiscount(cardService));
    }

    @Bean
    public Discount mostExpensivePizzaDiscount() {
        return this.discountRepository.save(new MostExpensivePizzaDiscount());
    }

}
