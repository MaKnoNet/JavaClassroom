# Übung 04: Repository und Transaktion

## Aufgabe

Zwei Schritte, zwei Testklassen.

**Schritt 1 – CRUD.** `KundenRepository` ist ein leeres Interface; das Projekt kompiliert
deshalb nicht einmal. Lass es `JpaRepository<Kunde, Long>` erweitern und ergänze die
abgeleitete Abfrage `Optional<Kunde> findByEmail(String email)`. Keine Implementierung
schreiben – das ist der Punkt. Danach ist `KundenRepositoryTest` grün (vier Tests: Create,
Read, Update, Delete).

**Schritt 2 – ganz oder gar nicht.** `KundenService.uebertrage` bucht einen Betrag von
Anna ab und Ben gut. Ist Bens Adresse unbekannt, fliegt eine Exception – aber Annas
Belastung ist dann schon in der Datenbank. `KundenServiceTest` deckt das auf. Mach die
Methode zu einer Transaktion, sodass bei einem Fehler *nichts* übrig bleibt.

## Abnahmekriterien

- `./gradlew test` ist grün (sechs Tests).
- `KundenRepository` enthält keine Implementierung und keine `@Query`.
- `KundenService` enthält keinen `try`/`catch` – das Zurückrollen ist nicht deine
  Aufgabe, sondern die von Spring.

## Hinweise

1. `import org.springframework.data.jpa.repository.JpaRepository;` – das Interface
   liefert `save`, `findById`, `findAll`, `delete`, `deleteAll`, `count`, `saveAndFlush`.
2. Abgeleitete Abfragen folgen einem Muster: `findBy` + Feldname in Großschreibung.
   Weitere Beispiele: `findByNameContaining`, `existsByEmail`, `countByGuthabenGreaterThan`.
   Falscher Feldname → die Anwendung startet nicht (lies die Meldung: sie nennt das
   fehlende Attribut).
3. `@Transactional` aus `org.springframework.transaction.annotation` an die Methode.
   Rollback passiert bei `RuntimeException` (und damit `IllegalArgumentException`) von
   selbst; bei checked Exceptions nicht – merken, das ist eine klassische Prüfungsfrage.
4. Warum der Service-Test `@SpringBootTest` nutzt und nicht `@DataJpaTest`: `@DataJpaTest`
   packt jeden Test selbst in eine Transaktion und rollt sie am Ende zurück – da wäre
   der Fehler unsichtbar. Der Service-Test will die echte Wirkung sehen.
