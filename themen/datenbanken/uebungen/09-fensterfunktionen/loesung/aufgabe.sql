-- Lösung zu Übung 09

-- ROW_NUMBER() nummeriert innerhalb jeder Partition (Kategorie) neu – LIMIT könnte das nicht.
-- Die Fensterfunktion kommt erst NACH dem GROUP BY dran, deshalb die Unterabfrage/CTE.
CREATE VIEW aufgabe_1 AS
    WITH verkauft AS (
        SELECT a.kategorie, a.name AS artikel, SUM(p.menge) AS stueck
        FROM position p JOIN artikel a ON a.id = p.artikel_id
        GROUP BY a.kategorie, a.name
    ),
    nummeriert AS (
        SELECT kategorie, artikel, stueck,
               ROW_NUMBER() OVER (PARTITION BY kategorie ORDER BY stueck DESC, artikel) AS rang
        FROM verkauft
    )
    SELECT kategorie, artikel, stueck, rang FROM nummeriert WHERE rang <= 3;

-- LAG() schaut eine Zeile zurück – in der Reihenfolge, die OVER (ORDER BY …) vorgibt.
CREATE VIEW aufgabe_2 AS
    WITH monatsumsatz AS (
        SELECT SUBSTR(CAST(b.datum AS VARCHAR(10)), 1, 7) AS monat, SUM(p.menge * a.preis) AS umsatz
        FROM position p
        JOIN bestellung b ON b.id = p.bestellung_id
        JOIN artikel a ON a.id = p.artikel_id
        GROUP BY SUBSTR(CAST(b.datum AS VARCHAR(10)), 1, 7)
    )
    SELECT monat, umsatz, LAG(umsatz) OVER (ORDER BY monat) AS vormonat FROM monatsumsatz;

-- SUM() OVER (ORDER BY …) summiert vom Anfang bis zur aktuellen Zeile – die laufende Summe.
CREATE VIEW aufgabe_3 AS
    WITH tagesumsatz AS (
        SELECT b.datum, SUM(p.menge * a.preis) AS tagesumsatz
        FROM position p
        JOIN bestellung b ON b.id = p.bestellung_id
        JOIN artikel a ON a.id = p.artikel_id
        GROUP BY b.datum
    )
    SELECT datum, tagesumsatz, SUM(tagesumsatz) OVER (ORDER BY datum) AS laufende_summe FROM tagesumsatz;
