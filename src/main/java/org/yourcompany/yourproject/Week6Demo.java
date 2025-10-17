package org.yourcompany.yourproject;

import java.util.Scanner;

public final class Week6Demo {
    public static void main(String[] args) {
        //CLI();
        example();
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
        // Old behavior
        String oldReceipt = OrderManagerGod.process("LAT+L", 2, "CARD", "LOYAL5", false);

        // New behavior with equivalent result
        var pricing = new PricingService(new LoyaltyPercentDiscount(5), new FixedRateTaxPolicy(10));
        var printer = new ReceiptPrinter();
        var checkout = new CheckoutService(new ProductFactory(), pricing, printer, 10);
        
        String newReceipt = checkout.checkout("LAT+L", 2);
        System.out.println("Old Receipt:\n" + oldReceipt);
        System.out.println("\nNew Receipt:\n" + newReceipt);
        System.out.println("\nMatch: " + oldReceipt.equals(newReceipt));
    }
}
