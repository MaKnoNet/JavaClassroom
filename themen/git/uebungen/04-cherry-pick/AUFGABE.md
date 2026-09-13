# Übung 04: Einen einzelnen Commit holen

## Vorbereitung (führt Claude aus)

```bash
mkdir -p arbeit/git/04-cherry-pick/rechner && cd arbeit/git/04-cherry-pick/rechner
git init -q -b main
printf "teile(a, b):\n  return a / b\n" > rechner.txt
printf "1) Addieren\n2) Teilen\n" > menue.txt
git add . && git commit -q -m "Rechner und Menü angelegt"
git switch -q -c alte-funktion
printf "1) Addieren\n2) Teilen\n3) Potenzieren\n" > menue.txt
git commit -q -am "Menü um Potenzieren erweitert"
printf "teile(a, b):\n  wenn b == 0: Fehler 'Division durch 0'\n  return a / b\n" > rechner.txt
git commit -q -am "Bugfix: Division durch 0 abgefangen"
printf "noch unfertig\n" > experiment.txt
git add experiment.txt && git commit -q -m "Experiment begonnen"
git switch -q main
```

Danach `git log --oneline --all --graph` zeigen: `main` mit einem Commit, `alte-funktion`
mit drei weiteren – darunter ein Bugfix, der eigentlich auf `main` gehört.

## Aufgabe

Arbeite in `arbeit/git/04-cherry-pick/rechner`. Der Bugfix wurde versehentlich auf dem
alten Feature-Branch committet. Hole **genau diesen einen Commit** nach `main` – ohne das
Menü und ohne das Experiment.

## Abnahmekriterien

- `git log --oneline main` zeigt zwei Commits; der neue trägt die Bugfix-Nachricht.
- `rechner.txt` auf `main` enthält die Zeile mit `Division durch 0`.
- `menue.txt` auf `main` hat weiterhin zwei Zeilen; `experiment.txt` existiert auf `main`
  nicht.
- `alte-funktion` ist unverändert (immer noch drei Commits über dem Anfang).

## Hinweise

1. Erst den Commit finden: `git log --oneline alte-funktion`. Die sieben Zeichen vorne sind
   der Hash, den du brauchst.
2. `git cherry-pick <hash>` – auf `main` stehend. Git kopiert die *Änderung* des Commits
   als neuen Commit hierher; der alte bleibt, wo er war. Mit `-x` hängt Git eine Zeile
   `(cherry picked from commit …)` an – nützlich, wenn beide Branches weiterleben.
3. Wenn ein Cherry-Pick einen Konflikt meldet, gilt dasselbe wie beim Merge (Übung 03):
   lösen, `git add`, dann `git cherry-pick --continue`; oder `git cherry-pick --abort`.
