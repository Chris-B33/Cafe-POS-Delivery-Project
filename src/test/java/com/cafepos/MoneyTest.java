package org.yourcompany.yourproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    void testAdd() {
        Money m1 = Money.of(10);
        Money m2 = Money.of(5.50);
        Money result = m1.add(m2);
        assertEquals(Money.of(15.50), result);
    }

    @Test
    void testMultiply() {
        Money m = Money.of(3.25);
        Money result = m.multiply(4);
        assertEquals(Money.of(13.00), result);
    }

    @Test
    void testDivide() {
        Money m = Money.of(10);
        Money result = m.divide(4);
        assertEquals(Money.of(2.50), result);
    }

    @Test
    void testNegativeBalanceThrows() {
        Money m = Money.of(5);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> m.add(Money.of(-10)));
        assertEquals("negative balance would occur", ex.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        Money m1 = Money.of(7.50);
        Money m2 = Money.of(7.50);
        Money m3 = Money.of(8.00);
        assertEquals(m1, m2);
        assertNotEquals(m1, m3);
        assertEquals(m1.hashCode(), m2.hashCode());
    }
}