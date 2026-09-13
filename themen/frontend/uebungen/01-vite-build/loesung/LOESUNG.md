# Lösung 01

Drei Dateien: `package.json` und `.gitignore` kommen neu in den Projektordner,
`index.html` ersetzt die vorhandene (einzige Änderung: `type="module"` am Script).
Danach `npm install`, `npm run build`.

Warum so: `package.json` ist das Gegenstück zu `build.gradle` – Abhängigkeiten,
Skripte, Name. Vite steht unter `devDependencies`, weil der Browser es nie sieht; es
arbeitet nur beim Entwickeln und Bauen. `"type": "module"` macht ES-Module zum Standard
für alle `.js`-Dateien des Projekts. Das `type="module"` am Script-Tag ist der Einstieg
für Vite: Von dort verfolgt es alle `import`s und das `<link>` zum Stylesheet und packt
alles, was erreichbar ist, in `dist/assets/` – minifiziert, mit Hash im Namen, ungenutzter
Code fällt weg (*Tree Shaking*). Ohne Build-Tool lädt der Browser jede Datei einzeln,
unkomprimiert, und kann sie nicht sicher cachen; mit vielen Modulen und Bibliotheken wird
das langsam und unübersichtlich. Der Dev-Server dagegen bündelt gar nicht – er liefert
Module einzeln und tauscht bei einer Änderung nur das betroffene aus (HMR), deshalb ist er
so schnell.
