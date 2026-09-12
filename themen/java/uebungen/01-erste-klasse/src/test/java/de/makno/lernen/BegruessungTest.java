package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BegruessungTest {

    @Test
    void begruesstMitNamen() {
        assertEquals("Hallo, Anna!", new Begruessung().begruesse("Anna"));
    }

    @Test
    void begruesstUnbekanntBeiLeeremOderFehlendemNamen() {
        Begruessung begruessung = new Begruessung();
        assertEquals("Hallo, Unbekannt!", begruessung.begruesse(""));
        assertEquals("Hallo, Unbekannt!", begruessung.begruesse(null));
    }
}
