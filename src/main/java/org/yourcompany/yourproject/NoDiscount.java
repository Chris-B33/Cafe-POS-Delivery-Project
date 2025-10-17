package org.yourcompany.yourproject;

public final class NoDiscount implements DiscountPolicy {
    @Override 
    public Money discountOf(Money subtotal) { return Money.zero(); }
}