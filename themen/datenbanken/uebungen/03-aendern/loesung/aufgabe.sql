-- Lösung zu Übung 03

INSERT INTO kunde (id, name, email, geboren, aktiv)
    VALUES (7, 'Greta Groß', 'greta@example.org', '1999-08-08', TRUE);

UPDATE kunde SET email = 'ben@example.org' WHERE id = 2;

DELETE FROM kunde WHERE id = 4;

UPDATE artikel SET preis = preis * 1.10 WHERE kategorie = 'Werkzeug';

-- Beide Buchungen oder keine: Fällt zwischen den UPDATEs etwas aus, nimmt ROLLBACK die
-- erste zurück. Ohne BEGIN wäre jede Anweisung für sich sofort endgültig.
BEGIN;
UPDATE konto SET stand = stand - 50 WHERE id = 1;
UPDATE konto SET stand = stand + 50 WHERE id = 2;
COMMIT;
