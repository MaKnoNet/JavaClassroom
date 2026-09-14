# Lösung 04

`Bestellung` bekommt einen privaten Konstruktor, eine statische Fabrikmethode
`fuer(kunde)` als Einstieg und eine geschachtelte Klasse `Builder` mit einer Methode je
Angabe, die `this` zurückgibt (Verkettung). Standardwerte liegen im Builder
(`express = false`, `rabatt = 0`), abgeleitete Werte im Konstruktor (Rechnungsadresse =
Lieferadresse, wenn nicht gesetzt), Prüfungen dort, wo sie hingehören: der Rabattbereich
sofort im Setter, die Pflichtangabe in `build()`.

Was der Builder *nicht* ist: eine Fabrik. Er macht das Erzeugen lesbar und prüfbar, nicht
austauschbar. Wann er lohnt: ab etwa vier optionalen Werten oder wenn Pflicht und
Standard sich mischen. Bei drei Pflichtwerten ohne Optionen reicht ein Record.

Vorbilder im JDK: `HttpRequest.newBuilder().uri(u).GET().build()`, `StringBuilder`,
`Stream.builder()`.
