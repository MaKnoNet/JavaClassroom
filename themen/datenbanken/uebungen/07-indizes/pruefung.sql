-- Prüfung zu Übung 07 (dialektunabhängiger Teil). "Fehler:"-Zeilen = nicht erfüllt.
-- Den Index prüft die Datei pruefung-<datenbank>.sql, weil jede Datenbank ihren Katalog
-- anders nennt.

SELECT 'Fehler: offene_bestellungen soll 5000 Zeilen haben' AS fehler
WHERE (SELECT COUNT(*) FROM offene_bestellungen) <> 5000;
SELECT 'Fehler: offene_bestellungen enthält falsche Zeilen oder falsche Spalten' AS fehler
WHERE EXISTS (SELECT * FROM offene_bestellungen EXCEPT SELECT id, kunde_id, betrag FROM bestellung WHERE status = 'OFFEN');
