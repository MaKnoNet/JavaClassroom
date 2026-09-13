package de.makno.lernen;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Ein kleines Lager: Artikel einlagern, entnehmen, Bestand aus einer Datei laden. */
public class Lagerverwaltung {

    // Ein Logger je Klasse, benannt nach der Klasse – so lässt er sich in logback.xml gezielt schalten.
    private static final Logger LOG = LoggerFactory.getLogger(Lagerverwaltung.class);

    private final Map<String, Integer> bestand = new HashMap<>();

    public void lege(String artikel, int menge) {
        // DEBUG: Ablauf für den Entwickler; im Betrieb abgeschaltet. Platzhalter statt "+" –
        // der Text wird nur zusammengebaut, wenn das Level aktiv ist.
        LOG.debug("lege {} x {}", menge, artikel);
        bestand.merge(artikel, menge, Integer::sum);
    }

    /** Entnimmt die Menge und meldet mit {@code false}, wenn der Bestand nicht reicht. */
    public boolean entnimm(String artikel, int menge) {
        LOG.debug("entnimm {} x {}", menge, artikel);
        int vorhanden = bestand(artikel);
        if (vorhanden < menge) {
            // WARN: fachlich ungewöhnlich, aber das Programm läuft weiter.
            LOG.warn("Nicht genug {}: {} da, {} gewünscht", artikel, vorhanden, menge);
            return false;
        }
        bestand.put(artikel, vorhanden - menge);
        return true;
    }

    public int bestand(String artikel) {
        return bestand.getOrDefault(artikel, 0);
    }

    /** Liest Zeilen der Form {@code artikel;menge} und legt sie ein. */
    public void ladeBestand(Path datei) {
        try {
            for (String zeile : Files.readAllLines(datei, StandardCharsets.UTF_8)) {
                String[] teile = zeile.split(";");
                lege(teile[0], Integer.parseInt(teile[1]));
            }
        } catch (IOException e) {
            // ERROR mit Exception als letztem Argument: Logback hängt den Stacktrace an.
            // Danach weiterwerfen – loggen ersetzt die Fehlerbehandlung nicht.
            LOG.error("Bestand aus {} konnte nicht geladen werden", datei, e);
            throw new UncheckedIOException("Bestand aus " + datei + " konnte nicht geladen werden", e);
        }
    }
}
