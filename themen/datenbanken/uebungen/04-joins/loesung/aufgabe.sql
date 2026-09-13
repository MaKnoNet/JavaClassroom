-- Lösung zu Übung 04

-- Ohne ON-Bedingung entsteht das kartesische Produkt: 5 Bestellungen x 6 Kunden = 30 Zeilen.
CREATE VIEW aufgabe_1 AS
    SELECT b.id, b.datum, k.name
    FROM bestellung b
    JOIN kunde k ON k.id = b.kunde_id;

-- LEFT JOIN behält alle Kunden; wer keine Bestellung hat, bekommt NULL auf der rechten Seite.
CREATE VIEW aufgabe_2 AS
    SELECT k.name
    FROM kunde k
    LEFT JOIN bestellung b ON b.kunde_id = k.id
    WHERE b.id IS NULL;

CREATE VIEW aufgabe_3 AS
    SELECT a.name, p.menge, p.menge * a.preis AS zeilensumme
    FROM position p
    JOIN artikel a ON a.id = p.artikel_id
    WHERE p.bestellung_id = 1;

CREATE VIEW aufgabe_4 AS
    SELECT DISTINCT k.name AS kunde, a.name AS artikel
    FROM position p
    JOIN bestellung b ON b.id = p.bestellung_id
    JOIN kunde k ON k.id = b.kunde_id
    JOIN artikel a ON a.id = p.artikel_id;

CREATE VIEW aufgabe_5 AS
    SELECT k.name, b.status
    FROM kunde k
    LEFT JOIN bestellung b ON b.kunde_id = k.id;
