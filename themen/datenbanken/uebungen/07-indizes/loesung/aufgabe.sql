-- Lösung zu Übung 07

CREATE VIEW offene_bestellungen AS
    SELECT id, kunde_id, betrag FROM bestellung WHERE status = 'OFFEN';

-- Ein B-Baum über kunde_id: Die Datenbank springt zu den ~50 passenden Zeilen, statt
-- 50 000 zu lesen. Preis: Jeder INSERT/UPDATE/DELETE auf bestellung pflegt den Baum mit.
CREATE INDEX idx_bestellung_kunde ON bestellung (kunde_id);
