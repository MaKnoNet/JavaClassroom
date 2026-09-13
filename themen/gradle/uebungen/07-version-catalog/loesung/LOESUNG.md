Zwei Dateien: `gradle/libs.versions.toml` kommt neu in den Ordner `gradle/` (neben
`wrapper/`), `build.gradle` ersetzt die im Projektordner.

Warum so: Der Katalog trennt *was* (Koordinaten) von *welche Version* – `version.ref`
zeigt auf einen Eintrag unter `[versions]`, den mehrere Bibliotheken teilen können. In
`build.gradle` wird aus `'org.mockito:mockito-core:5.11.0'` der typsichere Zugriff
`libs.mockito.core` (Bindestriche werden zu Punkten); ein Tippfehler fällt beim Laden auf,
nicht erst beim Herunterladen. `[bundles]` fasst zusammen, was immer gemeinsam gebraucht
wird. Der Gewinn zeigt sich im Multi-Projekt: Zehn Teilprojekte, eine Datei – JUnit
anheben heißt eine Zeile ändern, und kein Teilprojekt läuft mit einer anderen Version als
die anderen. `junit-jupiter` und der Launcher stehen ohne Version im Katalog, weil die
BOM sie festlegt – das geht im Katalog genauso wie vorher.
