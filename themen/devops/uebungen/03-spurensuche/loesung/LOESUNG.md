# Lösung 03

Die drei Fehler, in der Reihenfolge, in der sie sich zeigen:

| # | Symptom (`podman compose logs app`) | Beweis | Ursache | Behebung |
|---|---|---|---|---|
| 1 | `Caused by: java.net.ConnectException: Connection refused` unter `Connection to localhost:5432 refused` | `podman exec <app> sh -c 'getent hosts db'` löst `db` auf – `localhost` ist der App-Container selbst | `DB_URL` zeigt auf `localhost` statt auf den Dienstnamen | `jdbc:postgresql://db:5432/…` |
| 2 | `The server requested SCRAM-based authentication, but no password was provided` | Warnung beim `up`: *The "DB_PASSWORD" variable is not set*; `podman compose config` zeigt `DB_PASSWORD: ""`; `podman inspect <app> --format '{{.Config.Env}}'` bestätigt es | `compose.yaml` liest `${DB_PASSWORD}`, in `.env` heißt die Variable `POSTGRES_PASSWORD` | `DB_PASSWORD: ${POSTGRES_PASSWORD}` |
| 3 | Log sagt `Tomcat started on port 8080`, `curl` bekommt `Empty reply from server` | `podman compose ps` zeigt `0.0.0.0:8080->8000/tcp`; `podman port <app>` dito | Port-Abbildung `8080:8000` – rechts steht der Container-Port, die App lauscht aber auf 8080 | `"8080:8080"` |

`compose.yaml` in diesem Ordner ist die korrigierte Fassung; `.env` ist unverändert – dort
lag nie ein Fehler, auch wenn Fehler 2 so aussieht.

Warum die Reihenfolge wichtig ist: Jeder Fehler verdeckt den nächsten. Wer nach dem ersten
`Connection refused` alles löscht und neu baut (`down -v`, `--build`), sieht denselben
Fehler wieder – und hat nebenbei die Datenbank gelöscht. Auf dem Server ist das die
teuerste Reaktion. Die billigste: Log lesen, unterste `Caused by` nehmen, mit *einem*
Befehl belegen, *eine* Zeile ändern, `up -d` (Compose ersetzt nur den geänderten Dienst).

Die Werkzeuge kommen in der Reihenfolge ihrer Tiefe: `ps` (lebt es?), `logs` (was sagt
es?), `config`/`inspect` (womit wurde es gestartet?), `port` (kommt man hin?), `exec`
(Blick von innen). Wer diese Leiter von oben abarbeitet, braucht selten die unterste Sprosse.
