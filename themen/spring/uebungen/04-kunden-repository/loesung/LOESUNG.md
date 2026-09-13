Zwei Dateien ersetzen ihre Gegenstücke in `src/main/java/de/makno/lernen/`.

Warum so: `JpaRepository<Kunde, Long>` bringt `save`, `findById`, `delete`, `count`,
`findAll` und mehr mit – Spring Data erzeugt beim Start eine Implementierung als Bean,
deshalb kann der Service sie per Konstruktor bekommen, obwohl nirgends eine Klasse steht.
`findByEmail` ist eine **abgeleitete Abfrage**: Spring Data zerlegt den Namen (`find`,
`By`, `Email`), prüft, dass `Kunde` ein Feld `email` hat, und baut daraus `select … where
email = ?`. Ein Tippfehler im Namen fällt beim Start auf, nicht erst im Betrieb.
`Optional` als Rückgabe, weil „nicht gefunden" kein Fehler ist (Java-Lektion 12).

`@Transactional` macht `uebertrage` zu einer Einheit: Spring öffnet vor der Methode eine
Transaktion und schließt sie danach mit *commit* – oder mit *rollback*, wenn eine
`RuntimeException` herausfliegt. Ohne die Annotation war jedes `save` seine eigene
Mini-Transaktion: Annas Belastung war schon dauerhaft, als Bens Adresse nicht gefunden
wurde. Nebenbei: Innerhalb der Transaktion wäre `save` sogar überflüssig – Hibernate
schreibt geänderte Entities beim Commit von selbst (*dirty checking*). Der Aufruf bleibt
in der Lösung, weil er die Absicht lesbar macht.
