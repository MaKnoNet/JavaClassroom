package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StapelTest {

    @Test
    void neuerStapelIstLeer() {
        assertTrue(new Stapel().istLeer());
    }

    @Test
    void nachLegenNichtLeerUndGroesseEins() {
        Stapel stapel = new Stapel();
        stapel.lege("a");
        assertFalse(stapel.istLeer());
        assertEquals(1, stapel.groesse());
    }

    @Test
    void nimmLiefertZuletztGelegtesElement() {
        Stapel stapel = new Stapel();
        stapel.lege("a");
        stapel.lege("b");
        assertEquals("b", stapel.nimm());
    }

    @Test
    void nimmAufLeeremStapelWirftException() {
        assertThrows(IllegalStateException.class, () -> new Stapel().nimm());
    }
}
