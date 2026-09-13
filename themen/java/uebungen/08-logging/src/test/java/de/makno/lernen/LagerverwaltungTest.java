package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

/**
 * Prüft nicht nur das Verhalten, sondern auch, WIE die Klasse darüber berichtet: über
 * einen Logger, nicht über System.out. Dafür hängt sich der Test mit einem ListAppender an
 * den Logger der Klasse und fängt System.out ab.
 */
class LagerverwaltungTest {

    private final Lagerverwaltung lager = new Lagerverwaltung();
    private final ListAppender<ILoggingEvent> logEintraege = new ListAppender<>();
    private final ByteArrayOutputStream konsole = new ByteArrayOutputStream();
    private final ByteArrayOutputStream fehlerKonsole = new ByteArrayOutputStream();
    private PrintStream echteKonsole;
    private PrintStream echteFehlerKonsole;

    /** Was Logback auf die Konsole schreibt, beginnt laut logback.xml mit Uhrzeit und Level. */
    private static final String LOGBACK_ZEILE = "[0-9]{2}:[0-9]{2}:[0-9]{2}[.][0-9]{3} (DEBUG|INFO|WARN|ERROR) .*";

    @BeforeEach
    void logUndKonsoleAbfangen() {
        Logger logger = (Logger) LoggerFactory.getLogger(Lagerverwaltung.class);
        logger.setLevel(Level.DEBUG);
        logEintraege.start();
        logger.addAppender(logEintraege);
        echteKonsole = System.out;
        echteFehlerKonsole = System.err;
        System.setOut(new PrintStream(konsole, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(fehlerKonsole, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void aufraeumen() {
        System.setOut(echteKonsole);
        System.setErr(echteFehlerKonsole);
        ((Logger) LoggerFactory.getLogger(Lagerverwaltung.class)).detachAppender(logEintraege);
    }

    @Test
    void entnahmeUnterBestandWarntImLog() {
        lager.lege("Schraube", 3);

        assertFalse(lager.entnimm("Schraube", 5));

        List<ILoggingEvent> warnungen = eintraegeAb(Level.WARN);
        assertEquals(1, warnungen.size(), "genau eine Warnung erwartet, war: " + logEintraege.list);
        String meldung = warnungen.get(0).getFormattedMessage();
        assertTrue(meldung.contains("Schraube") && meldung.contains("5"), "Meldung nennt Artikel und Menge: " + meldung);
    }

    @Test
    void normalerBetriebSchreibtNichtsAufSystemOutUndNichtsAbInfo() {
        lager.lege("Mutter", 10);
        assertTrue(lager.entnimm("Mutter", 4));

        for (String zeile : konsole.toString(StandardCharsets.UTF_8).split("\\R")) {
            assertTrue(zeile.isBlank() || zeile.matches(LOGBACK_ZEILE), "Konsole nur über den Logger, nicht per println: " + zeile);
        }
        assertTrue(eintraegeAb(Level.INFO).isEmpty(), "Alltag ist höchstens DEBUG, war: " + logEintraege.list);
        assertFalse(logEintraege.list.isEmpty(), "auf DEBUG darf (und soll) die Entnahme erscheinen");
    }

    @Test
    void fehlendeDateiWirdAlsErrorMitStacktraceGeloggtUndGemeldet() {
        Path fehlt = Path.of("gibt-es-nicht", "bestand.csv");

        assertThrows(UncheckedIOException.class, () -> lager.ladeBestand(fehlt));

        List<ILoggingEvent> fehler = eintraegeAb(Level.ERROR);
        assertEquals(1, fehler.size(), "genau ein ERROR erwartet, war: " + logEintraege.list);
        assertNotNull(fehler.get(0).getThrowableProxy(), "die Exception gehört als letztes Argument in den Log-Aufruf");
        assertTrue(fehler.get(0).getFormattedMessage().contains("bestand.csv"));
        assertEquals("", fehlerKonsole.toString(StandardCharsets.UTF_8), "kein printStackTrace auf System.err");
    }

    private List<ILoggingEvent> eintraegeAb(Level level) {
        return logEintraege.list.stream().filter(e -> e.getLevel().isGreaterOrEqual(level)).toList();
    }
}
