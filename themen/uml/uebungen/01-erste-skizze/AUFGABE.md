# Übung 01: Erste Skizze

## Aufgabe

`diagramm.puml` enthält ein Szenario als Kommentar und sonst nichts. Übersetze es in ein
Klassendiagramm: drei Klassen mit ihren Attributen, zwei Beziehungen von der Bibliothek zu
Büchern und Mitgliedern. Rendere es und sieh es dir an – das ist der Zweck dieser Übung:
die Werkzeugkette einmal komplett.

## Ausführen

Einmalig das PlantUML-Jar nach `arbeit/uml/` laden:

```bash
curl -O https://repo1.maven.org/maven2/net/sourceforge/plantuml/plantuml/1.2026.8/plantuml-1.2026.8.jar
```

Im Übungsordner rendern und prüfen:

```bash
java -jar ../plantuml-1.2026.8.jar -tsvg -Playout=smetana diagramm.puml
./gradlew test
```

`diagramm.svg` öffnet der Lehrer in der Browser-Ansicht (oder du im Browser).

## Abnahmekriterien

- `./gradlew test` ist grün (drei Tests): gültige Syntax, drei Klassen mit den
  Attributen `titel`, `isbn`, `mitgliedsnummer`, zwei Beziehungen.
- Das gerenderte SVG zeigt drei Kästen und zwei Linien.

## Hinweise

1. Gerüst: `class Buch { … }` mit einem Attribut je Zeile im Kasten. Sichtbarkeit kommt in
   Lektion 02 – hier reicht `titel: String`.
2. Eine Beziehung ist eine Zeile `Bibliothek --> Buch` (Pfeil = die Bibliothek kennt das
   Buch). Zwei davon.
3. Beim ersten `./gradlew test` lädt Gradle PlantUML als Testabhängigkeit (30 MB) – das
   dauert einmal, danach nicht mehr.
4. Syntaxfehler zeigt der erste Test mit PlantUMLs Meldung an; die Zeilennummer steht
   darin.
