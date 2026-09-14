# Übung 07: Kommando mit Undo

## Aufgabe

`Editor` ändert seinen Text direkt – `schreibe`, `entferneVomEnde`. Der Test will
**Rückgängig**: drei Änderungen, dreimal `rueckgaengig()`, wieder leer. Dafür wird jede
Änderung ein Objekt: Interface `Kommando` mit `ausfuehren()` und `rueckgaengig()`, Klassen
`Schreiben(editor, text)` und `EntfernenVomEnde(editor, anzahl)`, und im Editor
`fuehreAus(Kommando)` sowie `rueckgaengig()`.

## Abnahmekriterien

- `./gradlew test` ist grün (fünf Tests).
- `Kommando` hat genau zwei Methoden.
- `rueckgaengig()` ohne Historie wirft nichts.

## Hinweise

1. Der Editor braucht einen Stapel (`Deque<Kommando>`, `ArrayDeque`): `fuehreAus` führt
   aus und `push`t, `rueckgaengig` `pop`t und ruft `rueckgaengig()` am Kommando.
2. `Schreiben.rueckgaengig()` entfernt so viele Zeichen, wie es geschrieben hat – die Zahl
   kennt es aus dem Konstruktor.
3. `EntfernenVomEnde.rueckgaengig()` muss den entfernten Text zurückschreiben. Woher kennt
   es ihn? Nur, wenn es ihn sich beim Ausführen gemerkt hat. Das ist der Grund, warum ein
   Kommando ein Objekt mit Zustand ist und kein Lambda.
4. Die alten Methoden `schreibe`/`entferneVomEnde` bleiben im Editor – die Kommandos rufen
   sie. Das Muster fügt eine Schicht hinzu, es ersetzt nichts.
