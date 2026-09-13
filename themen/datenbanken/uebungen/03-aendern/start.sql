-- Ausgangszustand für Übung 03: Kunden, Konten, Artikel. Beliebig oft wiederholbar.
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
    id      INTEGER      PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    email   VARCHAR(100),
    geboren DATE,
    aktiv   BOOLEAN      NOT NULL
);

INSERT INTO kunde (id, name, email, geboren, aktiv) VALUES
    (1, 'Anna Adler',  'anna@example.org',  '1990-05-17', TRUE),
    (2, 'Ben Berger',  NULL,                '1985-11-02', TRUE),
    (3, 'Clara Cohen', 'clara@example.org', '2001-03-30', FALSE),
    (4, 'David Dahl',  NULL,                '1978-07-09', FALSE),
    (5, 'Eva Ernst',   'eva@example.org',   '1995-12-24', TRUE),
    (6, 'Fritz Falk',  'fritz@example.org', '1969-01-15', TRUE);

CREATE TABLE konto (
    id       INTEGER       PRIMARY KEY,
    kunde_id INTEGER       NOT NULL REFERENCES kunde (id),
    stand    NUMERIC(10,2) NOT NULL
);

INSERT INTO konto (id, kunde_id, stand) VALUES
    (1, 1, 150.00),
    (2, 2, 20.00),
    (3, 5, 300.00);

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
    (7, 'Zollstock',       'Messen',     4.99);
