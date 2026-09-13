# Übung 07: Alle Versionen an einer Stelle

## Aufgabe

`build.gradle` nennt vier Versionsnummern, verstreut über die `dependencies`. Zieh sie in
einen **Version Catalog** `gradle/libs.versions.toml`:

1. `[versions]` mit je einem Eintrag für JUnit, Mockito, H2 und die JetBrains-Annotationen.
2. `[libraries]` für alle sechs Abhängigkeiten – die BOM-gebundenen (`junit-jupiter`,
   `junit-platform-launcher`) ohne Version.
3. Ein `[bundles]`-Eintrag `test`, der `junit-jupiter` und `mockito-core` zusammenfasst.
4. `build.gradle` greift nur noch über `libs.…` zu.

## Abnahmekriterien

- `./gradlew test` ist grün (zwei Tests).
- `build.gradle` enthält keine Versionsnummer mehr (kein `5.10.2`, `5.11.0`, `2.3.232`,
  `24.1.0`).
- `gradle/libs.versions.toml` hat die drei Abschnitte `[versions]`, `[libraries]`,
  `[bundles]`; der Bundle `test` wird in `build.gradle` benutzt.

## Hinweise

1. Ein Eintrag unter `[libraries]` sieht so aus:
   `mockito-core = { module = "org.mockito:mockito-core", version.ref = "mockito" }` –
   und `mockito = "5.11.0"` steht unter `[versions]`. Ohne Version:
   `junit-jupiter = { module = "org.junit.jupiter:junit-jupiter" }`.
2. In `build.gradle` werden Bindestriche zu Punkten: `libs.mockito.core`,
   `libs.junit.bom`, `libs.bundles.test`. Die BOM bleibt eine BOM:
   `testImplementation platform(libs.junit.bom)`.
3. Gradle findet die Datei von selbst, wenn sie `gradle/libs.versions.toml` heißt –
   keine Zeile in `settings.gradle` nötig. Bei einem Tippfehler im Namen sagt der Build
   beim Start, welcher Eintrag fehlt.
