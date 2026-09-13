-- Prüfung zu Übung 10, nur H2: berechnete Spalte NACHNAME_KLEIN mit Index?
SELECT 'Fehler: keine indizierte Spalte nachname_klein auf kunde' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM information_schema.index_columns WHERE table_name = 'KUNDE' AND column_name = 'NACHNAME_KLEIN');
SELECT 'Fehler: nachname_klein enthält nicht LOWER(nachname)' AS fehler
WHERE EXISTS (SELECT 1 FROM kunde WHERE nachname_klein <> LOWER(nachname));
