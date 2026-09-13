-- Ausgangszustand für Übung 09: Artikel, Bestellungen über vier Monate, Positionen.
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

CREATE TABLE artikel (
    id        INTEGER       PRIMARY KEY,
    name      VARCHAR(100)  NOT NULL,
    kategorie VARCHAR(50)   NOT NULL,
    preis     NUMERIC(10,2) NOT NULL
);

INSERT INTO artikel (id, name, kategorie, preis) VALUES
    (1, 'Schraube M4',     'Kleinteile', 0.05),
    (2, 'Schraubendreher', 'Werkzeug',   12.90),
    (3, 'Hammer',          'Werkzeug',   24.50),
    (4, 'Bohrmaschine',    'Elektro',    89.00),
    (5, 'Säge',            'Werkzeug',   31.00),
    (6, 'Mutter M4',       'Kleinteile', 0.03),
    (7, 'Zollstock',       'Messen',     4.99),
    (8, 'Zange',           'Werkzeug',   15.00);

CREATE TABLE bestellung (
    id    INTEGER PRIMARY KEY,
    datum DATE    NOT NULL
);

INSERT INTO bestellung (id, datum) VALUES
    (1,  '2026-01-10'), (2,  '2026-01-22'),
    (3,  '2026-02-03'), (4,  '2026-02-14'), (5, '2026-02-14'),
    (6,  '2026-03-01'), (7,  '2026-03-15'),
    (8,  '2026-04-02'), (9,  '2026-04-20'), (10, '2026-04-20');

CREATE TABLE position (
    id            INTEGER PRIMARY KEY,
    bestellung_id INTEGER NOT NULL REFERENCES bestellung (id),
    artikel_id    INTEGER NOT NULL REFERENCES artikel (id),
    menge         INTEGER NOT NULL
);

INSERT INTO position (id, bestellung_id, artikel_id, menge) VALUES
    (1,  1, 3, 2), (2,  1, 1, 200),
    (3,  2, 8, 1), (4,  2, 7, 1),
    (5,  3, 4, 1), (6,  3, 3, 1),
    (7,  4, 2, 2), (8,  4, 6, 300),
    (9,  5, 8, 2), (10, 5, 5, 1),
    (11, 6, 3, 1), (12, 6, 2, 1),
    (13, 7, 4, 1), (14, 7, 7, 3),
    (15, 8, 8, 1), (16, 8, 3, 1),
    (17, 9, 1, 500), (18, 9, 4, 1),
    (19, 10, 7, 2), (20, 10, 6, 100);
