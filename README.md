# Design Notes
### 1: Removed Smells
- Line 4: Global tax percent is risky and primitive.
- Line 5: Last discount code means nothing.
- Line 12: LineItem handles that logic already.
- Line 24: Hard-coded values makes introduction of new logic difficult
- Line 47: PaymentStrategy was required to be changed to work as intended in the OrderManagerGod.
- Line 62: Function that can be abstracted and handled elsewhere

### 2: Refactorings
- Extracted to DiscountPolicy
    - To be able to use Polymorphism to implemnt extra discounts easily.
- Extracted to TaxPolicy
    - To be able to use Polymorphism to implemnt extra taxes easily.
- Extracted to ReceiptPrinter
    - Makes code more modular and extensible
- Replaced payment type string switch
    - To handle each payment strategies requirements independently

### 3: SOLID Principles
- Each class we abstracted the OrderManagerGod to create are examples of Single Responsibility. They all handle one thing and one thing only.
- We implement base classes such as PaymentStrategy that are extensible shown through various strategies like wallet and card. This is an example of Open-Closed.
- Using inheritance means the base classes and the derived classes share the same methods. Such as Product and SimpleProduct. This is Liskov Substitution.
- Each payment type implements only the pay() method, which is all the CLI requires. CLI does not need to know about credit limits, card expiry, or wallet balance checking inside each strategy—those are internal to each class.
- PricingService depends on abstractions (DiscountPolicy and TaxPolicy), not concrete implementations. You can swap LoyaltyPercentDiscount or FixedRateTaxPolicy with other implementations without changing PricingService. This is is Dependency Inversion in practice.

### 4: Implemeting a new Discount
- Since we use polymorphism, implementing a new Discount would just be creating a new class that implements DiscountPolicy.