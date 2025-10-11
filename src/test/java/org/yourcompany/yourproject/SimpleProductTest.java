package org.yourcompany.yourproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class SimpleProductTest {

    @Test
    void testConstructorValidation() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new SimpleProduct("P1", "Negative Price", Money.of(-1))
        );
        assertEquals("negative balance would occur", ex.getMessage());
    }

    @Test
    void testGetters() {
        SimpleProduct p = new SimpleProduct("P1", "Test Product", Money.of(5));
        assertEquals("P1", p.id());
        assertEquals("Test Product", p.name());
        assertEquals(Money.of(5), p.basePrice());
    }
}
