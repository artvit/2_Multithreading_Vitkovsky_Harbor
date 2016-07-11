package by.epam.port.action;

import java.util.concurrent.atomic.AtomicLong;

public class ShipIdGenerator {
    private static AtomicLong counter = new AtomicLong(0);

    public static long nextId() {
        return counter.getAndIncrement();
    }
}
