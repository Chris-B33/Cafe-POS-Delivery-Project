package org.yourcompany.yourproject;

public final class CashPayment implements PaymentStrategy {
    @Override
    public void pay(Order order, int tax) {
        System.out.println("[Cash] Customer paid " + order.totalWithTax(tax) + " EUR");
    }
}