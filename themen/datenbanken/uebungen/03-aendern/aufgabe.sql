-- Übung 03: Daten ändern
--
-- 1. Neuer Kunde: id 7, 'Greta Groß', 'greta@example.org', geboren 1999-08-08, aktiv.

-- 2. Ben Berger (id 2) hat jetzt die E-Mail 'ben@example.org'.

-- 3. David Dahl (id 4) hat gekündigt – lösche ihn.

-- 4. Alle Artikel der Kategorie 'Werkzeug' werden 10 % teurer.

-- 5. Überweisung: 50 Euro von Konto 1 (Anna) auf Konto 2 (Ben) – als EINE Transaktion,
--    damit nie nur die Hälfte passiert.
--    (Der folgende Anfang ist gleich doppelt falsch. Finde beide Fehler.)
UPDATE konto SET stand = stand - 50;
UPDATE konto SET stand = stand + 50 WHERE id = 2;
