package org.yourcompany.yourproject;

import java.util.ArrayList;
import java.util.List;

public final class Order {
    private final long id;
    private final List<LineItem> items = new ArrayList<>();

    public Order(long id) { this.id = id; }

    public long id() { return id; }
    public List<LineItem> items() { return items; }

    public void addItem(LineItem li) {
        if (li.quantity() <= 0.0) throw new IllegalArgumentException("neagtive or zero quantity");
        items.add(li);
    }
    public Money subtotal() {
        return items.stream()
                    .map(LineItem::lineTotal)
                    .reduce(Money.zero(), Money::add);
    }
    public Money taxAtPercent(int percent) {
        return this.subtotal().multiply(percent).divide(100);
    }
    public Money totalWithTax(int percent) {
        return this.subtotal().add(this.taxAtPercent(percent));
    }
}