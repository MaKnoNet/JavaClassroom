# Übung 02: Formular mit Binder

## Aufgabe

`PersonFormularView` kopiert die Feldwerte von Hand in ein `Person`-Objekt – ohne jede
Prüfung. Ersetze das durch einen `Binder<Person>`:

1. Alle drei Felder an die Eigenschaften von `Person` binden.
2. Validierung: Name ist Pflicht (`asRequired`), E-Mail muss gültig sein
   (`EmailValidator`), Alter ist Pflicht und liegt zwischen 0 und 150
   (`IntegerRangeValidator`).
3. „Speichern" schreibt nur bei gültigen Daten in ein neues `Person`-Objekt
   (`writeBean`) und merkt es sich in `gespeicherte`; bei Fehlern bleibt `gespeicherte`
   leer und die betroffenen Felder zeigen ihre Fehlermeldung.

## Abnahmekriterien

- `./gradlew test` ist grün (vier Karibu-Tests).
- Kein `name.getValue()`/`email.getValue()` mehr im Speichern-Listener – der Binder
  überträgt.

## Hinweise

1. Aufbau: `Binder<Person> binder = new Binder<>(Person.class);` dann
   `binder.forField(name).asRequired("Name fehlt").bind(Person::getName, Person::setName);`
   – für E-Mail `.withValidator(new EmailValidator("Keine gültige E-Mail"))`, für Alter
   `.asRequired(...).withValidator(new IntegerRangeValidator("0 bis 150", 0, 150))`.
2. Speichern: `Person person = new Person(); try { binder.writeBean(person); gespeicherte = person; }
   catch (ValidationException e) { /* Felder zeigen die Meldungen schon */ }`.
