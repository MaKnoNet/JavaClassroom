package de.makno.lernen;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Summiert das Feld "betrag" aller Buchungen in einer JSON-Datei der Form
 * {"buchungen":[{"id":1,"kunde":"…","betrag":12.50}, …]}.
 */
public class Umsatz {

    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonFactory factory = mapper.getFactory();

    /** Bequem: die ganze Datei als Baum im Speicher. Funktioniert – bis die Datei groß wird. */
    public BigDecimal summeDom(Path datei) throws IOException {
        JsonNode wurzel = mapper.readTree(Files.newInputStream(datei));
        BigDecimal summe = BigDecimal.ZERO;
        for (JsonNode buchung : wurzel.get("buchungen")) {
            summe = summe.add(buchung.get("betrag").decimalValue());
        }
        return summe;
    }

    /**
     * Streaming: Token für Token lesen, nur die Summe im Speicher halten. Der Speicherbedarf
     * hängt nicht von der Dateigröße ab – 60 MB oder 60 GB, egal.
     */
    public BigDecimal summeStreaming(Path datei) throws IOException {
        BigDecimal summe = BigDecimal.ZERO;
        try (JsonParser parser = factory.createParser(Files.newInputStream(datei))) {
            // Bis zum Array "buchungen" auf der obersten Ebene vorspulen.
            while (parser.nextToken() != null) {
                if (parser.currentToken() == JsonToken.FIELD_NAME && "buchungen".equals(parser.currentName())
                        && parser.nextToken() == JsonToken.START_ARRAY) {
                    break;
                }
            }
            // Jede Buchung: ein Objekt. Innerhalb nur das Feld "betrag" auf Objekt-Ebene 1 zählen.
            while (parser.nextToken() == JsonToken.START_OBJECT) {
                summe = summe.add(betragDerBuchung(parser));
            }
        }
        return summe;
    }

    /** Liest ein Buchungsobjekt bis zu seinem END_OBJECT und liefert dessen "betrag". */
    private static BigDecimal betragDerBuchung(JsonParser parser) throws IOException {
        BigDecimal betrag = BigDecimal.ZERO;
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String feld = parser.currentName();
            parser.nextToken(); // zum Wert
            if ("betrag".equals(feld)) {
                betrag = parser.getDecimalValue();
            } else if (parser.currentToken().isStructStart()) {
                parser.skipChildren(); // verschachtelte Objekte/Arrays überspringen – auch wenn dort "betrag" steht
            }
        }
        return betrag;
    }
}
