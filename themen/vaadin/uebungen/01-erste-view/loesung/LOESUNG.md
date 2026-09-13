Warum so: Der Listener ruft eine benannte Methode auf, statt die Logik in die Lambda zu
stopfen – lesbar und einzeln testbar. Der Name wird mit `strip()` bereinigt, sonst gilt
ein Leerzeichen als Name. `addClickShortcut(Key.ENTER)` bindet Enter an den Knopf, ohne
einen zweiten Listener. Die Datei ersetzt `src/main/java/de/makno/lernen/HalloView.java`.
