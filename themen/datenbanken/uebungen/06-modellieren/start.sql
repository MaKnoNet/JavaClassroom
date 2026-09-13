-- Ausgangszustand für Übung 06: eine einzige, unnormalisierte Tabelle – so wie sie aus einer Excel-Liste käme.
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

CREATE TABLE anmeldung (
    kurs_titel       VARCHAR(100) NOT NULL,
    kurs_plaetze     INTEGER      NOT NULL,
    teilnehmer_name  VARCHAR(100) NOT NULL,
    teilnehmer_email VARCHAR(100)
);

INSERT INTO anmeldung (kurs_titel, kurs_plaetze, teilnehmer_name, teilnehmer_email) VALUES
    ('SQL Grundlagen',     12, 'Anna Adler',  'anna@example.org'),
    ('SQL Grundlagen',     12, 'Ben Berger',  'ben@example.org'),
    ('Git für Einsteiger',  8, 'Anna Adler',  'anna@example.org'),
    ('Java Basics',        20, 'Ben Berger',  'ben@example.org'),
    ('Java Basics',        20, 'Clara Cohen', 'clara@example.org'),
    ('SQL Grundlagen',     15, 'Clara Cohen', 'clara@example.org');
