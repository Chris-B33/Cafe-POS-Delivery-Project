package org.yourcompany.yourproject;

public interface PaymentStrategy {
    void pay(Order order, int tax);
}