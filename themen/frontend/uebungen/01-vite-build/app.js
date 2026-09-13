// Vanilla JavaScript wie in JavaScript-Übung 02 – funktioniert, wenn man index.html
// direkt im Browser öffnet. Ein Build-Tool kennt diese Datei noch nicht.
let anzahl = 0;
const anzeige = document.getElementById('anzahl');
document.getElementById('knopf').addEventListener('click', () => {
  anzahl += 1;
  anzeige.textContent = String(anzahl);
});
