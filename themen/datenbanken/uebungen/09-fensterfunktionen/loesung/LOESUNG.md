# Lösung 09

Drei Sichten mit je einer CTE für die Gruppierung und dem Fenster darüber, siehe
`loesung/aufgabe.sql`.

Die Prüffrage der Lektion an dieser Übung: `GROUP BY a.kategorie` liefert **eine Zeile je
Kategorie** – vier. `ROW_NUMBER() OVER (PARTITION BY kategorie …)` liefert **alle acht
Artikelzeilen**, jede mit ihrem Rang innerhalb der Kategorie; erst `WHERE rang <= 3`
reduziert auf sieben (Werkzeug verliert die Säge). Aggregat fasst zusammen, Fenster
reichert an.

Ergebnisse zum Abgleich: `aufgabe_2` – Januar 78.99 (kein Vormonat), Februar 209.30
(Vormonat 78.99), März 141.37, April 166.48. `aufgabe_3` – acht Tage, laufende Summe am
Ende 596.14.

Wo das im Betrieb vorkommt: Top-N je Gruppe (Bestseller je Filiale), Vorperiodenvergleich
(Monatsbericht), kumulierte Werte (Budgetverbrauch), Deduplizieren (`ROW_NUMBER() … WHERE
rn = 1` behält je Schlüssel die neueste Zeile). Ohne Fensterfunktionen landen solche
Berechnungen im Java-Code – mit ihnen bleiben sie dort, wo die Daten sind.
