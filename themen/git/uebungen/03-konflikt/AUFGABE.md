# Übung 03: Einen Merge-Konflikt lösen

## Vorbereitung (führt Claude aus)

```bash
mkdir -p arbeit/git/03-konflikt/gruss && cd arbeit/git/03-konflikt/gruss
git init -q -b main
printf "Hallo Welt\n" > begruessung.txt
git add begruessung.txt && git commit -q -m "Begrüßung angelegt"
git switch -q -c englisch
printf "Hello World\n" > begruessung.txt
git commit -q -am "Begrüßung übersetzt"
git switch -q main
printf "Hallo Welt!\n" > begruessung.txt
git commit -q -am "Ausrufezeichen ergänzt"
```

Danach `git log --oneline --graph --all` zeigen: zwei Branches, die dieselbe Zeile
unterschiedlich geändert haben.

## Aufgabe

Arbeite in `arbeit/git/03-konflikt/gruss`. Merge den Branch `englisch` nach `main`.
Git wird einen Konflikt melden. Löse ihn so, dass **beide** Änderungen erhalten bleiben:
Die Datei soll am Ende genau `Hello World!` enthalten. Schließe den Merge ab.

## Abnahmekriterien

- `begruessung.txt` enthält genau eine Zeile: `Hello World!` – keine `<<<<<<<`, `=======`
  oder `>>>>>>>` mehr.
- `git log --oneline --graph` zeigt einen Merge-Commit mit zwei Eltern.
- `git status` ist sauber, `git branch --merged main` listet `englisch`.

## Hinweise

1. `git merge englisch` bricht mit `CONFLICT (content)` ab. Das ist kein Fehler, das ist
   eine Frage an dich: Git weiß nicht, welche Zeile gewinnen soll.
2. Öffne die Datei. Zwischen `<<<<<<< HEAD` und `=======` steht deine Fassung (`main`),
   zwischen `=======` und `>>>>>>> englisch` die andere. Schreibe hin, was gelten soll,
   und lösche alle Markierungen.
3. Dann `git add begruessung.txt` – damit sagst du „gelöst" – und `git commit` ohne `-m`:
   Git schlägt die Merge-Nachricht selbst vor. Wenn du abbrechen willst: `git merge --abort`
   stellt den Zustand vor dem Merge wieder her.
