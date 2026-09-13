-- Prüfung zu Übung 02: Jede Sicht wird in beide Richtungen mit dem erwarteten Ergebnis
-- verglichen (EXCEPT = "was ist in A, aber nicht in B"). "Fehler:"-Zeilen = nicht erfüllt.

SELECT 'Fehler: aufgabe_1 liefert Zeilen, die nicht hineingehören (oder falsche/zu viele Spalten)' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_1 EXCEPT SELECT name, email FROM kunde WHERE aktiv = TRUE);
SELECT 'Fehler: aufgabe_1 – es fehlen aktive Kunden' AS fehler
WHERE EXISTS (SELECT name, email FROM kunde WHERE aktiv = TRUE EXCEPT SELECT * FROM aufgabe_1);

SELECT 'Fehler: aufgabe_2 liefert Artikel außerhalb 10..50 (oder falsche Spalten)' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_2 EXCEPT SELECT name, preis FROM artikel WHERE preis >= 10 AND preis <= 50);
SELECT 'Fehler: aufgabe_2 – es fehlen Artikel (Grenzen 10 und 50 gehören dazu)' AS fehler
WHERE EXISTS (SELECT name, preis FROM artikel WHERE preis >= 10 AND preis <= 50 EXCEPT SELECT * FROM aufgabe_2);

SELECT 'Fehler: aufgabe_3 liefert Kunden mit E-Mail (oder falsche Spalten)' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_3 EXCEPT SELECT name FROM kunde WHERE email IS NULL);
SELECT 'Fehler: aufgabe_3 – es fehlen Kunden ohne E-Mail (Tipp: IS NULL, nicht = NULL)' AS fehler
WHERE EXISTS (SELECT name FROM kunde WHERE email IS NULL EXCEPT SELECT * FROM aufgabe_3);

SELECT 'Fehler: aufgabe_4 soll genau die vier Kategorien liefern, jede einmal' AS fehler
WHERE (SELECT COUNT(*) FROM aufgabe_4) <> 4
   OR EXISTS (SELECT * FROM aufgabe_4 EXCEPT SELECT kategorie FROM artikel);

SELECT 'Fehler: aufgabe_5 soll genau drei Zeilen haben' AS fehler
WHERE (SELECT COUNT(*) FROM aufgabe_5) <> 3;
SELECT 'Fehler: aufgabe_5 – das sind nicht die drei teuersten Artikel' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_5 EXCEPT SELECT name, preis FROM artikel WHERE preis >= 31);

SELECT 'Fehler: aufgabe_6 liefert Artikel, die nicht mit S beginnen (oder falsche Spalten)' AS fehler
WHERE EXISTS (SELECT * FROM aufgabe_6 EXCEPT SELECT name FROM artikel WHERE name LIKE 'S%');
SELECT 'Fehler: aufgabe_6 – es fehlen Artikel mit S (auch Säge!)' AS fehler
WHERE EXISTS (SELECT name FROM artikel WHERE name LIKE 'S%' EXCEPT SELECT * FROM aufgabe_6);
