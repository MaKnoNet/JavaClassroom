# Lösung

`gradle.properties` im Projekt enthält nur noch, was auf jedem Rechner gilt (Build-Cache,
parallele Ausführung). Die Java-Version legt `build.gradle` über die **Toolchain** fest –
Gradle sucht ein JDK 21 selbst oder lädt es (Foojay-Plugin in `settings.gradle`, falls
eingerichtet); `org.gradle.java.home` ist dafür überflüssig und war der Grund, warum der
Build auf jedem fremden Rechner abbrach.

Was maschinenlokal ist (JDK-Pfad, Proxy, Truststore), wandert nach
`~/.gradle/gradle.properties` – Vorlage in `gradle.properties.maschinenlokal`. Gradle liest
sie vor der Projektdatei, deshalb funktioniert derselbe Build im Firmennetz mit Proxy und zu
Hause ohne, ohne dass sich im Repository eine Zeile ändert.

Der Test `keineMaschinenlokalenEinstellungenImRepository` ist der Wächter: Wer die
Einstellungen wieder eincheckt, wird nicht von einem Kollegen, sondern vom Build erwischt.
