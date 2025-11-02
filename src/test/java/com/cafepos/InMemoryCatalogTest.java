package org.yourcompany.yourproject;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class InMemoryCatalogTest {

    @Test
    void testAddAndFindById() {
        InMemoryCatalog catalog = new InMemoryCatalog();
        SimpleProduct p = new SimpleProduct("P1", "Product 1", Money.of(2.5));
        catalog.add(p);
        Optional<Product> found = catalog.findById("P1");
        assertTrue(found.isPresent());
        assertEquals(p, found.get());
    }

    @Test
    void testFindNonExistentProduct() {
        InMemoryCatalog catalog = new InMemoryCatalog();
        Optional<Product> found = catalog.findById("NON_EXISTENT");
        assertFalse(found.isPresent());
    }

    @Test
    void testAddNullProductThrows() {
        InMemoryCatalog catalog = new InMemoryCatalog();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> catalog.add(null));
        assertEquals("product required", ex.getMessage());
    }
}
