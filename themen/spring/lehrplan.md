---
thema: spring
titel: Spring Boot
voraussetzungen: [java, gradle, junit]
zielgruppe: [azubi, student, kollege]
---

# Spring Boot

Die Brücke von Core-Java zur Anwendung im Betrieb: Wer Java 18 hinter sich hat, kann
Klassen schreiben – hier lernt er, wie daraus ein Server wird, der über HTTP spricht,
Daten dauerhaft speichert und dabei testbar bleibt. Spring Boot ist das Fundament unserer
Vaadin-Anwendungen; alles hier Gelernte wird dort vorausgesetzt.

Fünf Lektionen, in dieser Reihenfolge: Erst der Container (ohne ihn versteht man nichts,
was danach kommt), dann HTTP nach außen, dann die Datenbank, dann Transaktionen, zum
Schluss die Konfiguration, an der im Betrieb alles hängt. Übungen laufen ohne
Installation: H2 als Datenbank im Speicher, der eingebettete Tomcat als Webserver.

## Lektionen

### 01 Der Container: Inversion of Control
- **Ziele:** Das Hollywood-Prinzip („Don't call us, we'll call you"): Nicht der Code baut seine Abhängigkeiten, der Container reicht sie herein; warum `new` in Service-Klassen den Code starr und untestbar macht (Java-Übung 07 als Vorlage); `@SpringBootApplication` und Komponenten-Scanning ab dem Startpaket; `@Component`, `@Service`, `@Repository`, `@Configuration` + `@Bean`; **Konstruktor-Injection** als Regel – `final`-Feld, ein Konstruktor, kein `@Autowired` nötig; warum `@Autowired` am Feld schlecht ist (nicht `final`, ohne Container nicht baubar, versteckt Abhängigkeiten); Bean-Scopes: Singleton als Standard und die Folge „kein Nutzerzustand in Services" (Vaadin-Lektion 11 greift das auf); `@SpringBootTest` und `@MockitoBean`
- **Übung:** uebungen/01-automat-im-container
- **Prüffrage:** Warum erschwert `new` innerhalb einer Service-Klasse das Schreiben isolierter Unit-Tests – und was ändert Konstruktor-Injection daran? Und: Ein Service hält den „aktuellen Kunden" in einem Feld – was passiert bei zwei gleichzeitigen Nutzern?
- **Übersetzung:** en: The container: inversion of control | fr: Le conteneur : inversion de contrôle
- **Stufe:** 2

Leitfaden: Mit Java-Übung 07 beginnen – dort hat der Lernende das `new` schon selbst
nach außen geschoben. Frage: „Wer ruft jetzt `new GetraenkeAutomat(...)` auf?" Antwort im
Betrieb: der Container. Erst dann `@Service` zeigen. Fehlermeldungen lesen lassen –
`No qualifying bean` und `required a bean of type … that could not be found` sind die
zwei, die jeder Spring-Entwickler auswendig kennt.

### 02 Über HTTP sprechen: REST und JSON
- **Ziele:** HTTP-Grundlagen: Verben (`GET`, `POST`, `PUT`, `DELETE`), Pfad, Header, Körper; Statuscodes, die man kennen muss (200, 201, 400, 404, 500) und was ein 500 über den Code sagt; `@RestController`, `@GetMapping`/`@PostMapping`, `@PathVariable`, `@RequestBody`; `ResponseEntity`, wenn der Status vom Ergebnis abhängt; Jackson: Java-Objekt ↔ JSON, warum Records dafür ideal sind; die andere Richtung mit dem JDK-`HttpClient` – Status prüfen, dann parsen; Tests mit `@SpringBootTest(webEnvironment = RANDOM_PORT)` und `TestRestTemplate` (Ergänzung zu JUnit-Übung 05)
- **Übung:** uebungen/02-wetter-api
- **Prüffrage:** Welcher Statuscode ist richtig, wenn ein Client einen Benutzer abruft, den es nicht gibt – und welcher, wenn die Id keine Zahl ist? Und: Was unterscheidet `@RestController` von `@Controller`?
- **Übersetzung:** en: Talking HTTP: REST and JSON | fr: Parler HTTP : REST et JSON
- **Stufe:** 2

Leitfaden: `./gradlew bootRun`, dann die URL im Browser – der Lernende soll das JSON
sehen, bevor er den Test liest. Statuscodes als Vokabeltest: eine Situation nennen, der
Lernende sagt den Code. 500 gesondert: „Das ist nie eine Antwort, das ist ein Bug."

### 03 Daten für immer: JPA und Entities
- **Ziele:** Die Impedanzfehlanpassung: Objekte haben Referenzen und Vererbung, Tabellen haben Zeilen und Fremdschlüssel – ein ORM übersetzt; Hibernate als JPA-Implementierung, `spring-boot-starter-data-jpa`; `@Entity`, `@Id`, `@GeneratedValue`, `@Column(nullable, unique, precision/scale)`; der parameterlose Konstruktor als Preis des ORM – und warum ein Record deshalb keine Entity sein kann (Record für DTOs, Klasse für Entities); Beziehungen `@ManyToOne` (Fremdschlüssel liegt auf der „Viele"-Seite) und `@OneToMany(mappedBy)` als Gegenrichtung; `spring.jpa.show-sql` – das erzeugte SQL lesen; `ddl-auto` im Test bequem, im Betrieb gefährlich → Migrationen (Flyway/Liquibase) als Ausblick; H2 im Speicher für Übungen und Tests
- **Übung:** uebungen/03-entitaet
- **Prüffrage:** Warum reicht eine normale Java-Klasse nicht, um direkt in einer relationalen Datenbank gespeichert zu werden, und welche Aufgabe übernimmt Hibernate dabei? Und: Warum kann ein Record keine Entity sein?
- **Übersetzung:** en: Data that lasts: JPA and entities | fr: Des données durables : JPA et entités
- **Stufe:** 2

Leitfaden: Erst das SQL im Testlog anschauen (`create table produkt (…)`), dann die
Annotationen erklären, die es erzeugt haben – nicht umgekehrt. Geld ist `BigDecimal` mit
`precision`/`scale`, nie `double` (Java-Lektion 02 einlösen).

### 04 Repository und Transaktionen
- **Ziele:** Trennung von Geschäftslogik und Datenzugriff; `JpaRepository<Entity, Id>` – Spring Data liefert die Implementierung; CRUD (`save`, `findById`, `findAll`, `delete`, `count`); **abgeleitete Abfragen** aus dem Methodennamen (`findByEmail`, `existsBy…`, `countBy…GreaterThan`), Fehler fallen beim Start auf; `Optional` als Rückgabe für „vielleicht nicht da"; `@Transactional`: ganz oder gar nicht, Rollback bei `RuntimeException` (nicht bei checked Exceptions!); der Persistenzkontext und *dirty checking* – warum `save` in einer Transaktion oft überflüssig ist; `@DataJpaTest` (transaktional, rollt zurück) vs. `@SpringBootTest` (echte Wirkung); Testcontainers als der ehrliche Weg gegen die echte Datenbank – braucht Docker, deshalb hier nur genannt, und was H2 verschweigt (Dialekt, echte Constraints)
- **Übung:** uebungen/04-kunden-repository
- **Prüffrage:** Was passiert mit den Änderungen an der Datenbank, wenn innerhalb einer `@Transactional`-Methode eine `RuntimeException` geworfen wird – und was, wenn es eine checked Exception ist?
- **Übersetzung:** en: Repositories and transactions | fr: Dépôts et transactions
- **Stufe:** 2

Leitfaden: Den Fehler erleben lassen – Schritt 2 der Übung ist zuerst rot, Anna hat
Geld verloren, das niemand bekommen hat. Erst dann `@Transactional`. Das ist die
Banküberweisung aus dem Lehrbuch, und sie bleibt hängen, wenn man sie einmal selbst
kaputt gesehen hat.

### 05 Konfiguration und Profile
- **Ziele:** `application.properties`/`.yml`; `@Value` und typsichere `@ConfigurationProperties` (Record mit `@ConfigurationProperties`); Profile (`spring.profiles.active=test|dev|prod`, `application-prod.properties`); Rangfolge: Umgebungsvariablen und Kommandozeile schlagen Dateien; **Secrets nie im Repository** – Passwörter und Tokens über Umgebungsvariablen oder Secret-Manager; H2 im Test, PostgreSQL im Betrieb als typische Profil-Weiche; Actuator (`/actuator/health`) als Ausblick auf den Betrieb; `bootJar` und `java -jar` – die Anwendung ist eine Datei
- **Prüffrage:** Ein Datenbankpasswort steht in `application.properties` und liegt im Git-Repository – was ist daran falsch, und wo gehört es hin? Und: Welche Quelle gewinnt, wenn `server.port` in der Datei *und* als Umgebungsvariable gesetzt ist?
- **Übersetzung:** en: Configuration and profiles | fr: Configuration et profils
- **Stufe:** 2

Leitfaden: An der Übung 04 zeigen: `./gradlew bootRun --args='--server.port=9000'`,
dann `SERVER_PORT=9001` als Umgebungsvariable – wer gewinnt? Die Regel „was im Repo
steht, muss auf jedem Rechner funktionieren" gilt hier wie bei Gradle-Properties.
