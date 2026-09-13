-- Ausgangszustand für Übung 07: 1 000 Kunden und 50 000 Bestellungen – genug, dass ein Index etwas ausmacht.
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
    id   INTEGER      PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Eine rekursive CTE zählt von 1 bis 1000 – so erzeugt man Testdaten ohne 1000 INSERT-Zeilen.
INSERT INTO kunde (id, name)
WITH RECURSIVE n(i) AS (SELECT 1 UNION ALL SELECT i + 1 FROM n WHERE i < 1000)
SELECT i, 'Kunde ' || i FROM n;

-- kunde_id bewusst OHNE Fremdschlüssel: H2 und MySQL legen für Fremdschlüssel von selbst
-- einen Index an, PostgreSQL und SQLite nicht. So sehen alle dasselbe – erst ohne, dann mit Index.
CREATE TABLE bestellung (
    id       INTEGER       PRIMARY KEY,
    kunde_id INTEGER       NOT NULL,
    datum    DATE          NOT NULL,
    status   VARCHAR(20)   NOT NULL,
    betrag   NUMERIC(10,2) NOT NULL
);

INSERT INTO bestellung (id, kunde_id, datum, status, betrag)
WITH RECURSIVE n(i) AS (SELECT 1 UNION ALL SELECT i + 1 FROM n WHERE i < 50000)
SELECT i,
       (i % 1000) + 1,
       '2026-01-01',
       CASE WHEN i % 10 = 0 THEN 'OFFEN' ELSE 'GELIEFERT' END,
       (i % 500) + 0.99
FROM n;
