# Lösung 05

`Konfiguration` wird ein Record – kein `getInstance`, kein statisches Feld, öffentlicher
Konstruktor. `Preisrechner` bekommt die Konfiguration per Konstruktor. Der Test kann jetzt
19 %, 7 % und 8 % nebeneinander prüfen; mit dem Singleton war nur der eine fest verdrahtete
Wert erreichbar.

Was das Singleton lösen wollte: „genau eine Konfiguration im ganzen Programm". Das
Problem war nie das *eine*, sondern das *sich selbst holen*. Die Antwort heute: Es gibt
weiterhin eine Instanz – aber jemand anderes erzeugt sie und reicht sie herein. In Spring
ist das der Container: `@Bean Konfiguration` einmal, `@Service Preisrechner(Konfiguration
k)` bekommt sie – Scope Singleton, ohne dass eine Klasse `getInstance()` kennt.

Wo Singleton legitim bleibt: zustandslose Werkzeuge (`Collections.emptyList()`), Enums,
Logger-Fabriken – Dinge, die niemand austauschen will.
