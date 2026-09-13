Warum so: `AtomicInteger` statt `synchronized`, weil ein einzelner Zähler genau der Fall
ist, für den es gebaut wurde – keine Sperre, kein Deadlock-Risiko. `synchronized` wäre
ebenso richtig und die bessere Wahl, sobald mehrere Felder zusammen konsistent bleiben
müssen. Beim Pool: try-with-resources schließt ihn auch bei Fehlern; `InterruptedException`
wird nicht verschluckt, sondern der Interrupt-Status wiederhergestellt. Die Dateien
ersetzen ihre Gegenstücke unter `src/main/java/de/makno/lernen/`.
