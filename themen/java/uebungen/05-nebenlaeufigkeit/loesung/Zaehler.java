package de.makno.lernen;

import java.util.concurrent.atomic.AtomicInteger;

/** Thread-sicherer Zähler: incrementAndGet ist atomar, keine Sperre nötig. */
public class Zaehler {

    private final AtomicInteger wert = new AtomicInteger();

    public void erhoehe() {
        wert.incrementAndGet();
    }

    public int wert() {
        return wert.get();
    }
}
