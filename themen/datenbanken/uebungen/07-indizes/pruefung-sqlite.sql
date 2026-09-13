-- Prüfung zu Übung 07, nur SQLite: Gibt es einen Index auf bestellung(kunde_id)?
SELECT 'Fehler: kein Index auf bestellung(kunde_id)' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM sqlite_master WHERE type = 'index' AND tbl_name = 'bestellung' AND sql LIKE '%kunde_id%');
