package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Sichtbarkeiten und Typen müssen exakt zur Java-Klasse passen. */
class KontoDiagrammTest {

    @Test
    void gueltigesKlassendiagrammMitKonto() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.syntaxOk(), "PlantUML meldet: " + puml.typ());
        assertTrue(puml.hatElement("class", "Konto"));
    }

    @Test
    void attributeMitSichtbarkeitUndTyp() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatZeile("^- inhaber: String$"), "- inhaber: String");
        assertTrue(puml.hatZeile("^- stand: double$"), "- stand: double");
        assertTrue(puml.hatZeile("^# buchungen: int$"), "# buchungen: int (protected)");
        assertTrue(puml.hatZeile("^[+] [{]static[}] MAX_STAND: double$") || puml.hatZeile("^[{]static[}] [+] MAX_STAND: double$"),
                "+ {static} MAX_STAND: double");
    }

    @Test
    void methodenMitParameternUndRueckgabetyp() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatZeile("^[+] Konto[(]inhaber: String[)]$"), "+ Konto(inhaber: String)");
        assertTrue(puml.hatZeile("^[+] stand[(][)]: double$"), "+ stand(): double");
        assertTrue(puml.hatZeile("^[+] einzahlen[(]betrag: double[)]: void$"), "+ einzahlen(betrag: double): void");
        assertTrue(puml.hatZeile("^- pruefe[(]betrag: double[)]: void$"), "- pruefe(betrag: double): void");
        assertTrue(puml.hatZeile("^~ inhaber[(][)]: String$"), "~ inhaber(): String (package-private)");
    }

    @Test
    void keineFalschenSichtbarkeiten() throws Exception {
        Puml puml = Puml.laden();
        assertFalse(puml.hatZeile("^[+#~] (inhaber|stand): "), "Felder sind privat");
        assertFalse(puml.hatZeile("^[-#~] einzahlen"), "einzahlen ist public");
        assertFalse(puml.hatZeile("^[+#~] pruefe"), "pruefe ist private");
    }
}
