# Lösung 03

Sechs Linien, vier Arten:

| Linie | Art | Woran erkennbar |
|---|---|---|
| `Kunde --|> Person` | Generalisierung | durchgezogen, leeres Dreieck an der Oberklasse |
| `Kunde ..|> Zahlungsfaehig` | Realisierung | gestrichelt, leeres Dreieck am Interface |
| `Kunde "1" o-- "1" Adresse` | Aggregation | offene Raute am Ganzen – die Adresse überlebt |
| `Kunde "1" --> "*" Bestellung` | gerichtete Assoziation | Pfeil, Multiplizitäten an den Enden |
| `Bestellung "1" *-- "1..*" Bestellposition` | Komposition | gefüllte Raute – Positionen sterben mit |
| `Bestellposition "*" --> "1" Artikel` | gerichtete Assoziation | die Position kennt den Artikel, nicht umgekehrt |

Die Raute sitzt immer am **Ganzen**; wer sie an das Teil setzt, dreht die Aussage um.
Die Multiplizität steht am Ende, das sie beschreibt: `"1..*"` neben Bestellposition heißt
„eine Bestellung hat 1 bis viele Positionen".

Was das für den Code bedeutet: Komposition → die Bestellung erzeugt und besitzt ihre
Positionen (`List<Bestellposition>`, im Konstruktor angelegt); Aggregation → die Adresse
wird dem Kunden gereicht; `-->` → ein Feld beim Pfeilanfang, keins beim Pfeilende.
