package org.yourcompany.yourproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class LineItemTest {

    @Test
    void testConstructorValidation() {
        SimpleProduct p = new SimpleProduct("P1", "Product", Money.of(5));

        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> new LineItem(null, 1));
        assertEquals("product required", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> new LineItem(p, 0));
        assertEquals("quantity must be > 0", ex2.getMessage());

        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> new LineItem(p, -3));
        assertEquals("quantity must be > 0", ex3.getMessage());
    }

    @Test
    void testLineTotal() {
        SimpleProduct p = new SimpleProduct("P1", "Product", Money.of(3));
        LineItem li = new LineItem(p, 4);
        assertEquals(Money.of(12), li.lineTotal());
    }
}
