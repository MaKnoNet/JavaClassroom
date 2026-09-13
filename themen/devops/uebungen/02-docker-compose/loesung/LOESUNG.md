# Lösung 02

`compose.yaml` und `.env` kommen in den Projektordner.

```bash
podman compose up --build -d
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Anna"}' http://localhost:8080/api/kunden
podman compose down            # Container weg, Volume bleibt
podman compose up -d           # Anna ist noch da
podman compose down -v         # jetzt ist auch das Volume weg
```

Warum so: Compose beschreibt die ganze Umgebung in einer Datei – jeder im Team startet
dieselben Dienste mit einem Befehl. Die Dienste liegen in einem gemeinsamen Netz, in dem
der **Dienstname der Hostname** ist: Die App erreicht die Datenbank unter `db`, nicht
unter `localhost` (das wäre der eigene Container). Die Zugangsdaten stehen in `.env` und
werden per `${…}` eingesetzt; die App bekommt sie als Umgebungsvariablen, die Spring in
`application-prod.properties` einliest (Spring-Lektion 05) – nichts davon steht im Image.

`depends_on` allein regelt nur die *Start*-Reihenfolge; PostgreSQL braucht danach noch
Sekunden, bis es Verbindungen annimmt. Genau in dieser Lücke stirbt die App mit
`Connection refused`. Der `healthcheck` mit `pg_isready` sagt Compose, wann die Datenbank
*bereit* ist, und `condition: service_healthy` lässt die App erst dann starten. Das
benannte Volume hält die Daten außerhalb des Containers: `down` entfernt Container und
Netz, das Volume bleibt – so wie eine Datenbank es soll. Keycloak liegt unter einem
Profil, damit er nur mitstartet, wenn man ihn braucht (Lektion 04) – 500 MB Image und
eine halbe Minute Start will nicht jeder bei jedem `up`.
