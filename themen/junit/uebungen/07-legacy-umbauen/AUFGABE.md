# Übung 07: Legacy-Code sicher umbauen

## Aufgabe

`Rechnung.berechne` ist 40 Zeilen gewachsener Code: Magic Numbers, verschachtelte `if`s,
vier Regeln in einer Methode. `RechnungVerhaltenTest` ist grün – das sind
**Charakterisierungstests**: Sie beschreiben, was der Code heute tut, nicht was er tun
sollte. `RechnungStrukturTest` ist rot: Er wird grün, wenn `berechne` höchstens 15 Zeilen
hat, alle Zahlen benannte Konstanten sind und die Schritte in private Methoden ausgelagert
sind.

Baue um – **mit den Refactoring-Werkzeugen deiner IDE**, nicht per Hand – und lass nach
jedem Schritt die Tests laufen.

## Abnahmekriterien

- `./gradlew test` ist grün (neun Tests): die sechs Verhaltenstests bleiben grün, die drei
  Strukturtests werden grün.
- Kein Verhalten geändert – auch nicht die Eigenheit, dass die Steuer auf das Netto vor
  Rabatt gerechnet wird.

## Hinweise

1. Reihenfolge: erst *Extract Constant* für jede Zahl, dann *Extract Method* für
   Zeilenbetrag, Steuersatz, Rabatt, Versand, Rundung.
2. Eclipse: Refactor-Menü Alt+Shift+T, Extract Method Alt+Shift+M, Rename Alt+Shift+R.
   IntelliJ: Strg+Alt+M, Strg+Alt+C, Shift+F6. VS Code: Strg+Shift+R.
3. Tests nach jedem Schritt. Wird einer rot: Strg+Z, nicht debuggen.
4. Wer eine Zahl „verbessern" will: nicht jetzt. Refactoring ändert die Form, nie die
   Funktion. Notieren, später als eigene Änderung mit eigenem Test.
5. Die Zeilenzählung des Strukturtests ignoriert Leerzeilen; Kommentare zählen mit.
