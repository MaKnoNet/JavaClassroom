package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class LogikTest {

    @Test
    void istEinSequenzdiagramm() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.syntaxOk(), "PlantUML meldet: " + puml.typ());
        assertEquals("SequenceDiagram", puml.typ());
    }

    @Test
    void schleifeUeberDiePositionen() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatZeile("^loop .*position"), "loop mit einer Bedingung, die die Positionen nennt");
        assertTrue(puml.hatZeile("^BestellService -> Lager ?: ?istVerfuegbar"), "im loop: BestellService -> Lager : istVerfuegbar(...)");
    }

    @Test
    void fehlerfallAlsAlternativeMitException() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatZeile("^alt "), "alt-Rahmen mit Bedingung");
        assertTrue(puml.hatZeile("^else"), "else-Zweig");
        assertTrue(puml.hatZeile("^BestellService --> BestellController ?: ?.*BestellungUngueltigException"),
                "Exception als gestrichelte Antwort an den Controller");
    }

    @Test
    void erfolgsfallSpeichertUndAntwortet() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatZeile("^BestellService -> BestellRepository ?: ?speichere"), "speichere(bestellung) am Repository");
        assertTrue(puml.hatZeile("^BestellService --> BestellController ?: ?.*[Bb]estellnummer"), "Antwort mit der Bestellnummer");
    }

    @Test
    void rahmenSindGeschlossenUndDieSchleifeKommtVorDerEntscheidung() throws Exception {
        Puml puml = Puml.laden();
        List<String> z = puml.zeilen();
        long oeffnend = puml.anzahl("^(loop|alt|opt) ");
        assertEquals(oeffnend, puml.anzahl("^end$"), "jeder Rahmen endet mit end");
        int loop = erste(z, "loop"), alt = erste(z, "alt");
        assertTrue(loop >= 0 && alt >= 0 && loop < alt, "erst prüfen (loop), dann entscheiden (alt)");
    }

    private static int erste(List<String> z, String wort) {
        for (int i = 0; i < z.size(); i++) {
            if (z.get(i).toLowerCase().startsWith(wort + " ")) return i;
        }
        return -1;
    }
}
