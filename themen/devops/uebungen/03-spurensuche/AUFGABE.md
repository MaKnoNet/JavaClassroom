# Übung 03: Spurensuche – „Auf dem Server geht's nicht"

## Aufgabe

Die `kundenapi` aus Übung 02 soll auf einem neuen Rechner laufen. `compose.yaml` und
`.env` stammen aus einem Wiki-Artikel, den seit Monaten niemand angefasst hat. Du
startest:

```bash
podman compose up --build -d
curl http://localhost:8080/actuator/health
```

… und bekommst keine Antwort. **Der Code ist in Ordnung.** Die Übung ist nicht, den Fehler
zu raten, sondern ihn zu *finden* – mit den Werkzeugen, die auf jedem Server da sind.

Es stecken **drei voneinander unabhängige Fehler** in `compose.yaml`. Sie zeigen sich
nacheinander: Erst wenn der erste behoben ist, wird der zweite sichtbar. Für jeden Fehler
hältst du in `BEFUND.md` fest:

1. **Symptom** – was hast du gesehen (Befehl und die entscheidende Zeile der Ausgabe)?
2. **Beweis** – welcher Befehl hat die Ursache belegt, nicht nur vermutet?
3. **Ursache** – ein Satz.
4. **Behebung** – die geänderte Zeile.

## Abnahmekriterien

- `curl http://localhost:8080/actuator/health` antwortet `{"status":"UP"}`.
- `POST /api/kunden` mit `{"name":"Anna"}` antwortet 201.
- `BEFUND.md` enthält drei Einträge mit je Symptom, Beweis, Ursache, Behebung.
- Du hast **kein** `podman compose down -v` gebraucht, um einen Fehler zu finden – und
  kannst sagen, warum das auf einem echten Server die falsche erste Reaktion wäre.

## Werkzeugkasten

| Frage | Befehl |
|---|---|
| Was läuft, was ist gestorben? | `podman compose ps` – Spalte *Status* und *Ports* |
| Was sagt der Dienst selbst? | `podman compose logs app` (mit `-f` live, `--tail 50` für das Ende) |
| Wie sieht die Compose-Datei *nach* dem Einsetzen der Variablen aus? | `podman compose config` |
| Womit wurde der Container tatsächlich gestartet? | `podman inspect <container> --format '{{.Config.Env}}'` |
| Welche Ports sind wohin gebunden? | `podman port <container>` |
| Kann der Container selbst sein Ziel erreichen? | `podman exec <container> sh -c 'getent hosts db'` |
| Was läuft im Container? | `podman exec -it <container> sh`, dann `ps`, `env`, `ls /app` |

## Hinweise

1. **Stacktrace von unten lesen.** Ein Java-Stacktrace listet die Ursachenkette als
   `Caused by:` – die letzte davon ist die eigentliche. Alles darüber ist Verpackung
   (Hibernate, Spring), die den gleichen Fehler weiterreicht.
2. Ein Hostname, der im Log auftaucht, ist eine Behauptung der Konfiguration – nicht der
   Wirklichkeit. Was ist `localhost` aus Sicht eines Containers?
3. Compose warnt beim Start, wenn eine Variable in `${…}` nirgends gesetzt ist – die
   Warnung steht **vor** dem eigentlichen Start und wird gern überscrollt.
   `podman compose config` zeigt, was daraus wurde.
4. Wenn das Log „Tomcat started on port 8080" sagt und `curl` trotzdem ins Leere läuft,
   liegt der Fehler zwischen deinem Rechner und dem Container – nicht in der App.
   `podman compose ps` zeigt in der Spalte *Ports* beide Seiten der Abbildung.
