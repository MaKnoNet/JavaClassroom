# Übung 10: Lokale Umgebung zähmen

Ein Kollege hat sein Projekt „zum Laufen gebracht" und die Einstellungen dafür eingecheckt.
Bei dir bricht der Build ab, bevor eine Zeile Java kompiliert ist:

```
Value 'C:/Users/kollege/.jdks/corretto-17.0.9' given for org.gradle.java.home Gradle property is invalid
```

Das ist die Übung: Erst herausfinden, **wo** Gradle diese Einstellung herhat, dann
entscheiden, was ins Repository gehört und was auf den einzelnen Rechner.

## Aufgabe

1. Lies `gradle.properties` im Projekt. Welche der Zeilen gelten auf deinem Rechner, welche
   auf dem des Kollegen, welche auf einem Linux-Server in der CI?
2. Räume `gradle.properties` auf: Es bleibt nur, was **überall** gilt. Wo die Java-Version
   herkommt, steht in `build.gradle` (Abschnitt `toolchain`) – prüfe, ob das reicht.
3. Lege die maschinenlokalen Einstellungen dort ab, wo Gradle sie ebenfalls liest, ohne
   dass sie im Repository landen: `~/.gradle/gradle.properties` (unter Windows
   `C:\Users\<du>\.gradle\gradle.properties`). Brauchst du auf deinem Rechner keine davon
   (kein Proxy, JDK im PATH), bleibt die Datei leer oder enthält nur Kommentare.
4. `./gradlew test` – alle drei Tests grün. Danach `./gradlew --version` lesen: Welches JDK
   startet Gradle, welches führt die Tests aus? (Beides darf verschieden sein – warum?)

## Abnahmekriterien

- `./gradlew test` läuft ohne Änderung an `build.gradle` durch.
- `gradle.properties` im Projekt enthält weder `org.gradle.java.home` noch Truststore- oder
  Proxy-Einstellungen noch einen absoluten Pfad eines Rechners.
- Die Tests laufen unter Java 21, festgelegt durch die Toolchain in `build.gradle`.
- Du kannst in zwei Sätzen sagen, in welcher Reihenfolge Gradle `gradle.properties`
  einliest und welche Datei gewinnt.

## Hinweise

- **Hinweis 1:** Gradle liest `gradle.properties` an mehreren Orten. Reihenfolge und wer
  gewinnt: Kommandozeile `-P` > `GRADLE_USER_HOME/gradle.properties` > Projektordner >
  Gradle-Installation. `./gradlew --info` zeigt am Anfang, welches Java-Home Gradle
  verwendet.
- **Hinweis 2:** `org.gradle.java.home` bestimmt, womit der **Gradle-Daemon** startet – die
  Toolchain bestimmt, womit **kompiliert und getestet** wird. Für den Daemon reicht jedes
  JDK 17+ aus dem PATH; die Toolchain holt sich das JDK 21 unabhängig davon. Deshalb kann
  die Zeile ersatzlos weg.
- **Hinweis 3:** Firmenproxy? Der Lehrplan-Abschnitt „Firmenproxy" in `AGENTS.md` zeigt die
  zwei Zeilen, die in `~/.gradle/gradle.properties` gehören – und warum sie **nur** dort
  richtig sind: `Windows-ROOT` gibt es auf einem Linux-Server nicht.
