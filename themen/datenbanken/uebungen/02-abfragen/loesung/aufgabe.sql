-- Lösung zu Übung 02

CREATE VIEW aufgabe_1 AS
    SELECT name, email FROM kunde WHERE aktiv = TRUE;

CREATE VIEW aufgabe_2 AS
    SELECT name, preis FROM artikel WHERE preis BETWEEN 10 AND 50;

CREATE VIEW aufgabe_3 AS
    SELECT name FROM kunde WHERE email IS NULL;

CREATE VIEW aufgabe_4 AS
    SELECT DISTINCT kategorie FROM artikel;

CREATE VIEW aufgabe_5 AS
    SELECT name, preis FROM artikel ORDER BY preis DESC LIMIT 3;

CREATE VIEW aufgabe_6 AS
    SELECT name FROM artikel WHERE name LIKE 'S%' ORDER BY name;
