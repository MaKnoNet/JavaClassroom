// Imperativ: Jeder Klick ändert das DOM von Hand – und jede Stelle muss an alles denken.

export function erzeugeZaehler(wurzel) {
  let wert = 0;

  wurzel.innerHTML = `
    <p>Wert: <strong class="wert">0</strong> (<span class="paritaet">gerade</span>)</p>
    <button class="plus">+</button>
    <button class="minus">−</button>
    <button class="reset">Zurücksetzen</button>`;

  const wertAnzeige = wurzel.querySelector('.wert');
  const paritaetAnzeige = wurzel.querySelector('.paritaet');

  wurzel.querySelector('.plus').addEventListener('click', () => {
    wert += 1;
    wertAnzeige.textContent = String(wert);
    paritaetAnzeige.textContent = wert % 2 === 0 ? 'gerade' : 'ungerade';
  });

  wurzel.querySelector('.minus').addEventListener('click', () => {
    wert -= 1;
    wertAnzeige.textContent = String(wert);
    paritaetAnzeige.textContent = wert % 2 === 0 ? 'gerade' : 'ungerade';
  });

  wurzel.querySelector('.reset').addEventListener('click', () => {
    wert = 0;
    wertAnzeige.textContent = '0';
    // Hier hat jemand die Parität vergessen. Sieht man nicht – bis der Wert vorher ungerade war.
  });
}
