package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Integrationstest: benutzt das echte Dateisystem – Namenskonvention *IT. */
class ProtokollIT {

    @Test
    void schreibtZweiZeilen(@TempDir Path ordner) throws IOException {
        Protokoll protokoll = new Protokoll(ordner.resolve("protokoll.txt"));
        protokoll.schreibe("eins");
        protokoll.schreibe("zwei");
        assertEquals(2, protokoll.anzahlZeilen());
    }
}
