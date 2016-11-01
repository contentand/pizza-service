package ua.rd.pizza.repository;

import ua.rd.pizza.domain.discount.Discount;

public interface DiscountRepository {

    Discount save(Discount discount);

}
