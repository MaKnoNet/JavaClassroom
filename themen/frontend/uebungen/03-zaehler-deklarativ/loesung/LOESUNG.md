# Lösung 03

`loesung/src/zaehler.js` ersetzt `src/zaehler.js`.

Warum so: Der Fehler im Ausgangscode ist kein Tippfehler, sondern ein Strukturproblem –
drei Listener müssen dieselben zwei DOM-Stellen konsistent halten, und beim vierten
Knopf vergisst es garantiert wieder jemand. Im deklarativen Aufbau gibt es diese
Verantwortung nicht: Die Listener ändern nur den Zustand, und `render` leitet *alles*
Sichtbare daraus ab. Parität kann nicht veralten, weil sie nie gespeichert, sondern
immer berechnet wird. `render` ist rein – testbar ohne DOM, wie die beiden ersten Tests
zeigen. Die Event Delegation ist die Folge davon, dass `innerHTML` die Knöpfe bei jedem
Rendern neu erzeugt: Ein Listener an der Wurzel überlebt das. Der Preis dieser einfachen
Fassung ist, dass jedes Mal das ganze Stück DOM neu entsteht – bei einem Zähler egal,
bei einer Tabelle mit tausend Zeilen nicht. Genau da setzen Lit und Co. an: gleiche Idee,
aber nur die Stellen aktualisieren, die sich geändert haben.
