---
thema: git
titel: Git
voraussetzungen: []
zielgruppe: [anfaenger, fortgeschritten]
reihenfolge: 10
---

# Git

Git ist das Gedächtnis eines Projekts: Jede Änderung wird festgehalten, jede Version ist
wiederherstellbar, und mehrere Menschen können am selben Code arbeiten, ohne sich gegenseitig
zu überschreiben. Ohne Git gibt es keine Teamarbeit in der Softwareentwicklung.

Am Ende kann der Lernende ein Repository anlegen, Änderungen nachvollziehbar committen, mit
Branches arbeiten, Konflikte lösen und mit einem Remote (GitHub, GitLab, Firmenserver)
zusammenarbeiten. Die Lektionen 10 bis 14 gehen darüber hinaus: Historie gezielt
formen, Verlorenes zurückholen, Fehler in langen Historien einkreisen und Spuren lesen –
das Handwerkszeug, das aus einem Git-Nutzer jemanden macht, den das Team fragt.

## Lektionen

### 01 Warum Versionierung, erstes Repository
- **Ziele:** Verstehen, was ein Commit ist; `git init`, `git add`, `git commit`, `git log`, `git status` anwenden; Name und E-Mail konfigurieren
- **Übung:** uebungen/01-erstes-repo
- **Prüffrage:** Was ist der Unterschied zwischen dem Arbeitsverzeichnis, der Staging Area und dem Repository?
- **Übersetzung:** en: Why version control, first repository | fr: Pourquoi versionner, premier dépôt
- **Stufe:** 1

Leitfaden: Mit der Frage beginnen, wie der Lernende bisher Versionen gesichert hat
(`projekt_final_v2_neu`). Die drei Bereiche als Schreibtisch, Paketannahme und Archiv erklären.

### 02 Branches und Merge
- **Ziele:** Einen Branch anlegen, darauf committen, in `main` mergen; `git branch`, `git switch`, `git merge` anwenden; Fast-Forward von Merge-Commit unterscheiden
- **Übung:** uebungen/02-branch-und-merge
- **Prüffrage:** Warum arbeitet man an einem Feature in einem eigenen Branch und nicht direkt in `main`?
- **Übersetzung:** en: Branches and merge | fr: Branches et fusion
- **Stufe:** 1

### 03 Änderungen ansehen und zurücknehmen
- **Ziele:** `git diff`, `git restore`, `git reset --soft`, `git revert` unterscheiden und sicher einsetzen
- **Prüffrage:** Wann nimmt man `revert` statt `reset`?
- **Übersetzung:** en: Inspecting and undoing changes | fr: Voir et annuler des modifications
- **Stufe:** 1

Leitfaden: Faustregel – alles, was schon geteilt ist, wird mit `revert` zurückgenommen.

### 04 Remote: clone, push, pull
- **Ziele:** Ein Repository klonen, Änderungen hochladen und holen; `origin`, `git fetch` vs. `git pull`
- **Prüffrage:** Was passiert bei `git pull` genau – aus welchen zwei Befehlen besteht es?
- **Übersetzung:** en: Remote: clone, push, pull | fr: Dépôt distant : clone, push, pull
- **Stufe:** 1

### 05 .gitignore und gute Commits
- **Ziele:** Build-Reste ausschließen; kleine, thematisch geschlossene Commits mit sprechender Nachricht schreiben; Zeilenenden im gemischten Team: `.gitattributes` mit `* text=auto eol=lf` (und `*.bat eol=crlf`) statt `core.autocrlf` auf jedem Rechner – sonst zeigt Git „300 geänderte Dateien", obwohl niemand etwas geändert hat
- **Prüffrage:** Warum gehört `build/` nicht ins Repository, `build.gradle` aber schon? Und: Ein Kollege am Mac sieht nach deinem Windows-Commit jede Zeile als geändert – was ist passiert, und was verhindert es dauerhaft?
- **Übersetzung:** en: .gitignore and good commits | fr: .gitignore et bons commits
- **Stufe:** 1

### 06 Konflikte lösen
- **Ziele:** Einen Merge-Konflikt erkennen, die Markierungen lesen, auflösen und den Merge abschließen; `git merge --abort` als Notausgang
- **Übung:** uebungen/03-konflikt
- **Prüffrage:** Woran erkennt Git, dass es einen Konflikt gibt, und woran erkennt man ihn in der Datei?
- **Übersetzung:** en: Resolving conflicts | fr: Résoudre les conflits
- **Stufe:** 2

### 07 Rebase und Historie
- **Ziele:** `git rebase` verstehen, `git log --graph` lesen, den Unterschied zu Merge erklären; Regel „nie geteilte Historie umschreiben"
- **Prüffrage:** Was ändert Rebase an den Commits, was Merge nicht ändert?
- **Übersetzung:** en: Rebase and history | fr: Rebase et historique
- **Stufe:** 2

### 08 Alltag im Team
- **Ziele:** Ablauf Branch → Push → Pull Request → Review → Merge; Branch-Namen, Commit-Konventionen (`feat:`, `fix:`)
- **Prüffrage:** Was prüft ein Reviewer, was die Tests nicht prüfen können?
- **Übersetzung:** en: Everyday teamwork | fr: Le quotidien en équipe
- **Stufe:** 2

### 09 Worktrees: mehrere Branches gleichzeitig
- **Ziele:** `git stash` / `stash pop` / `stash list` für „kurz weglegen" – und seine Grenze (leicht vergessen, kein Branch, kein Commit); `git worktree add`, `list`, `remove`; einen zweiten Branch in einem eigenen Ordner auschecken, ohne die laufende Arbeit zu stashen; wann Worktree, wann Stash, wann Clone
- **Prüffrage:** Du arbeitest an einem Feature und sollst schnell einen Hotfix auf `main` machen – warum ist ein Worktree hier besser als `git stash`?
- **Übersetzung:** en: Worktrees: several branches at once | fr: Worktrees : plusieurs branches en même temps
- **Stufe:** 2

Leitfaden: Ein Repository, mehrere Arbeitsverzeichnisse – jedes mit eigenem Branch, alle
teilen dieselben Objekte und dieselbe Historie. `git worktree add ../hotfix main` legt
neben dem Projekt einen Ordner an, in dem `main` ausgecheckt ist; die IDE kann beide
gleichzeitig offen haben, Builds laufen getrennt. Typische Fehler zeigen: derselbe Branch
kann nicht in zwei Worktrees ausgecheckt sein; `remove` räumt den Ordner, `prune` die
Reste weg. Abgrenzung: Stash für „kurz weglegen", Worktree für „parallel arbeiten", Clone
nur, wenn es wirklich ein zweites Repository sein soll.

### 10 Rosinenpicken: git cherry-pick
- **Ziele:** Einen einzelnen Commit von einem Branch auf einen anderen kopieren, ohne den ganzen Branch zu mergen; Hash finden (`git log --oneline <branch>`), `git cherry-pick <hash>`, `-x` als Herkunftsvermerk; Konflikte beim Cherry-Pick lösen (`--continue`, `--abort`); wann Cherry-Pick richtig ist (Hotfix auf Release-Branch, versehentlich falscher Branch) und wann er Doppelarbeit erzeugt
- **Übung:** uebungen/04-cherry-pick
- **Prüffrage:** Du hast einen dringenden Bugfix aus Versehen auf einem alten Feature-Branch statt auf `main` committet. Wie holst du genau diesen einen Commit sauber auf `main` – und warum hat er dort einen anderen Hash?
- **Übersetzung:** en: Cherry-picking single commits | fr: Cherry-pick : reprendre un seul commit
- **Stufe:** 2

### 11 Die perfekte Historie: interaktiver Rebase
- **Ziele:** Die eigene Historie vor dem Pull Request aufräumen; `git rebase -i`: `pick`, `squash`, `fixup`, `reword`, `drop`, Reihenfolge ändern; Editor konfigurieren (`core.editor`) – sonst landet man in `vim`; `git rebase --abort`; den umgeschriebenen Branch veröffentlichen: `git push --force-with-lease`, nie `--force`; die Regel aus Lektion 07 bleibt: nur eigene, ungeteilte Historie
- **Übung:** uebungen/05-historie-aufraeumen
- **Prüffrage:** Was ist der Unterschied zwischen einem normalen `git rebase` und einem interaktiven Rebase mit `squash`? Und: Warum `--force-with-lease` statt `--force`?
- **Übersetzung:** en: The perfect history: interactive rebase | fr: L'historique parfait : rebase interactif
- **Stufe:** 2

Leitfaden: Claude Code kann `git rebase -i` nicht selbst ausführen (interaktiv) – der
Lernende macht ihn in seinem Terminal, die Abnahme läuft über `git log` und `git diff
vorher feature-login`. Vorher `core.editor` prüfen; ein Azubi, der zum ersten Mal in
`vim` landet, lernt in dieser Stunde nichts über Rebase. Die Lösung zeigt zusätzlich die
`GIT_SEQUENCE_EDITOR`-Variante, mit der Claude die Übung ohne Editor nachvollziehen kann.

### 12 Das Sicherheitsnetz: git reflog
- **Ziele:** Git vergisst lokal fast nichts: `git reflog` als Protokoll aller `HEAD`-Bewegungen; „verlorene" Commits nach `git reset --hard` oder einem missglückten Rebase zurückholen (`git reset --hard HEAD@{n}`); gelöschte Branches wiederherstellen (`git branch <name> <hash>`); die Grenzen: nur Committetes, nur lokal, läuft nach 90 Tagen ab; warum ein abgelehnter Push (`rejected`) meist kein Fall für Gewalt ist, sondern für `git pull --rebase`
- **Übung:** uebungen/06-reflog
- **Prüffrage:** Du hast versehentlich `git reset --hard HEAD~3` ausgeführt und drei wichtige, noch nicht gepushte Commits gelöscht. Wie rettest du deine Arbeit mit `git reflog` – und was wäre *nicht* zu retten gewesen?
- **Übersetzung:** en: The safety net: git reflog | fr: Le filet de sécurité : git reflog
- **Stufe:** 2

Leitfaden: Direkt nach Lektion 11 unterrichten – wer Historie umschreibt, braucht das Netz
sofort. Die Übung lässt den Lernenden den Fehler absichtlich machen; die Erleichterung beim
Zurückholen ist der Lerneffekt.

### 13 Detektivarbeit: git bisect
- **Ziele:** Fehlersuche in langer Historie per Binärsuche: `git bisect start`, `good`, `bad`, `reset`; von Hand oder automatisch mit `git bisect run <skript>` (im Projekt: `./gradlew test`); warum 1000 Commits nur zehn Schritte brauchen; Voraussetzung: ein bekannt guter Stand und eine Ja/Nein-Prüfung
- **Übung:** uebungen/07-bisect
- **Prüffrage:** Ein Feature ist seit gestern kaputt, aber in den letzten 24 Stunden wurden 40 Commits gepusht. Wie hilft dir `git bisect`, den fehlerhaften Commit effizient zu finden – und wie viele Schritte brauchst du etwa?
- **Übersetzung:** en: Detective work: git bisect | fr: Travail d'enquête : git bisect
- **Stufe:** 2

### 14 Spurensuche und Releases
- **Ziele:** Herausfinden, wer was warum geändert hat: `git blame` (mit `-w`, `-L`), `git log -p -- <datei>`, `git log -S "text"` (wann kam oder ging dieser Text?), `git log --author`, `git show <hash>`; gute Commit-Nachrichten als Voraussetzung dafür, dass Spurensuche etwas findet (Lektion 06); Releases markieren: `git tag -a v1.2.0 -m "…"`, `git push --tags`, Tags im Gradle-Build als Versionsnummer; `git describe`
- **Prüffrage:** Eine Konstante hat plötzlich einen anderen Wert. Welche zwei Befehle zeigen dir in unter einer Minute, in welchem Commit und von wem das passiert ist? Und: Warum annotierte Tags (`-a`) statt leichter?
- **Übersetzung:** en: Tracing changes and releases | fr: Retrouver les traces et les versions
- **Stufe:** 2
