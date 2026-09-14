# Lösung 02

Neun Zeilen im Kasten – eine je Feld und Methode, mit Sichtbarkeit (`-` `+` `#` `~`),
Name, Parametern als `name: Typ` und Rückgabetyp nach dem Doppelpunkt. `{static}`
unterstreicht `MAX_STAND`; der Konstruktor hat keinen Rückgabetyp.

Warum der Typ hinten steht: UML ist sprachunabhängig, und `name: Typ` liest sich wie
ein Satz („inhaber vom Typ String"). Java hat die Reihenfolge von C geerbt; Kotlin,
TypeScript, Swift und Scala schreiben es wie UML.

`final` hat in UML keine eigene Notation ({readOnly} wäre die Entsprechung); für die
Übung lässt man es weg.
