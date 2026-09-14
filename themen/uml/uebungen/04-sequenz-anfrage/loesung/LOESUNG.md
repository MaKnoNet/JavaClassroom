# Lösung 04

Vier Teilnehmer in Aufrufreihenfolge (der `actor` links), drei synchrone Aufrufe mit `->`,
drei Antworten mit `-->`, je Schicht ein `activate`/`deactivate`-Paar. Im Bild: Der
Balken des Clients gibt es nicht, weil er nur wartet; die drei Balken der Schichten sind
ineinander geschachtelt – der Controller ist so lange aktiv, wie Service und Repository
darunter arbeiten.

Das ist der Standardfluss jeder Spring-Boot-Anfrage (Spring-Lektion 02). Wer ihn
zeichnen kann, kann ihn auch im Debugger verfolgen.
