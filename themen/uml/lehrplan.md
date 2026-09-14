---
thema: uml
titel: UML-Diagramme
voraussetzungen: [java]
zielgruppe: [anfaenger, fortgeschritten]
reihenfolge: 82
---

# UML-Diagramme

UML (Unified Modeling Language) ist die visuelle Sprache der Softwarearchitektur. Sie
erlaubt es, Systeme, Datenstrukturen und Abläufe zu skizzieren und zu verstehen, ohne im
Quellcode zu versinken – ein Diagramm sagt oft mehr als tausend Zeilen Code. Hier geht es
nicht um verstaubte Spezifikationen, sondern um UML als pragmatisches Werkzeug im
Entwickleralltag: im Code-Review, beim Entwurf neuer Features und als direkte Vorlage für
Code, insbesondere für das Thema **Entwurfsmuster**.

Am Ende kann der Lernende Klassen-, Sequenz- und Zustandsdiagramme flüssig lesen, Fehler
in der Modellierung erkennen und Java-Code in Diagramme übersetzen – und umgekehrt. Er
beherrscht die gängige Notation und kann PlantUML in seinen Arbeitsablauf einbauen.

Werkzeug ist **PlantUML** als Textformat: Diagramme sind `.puml`-Dateien, die wie Code
im Repository liegen, sich vergleichen und prüfen lassen. Gerendert wird mit dem
PlantUML-Jar (nur das JDK nötig, kein Installer, kein Graphviz):

```bash
curl -O https://repo1.maven.org/maven2/net/sourceforge/plantuml/plantuml/1.2026.8/plantuml-1.2026.8.jar
java -jar plantuml-1.2026.8.jar -tsvg -Playout=smetana diagramm.puml
```

Das SVG öffnet der Lehrer in der Browser-Ansicht (`jwebserver` im Übungsordner) – wie
bei den HTML-Übungen. Jede Übung ist ein Gradle-Projekt: Der Lernende schreibt die
`.puml` (in Lektion 08 auch Java), ein JUnit-Test lässt PlantUML die Datei parsen und
prüft die Struktur – Klassen, Pfeilarten, Multiplizitäten, Sichtbarkeiten. Rot/grün wie
überall.

## Lektionen

### 01 Warum UML: Visuelle Sprache und Werkzeuge
- **Ziele:** UML als Kommunikationsmittel im Team – Skizze am Whiteboard vs. Dokumentation; die Gefahr von Missverständnissen im Fließtext; die drei Diagrammarten dieses Themas und wofür sie taugen (Klassendiagramm: Struktur, Sequenzdiagramm: Ablauf zwischen Objekten, Zustandsdiagramm: Lebenszyklus eines Objekts); **PlantUML** als Text-zu-Diagramm-Werkzeug – warum Textdateien im Git-Repository diffbar und wartbar sind, Binärdateien von Klick-Werkzeugen nicht; die Werkzeugkette: `.puml` schreiben, Jar rendern, SVG ansehen; erste Syntax: `@startuml`/`@enduml`, `class`, `interface`, ein Pfeil
- **Übung:** uebungen/01-erste-skizze
- **Prüffrage:** Warum gehören UML-Diagramme als PlantUML-Textdateien ins Git-Repository, während `.png`- oder `.drawio`-Dateien dort Probleme machen? Und: Welches der drei Diagramme nimmst du, um zu zeigen, *welche Klasse wen aufruft* – und welches, um zu zeigen, *welche Klasse was kennt*?
- **Übersetzung:** en: Why UML: a visual language and its tools | fr: Pourquoi UML : un langage visuel et ses outils
- **Stufe:** 1

Leitfaden: Ein verworrenes Anforderungsdokument zeigen und fragen: „Siehst du auf den
ersten Blick, welche Klasse hier wen aufruft?" Die Antwort führt zur ersten `.puml`, die
der Lehrer sofort rendert und in der Browser-Ansicht zeigt. Die Werkzeugkette muss in
dieser Lektion einmal komplett durchlaufen sein – Jar laden, rendern, ansehen.

### 02 Das Klassendiagramm: Typen, Attribute und Sichtbarkeiten
- **Ziele:** Die Anatomie einer Klasse im Diagramm: Name, Attribute, Methoden in drei Fächern; Sichtbarkeiten: `-` private, `+` public, `#` protected, `~` package-private; UML-Notation für Typen – `name: String`, `berechne(): double`, Parameter `zahle(betrag: double)`; statische Elemente unterstrichen (`{static}`), abstrakte kursiv (`{abstract}`); Interfaces mit `<<interface>>`; was ins Diagramm gehört und was nicht (Getter weglassen, wenn sie nichts sagen)
- **Übung:** uebungen/02-klassen-struktur
- **Prüffrage:** Wie unterscheidet sich `private int alter;` in Java von der Schreibweise im UML-Klassendiagramm – und warum steht der Typ in UML hinten? Und: Wann lässt man Methoden im Diagramm bewusst weg?
- **Übersetzung:** en: The class diagram: types, attributes, visibility | fr: Le diagramme de classes : types, attributs, visibilité
- **Stufe:** 1

Leitfaden: Hier wird die präzise Schreibweise trainiert. Der Test der Übung prüft die
Doppelpunkt-Notation und ob die Sichtbarkeitsmarker exakt den Java-Modifiern der Vorlage
entsprechen – ein `+` an einem privaten Feld ist rot.

### 03 Beziehungen Teil 1: Vererbung und Realisierung
- **Ziele:** Die strukturellen Beziehungen der Objektorientierung: **Generalisierung** („ist ein", Vererbung) – durchgezogener Pfeil mit leerem Dreieck zur Oberklasse (`--|>`); **Realisierung** (Interface implementieren) – gestrichelter Pfeil mit leerem Dreieck (`..|>`); die Pfeilrichtung zeigt immer zur Abstraktion – Klasse zeigt auf Interface, nie umgekehrt; **Abhängigkeit** (`..>`) als schwächste Beziehung (benutzt, kennt aber nicht dauerhaft); Open/Closed im Diagramm lesen: neue Klassen kommen als weitere Pfeile auf dasselbe Interface, das Interface bleibt
- **Prüffrage:** In welche Richtung zeigt der Pfeil bei einer Vererbung – Unterklasse → Oberklasse oder umgekehrt – und warum ist das logisch? Und: Woran erkennst du im Diagramm den Unterschied zwischen „erbt von" und „implementiert"?
- **Übersetzung:** en: Relationships 1: inheritance and realisation | fr: Relations 1 : héritage et réalisation
- **Stufe:** 1

Leitfaden: Keine eigene Übung – die Pfeile kommen in Übung 03 zusammen mit den
Assoziationen dran. Das ist die wichtigste visuelle Weiche für die Entwurfsmuster: Wer die
Pfeilrichtung vertauscht, versteht später Strategie und Beobachter nicht. An der Tafel
drei Klassen und ein Interface zeichnen lassen, bevor eine Zeile PlantUML entsteht.

### 04 Beziehungen Teil 2: Assoziation, Aggregation und Komposition
- **Ziele:** Die Objektbeziehungen („hat ein"): **Assoziation** – kennt ein anderes Objekt (`-->`), gerichtet oder ungerichtet; **Multiplizitäten** lesen und schreiben (`1`, `*`, `0..1`, `1..*`); **Aggregation** – Teil-Ganzes, offene Raute (`o--`), das Teil lebt ohne das Ganze weiter (Universität und Professor); **Komposition** – Existenzabhängigkeit, ausgefüllte Raute (`*--`), stirbt das Ganze, sterben die Teile (Bestellung und Bestellposition); Rollennamen an den Linienenden; was die Raute für den Code bedeutet (Komposition erzeugt und besitzt, Aggregation bekommt gereicht) und für die Datenbank (`ON DELETE CASCADE`, JPA `cascade`)
- **Übung:** uebungen/03-beziehungen
- **Prüffrage:** Ein `Kunde` hat eine `Adresse`; wird der Kunde gelöscht, bleibt die Adresse in der Datenbank bestehen. Aggregation oder Komposition – und welches Symbol gehört an die Linie? Und: Was bedeutet `1` auf der einen und `*` auf der anderen Seite einer Linie für die beiden Java-Klassen?
- **Übersetzung:** en: Relationships 2: association, aggregation, composition | fr: Relations 2 : association, agrégation, composition
- **Stufe:** 1

Leitfaden: Greifbare Beispiele. In der Übung entsteht ein Domänenmodell (Kunde,
Bestellung, Bestellposition, Adresse); der Test prüft Rautenart und Multiplizitäten. Die
schwarze Raute richtig zu setzen verhindert später Fehler im Datenbankdesign
(Datenbanken-Lektion 06, Spring-Lektion 03).

### 05 Das Sequenzdiagramm: Nachrichten und Lebenslinien
- **Ziele:** Von der Struktur zur Interaktion: Teilnehmer (`participant`, `actor`) und ihre Lebenslinien; Zeit läuft von oben nach unten; synchrone Nachricht (durchgezogener Pfeil, ausgefüllte Spitze, `->`) vs. asynchron (offene Spitze, `->>`); Antwort (gestrichelt, `-->`); Aktivierungsbalken (`activate`/`deactivate`) zeigen, wer gerade arbeitet und wer wartet; Objekte erzeugen (`create`) und zerstören; der Schichtenfluss Client → Controller → Service → Repository als Standardbild (Spring-Lektion 02)
- **Übung:** uebungen/04-sequenz-anfrage
- **Prüffrage:** Was stellt die vertikale Achse dar, und woran erkennst du, dass ein Objekt gerade auf die Antwort eines anderen wartet? Und: Wann ist ein Pfeil gestrichelt?
- **Übersetzung:** en: The sequence diagram: messages and lifelines | fr: Le diagramme de séquence : messages et lignes de vie
- **Stufe:** 2

Leitfaden: Sequenzdiagramme zeigen den Fluss durch die Schichten – genau das, was in
Spring sonst „Magie" ist. Die Übung zeichnet einen HTTP-Aufruf vom Client bis zur
Datenbank und zurück; der Test prüft Teilnehmer, Reihenfolge und Antwortpfeile.

### 06 Schleifen und Bedingungen im Sequenzdiagramm
- **Ziele:** Kontrollfluss in kombinierten Fragmenten: `alt`/`else` (if/else), `opt` (einfaches if), `loop` (Schleife mit Bedingung im Kopf); Fehlerfälle als eigener Zweig – die Exception als Antwort aus dem `alt` heraus; `ref` als Verweis auf ein anderes Diagramm; die Grenze: ein Sequenzdiagramm mit vier verschachtelten Rahmen sagt nichts mehr – dann zwei Diagramme; Notizen (`note`) sparsam
- **Übung:** uebungen/05-sequenz-logik
- **Prüffrage:** Welchen Rahmen nutzt du für ein Verhalten, das einem `if`/`else` im Code entspricht – und welchen für ein `if` ohne `else`? Und: Wie stellst du dar, dass ein Aufruf mit einer Exception statt einer Antwort endet?
- **Übersetzung:** en: Loops and conditions in sequence diagrams | fr: Boucles et conditions dans les diagrammes de séquence
- **Stufe:** 2

Leitfaden: Mit bekanntem Code verbinden – die Validierung aus einer früheren Übung, die
im Fehlerfall früh abbricht. Der Lernende zeichnet den Ablauf mit `loop` über die
Positionen einer Bestellung und `alt` für die fehlgeschlagene Prüfung.

### 07 Das Zustandsdiagramm: Ereignisse und Übergänge
- **Ziele:** Der Lebenszyklus *eines* Objekts: Startzustand (`[*] -->`), Endzustand (`--> [*]`), Zustände als abgerundete Rechtecke; Übergänge, ausgelöst durch Ereignisse; die Notation am Pfeil: `Ereignis [Bedingung] / Aktion`; interne Aktivitäten `entry /`, `do /`, `exit /`; wann ein Zustandsdiagramm nötig ist (Bestellungen, Verträge, Tickets, Ampeln) und wann es Overhead ist (reine Datenklassen); das Diagramm als Vorlage für das Zustandsmuster: jeder Zustand eine Klasse, jeder Pfeil eine Methode (Entwurfsmuster-Lektion 09)
- **Übung:** uebungen/06-zustandsautomat
- **Prüffrage:** Ein Ticket darf von `OFFEN` nach `IN_BEARBEITUNG` nur, wenn ein Bearbeiter zugewiesen ist – wie notierst du Ereignis und Bedingung an der Transition? Und: Was unterscheidet ein Zustandsdiagramm von einem Sequenzdiagramm in der Frage, die es beantwortet?
- **Übersetzung:** en: The state diagram: events and transitions | fr: Le diagramme d'états : événements et transitions
- **Stufe:** 2

Leitfaden: Die Übung zeichnet den Lebenszyklus eines Support-Tickets (Offen, In
Bearbeitung, Gelöst, Geschlossen, Wiedereröffnet) mit Bedingungen und Aktionen. Wer
dieses Diagramm lesen kann, schreibt in Entwurfsmuster-Übung 08 den Automaten ohne
`if`-Kaskaden – dort ist es eine Bestellung, hier bewusst etwas anderes.

### 08 Vom Diagramm zum Code (und zurück)
- **Ziele:** Die Synthese: ein Klassendiagramm in Java übersetzen – Klassen, Felder mit passendem Typ, `List<…>` für `*`-Beziehungen, Komposition erzeugt im Konstruktor, Aggregation bekommt gereicht, Realisierung wird `implements`; Reverse Engineering: fremden Code lesen und als Diagramm festhalten; Architektur-Sünden im Diagramm erkennen – zyklische Abhängigkeiten, Gottklassen, Pfeile von unten nach oben durch die Schichten; Refactoring im Diagramm planen (Interface einziehen), dann im Code ausführen
- **Übung:** uebungen/07-diagramm-zu-code
- **Prüffrage:** Im Klassendiagramm zeigt eine Linie von `Firma` zu `Mitarbeiter` mit `*` am Mitarbeiter-Ende – wie sieht das Feld in `Firma` aus? Und: Woran erkennst du im Diagramm eine Gottklasse, bevor du eine Zeile Code gelesen hast?
- **Übersetzung:** en: From diagram to code and back | fr: Du diagramme au code et retour
- **Stufe:** 2

Leitfaden: Die Abschlussübung schließt den Kreis: ein stark gekoppeltes Diagramm
analysieren, das Refactoring einzeichnen (Interface einziehen, Pfeile umdrehen) und den
Java-Code dazu schreiben. Die Tests prüfen beides – die `.puml` und den Code per
Reflection (Assoziationen als Collection, Interface implementiert).
