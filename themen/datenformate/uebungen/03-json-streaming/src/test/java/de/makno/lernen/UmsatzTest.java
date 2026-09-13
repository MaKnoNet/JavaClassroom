package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Läuft mit -Xmx64m (siehe build.gradle). Die große Datei ist etwa so groß wie der Heap. */
class UmsatzTest {

    private static final int ANZAHL_BUCHUNGEN = 1_200_000;

    private static Path grosseDatei;
    private static Path kleineDatei;
    private static BigDecimal erwarteteSumme;

    @BeforeAll
    static void dateienErzeugen() throws IOException {
        grosseDatei = Files.createTempFile("buchungen-gross", ".json");
        erwarteteSumme = schreibeBuchungen(grosseDatei, ANZAHL_BUCHUNGEN);
        kleineDatei = Files.createTempFile("buchungen-klein", ".json");
        Files.writeString(kleineDatei, """
                {"kommentar": "Felder außerhalb der Buchungen sind zu ignorieren", "buchungen": [
                  {"id": 1, "kunde": "Anna", "betrag": 10.50, "details": {"betrag": 999}},
                  {"id": 2, "kunde": "Ben", "betrag": 0.25}
                ], "summeLautAbsender": 12345}
                """, StandardCharsets.UTF_8);
    }

    @AfterAll
    static void aufraeumen() throws IOException {
        Files.deleteIfExists(grosseDatei);
        Files.deleteIfExists(kleineDatei);
    }

    /** Schreibt selbst streamend – sonst käme schon der Test nicht durch den kleinen Heap. */
    private static BigDecimal schreibeBuchungen(Path datei, int anzahl) throws IOException {
        BigDecimal summe = BigDecimal.ZERO;
        try (OutputStream aus = Files.newOutputStream(datei);
                JsonGenerator g = new JsonFactory().createGenerator(aus)) {
            g.writeStartObject();
            g.writeArrayFieldStart("buchungen");
            for (int i = 1; i <= anzahl; i++) {
                BigDecimal betrag = BigDecimal.valueOf(i % 1000, 2);
                summe = summe.add(betrag);
                g.writeStartObject();
                g.writeNumberField("id", i);
                g.writeStringField("kunde", "Kunde Nummer " + i);
                g.writeNumberField("betrag", betrag);
                g.writeEndObject();
            }
            g.writeEndArray();
            g.writeEndObject();
        }
        return summe;
    }

    @Test
    void streamingSummiertDieGrosseDatei() throws IOException {
        assertEquals(erwarteteSumme, new Umsatz().summeStreaming(grosseDatei));
    }

    @Test
    void streamingIgnoriertFremdeFelderUndVerschachtelteBetraege() throws IOException {
        assertEquals(new BigDecimal("10.75"), new Umsatz().summeStreaming(kleineDatei));
    }

    @Test
    void domStirbtAnDerGrossenDatei() {
        long dateiGroesse = grosseDatei.toFile().length();
        long heap = Runtime.getRuntime().maxMemory();

        assertThrows(OutOfMemoryError.class, () -> new Umsatz().summeDom(grosseDatei),
                "Datei " + dateiGroesse / 1_000_000 + " MB, Heap " + heap / 1_000_000 + " MB – readTree müsste scheitern");
    }
}
