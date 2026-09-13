package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Unit-Test: schnell, ohne Dateisystem. */
class RechnerTest {

    @Test
    void addiert() {
        assertEquals(5, new Rechner().addiere(2, 3));
    }

    @Test
    void multipliziert() {
        assertEquals(6, new Rechner().multipliziere(2, 3));
    }
}
