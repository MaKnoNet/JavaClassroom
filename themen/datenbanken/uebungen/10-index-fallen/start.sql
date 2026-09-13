-- Ausgangszustand für Übung 10: 20 000 Kunden, Index auf nachname ist schon da.
-- Aufräumen: alles weg, was frühere Übungen dieses Themas angelegt haben könnten.
-- Sichten zuerst (sie hängen an Tabellen), dann Tabellen in umgekehrter Abhängigkeitsfolge.
DROP VIEW IF EXISTS aufgabe_1;
DROP VIEW IF EXISTS aufgabe_2;
DROP VIEW IF EXISTS aufgabe_3;
DROP VIEW IF EXISTS aufgabe_4;
DROP VIEW IF EXISTS aufgabe_5;
DROP VIEW IF EXISTS aufgabe_6;
DROP VIEW IF EXISTS offene_bestellungen;
DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS bestellung;
DROP TABLE IF EXISTS konto;
DROP TABLE IF EXISTS teilnahme;
DROP TABLE IF EXISTS teilnehmer;
DROP TABLE IF EXISTS kurs;
DROP TABLE IF EXISTS anmeldung;
DROP TABLE IF EXISTS artikel;
DROP TABLE IF EXISTS kunde;

CREATE TABLE kunde (
    id       INTEGER      PRIMARY KEY,
    vorname  VARCHAR(100) NOT NULL,
    nachname VARCHAR(100) NOT NULL,
    email    VARCHAR(100)
);

-- Nachnamen in gemischter Schreibweise – so, wie Daten aus Formularen wirklich aussehen.
INSERT INTO kunde (id, vorname, nachname, email)
WITH RECURSIVE n(i) AS (SELECT 1 UNION ALL SELECT i + 1 FROM n WHERE i < 20000)
SELECT i,
       'Vorname' || i,
       CASE WHEN i % 3 = 0 THEN 'MUELLER' || i WHEN i % 3 = 1 THEN 'Mueller' || i ELSE 'mueller' || i END,
       'kunde' || i || '@example.org'
FROM n;

-- Der Index existiert. Die Frage dieser Übung ist, wann die Datenbank ihn trotzdem nicht nutzt.
CREATE INDEX idx_kunde_nachname ON kunde (nachname);
