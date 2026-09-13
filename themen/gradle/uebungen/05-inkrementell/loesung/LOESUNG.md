Zwei Dateien: `build.gradle` ersetzt die im Projektordner, `gradle.properties` kommt neu
daneben.

Warum so: `inputs.dir` und `outputs.file` sagen Gradle, wovon das Ergebnis abhängt und wo
es liegt. Vor jedem Lauf vergleicht Gradle Fingerabdrücke der Eingaben und prüft, ob die
Ausgabe noch da ist – stimmt beides, meldet es `UP-TO-DATE` und führt `doLast` nicht aus.
Ohne diese Angaben kann Gradle nicht wissen, dass `daten/` die Eingabe ist, und muss
jedes Mal rechnen. `outputs.cacheIf { true }` erlaubt zusätzlich, das Ergebnis im
Build-Cache abzulegen; `org.gradle.caching=true` schaltet den Cache ein. Nach `clean` ist
die Ausgabe weg, aber der Fingerabdruck der Eingaben bekannt – Gradle holt die Datei aus
dem Cache: `FROM-CACHE`. Genau so funktioniert es bei `compileJava` und `test`, nur dass
das Java-Plugin die Inputs und Outputs dort schon deklariert hat.

`gradle.properties` im Projekt gilt für alle, die das Repo klonen – deshalb gehört dort nur
hinein, was auf jedem Rechner richtig ist (`org.gradle.caching`, `org.gradle.parallel`).
Maschinenspezifisches (Proxy-Truststore, Speicher für den Daemon) gehört in
`~/.gradle/gradle.properties`, nie ins Repository.
