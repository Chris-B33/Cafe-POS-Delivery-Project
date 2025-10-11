package org.yourcompany.yourproject;

public final class Week2Demo {
    public static void main(String[] args) {
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P-ESP", "Espresso", Money.of(2.50)));
        catalog.add(new SimpleProduct("P-CCK", "Chocolate Cookie", Money.of(3.50)));
        catalog.add(new SimpleProduct("P-CBO", "Club Orange", Money.of(2.00)));

        Order order = new Order(OrderIds.next());
        order.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2));
        order.addItem(new LineItem(catalog.findById("P-CCK").orElseThrow(), 1));
        order.addItem(new LineItem(catalog.findById("P-CBO").orElseThrow(), 4));

        int taxPct = 10;
        System.out.println("Order #" + order.id());
        System.out.println("Unique Items: " + order.items().size());
        System.out.println("Total Items: " + order.items().stream().mapToInt(LineItem::quantity).sum());
        System.out.println("Subtotal: " + order.subtotal());
        System.out.println("Tax (" + taxPct + "%): " + order.taxAtPercent(taxPct));
        System.out.println("Total: " + order.totalWithTax(taxPct));
    }
}