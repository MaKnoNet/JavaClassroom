Die Datei ersetzt `src/main/java/de/makno/lernen/BestellungJson.java`.

Warum so: Jackson bildet Records direkt ab – die Komponenten werden zu JSON-Feldern, der
kanonische Konstruktor zum Weg zurück; kein Setter, kein leerer Konstruktor. Drei
Einstellungen machen den Mapper alltagstauglich: Das `JavaTimeModule` lehrt ihn
`LocalDate` (ohne Modul: Fehler beim Schreiben), `WRITE_DATES_AS_TIMESTAMPS` aus, damit
ein Datum als ISO-Text erscheint und nicht als `[2026,9,13]` – lesbar und unabhängig
von der Sprache auf der anderen Seite. `FAIL_ON_UNKNOWN_PROPERTIES` aus, weil fremde APIs
Felder hinzufügen dürfen, ohne dass unser Import stirbt (*tolerant reader*). Was das
bewusst **nicht** tut: Typen aus dem JSON bestimmen (`activateDefaultTyping`) – das
Ziel ist immer eine feste Klasse, nie `Object`; sonst kann der Absender vorschreiben,
welche Klasse instanziiert wird (Lektion 05). Und Dateien immer mit
`StandardCharsets.UTF_8` – `Files.readString` ohne Charset ist zwar UTF-8, aber
`FileReader`/`FileWriter` ohne Charset nehmen bis Java 17 das Betriebssystem: Windows
`Cp1252`, und aus „Müller" wird „MÃ¼ller".
