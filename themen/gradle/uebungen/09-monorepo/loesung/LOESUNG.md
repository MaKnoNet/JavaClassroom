# Lösung 09

Der Ordner `loesung/` enthält den kompletten Zielbaum (ohne Wrapper – der bleibt, wo er
ist): `settings.gradle` und `build.gradle` ersetzen die im Projektordner, `buildSrc/`,
`kern/`, `konsole/` und `bericht/` kommen daneben, `src/` im Wurzelprojekt verschwindet.

```
monorepo/
├── settings.gradle                    include 'kern', 'konsole', 'bericht'
├── build.gradle                       leer – nur Kommentar
├── buildSrc/
│   ├── build.gradle                   id 'groovy-gradle-plugin'
│   └── src/main/groovy/de.makno.java-konventionen.gradle
├── kern/      build.gradle + src/     Rabattrechner, RabattrechnerTest
├── konsole/   build.gradle + src/     Konsole (main)          → :kern
└── bericht/   build.gradle + src/     Bericht, BerichtTest    → :kern
```

Warum so: `include` in `settings.gradle` macht aus Ordnern Teilprojekte; jedes hat eine
eigene `build.gradle`, die nur sagt, was an *diesem* Modul besonders ist. Alles Gemeinsame
– Toolchain, Encoding, JUnit – steht genau einmal im Konventions-Plugin unter `buildSrc/`.
Gradle kompiliert `buildSrc` vor allem anderen und stellt die Datei als Plugin
`de.makno.java-konventionen` bereit; ein Teilprojekt aktiviert sie mit einer Zeile.
(`subprojects { }` in der Wurzel-`build.gradle` täte Ähnliches, gilt aber als veraltet:
Es konfiguriert Projekte von außen, und die IDE versteht es schlechter.)

`implementation project(':kern')` statt Maven-Koordinaten: Es gibt keine Version, weil es
keine Veröffentlichung gibt – `konsole` benutzt immer den Stand von `kern`, der gerade im
Repository liegt. Ändert sich `kern`, ist das beim nächsten Build sofort drin, ohne
Publish, ohne Versionssprung. Genau das ist der Grund für ein Monorepo.

Die Build-Reihenfolge berechnet Gradle selbst: Aus den Abhängigkeiten entsteht ein
gerichteter Graph ohne Zyklen (DAG); `:konsole:compileJava` braucht `:kern:jar`, also
kommt `kern` zuerst. Was nicht voneinander abhängt (`konsole` und `bericht`), darf
parallel laufen (`org.gradle.parallel=true`). Ein Zyklus – `kern` hängt von `konsole`,
`konsole` von `kern` – ist kein Graph mehr, den man abarbeiten kann; Gradle bricht mit
`Circular dependency between the following tasks` ab, noch bevor ein Compiler startet.
