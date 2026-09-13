-- Prüfung zu Übung 01. Jede Zeile in der Ausgabe, die mit "Fehler:" beginnt, ist ein
-- nicht erfülltes Kriterium. Keine "Fehler:"-Zeile und keine Fehlermeldung = bestanden.

SELECT 'Fehler: kunde soll genau drei Zeilen haben' AS fehler
WHERE (SELECT COUNT(*) FROM kunde) <> 3;

SELECT 'Fehler: Anna Adler mit id 1 fehlt oder hat die falsche E-Mail' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM kunde WHERE id = 1 AND name = 'Anna Adler' AND email = 'anna@example.org');

SELECT 'Fehler: Ben Berger soll keine E-Mail haben (NULL, nicht leerer Text)' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM kunde WHERE id = 2 AND name = 'Ben Berger' AND email IS NULL);

SELECT 'Fehler: Clara Cohen soll nicht aktiv sein' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM kunde WHERE id = 3 AND name = 'Clara Cohen' AND aktiv = FALSE);

SELECT 'Fehler: Geburtsdatum von Anna ist nicht 1990-05-17' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM kunde WHERE id = 1 AND CAST(geboren AS VARCHAR(10)) = '1990-05-17');

-- Die nächsten Anweisungen MÜSSEN scheitern (Fehlermeldung ist hier richtig):
-- Pflichtfeld name ohne Wert …
INSERT INTO kunde (id, name, email, geboren, aktiv) VALUES (4, NULL, NULL, '2000-01-01', TRUE);
-- … und ein zweiter Kunde mit der id 1.
INSERT INTO kunde (id, name, email, geboren, aktiv) VALUES (1, 'Doppelt', NULL, '2000-01-01', TRUE);

SELECT 'Fehler: name ist kein Pflichtfeld (NOT NULL fehlt)' AS fehler
WHERE EXISTS (SELECT 1 FROM kunde WHERE name IS NULL);

SELECT 'Fehler: id ist kein Primärschlüssel (Duplikat wurde angenommen)' AS fehler
WHERE (SELECT COUNT(*) FROM kunde WHERE id = 1) <> 1;
