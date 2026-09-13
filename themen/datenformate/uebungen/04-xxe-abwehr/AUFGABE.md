# Übung 04: XML von außen – der Parser liest, was man ihm sagt

## Aufgabe

`KundenXml.liesName` liest ein Kundenelement mit dem JDK-Parser in Standardeinstellung.
`./gradlew test` zeigt, was der Standard erlaubt: Der zweite Test schreibt eine Datei
mit einem Passwort auf die Platte und schickt XML, das diese Datei per **externer
Entity** einbindet – und der Parser liefert den Inhalt brav im Namensfeld zurück.

Härte den Parser so, dass

1. externe Entities nicht aufgelöst werden,
2. jedes `DOCTYPE` abgewiesen wird – auch ein harmloses,
3. gewöhnliches XML weiterhin gelesen wird.

## Abnahmekriterien

- `./gradlew test` ist grün (drei Tests).
- Die Härtung sitzt an **einer** Stelle (eigene Methode, die den Parser baut), nicht
  verstreut im Lesecode.
- Du kannst erklären, warum ein DOCTYPE-Verbot mehr schützt als das Abschalten
  externer Entities allein.

## Hinweise

1. `factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)` –
   damit wirft der Parser bei jedem DOCTYPE eine `SAXParseException`. Das ist die
   OWASP-Empfehlung für JAXP und deckt XXE und Entity-Bomben gleichzeitig ab.
2. Zusätzlich: `XMLConstants.FEATURE_SECURE_PROCESSING` auf `true`,
   `external-general-entities` und `external-parameter-entities` (Präfix
   `http://xml.org/sax/features/`) auf `false`, `setXIncludeAware(false)`,
   `setExpandEntityReferences(false)`.
3. Dasselbe Prinzip bei JSON und YAML: Jackson nie mit `activateDefaultTyping` und
   `Object` als Ziel (Übung 01), SnakeYAML nie mit `new Yaml()` ohne `SafeConstructor`
   – sonst bestimmt der Absender, welche Java-Klasse instanziiert wird. Und bei CSV
   die Formeln (Übung 02). Regel für alle Formate: Daten von außen sind Daten, nie
   Anweisungen.
