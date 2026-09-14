package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Zustandsdiagramm:  NEU --bezahle--> BEZAHLT --versende--> VERSANDT --retourniere--> RETOURNIERT
 *                    NEU, BEZAHLT --storniere--> STORNIERT
 * Jeder Zustand ist eine Klasse; die Bestellung delegiert an den aktuellen.
 */
class BestellungTest {

    @Test
    void normalerWeg() {
        Bestellung b = new Bestellung();
        assertEquals("NEU", b.status());
        b.bezahle();
        assertEquals("BEZAHLT", b.status());
        b.versende();
        assertEquals("VERSANDT", b.status());
    }

    @Test
    void retourNurNachVersand() {
        Bestellung b = new Bestellung();
        b.bezahle();
        b.versende();

        b.retourniere();

        assertEquals("RETOURNIERT", b.status());
        assertThrows(IllegalStateException.class, () -> new Bestellung().retourniere());
    }

    @Test
    void stornoNurVorVersand() {
        Bestellung neu = new Bestellung();
        neu.storniere();
        assertEquals("STORNIERT", neu.status());

        Bestellung versandt = new Bestellung();
        versandt.bezahle();
        versandt.versende();
        assertThrows(IllegalStateException.class, versandt::storniere);
    }

    @Test
    void unerlaubteUebergaengeWerfen() {
        Bestellung b = new Bestellung();
        assertThrows(IllegalStateException.class, b::versende);
        b.bezahle();
        assertThrows(IllegalStateException.class, b::bezahle);
        b.storniere();
        assertThrows(IllegalStateException.class, b::bezahle);
        assertThrows(IllegalStateException.class, b::retourniere);
    }

    @Test
    void jederZustandIstEineKlasseDieDasZustandsInterfaceErfuellt() throws Exception {
        Class<?> zustand = Class.forName("de.makno.lernen.BestellZustand");
        assertTrue(zustand.isInterface(), "BestellZustand muss ein Interface sein");
        for (String name : new String[] {"Neu", "Bezahlt", "Versandt", "Storniert", "Retourniert"}) {
            Class<?> klasse = Class.forName("de.makno.lernen." + name);
            assertTrue(zustand.isAssignableFrom(klasse), name + " implementiert BestellZustand");
        }
    }
}
