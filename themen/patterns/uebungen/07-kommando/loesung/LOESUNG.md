# Lösung 07

Interface `Kommando` mit `ausfuehren()` und `rueckgaengig()`, zwei Kommandos `Schreiben`
und `EntfernenVomEnde`, und ein `Editor`, der `fuehreAus(kommando)` anbietet und jedes
Kommando auf einen Stapel legt; `rueckgaengig()` nimmt das oberste und dreht es um.

Der Punkt, an dem das Muster sich rechtfertigt, ist `EntfernenVomEnde`: Es *muss* sich
beim Ausführen merken, was es entfernt hat, sonst kann es nichts zurückgeben. Ein Lambda
könnte das nicht – es hat keinen Zustand zwischen zwei Aufrufen. Deshalb ist das Kommando
ein Objekt: Es trägt Parameter (Anzahl) und Verlauf (entfernter Text).

`Schreiben` kommt ohne gespeicherten Zustand aus: Rückgängig heißt „so viele Zeichen
entfernen, wie ich geschrieben habe" – die Länge kennt es aus dem Konstruktor.

Redo wäre ein zweiter Stapel: Was `rueckgaengig()` herunternimmt, wandert dorthin;
`wiederholen()` führt es erneut aus. Ein neues Kommando leert den Redo-Stapel.
