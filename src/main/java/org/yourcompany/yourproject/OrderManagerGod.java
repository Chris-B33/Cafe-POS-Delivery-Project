package org.yourcompany.yourproject;

public class OrderManagerGod {
    public static int TAX_PERCENT = 10;             // Global tax percent is risky and primitive
    public static String LAST_DISCOUNT_CODE = null; // means nothing

    public static String process(String recipe, int qty, String paymentType, String discountCode, boolean printReceipt) {
        ProductFactory factory = new ProductFactory();
        Product product = factory.create(recipe);
        Money unitPrice;
        try {
            var priced = product instanceof Priced p ? p.price() : product.basePrice(); // LineItem handles this.
            unitPrice = priced;
        } catch (Exception e) {
            unitPrice = product.basePrice();
        }
        
        if (qty <= 0) {
            qty = 1;
        }

        Money subtotal = unitPrice.multiply(qty);

        // Hard-coded values makes introduction of new codes difficult
        Money discount = Money.zero();
        if (discountCode != null) {
            if (discountCode.equalsIgnoreCase("LOYAL5")) {
                discount = subtotal.multiply(5).divide(100);
            } else if (discountCode.equalsIgnoreCase("COUPON1")) {
                discount = Money.of(1.00);
            } else if (discountCode.equalsIgnoreCase("NONE")) {
                discount = Money.zero();
            } else {
                discount = Money.zero();
            }
            LAST_DISCOUNT_CODE = discountCode; // Means nothing and global
        }

        // Discounting logic should be seperated
        Money discounted = new Money(subtotal.value().subtract(discount.value()));
        if (discounted.value().signum() < 0) {
            discounted = Money.zero(); 
        }
        var tax = discounted.multiply(TAX_PERCENT).divide(100);
        var total = discounted.add(tax);

        // Whole if statement should be handled by PaymentStrategy and is aleady with the pay method.
        // However, there is no mention of imputting card number, wallet id or anything in process arguments.
        // So this won't work without changing arguments to the method. Which then invalidates the tests.
        if (paymentType != null) {
            if (paymentType.equalsIgnoreCase("CASH")) {
                System.out.println("[Cash] Customer paid " + total + "EUR");
            } else if (paymentType.equalsIgnoreCase("CARD")) {
                System.out.println("[Card] Customer paid " + total + "EUR with card ****1234");
            } else if (paymentType.equalsIgnoreCase("WALLET")) {
                System.out.println("[Wallet] Customer paid " + total + "EUR via wallet user-wallet-789");
            } else {
                System.out.println("[UnknownPayment] " + total);
            }
        }

        // Just another action that is performed by a long method -> should be seperated.
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order (").append(recipe).append(") x").append(qty).append("\n");
        receipt.append("Subtotal: ").append(subtotal).append("\n");
        if (discount.value().signum() > 0) {
            receipt.append("Discount: -").append(discount).append("\n");
        }
        receipt.append("Tax (").append(TAX_PERCENT).append("%): ").append(tax).append("\n");
        receipt.append("Total: ").append(total);
        String out = receipt.toString();
        if (printReceipt) {
            System.out.println(out);
        }
        return out;
    }
}