package com.cafepos.payment;

import com.cafepos.order.Order;

public final class CardPayment implements PaymentStrategy {
    private final String cardNumber;
    public CardPayment(String cardNumber) { this.cardNumber = cardNumber; }

    @Override
    public void pay(Order order, int tax) {
        String maskedCard = "****" +  cardNumber.substring(cardNumber.length() - 4);
        System.out.println("[Card] Customer paid " + order.totalWithTax(tax) + " EUR with card " + maskedCard);
    }
}