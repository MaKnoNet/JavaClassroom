package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Das Auto bekommt seinen Antrieb von außen – und der Antrieb ist ein Interface. */
class AutoTest {

    @Test
    void antriebIstEinInterface() {
        assertTrue(Antrieb.class.isInterface(), "Antrieb muss ein Interface sein, keine Klasse");
    }

    @Test
    void benzinAutoFaehrtMitBenzinmotor() {
        Auto auto = new Auto(new BenzinMotor());

        assertEquals("Benzinmotor läuft – Reichweite 600 km", auto.fahre());
    }

    @Test
    void elektroAutoOhneUnterklasse() {
        Auto auto = new Auto(new Elektromotor());

        assertEquals("Elektromotor summt – Reichweite 350 km", auto.fahre());
    }

    @Test
    void hybridKombiniertBeideAntriebeOhneNeueAutoKlasse() {
        Antrieb hybrid = new HybridAntrieb(new BenzinMotor(), new Elektromotor());
        Auto auto = new Auto(hybrid);

        assertEquals("Benzinmotor läuft + Elektromotor summt – Reichweite 950 km", auto.fahre());
    }
}
