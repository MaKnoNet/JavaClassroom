# Übung 02: Wetter über HTTP – in beide Richtungen

## Aufgabe

Zwei Klassen sind Rümpfe; zwei Tests erwarten, dass sie sprechen.

**Server:** `WetterController` soll unter `GET /api/wetter/{stadt}` antworten. Bekannte
Stadt → Status 200 und das `Wetter` als JSON (`{"stadt":"Berlin","temperatur":21.5}`);
unbekannte Stadt → Status 404. Die Daten liefert der fertige `WetterService`.

**Client:** `WetterAbruf.hole(stadt)` ruft mit dem JDK-eigenen `HttpClient` denselben
Pfad bei einem fremden Server auf (`basisUrl` aus dem Konstruktor), wandelt die
JSON-Antwort mit Jacksons `ObjectMapper` in ein `Wetter` und wirft bei jedem Status außer
200 eine `IllegalStateException` mit der Nachricht `Wetterdienst antwortete mit <Status>`.

## Abnahmekriterien

- `./gradlew test` ist grün: `WetterControllerTest` (3) spricht über einen zufälligen Port
  mit dem echten Server, `WetterAbrufTest` (2) gegen einen simulierten Wetterdienst.
- Der Controller kennt den Service nur über den Konstruktor (Lektion 01).
- `./gradlew bootRun`, dann im Browser `http://localhost:8080/api/wetter/berlin` – JSON
  erscheint; `/api/wetter/atlantis` zeigt 404.

## Hinweise

1. Server: `@RestController` an die Klasse, `@GetMapping("/api/wetter/{stadt}")` an die
   Methode, `@PathVariable String stadt` als Parameter. Rückgabetyp
   `ResponseEntity<Wetter>`: `ResponseEntity.ok(wetter)` bzw.
   `ResponseEntity.notFound().build()`. Das `Optional` des Service lässt sich mit
   `map`/`orElseGet` in genau diese beiden Antworten übersetzen (Java-Lektion 12).
2. Client: Anfrage bauen mit `HttpRequest.newBuilder(URI.create(url)).GET().build()`,
   senden mit `client.send(anfrage, HttpResponse.BodyHandlers.ofString())`; danach
   `statusCode()` prüfen, dann `new ObjectMapper().readValue(antwort.body(), Wetter.class)`.
   Das kennst du aus JUnit-Übung 05 – dort war der Server das Prüfobjekt, hier der Client.
3. Statuscodes, die du kennen solltest: 200 OK, 201 Created (nach POST), 400 Bad Request
   (Client-Fehler in der Anfrage), 404 Not Found, 500 Internal Server Error (unbehandelte
   Exception im Server – kommt nie absichtlich).
