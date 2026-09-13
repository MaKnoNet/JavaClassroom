# Übung 04: Generics schreiben

## Aufgabe

`Stapel` funktioniert nur für Strings, `Paar` speichert alles als `Object`, und die
Methoden in `Werkzeuge` liefern `Object`. Der Test in `GenericsTest` **kompiliert deshalb
nicht** – er verlangt Typsicherheit ohne Casts. Mach die drei Klassen generisch:

1. `Stapel<T>` – ein Stapel für jeden Typ.
2. `Paar<A, B>` – ein Record mit zwei unabhängigen Typparametern.
3. `Werkzeuge.groesstes(List<T>)` liefert `T`, aber nur für Typen, die sich vergleichen
   lassen (`T extends Comparable<T>`); `Werkzeuge.endenVon(List<T>)` liefert `Paar<T, T>`
   aus erstem und letztem Element.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests).
- Kein `Object` mehr in den Signaturen der drei Klassen, kein Cast in den Tests.

## Hinweise

1. Klassen und Records bekommen den Parameter nach dem Namen: `class Stapel<T>`,
   `record Paar<A, B>(A erstes, B zweites)`. Innen ersetzt `T` bzw. `A`/`B` das
   bisherige `String`/`Object`.
2. Generische **Methoden** deklarieren den Parameter vor dem Rückgabetyp:
   `public static <T extends Comparable<T>> T groesstes(List<T> werte)`. Vergleichen mit
   `a.compareTo(b) > 0` oder gleich `Collections.max(werte)`.
