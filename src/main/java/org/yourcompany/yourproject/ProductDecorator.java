package org.yourcompany.yourproject;

public abstract class ProductDecorator implements Product, Priced {
    protected final Product base;
    
    protected ProductDecorator(Product base) {
        if (base == null) throw new IllegalArgumentException("base product required");
        this.base = base;
    }

    @Override 
    public String id() { return base.id(); } // id may remain the base product id

    @Override 
    public Money basePrice() { 
        return base.basePrice(); 
    }

    @Override
    public Money price() {
        return (base instanceof Priced p) ? p.price() : base.basePrice();
    }
}