# Übung 06: Der richtige Scope für jede Abhängigkeit

## Aufgabe

`Datenbank` spricht über JDBC mit H2 und nutzt `@NotNull` aus den JetBrains-Annotationen;
`DatenbankTest` prüft die Antwort. Der Build kompiliert, aber `./gradlew test` schlägt
fehl – und selbst wenn er liefe, wäre die `build.gradle` falsch: Alle drei Bibliotheken
stehen im falschen Scope. Repariere sie, ohne eine Zeile Java zu ändern:

1. JUnit darf nur die Tests sehen.
2. Der H2-Treiber wird nie importiert, muss aber zur Laufzeit da sein.
3. Die Annotationen braucht nur der Compiler.

## Abnahmekriterien

- `./gradlew test` ist grün.
- `./gradlew dependencies --configuration compileClasspath` listet **kein** `junit` und
  **kein** `h2`, aber `annotations`.
- `./gradlew dependencies --configuration runtimeClasspath` listet `h2`, aber **kein**
  `junit` und **keine** `annotations`.

## Hinweise

1. Lies die Fehlermeldung des Tests: `No suitable driver found for jdbc:h2:mem:lernen`
   heißt, der Treiber fehlt *zur Laufzeit* – beim Kompilieren war er da.
2. Vier Scopes decken fast alles ab: `implementation` (kompilieren und Laufzeit),
   `compileOnly` (nur kompilieren), `runtimeOnly` (nur Laufzeit), und jeweils mit
   `test`-Präfix dieselben nur für Tests.
3. `./gradlew dependencyInsight --dependency h2 --configuration runtimeClasspath` zeigt,
   ob und warum eine Bibliothek im Laufzeit-Klassenpfad ist – auch wenn sie eine andere
   Bibliothek nur mitbringt (transitiv). Mit `exclude` wirft man Mitgebrachtes raus, das
   Konflikte macht.
