# Übung 01: Kopplung aufheben

## Aufgabe

`Auto` baut sich seinen Motor selbst: `new BenzinMotor()` steht fest im Feld. Für ein
Elektroauto gibt es deshalb eine Unterklasse `ElektroAuto`, die `fahre()` überschreibt
und den Code kopiert. Der Test verlangt drei Dinge, die so nicht gehen: ein `Auto`, das
seinen Antrieb von außen bekommt; einen Elektroantrieb ohne Unterklasse; einen
**Hybrid**, der beide Motoren kombiniert – ohne dritte Auto-Klasse.

## Abnahmekriterien

- `./gradlew test` ist grün (vier Tests).
- `Antrieb` ist ein Interface; `Auto` kennt keine konkrete Motorklasse mehr (kein `new`
  im Auto, kein Import von `BenzinMotor`/`Elektromotor`).
- `HybridAntrieb` ist eine Komposition aus zwei `Antrieb`-Objekten, keine Unterklasse
  von `Auto`.

## Hinweise

1. Was braucht `Auto` vom Motor wirklich? Zwei Methoden. Genau die gehören in das
   Interface – nicht mehr.
2. Der Antrieb kommt per Konstruktor: `public Auto(Antrieb antrieb)`. Das ist Dependency
   Injection von Hand (Java-Lektion 11).
3. `HybridAntrieb implements Antrieb` und **hat** zwei Antriebe: `starte()` verbindet beide
   Texte mit ` + `, `reichweiteKm()` addiert.
4. `ElektroAuto` kompiliert nach dem Umbau nicht mehr (kein Konstruktor ohne Argument).
   Entweder löschen – oder als Einzeiler `super(new Elektromotor())` behalten und im
   Gespräch begründen, warum sie überflüssig ist.
