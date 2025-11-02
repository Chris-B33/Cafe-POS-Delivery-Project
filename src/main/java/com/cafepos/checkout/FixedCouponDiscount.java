package com.cafepos.checkout;

import com.cafepos.common.Money;

public final class FixedCouponDiscount implements DiscountPolicy {
    private final Money amount;
    public FixedCouponDiscount(Money amount) { this.amount = amount; }

    @Override 
    public Money discountOf(Money subtotal) {
        if (amount.compareTo(subtotal) > 0) { return subtotal; }
        return amount;
    }
}