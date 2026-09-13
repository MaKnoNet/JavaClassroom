---
thema: vaadin
titel: Vaadin
voraussetzungen: [java, gradle, junit, spring]
zielgruppe: [azubi, student, kollege]
reihenfolge: 110
---

# Vaadin

Vaadin Flow baut Web-Oberflächen in reinem Java: Komponenten, Layouts, Ereignisse und
Datenbindung laufen auf dem Server, der Browser zeigt nur an. Das ist unser
UI-Framework; wer hier eine View anfasst, braucht Stufe 1 und 2 dieses Lehrplans. HTML und
CSS sind keine Voraussetzung, helfen aber ab Lektion 14 (Styling).

Am Ende von Stufe 1 kann der Lernende eine Anwendung mit mehreren Views, Navigation und
Ereignissen bauen und per Karibu testen. Nach Stufe 2 beherrscht er Formulare mit Binder,
Grids mit Lazy Loading, die Trennung von View und Service und den Lebenszyklus. Stufe 3
behandelt Speicher, Hintergrundarbeit, Sicherheit und eigene Komponenten.

Werkzeugkette der Übungen: Spring Boot 3.5.16, Vaadin 24.10.9, Karibu-Testing 2.5.0
(UI-Tests ohne Browser, laufen in `./gradlew test`). Vaadin 24 bringt für
Standardkomponenten ein vorkompiliertes Frontend-Bundle mit – **kein Node/npm nötig**;
erst eigene npm-Pakete (Lektion 18) brauchen es.

## Lektionen

### 01 Projekt aufsetzen und Dev-Modus
- **Ziele:** Spring-Boot-Projekt mit `vaadin-spring-boot-starter` und Vaadin-Gradle-Plugin; Anwendung starten (IDE und `./gradlew bootRun`), Dev-Modus und Hot Reload; was beim ersten Start passiert (Bundle, Frontend-Ordner); Eclipse/IntelliJ-Besonderheiten; Proxy-Stolperfallen
- **Prüffrage:** Warum braucht ein Vaadin-24-Projekt mit Standardkomponenten kein npm – und ab wann doch?
- **Übersetzung:** en: Project setup and dev mode | fr: Mise en place du projet et mode dev
- **Stufe:** 1

Leitfaden: Der erste Start dauert – Abhängigkeiten laden, Bundle entpacken. Vorher
ankündigen. In Eclipse läuft die App als Java-Application über die `main`-Klasse; nach
Änderungen an `build.gradle` Buildship-Refresh. Fehlerbild „PKIX path building failed"
→ Proxy-Hinweis in `AGENTS.md`. **Entwicklerlizenz:** Seit Vaadin 24.9 verlangt der
Dev-Modus beim ersten Start eine kostenlose Entwicklerlizenz – Anmeldung mit einem
Vaadin-Konto im Browser, hinter Proxys alternativ ein Offline-Schlüssel von vaadin.com. Die
Karibu-Tests laufen im Produktionsmodus (`vaadin.productionMode=true` im `test`-Task)
und brauchen das nicht; die Übungen bleiben also auch ohne Konto prüfbar.

### 02 Komponenten
- **Ziele:** `H1`, `Paragraph`, `TextField`, `EmailField`, `IntegerField`, `Button`, `ComboBox`, `DatePicker`, `Checkbox`; Wert lesen und setzen; Platzhalter, Pflichtfeld-Markierung; die Komponentendoku lesen
- **Prüffrage:** Was ist der Unterschied zwischen `setValue` auf einem `TextField` und dem, was der Nutzer tippt – wann weiß der Server davon?
- **Übersetzung:** en: Components | fr: Composants
- **Stufe:** 1

### 03 Layouts
- **Ziele:** `VerticalLayout`, `HorizontalLayout`, `FormLayout` mit Responsive Steps; Abstände (`setSpacing`, `setPadding`), Ausrichtung, `expand`; Verschachtelung ohne Layout-Suppe
- **Prüffrage:** Zwei Buttons nebeneinander, darunter ein Text – welche Layouts, wie verschachtelt?
- **Übersetzung:** en: Layouts | fr: Mises en page
- **Stufe:** 1

### 04 Routing und Hauptlayout
- **Ziele:** `@Route`, `@PageTitle`, `RouterLink`, Routen mit Parametern (`HasUrlParameter`); `AppLayout` mit Navigation als `@Layout`/`RouterLayout`; Startseite `@Route("")`
- **Prüffrage:** Was passiert, wenn zwei Views dieselbe Route haben – und wann merkt man es?
- **Übersetzung:** en: Routing and main layout | fr: Routage et mise en page principale
- **Stufe:** 1

### 05 Ereignisse und Rückmeldung
- **Ziele:** `addClickListener`, `addValueChangeListener`; `Notification.show`, Varianten (Erfolg, Fehler); Zustand in der View halten; Enter als Klick (`addClickShortcut`)
- **Übung:** uebungen/01-erste-view
- **Prüffrage:** Warum darf ein Click-Listener keine Sekunden dauern – und wo läuft er eigentlich?
- **Übersetzung:** en: Events and feedback | fr: Événements et retour utilisateur
- **Stufe:** 1

### 06 UI-Tests mit Karibu
- **Ziele:** Warum ohne Browser (schnell, stabil, im RAM); `MockVaadin.setup`, `Routes`, `UI.getCurrent().navigate`; `_get`, `_setValue`, `_click`, `expectNotifications`; Tests neben die View legen
- **Prüffrage:** Was prüft ein Karibu-Test nicht, was ein Browser-Test prüfen würde – und warum ist das meistens in Ordnung?
- **Übersetzung:** en: UI tests with Karibu | fr: Tests d'interface avec Karibu
- **Stufe:** 1

Leitfaden: Der Test aus Übung 01 ist das Beispiel – der Lernende hat ihn schon grün
gemacht, jetzt liest er ihn Zeile für Zeile. Ab Übung 02 schreibt er Karibu-Tests selbst.

### 07 Fehlersuche
- **Ziele:** Vaadin-Dev-Panel im Browser; Server-Log vs. Browser-Konsole – wo ein Fehler entsteht und wo er sichtbar wird; eigene Fehler-View (`HasErrorParameter`), 404-Seite; `ErrorHandler` für unbehandelte Exceptions; Debugger auf Listener setzen
- **Prüffrage:** Eine Exception im Click-Listener – was sieht der Nutzer ohne `ErrorHandler`, was mit?
- **Übersetzung:** en: Troubleshooting | fr: Recherche d'erreurs
- **Stufe:** 1

### 08 Formulare mit Binder
- **Ziele:** `Binder<T>`, `bind`, `bindInstanceFields`; Validierung (`asRequired`, `withValidator`, `EmailValidator`), Fehlermeldung am Feld; `writeBean`/`readBean` vs. `setBean`; Speichern nur bei gültigen Daten
- **Übung:** uebungen/02-binder-formular
- **Prüffrage:** Unterschied zwischen `setBean` und `readBean`/`writeBean` – wann ändert sich das Java-Objekt?
- **Übersetzung:** en: Forms with Binder | fr: Formulaires avec Binder
- **Stufe:** 2

### 09 Grid
- **Ziele:** `Grid<T>`, Spalten (`addColumn`, Renderer), Sortieren, Filtern über `ListDataProvider`, Selektion (Single/Multi), Zeile bearbeiten im `Dialog`; `ConfirmDialog`
- **Prüffrage:** Warum ist ein `ComponentRenderer` mit einem Button je Zeile bei 10 000 Zeilen ein Problem?
- **Übersetzung:** en: Grid | fr: Grid
- **Stufe:** 2

### 10 Lazy Loading
- **Ziele:** `DataProvider`, `CallbackDataProvider` (`fetch`/`count` mit Offset, Limit, Sortierung), Anbindung an Spring Data (`Pageable`); Filter serverseitig; warum `setItems(list)` bei großen Tabellen scheitert
- **Prüffrage:** Der Nutzer scrollt im Grid – welche Methode ruft Vaadin auf, mit welchen Parametern?
- **Übersetzung:** en: Lazy loading | fr: Chargement paresseux
- **Stufe:** 2

### 11 View und Service trennen, Scopes
- **Ziele:** Keine Datenbank in der View – Service per Konstruktor-Injection; Spring-Scopes bei Vaadin: Singleton (kein Nutzerzustand!), `@UIScope`, `@RouteScope`, `@VaadinSessionScope`; die Singleton-Falle (Nutzer A sieht Daten von B); Architekturregel als ArchUnit-Test
- **Prüffrage:** Ein Service hält den „aktuellen Kunden" in einem Feld und ist Singleton – was passiert bei zwei gleichzeitigen Nutzern?
- **Übersetzung:** en: Separating view and service, scopes | fr: Séparer vue et service, portées
- **Stufe:** 2

### 12 Navigations-Lebenszyklus
- **Ziele:** `BeforeEnterObserver` (Umleitung, Parameter prüfen), `BeforeLeaveObserver` mit `postpone()` („ungespeicherte Änderungen?"), `AfterNavigationObserver`; `AttachEvent`/`DetachEvent` zum Aufräumen von Listenern; `@PreserveOnRefresh`
- **Prüffrage:** Wo registriert man einen Listener auf einen Hintergrunddienst, und wo meldet man ihn wieder ab?
- **Übersetzung:** en: Navigation lifecycle | fr: Cycle de vie de la navigation
- **Stufe:** 2

### 13 Upload und Download
- **Ziele:** `Upload` mit `MemoryBuffer`/`FileBuffer`, Größen- und Typbeschränkung; Download über `StreamResource`/Download-Handler und `Anchor`; Excel-Export als Beispiel; große Dateien streamen statt in den Speicher laden
- **Prüffrage:** Warum darf ein Download nicht erst die ganze Datei in ein `byte[]` laden?
- **Übersetzung:** en: Upload and download | fr: Téléversement et téléchargement
- **Stufe:** 2

### 14 Styling und i18n
- **Ziele:** Lumo-Utility-Klassen (`addClassNames`), Lumo-Variablen, eigenes Theme (`@Theme`, `styles.css`), Dark Mode; `I18NProvider` und `getTranslation`; wo CSS-Kenntnisse jetzt helfen
- **Prüffrage:** Wie färbt man alle Buttons einer Anwendung um, ohne jeden Button anzufassen?
- **Übersetzung:** en: Styling and i18n | fr: Style et i18n
- **Stufe:** 2

### 15 Speichermodell der Session
- **Ziele:** Was auf dem Server liegt (Komponentenbaum je UI, je Nutzer); Speicher pro Session messen; `ComponentRenderer` vs. `LitRenderer`; Listen nicht in Feldern halten; Detach und Session-Timeout; `OutOfMemoryError` lesen (Heap Dump)
- **Prüffrage:** 200 Nutzer, jeder mit einem Grid von 5 000 Zeilen per `setItems` – wie viel Speicher, und was ändert Lazy Loading daran?
- **Übersetzung:** en: The session memory model | fr: Le modèle mémoire de la session
- **Stufe:** 3

### 16 Hintergrundarbeit, Polling und Push
- **Ziele:** Lange Aufgaben aus dem Listener heraus in einen `ExecutorService`; Ergebnis anzeigen per `ui.setPollInterval` (einfach) oder `@Push` mit `UI.access()` (WebSocket); Thread-Sicherheit der UI; `ui.isAttached` prüfen
- **Prüffrage:** Warum darf ein Hintergrund-Thread nie direkt `label.setText(...)` aufrufen?
- **Übersetzung:** en: Background work, polling and push | fr: Travail en arrière-plan, polling et push
- **Stufe:** 3

### 17 Sicherheit
- **Ziele:** Spring Security mit Vaadin (`VaadinWebSecurity`), Login-View, `@RolesAllowed`, `@PermitAll`, `@AnonymousAllowed`; CSRF ist eingebaut; XSS vermeiden: `Text`/`setValue` statt `Html`/`setInnerHtml` bei Nutzereingaben
- **Prüffrage:** Ein Kommentarfeld zeigt Nutzereingaben mit `new Html(text)` an – was kann ein Angreifer damit tun?
- **Übersetzung:** en: Security | fr: Sécurité
- **Stufe:** 3

### 18 Eigene Komponenten und Produktion
- **Ziele:** `Composite<T>` für wiederverwendbare Java-Komponenten; `@JsModule`/`@NpmPackage` für Web-Komponenten (ab hier npm nötig); Produktionsbuild (`productionMode`, `vaadinBuildFrontend`); Flow vs. Hilla in einem Satz
- **Prüffrage:** Wann reicht ein `Composite`, wann braucht man eine Web-Komponente?
- **Übersetzung:** en: Custom components and production | fr: Composants personnalisés et production
- **Stufe:** 3
