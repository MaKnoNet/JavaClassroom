package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Ein einziger Test – grün, aber schwach. Der Mutationstest zeigt, was er nicht prüft. */
class RabattTest {

    @Test
    void stammkundeMitGrossemBetragBekommtFuenfzehnProzent() {
        assertEquals(0.15, Rabatt.prozent(200, true), 0.0001);
    }
}
