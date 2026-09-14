package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Prüft die FORM des Codes, nicht sein Verhalten: kurze Methode, benannte Konstanten,
 * ausgelagerte Schritte. Rot, bis das Refactoring durch ist.
 */
class RechnungStrukturTest {

    private static final Path QUELLE = Path.of("src", "main", "java", "de", "makno", "lernen", "Rechnung.java");
    private static final int MAX_ZEILEN_BERECHNE = 15;

    @Test
    void berechneIstKurz() throws Exception {
        List<String> rumpf = rumpfVon("berechne(");
        assertTrue(rumpf.size() <= MAX_ZEILEN_BERECHNE,
                "berechne hat " + rumpf.size() + " Zeilen, erlaubt sind " + MAX_ZEILEN_BERECHNE + " – Schritte in Methoden auslagern");
    }

    @Test
    void keineMagicNumbersInMethoden() throws Exception {
        for (String zeile : Files.readAllLines(QUELLE, StandardCharsets.UTF_8)) {
            if (zeile.contains("static final") || zeile.contains("Math.round")) {
                continue;
            }
            for (String zahl : new String[] {"0.19", "0.07", "0.10", "0.20", "0.9", "0.05", "0.2", "9.99", "4.99", "100", "50", "10"}) {
                assertFalse(zeile.matches(".*[^A-Za-z_0-9.]" + zahl.replace(".", "[.]") + "[^0-9.].*"),
                        "Magic Number " + zahl + " in: " + zeile.strip() + " – als static final mit Namen deklarieren");
            }
        }
    }

    @Test
    void benannteKonstantenUndHilfsmethoden() throws Exception {
        List<String> zeilen = Files.readAllLines(QUELLE, StandardCharsets.UTF_8);
        long konstanten = zeilen.stream().filter(z -> z.contains("static final")).count();
        long hilfsmethoden = zeilen.stream().filter(z -> z.strip().startsWith("private ") && z.contains("(") && z.contains(")")).count();
        assertTrue(konstanten >= 6, "mindestens sechs benannte Konstanten, gefunden: " + konstanten);
        assertTrue(hilfsmethoden >= 3, "mindestens drei private Hilfsmethoden (Steuer, Rabatt, Versand …), gefunden: " + hilfsmethoden);
    }

    /** Zeilen zwischen der Signatur, die {@code kopf} enthält, und der schließenden Klammer auf Methodenebene. */
    private static List<String> rumpfVon(String kopf) throws Exception {
        List<String> zeilen = Files.readAllLines(QUELLE, StandardCharsets.UTF_8);
        int start = -1;
        for (int i = 0; i < zeilen.size(); i++) {
            if (zeilen.get(i).contains(kopf) && zeilen.get(i).contains("public")) {
                start = i;
                break;
            }
        }
        assertTrue(start >= 0, "Methode " + kopf + " nicht gefunden");
        int ende = start + 1;
        while (ende < zeilen.size() && !zeilen.get(ende).equals("    }")) {
            ende++;
        }
        return zeilen.subList(start + 1, ende).stream().filter(z -> !z.isBlank()).toList();
    }
}
