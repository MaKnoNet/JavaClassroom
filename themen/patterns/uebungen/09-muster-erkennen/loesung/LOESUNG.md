# Lösung 09

**Teil 1** – die Zuordnungen stehen mit Begründung als Kommentar in `MusterQuiz.java`.
Die Begründung ist wichtiger als der Name: Woran erkennt man den Dekorierer in
`new BufferedReader(new InputStreamReader(…))`? Beide sind `Reader`, beide umhüllen einen
`Reader` – Interface behalten, Verhalten ergänzt. Woran den Proxy bei `@Transactional`?
Der Aufrufer bekommt ein Objekt vom selben Typ, das *vor* der eigentlichen Methode etwas
tut – und genau deshalb greift die Annotation beim Selbstaufruf nicht (Lektion 10).

**Teil 2** – `Begruessung` mit einer Methode, `Anwendung` benutzt sie direkt. Die vier
Overkill-Klassen (`BegruessungStrategieFactory`, `BegruessungStrategie`,
`StandardBegruessungStrategie`) gehören gelöscht; sie bleiben im Lösungsordner nur weg,
weil die Prüfung Dateien überlagert statt entfernt.

Die Frage, die Overkill entlarvt: „Welche Änderung erwarten wir, und macht das Muster sie
billiger?" Für die Begrüßung gab es nie eine zweite Variante – die Strategie hatte einen
Implementierer, die Fabrik ein Produkt, das Singleton keinen Zustand. Drei Muster, null
Nutzen, vier Dateien mehr zu lesen. Wer ein Muster einzieht, sollte die zweite Variante
nennen können, die es braucht.
