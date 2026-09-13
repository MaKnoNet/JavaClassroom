# Lösung 07

Von Hand:

```bash
git bisect start
git bisect bad
git bisect good v1
./pruefe.sh && git bisect good || git bisect bad     # wiederholen, bis Git fertig ist
git bisect log
git bisect reset
```

Automatisch:

```bash
git bisect start HEAD v1
git bisect run ./pruefe.sh
git bisect reset
```

Ergebnis: **„Einstellung 13 ergänzt"** – der Commit hat nebenbei `steuersatz = 19` in
`1.9` geändert. Die Nachricht verrät nichts davon; genau deshalb findet man solche Fehler
nicht durch Lesen der Historie, sondern durch Prüfen.

Warum so: Bisect braucht nur zwei Dinge – einen bekannt guten und einen bekannt schlechten
Stand sowie eine Ja/Nein-Prüfung. Alles dazwischen erledigt die Binärsuche. Mit `run` wird
aus der Prüfung ein Skript, und Git sucht ohne Zutun – bei 1000 Commits in zehn Schritten.
