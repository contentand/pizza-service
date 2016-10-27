package ua.rd.pizza.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.rd.pizza.domain.Customer;
import ua.rd.pizza.domain.MemberCard;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<Integer, Customer> customerMap;

    public InMemoryCustomerRepository() {
        customerMap = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        customerMap.put(1, new Customer(1,
                "Semen Avdeevich",
                "12 Super Str., Kyiv",
                new MemberCard(1, LocalDate.now(), new BigDecimal("0.00"))));
        customerMap.put(2, new Customer(2,
                "Daria Kapenskaya",
                "45 Strange Str., Kyiv",
                new MemberCard(2, LocalDate.now(), new BigDecimal("230.00"))));
        customerMap.put(3, new Customer(3,
                "Voldemar Brezhnev",
                "1 Awesome Str., Kyiv",
                new MemberCard(3, LocalDate.now(), new BigDecimal("30.00"))));
    }


    @Override
    public Customer get(Integer id) {
        return customerMap.get(id);
    }
}
