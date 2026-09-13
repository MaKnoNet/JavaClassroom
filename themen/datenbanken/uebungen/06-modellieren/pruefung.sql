-- Prüfung zu Übung 06. "Fehler:"-Zeilen = nicht erfüllt.
-- Die nächste Zeile braucht nur SQLite (dort sind Fremdschlüssel sonst aus);
-- PostgreSQL und H2 melden dafür einen Syntaxfehler und machen weiter – das ist in Ordnung.
PRAGMA foreign_keys = ON;

SELECT 'Fehler: kurs soll 3, teilnehmer 3 und teilnahme 5 Zeilen haben' AS fehler
WHERE (SELECT COUNT(*) FROM kurs) <> 3 OR (SELECT COUNT(*) FROM teilnehmer) <> 3 OR (SELECT COUNT(*) FROM teilnahme) <> 5;

-- Ab hier versucht die Prüfung absichtlich, Regeln zu verletzen. Jede dieser Anweisungen
-- MUSS mit einer Fehlermeldung scheitern; danach wird geprüft, dass nichts davon drin ist.

-- Doppelter Kurstitel:
INSERT INTO kurs (id, titel, plaetze) VALUES (9, 'SQL Grundlagen', 5);
SELECT 'Fehler: titel ist nicht eindeutig (UNIQUE fehlt)' AS fehler
WHERE (SELECT COUNT(*) FROM kurs WHERE titel = 'SQL Grundlagen') <> 1;

-- Kurs ohne Plätze:
INSERT INTO kurs (id, titel, plaetze) VALUES (10, 'Leerer Kurs', 0);
SELECT 'Fehler: plaetze darf nicht 0 sein (CHECK (plaetze > 0) fehlt)' AS fehler
WHERE EXISTS (SELECT 1 FROM kurs WHERE plaetze <= 0);

-- Doppelte E-Mail:
INSERT INTO teilnehmer (id, name, email) VALUES (9, 'Anna Zwei', 'anna@example.org');
SELECT 'Fehler: email ist nicht eindeutig (UNIQUE fehlt)' AS fehler
WHERE (SELECT COUNT(*) FROM teilnehmer WHERE email = 'anna@example.org') <> 1;

-- Teilnahme an einem Kurs, den es nicht gibt:
INSERT INTO teilnahme (kurs_id, teilnehmer_id) VALUES (99, 1);
SELECT 'Fehler: teilnahme verweist auf einen Kurs, den es nicht gibt (REFERENCES fehlt)' AS fehler
WHERE EXISTS (SELECT 1 FROM teilnahme WHERE kurs_id NOT IN (SELECT id FROM kurs));

-- Dieselbe Teilnahme zweimal:
INSERT INTO teilnahme (kurs_id, teilnehmer_id) VALUES (1, 1);
SELECT 'Fehler: dieselbe Teilnahme kann zweimal eingetragen werden (zusammengesetzter PRIMARY KEY fehlt)' AS fehler
WHERE (SELECT COUNT(*) FROM teilnahme WHERE kurs_id = 1 AND teilnehmer_id = 1) <> 1;

-- Kurs 2 wird abgesagt – seine Teilnahmen müssen von selbst verschwinden (ON DELETE CASCADE).
-- Diese Anweisung MUSS gelingen.
DELETE FROM kurs WHERE id = 2;
SELECT 'Fehler: Kurs 2 ließ sich nicht löschen oder seine Teilnahmen blieben stehen (ON DELETE CASCADE fehlt)' AS fehler
WHERE EXISTS (SELECT 1 FROM kurs WHERE id = 2) OR EXISTS (SELECT 1 FROM teilnahme WHERE kurs_id = 2);
SELECT 'Fehler: Nach dem Löschen von Kurs 2 sollen noch 4 Teilnahmen übrig sein' AS fehler
WHERE (SELECT COUNT(*) FROM teilnahme) <> 4;
