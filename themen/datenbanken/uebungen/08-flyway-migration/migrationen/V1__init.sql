-- V1: Das erste Schema, so wie es auf dem Server schon läuft. Diese Datei wird NIE mehr
-- verändert – Flyway merkt sich ihre Prüfsumme.
CREATE TABLE kunde (
    id    INTEGER      PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    aktiv BOOLEAN      NOT NULL
);

INSERT INTO kunde (id, name, email, aktiv) VALUES
    (1, 'Anna Adler',  'Anna@Example.org',  TRUE),
    (2, 'Ben Berger',  NULL,                TRUE),
    (3, 'Clara Cohen', 'CLARA@example.org', FALSE),
    (4, 'David Dahl',  NULL,                FALSE),
    (5, 'Eva Ernst',   'eva@example.org',   TRUE),
    (6, 'Fritz Falk',  'Fritz@Example.ORG', TRUE);
