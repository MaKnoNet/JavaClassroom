# Lösung 08: Logging

Vier Änderungen in `Lagerverwaltung`:

1. **Ein Logger je Klasse:** `private static final Logger LOG = LoggerFactory.getLogger(Lagerverwaltung.class);`
   – `static final`, weil er unveränderlich ist und für alle Instanzen gilt; benannt nach
   der Klasse, damit `logback.xml` ihn gezielt schalten kann.
2. **`System.out.println` → `LOG.debug("lege {} x {}", menge, artikel)`.** Platzhalter
   statt `+`: Der Text wird nur gebaut, wenn DEBUG aktiv ist. Mit `<root level="INFO">`
   in `logback.xml` verschwinden diese Zeilen im Betrieb – ohne Codeänderung.
3. **Fachliche Auffälligkeit → `LOG.warn(...)`.** Das Programm läuft weiter, aber jemand
   sollte es sehen.
4. **`e.printStackTrace()` → `LOG.error("…", datei, e)` und weiterwerfen.** Die Exception
   als *letztes* Argument – dann hängt Logback den Stacktrace an. Loggen ersetzt die
   Behandlung nicht: `throw new UncheckedIOException(…, e)`, damit der Aufrufer es merkt.

Die Level-Faustregel:

| Level | Für wen | Beispiel |
|---|---|---|
| DEBUG | Entwickler beim Suchen | „entnimm 4 x Mutter" |
| INFO | Betrieb, Meilensteine | „Bestand geladen: 120 Artikel" |
| WARN | Unerwartet, läuft weiter | „Nicht genug Schraube" |
| ERROR | Kaputt, jemand muss ran | „Bestand konnte nicht geladen werden" + Stacktrace |

Was nicht ins Log gehört: Passwörter, Tokens, vollständige Personendaten – Logs werden
kopiert, weitergeleitet und lange aufbewahrt.
