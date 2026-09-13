package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.xml.sax.SAXException;

class KundenXmlTest {

    private final KundenXml xml = new KundenXml();

    @Test
    void gewoehnlichesXmlWirdGelesen() throws Exception {
        assertEquals("Anna Müller", xml.liesName("<kunde><name>Anna Müller</name></kunde>"));
    }

    @Test
    void externeEntityDarfKeineDateiVomServerLesen(@TempDir Path ordner) throws Exception {
        Path geheim = ordner.resolve("geheim.txt");
        Files.writeString(geheim, "DB_PASSWORD=streng-geheim", StandardCharsets.UTF_8);
        String angriff = """
                <?xml version="1.0"?>
                <!DOCTYPE kunde [<!ENTITY datei SYSTEM "%s">]>
                <kunde><name>&datei;</name></kunde>
                """.formatted(geheim.toUri());

        String ergebnis;
        try {
            ergebnis = xml.liesName(angriff);
        } catch (SAXException abgewiesen) {
            return; // Parser hat das DOCTYPE oder die Entity verweigert – genau richtig.
        }
        assertFalse(ergebnis.contains("streng-geheim"), "Der Parser hat die Serverdatei ausgeliefert: " + ergebnis);
    }

    @Test
    void doctypeWirdGrundsaetzlichAbgewiesen() {
        String mitDoctype = "<!DOCTYPE kunde [<!ENTITY x \"harmlos\">]><kunde><name>&x;</name></kunde>";

        assertThrows(SAXException.class, () -> xml.liesName(mitDoctype),
                "Auch harmlose DOCTYPEs abweisen – ein Parser, der sie zulässt, lässt auch gefährliche zu");
    }
}
