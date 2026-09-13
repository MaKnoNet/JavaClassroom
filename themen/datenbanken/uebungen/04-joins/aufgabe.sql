-- Übung 04: Mehrere Tabellen verbinden
--
-- Wieder eine Sicht je Teilaufgabe. Spalten in der genannten Reihenfolge.
--
-- aufgabe_1: Jede Bestellung mit Kundenname: bestellung.id, datum, kunde.name.
--            (Der folgende Anfang liefert 30 Zeilen statt 5 – warum? Verbessere ihn.)
CREATE VIEW aufgabe_1 AS SELECT bestellung.id, datum, name FROM bestellung, kunde;

-- aufgabe_2: name der Kunden, die noch NIE bestellt haben.

-- aufgabe_3: Die Positionen der Bestellung 1: artikel.name, menge, zeilensumme (menge * preis).

-- aufgabe_4: Welcher Kunde hat welchen Artikel bestellt? kunde.name, artikel.name – jede
--            Kombination nur einmal, auch wenn sie in mehreren Bestellungen vorkommt.

-- aufgabe_5: ALLE Kunden mit dem Status ihrer Bestellungen: kunde.name, status – Kunden
--            ohne Bestellung erscheinen mit leerem Status (NULL).
