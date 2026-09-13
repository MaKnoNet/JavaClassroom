-- Prüfung zu Übung 05. "Fehler:"-Zeilen = nicht erfüllt.

SELECT 'Fehler: aufgabe_1 – Umsatz je Kunde stimmt nicht (GROUP BY k.name? nur Kunden mit Bestellung?)' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_1
              EXCEPT SELECT k.name, SUM(p.menge * a.preis) FROM kunde k
                     JOIN bestellung b ON b.kunde_id = k.id JOIN position p ON p.bestellung_id = b.id
                     JOIN artikel a ON a.id = p.artikel_id GROUP BY k.name)
   OR (SELECT COUNT(*) FROM aufgabe_1) <> 4;

SELECT 'Fehler: aufgabe_2 – erwartet wird genau Anna Adler mit 2 Bestellungen (HAVING, nicht WHERE)' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_2 EXCEPT SELECT 'Anna Adler', 2)
   OR (SELECT COUNT(*) FROM aufgabe_2) <> 1;

SELECT 'Fehler: aufgabe_3 – Artikel über dem Durchschnittspreis sind Säge und Bohrmaschine' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_3 EXCEPT SELECT name FROM artikel WHERE preis > (SELECT AVG(preis) FROM artikel))
   OR (SELECT COUNT(*) FROM aufgabe_3) <> 2;

SELECT 'Fehler: aufgabe_4 – erwartet wird eine Zeile: 4 Kunden mit E-Mail, 6 gesamt (COUNT(email) zählt keine NULL)' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_4 EXCEPT SELECT 4, 6)
   OR (SELECT COUNT(*) FROM aufgabe_4) <> 1;

SELECT 'Fehler: aufgabe_5 – über dem Durchschnitt liegen die Bestellungen 3 und 5' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_5
              EXCEPT SELECT p.bestellung_id, SUM(p.menge * a.preis) FROM position p JOIN artikel a ON a.id = p.artikel_id
                     WHERE p.bestellung_id IN (3, 5) GROUP BY p.bestellung_id)
   OR (SELECT COUNT(*) FROM aufgabe_5) <> 2;
