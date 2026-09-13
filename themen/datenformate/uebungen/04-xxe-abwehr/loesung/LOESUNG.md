Die Datei ersetzt `src/main/java/de/makno/lernen/KundenXml.java`.

Warum so: Der JDK-Parser ist standardmäßig ein vollständiger XML-1.0-Parser – und der
Standard erlaubt, in einem DOCTYPE Entities zu definieren, die auf *externe* Quellen
zeigen: `SYSTEM "file:///etc/passwd"`, `SYSTEM "http://intern.firma/…"`. Der Parser
holt den Inhalt und setzt ihn ein, als stünde er im Dokument. Wer den geparsten Wert
dann speichert, anzeigt oder als Fehlermeldung zurückschickt, liefert Serverdateien
aus (XXE, *XML External Entity*). Die wirksamste Einstellung ist die erste:
`disallow-doctype-decl` – ohne DOCTYPE gibt es keine Entities, weder externe noch
die „Billion Laughs"-Bombe, bei der sich verschachtelte Entities zu Gigabytes
aufblähen. Die weiteren Features sind der Gürtel zum Hosenträger, falls jemand das
DOCTYPE-Verbot später lockert. `FEATURE_SECURE_PROCESSING` begrenzt zusätzlich
Entity-Größen und -Tiefe.

Die Regel gilt für jeden XML-Parser im Code – `DocumentBuilderFactory`, `SAXParserFactory`,
`XMLInputFactory` (StAX), `TransformerFactory`, JAXB. Bibliotheken wie Jackson-XML oder
Spring härten ihre Parser selbst; wer `javax.xml` direkt nutzt, muss es selbst tun.
