# Übung 05: Ein Task, der weiß, wann er nichts zu tun hat

## Aufgabe

`./gradlew bericht` zählt die Zeilen der Dateien in `daten/` und schreibt
`build/bericht.txt`. Ruf ihn zweimal hintereinander auf: Er rechnet beide Male – obwohl
sich nichts geändert hat. `compileJava` und `test` machen das nicht; die melden beim
zweiten Mal `UP-TO-DATE`. Bring dem Task bei, was die eingebauten können:

1. Deklariere `daten/` als Eingabe und `build/bericht.txt` als Ausgabe des Tasks.
2. Erlaube dem Task, sein Ergebnis im Build-Cache abzulegen, und schalte den Cache im
   Projekt ein (`gradle.properties`).

## Abnahmekriterien

- `./gradlew bericht` zweimal: Der zweite Lauf zeigt `> Task :bericht UP-TO-DATE` und
  gibt *nicht* „Bericht geschrieben" aus.
- Eine Zeile an `daten/obst.txt` anhängen, dann `./gradlew bericht`: Der Task läuft wieder,
  `build/bericht.txt` zeigt `obst.txt: 4 Zeilen`.
- `./gradlew clean bericht`: Der Task zeigt `FROM-CACHE` – das Ergebnis kommt aus dem
  Cache, ohne zu rechnen.
- `gradle.properties` im Projekt enthält nur `org.gradle.caching=true` (und Kommentare).

## Hinweise

1. Innerhalb von `tasks.register('bericht') { … }`: `inputs.dir(quelle)` und
   `outputs.file(ziel)` – die beiden Variablen gibt es schon. Gradle bildet daraus vor
   jedem Lauf Fingerabdrücke und vergleicht sie mit dem letzten Lauf.
2. `outputs.cacheIf { true }` erklärt das Ergebnis für cachebar. Ohne
   `org.gradle.caching=true` (Datei `gradle.properties` neben `build.gradle`) bleibt der
   Cache aus – dann gibt es `UP-TO-DATE`, aber nie `FROM-CACHE`.
3. Nützlich beim Nachschauen: `./gradlew bericht --info` erklärt, *warum* ein Task
   läuft („Task ':bericht' is not up-to-date because: Input property … has changed").
   Weitere Schalter für den Alltag: `--offline` (nichts herunterladen),
   `--refresh-dependencies` (Abhängigkeiten neu holen), `-q` (nur Ausgaben, keine
   Task-Liste), `--scan` (Build-Analyse im Browser).
4. Zwei `gradle.properties`, zwei Zwecke: Die im Projekt gilt für alle und wird
   versioniert; `~/.gradle/gradle.properties` gilt nur für deinen Rechner (Proxy,
   Truststore, Speicher) und bleibt draußen.
