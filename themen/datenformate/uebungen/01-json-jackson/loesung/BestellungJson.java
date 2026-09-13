package de.makno.lernen;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Wandelt Bestellungen in JSON und zurück. Ein Mapper für die ganze Anwendung – teuer im Aufbau, thread-sicher. */
public class BestellungJson {

    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())                                   // LocalDate & Co.
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)           // "2026-09-13" statt [2026,9,13]
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)        // fremde Felder ignorieren
            .build();

    public String schreibe(Bestellung bestellung) throws IOException {
        return mapper.writeValueAsString(bestellung);
    }

    public Bestellung lies(String json) throws IOException {
        return mapper.readValue(json, Bestellung.class);
    }

    /** Schreibt die Bestellung als UTF-8-Datei – Encoding immer explizit, nie das des Betriebssystems. */
    public void speichere(Bestellung bestellung, Path datei) throws IOException {
        Files.writeString(datei, schreibe(bestellung), StandardCharsets.UTF_8);
    }

    /** Liest eine UTF-8-Datei. */
    public Bestellung lade(Path datei) throws IOException {
        return lies(Files.readString(datei, StandardCharsets.UTF_8));
    }
}
