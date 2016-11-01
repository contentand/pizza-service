package ua.rd.pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizza.domain.other.Order;
import ua.rd.pizza.repository.OrderRepository;

@Service
public class SimpleOrderService implements OrderService {

    private OrderRepository orderRepository;
    private MemberCardService memberCardService;

    @Autowired
    public SimpleOrderService(OrderRepository orderRepository, MemberCardService memberCardService) {
        this.orderRepository = orderRepository;
        this.memberCardService = memberCardService;
    }

    @Override @Transactional
    public void place(Order order) {
        orderRepository.save(order);
        memberCardService.addAmount(order.getCustomer(), order.getTotalWithDiscount());
    }

}