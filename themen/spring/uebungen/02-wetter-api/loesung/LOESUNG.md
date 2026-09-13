Zwei Dateien ersetzen ihre Gegenstücke in `src/main/java/de/makno/lernen/`.

Warum so: `@RestController` sagt Spring „die Rückgabewerte sind die Antwort, nicht der
Name einer HTML-Seite" – Jackson wandelt das `Wetter`-Record in JSON, weil das Record
öffentliche Zugriffsmethoden hat. `ResponseEntity` ist nötig, sobald der Statuscode vom
Ergebnis abhängt; ein nacktes `Wetter` als Rückgabe könnte nur 200 (oder 500). Das
`Optional` aus dem Service wird nicht ausgepackt, sondern mit `map`/`orElseGet` direkt in
die zwei Antworten übersetzt. Im Client prüft die Lösung den Status *vor* dem Parsen: Ein
404 mit leerem Körper würde sonst als unverständliches JSON-Fehlerbild erscheinen, nicht
als das, was es ist. `HttpClient` und `ObjectMapper` sind teuer im Aufbau und thread-sicher
– deshalb Felder, nicht lokale Variablen pro Aufruf.
