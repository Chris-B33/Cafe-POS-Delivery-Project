package org.yourcompany.yourproject;

public interface Priced {
    Money price();
}
// Make SimpleProduct implement Priced (price() == basePrice())
// Make all decorators implement Priced (price() == basePrice() + surcharges)