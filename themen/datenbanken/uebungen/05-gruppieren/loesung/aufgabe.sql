-- Lösung zu Übung 05

-- Jede Spalte, die nicht in einer Aggregatfunktion steht, gehört ins GROUP BY.
CREATE VIEW aufgabe_1 AS
    SELECT k.name, SUM(p.menge * a.preis) AS umsatz
    FROM kunde k
    JOIN bestellung b ON b.kunde_id = k.id
    JOIN position p ON p.bestellung_id = b.id
    JOIN artikel a ON a.id = p.artikel_id
    GROUP BY k.name;

-- WHERE filtert Zeilen VOR dem Gruppieren, HAVING filtert Gruppen DANACH.
CREATE VIEW aufgabe_2 AS
    SELECT k.name, COUNT(*) AS anzahl
    FROM kunde k
    JOIN bestellung b ON b.kunde_id = k.id
    GROUP BY k.name
    HAVING COUNT(*) > 1;

-- Die Unterabfrage liefert einen einzelnen Wert, mit dem verglichen wird.
CREATE VIEW aufgabe_3 AS
    SELECT name FROM artikel
    WHERE preis > (SELECT AVG(preis) FROM artikel);

-- COUNT(spalte) zählt nur Zeilen, in denen die Spalte nicht NULL ist; COUNT(*) zählt alle.
CREATE VIEW aufgabe_4 AS
    SELECT COUNT(email) AS mit_email, COUNT(*) AS gesamt FROM kunde;

-- WITH gibt einem Zwischenergebnis einen Namen; danach kann man es wie eine Tabelle nutzen.
CREATE VIEW aufgabe_5 AS
    WITH bestellwert AS (
        SELECT p.bestellung_id, SUM(p.menge * a.preis) AS wert
        FROM position p
        JOIN artikel a ON a.id = p.artikel_id
        GROUP BY p.bestellung_id
    )
    SELECT bestellung_id, wert
    FROM bestellwert
    WHERE wert > (SELECT AVG(wert) FROM bestellwert);
