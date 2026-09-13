---
thema: devops
titel: Cloud-Native und Betrieb
voraussetzungen: [java, gradle, git, spring]
zielgruppe: [azubi, student, kollege]
reihenfolge: 140
---

# Cloud-Native und Betrieb

Die Brücke zwischen fertigem Quellcode und echtem Betrieb: Wie Software paketiert,
automatisiert geprüft, sicher ausgerollt und im Team betrieben wird. Alle vier Lektionen
sind Stufe 3 – sie setzen den Java-Pfad bis Spring Boot voraus; Lektion 04 zusätzlich
Vaadin-Lektion 17 (Spring Security mit Vaadin).

Werkzeug ist eine Container-Laufzeit. Docker Desktop ist in Firmen ab 250 Mitarbeitern
lizenzpflichtig; **Podman** (oder Rancher Desktop) ist frei und versteht dieselben
Dockerfiles und Compose-Dateien. Die Übungen sind mit Podman geprüft; wer `docker` hat,
ersetzt nur das Wort. Nach einem Neustart unter Windows zuerst `podman machine start`.

## Lektionen

### 01 Anwendung containerisieren: Docker
- **Ziele:** Container vs. virtuelle Maschine (geteilter Kernel, Sekundenstart; unter Windows lebt alles in einer WSL-Maschine); Image, Container, Registry; `Dockerfile` für eine Spring-Boot-Anwendung: `FROM`, `WORKDIR`, `COPY`, `RUN`, `EXPOSE`, `ENTRYPOINT`; **Multi-Stage-Build** – kompilieren im JDK-Image, ausführen im schlanken JRE-Image (Gradle-Lektion 11 und Spring-Lektion 05 liefern das JAR); Layer-Caching: Reihenfolge der `COPY`-Zeilen bestimmt, was nach einer Änderung neu gebaut wird; `.dockerignore`; nicht als root (`USER`); `podman build/run/ps/logs/stop/exec`, `--entrypoint`
- **Übung:** uebungen/01-docker-image
- **Prüffrage:** Warum nutzt man Multi-Stage-Builds in einem Dockerfile, und warum gehört das vollständige JDK nicht in das finale Produktions-Image? Und: Warum kopiert man `gradlew` und `build.gradle` vor `src/`?
- **Übersetzung:** en: Containerising the application: Docker | fr: Conteneuriser l'application : Docker
- **Stufe:** 3

Leitfaden: Den zweiten Build vorführen – nach einer Zeile Änderung im Controller melden
sieben Schritte `Using cache`, nur `bootJar` läuft. Wer die `COPY`-Reihenfolge falsch hat,
sieht Gradle jedes Mal neu laden; das ist die Lektion, nicht die Syntax.

### 02 Lokale Multi-Container-Umgebungen: Compose
- **Ziele:** `compose.yaml`: Dienste, Netz, Ports, Volumes; die eigene Anwendung zusammen mit PostgreSQL (und Keycloak für Lektion 04) starten; Dienstname = Hostname im Compose-Netz; Umgebungsvariablen aus `.env` per `${…}` – nie Passwörter in der Datei, `.env` in `.gitignore`, `.env.example` im Repo (Spring-Lektion 05); Startreihenfolge vs. Bereitschaft: `depends_on` mit `condition: service_healthy` und `healthcheck` (`pg_isready`); benannte Volumes – `down` behält, `down -v` löscht; Compose-Profile für optionale Dienste; `compose logs`, `compose ps`
- **Übung:** uebungen/02-docker-compose
- **Prüffrage:** Deine Anwendung stürzt beim Start via Compose ab, weil sie keine Verbindung zur Datenbank bekommt. Wie stellst du sicher, dass die Datenbank vor der App *bereit* ist – und wie lautet der Hostname der Datenbank im gemeinsamen Netz?
- **Übersetzung:** en: Local multi-container environments: Compose | fr: Environnements multi-conteneurs locaux : Compose
- **Stufe:** 3

Leitfaden: Den Fehler erleben lassen – `depends_on` ohne `condition` schreiben, `up`,
`Connection refused` im Log lesen. Dann `healthcheck` ergänzen. Dasselbe Muster wie
Spring-Lektion 04: erst kaputt sehen, dann verstehen, warum die Lösung so aussieht.

### 03 Automatisierung: CI/CD-Pipelines
- **Ziele:** Pipeline als YAML im Repository (GitHub Actions oder GitLab CI): Trigger (Push, Pull Request, Tag), Jobs, Schritte, Runner; bei jedem Pull Request `./gradlew check` (enthält `test` – Gradle-Lektion 05); Gradle-Cache in der Cloud (`gradle/actions/setup-gradle`) gegen Zeit und Kosten; Testberichte als Artefakt; Image bauen und in die Registry pushen, wenn ein Git-Tag `v*` gesetzt wird (Git-Lektion 14); **Secrets** nie im Klartext in der YAML – Plattform-Secrets (`${{ secrets.NAME }}`) und der bereitgestellte `GITHUB_TOKEN`; `npm ci` statt `npm install` in Pipelines (Frontend-Lektion 01); Vorlage `vorlagen/github-actions-ci.yml`
- **Prüffrage:** Warum ist es ein Anti-Pattern, die Zugangsdaten zur Docker-Registry im Klartext in die Pipeline-YAML zu schreiben, und wie lösen GitHub und GitLab dieses Problem? Und: Warum läuft der Image-Job nur bei einem Tag, die Prüfung aber bei jedem Pull Request?
- **Übersetzung:** en: Automation: CI/CD pipelines | fr: Automatisation : pipelines CI/CD
- **Stufe:** 3

Leitfaden: Keine Übung – eine Pipeline läuft nicht lokal. Die Vorlage gemeinsam lesen,
Zeile für Zeile: Was ist ein Trigger, was ein Job, warum `needs`. Wer ein eigenes
GitHub-Repository hat, legt sie unter `.github/workflows/ci.yml` ab und schaut beim
ersten Push zu. Die JUnit-Lektion 11 (Tests in der Pipeline) ist die inhaltliche Vorstufe.

### 04 Enterprise Security: OAuth2 und OpenID Connect
- **Ziele:** Ablösung der lokalen Benutzertabelle durch einen Identity Provider (Keycloak aus Übung 02, Profil `auth`); OAuth2 als Autorisierung, OpenID Connect als Identität obendrauf; Authorization Code Flow mit PKCE Schritt für Schritt; ID-Token vs. Access-Token, JWT lesen (Header, Claims, Signatur); Spring Security als OIDC-Client (`spring-boot-starter-oauth2-client`, `spring.security.oauth2.client.registration.*`); Rollen aus dem Token: Spring liest sie **nicht** von selbst – `GrantedAuthoritiesMapper` (OIDC-Login) bzw. `JwtAuthenticationConverter` (Resource Server) bilden Keycloak-Rollen auf `ROLE_*` ab, erst dann greift `@RolesAllowed` in Vaadin (Lektion 17); Logout beim Provider; niemals Tokens loggen
- **Prüffrage:** Was ist der Unterschied zwischen OAuth2 und OpenID Connect – und welches der beiden liefert die Benutzerdaten (E-Mail, Name) für deine Anwendung? Und: Warum sieht Spring die Keycloak-Rolle `admin` nicht automatisch als `ROLE_ADMIN`?
- **Übersetzung:** en: Enterprise security: OAuth2 and OpenID Connect | fr: Sécurité d'entreprise : OAuth2 et OpenID Connect
- **Stufe:** 3

Leitfaden: Keycloak aus Übung 02 starten (`--profile auth`), unter `localhost:8180` einen
Realm, einen Client (Confidential, Redirect-URI der App) und einen Benutzer mit Rolle
anlegen – von Hand, damit die Begriffe sitzen. Dann den Flow im Browser-Netzwerktab
verfolgen: Redirect zum Provider, Login, Redirect zurück mit `code`, Token-Austausch im
Hintergrund. Das JWT auf jwt.io einfügen (nur Test-Tokens!) und die Claims lesen. Erst
dann Spring konfigurieren.
