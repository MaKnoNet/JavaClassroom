# Übung 02: App und Datenbank zusammen starten

## Aufgabe

Das Projekt ist die `kundenapi` aus Übung 01 samt Dockerfile. Sie hat ein Profil `prod`,
das eine echte Datenbank über die Umgebungsvariablen `DB_URL`, `DB_USER`, `DB_PASSWORD`
erwartet. Schreibe `compose.yaml` (und `.env`), sodass `podman compose up --build` die
App **und** eine PostgreSQL-Datenbank startet:

1. Dienst `db`: `postgres:16`, Zugangsdaten aus `.env`, Daten in einem benannten
   Volume.
2. Dienst `app`: aus dem Dockerfile gebaut, Port 8080, Profil `prod` und die drei
   `DB_*`-Variablen. Die App darf erst starten, wenn die Datenbank **bereit** ist.
3. Ein dritter Dienst `keycloak` (`quay.io/keycloak/keycloak:26.4`, `start-dev`,
   Port 8180) unter dem Compose-Profil `auth`, damit er nur auf Wunsch mitläuft.

## Abnahmekriterien

- `podman compose up --build -d`, dann `POST /api/kunden` mit `{"name":"Anna"}` → 201.
- `podman compose down` und erneut `up -d`: `GET /api/kunden` zeigt Anna weiterhin –
  die Daten liegen im Volume, nicht im Container.
- `podman compose logs app` zeigt **keinen** `Connection refused` – die App hat auf die
  Datenbank gewartet.
- `podman compose --profile auth up -d` startet zusätzlich Keycloak; `http://localhost:8180`
  antwortet.
- Kein Passwort in `compose.yaml` – nur `${…}`-Verweise auf `.env`.

## Hinweise

1. Im Compose-Netz ist der Dienstname der Hostname: `jdbc:postgresql://db:5432/kunden`.
   `localhost` wäre der App-Container selbst.
2. `depends_on: db` regelt nur die Startreihenfolge. Bereit ist PostgreSQL erst Sekunden
   später – dazwischen stirbt die App. Lösung: `healthcheck` am `db`-Dienst
   (`pg_isready -U <user> -d <db>`) und bei der App
   `depends_on: { db: { condition: service_healthy } }`.
3. `volumes: - kundendaten:/var/lib/postgresql/data` plus ein Eintrag `volumes:
   kundendaten:` am Dateiende. `podman compose down` behält es, `down -v` löscht es.
4. `.env` liegt neben `compose.yaml` und wird automatisch gelesen. Im echten Projekt
   gehört sie in `.gitignore`; eine `.env.example` ohne Werte zeigt, was gebraucht wird.
5. Profile: `profiles: ["auth"]` am Dienst – gestartet nur mit `--profile auth`.
