-- Prüfung zu Übung 10, nur PostgreSQL: Gibt es einen Index über lower(nachname)?
SELECT 'Fehler: kein Index über LOWER(nachname) auf kunde' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM pg_indexes WHERE tablename = 'kunde' AND LOWER(indexdef) LIKE '%lower(%nachname%');
