# Lösung 03

Siehe `loesung/aufgabe.sql`. Die zwei Fehler im Überweisungs-Anfang: kein `WHERE id = 1`
im ersten `UPDATE` (Konto 3 verliert 50, die Summe stimmt nicht mehr – zwei
`Fehler:`-Zeilen der Prüfung) und kein `BEGIN`/`COMMIT` (nicht messbar, aber im Betrieb
der gefährlichere).

Warum die Prüfung die Summe prüft: Bei einer Überweisung ist die Invariante „Summe aller
Konten bleibt gleich" wichtiger als jeder Einzelwert – genau das sichert die Transaktion
zu. Spring-Lektion 04 baut dieselbe Überweisung in Java und lässt sie ohne
`@Transactional` scheitern.

Wer beim `UPDATE artikel` `1.1` statt `1.10` schreibt: gleich. Wer `preis + 10` schreibt:
zehn Euro, nicht zehn Prozent – die Prüfung erwartet 26.95 für den Hammer.
