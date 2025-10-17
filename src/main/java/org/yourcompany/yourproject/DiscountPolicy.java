package org.yourcompany.yourproject;

public interface DiscountPolicy {
    Money discountOf(Money subtotal);
}