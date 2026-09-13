package de.makno.lernen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/** Ein kleines Lager: Artikel einlagern, entnehmen, Bestand aus einer Datei laden. */
public class Lagerverwaltung {

    private final Map<String, Integer> bestand = new HashMap<>();

    public void lege(String artikel, int menge) {
        System.out.println("lege " + menge + " x " + artikel);
        bestand.merge(artikel, menge, Integer::sum);
    }

    /** Entnimmt die Menge und meldet mit {@code false}, wenn der Bestand nicht reicht. */
    public boolean entnimm(String artikel, int menge) {
        System.out.println("entnimm " + menge + " x " + artikel);
        int vorhanden = bestand(artikel);
        if (vorhanden < menge) {
            System.out.println("Nicht genug " + artikel + ": " + vorhanden + " da, " + menge + " gewünscht");
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
            e.printStackTrace();
        }
    }
}
