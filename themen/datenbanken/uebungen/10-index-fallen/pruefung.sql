-- Prüfung zu Übung 10 (dialektunabhängiger Teil). Den Index prüft pruefung-<datenbank>.sql.

SELECT 'Fehler: Die Daten wurden verändert – es sollen 20 000 Kunden sein und Mueller421 existieren' AS fehler
WHERE (SELECT COUNT(*) FROM kunde) <> 20000
   OR NOT EXISTS (SELECT 1 FROM kunde WHERE LOWER(nachname) = 'mueller421');
