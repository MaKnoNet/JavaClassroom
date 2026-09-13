package de.makno.lernen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV-Import und -Export für Kunden. Format: Semikolon-getrennt, erste Zeile Kopfzeile
 * (ID;Name;Rolle), Texte mit Sonderzeichen in Anführungszeichen.
 *
 * Der Import „funktioniert" – bis der erste Name ein Komma, ein Semikolon oder
 * Anführungszeichen enthält. Der Export exportiert alles ungefiltert.
 */
public class KundenCsv {

    private static final String TRENNER = ";";

    public List<Kunde> lies(Path datei) throws IOException {
        List<Kunde> kunden = new ArrayList<>();
        List<String> zeilen = Files.readAllLines(datei, StandardCharsets.UTF_8);
        for (String zeile : zeilen.subList(1, zeilen.size())) {
            String[] felder = zeile.split(TRENNER);
            kunden.add(new Kunde(Integer.parseInt(felder[0]), felder[1], felder[2]));
        }
        return kunden;
    }

    public void schreibe(List<Kunde> kunden, Path datei) throws IOException {
        StringBuilder text = new StringBuilder("ID;Name;Rolle\n");
        for (Kunde kunde : kunden) {
            text.append(kunde.id()).append(TRENNER).append(kunde.name()).append(TRENNER).append(kunde.rolle()).append('\n');
        }
        Files.writeString(datei, text, StandardCharsets.UTF_8);
    }
}
