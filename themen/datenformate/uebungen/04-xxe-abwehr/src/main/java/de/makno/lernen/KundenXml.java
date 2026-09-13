package de.makno.lernen;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Liest {@code <kunde><name>…</name></kunde>} aus XML, das von außen kommt – aus einer
 * Schnittstelle, einem Upload, einer E-Mail. Der Parser ist so, wie das JDK ihn liefert.
 */
public class KundenXml {

    public String liesName(String xml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document dokument = builder.parse(new InputSource(new StringReader(xml)));
        return dokument.getElementsByTagName("name").item(0).getTextContent();
    }
}
