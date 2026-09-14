# Lösung 07

**Diagramm:** `Gehaltsregel` als Interface, `StandardRegel ..|> Gehaltsregel`,
`Gehaltsrechner --> Gehaltsregel` statt `--> Mitarbeiter`, der Rückpfeil von
`Mitarbeiter` ist weg, und `Firma "1" *-- "*" Mitarbeiter`. Die Nutzung des Mitarbeiters
als Parameter ist eine gestrichelte Abhängigkeit (`..>`) – die schwächste Beziehung, kein
Feld.

**Code, Zeile für Zeile aus dem Diagramm:** `*` am Mitarbeiter-Ende → `List<Mitarbeiter>`
in `Firma`, Komposition → im Feld angelegt und nur als Kopie herausgegeben; Realisierung
→ `implements Gehaltsregel`; Assoziation `Gehaltsrechner --> Gehaltsregel` → ein Feld
vom Interface-Typ, per Konstruktor gereicht; Abhängigkeit `..> Mitarbeiter` → nur ein
Parameter.

Was das Refactoring bringt, zeigt der letzte Test: Eine andere Gehaltsregel ist ein
Lambda – kein Umbau am Rechner. Das ist die Strategie aus Entwurfsmuster-Lektion 03, hier
zuerst gezeichnet und dann programmiert.
