-- Prüfung zu Übung 09. "Fehler:"-Zeilen = nicht erfüllt.

SELECT 'Fehler: aufgabe_1 – erwartet werden je Kategorie höchstens drei Artikel mit Rang 1..3 (Werkzeug hat vier Artikel!)' AS fehler
WHERE EXISTS (
    SELECT * FROM aufgabe_1
    EXCEPT
    SELECT kategorie, artikel, stueck, rang FROM (
        SELECT a.kategorie, a.name AS artikel, SUM(p.menge) AS stueck,
               ROW_NUMBER() OVER (PARTITION BY a.kategorie ORDER BY SUM(p.menge) DESC, a.name) AS rang
        FROM position p JOIN artikel a ON a.id = p.artikel_id
        GROUP BY a.kategorie, a.name
    ) t WHERE rang <= 3)
   OR (SELECT COUNT(*) FROM aufgabe_1) <> 7;

SELECT 'Fehler: aufgabe_2 – Monat, Umsatz und Vormonatsumsatz stimmen nicht (LAG über ORDER BY monat; erster Monat NULL)' AS fehler
WHERE EXISTS (
    SELECT * FROM aufgabe_2
    EXCEPT
    SELECT monat, umsatz, LAG(umsatz) OVER (ORDER BY monat) FROM (
        SELECT SUBSTR(CAST(b.datum AS VARCHAR(10)), 1, 7) AS monat, SUM(p.menge * a.preis) AS umsatz
        FROM position p JOIN bestellung b ON b.id = p.bestellung_id JOIN artikel a ON a.id = p.artikel_id
        GROUP BY SUBSTR(CAST(b.datum AS VARCHAR(10)), 1, 7)
    ) m)
   OR (SELECT COUNT(*) FROM aufgabe_2) <> 4;

SELECT 'Fehler: aufgabe_3 – Tagesumsatz oder laufende Summe stimmen nicht (SUM(…) OVER (ORDER BY datum))' AS fehler
WHERE EXISTS (
    SELECT * FROM aufgabe_3
    EXCEPT
    SELECT datum, tagesumsatz, SUM(tagesumsatz) OVER (ORDER BY datum) FROM (
        SELECT b.datum, SUM(p.menge * a.preis) AS tagesumsatz
        FROM position p JOIN bestellung b ON b.id = p.bestellung_id JOIN artikel a ON a.id = p.artikel_id
        GROUP BY b.datum
    ) t)
   OR (SELECT COUNT(*) FROM aufgabe_3) <> 8;
