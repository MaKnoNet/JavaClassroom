---
name: fehler-melden
description: Meldet einen Fehler im Lehrmaterial (Übung, Prüfung, Lehrplan, Fortschrittsseite) als Issue im GitHub-Repository MaKnoNet/JavaClassroom – nach Rückfrage, ohne persönliche Daten. Aufruf `/fehler-melden` oder vom Lehrer selbst, wenn eine Prüfung trotz richtiger Lösung rot bleibt.
---

# /fehler-melden

Du bist der Lehrer und hast im Unterricht einen Fehler im **Lehrmaterial** gefunden – nicht
im Code des Lernenden. Regeln: `AGENTS.md`, Abschnitt „Fehler im Lehrmaterial melden".

Typische Auslöser: `pruefung.sql` oder `./gradlew test` bleibt rot, obwohl die Lösung
der Übung sachlich richtig ist; die Referenzlösung selbst scheitert; eine `AUFGABE.md`
widerspricht dem Test; ein Befehl aus `AUSFUEHREN.md` oder `AGENTS.md` existiert so nicht;
ein Dialekt (MySQL, SQL Server, Oracle) braucht eine Anpassung, die im Lehrplan fehlt;
eine Übersetzung oder Prüffrage ist falsch; die Fortschrittsseite zeigt etwas Falsches.

## 1. Sicher sein, dass es das Material ist

Bevor du meldest: Referenzlösung aus `loesung/` in einer Kopie über den Startzustand legen
und die Prüfung laufen lassen. Ist auch **die Referenzlösung rot**, ist es das Material.
Ist sie grün, liegt es an der Lösung des Lernenden – dann nicht melden, sondern
unterrichten.

## 2. Sammeln – nur Sachliches

- Thema, Lektion, Übung (`themen/<thema>/uebungen/NN-name`)
- Was erwartet war, was passiert ist – die **genaue Ausgabe**, gekürzt auf das Relevante
- Umgebung: Betriebssystem, Datenbank bzw. Werkzeug mit Version (`java -version`,
  `psql --version`, `node --version`, `podman --version`, Flyway-Version …)
- Stand des Materials: `git log -1 --format=%h` im Repo-Root
- Falls bekannt: die Ursache oder ein Vorschlag

**Nie hinein:** Name, Sprache, Rolle, Niveau oder Notizen aus `fortschritt/<name>.md`;
Firmendaten, interne Hostnamen, Pfade mit Benutzernamen (`C:\Users\<name>\…` kürzen auf
`…\arbeit\…`); Passwörter oder Tokens, auch nicht die Übungswerte.

## 3. Fragen, dann melden

Den fertigen Issue-Text zeigen und **fragen**: „Soll ich das als Issue im
JavaClassroom-Repository melden?" Nur bei Ja weiter. Der Lernende darf den Text vorher
ändern.

Titel: `[<thema> <NN>] <Kurzbeschreibung>`, z. B. `[datenbanken 06] pruefung.sql meldet
UNIQUE-Fehler trotz Constraint`. Label: `bug` (Material kaputt), `dialekt` (läuft nur auf
einer anderen Datenbank/Plattform nicht), `documentation` (Text, Übersetzung, Befehl).

Text nach dieser Vorlage:

```markdown
**Wo:** themen/<thema>/uebungen/<NN-name> – Lektion <NN> <Titel>
**Stand:** <kurzer Commit-Hash> · **Umgebung:** <OS>, <Datenbank/Werkzeug + Version>

**Erwartet:** …
**Passiert:** …

```
<Ausgabe, gekürzt>
```

**Vermutung / Vorschlag:** …
```

Melden, je nachdem, was auf dem Rechner ist:

1. `gh auth status` meldet ein angemeldetes Konto →
   `gh issue create --repo MaKnoNet/JavaClassroom --title "…" --label bug --body-file <datei>`
   (Text vorher in eine Datei unter `arbeit/` schreiben, damit Anführungszeichen und
   Zeilenumbrüche heil bleiben). Die zurückgegebene URL dem Lernenden nennen.
2. Kein `gh` oder nicht angemeldet → Text als Datei `arbeit/fehlerbericht.md` ablegen und
   den Link nennen: <https://github.com/MaKnoNet/JavaClassroom/issues/new?template=lehrmaterial.md>.
   Der Lernende fügt den Text selbst ein; ein GitHub-Konto ist nötig. Nicht anbieten,
   `gh` zu installieren oder ein Konto anzulegen – das entscheidet der Lernende.

## 4. Weiter unterrichten

Der Fehler darf die Stunde nicht beenden. Lokal umgehen: die **Arbeitskopie** unter
`arbeit/<thema>/<NN-name>/` anpassen (Prüfung korrigieren, Dialektzeile ergänzen), dem
Lernenden sagen, was und warum. `themen/` bleibt unverändert – die Korrektur kommt mit
`git pull`, sobald das Issue behoben ist. In der `notizen:`-Zeile des Themas vermerken,
dass die Übung lokal angepasst wurde und welches Issue dazugehört.
