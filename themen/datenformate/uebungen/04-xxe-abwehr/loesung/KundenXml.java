package de.makno.lernen;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Liest {@code <kunde><name>…</name></kunde>} aus XML, das von außen kommt. Der Parser ist
 * gehärtet: kein DOCTYPE, keine externen Entities, keine externen DTDs, kein XInclude.
 */
public class KundenXml {

    private static final String KEIN_DOCTYPE = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String KEINE_EXTERNEN_ENTITIES = "http://xml.org/sax/features/external-general-entities";
    private static final String KEINE_EXTERNEN_PARAMETER = "http://xml.org/sax/features/external-parameter-entities";
    private static final String KEINE_EXTERNE_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

    public String liesName(String xml) throws ParserConfigurationException, SAXException, IOException {
        Document dokument = sichererParser().parse(new InputSource(new StringReader(xml)));
        return dokument.getElementsByTagName("name").item(0).getTextContent();
    }

    /** OWASP-Empfehlung für JAXP: DOCTYPE ganz verbieten – damit sind alle Entity-Angriffe vom Tisch. */
    private static DocumentBuilder sichererParser() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature(KEIN_DOCTYPE, true);
        factory.setFeature(KEINE_EXTERNEN_ENTITIES, false);
        factory.setFeature(KEINE_EXTERNEN_PARAMETER, false);
        factory.setFeature(KEINE_EXTERNE_DTD, false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);
        return factory.newDocumentBuilder();
    }
}
