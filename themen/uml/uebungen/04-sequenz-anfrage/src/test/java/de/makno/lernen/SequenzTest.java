package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class SequenzTest {

    @Test
    void istEinSequenzdiagramm() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.syntaxOk(), "PlantUML meldet: " + puml.typ());
        assertEquals("SequenceDiagram", puml.typ());
    }

    @Test
    void teilnehmerInAufrufreihenfolge() throws Exception {
        List<String> zeilen = Puml.laden().zeilen();
        int client = index(zeilen, "Client"), controller = index(zeilen, "KundenController");
        int service = index(zeilen, "KundenService"), repo = index(zeilen, "KundenRepository");
        assertTrue(client >= 0 && controller >= 0 && service >= 0 && repo >= 0, "alle vier Teilnehmer");
        assertTrue(client < controller && controller < service && service < repo,
                "Reihenfolge von links nach rechts: Client, Controller, Service, Repository");
    }

    @Test
    void synchroneAufrufeDurchDieSchichten() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatZeile("^Client -> KundenController ?: ?GET /api/kunden/1"), "Client -> KundenController : GET /api/kunden/1");
        assertTrue(puml.hatZeile("^KundenController -> KundenService ?: ?finde[(]1[)]"), "KundenController -> KundenService : finde(1)");
        assertTrue(puml.hatZeile("^KundenService -> KundenRepository ?: ?findById[(]1[)]"), "KundenService -> KundenRepository : findById(1)");
    }

    @Test
    void antwortenGestricheltZurueck() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.hatZeile("^KundenRepository --> KundenService"), "Antwort Repository -> Service gestrichelt");
        assertTrue(puml.hatZeile("^KundenService --> KundenController"), "Antwort Service -> Controller gestrichelt");
        assertTrue(puml.hatZeile("^KundenController --> Client ?: ?200"), "Antwort an den Client mit 200");
    }

    @Test
    void aktivierungsbalken() throws Exception {
        Puml puml = Puml.laden();
        assertTrue(puml.anzahl("^activate ") >= 3, "mindestens drei activate");
        assertEquals(puml.anzahl("^activate "), puml.anzahl("^deactivate "), "jedes activate hat ein deactivate");
    }

    private static int index(List<String> zeilen, String teilnehmer) {
        for (int i = 0; i < zeilen.size(); i++) {
            if (zeilen.get(i).matches("(participant|actor|boundary|control|entity|database)[ ]+[\"]?" + teilnehmer + "[\"]?.*")) {
                return i;
            }
        }
        return -1;
    }
}
