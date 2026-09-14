# Übung 03: Beziehungen

## Aufgabe

`diagramm.puml` enthält sieben Elemente und einen Kommentar, der ihre Beziehungen in
Worten beschreibt. Zeichne die sechs Linien – jede mit der richtigen Art (Vererbung,
Realisierung, Aggregation, Komposition, Assoziation), Richtung und Multiplizität.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests): Pfeilarten, Richtungen und Multiplizitäten.
- Im gerenderten Diagramm zeigen die Dreiecke auf Person und Zahlungsfaehig, die Rauten
  sitzen bei Kunde und Bestellung.

## Hinweise

1. PlantUML-Pfeile: `--|>` Vererbung, `..|>` Realisierung, `o--` Aggregation, `*--`
   Komposition, `-->` gerichtete Assoziation. Die Raute steht am Ganzen, das Dreieck an
   der Abstraktion.
2. Multiplizitäten in Anführungszeichen an beiden Enden: `Kunde "1" --> "*" Bestellung`.
3. „Die Adresse bleibt bestehen, wenn der Kunde geht" → Aggregation. „Ohne Bestellung keine
   Position" → Komposition. Das ist die Prüffrage der Lektion, als Linie.
4. Reihenfolge ist egal; PlantUML akzeptiert auch `Person <|-- Kunde`. Wichtig ist, wo das
   Dreieck landet.
