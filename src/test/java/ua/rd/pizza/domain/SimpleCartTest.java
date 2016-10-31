package ua.rd.pizza.domain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ua.rd.pizza.domain.discount.Discount;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.exception.*;
import ua.rd.pizza.service.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class SimpleCartTest extends Mockito {

    private static final String VALID_VOUCHER = "M8811";
    private static final String INVALID_VOUCHER = "INVALID";
    private static final Long VALID_PRODUCT_ID = 22L;
    private static final Long INVALID_PRODUCT_ID = 32L;

    private Cart cart;
    private OrderService orderService;

    private DiscountService getDiscountService() {
        DiscountService discountService = mock(DiscountService.class);
        Discount voucherDiscount = mock(Discount.class);
        Set<Discount> vouchers = new HashSet<>();
        vouchers.add(voucherDiscount);
        when(discountService.getByVoucher(eq(VALID_VOUCHER))).thenReturn(voucherDiscount);
        doThrow(NullPointerException.class).when(discountService).getByVoucher(eq(null));
        doThrow(NoSuchVoucherException.class).when(discountService).getByVoucher(eq(INVALID_VOUCHER));
        when(discountService.compute(any(), any(), any())).thenReturn(getDiscounts());
        when(discountService.compute(any(), any(), eq(vouchers))).thenReturn(getDiscountsWithVoucher());
        when(discountService.compute(any(), eq(Collections.emptyMap()), eq(Collections.emptySet())))
                .thenReturn(Collections.emptyMap());
        return discountService;
    }

    private Map<Discount, BigDecimal> getDiscountsWithVoucher() {
        Map<Discount, BigDecimal> discounts = getDiscounts();
        discounts.put(mock(Discount.class), new BigDecimal("3.00"));
        return discounts;
    }

    private Map<Discount, BigDecimal> getDiscounts() {
        Map<Discount, BigDecimal> discounts = new HashMap<>();
        discounts.put(mock(Discount.class), new BigDecimal("12.22"));
        discounts.put(mock(Discount.class), new BigDecimal("0"));
        discounts.put(mock(Discount.class), new BigDecimal("1.02"));
        return discounts;
    }

    private ProductService getProductService() {
        ProductService productService = mock(ProductService.class);
        Product product = getProduct();
        doThrow(NullPointerException.class).when(productService).getById(eq(null));
        doThrow(NoSuchProductException.class).when(productService).getById(eq(INVALID_PRODUCT_ID));
        when(productService.getById(eq(VALID_PRODUCT_ID))).thenReturn(product);
        return productService;
    }

    private Product getProduct() {
        Product product = mock(Product.class);
        when(product.getUnitPrice()).thenReturn(new BigDecimal("234.21"));
        return product;
    }

    private OrderService getOrderService() {
        OrderService orderService = mock(OrderService.class);
        this.orderService = orderService;
        return orderService;
    }

    private Customer getCustomer() {
        return new Customer();
    }

    @Before
    public void setup() throws Exception {
        this.cart = new SimpleCart(getDiscountService(), getProductService(), getOrderService(), getCustomer());
    }

    @Test
    public void hasDefaultConstructor() throws Exception {
        new SimpleCart();
    }

    @Test(expected = NoQuantityException.class)
    public void addItem_nullQuantityPassed_throwsNoQuantityException() throws Exception {
        this.cart.addItem(VALID_PRODUCT_ID, null);
    }

    @Test(expected = NegativeQuantityException.class)
    public void addItem_negativeQuantityPassed_throwsNegativeQuantityException() throws Exception {
        this.cart.addItem(VALID_PRODUCT_ID, -1);
    }

    @Test(expected = ZeroQuantityException.class)
    public void addItem_zeroQuantityPassed_throwsZeroQuantityException() throws Exception {
        this.cart.addItem(VALID_PRODUCT_ID, 0);
    }

    @Test(expected = NullPointerException.class)
    public void addItem_nullProductPassed_throwsNullPointerException() throws Exception {
        this.cart.addItem(null, 1);
    }

    @Test(expected = NoSuchProductException.class)
    public void addItem_invalidProductPassed_throwsNoSuchProductException() throws Exception {
        this.cart.addItem(INVALID_PRODUCT_ID, 1);
    }

    @Test
    public void addItem_validInput_returnsValidData() throws Exception {
        this.cart.addItem(VALID_PRODUCT_ID, 2);
        NewOrder order = this.cart.getOrder();
        assertEquals(new BigDecimal("468.42"), order.getTotal());
        assertEquals(new BigDecimal("455.18"), order.getTotalWithDiscount());
        assertEquals(new BigDecimal("13.24"), order.getDiscountAmount());
    }

    @Test(expected = NoQuantityException.class)
    public void removeItem_nullQuantityPassed_throwsNoQuantityException() throws Exception {
        this.cart.removeItem(VALID_PRODUCT_ID, null);
    }

    @Test(expected = NegativeQuantityException.class)
    public void removeItem_negativeQuantityPassed_throwsNegativeQuantityException() throws Exception {
        this.cart.removeItem(VALID_PRODUCT_ID, -1);
    }

    @Test(expected = ZeroQuantityException.class)
    public void removeItem_zeroQuantityPassed_throwsZeroQuantityException() throws Exception {
        this.cart.removeItem(VALID_PRODUCT_ID, 0);
    }

    @Test(expected = NullPointerException.class)
    public void removeItem_nullProductPassed_throwsNullPointerException() throws Exception {
        this.cart.removeItem(null, 1);
    }

    @Test(expected = NoSuchProductException.class)
    public void removeItem_invalidProductPassed_throwsNoSuchProductException() throws Exception {
        this.cart.removeItem(INVALID_PRODUCT_ID, 1);
    }

    @Test
    public void removeItem_validInput_returnsValidData() throws Exception {
        this.cart.addItem(VALID_PRODUCT_ID, 3);
        this.cart.removeItem(VALID_PRODUCT_ID, 1);
        NewOrder order = this.cart.getOrder();
        assertEquals(new BigDecimal("468.42"), order.getTotal());
        assertEquals(new BigDecimal("455.18"), order.getTotalWithDiscount());
        assertEquals(new BigDecimal("13.24"), order.getDiscountAmount());
    }

    @Test(expected = NullPointerException.class)
    public void addVoucher_nullVoucher_throwsNullPointerException() throws Exception {
        this.cart.addVoucher(null);
    }

    @Test(expected = NoSuchVoucherException.class)
    public void addVoucher_invalidVoucher_throwsNoSuchVoucherException() throws Exception {
        this.cart.addVoucher(INVALID_VOUCHER);
    }

    @Test
    public void addVoucher_validVoucher_returnsValidData() throws Exception {
        this.cart.addItem(VALID_PRODUCT_ID, 1);
        this.cart.addVoucher(VALID_VOUCHER);
        NewOrder order = this.cart.getOrder();
        assertEquals(new BigDecimal("234.21"), order.getTotal());
        assertEquals(new BigDecimal("217.97"), order.getTotalWithDiscount());
        assertEquals(new BigDecimal("16.24"), order.getDiscountAmount());
    }

    @Test
    public void addVoucher_sameVoucherTwice_voucherCalculatedOnce() throws Exception {
        this.cart.addItem(VALID_PRODUCT_ID, 1);
        this.cart.addVoucher(VALID_VOUCHER);
        this.cart.addVoucher(VALID_VOUCHER);
        NewOrder order = this.cart.getOrder();
        assertEquals(new BigDecimal("234.21"), order.getTotal());
        assertEquals(new BigDecimal("217.97"), order.getTotalWithDiscount());
        assertEquals(new BigDecimal("16.24"), order.getDiscountAmount());
    }

    @Test(expected = NullPointerException.class)
    public void removeVoucher_nullVoucher_throwsNullPointerException() throws Exception {
        this.cart.removeVoucher(null);
    }

    @Test(expected = NoSuchVoucherException.class)
    public void removeVoucher_invalidVoucher_throwsNoSuchVoucherException() throws Exception {
        this.cart.removeVoucher(INVALID_VOUCHER);
    }

    @Test
    public void removeVoucher_validVoucher_returnsValidData() throws Exception {
        this.cart.addItem(VALID_PRODUCT_ID, 1);
        this.cart.addVoucher(VALID_VOUCHER);
        this.cart.removeVoucher(VALID_VOUCHER);
        NewOrder order = this.cart.getOrder();
        assertEquals(new BigDecimal("234.21"), order.getTotal());
        assertEquals(new BigDecimal("220.97"), order.getTotalWithDiscount());
        assertEquals(new BigDecimal("13.24"), order.getDiscountAmount());
    }

    @Test
    public void removeVoucher_unsetValidVoucher_doesNothing() throws Exception {
        this.cart.addItem(VALID_PRODUCT_ID, 1);
        this.cart.removeVoucher(VALID_VOUCHER);
        NewOrder order = this.cart.getOrder();
        assertEquals(new BigDecimal("234.21"), order.getTotal());
        assertEquals(new BigDecimal("220.97"), order.getTotalWithDiscount());
        assertEquals(new BigDecimal("13.24"), order.getDiscountAmount());
    }

    @Test
    public void cancel_resetsAllProductsAndDiscountsToZero() throws Exception {
        this.cart.addItem(VALID_PRODUCT_ID, 1);
        this.cart.addVoucher(VALID_VOUCHER);
        this.cart.cancel();
        NewOrder order = this.cart.getOrder();
        assertEquals(Collections.EMPTY_MAP, order.getDiscounts());
        assertEquals(Collections.EMPTY_MAP, order.getProducts());
        assertEquals(new BigDecimal("0"), order.getTotal());
        assertEquals(new BigDecimal("0"), order.getTotalWithDiscount());
        assertEquals(new BigDecimal("0"), order.getDiscountAmount());
    }

    @Test
    public void buy_persistsOrderAndResetsAllProductsAndDiscountsToZero() throws Exception {
        this.cart.addItem(VALID_PRODUCT_ID, 1);
        this.cart.addVoucher(VALID_VOUCHER);
        this.cart.buy();

        ArgumentCaptor<NewOrder> orderArgumentCaptor = ArgumentCaptor.forClass(NewOrder.class);
        verify(orderService).place(orderArgumentCaptor.capture());

        NewOrder persistedOrder = orderArgumentCaptor.getValue();
        assertEquals(new BigDecimal("234.21"), persistedOrder.getTotal());
        assertEquals(new BigDecimal("217.97"), persistedOrder.getTotalWithDiscount());
        assertEquals(new BigDecimal("16.24"), persistedOrder.getDiscountAmount());

        NewOrder cartOrder = this.cart.getOrder();
        assertEquals(Collections.EMPTY_MAP, cartOrder.getDiscounts());
        assertEquals(Collections.EMPTY_MAP, cartOrder.getProducts());
        assertEquals(new BigDecimal("0"), cartOrder.getTotal());
        assertEquals(new BigDecimal("0"), cartOrder.getTotalWithDiscount());
        assertEquals(new BigDecimal("0"), cartOrder.getDiscountAmount());
    }
}
