-- Übung 05: Zusammenfassen
--
-- Eine Sicht je Teilaufgabe. Spalten in der genannten Reihenfolge.
--
-- aufgabe_1: Umsatz je Kunde: kunde.name, umsatz (Summe aus menge * preis über alle
--            Positionen aller Bestellungen des Kunden). Nur Kunden, die bestellt haben.
--            (Der folgende Anfang ist unvollständig – PostgreSQL und H2 verweigern ihn,
--            SQLite liefert stillschweigend Unsinn. Was fehlt?)
CREATE VIEW aufgabe_1 AS
    SELECT k.name, SUM(p.menge * a.preis)
    FROM kunde k
    JOIN bestellung b ON b.kunde_id = k.id
    JOIN position p ON p.bestellung_id = b.id
    JOIN artikel a ON a.id = p.artikel_id;

-- aufgabe_2: Kunden mit MEHR ALS EINER Bestellung: kunde.name, anzahl.

-- aufgabe_3: name der Artikel, die teurer sind als der Durchschnittspreis aller Artikel.

-- aufgabe_4: Eine einzige Zeile mit zwei Zahlen: wie viele Kunden eine E-Mail haben, und
--            wie viele Kunden es insgesamt gibt.

-- aufgabe_5: Bestellungen, deren Wert über dem Durchschnittswert aller Bestellungen liegt:
--            bestellung.id, wert. Tipp: erst mit WITH je Bestellung den Wert berechnen,
--            dann damit weiterrechnen.
