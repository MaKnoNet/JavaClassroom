# Übung 07: Vom Diagramm zum Code

## Aufgabe

`diagramm.puml` zeigt ein Design mit zwei Fehlern: `Gehaltsrechner` hängt an der
konkreten Klasse `Mitarbeiter`, und `Mitarbeiter` ruft den Rechner zurück – ein Zyklus.
Repariere zuerst das Diagramm (Interface `Gehaltsregel` einziehen, Zyklus auflösen,
`Firma *-- Mitarbeiter` als Komposition mit `*`), dann schreibe den Code dazu in
`src/main/java`: `Firma`, `Gehaltsregel`, `StandardRegel` (10 % Zulage), `Gehaltsrechner`.
`Mitarbeiter` ist schon da.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests): zwei prüfen das Diagramm, drei den Code per
  Reflection und Verhalten.
- `Firma` hält die Mitarbeiter als `List<Mitarbeiter>`; `Gehaltsrechner` hat ein Feld
  vom Typ `Gehaltsregel` und keins vom Typ `Mitarbeiter`; `Mitarbeiter` kennt den Rechner
  nicht.

## Hinweise

1. Diagramm zuerst – es ist die Vorlage. Jede Linie wird eine Codezeile: `*` → Liste,
   `..|>` → `implements`, `-->` → Feld, `..>` → nur Parameter.
2. `Gehaltsregel` hat eine Methode `berechne(grundgehalt: double): double`; mit
   `@FunctionalInterface` geht im Test auch ein Lambda.
3. `Firma`: `List<Mitarbeiter> mitarbeiter = new ArrayList<>()`, `stelleEin(m)`,
   `mitarbeiter()` gibt eine Kopie zurück (`List.copyOf`).
4. Der Rückpfeil `Mitarbeiter --> Gehaltsrechner` verschwindet ersatzlos: Der Rechner
   bekommt den Mitarbeiter als Parameter, der Mitarbeiter muss ihn nicht kennen.
