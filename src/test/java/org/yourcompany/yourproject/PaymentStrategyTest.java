package org.yourcompany.yourproject;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PaymentStrategyTest {

    @Test
    void testCashPayment() {
        Order order = new Order(123);
        SimpleProduct p = new SimpleProduct("P1", "Product", Money.of(10));
        LineItem li = new LineItem(p, 3);
        order.addItem(li);

        PaymentStrategy cash = new CashPayment();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        cash.pay(order);

        String output = out.toString().trim();
        assertEquals(String.format("[Cash] Customer paid %s EUR", order.totalWithTax(10)), output);
    }

    @Test
    void testCardPayment() {
        Order order = new Order(456);
        SimpleProduct p = new SimpleProduct("P2", "Product", Money.of(20));
        LineItem li = new LineItem(p, 2);
        order.addItem(li);

        PaymentStrategy card = new CardPayment("123412341234");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        card.pay(order);

        String output = out.toString().trim();
        assertEquals(String.format("[Card] Customer paid %s EUR with card ****1234", order.totalWithTax(10)), output);
    }

    @Test
    void testWalletPayment() {
        Order order = new Order(789);
        SimpleProduct p = new SimpleProduct("P3", "Product", Money.of(30));
        LineItem li = new LineItem(p, 4);
        order.addItem(li);

        PaymentStrategy wallet = new WalletPayment("alex-wallet-3");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        wallet.pay(order);

        String output = out.toString().trim();
        assertEquals(String.format("[Card] Customer paid %s EUR via wallet alex-wallet-3", order.totalWithTax(10)), output);
    }
}