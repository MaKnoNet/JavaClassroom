-- Lösung zu Übung 10 – H2 (kennt keine Ausdrucksindizes):
-- Eine berechnete Spalte hält LOWER(nachname) dauerhaft vor; darauf ein normaler Index.
-- Die Abfrage muss dann die Spalte nutzen: WHERE nachname_klein = 'mueller421'.
ALTER TABLE kunde ADD COLUMN nachname_klein VARCHAR(100) GENERATED ALWAYS AS (LOWER(nachname));
CREATE INDEX idx_kunde_nachname_klein ON kunde (nachname_klein);
