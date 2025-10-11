package org.yourcompany.yourproject;

public interface OrderObserver {
    void updated(Order order, String eventType);
}