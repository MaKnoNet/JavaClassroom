package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Die Versandart ist eine Strategie: Der Kontext Versandkosten kennt nur das Interface. */
class VersandkostenTest {

    private static final double GENAU = 0.001;

    @Test
    void versandStrategieIstEinInterface() {
        assertTrue(VersandStrategie.class.isInterface());
    }

    @Test
    void standardMitZuschlagAbFuenfKilo() {
        assertEquals(4.99, new Versandkosten(new StandardVersand()).berechne(5), GENAU);
        assertEquals(6.99, new Versandkosten(new StandardVersand()).berechne(7), GENAU);
    }

    @Test
    void expressUndAbholung() {
        assertEquals(14.99, new Versandkosten(new ExpressVersand()).berechne(7), GENAU);
        assertEquals(0.0, new Versandkosten(new Abholung()).berechne(20), GENAU);
    }

    /** Open/Closed: Eine Versandart, die der Startcode nicht kennt – ohne eine Zeile in Versandkosten. */
    @Test
    void neueVersandartOhneAenderungAmKontext() {
        VersandStrategie drohne = gewichtKg -> gewichtKg <= 2 ? 14.99 : Double.POSITIVE_INFINITY;

        assertEquals(14.99, new Versandkosten(drohne).berechne(1.5), GENAU);
        assertEquals(Double.POSITIVE_INFINITY, new Versandkosten(drohne).berechne(3));
    }
}
