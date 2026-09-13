-- Ausgangszustand für Übung 05: dieselben Daten wie in Übung 04.
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
    aktiv   BOOLEAN      NOT NULL
);

INSERT INTO kunde (id, name, email, aktiv) VALUES
    (1, 'Anna Adler',  'anna@example.org',  TRUE),
    (2, 'Ben Berger',  NULL,                TRUE),
    (3, 'Clara Cohen', 'clara@example.org', FALSE),
    (4, 'David Dahl',  NULL,                FALSE),
    (5, 'Eva Ernst',   'eva@example.org',   TRUE),
    (6, 'Fritz Falk',  'fritz@example.org', TRUE);

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

-- Eine Bestellung gehört zu genau einem Kunden (Fremdschlüssel kunde_id).
CREATE TABLE bestellung (
    id       INTEGER     PRIMARY KEY,
    kunde_id INTEGER     NOT NULL REFERENCES kunde (id),
    datum    DATE        NOT NULL,
    status   VARCHAR(20) NOT NULL
);

INSERT INTO bestellung (id, kunde_id, datum, status) VALUES
    (1, 1, '2026-01-10', 'OFFEN'),
    (2, 1, '2026-02-03', 'GELIEFERT'),
    (3, 2, '2026-02-15', 'OFFEN'),
    (4, 5, '2026-03-01', 'GELIEFERT'),
    (5, 6, '2026-03-20', 'STORNIERT');

-- Eine Position gehört zu einer Bestellung und nennt einen Artikel mit Menge.
CREATE TABLE position (
    id            INTEGER PRIMARY KEY,
    bestellung_id INTEGER NOT NULL REFERENCES bestellung (id),
    artikel_id    INTEGER NOT NULL REFERENCES artikel (id),
    menge         INTEGER NOT NULL
);

INSERT INTO position (id, bestellung_id, artikel_id, menge) VALUES
    (1, 1, 2, 2),
    (2, 1, 1, 100),
    (3, 2, 3, 1),
    (4, 3, 4, 1),
    (5, 3, 7, 3),
    (6, 4, 5, 1),
    (7, 4, 3, 1),
    (8, 5, 4, 1);
