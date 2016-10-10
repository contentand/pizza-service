package ua.rd.pizza.repository;

import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.MemberCard;
import ua.rd.pizza.infrastructure.annotation.PostCreate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<Integer, Customer> customerMap;

    public InMemoryCustomerRepository() {
        customerMap = new HashMap<>();
    }

    @PostCreate
    public void init() {
        customerMap.put(1, new Customer(1,
                "Semen Avdeevich",
                "12 Super Str., Kyiv",
                new MemberCard(1, new BigDecimal("0.00"))));
        customerMap.put(2, new Customer(2,
                "Daria Kapenskaya",
                "45 Strange Str., Kyiv",
                new MemberCard(2, new BigDecimal("230.00"))));
        customerMap.put(3, new Customer(3,
                "Voldemar Brezhnev",
                "1 Awesome Str., Kyiv",
                new MemberCard(3, new BigDecimal("30.00"))));
    }


    @Override
    public Customer get(Integer id) {
        return customerMap.get(id);
    }
}
