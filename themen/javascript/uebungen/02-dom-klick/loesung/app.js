const anzeige = document.querySelector("#anzeige");
let wert = 0;

function zeige() {
  anzeige.textContent = String(wert);
}

document.querySelector("#plus").addEventListener("click", () => {
  wert += 1;
  zeige();
});

document.querySelector("#minus").addEventListener("click", () => {
  wert = Math.max(0, wert - 1);
  zeige();
});

document.querySelector("#reset").addEventListener("click", () => {
  wert = 0;
  zeige();
});

zeige();
