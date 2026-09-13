# Übung 07: Den Commit finden, der es kaputt gemacht hat

## Vorbereitung (führt Claude aus)

```bash
mkdir -p arbeit/git/07-bisect/konfig && cd arbeit/git/07-bisect/konfig
git init -q -b main
printf "steuersatz = 19\n" > konfiguration.txt
printf '#!/usr/bin/env bash\n# Endet mit 0, wenn die Konfiguration in Ordnung ist, sonst mit 1.\ngrep -q "^steuersatz = 19$" konfiguration.txt\n' > pruefe.sh
chmod +x pruefe.sh
git add . && git commit -q -m "Konfiguration angelegt"
git tag v1
for n in $(seq 1 20); do
  printf "einstellung_%s = an\n" "$n" >> konfiguration.txt
  if [ "$n" = 13 ]; then sed -i 's/^steuersatz = 19$/steuersatz = 1.9/' konfiguration.txt; fi
  git commit -q -am "Einstellung $n ergänzt"
done
```

Danach zeigen: `./pruefe.sh; echo $?` gibt `1` (kaputt), `git log --oneline | wc -l`
gibt 21. Irgendwo in den 20 Commits seit `v1` ist der Steuersatz falsch geworden.

## Aufgabe

Arbeite in `arbeit/git/07-bisect/konfig`. `pruefe.sh` meldet, dass die Konfiguration
kaputt ist; bei `v1` war sie noch in Ordnung. Finde mit `git bisect` den **ersten**
Commit, der den Fehler eingeführt hat – ohne alle 20 durchzusehen. Nenne seine Nachricht.

## Abnahmekriterien

- Du nennst die Nachricht des schuldigen Commits (Claude kennt sie).
- `git bisect log` belegt die Suche: höchstens fünf Schritte zwischen `good` und `bad`.
- Danach `git bisect reset` – `git status` zeigt wieder `main`.

## Hinweise

1. `git bisect start`, dann `git bisect bad` (jetzt ist es kaputt) und `git bisect good v1`
   (da ging es noch). Git checkt den mittleren Commit aus; du prüfst mit `./pruefe.sh`
   und antwortest `git bisect good` oder `git bisect bad`. Nach etwa fünf Runden steht
   der Täter fest: `<hash> is the first bad commit` (neuere Git-Versionen: `first 'bad' commit`).
2. Wenn ein Skript die Prüfung übernehmen kann, macht Git alles allein:
   `git bisect run ./pruefe.sh`. Endet das Skript mit 0, gilt der Commit als gut, sonst
   als schlecht. Im echten Projekt ist das Skript meist `./gradlew test`.
3. Warum so wenige Schritte: Binärsuche halbiert den Bereich jedes Mal – 20 Commits
   brauchen 5 Runden, 1000 Commits nur 10. Deshalb lohnt sich bisect gerade bei langen
   Historien.
