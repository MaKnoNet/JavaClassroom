-- Prüfung zu Übung 03. "Fehler:"-Zeilen = nicht erfüllt.

SELECT 'Fehler: Greta Groß (id 7) fehlt oder hat falsche Daten' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM kunde WHERE id = 7 AND name = 'Greta Groß' AND email = 'greta@example.org'
                  AND CAST(geboren AS VARCHAR(10)) = '1999-08-08' AND aktiv = TRUE);

SELECT 'Fehler: Ben Berger hat nicht die E-Mail ben@example.org' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM kunde WHERE id = 2 AND email = 'ben@example.org');
SELECT 'Fehler: Beim UPDATE der E-Mail wurden andere Kunden mit geändert (WHERE?)' AS fehler
WHERE EXISTS (SELECT 1 FROM kunde WHERE id <> 2 AND email = 'ben@example.org');

SELECT 'Fehler: David Dahl (id 4) ist noch da' AS fehler
WHERE EXISTS (SELECT 1 FROM kunde WHERE id = 4);
SELECT 'Fehler: Es wurden mehr Kunden gelöscht als David (erwartet: 6 Kunden nach Greta und David)' AS fehler
WHERE (SELECT COUNT(*) FROM kunde) <> 6;

SELECT 'Fehler: Werkzeug ist nicht 10 % teurer (Hammer soll 26.95 kosten)' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM artikel WHERE id = 3 AND ABS(preis - 26.95) < 0.001);
SELECT 'Fehler: Andere Kategorien wurden mit erhöht (Zollstock soll 4.99 bleiben)' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM artikel WHERE id = 7 AND ABS(preis - 4.99) < 0.001);

SELECT 'Fehler: Konto 1 soll 100.00 haben' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM konto WHERE id = 1 AND ABS(stand - 100) < 0.001);
SELECT 'Fehler: Konto 2 soll 70.00 haben' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM konto WHERE id = 2 AND ABS(stand - 70) < 0.001);
SELECT 'Fehler: Konto 3 wurde mit belastet – dem UPDATE fehlt das WHERE' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM konto WHERE id = 3 AND ABS(stand - 300) < 0.001);
SELECT 'Fehler: Geld ist verschwunden oder entstanden – Summe aller Konten muss 470.00 bleiben' AS fehler
WHERE ABS((SELECT SUM(stand) FROM konto) - 470) > 0.001;
