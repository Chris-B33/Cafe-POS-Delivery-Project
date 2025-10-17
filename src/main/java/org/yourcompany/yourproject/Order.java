package org.yourcompany.yourproject;

import java.util.ArrayList;
import java.util.List;

public final class Order {
    private final long id;
    private final List<LineItem> items = new ArrayList<>();
    private final List<OrderObserver> observers = new ArrayList<>();

    public Order(long id) { this.id = id; }

    public long id() { return id; }
    public List<LineItem> items() { return items; }

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

    public void addItem(LineItem li) {
        if (li.quantity() <= 0.0) throw new IllegalArgumentException("neagtive or zero quantity");
        items.add(li);
        notifyObservers("itemAdded");
    }
    
    public void pay(PaymentStrategy strategy, int tax) {
        if (strategy == null) throw new IllegalArgumentException("strategy required");
        strategy.pay(this, tax);
        notifyObservers("paid");
    }
    
    public void register(OrderObserver o) {
        if (o == null) throw new IllegalArgumentException("observer required");
        if (observers.contains(o)) throw new IllegalArgumentException("observer already registered");
        observers.add(o);
    }
    
    public void unregister(OrderObserver o) {
        if (!observers.contains(o)) throw new IllegalArgumentException("observer not in registry");
        observers.remove(o);
    }

    public void notifyObservers(String eventType) {
        for (OrderObserver o : observers) {
            o.updated(this, eventType);
        }
    }

    public void markReady() {
        notifyObservers("ready");
    }
}