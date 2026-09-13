Warum so: `<T extends Comparable<T>>` sagt dem Compiler, dass `compareTo` existiert –
deshalb kann `groesstes` für `Integer` und `String` arbeiten, aber nicht für Typen ohne
Ordnung. `Paar<A, B>` hat zwei Parameter, weil die beiden Werte nichts miteinander zu tun
haben müssen; `endenVon` nutzt es als `Paar<T, T>`. Die drei Dateien ersetzen ihre
Gegenstücke unter `src/main/java/de/makno/lernen/`.
