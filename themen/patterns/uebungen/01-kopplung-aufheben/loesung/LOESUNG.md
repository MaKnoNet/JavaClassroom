# Lösung 01

Interface `Antrieb` mit `starte()` und `reichweiteKm()`; `BenzinMotor` und `Elektromotor`
implementieren es; `Auto` bekommt den Antrieb per Konstruktor; `HybridAntrieb` setzt zwei
Antriebe zusammen. `ElektroAuto` wird zur Einzeiler-Unterklasse und kann weg.

Der Kern: Vor dem Umbau kannte `Auto` die Klasse `BenzinMotor` (Kopplung an eine
Implementierung), danach nur den Vertrag `Antrieb`. Der Hybrid zeigt den Unterschied
zwischen Vererbung und Komposition am Test: `class HybridAuto extends Auto` hätte den
Code beider Unterklassen kopieren müssen; `new Auto(new HybridAntrieb(…))` braucht keine
Zeile im Auto.
