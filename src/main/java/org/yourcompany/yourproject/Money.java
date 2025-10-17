package org.yourcompany.yourproject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money implements Comparable<Money> {
    private final BigDecimal amount;

    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public Money(BigDecimal a) {
        if (a == null) throw new IllegalArgumentException("amount required");
        this.amount = a.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal value() {
        return amount;
    }

    public Money add(Money other) {
        if (other == null) throw new IllegalArgumentException("other required");
        BigDecimal result = this.amount.add(other.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("negative balance would occur");
        return new Money(result);
    }

    public Money subtract(Money other) {
        if (other == null) throw new IllegalArgumentException("other required");
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("negative balance would occur");
        return new Money(result);
    }

    public Money multiply(int qty) {
        BigDecimal result = this.amount.multiply(BigDecimal.valueOf(qty));
        if (result.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("negative balance would occur");
        return new Money(result);
    }

    public Money divide(int divisor) {
        if (divisor == 0) throw new ArithmeticException("division by zero");
        BigDecimal result = this.amount.divide(BigDecimal.valueOf(divisor), 2, RoundingMode.HALF_UP);
        if (result.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("negative balance would occur");
        return new Money(result);
    }

    @Override
    public int compareTo(Money other) {
        return this.amount.compareTo(other.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.equals(money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return String.format("%.2f", amount);
    }
}