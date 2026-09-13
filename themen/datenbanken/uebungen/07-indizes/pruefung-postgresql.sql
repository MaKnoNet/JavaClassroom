-- Prüfung zu Übung 07, nur PostgreSQL: Gibt es einen Index auf bestellung(kunde_id)?
SELECT 'Fehler: kein Index auf bestellung(kunde_id)' AS fehler
WHERE NOT EXISTS (SELECT 1 FROM pg_indexes WHERE tablename = 'bestellung' AND indexdef LIKE '%(kunde_id)%');
