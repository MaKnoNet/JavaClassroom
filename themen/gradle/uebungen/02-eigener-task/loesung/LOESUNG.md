Warum so: `tasks.register` legt den Task faul an – Gradle konfiguriert ihn nur, wenn er
gebraucht wird. `doLast` trennt Konfiguration von Ausführung; `dependsOn` macht die
Reihenfolge explizit statt sie durch Aufrufreihenfolge zu erzwingen.
Die Datei ersetzt `build.gradle` im Projektordner.
