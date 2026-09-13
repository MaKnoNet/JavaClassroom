Die Datei ersetzt `build.gradle` im Projektordner.

Warum so: Ein Scope beantwortet zwei Fragen – *wer* braucht die Bibliothek (Anwendung oder
nur Tests) und *wann* (beim Kompilieren, zur Laufzeit, beides).

| Scope | kompilieren | Laufzeit | typisch |
|---|---|---|---|
| `implementation` | ja | ja | alles, was der Code importiert und benutzt |
| `compileOnly` | ja | nein | Annotationen, APIs, die der Server bereitstellt |
| `runtimeOnly` | nein | ja | JDBC-Treiber, Logging-Backends |
| `testImplementation` | nur Tests | nur Tests | JUnit, Mockito |
| `testRuntimeOnly` | nein | nur Tests | Test-Engine, Launcher |

JUnit als `implementation` funktioniert – aber dann kann Anwendungscode `@Test`
importieren, und JUnit landet im ausgelieferten Programm. `compileOnly` für den Treiber
kompiliert, scheitert aber zur Laufzeit mit `No suitable driver found`, weil der Treiber
im `runtimeClasspath` fehlt. Die Annotationen sind der Spiegelfall: Der Compiler braucht
sie, im fertigen JAR wären sie Ballast.

Nachsehen statt raten: `./gradlew dependencies --configuration compileClasspath` und
`… runtimeClasspath` zeigen, was jeweils drin ist; `./gradlew dependencyInsight
--dependency h2 --configuration runtimeClasspath` erklärt, *warum* eine Bibliothek in
welcher Version dabei ist – bei transitiven Konflikten der erste Blick. Muss etwas raus,
das eine andere Bibliothek mitbringt: `implementation('x:y') { exclude group: 'a', module:
'b' }` – so hält die Vaadin-Übung die Pro-Komponenten draußen.
