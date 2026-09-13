# Lösung 05

```bash
git rebase -i main
```

Im Editor die Liste so ändern (ältester Commit oben):

```
pick    a1b2c3d Login-Formular angelegt
fixup   b2c3d4e wip
fixup   c3d4e5f fix typo
pick    d4e5f6a Passwort-Prüfung ergänzt
fixup   e5f6a7b asdf
```

Speichern, schließen – fertig. `fixup` statt `squash`, weil die Nachrichten „wip", „fix
typo" und „asdf" nichts beitragen; mit `squash` müsste man sie beim Zusammenfassen von
Hand aus der Nachricht löschen.

Ohne Editor (so prüft Claude die Lösung, ohne einen Editor zu öffnen):

```bash
GIT_SEQUENCE_EDITOR="sed -i -e '2s/^pick/fixup/' -e '3s/^pick/fixup/' -e '5s/^pick/fixup/'" git rebase -i main
```

Warum so: Der Rebase spielt die Commits nacheinander neu auf `main` ab; `fixup` verschmilzt
einen Commit mit seinem Vorgänger. Der Inhalt am Ende ist derselbe (`git diff vorher
feature-login` ist leer), aber die Commits sind neue Objekte mit neuen Hashes – deshalb
gilt die Regel aus Lektion 07: Nur die eigene, noch nicht geteilte Historie umschreiben.
