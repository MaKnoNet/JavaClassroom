package de.makno.lernen;

import com.fasterxml.jackson.core.JsonFactory;
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

    /** Streaming: Token für Token lesen, nur die Summe im Speicher halten. */
    public BigDecimal summeStreaming(Path datei) throws IOException {
        throw new UnsupportedOperationException("noch nicht umgesetzt");
    }
}
