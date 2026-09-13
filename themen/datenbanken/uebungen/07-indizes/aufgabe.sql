-- Übung 07: Sichten, Indizes, Ausführungspläne
--
-- 1. Sicht offene_bestellungen: id, kunde_id, betrag aller Bestellungen mit status 'OFFEN'.

-- 2. Schau dir VOR dem nächsten Schritt in der Konsole an, wie die Datenbank diese
--    Abfrage ausführt:
--        PostgreSQL:  EXPLAIN SELECT * FROM bestellung WHERE kunde_id = 42;
--        SQLite:      EXPLAIN QUERY PLAN SELECT * FROM bestellung WHERE kunde_id = 42;
--        H2:          EXPLAIN SELECT * FROM bestellung WHERE kunde_id = 42;
--    Du siehst "Seq Scan" / "SCAN bestellung" / "tableScan": alle 50 000 Zeilen werden gelesen.

-- 3. Lege einen Index auf bestellung(kunde_id) an. Name: idx_bestellung_kunde.

-- 4. Dieselbe EXPLAIN-Abfrage noch einmal: jetzt "Index Scan" / "SEARCH … USING INDEX" /
--    "/* PUBLIC.IDX_BESTELLUNG_KUNDE */". Notiere beide Pläne für das Gespräch.

-- 5. Zum Nachdenken (nicht geprüft): Warum legt man nicht auf jede Spalte einen Index?
--    Probiere EXPLAIN mit WHERE status = 'GELIEFERT' – hilft ein Index auf status?
