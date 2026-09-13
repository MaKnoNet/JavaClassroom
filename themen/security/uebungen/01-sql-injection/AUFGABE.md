# Übung 01: SQL-Injection

## Aufgabe

`KundenSuche.nachName` baut das SQL mit `+` zusammen. Zwei Tests sind grün, drei rot –
und die drei roten sind kein Detail:

- `O'Brien` ist ein gültiger Name und liefert einen **Syntaxfehler**.
- `' OR '1'='1` liefert **alle Kunden**, obwohl niemand so heißt.
- `x'; delete from kunde; --` **leert die Tabelle**. Wirklich: `Anna` ist danach weg.

Alle drei sind dasselbe Problem: Der Nutzer schreibt SQL mit. Behebe es, ohne die
Eingabe zu „säubern" – Zeichen zu verbieten ist keine Lösung (O'Brien darf so heißen).

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests).
- Das SQL in `KundenSuche` ist eine Konstante ohne `+`.
- Kein Filtern, Ersetzen oder Verbieten von Zeichen in der Eingabe.

## Hinweise

1. `PreparedStatement`: `verbindung.prepareStatement("… where name = ?")`, dann
   `setString(1, name)`. Der Treiber überträgt den Wert getrennt vom SQL-Text – die
   Datenbank *kann* ihn gar nicht als Befehl lesen.
2. Das `?` ist kein Textersatz. `setString` fügt keine Anführungszeichen ein und maskiert
   nichts; der Parameter geht als Datum an die Datenbank. Deshalb funktioniert es bei
   jeder Eingabe, nicht nur bei den drei getesteten.
3. Wer Spring nutzt, hat dasselbe unter anderem Namen: `JdbcTemplate.query(sql, mapper, name)`,
   in JPQL `:name` mit `setParameter`. Spring Data Repositories (Spring-Lektion 04)
   parametrisieren immer – außer man baut `@Query`-Strings mit `+` zusammen.
4. Dasselbe Muster gilt überall, wo Text zu Code wird: Shell-Befehle (`ProcessBuilder`
   mit Argumentliste statt einem String), LDAP-Filter, XPath, `eval` in JavaScript.
