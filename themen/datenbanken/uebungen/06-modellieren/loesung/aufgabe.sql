-- Lösung zu Übung 06

CREATE TABLE kurs (
    id      INTEGER      PRIMARY KEY,
    titel   VARCHAR(100) NOT NULL UNIQUE,
    plaetze INTEGER      NOT NULL CHECK (plaetze > 0)
);

CREATE TABLE teilnehmer (
    id    INTEGER      PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE
);

-- n:m – ein Kurs hat viele Teilnehmer, ein Teilnehmer viele Kurse: dazwischen eine
-- Verbindungstabelle, deren Primärschlüssel aus beiden Fremdschlüsseln besteht.
CREATE TABLE teilnahme (
    kurs_id       INTEGER NOT NULL REFERENCES kurs (id) ON DELETE CASCADE,
    teilnehmer_id INTEGER NOT NULL REFERENCES teilnehmer (id),
    PRIMARY KEY (kurs_id, teilnehmer_id)
);

INSERT INTO kurs (id, titel, plaetze) VALUES
    (1, 'SQL Grundlagen', 12),
    (2, 'Git für Einsteiger', 8),
    (3, 'Java Basics', 20);

INSERT INTO teilnehmer (id, name, email) VALUES
    (1, 'Anna Adler', 'anna@example.org'),
    (2, 'Ben Berger', 'ben@example.org'),
    (3, 'Clara Cohen', 'clara@example.org');

INSERT INTO teilnahme (kurs_id, teilnehmer_id) VALUES
    (1, 1), (1, 2), (2, 1), (3, 2), (3, 3);
