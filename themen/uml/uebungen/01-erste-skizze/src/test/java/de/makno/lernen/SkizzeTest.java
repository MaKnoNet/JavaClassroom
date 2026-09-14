package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SkizzeTest {

    @Test
    void istEinGueltigesKlassendiagramm() throws Exception {
        Puml puml = Puml.laden();

        assertTrue(puml.syntaxOk(), "PlantUML meldet: " + puml.typ());
        assertEquals("ClassDiagram", puml.typ());
    }

    @Test
    void dreiKlassenMitAttributen() throws Exception {
        Puml puml = Puml.laden();

        for (String klasse : new String[] {"Bibliothek", "Buch", "Mitglied"}) {
            assertTrue(puml.hatElement("class", klasse), "Klasse " + klasse + " fehlt");
        }
        assertTrue(puml.hatZeile("titel"), "Buch braucht ein Attribut titel");
        assertTrue(puml.hatZeile("isbn"), "Buch braucht ein Attribut isbn");
        assertTrue(puml.hatZeile("mitgliedsnummer"), "Mitglied braucht ein Attribut mitgliedsnummer");
    }

    @Test
    void bibliothekKenntBuecherUndMitglieder() throws Exception {
        Puml puml = Puml.laden();

        assertTrue(puml.hatBeziehungBeliebig("Bibliothek", "[-o*.]+>?", "<?[-o*.]+", "Buch"),
                "Beziehung zwischen Bibliothek und Buch fehlt");
        assertTrue(puml.hatBeziehungBeliebig("Bibliothek", "[-o*.]+>?", "<?[-o*.]+", "Mitglied"),
                "Beziehung zwischen Bibliothek und Mitglied fehlt");
    }
}
