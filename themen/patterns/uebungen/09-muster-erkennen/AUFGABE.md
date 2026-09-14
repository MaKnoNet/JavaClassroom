# Übung 09: Muster erkennen, Muster vermeiden

## Aufgabe

**Teil 1 – erkennen.** `MusterQuiz.musterVon` bekommt acht Code-Stellen aus JDK und
Spring und soll das Muster nennen, das darin steckt. Alle Antworten sind im Startcode
`null`. Trage sie ein – und schreib zu jeder in einem Kommentar, *woran* du es erkennst.

**Teil 2 – vermeiden.** Die Begrüßung der Anwendung läuft über
`BegruessungStrategieFactory.getInstance().erzeugeStrategie().begruesse(name)`: Singleton,
Fabrik, Strategie – für eine Zeile Text, für die es nie eine zweite Variante gab. Baue
es zurück: eine Klasse `Begruessung` mit `begruesse(name)`, die `Anwendung` benutzt sie
direkt.

## Abnahmekriterien

- `./gradlew test` ist grün (drei Tests).
- Jede Zuordnung in `MusterQuiz` hat einen Kommentar mit dem Erkennungsmerkmal.
- `Anwendung` hat kein Feld mehr, dessen Typ „Factory" oder „Strategie" im Namen trägt;
  die drei Overkill-Klassen sind gelöscht.

## Hinweise

1. Frag bei jeder Stelle: Wird *Verhalten ausgetauscht* (Strategie)? *Umhüllt* etwas
   denselben Typ (Dekorierer/Proxy)? Wird *schrittweise erzeugt* (Builder)? *Registriert*
   sich jemand für Ereignisse (Beobachter)? Ist ein *Ablauf fest* und ein Schritt
   variabel (Schablonenmethode)?
2. Dekorierer und Proxy sehen gleich aus – der Unterschied ist die Absicht: Der Dekorierer
   ergänzt sichtbares Verhalten (Puffern), der Proxy kontrolliert den Zugriff (Transaktion,
   Lazy Loading) und will unsichtbar sein.
3. `List.of` ist keine Fabrikmethode im strengen GoF-Sinn (keine Unterklasse entscheidet),
   sondern eine *statische* Fabrikmethode – für diese Übung zählt beides als
   `FABRIKMETHODE`.
4. Teil 2: Bevor du löschst, überlege, was das Muster *hätte* bringen sollen, und ob diese
   Änderung je kam. Das ist die Frage aus Lektion 02 – jetzt in die andere Richtung.
