package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BenutzerVerwaltungTest {

    private static final String PASSWORT = "geheim123";

    private final BenutzerVerwaltung verwaltung = new BenutzerVerwaltung();

    @BeforeEach
    void annaRegistrieren() {
        verwaltung.registriere("anna", PASSWORT);
    }

    @Test
    void anmeldungMitRichtigemPasswort() {
        assertTrue(verwaltung.anmelden("anna", PASSWORT));
    }

    @Test
    void anmeldungMitFalschemPasswortScheitert() {
        assertFalse(verwaltung.anmelden("anna", "geheim124"));
        assertFalse(verwaltung.anmelden("unbekannt", PASSWORT));
    }

    @Test
    void gespeichertIstNichtDasPasswort() {
        String wert = verwaltung.gespeicherterWert("anna");

        assertNotEquals(PASSWORT, wert, "Klartext in der Datenbank");
        assertFalse(wert.contains(PASSWORT), "Das Passwort steckt lesbar im gespeicherten Wert: " + wert);
    }

    @Test
    void gleichesPasswortErgibtVerschiedeneWerte() {
        verwaltung.registriere("ben", PASSWORT);

        assertNotEquals(verwaltung.gespeicherterWert("anna"), verwaltung.gespeicherterWert("ben"),
                "Ohne Salt sieht ein Angreifer, wer dasselbe Passwort hat – und knackt alle auf einmal");
    }

    @Test
    void erneuteRegistrierungErgibtEinenAnderenWert() {
        String vorher = verwaltung.gespeicherterWert("anna");
        verwaltung.registriere("anna", PASSWORT);

        assertNotEquals(vorher, verwaltung.gespeicherterWert("anna"), "Salt muss zufällig sein, nicht vom Namen abgeleitet");
    }
}
