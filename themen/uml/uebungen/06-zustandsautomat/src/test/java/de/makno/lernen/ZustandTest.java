package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ZustandTest {

    @Test
    void istEinZustandsdiagramm() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.syntaxOk(), "PlantUML meldet: " + puml.typ());
        assertEquals("StateDiagram", puml.typ());
    }

    @Test
    void startUndEnde() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatZeile("^.[*]. --> Offen"), "[*] --> Offen");
        assertTrue(puml.hatZeile("^Geschlossen --> .[*]."), "Geschlossen --> [*]");
    }

    @Test
    void uebergangMitBedingungUndAktion() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatZeile("^Offen --> InBearbeitung ?: ?zuweisen ?.Bearbeiter gesetzt. ?/ ?benachrichtigeBearbeiter"),
                "Offen --> InBearbeitung : zuweisen [Bearbeiter gesetzt] / benachrichtigeBearbeiter");
    }

    @Test
    void uebrigeUebergaenge() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatZeile("^InBearbeitung --> Geloest ?: ?loesen"), "InBearbeitung --> Geloest : loesen");
        assertTrue(puml.hatZeile("^Geloest --> Geschlossen ?: ?schliessen ?/ ?archiviere"), "Geloest --> Geschlossen : schliessen / archiviere");
        assertTrue(puml.hatZeile("^Geloest --> Offen ?: ?wiedereroeffnen"), "Geloest --> Offen : wiedereroeffnen");
    }

    @Test
    void interneAktivitaet() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatZeile("^InBearbeitung ?: ?do ?/ ?bearbeite"), "InBearbeitung : do / bearbeite");
    }
}
