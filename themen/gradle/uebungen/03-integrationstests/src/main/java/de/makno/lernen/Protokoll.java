package de.makno.lernen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/** Schreibt Zeilen in eine Datei – stellvertretend für eine echte Datenbank. */
public class Protokoll {

    private final Path datei;

    public Protokoll(Path datei) {
        this.datei = datei;
    }

    public void schreibe(String zeile) throws IOException {
        Files.writeString(datei, zeile + System.lineSeparator(), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public long anzahlZeilen() throws IOException {
        if (!Files.exists(datei)) {
            return 0;
        }
        try (var zeilen = Files.lines(datei, StandardCharsets.UTF_8)) {
            return zeilen.count();
        }
    }
}
