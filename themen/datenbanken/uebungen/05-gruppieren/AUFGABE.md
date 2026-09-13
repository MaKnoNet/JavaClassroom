# Übung 05: Zusammenfassen

## Aufgabe

Dieselben Daten wie in Übung 04. Fünf Sichten: Umsatz je Kunde, Kunden mit mehr als einer
Bestellung, Artikel über dem Durchschnittspreis, Zählen mit und ohne `NULL`, und
Bestellungen über dem Durchschnittswert (mit `WITH`). `aufgabe_1` beginnt ohne `GROUP BY` –
PostgreSQL und H2 lehnen das ab, SQLite liefert stillschweigend eine falsche Zeile.
Ausführen: siehe [AUSFUEHREN.md](../../AUSFUEHREN.md).

## Abnahmekriterien

- `pruefung.sql` gibt keine `Fehler:`-Zeile und keine Fehlermeldung aus.
- `aufgabe_2` nutzt `HAVING`, nicht eine Unterabfrage im `WHERE`.

## Hinweise

1. Aggregatfunktionen fassen viele Zeilen zu einer zusammen: `SUM`, `COUNT`, `AVG`, `MIN`,
   `MAX`. Jede Spalte im `SELECT`, die *nicht* in einer Aggregatfunktion steht, muss ins
   `GROUP BY` – das ist der Fehler in `aufgabe_1`.
2. `WHERE` filtert Zeilen **vor** dem Gruppieren, `HAVING` filtert Gruppen **danach**:
   `HAVING COUNT(*) > 1`.
3. Eine Unterabfrage in Klammern liefert einen Wert: `WHERE preis > (SELECT AVG(preis) FROM artikel)`.
4. `COUNT(*)` zählt Zeilen, `COUNT(email)` nur die, in denen `email` nicht `NULL` ist –
   dieselbe Regel gilt für `SUM`, `AVG`: `NULL` wird übersprungen, nicht als 0 gezählt.
5. `WITH bestellwert AS (SELECT … GROUP BY …) SELECT … FROM bestellwert WHERE …` – ein
   benanntes Zwischenergebnis (CTE, *Common Table Expression*). Man kann es in derselben
   Abfrage mehrfach verwenden, z. B. für den Durchschnitt *und* den Vergleich.
