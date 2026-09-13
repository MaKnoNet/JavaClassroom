package de.makno.lernen.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PreisrechnerTest {

    @Test
    void schlaegtMehrwertsteuerAuf() {
        assertEquals(119.0, new Preisrechner().brutto(100.0), 0.001);
    }
}
