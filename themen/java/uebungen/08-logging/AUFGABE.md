# Übung 08: Fehlersuche – Debugger und Logging

## Aufgabe

`Lagerverwaltung` funktioniert, aber sie *redet* falsch: `System.out.println` für alles,
und wenn die Bestandsdatei fehlt, landet ein `printStackTrace()` auf der Konsole und das
Programm tut so, als wäre nichts gewesen.

**Schritt 0 – Debugger (ohne Test).** Setze in `entnimm` einen Breakpoint auf die Zeile
`int vorhanden = bestand(artikel);` und starte `entnahmeUnterBestandWarntImLog` im
Debug-Modus. Sieh dir `bestand` und `menge` an, geh mit *Step Over* bis zum `return`.
Wo genau entscheidet sich, dass `false` zurückkommt? Das ist die Frage, die der Debugger
beantwortet, ohne dass du eine Zeile `println` schreibst.

**Schritt 1 – Logging.** Ersetze jede Konsolenausgabe durch einen SLF4J-Logger mit dem
passenden Level. Beim Laden der Datei: den Fehler mit Stacktrace loggen *und* dem
Aufrufer melden (`UncheckedIOException`) – nicht verschlucken.

## Abnahmekriterien

- `./gradlew test` ist grün (drei Tests).
- Kein `System.out`, kein `printStackTrace` mehr in `Lagerverwaltung`.
- Die Log-Aufrufe nutzen `{}`-Platzhalter, keine String-Konkatenation.
- Der Logger ist `private static final` und nach der Klasse benannt.

## Hinweise

1. `import org.slf4j.Logger; import org.slf4j.LoggerFactory;` –
   `LoggerFactory.getLogger(Lagerverwaltung.class)`.
2. Level-Wahl: Ablauf für dich → `debug`; fachlich unerwartet, geht aber weiter → `warn`;
   kaputt → `error`. `info` braucht diese Klasse gar nicht.
3. `LOG.error("Text {}", wert, e)` – die Exception als **letztes** Argument ohne
   Platzhalter; SLF4J erkennt sie und Logback hängt den Stacktrace an. Der Test prüft
   genau das (`getThrowableProxy()`).
4. Debugger je IDE: Eclipse *Debug As → JUnit Test* (F6 Step Over, F5 Step Into, F8
   weiter); IntelliJ Käfer-Symbol neben dem Test (F8 / F7 / F9); VS Code *Debug Test* über
   der Methode (F10 / F11 / F5). Breakpoint: Klick in den Rand links neben der Zeile.
5. `logback.xml` steht auf `INFO` – deshalb siehst du die DEBUG-Zeilen im normalen Lauf
   nicht. Stell probeweise auf `DEBUG` und schau in die Testausgabe.
