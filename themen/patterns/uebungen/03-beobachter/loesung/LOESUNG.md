# Lösung 03

Record `BestandsEreignis`, Interface `BestandsBeobachter` (eine Methode, also
lambda-fähig), `Lager` mit Beobachterliste und `registriere`/`entferne`/`benachrichtige`;
`Anzeige` und `Protokoll` implementieren das Interface und werden von außen registriert.

Was sich im Klassendiagramm ändert: Vorher zwei Pfeile `Lager → Anzeige`, `Lager →
Protokoll` (Kopplung an konkrete Klassen). Nachher ein Pfeil `Lager → <<interface>>
BestandsBeobachter` und zwei Realisierungspfeile von Anzeige und Protokoll zum Interface.
Die Abhängigkeitsrichtung hat sich umgedreht – das ist Dependency Inversion.

Zwei Betriebsdetails, die die Tests erzwingen: `try/catch` um jeden Beobachter (ein
kaputter darf die anderen nicht stoppen) und `CopyOnWriteArrayList`, damit ein
Beobachter sich im Ereignis selbst abmelden darf, ohne eine
`ConcurrentModificationException` zu werfen.
