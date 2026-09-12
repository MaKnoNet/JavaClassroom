# Lösung 02

```bash
git switch -c zutaten-ergaenzen
printf -- "- Prise Salz\n" >> rezept.txt
git add rezept.txt
git commit -m "Salz ergänzt"
git switch main
git merge zutaten-ergaenzen
git branch -d zutaten-ergaenzen
git log --oneline --graph
```

Warum so: Die Änderung entsteht isoliert im Branch; `main` bleibt bis zum Merge
unverändert. `-d` löscht nur, wenn der Branch gemergt ist – ein Schutz gegen Datenverlust.
