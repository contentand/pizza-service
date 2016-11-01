package ua.rd.pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.other.MemberCard;
import ua.rd.pizza.repository.MemberCardRepository;

import java.math.BigDecimal;

@Service
public class SimpleMemberCardService implements MemberCardService {

    private MemberCardRepository memberCardRepository;

    public SimpleMemberCardService() {
    }

    @Autowired
    public SimpleMemberCardService(MemberCardRepository memberCardRepository) {
        this.memberCardRepository = memberCardRepository;
    }

    @Override @Transactional
    public MemberCard getByCustomer(Customer customer) {
        return memberCardRepository.getByCustomer(customer);
    }

    @Override @Transactional
    public void addAmount(Customer customer, BigDecimal amount) {
        MemberCard card = memberCardRepository.getByCustomer(customer);
        if (card != null) memberCardRepository.addAmount(customer, amount);
    }
}
