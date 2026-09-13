-- Übung 09: Fensterfunktionen (Window Functions)
--
-- Drei Controlling-Berichte als Sichten. Spalten in der genannten Reihenfolge.
--
-- aufgabe_1: Die drei meistverkauften Artikel JE KATEGORIE nach Stück:
--            kategorie, artikel (name), stueck, rang (1 = meistverkauft; bei gleicher
--            Stückzahl entscheidet der Artikelname alphabetisch).
--            (Der folgende Anfang liefert die Top 3 über ALLE Kategorien – LIMIT kennt keine
--            Gruppen. Baue ihn mit ROW_NUMBER() OVER (PARTITION BY …) um.)
CREATE VIEW aufgabe_1 AS
    SELECT a.kategorie, a.name AS artikel, SUM(p.menge) AS stueck
    FROM position p JOIN artikel a ON a.id = p.artikel_id
    GROUP BY a.kategorie, a.name
    ORDER BY stueck DESC
    LIMIT 3;

-- aufgabe_2: Umsatz je Monat im Vergleich zum Vormonat: monat ('2026-01'), umsatz,
--            vormonat (der Umsatz des vorigen Monats; im ersten Monat NULL).
--            Monat aus dem Datum – in allen Datenbanken gleich:
--            SUBSTR(CAST(b.datum AS VARCHAR(10)), 1, 7)

-- aufgabe_3: Tagesumsatz mit fortlaufender Summe: datum, tagesumsatz, laufende_summe
--            (Summe aller Tage bis einschließlich diesem).
