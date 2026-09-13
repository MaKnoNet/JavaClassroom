# Übung 01: Die Anwendung in einen Container packen

## Aufgabe

`kundenapi` ist eine fertige Spring-Boot-Anwendung (GET/POST `/api/kunden`, H2 im
Speicher). Sie soll als Container laufen – auf jedem Rechner, ohne installiertes Java.
Schreibe ein `Dockerfile` mit **zwei Stufen**:

1. **Bauen** in `eclipse-temurin:21-jdk`: Wrapper und Build-Dateien kopieren, dann erst
   den Quellcode; `./gradlew bootJar` erzeugt `build/libs/kundenapi.jar`.
2. **Ausführen** in `eclipse-temurin:21-jre`: nur das JAR aus Stufe 1 übernehmen,
   Port 8080 freigeben, `java -jar` als Startbefehl.

## Abnahmekriterien

- `podman build -t kundenapi .` läuft durch; `podman images` zeigt `kundenapi` unter
  400 MB.
- `podman run --rm -p 8080:8080 kundenapi` startet; `curl http://localhost:8080/api/kunden`
  liefert `[]`, ein `POST` mit `{"name":"Anna"}` liefert 201.
- Eine Änderung in `KundenController.java` und erneutes `podman build`: Die Schritte
  für Wrapper und Abhängigkeiten melden `CACHED`, nur `bootJar` läuft neu.
- Das fertige Image enthält kein `gradlew` und kein `src/` (`podman run --rm --entrypoint ls kundenapi /app` – ohne `--entrypoint` würde `ls` als Argument an `java -jar` gehen).

## Hinweise

1. Grundgerüst: `FROM <image> AS build`, `WORKDIR`, `COPY`, `RUN`, dann `FROM <image>`
   erneut und `COPY --from=build <pfad> <ziel>`. `EXPOSE 8080`, `ENTRYPOINT ["java",
   "-jar", "app.jar"]`.
2. Schichten: Jede Zeile ist eine Schicht, und ab der ersten geänderten Eingabe wird
   alles darunter neu gebaut. Deshalb `gradlew` und `gradle/` zuerst (`./gradlew
   --version` lädt die Distribution), dann `settings.gradle`/`build.gradle` (`./gradlew
   dependencies`), erst dann `src`. Falsche Reihenfolge merkst du am zweiten Build.
3. `--no-daemon` in Containern – der Daemon würde nach dem Build weiterlaufen.
4. **Container vs. VM:** Ein Container teilt sich den Kernel mit dem Host und startet in
   Sekunden; eine VM bringt ein ganzes Betriebssystem mit. Unter Windows läuft Podman
   deshalb in einer WSL-Maschine – das ist die eine VM, in der alle Container leben.
   Nach einem Neustart: `podman machine start`.
5. Logs: `podman logs <container>`; laufende Container: `podman ps`; stoppen: `podman
   stop`. Wer `docker` gewohnt ist: Die Befehle sind dieselben.
