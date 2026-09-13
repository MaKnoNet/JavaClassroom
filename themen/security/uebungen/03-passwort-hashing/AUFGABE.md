# Übung 03: Passwörter speichern

## Aufgabe

`BenutzerVerwaltung` funktioniert – Anmelden klappt, falsches Passwort scheitert. Aber
sie speichert das Passwort **im Klartext**. Der Test `gespeichertIstNichtDasPasswort`
schaut in die „Datenbank" wie jemand, der ein Backup gefunden hat: Er sieht `geheim123`.

Speichere statt des Passworts einen **Hash mit zufälligem Salt** und prüfe die Anmeldung
gegen den Hash. Die Bibliothek ist im `build.gradle` (`spring-security-crypto`).

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests).
- Kein `MessageDigest`, kein SHA-256 von Hand, kein eigener Salt-Code – ein
  `PasswordEncoder` aus der Bibliothek.
- `anmelden` vergleicht über `matches`, nicht mit `equals` auf zwei Hashes.

## Hinweise

1. `PasswordEncoder encoder = new BCryptPasswordEncoder();` –
   `encoder.encode(passwort)` beim Registrieren, `encoder.matches(eingabe, hash)` beim
   Anmelden. Der Salt steckt im Hash (`$2a$10$<salt><hash>`), `matches` liest ihn dort.
2. Warum nicht SHA-256? Es ist *schnell* – eine Grafikkarte probiert Milliarden
   Kandidaten pro Sekunde. BCrypt und Argon2 sind absichtlich langsam (Kostenfaktor)
   und Argon2 zusätzlich speicherhungrig, damit sich das Raten nicht parallelisieren
   lässt.
3. Warum zufälliger Salt? Ohne ihn haben zwei Nutzer mit demselben Passwort denselben
   Hash, und eine vorberechnete Tabelle (Rainbow Table) knackt alle auf einmal. Die
   letzten zwei Tests prüfen genau das.
4. Argon2 ist die aktuelle Empfehlung (OWASP): `Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()` –
   braucht zusätzlich `org.bouncycastle:bcprov-jdk18on`. BCrypt bleibt in Ordnung.
5. In einer Spring-Boot-Anwendung ist der `PasswordEncoder` eine Bean, die Spring
   Security beim Login selbst benutzt (Vaadin-Lektion 17) – der Code hier ist derselbe.
