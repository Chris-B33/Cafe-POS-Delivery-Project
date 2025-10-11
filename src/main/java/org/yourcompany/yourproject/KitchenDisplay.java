package org.yourcompany.yourproject;

public final class KitchenDisplay implements OrderObserver {
    @Override
    public void updated(Order order, String eventType) {
        if (eventType.equals("itemAdded")) {
            System.out.println("[Kitchen] Order #" + order.id() + ": item added");
        }
        else if (eventType.equals("paid")) {
            System.out.println("[Kitchen] Order #" + order.id() + ": payment received");
        }
    }
}