package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Der Build läuft auf jedem Rechner mit Java 21 – ohne dass eine Datei im Repo weiß, wo dieser Rechner sein JDK hat. */
class UmgebungTest {

    @Test
    void testsLaufenUnterJava21() {
        assertEquals(21, Umgebung.javaHauptversion(), "Toolchain: die Tests laufen mit " + Umgebung.javaVersion());
    }

    @Test
    void keineMaschinenlokalenEinstellungenImRepository() throws Exception {
        Path properties = Path.of("gradle.properties");
        List<String> zeilen = Files.exists(properties) ? Files.readAllLines(properties, StandardCharsets.UTF_8) : List.of();
        for (String zeile : zeilen) {
            String z = zeile.strip();
            if (z.startsWith("#") || z.isEmpty()) {
                continue;
            }
            assertFalse(z.startsWith("org.gradle.java.home"), "org.gradle.java.home gehört nach ~/.gradle/gradle.properties, nicht ins Repo: " + z);
            assertFalse(z.contains("trustStore"), "Truststore-Einstellungen sind maschinenlokal: " + z);
            assertFalse(z.contains("proxyHost") || z.contains("proxyPort"), "Proxy-Einstellungen sind maschinenlokal: " + z);
            assertFalse(z.contains(":/") || z.contains("/Users/") || z.contains("/home/"), "absoluter Pfad eines Rechners: " + z);
        }
    }

    @Test
    void toolchainIstImBuildFestgelegt() throws Exception {
        String build = Files.readString(Path.of("build.gradle"), StandardCharsets.UTF_8);
        assertTrue(build.contains("toolchain") && build.contains("JavaLanguageVersion.of(21)"),
                "build.gradle legt die Java-Version über die Toolchain fest – das ist der portable Weg");
    }
}
