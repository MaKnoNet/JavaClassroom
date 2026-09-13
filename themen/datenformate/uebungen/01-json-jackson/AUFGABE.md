# Übung 01: Bestellungen als JSON – mit Jackson

## Aufgabe

`Bestellung` ist ein Record mit Datum, Betrag und Positionen. `BestellungJson` soll ihn
mit **Jackson** in JSON verwandeln und zurück – vier Methoden, alle noch Rümpfe:

1. `schreibe`/`lies`: Text hin und zurück, ohne Verlust.
2. Das Datum steht als `"2026-09-13"` im JSON, nicht als Zahl oder Array.
3. JSON aus einer fremden API darf Felder enthalten, die unser Record nicht kennt.
4. `speichere`/`lade` schreiben und lesen eine Datei – ausdrücklich in UTF-8.

## Abnahmekriterien

- `./gradlew test` ist grün (vier Tests).
- Es gibt genau **einen** `ObjectMapper` als Feld, nicht einen pro Aufruf.
- Kein `activateDefaultTyping`, kein `Object` als Zieltyp.

## Hinweise

1. Aufbau: `JsonMapper.builder().addModule(new JavaTimeModule()).disable(…).build()`.
   Ohne das Modul kennt Jackson `LocalDate` nicht (`InvalidDefinitionException`).
2. Die zwei Schalter: `SerializationFeature.WRITE_DATES_AS_TIMESTAMPS` und
   `DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES` – beide **aus**.
3. `mapper.writeValueAsString(objekt)` und `mapper.readValue(text, Bestellung.class)`.
   Records braucht Jackson nicht erklärt zu bekommen.
4. `Files.writeString(datei, text, StandardCharsets.UTF_8)` – das Charset immer
   hinschreiben, auch wo es der Standard wäre. Wer es einmal weglässt, lässt es überall
   weg, und irgendwann erwischt es einen `FileWriter` auf einem Windows-Server.
5. JSON kennt keine Kommentare – `//` im JSON ist ein Syntaxfehler, kein Stilbruch.
