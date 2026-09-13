# Übung 09: Fensterfunktionen

## Aufgabe

Drei Controlling-Berichte als Sichten: die drei meistverkauften Artikel **je Kategorie**,
der Monatsumsatz **neben** dem Vormonat, der Tagesumsatz mit **laufender Summe**. Alle
drei haben gemeinsam, dass `GROUP BY` allein nicht reicht: Es fasst Zeilen zusammen, aber
es kann nicht „innerhalb jeder Gruppe nummerieren", „eine Zeile zurückschauen" oder „bis
hierher aufsummieren". Das tun Fensterfunktionen mit `OVER (…)`. Der Anfang von
`aufgabe_1` zeigt den typischen Fehlversuch: `LIMIT 3` über alles statt je Kategorie.
Ausführen: siehe [AUSFUEHREN.md](../../AUSFUEHREN.md).

## Abnahmekriterien

- `pruefung.sql` gibt keine `Fehler:`-Zeile und keine Fehlermeldung aus.
- Jede Sicht nutzt eine Fensterfunktion (`ROW_NUMBER`, `LAG`, `SUM … OVER`).

## Hinweise

1. Reihenfolge der Auswertung: Fensterfunktionen laufen **nach** `GROUP BY` und `HAVING`,
   aber **vor** `ORDER BY`. Deshalb erst gruppieren (in einer CTE oder Unterabfrage), dann
   darüber das Fenster legen.
2. `ROW_NUMBER() OVER (PARTITION BY kategorie ORDER BY stueck DESC, artikel)` – `PARTITION BY`
   startet die Nummerierung je Kategorie neu, `ORDER BY` bestimmt, wer die 1 bekommt. Das
   zweite Sortierkriterium macht die Vergabe eindeutig. `WHERE rang <= 3` geht erst in der
   nächsten Schicht – in derselben `SELECT`-Ebene ist `rang` noch nicht bekannt.
3. `LAG(umsatz) OVER (ORDER BY monat)` liefert den Wert der vorigen Zeile in dieser
   Reihenfolge; für die erste Zeile `NULL`. `LEAD` schaut nach vorn.
4. `SUM(tagesumsatz) OVER (ORDER BY datum)` summiert vom ersten Tag bis zur aktuellen
   Zeile – ohne `PARTITION BY` ist das ganze Ergebnis ein Fenster.
5. Monat aus dem Datum: `SUBSTR(CAST(datum AS VARCHAR(10)), 1, 7)` liefert `'2026-01'` in
   PostgreSQL, H2 und SQLite gleichermaßen. Die eleganteren Wege sind dialektabhängig
   (`date_trunc`, `EXTRACT`, `strftime`) – merken, hier nicht nötig.
6. `RANK()` statt `ROW_NUMBER()` vergibt bei Gleichstand dieselbe Nummer (1, 1, 3);
   `DENSE_RANK()` ohne Lücke (1, 1, 2). Die Prüfung erwartet `ROW_NUMBER` mit eindeutiger
   Sortierung.
