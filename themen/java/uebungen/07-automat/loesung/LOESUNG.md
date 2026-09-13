Drei Dateien ersetzen `src/main/java/de/makno/lernen/`: das neue Interface `BezahlService`,
`KartenBezahlung` mit `implements`, und der Automat, der seine Bezahlung per Konstruktor
bekommt statt sie selbst zu bauen.

Warum so: Der Automat weiß jetzt nur noch, dass *irgendwer* `belaste(double)` kann – das
ist Dependency Inversion. Wer den Automaten baut (im Test: Mockito, im Betrieb: `new
GetraenkeAutomat(new KartenBezahlung())`, später: der Spring-Container), entscheidet über
die konkrete Bezahlung. Das `new` ist nicht verschwunden, es ist nur nach außen gewandert –
an die einzige Stelle, die die konkrete Klasse kennen darf.
