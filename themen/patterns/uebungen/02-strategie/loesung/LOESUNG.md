# Lösung 02

Interface `VersandStrategie` mit einer Methode, drei Klassen `StandardVersand`,
`ExpressVersand`, `Abholung`, der Kontext `Versandkosten` bekommt die Strategie per
Konstruktor. Der Enum und der `switch` sind weg; die gemeinsame Zuschlagsregel liegt in
`Zuschlag`, damit die Strategien nichts voneinander erben müssen.

Der entscheidende Test ist `neueVersandartOhneAenderungAmKontext`: Die Drohne ist ein
Lambda im Test – eine Versandart, die beim Schreiben des Kontexts niemand kannte. Mit dem
`switch` hätte sie einen neuen Enum-Wert, einen neuen Zweig und eine neue Auslieferung von
`Versandkosten` gebraucht. Das ist Open/Closed.

Wo `Comparator` schon dasselbe war: `liste.sort(Comparator.comparing(Kunde::name))` –
`sort` ist der Kontext, der Comparator die Strategie.
