-- Prüfung zu Übung 07, nur H2: Gibt es einen Index auf bestellung(kunde_id)?
-- H2 speichert unquotierte Namen in Großbuchstaben – deshalb BESTELLUNG und KUNDE_ID.
SELECT 'Fehler: kein Index auf bestellung(kunde_id)' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM information_schema.index_columns WHERE table_name = 'BESTELLUNG' AND column_name = 'KUNDE_ID');
