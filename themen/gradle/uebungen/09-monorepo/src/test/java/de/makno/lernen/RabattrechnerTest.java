package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RabattrechnerTest {

    private final Rabattrechner rechner = new Rabattrechner();

    @Test
    void zwanzigProzentVonHundert() {
        assertEquals(80.0, rechner.preisNachRabatt(100.0, 20), 0.001);
    }

    @Test
    void mehrAlsHundertProzentIstUnzulaessig() {
        assertThrows(IllegalArgumentException.class, () -> rechner.preisNachRabatt(100.0, 101));
    }
}
