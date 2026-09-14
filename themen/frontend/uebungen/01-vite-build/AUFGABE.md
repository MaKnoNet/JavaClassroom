# Übung 01: Ein Vanilla-Projekt unter Vite stellen

## Aufgabe

`index.html`, `app.js` und `style.css` sind ein Klickzähler, wie du ihn aus
JavaScript-Übung 02 kennst – Datei öffnen, fertig. Jetzt soll daraus ein Projekt werden,
das ein Build-Tool kennt:

1. Lege eine `package.json` an (Name, `"private": true`, `"type": "module"`) mit **Vite**
   als `devDependency` und den Skripten `dev`, `build`, `preview`.
2. `npm install` – danach gibt es `node_modules/`. Lege eine `.gitignore` an, die
   `node_modules/` und `dist/` ausschließt.
3. Vite bündelt nur Skripte, die als Modul geladen werden: `<script type="module"
   src="app.js">`.
4. `npm run dev` startet den Entwicklungsserver – ändere den Knopftext in `index.html`
   und sieh zu, wie der Browser ohne Neuladen nachzieht (Hot Module Replacement).
5. `npm run build` erzeugt `dist/` – die Auslieferung.

## Abnahmekriterien

- `npm run build` endet mit `✓ built`; `dist/index.html` verweist auf
  `assets/index-<hash>.js` und `assets/index-<hash>.css`.
- `npm run preview` zeigt den gebauten Zähler unter `http://localhost:4173`, und er zählt.
- `.gitignore` enthält `node_modules/` und `dist/`; `package-lock.json` bleibt **im**
  Repository.
- Du kannst sagen, was der Hash im Dateinamen bewirkt.

## Hinweise

1. `npm install --save-dev vite` schreibt den Eintrag in `package.json` und erzeugt
   `package-lock.json`. Die Lock-Datei hält *exakte* Versionen fest – im Team und in der
   Pipeline installiert `npm ci` genau diese, nicht „irgendeine passende".
2. Der Hash im Namen (`index-CeyL0XDW.js`) ändert sich mit dem Inhalt. Browser dürfen die
   Datei deshalb ewig cachen – eine neue Version hat einen neuen Namen. Das nennt sich
   *Cache Busting*.
3. **Firmenproxy:** Bricht `npm install` mit `UNABLE_TO_GET_ISSUER_CERT_LOCALLY` ab,
   prüft ein TLS-Proxy die Verbindung – dieselbe Ursache wie `PKIX path building failed`
   bei Gradle. Abhilfe maschinenlokal, nie im Repo: `npm config set cafile
   <pfad-zur-firmen-ca.pem>`. Das landet in `~/.npmrc`, dem Gegenstück zu
   `~/.gradle/gradle.properties`.
4. `dist/` nie ins Repository: Es ist ein Build-Ergebnis wie `build/` bei Gradle
   (Git-Lektion 05) und wird in der Pipeline neu erzeugt.
