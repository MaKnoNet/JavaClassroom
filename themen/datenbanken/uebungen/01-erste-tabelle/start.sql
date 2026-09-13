-- Ausgangszustand: noch keine Tabelle. Dieses Skript räumt auf, damit du es beliebig oft
-- neu starten kannst.
-- Aufräumen: alles weg, was frühere Übungen dieses Themas angelegt haben könnten.
-- Sichten zuerst (sie hängen an Tabellen), dann Tabellen in umgekehrter Abhängigkeitsfolge.
DROP VIEW IF EXISTS aufgabe_1;
DROP VIEW IF EXISTS aufgabe_2;
DROP VIEW IF EXISTS aufgabe_3;
DROP VIEW IF EXISTS aufgabe_4;
DROP VIEW IF EXISTS aufgabe_5;
DROP VIEW IF EXISTS aufgabe_6;
DROP VIEW IF EXISTS offene_bestellungen;
DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS bestellung;
DROP TABLE IF EXISTS konto;
DROP TABLE IF EXISTS teilnahme;
DROP TABLE IF EXISTS teilnehmer;
DROP TABLE IF EXISTS kurs;
DROP TABLE IF EXISTS anmeldung;
DROP TABLE IF EXISTS artikel;
DROP TABLE IF EXISTS kunde;
