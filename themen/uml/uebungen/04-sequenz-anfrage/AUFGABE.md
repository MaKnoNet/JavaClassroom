# Übung 04: Sequenzdiagramm einer Anfrage

## Aufgabe

`diagramm.puml` beschreibt einen HTTP-Aufruf in Worten. Zeichne ihn: Teilnehmer in
Aufrufreihenfolge (Client als `actor`), die drei synchronen Aufrufe mit Nachrichtentext,
die drei Antworten gestrichelt zurück, und Aktivierungsbalken für jede Schicht.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests): Diagrammtyp, Reihenfolge der Teilnehmer, die
  drei Aufrufe mit exaktem Text, die Antworten, `activate`/`deactivate` paarweise.

## Hinweise

1. Teilnehmer deklarieren: `actor Client`, `participant KundenController` … – die
   Reihenfolge der Deklaration ist die Reihenfolge im Bild.
2. Aufruf: `Client -> KundenController : GET /api/kunden/1`. Antwort: `KundenController --> Client : 200 OK`.
3. `activate KundenController` direkt nach dem Aufruf, `deactivate` nach der Antwort.
   Schachtele: Der Controller bleibt aktiv, solange der Service arbeitet.
4. Rendern und hinschauen: Der Client hat keinen Balken – er wartet. Genau das ist der
   Sinn der Balken.
