package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RabattTest {

    private static final double TOLERANZ = 0.0001;

    @Test
    void keinRabattUnterDerSchwelleOhneStammkunde() {
        assertEquals(0.0, Rabatt.prozent(99.99, false), TOLERANZ);
    }

    @Test
    void mengenrabattGenauAbDerSchwelle() {
        assertEquals(0.10, Rabatt.prozent(100.0, false), TOLERANZ);
    }

    @Test
    void stammkundeUnterDerSchwelleBekommtNurStammrabatt() {
        assertEquals(0.05, Rabatt.prozent(50.0, true), TOLERANZ);
    }

    @Test
    void stammkundeMitGrossemBetragBekommtFuenfzehnProzent() {
        assertEquals(0.15, Rabatt.prozent(200.0, true), TOLERANZ);
    }

    @Test
    void endpreisZiehtDenRabattAb() {
        assertEquals(170.0, Rabatt.endpreis(200.0, true), TOLERANZ);
        assertEquals(50.0, Rabatt.endpreis(50.0, false), TOLERANZ);
    }
}
