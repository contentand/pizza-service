package ua.rd.pizza.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.rd.pizza.domain.other.Customer;
import ua.rd.pizza.domain.other.MemberCard;
import ua.rd.pizza.repository.MemberCardRepository;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class SimpleMemberCardServiceTest extends Mockito {

    private static final Customer CUSTOMER = new Customer();
    private static final MemberCard MEMBER_CARD = mock(MemberCard.class);

    private SimpleMemberCardService cardService;
    private MemberCardRepository repository;

    private MemberCardRepository cardRepository() {
        repository = mock(MemberCardRepository.class);
        when(repository.getByCustomer(eq(CUSTOMER))).thenReturn(MEMBER_CARD);
        return repository;
    }

    @Before
    public void setup() {
        this.cardService = new SimpleMemberCardService(cardRepository());
    }

    @Test
    public void hasDefaultConstructor() throws Exception {
        new SimpleMemberCardService();
    }

    @Test
    public void getByCustomer_returnsMemberCard() throws Exception {
        MemberCard card = cardService.getByCustomer(CUSTOMER);
        assertEquals(card, MEMBER_CARD);
    }

    @Test
    public void addAmount_addsAmountToCard() throws Exception {
        cardService.addAmount(CUSTOMER, new BigDecimal("222.23"));
        verify(repository).addAmount(eq(CUSTOMER), eq(new BigDecimal("222.23")));
    }
}