-- Lösung zu Übung 10 – PostgreSQL und SQLite:
-- Ein Index über den Ausdruck. Die Datenbank berechnet LOWER(nachname) beim Schreiben
-- und legt das Ergebnis in den Baum; WHERE LOWER(nachname) = … trifft dann exakt den Index.
CREATE INDEX idx_kunde_nachname_klein ON kunde (LOWER(nachname));
