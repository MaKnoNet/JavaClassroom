Warum so: Der Server läuft einmal pro Klasse (`@BeforeAll`), nicht pro Test – Starten
ist teuer, die Tests ändern keinen Zustand. `hole()` bündelt den HTTP-Aufruf, sodass jeder
Test nur noch Status und Inhalt prüft. Geprüft wird ausschließlich die Schnittstelle:
Ersetzt jemand die Implementierung, bleiben die Tests gültig. In Projekten übernimmt
RestAssured diese Hilfsmethode mit lesbarerer Syntax. Die Datei ersetzt
`src/test/java/de/makno/lernen/ArtikelApiTest.java`.
