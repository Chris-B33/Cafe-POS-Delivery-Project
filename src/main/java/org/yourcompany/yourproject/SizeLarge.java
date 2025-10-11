package org.yourcompany.yourproject;

public final class SizeLarge extends ProductDecorator {
    private static final Money SURCHARGE = Money.of(0.70);

    public SizeLarge(Product base) { super(base); }
    
    @Override 
    public String name() { return base.name() + " + (Large)"; }

    @Override
    public Money price() { return super.price().add(SURCHARGE); }
}