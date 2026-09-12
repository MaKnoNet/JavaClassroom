Warum so: `testImplementation` macht JUnit nur für Tests sichtbar, nicht für den
Produktionscode. `testRuntimeOnly 'org.junit.platform:junit-platform-launcher'` braucht
Gradle 9.6.1, um die Tests zu starten. `useJUnitPlatform()` schaltet den JUnit-5-Runner ein –
ohne ihn findet Gradle keinen einzigen Test und meldet trotzdem Erfolg.
Die Datei ersetzt `build.gradle` im Projektordner.
