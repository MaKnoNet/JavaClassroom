-- Übung 02: Daten lesen
--
-- Jede Teilaufgabe ist eine Abfrage, die du als Sicht (VIEW) speicherst – so kann die
-- Prüfung sie ausführen. Muster:
--     CREATE VIEW aufgabe_1 AS SELECT … FROM … WHERE …;
-- Die Spalten müssen in der genannten Reihenfolge kommen; Sortierung ist nur dort
-- gefordert, wo es dabeisteht. Vorher in der Konsole ausprobieren, dann hier eintragen.
--
-- aufgabe_1: name und email aller AKTIVEN Kunden.
--            (Die folgende Zeile ist ein falscher Anfang – alle Spalten, alle Kunden. Verbessere sie.)
CREATE VIEW aufgabe_1 AS SELECT * FROM kunde;

-- aufgabe_2: name und preis aller Artikel, die zwischen 10 und 50 Euro kosten (einschließlich).

-- aufgabe_3: name der Kunden, die KEINE E-Mail-Adresse haben.

-- aufgabe_4: alle verschiedenen Kategorien – jede genau einmal.

-- aufgabe_5: name und preis der DREI teuersten Artikel.

-- aufgabe_6: name aller Artikel, deren Name mit 'S' beginnt.
