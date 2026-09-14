# Lösung 06

Sieben Zeilen: Start, vier Übergänge, eine interne Aktivität, Ende. Die Notation am Pfeil
ist immer dieselbe – `Ereignis [Bedingung] / Aktion` – und jeder Teil ist optional außer
dem Ereignis. `do / bearbeite` steht im Zustand, nicht am Pfeil: Es läuft, solange das
Ticket dort ist, nicht beim Wechsel.

Was das Diagramm für Entwurfsmuster-Übung 08 vorbereitet: Jeder Zustand wird eine Klasse
(`Offen`, `InBearbeitung`, …), jedes Ereignis eine Methode des Zustands-Interface
(`zuweisen()`, `loesen()`, …), die Bedingung landet als `if` in genau der Methode des
Zustands, der den Pfeil verlässt – und nirgends sonst.
