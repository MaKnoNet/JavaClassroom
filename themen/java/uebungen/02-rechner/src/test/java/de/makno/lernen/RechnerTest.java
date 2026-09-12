package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RechnerTest {

    private final Rechner rechner = new Rechner();

    @Test
    void addiertZweiZahlen() {
        assertEquals(5.0, rechner.addiere(2, 3));
    }

    @Test
    void subtrahiertZweiZahlen() {
        assertEquals(-1.0, rechner.subtrahiere(2, 3));
    }

    @Test
    void multipliziertZweiZahlen() {
        assertEquals(6.0, rechner.multipliziere(2, 3));
    }

    @Test
    void dividiertZweiZahlen() {
        assertEquals(2.5, rechner.dividiere(5, 2));
    }

    @Test
    void divisionDurchNullWirftException() {
        IllegalArgumentException fehler =
                assertThrows(IllegalArgumentException.class, () -> rechner.dividiere(1, 0));
        assertEquals("Division durch 0", fehler.getMessage());
    }
}
