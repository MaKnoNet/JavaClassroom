package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class KundenCsvTest {

    private final KundenCsv csv = new KundenCsv();

    @Test
    void liestNamenMitKommaSemikolonUndAnfuehrungszeichen(@TempDir Path ordner) throws Exception {
        Path datei = ordner.resolve("kunden.csv");
        Files.writeString(datei, """
                ID;Name;Rolle
                101;"Mustermann, Max";ADMIN
                102;"Meier; Anna";USER
                103;"Firma ""Blitz"" GmbH";USER
                """, StandardCharsets.UTF_8);

        List<Kunde> kunden = csv.lies(datei);

        assertEquals(List.of(
                new Kunde(101, "Mustermann, Max", "ADMIN"),
                new Kunde(102, "Meier; Anna", "USER"),
                new Kunde(103, "Firma \"Blitz\" GmbH", "USER")), kunden);
    }

    @Test
    void hinUndZurueckMitUmlauten(@TempDir Path ordner) throws Exception {
        Path datei = ordner.resolve("kunden.csv");
        List<Kunde> kunden = List.of(new Kunde(1, "Jürgen Öz, jr.", "USER"), new Kunde(2, "Straße 5", "ADMIN"));

        csv.schreibe(kunden, datei);

        assertEquals(kunden, csv.lies(datei));
        assertTrue(Files.readString(datei, StandardCharsets.UTF_8).startsWith("ID;Name;Rolle"));
    }

    @Test
    void entschaerftFormelnBeimExport(@TempDir Path ordner) throws Exception {
        Path datei = ordner.resolve("kunden.csv");
        List<Kunde> angriff = List.of(
                new Kunde(1, "=HYPERLINK(\"http://boese.example\";\"klick\")", "USER"),
                new Kunde(2, "+1+1", "USER"),
                new Kunde(3, "-2", "USER"),
                new Kunde(4, "@SUM(A1)", "USER"),
                new Kunde(5, "Harmlos", "USER"));

        csv.schreibe(angriff, datei);
        List<String> zeilen = Files.readAllLines(datei, StandardCharsets.UTF_8);

        for (String zeile : zeilen.subList(1, 5)) {
            // Das Namensfeld (zweite Spalte, ggf. in Anführungszeichen) beginnt mit dem Hochkomma.
            assertTrue(zeile.matches("[0-9];\"?'.*"), "Formel muss mit Hochkomma entschärft sein: " + zeile);
        }
        assertEquals("5;Harmlos;USER", zeilen.get(5));
    }
}
