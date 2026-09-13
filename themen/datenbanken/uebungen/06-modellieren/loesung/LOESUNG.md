# Lösung 06

Drei Tabellen, siehe `loesung/aufgabe.sql`. Die Normalformen, an denen man das misst:

- **1. NF:** Jede Zelle ein Wert – keine Listen („Anna, Ben") in einer Spalte. `anmeldung`
  erfüllt sie schon.
- **2. NF:** Jede Nicht-Schlüsselspalte hängt vom *ganzen* Schlüssel ab. In `anmeldung` wäre
  der Schlüssel (Kurstitel, Teilnehmer) – `kurs_plaetze` hängt aber nur vom Kurs ab. Deshalb
  raus in `kurs`.
- **3. NF:** Keine Abhängigkeit zwischen Nicht-Schlüsselspalten. `teilnehmer_email` hängt vom
  Teilnehmer ab, nicht von der Anmeldung. Deshalb raus in `teilnehmer`.

Was übrig bleibt, ist `teilnahme` – nur noch die Beziehung, sonst nichts. Die 12/15-Anomalie
ist damit unmöglich: Die Platzzahl steht genau einmal.

Die Regeln als Constraints, damit die Datenbank sie durchsetzt und nicht die Anwendung
(Spring-Lektion 03 legt genau dieselben Regeln als `@Column(nullable = false, unique = true)`
an – die Datenbank ist trotzdem die letzte Instanz):

| Regel | Constraint | Prüfung in `pruefung.sql` |
|---|---|---|
| Kurstitel eindeutig | `UNIQUE` | doppelter Titel scheitert |
| Plätze positiv | `CHECK (plaetze > 0)` | 0 Plätze scheitern |
| E-Mail eindeutig, darf fehlen | `UNIQUE` ohne `NOT NULL` | doppelte E-Mail scheitert |
| Teilnahme nur an echten Kursen | `REFERENCES kurs (id)` | Kurs 99 scheitert |
| Keine doppelte Teilnahme | `PRIMARY KEY (kurs_id, teilnehmer_id)` | (1,1) zweimal scheitert |
| Kurs absagen räumt auf | `ON DELETE CASCADE` | Kurs 2 weg, Teilnahmen weg |

Bonus (`INSERT INTO kurs SELECT DISTINCT …`): scheitert an der 12/15-Zeile mit
`UNIQUE`-Verletzung – genau richtig. Erst muss jemand entscheiden, welche Zahl stimmt.
Normalisierung deckt solche Widersprüche auf, sie löst sie nicht.
