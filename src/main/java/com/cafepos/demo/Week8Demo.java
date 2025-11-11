package com.cafepos.demo;

import java.util.Scanner;

import com.cafepos.checkout.CheckoutService;
import com.cafepos.checkout.FixedRateTaxPolicy;
import com.cafepos.checkout.LoyaltyPercentDiscount;
import com.cafepos.checkout.PricingService;
import com.cafepos.checkout.ReceiptPrinter;
import com.cafepos.command.OrderService;
import com.cafepos.factory.ProductFactory;
import com.cafepos.order.Order;
import com.cafepos.order.OrderIds;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.CashPayment;
import com.cafepos.payment.PaymentStrategy;
import com.cafepos.payment.WalletPayment;
import com.cafepos.smells.OrderManagerGod;

public final class Week8Demo {
    final private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CLI();
        //example();
    }

    public static void CLI() {
        Order o = new Order(OrderIds.next());
        OrderService os = new OrderService(o);
        int taxPercent = 10;

        boolean ordering = true;
        while (ordering) {
            System.out.println("What do you want to do?:");
            System.out.println("1) Add a new item");
            System.out.println("2) Remove the most recent item");
            System.out.println("3) See your current total");
            System.out.println("4) Pay for your order.");
            System.out.print("Choice: ");

            int command;
            try {
                command = scanner.nextInt();
                switch (command) {
                    case 1 -> addNewItem(os);
                    case 2 -> os.removeLastItem();
                    case 3 -> System.out.println("Current Total: " + os.totalWithTax(taxPercent));
                    case 4 -> { payForOrder(o, taxPercent); continue; }
                    default -> System.out.println("Invalid command.");
                }
            } 
            catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }

        System.out.println("Thank you for dining with us!");
    }

    public static void addNewItem(OrderService os) {
        System.out.println("\nSelect a base product:");
        System.out.println("1) Espresso ($2.50)");
        System.out.println("2) Latte ($3.20)");
        System.out.println("3) Cappuccino ($3.00)");
        System.out.print("Choice: ");
        int baseChoice = scanner.nextInt();
        scanner.nextLine();

        String recipe;
        switch (baseChoice) {
            case 1 -> recipe = "ESP";
            case 2 -> recipe = "LAT";
            case 3 -> recipe = "CAP";
            default -> {
                throw new IllegalArgumentException("Invalid choice.");
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

        os.addItem(recipe, 1);
    }

    public static void payForOrder(Order o, int taxPercent) {
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
        paymentStrategy.pay(o, taxPercent);
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
