package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Charakterisierungstests: Sie beschreiben, was der Code HEUTE tut – auch die Eigenheiten
 * (der Rabatt mindert das Netto, die Steuer wird trotzdem auf das volle Netto gerechnet).
 * Sie sind grün und müssen grün bleiben. Ein Umbau, der sie rot macht, hat Verhalten geändert.
 */
class RechnungVerhaltenTest {

    private static final double CENT = 0.001;
    private final Rechnung rechnung = new Rechnung();

    @Test
    void privatkundeDeutschlandBuchKleinerBestellwertMitVersand() {
        assertEquals(47.79, rechnung.berechne(List.of(new Position("BUCH", 20.00, 2)), "PRIVAT", false, "DE"), CENT);
    }

    @Test
    void stammkundeDeutschlandUeber100MitExpress() {
        assertEquals(180.99, rechnung.berechne(List.of(new Position("WERKZEUG", 30.00, 5)), "STAMM", true, "DE"), CENT);
    }

    @Test
    void mitarbeiterOesterreichMitMengenrabatt() {
        assertEquals(81.00, rechnung.berechne(List.of(new Position("BUCH", 10.00, 10)), "MITARBEITER", false, "AT"), CENT);
    }

    @Test
    void auslandOhneSteuer() {
        assertEquals(29.99, rechnung.berechne(List.of(new Position("SPIEL", 25.00, 1)), "PRIVAT", false, "CH"), CENT);
    }

    @Test
    void stammkundeGenau100BekommtKeinenRabatt() {
        assertEquals(119.00, rechnung.berechne(List.of(new Position("WERKZEUG", 50.00, 2)), "STAMM", false, "DE"), CENT);
    }

    @Test
    void gemischteBestellung() {
        List<Position> positionen = List.of(new Position("BUCH", 12.50, 4), new Position("WERKZEUG", 8.00, 12));
        assertEquals(149.50, rechnung.berechne(positionen, "STAMM", false, "DE"), CENT);
    }
}
