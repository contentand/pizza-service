package ua.rd.pizza.service;

import ua.rd.pizza.domain.other.Order;

public interface Cart {
    void addItem(Long itemId, Integer quantity);
    void removeItem(Long itemId, Integer quantity);
    void addVoucher(String voucherName);
    void removeVoucher(String voucherName);
    Order getOrder();
    void cancel();
    Order buy();
}
