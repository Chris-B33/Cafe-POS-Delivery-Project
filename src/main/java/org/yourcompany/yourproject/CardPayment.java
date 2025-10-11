package org.yourcompany.yourproject;

public final class CardPayment implements PaymentStrategy {
    private final String cardNumber;
    public CardPayment(String cardNumber) { this.cardNumber = cardNumber; }

    @Override
    public void pay(Order order) {
        String maskedCard = "****" +  cardNumber.substring(cardNumber.length() - 4);
        System.out.println("[Card] Customer paid " + order.totalWithTax(10) + " EUR with card " + maskedCard);
    }
}