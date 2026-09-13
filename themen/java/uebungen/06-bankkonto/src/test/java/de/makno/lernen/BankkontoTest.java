package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class BankkontoTest {

    @Test
    void neuesKontoHatInhaberUndNullEuro() {
        Bankkonto konto = new Bankkonto("Anna");
        assertEquals("Anna", konto.inhaber());
        assertEquals(0.0, konto.kontostand());
    }

    @Test
    void einzahlenErhoehtDenKontostand() {
        Bankkonto konto = new Bankkonto("Anna");
        konto.einzahlen(100.0);
        konto.einzahlen(50.0);
        assertEquals(150.0, konto.kontostand());
    }

    @Test
    void negativeEinzahlungWirdAbgelehnt() {
        Bankkonto konto = new Bankkonto("Anna");
        assertThrows(IllegalArgumentException.class, () -> konto.einzahlen(-50.0));
        assertEquals(0.0, konto.kontostand());
    }

    @Test
    void abhebenNurBisZumKontostand() {
        Bankkonto konto = new Bankkonto("Anna");
        konto.einzahlen(100.0);
        konto.abheben(30.0);
        assertEquals(70.0, konto.kontostand());
        assertThrows(IllegalStateException.class, () -> konto.abheben(100.0));
        assertEquals(70.0, konto.kontostand());
    }
}
