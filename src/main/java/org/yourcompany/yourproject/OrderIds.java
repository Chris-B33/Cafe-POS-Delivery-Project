package org.yourcompany.yourproject;

import java.util.concurrent.atomic.AtomicLong;

public final class OrderIds {
    private static final AtomicLong counter = new AtomicLong(0);

    private OrderIds() {} // prevent instantiation

    public static long next() {
        return counter.incrementAndGet();
    }
}