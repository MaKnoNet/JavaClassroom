-- Lösung zu Übung 01

CREATE TABLE kunde (
    id      INTEGER      PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    email   VARCHAR(100),
    geboren DATE,
    aktiv   BOOLEAN      NOT NULL
);

INSERT INTO kunde (id, name, email, geboren, aktiv) VALUES (1, 'Anna Adler', 'anna@example.org', '1990-05-17', TRUE);
INSERT INTO kunde (id, name, email, geboren, aktiv) VALUES (2, 'Ben Berger', NULL, '1985-11-02', TRUE);
INSERT INTO kunde (id, name, email, geboren, aktiv) VALUES (3, 'Clara Cohen', 'clara@example.org', '2001-03-30', FALSE);
