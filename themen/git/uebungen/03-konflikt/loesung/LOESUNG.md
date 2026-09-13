# Lösung 03

```bash
git merge englisch            # CONFLICT (content): Merge conflict in begruessung.txt
printf "Hello World!\n" > begruessung.txt
git add begruessung.txt
git commit --no-edit          # nimmt die vorgeschlagene Nachricht "Merge branch 'englisch'"
git log --oneline --graph
```

Warum so: Ein Konflikt ist eine Entscheidung, die nur ein Mensch treffen kann – Git kennt
den Inhalt nicht, nur die Zeilen. `git add` ist hier nicht „Datei hinzufügen", sondern
„Konflikt in dieser Datei ist gelöst"; erst dann lässt Git den Commit zu. Der Merge-Commit
hat zwei Eltern, deshalb erscheint er im Graph als Zusammenführung.
