package org.yourcompany.yourproject;

public final class WalletPayment implements PaymentStrategy {
    private final String walletId;
    public WalletPayment(String walletId) { this.walletId = walletId; }

    @Override
    public void pay(Order order, int tax) {
        System.out.println("[Card] Customer paid " + order.totalWithTax(tax) + " EUR via wallet " + this.walletId);
    }
}