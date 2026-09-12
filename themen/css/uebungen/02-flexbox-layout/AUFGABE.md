# Übung 02: Drei Karten nebeneinander

## Aufgabe

1. `.karten` wird ein Flex-Container mit `gap: 1rem`.
2. Jede `.karte` bekommt `flex: 1`, einen Rahmen `1px solid #e5e7eb`, abgerundete Ecken
   `0.5rem` und Innenabstand `1rem`.
3. Unter 600 px Breite (`@media (max-width: 600px)`) sollen die Karten untereinander
   stehen (`flex-direction: column`).

## Abnahmekriterien

- Auf einem breiten Fenster stehen die drei Karten in einer Reihe, gleich breit.
- Bei schmalem Fenster (Browser-Ansicht auf „mobile" stellen) stehen sie untereinander.
- Der Inspektor zeigt für `.karten` `display: flex`.

## Hinweise

1. `display: flex` gehört auf den **Container**, `flex: 1` auf die **Kinder**.
2. Eine Media Query umschließt normale Regeln:
   `@media (max-width: 600px) { .karten { flex-direction: column; } }`.
