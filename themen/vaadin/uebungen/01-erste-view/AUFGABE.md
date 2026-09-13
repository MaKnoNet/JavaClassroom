# Übung 01: Die erste View

## Aufgabe

`HalloView` zeigt Überschrift, Textfeld und Knopf – der Knopf tut noch nichts. Bring die
View zum Leben:

1. Klick auf „Grüßen" schreibt `Hallo, <Name>!` in den Absatz `ausgabe`. Ist das Feld
   leer (oder nur Leerzeichen), steht dort `Hallo, Unbekannt!`.
2. Zusätzlich erscheint eine `Notification` mit dem Text `Begrüßt: <Name>`.
3. Enter im Textfeld löst den Knopf aus (`addClickShortcut`).

Starte die Anwendung (IDE oder `./gradlew bootRun`, dann `http://localhost:8080`) und
probiere es im Browser. Die Tests prüfen dasselbe ohne Browser.

## Abnahmekriterien

- `./gradlew test` ist grün (drei Karibu-Tests in `HalloViewTest`).
- Die Anwendung startet und die Seite reagiert im Browser wie beschrieben.

## Hinweise

1. `gruessen.addClickListener(ereignis -> { ... })` – darin `nameFeld.getValue()` lesen,
   `ausgabe.setText(...)` setzen, `Notification.show(...)` aufrufen.
2. Der erste Start lädt Vaadin und entpackt das Frontend-Bundle – ein bis zwei Minuten.
   Erscheint `PKIX path building failed`, greift der Proxy-Hinweis in `AGENTS.md`.
3. Beim ersten Start im Dev-Modus fragt Vaadin nach einer kostenlosen Entwicklerlizenz
   (Anmeldung mit Vaadin-Konto im Browser). `./gradlew test` braucht das nicht – die
   Tests laufen im Produktionsmodus.
