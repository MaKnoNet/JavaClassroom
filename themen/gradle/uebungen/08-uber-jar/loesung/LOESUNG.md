Die Datei ersetzt `build.gradle` im Projektordner; `./gradlew shadowJar` baut
`build/libs/app-all.jar`.

Warum `java -jar app.jar` scheitert: Das normale `jar` enthält nur *unsere* Klassen. Die
`Main-Class` steht im Manifest, aber `ObjectMapper` liegt in `jackson-databind.jar` – und
das kennt die JVM beim Start nicht, weil niemand ihr einen Klassenpfad gegeben hat. In der
IDE und bei `./gradlew run` setzt Gradle den Klassenpfad; `java -jar` nicht. Ein Uber-JAR
(Fat-JAR) entpackt alle Abhängigkeiten und packt sie mit den eigenen Klassen in ein
einziges Archiv – dann reicht die eine Datei.

Der Weg ohne Plugin zeigt, was dabei passiert – zehn Zeilen, die dasselbe tun:

```groovy
tasks.register('uberJar', Jar) {
    archiveClassifier = 'all'
    manifest { attributes 'Main-Class': 'de.makno.lernen.App' }
    from sourceSets.main.output
    dependsOn configurations.runtimeClasspath
    from { configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) } }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    exclude 'META-INF/*.SF', 'META-INF/*.DSA', 'META-INF/*.RSA'
}
```

`zipTree` entpackt jedes Abhängigkeits-JAR in das eigene; `duplicatesStrategy` und die
`exclude`-Zeile räumen weg, was in mehreren JARs gleich heißt (`module-info.class`,
Signaturen). Shadow macht dasselbe und kümmert sich zusätzlich um Service-Dateien
(`META-INF/services`), die beim einfachen Überschreiben verloren gingen – deshalb im
Alltag das Plugin. Spring Boot bringt mit `bootJar` seinen eigenen Packer mit (Spring
Lektion 05); dort braucht man Shadow nicht.

Ausblick für Bibliotheken statt Anwendungen: Ein Uber-JAR ist dort falsch – wer eine
Bibliothek nutzt, will ihre Abhängigkeiten selbst steuern. Bibliotheken werden als
normales JAR mit `maven-publish` in ein Repository veröffentlicht (Stufe 3).
