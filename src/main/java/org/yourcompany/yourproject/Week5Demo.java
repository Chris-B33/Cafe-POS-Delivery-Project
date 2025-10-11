package org.yourcompany.yourproject;

import java.util.Scanner;

public final class Week5Demo {
    public static void main(String[] args) {
        CLI();
        //example();
    }

    public static void CLI() {
        Scanner scanner = new Scanner(System.in);
        ProductFactory factory = new ProductFactory();
        Order order = new Order(OrderIds.next());
        boolean addingItems = true;

        while (addingItems) {
            // Base product selection
            System.out.println("\nSelect a base product:");
            System.out.println("1. Espresso ($2.50)");
            System.out.println("2. Latte ($3.20)");
            System.out.println("3. Cappuccino ($3.00)");
            System.out.print("Choice: ");
            int baseChoice = scanner.nextInt();
            scanner.nextLine();

            String baseCode;
            switch (baseChoice) {
                case 1 -> baseCode = "ESP";
                case 2 -> baseCode = "LAT";
                case 3 -> baseCode = "CAP";
                default -> {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }
            }

            // Extra options with prices
            String[][] extras = {
                {"Extra Shot", "SHOT", "0.80"},
                {"Oat Milk", "OAT", "0.50"},
                {"Syrup", "SYP", "0.40"},
                {"Make it Large", "L", "0.70"}
            };

            for (String[] extra : extras) {
                System.out.printf("Add %s? ($%s) (y/n): ", extra[0], extra[2]);
                String choice = scanner.nextLine().trim().toLowerCase();
                if (choice.equals("y")) baseCode += "+" + extra[1];
            }

            // Create product via factory
            Product product;
            try {
                product = factory.create(baseCode);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                continue;
            }

            // Quantity
            System.out.print("Quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine();

            order.addItem(new LineItem(product, qty));

            System.out.println("Add another item? (y/n)");
            String more = scanner.nextLine().trim().toLowerCase();
            addingItems = more.equals("y");
        }

        // Print order summary
        System.out.println("\nOrder #" + order.id());
        for (LineItem li : order.items()) {
            System.out.println(" - " + li.product().name() + " x" + li.quantity() +
                    " = " + li.lineTotal());
        }

        System.out.println("Subtotal: " + order.subtotal());
        System.out.println("Tax (10%): " + order.taxAtPercent(10));
        System.out.println("Total: " + order.totalWithTax(10));
    }

    public static void example() {
        ProductFactory factory = new ProductFactory();
        Product p1 = factory.create("ESP+SHOT+OAT"); // Espresso + Extra Shot + Oat
        Product p2 = factory.create("LAT+L"); // Large Latte

        Order order = new Order(OrderIds.next());
        order.addItem(new LineItem(p1, 1));
        order.addItem(new LineItem(p2, 2));

        System.out.println("Order #" + order.id());
        for (LineItem li : order.items()) {
            System.out.println(" - " + li.product().name() + " x"
            + li.quantity() + " = " + li.lineTotal());
        }

        System.out.println("Subtotal: " + order.subtotal());
        System.out.println("Tax (10%): " +
        order.taxAtPercent(10));
        System.out.println("Total: " + order.totalWithTax(10));
    }
}
