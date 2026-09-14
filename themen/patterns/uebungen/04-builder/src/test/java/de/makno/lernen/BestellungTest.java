package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/** Bestellungen entstehen über einen Builder: lesbar, mit Standardwerten, geprüft in build(). */
class BestellungTest {

    @Test
    void lesbarStattSechsPositionsparameter() {
        Bestellung b = Bestellung.fuer("Anna")
                .lieferadresse("Weg 1, 10115 Berlin")
                .express()
                .rabatt(10)
                .build();

        assertEquals("Anna", b.kunde());
        assertEquals("Weg 1, 10115 Berlin", b.lieferadresse());
        assertTrue(b.express());
        assertEquals(10, b.rabattProzent());
        assertNull(b.gutscheincode());
    }

    @Test
    void rechnungsadresseIstStandardmaessigDieLieferadresse() {
        Bestellung b = Bestellung.fuer("Ben").lieferadresse("Gasse 2").build();

        assertEquals("Gasse 2", b.rechnungsadresse());
        assertFalse(b.express());
        assertEquals(0, b.rabattProzent());
    }

    @Test
    void ohneLieferadresseKeineBestellung() {
        assertThrows(IllegalStateException.class, () -> Bestellung.fuer("Clara").build());
    }

    @Test
    void rabattNurZwischenNullUndHundert() {
        assertThrows(IllegalArgumentException.class, () -> Bestellung.fuer("David").lieferadresse("x").rabatt(150));
    }

    @Test
    void bestellungIstUnveraenderlich() {
        boolean hatSetter = Arrays.stream(Bestellung.class.getMethods()).anyMatch(m -> m.getName().startsWith("set"));
        boolean konstruktorOeffentlich = Arrays.stream(Bestellung.class.getConstructors()).findAny().isPresent();

        assertFalse(hatSetter, "keine Setter – das Ergebnis des Builders ist unveränderlich");
        assertFalse(konstruktorOeffentlich, "kein öffentlicher Konstruktor – nur der Builder erzeugt Bestellungen");
    }
}
