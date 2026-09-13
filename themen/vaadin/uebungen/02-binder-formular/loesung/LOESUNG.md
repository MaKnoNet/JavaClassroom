Warum so: `writeBean` statt `setBean`, damit das Objekt erst entsteht, wenn alle Regeln
erfüllt sind – kein halb gefülltes Bean. Die Validatoren stehen an der Bindung, nicht im
Listener: Vaadin markiert die Felder selbst, und der Listener muss nichts über die Regeln
wissen. Die `ValidationException` wird nicht verschluckt, sondern in eine Rückmeldung
übersetzt. Die Datei ersetzt `src/main/java/de/makno/lernen/PersonFormularView.java`.
