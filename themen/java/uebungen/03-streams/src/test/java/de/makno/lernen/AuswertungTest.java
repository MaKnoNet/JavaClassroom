package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AuswertungTest {

    private static final List<Mitarbeiter> TEAM = List.of(
            new Mitarbeiter("Clara", "Entwicklung", 4200),
            new Mitarbeiter("Anna", "Entwicklung", 3900),
            new Mitarbeiter("Ben", "Vertrieb", 3500),
            new Mitarbeiter("Dilan", "Vertrieb", 3700));

    private final Auswertung auswertung = new Auswertung();

    @Test
    void namenAlphabetischSortiert() {
        assertEquals(List.of("Anna", "Ben", "Clara", "Dilan"), auswertung.namenSortiert(TEAM));
    }

    @Test
    void namenEinerAbteilungMitKommaVerbunden() {
        assertEquals("Clara, Anna", auswertung.namenDerAbteilung(TEAM, "Entwicklung"));
    }

    @Test
    void namenEinerUnbekanntenAbteilungSindLeer() {
        assertEquals("", auswertung.namenDerAbteilung(TEAM, "Einkauf"));
    }

    @Test
    void gehaltssummeJeAbteilung() {
        Map<String, Double> summen = auswertung.gehaltssummeProAbteilung(TEAM);
        assertEquals(2, summen.size());
        assertEquals(8100.0, summen.get("Entwicklung"));
        assertEquals(7200.0, summen.get("Vertrieb"));
    }

    @Test
    void bestverdienerIstClara() {
        Optional<Mitarbeiter> bester = auswertung.bestverdiener(TEAM);
        assertTrue(bester.isPresent());
        assertEquals("Clara", bester.get().name());
    }

    @Test
    void bestverdienerBeiLeererListeIstLeer() {
        assertTrue(auswertung.bestverdiener(List.of()).isEmpty());
    }
}
