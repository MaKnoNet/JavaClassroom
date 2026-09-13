Die Datei ersetzt `src/main/java/de/makno/lernen/KundenCsv.java`.

Warum so: CSV sieht trivial aus und ist es nicht. `split(";")` zerlegt `"Meier; Anna"`
mitten im Namen, lässt die Anführungszeichen stehen und kennt weder `""` als
maskiertes Anführungszeichen noch Zeilenumbrüche im Feld. Commons CSV setzt RFC 4180 um
– Lesen und Schreiben aus derselben Basis, damit beide Seiten dieselben Regeln haben.
Die eine Falle darin: `skipHeaderRecord` steuert *beides* – beim Parser „erste Zeile
überspringen", beim Printer „Kopfzeile nicht schreiben". Ein Format für beide verliert
deshalb entweder die Kopfzeile oder den ersten Datensatz; darum zwei abgeleitete Formate. Semikolon, weil im deutschsprachigen Raum das Komma die Dezimalstelle ist und
Excel CSV genau so erwartet. Zugriff über Spaltennamen (`zeile.get("Name")`) statt
Index: Eine neue Spalte in der Mitte bricht dann nichts.

Die Entschärfung ist ein Export-Thema: Die Gefahr entsteht, wenn jemand die Datei in
Excel öffnet und der Inhalt eines Feldes mit `=` beginnt – Excel rechnet, ruft URLs auf,
kann Daten abfließen lassen. Das Hochkomma zwingt Excel, den Inhalt als Text zu
behandeln. Die Liste `= + - @ Tab CR` ist die OWASP-Empfehlung; ein bloßes `-2` ist
harmlos, `-2+cmd|…` nicht, und man kann beim Export nicht unterscheiden – also alle.
Beim Import passiert das nicht, dort sind es nur Strings.
