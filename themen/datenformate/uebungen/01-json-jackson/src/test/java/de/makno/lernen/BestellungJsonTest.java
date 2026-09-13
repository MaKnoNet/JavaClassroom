package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BestellungJsonTest {

    private final BestellungJson json = new BestellungJson();

    private final Bestellung bestellung = new Bestellung(4711, "Müller & Söhne", LocalDate.of(2026, 9, 13),
            List.of(new Bestellung.Position("Schraube", 100, new BigDecimal("0.05")),
                    new Bestellung.Position("Mutter", 100, new BigDecimal("0.04"))));

    @Test
    void hinUndZurueckBleibtGleich() throws Exception {
        assertEquals(bestellung, json.lies(json.schreibe(bestellung)));
    }

    @Test
    void datumIstLesbarerText_keinZeitstempel() throws Exception {
        String text = json.schreibe(bestellung);

        assertTrue(text.contains("\"datum\":\"2026-09-13\""), text);
        assertFalse(text.contains("[2026,9,13]"), "Datum darf kein Array und kein Zeitstempel sein");
    }

    @Test
    void unbekannteFelderAusDerApiStoerenNicht() throws Exception {
        String vonDraussen = """
                {"nummer": 1, "kunde": "Anna", "datum": "2026-01-02", "positionen": [],
                 "kommentar": "dieses Feld kennt unser Record nicht"}
                """;

        assertEquals("Anna", json.lies(vonDraussen).kunde());
    }

    @Test
    void dateiWirdAlsUtf8Geschrieben(@TempDir Path ordner) throws Exception {
        Path datei = ordner.resolve("bestellung.json");

        json.speichere(bestellung, datei);

        assertTrue(Files.readString(datei, StandardCharsets.UTF_8).contains("Müller & Söhne"));
        assertEquals(bestellung, json.lade(datei));
    }
}
