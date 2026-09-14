# Übung 08: Zustand

## Aufgabe

`Bestellung` prüft in jeder Methode per `if`, ob der Übergang erlaubt ist. Der Test bringt
einen neuen Zustand mit – **RETOURNIERT**, erreichbar nur aus VERSANDT – und verlangt,
dass jeder Zustand eine eigene Klasse ist, die ein Interface `BestellZustand` erfüllt:
`Neu`, `Bezahlt`, `Versandt`, `Storniert`, `Retourniert`. Die Bestellung delegiert an
ihren aktuellen Zustand; die Übergangsregeln wandern in die Zustandsklassen.

Zeichne zuerst das Zustandsdiagramm (steht als Kommentar im Test), dann die Klassen.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests).
- `Bestellung` enthält kein `if` über den Zustand mehr; jeder Übergang ist ein Einzeiler
  `zustand = zustand.…()`.
- Unerlaubte Übergänge werfen `IllegalStateException` – aus den Zustandsklassen bzw. dem
  Interface, nicht aus der Bestellung.

## Hinweise

1. Jeder Pfeil im Diagramm wird eine Methode im Interface, die den **Folgezustand
   zurückgibt**: `BestellZustand bezahlen()`, `versenden()`, `stornieren()`, `retournieren()`.
   Dazu `String name()` für die Anzeige.
2. `default`-Methoden im Interface, die werfen, ersparen jeder Klasse die Verbotsfälle:
   Ein Zustand überschreibt nur, was er erlaubt. `Storniert` hat dann nur noch `name()`.
3. `Bestellung`: ein Feld `BestellZustand zustand = new Neu()`, und jede Methode ist
   `zustand = zustand.bezahlen();`.
4. Kontrollfrage nach dem Umbau: Wie viele Dateien musstest du für RETOURNIERT anfassen?
   Zwei. Im Startcode wären es vier Methoden in einer Datei gewesen – und jede ein Risiko.
