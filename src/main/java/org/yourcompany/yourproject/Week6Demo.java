package org.yourcompany.yourproject;

import java.util.Scanner;

public final class Week6Demo {
    final private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CLI();
        //example();
    }

    public static void CLI() {
        int taxPercent = 10;
        DiscountPolicy loyaltyDiscount = new LoyaltyPercentDiscount(5);
        TaxPolicy taxPolicy = new FixedRateTaxPolicy(taxPercent);
        PricingService pricing = new PricingService(loyaltyDiscount, taxPolicy);
        ReceiptPrinter printer = new ReceiptPrinter();
        CheckoutService checkout = new CheckoutService(new ProductFactory(), pricing, printer, taxPercent);

        String fullReceipt = "";
        Order order = new Order(OrderIds.next());

        boolean addingItems = true;
        while (addingItems) {
            System.out.println("\nSelect a base product:");
            System.out.println("1. Espresso ($2.50)");
            System.out.println("2. Latte ($3.20)");
            System.out.println("3. Cappuccino ($3.00)");
            System.out.print("Choice: ");
            int baseChoice = scanner.nextInt();
            scanner.nextLine();

            String recipe;
            switch (baseChoice) {
                case 1 -> recipe = "ESP";
                case 2 -> recipe = "LAT";
                case 3 -> recipe = "CAP";
                default -> {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }
            }

            String[][] extras = {
                {"Extra Shot", "SHOT"},
                {"Oat Milk", "OAT"},
                {"Syrup", "SYP"},
                {"Make it Large", "L"}
            };
            for (String[] extra : extras) {
                System.out.printf("Add %s? (y/n): ", extra[0]);
                String choice = scanner.nextLine().trim().toLowerCase();
                if (choice.equals("y")) recipe += "+" + extra[1];
            }

            System.out.print("Quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine();
            if (qty <= 0) qty = 1;

            Product product = new ProductFactory().create(recipe);
            order.addItem(new LineItem(product, qty));
            fullReceipt += checkout.checkout(recipe, qty);

            System.out.print("Add another product? (y/n): ");
            addingItems = scanner.nextLine().trim().equalsIgnoreCase("y");
        }

        System.out.print("Payment type (CASH, CARD, WALLET): ");
        String paymentType = scanner.nextLine().trim().toUpperCase();

        PaymentStrategy paymentStrategy;
        switch (paymentType) {
            case "CASH" -> paymentStrategy = new CashPayment();
            case "CARD" -> {
                System.out.print("Enter card number: ");
                String cardNumber = scanner.nextLine().trim();
                paymentStrategy = new CardPayment(cardNumber);
            }
            case "WALLET" -> {
                System.out.print("Enter wallet ID: ");
                String walletId = scanner.nextLine().trim();
                paymentStrategy = new WalletPayment(walletId);
            }
            default -> {
                System.out.println("Unknown payment type. Defaulting to cash.");
                paymentStrategy = new CashPayment();
            }
        }

        paymentStrategy.pay(order, taxPercent);

        System.out.print("Do you want a receipt? (y/n): ");
        boolean printReceipt =  scanner.nextLine().trim().equalsIgnoreCase("y");
        if (printReceipt) { 
            System.out.printf("Complete Receipt: %s\n\n", fullReceipt);
        }
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
