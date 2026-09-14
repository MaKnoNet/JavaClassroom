# Lösung 08

Interface `BestellZustand` mit `name()` und je einer Methode pro Übergang, die den
Folgezustand liefert; die `default`-Implementierungen werfen `IllegalStateException`.
Fünf Klassen `Neu`, `Bezahlt`, `Versandt`, `Storniert`, `Retourniert` überschreiben genau
die Übergänge, die das Zustandsdiagramm erlaubt. `Bestellung` delegiert:
`zustand = zustand.bezahlen()`.

Was der neue Zustand gekostet hat: eine Klasse `Retourniert` (drei Zeilen) und eine
Methode in `Versandt`. Im Startcode hätte er eine vierte Methode `retourniere()` mit `if`
und Änderungen an den drei anderen `if`-Bedingungen gebraucht – jede davon eine
Gelegenheit, einen Fall zu vergessen.

Die Regeln liegen jetzt dort, wo sie hingehören: bei dem Zustand, für den sie gelten.
`Storniert` und `Retourniert` sagen mit leerem Rumpf „von hier geht nichts mehr" – das
Interface erledigt das Werfen.

Leichte Variante für kleine Automaten: ein Enum, dessen Konstanten die Übergangsmethoden
überschreiben. Gleiches Muster, weniger Dateien, aber kein Zustand mit eigenen Feldern.
