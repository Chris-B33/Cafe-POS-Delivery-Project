package org.yourcompany.yourproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void testAddItem() {
        Order order = new Order(1);
        SimpleProduct p = new SimpleProduct("P1", "Product", Money.of(2));
        LineItem li = new LineItem(p, 3);
        order.addItem(li);
        assertEquals(1, order.items().size());
        assertEquals(li, order.items().get(0));
    }

    @Test
    void testSubtotalCalculation() {
        Order order = new Order(1);
        SimpleProduct p1 = new SimpleProduct("P1", "Product1", Money.of(2));
        SimpleProduct p2 = new SimpleProduct("P2", "Product2", Money.of(3));
        order.addItem(new LineItem(p1, 3)); // 6
        order.addItem(new LineItem(p2, 2)); // 6
        assertEquals(Money.of(12), order.subtotal());
    }

    @Test
    void testTaxCalculation() {
        Order order = new Order(1);
        SimpleProduct p = new SimpleProduct("P1", "Product", Money.of(10));
        order.addItem(new LineItem(p, 1));
        assertEquals(Money.of(2), order.taxAtPercent(20));
    }

    @Test
    void testTotalWithTax() {
        Order order = new Order(1);
        SimpleProduct p = new SimpleProduct("P1", "Product", Money.of(10));
        order.addItem(new LineItem(p, 1));
        assertEquals(Money.of(12), order.totalWithTax(20));
    }
}
