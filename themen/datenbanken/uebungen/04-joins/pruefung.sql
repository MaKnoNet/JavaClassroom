-- Prüfung zu Übung 04. "Fehler:"-Zeilen = nicht erfüllt.

SELECT 'Fehler: aufgabe_1 soll genau fünf Zeilen haben – eine je Bestellung' AS fehler
WHERE (SELECT COUNT(*) FROM aufgabe_1) <> 5;
SELECT 'Fehler: aufgabe_1 – Bestellung und Kunde passen nicht zusammen (oder falsche Spalten)' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_1
              EXCEPT SELECT b.id, b.datum, k.name FROM bestellung b JOIN kunde k ON k.id = b.kunde_id);

SELECT 'Fehler: aufgabe_2 – erwartet werden genau die Kunden ohne Bestellung (Clara, David)' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_2 EXCEPT SELECT name FROM kunde WHERE id NOT IN (SELECT kunde_id FROM bestellung))
   OR EXISTS (SELECT name FROM kunde WHERE id NOT IN (SELECT kunde_id FROM bestellung) EXCEPT SELECT * FROM aufgabe_2);

SELECT 'Fehler: aufgabe_3 – Positionen von Bestellung 1 mit Artikelname, Menge und Zeilensumme stimmen nicht' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_3
              EXCEPT SELECT a.name, p.menge, p.menge * a.preis FROM position p JOIN artikel a ON a.id = p.artikel_id WHERE p.bestellung_id = 1)
   OR (SELECT COUNT(*) FROM aufgabe_3) <> 2;

SELECT 'Fehler: aufgabe_4 – Kunde/Artikel-Paare stimmen nicht oder kommen doppelt vor' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_4
              EXCEPT SELECT DISTINCT k.name, a.name FROM position p JOIN bestellung b ON b.id = p.bestellung_id
                     JOIN kunde k ON k.id = b.kunde_id JOIN artikel a ON a.id = p.artikel_id)
   OR (SELECT COUNT(*) FROM aufgabe_4) <> 8;

SELECT 'Fehler: aufgabe_5 – Kunden ohne Bestellung fehlen (LEFT JOIN?) oder Paare stimmen nicht' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_5
              EXCEPT SELECT k.name, b.status FROM kunde k LEFT JOIN bestellung b ON b.kunde_id = k.id)
   OR (SELECT COUNT(*) FROM aufgabe_5) <> 7;
