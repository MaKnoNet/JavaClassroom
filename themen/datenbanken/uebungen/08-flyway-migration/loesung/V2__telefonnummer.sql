-- V2: Inkrementelle Änderung – nur das, was seit V1 dazukommt. Läuft genau einmal je
-- Datenbank; Flyway trägt sie danach in flyway_schema_history ein.

ALTER TABLE kunde ADD COLUMN telefonnummer VARCHAR(30);

-- Datenmigration: vorhandene Zeilen umformen, nicht neu anlegen. NULL bleibt NULL.
UPDATE kunde SET email = LOWER(email);

UPDATE kunde SET telefonnummer = '+49 30 1234560' WHERE id = 1;
