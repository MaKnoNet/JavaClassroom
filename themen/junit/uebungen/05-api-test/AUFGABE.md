# Übung 05: API-Test gegen einen echten HTTP-Server

## Aufgabe

`ArtikelServer` ist ein kleiner Webserver mit JSON-API (fertig, nicht ändern). Teste ihn
**von außen über HTTP** – Grey-Box: Du kennst die Schnittstelle, nicht die Innereien.
Schreibe in `ArtikelApiTest` mindestens vier Tests mit dem JDK-eigenen `HttpClient`:

1. `GET /api/artikel` → Status 200, `Content-Type` beginnt mit `application/json`, die
   Antwort enthält `"Schraube"`.
2. `GET /api/artikel/2` → Status 200, Antwort enthält `"name":"Mutter"`.
3. `GET /api/artikel/99` → Status 404.
4. `GET /api/artikel/abc` → Status 400.

Der Server wird einmal für die Testklasse gestartet (`@BeforeAll`) und danach gestoppt
(`@AfterAll`); den Port wählt er selbst (`basisUrl()`).

## Abnahmekriterien

- `./gradlew test` ist grün; `TEST-de.makno.lernen.ArtikelApiTest.xml` enthält `tests="4"`
  (oder mehr) und `failures="0"`.
- Kein Test greift auf `ArtikelServer` zu, außer um ihn zu starten, zu stoppen und die
  URL zu holen – geprüft wird nur, was über HTTP zurückkommt.

## Hinweise

1. `@BeforeAll`/`@AfterAll` brauchen `static` Methoden und Felder:
   ```java
   private static ArtikelServer server;
   private static final HttpClient CLIENT = HttpClient.newHttpClient();
   ```
2. Eine Anfrage:
   ```java
   HttpRequest anfrage = HttpRequest.newBuilder(URI.create(server.basisUrl() + "/api/artikel")).GET().build();
   HttpResponse<String> antwort = CLIENT.send(anfrage, HttpResponse.BodyHandlers.ofString());
   // antwort.statusCode(), antwort.body(), antwort.headers().firstValue("Content-Type")
   ```
   Eine kleine Hilfsmethode `hole(String pfad)` spart Wiederholung.
