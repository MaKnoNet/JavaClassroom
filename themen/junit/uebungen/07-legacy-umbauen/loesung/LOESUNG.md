# Lösung 07

`berechne` ist auf acht Zeilen geschrumpft und liest sich wie die Fachregel: Zeilenbetrag,
Steuer, Rabatt, Versand, runden. Jede Zahl hat einen Namen, jede Regel eine Methode. Die
sechs Charakterisierungstests sind unverändert grün – der Beweis, dass sich das Verhalten
nicht geändert hat, inklusive der Eigenheit „Steuer auf Netto vor Rabatt".

Der Weg mit IDE-Werkzeugen, nicht von Hand:

1. Zahl markieren → *Extract Constant* (Eclipse: Refactor-Menü Alt+Shift+T, IntelliJ:
   Strg+Alt+C) – die IDE ersetzt alle Vorkommen.
2. Block markieren → *Extract Method* (Eclipse: Alt+Shift+M, IntelliJ: Strg+Alt+M) – die
   IDE findet Parameter und Rückgabewert selbst.
3. Nach jedem Schritt Tests laufen lassen. Rot heißt: letzten Schritt zurück (Strg+Z),
   nicht suchen.
4. *Rename* (Alt+Shift+R / Shift+F6) für Namen, die beim Extrahieren noch `x` hießen.

Was man nicht tut: die Eigenheit „Steuer auf Netto vor Rabatt" nebenbei „korrigieren".
Das wäre eine Verhaltensänderung – eigenes Ticket, eigener Commit, mit Fachabteilung.
Refactoring ändert die Form, nie die Funktion.
