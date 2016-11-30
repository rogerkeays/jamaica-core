package jamaica.core.xml;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import static jamaica.core.functions.classpath.*;
import static jamaica.core.functions.io.*;
import static jamaica.core.functions.lang.*;
import static jamaica.core.functions.testing.*;

public class validate_xml {

    // validate_xml
    @Test public void test_valid_xml_document_validates() throws SAXException {
        assert_true(validate_xml(read_text_resource("xml/TestSchema.xsd"), read_text_resource("xml/TestDocument.xml")));
    }
    @Test public void test_badly_formed_xml_document_throws_an_exception() throws SAXException {
        assert_throws(SAXException.class, ()-> 
            validate_xml(read_text_resource("xml/TestSchema.xsd"), "not xml"));
    }
    @Test public void test_invalid_formed_xml_document_throws_an_exception() throws SAXException {
        assert_throws(SAXException.class, ()-> 
            validate_xml(read_text_resource("xml/TestSchema.xsd"), "<invalid>tag</invalid>"));
    }
    public static boolean validate_xml(String schema, String xml) {
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new StreamSource(new StringReader(schema)))
                    .newValidator().validate(
                            new StreamSource(new StringReader(xml)));
            return true;
        } catch (IOException e) {
            throw checked(e);
        } catch (SAXException e) {
            throw checked(e);
        }
    }
}
