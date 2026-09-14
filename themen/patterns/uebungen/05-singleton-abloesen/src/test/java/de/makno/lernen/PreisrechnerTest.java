package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/** Der Preisrechner bekommt seine Konfiguration – er holt sie sich nicht. */
class PreisrechnerTest {

    @Test
    void neunzehnProzentInDeutschland() {
        Preisrechner rechner = new Preisrechner(new Konfiguration(19, "EUR"));

        assertEquals(119.0, rechner.brutto(100), 0.001);
        assertEquals("119.00 EUR", rechner.formatiert(100));
    }

    @Test
    void ermaessigterSatzUndAndereWaehrung() {
        Preisrechner rechner = new Preisrechner(new Konfiguration(7, "CHF"));

        assertEquals(107.0, rechner.brutto(100), 0.001);
        assertEquals("107.00 CHF", rechner.formatiert(100));
    }

    @Test
    void zweiRechnerMitVerschiedenenKonfigurationenNebeneinander() {
        Preisrechner de = new Preisrechner(new Konfiguration(19, "EUR"));
        Preisrechner ch = new Preisrechner(new Konfiguration(8, "CHF"));

        assertEquals(119.0, de.brutto(100), 0.001);
        assertEquals(108.0, ch.brutto(100), 0.001);
    }

    @Test
    void konfigurationIstKeinSingletonMehr() {
        boolean hatGetInstance = Arrays.stream(Konfiguration.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("getInstance"));
        boolean konstruktorOeffentlich = Arrays.stream(Konfiguration.class.getDeclaredConstructors())
                .anyMatch(c -> Modifier.isPublic(c.getModifiers()));
        boolean statischeInstanz = Arrays.stream(Konfiguration.class.getDeclaredFields())
                .anyMatch(f -> Modifier.isStatic(f.getModifiers()) && f.getType() == Konfiguration.class);

        assertFalse(hatGetInstance, "kein getInstance()");
        assertTrue(konstruktorOeffentlich, "öffentlicher Konstruktor mit den Werten");
        assertFalse(statischeInstanz, "kein statisches Feld mit der einen Instanz");
    }
}
