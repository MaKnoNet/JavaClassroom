-- Prüfung zu Übung 08. "Fehler:"-Zeilen = nicht erfüllt.
-- Flyways Verlaufstabelle und ihre Spalten heißen in Kleinbuchstaben – deshalb überall in Anführungszeichen.

SELECT 'Fehler: Flyway hat nicht genau zwei Migrationen erfolgreich eingespielt (flyway info ansehen)' AS fehler
-- ("version" IS NOT NULL: Flyway trägt in manchen Datenbanken zusätzlich eine Zeile für das Anlegen des Schemas ein.)
WHERE (SELECT COUNT(*) FROM "flyway_schema_history" WHERE "success" = TRUE AND "version" IS NOT NULL) <> 2;

SELECT 'Fehler: Version 2 fehlt in der Verlaufstabelle – heißt die Datei V2__telefonnummer.sql?' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM "flyway_schema_history" WHERE "version" = '2' AND "success" = TRUE);

SELECT 'Fehler: Es sollen weiterhin sechs Kunden da sein – nichts löschen, nichts neu anlegen' AS fehler
WHERE (SELECT COUNT(*) FROM kunde) <> 6;

-- Scheitert diese Abfrage mit "Spalte nicht gefunden", fehlt die neue Spalte.
SELECT 'Fehler: Anna Adler soll die Telefonnummer +49 30 1234560 haben' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM kunde WHERE id = 1 AND telefonnummer = '+49 30 1234560');

SELECT 'Fehler: Nicht alle E-Mail-Adressen sind kleingeschrieben' AS fehler
WHERE EXISTS (SELECT 1 FROM kunde WHERE email <> LOWER(email));

SELECT 'Fehler: Ben Berger soll weiterhin keine E-Mail haben (NULL bleibt NULL)' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM kunde WHERE id = 2 AND email IS NULL);
