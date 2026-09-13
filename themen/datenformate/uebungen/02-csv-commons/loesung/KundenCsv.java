package de.makno.lernen;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 * CSV-Import und -Export für Kunden über Commons CSV: Semikolon-getrennt (europäisch –
 * das Komma ist das Dezimalzeichen), Kopfzeile ID;Name;Rolle, Anführungszeichen nach
 * RFC 4180.
 */
public class KundenCsv {

    private static final String[] KOPF = {"ID", "Name", "Rolle"};

    private static final CSVFormat BASIS = CSVFormat.DEFAULT.builder()
            .setDelimiter(';')
            .setHeader(KOPF)
            .setRecordSeparator("\n")
            .get();

    /** Beim Lesen die Kopfzeile überspringen … */
    private static final CSVFormat LESEN = BASIS.builder().setSkipHeaderRecord(true).get();

    /** … beim Schreiben ausgeben: Der Printer schreibt die Kopfzeile nur, wenn skipHeaderRecord false ist. */
    private static final CSVFormat SCHREIBEN = BASIS.builder().setSkipHeaderRecord(false).get();

    public List<Kunde> lies(Path datei) throws IOException {
        List<Kunde> kunden = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(datei, StandardCharsets.UTF_8);
                CSVParser parser = CSVParser.parse(reader, LESEN)) {
            for (CSVRecord zeile : parser) {
                kunden.add(new Kunde(Integer.parseInt(zeile.get("ID")), zeile.get("Name"), zeile.get("Rolle")));
            }
        }
        return kunden;
    }

    public void schreibe(List<Kunde> kunden, Path datei) throws IOException {
        try (Writer writer = Files.newBufferedWriter(datei, StandardCharsets.UTF_8);
                CSVPrinter printer = new CSVPrinter(writer, SCHREIBEN)) {
            for (Kunde kunde : kunden) {
                printer.printRecord(kunde.id(), entschaerft(kunde.name()), entschaerft(kunde.rolle()));
            }
        }
    }

    /**
     * Formula Injection: Beginnt ein Text mit =, +, -, @, Tab oder CR, würde Excel ihn als
     * Formel ausführen. Ein vorangestelltes Hochkomma macht ihn zum Text. Gehört an den
     * Export – dort entsteht die Datei, die jemand in Excel öffnet.
     */
    static String entschaerft(String text) {
        if (text.isEmpty()) {
            return text;
        }
        char erstes = text.charAt(0);
        boolean gefaehrlich = erstes == '=' || erstes == '+' || erstes == '-' || erstes == '@'
                || erstes == '\t' || erstes == '\r';
        return gefaehrlich ? "'" + text : text;
    }
}
