# Übung 08: Ein JAR, das allein läuft

## Aufgabe

`App` gibt eine Begrüßung als JSON aus und nutzt dafür Jackson. `./gradlew jar` baut
`build/libs/app.jar`, das Manifest kennt die `Main-Class` – und trotzdem:

```
java -jar build/libs/app.jar
Exception in thread "main" java.lang.NoClassDefFoundError: com/fasterxml/jackson/databind/ObjectMapper
```

Finde heraus, warum, und baue ein JAR `build/libs/app-all.jar`, das mit `java -jar`
auf jedem Rechner mit JDK 21 läuft – ohne Gradle, ohne IDE.

## Abnahmekriterien

- `java -jar build/libs/app-all.jar` gibt `{"gruss":"Hallo Welt"}` aus;
  `java -jar build/libs/app-all.jar Gradle` gibt `{"gruss":"Hallo Gradle"}` aus.
- Das JAR ist deutlich größer als `app.jar` (Jackson steckt drin – rund 2 MB statt 2 KB).
- Du kannst erklären, warum `app.jar` scheitert, obwohl es kompiliert und in der IDE läuft.

## Hinweise

1. Schau ins JAR: `jar tf build/libs/app.jar` listet nur `de/makno/lernen/App.class` und
   das Manifest. Wo ist Jackson? In `~/.gradle/caches/…` – da kommt `java -jar` nicht hin.
2. Zwei Wege: (a) Das Shadow-Plugin `id 'com.gradleup.shadow' version '9.2.2'` in
   `plugins { }` – danach gibt es `./gradlew shadowJar`, das `app-all.jar` erzeugt und
   das Manifest aus `jar` übernimmt. (b) Von Hand ein Task vom Typ `Jar`, der
   `sourceSets.main.output` und alle `configurations.runtimeClasspath` per `zipTree`
   zusammenpackt – zehn Zeilen, und man sieht, was Shadow tut. Mach (b) einmal, wenn du
   wissen willst, was ein Fat-JAR ist; nimm (a) im Projekt.
3. Achtung bei Anleitungen im Netz: `com.github.johnrengelman.shadow` ist die alte
   Kennung und läuft mit Gradle 9 nicht mehr.
