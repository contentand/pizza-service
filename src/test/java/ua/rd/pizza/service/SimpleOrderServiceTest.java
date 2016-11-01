package ua.rd.pizza.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.other.Order;
import ua.rd.pizza.repository.OrderRepository;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class SimpleOrderServiceTest extends Mockito {

    private static final Customer CUSTOMER = mock(Customer.class);
    private static final BigDecimal AMOUNT = mock(BigDecimal.class);

    private OrderRepository orderRepository;
    private MemberCardService memberCardService;
    private OrderService orderService;
    private Order order;

    @Before
    public void setup() {
        order = mock(Order.class);;
        when(order.getCustomer()).thenReturn(CUSTOMER);
        when(order.getTotalWithDiscount()).thenReturn(AMOUNT);
        orderRepository = mock(OrderRepository.class);
        memberCardService = mock(MemberCardService.class);
        orderService = new SimpleOrderService(orderRepository, memberCardService);
    }

    @Test
    public void place_placesNewOrderAndUpdatesMemberCard() throws Exception {
        orderService.place(order);

        verify(orderRepository).save(eq(order));
        verify(memberCardService).addAmount(eq(CUSTOMER), eq(AMOUNT));
    }

}