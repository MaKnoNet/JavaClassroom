Warum so: `@BeforeEach` baut für jeden Test frische Mocks – kein Test sieht die Aufrufe
eines anderen. Jeder Test hat genau eine Absicht: der erste prüft den Erfolgspfad, der
zweite den Ablehnungspfad inklusive `never()`, der dritte nur die Weitergabe der Menge.
`when` nur dort, wo eine Antwort gebraucht wird; im dritten Test reicht die
Mockito-Vorgabe `false`. Die Datei ersetzt `src/test/java/de/makno/lernen/BestellserviceTest.java`.
