-- Prüfung zu Übung 10, nur SQLite: Gibt es einen Index über lower(nachname)?
SELECT 'Fehler: kein Index über LOWER(nachname) auf kunde' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM sqlite_master WHERE type = 'index' AND tbl_name = 'kunde' AND LOWER(sql) LIKE '%lower(nachname)%');
