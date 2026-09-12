# Übung 02: Ein Formular

## Aufgabe

Baue unter der Überschrift ein Anmeldeformular:

1. `form` mit `method="post"`.
2. Textfeld für den Namen (`type="text"`, `required`) mit `label`.
3. E-Mail-Feld (`type="email"`, `required`) mit `label`.
4. Auswahl `select` „Rolle" mit den Optionen Azubi, Student, Kollege, mit `label`.
5. Checkbox „Newsletter" mit `label`.
6. Button „Absenden".

Jedes `label` ist über `for`/`id` mit seinem Feld verbunden. Jedes Feld hat ein `name`.

## Abnahmekriterien

- Klick auf ein Label setzt den Fokus in das zugehörige Feld (das prüft die Verbindung).
- Absenden ohne Namen wird vom Browser verhindert (`required`).
- Vier Eingabeelemente plus ein Button sind vorhanden.

## Hinweise

1. `<label for="name">Name</label> <input id="name" name="name" type="text" required>` –
   `for` zeigt auf die `id`.
2. Optionen einer Auswahl: `<select id="rolle" name="rolle"><option>Azubi</option>…</select>`.
