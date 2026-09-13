# Lösung 01

`loesung/Dockerfile` kommt in den Projektordner neben `build.gradle`.

```bash
podman build -t kundenapi .
podman run --rm -p 8080:8080 kundenapi
curl http://localhost:8080/api/kunden      # []
```

Warum so: Zwei Stufen, weil Bauen und Ausführen verschiedene Dinge brauchen. Die
Build-Stufe hat das ganze JDK, lädt Gradle und alle Abhängigkeiten – mehrere hundert MB,
die im fertigen Image nichts verloren haben. Die zweite Stufe beginnt bei einer nackten
JRE und holt sich mit `COPY --from=build` nur das eine JAR. Ergebnis: ein kleineres Image,
das keinen Compiler, keine Quellen und keine Build-Werkzeuge enthält – nichts, womit ein
Angreifer im Container etwas anfangen könnte.

Die Reihenfolge der `COPY`-Zeilen ist kein Zufall: Jede Anweisung wird zu einer Schicht,
und Podman baut eine Schicht nur neu, wenn sich ihre Eingaben geändert haben – alle
folgenden dann ebenfalls. Wrapper und Build-Dateien zuerst heißt: Nach einer Änderung im
Quellcode werden weder Gradle noch die Abhängigkeiten neu geladen; der zweite Build dauert
Sekunden statt Minuten. Wer `COPY . .` als Erstes schreibt, lädt bei jedem Tippfehler
alles neu.

`USER 1000` lässt die Anwendung nicht als root laufen; `.dockerignore` hält `build/` und
`.gradle/` aus dem Build-Kontext, sonst wandert der lokale Build-Müll in die erste Stufe
und macht den Cache kaputt.
