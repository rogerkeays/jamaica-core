package jamaica.core.xml;

import jamaica.core.io.UncheckedIOException;
import jamaica.core.testing.TestGrouper.APILayer;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import static jamaica.core.io.read_text_resource.read_text_resource;
import static jamaica.core.lang.get_resource_path.get_resource_path;
import static org.testng.AssertJUnit.assertTrue;

public class validate_xml implements APILayer {

    /**
     * Validate the given xml according to the schema at schemaURL. 
     * 
     * @param schema the schema document
     * @param xml the xml content to validate
     * @return true if the schema is valid, raises an exception otherwise
     * @throws SAXException if there is a validation error
     */
    public static boolean validate_xml(String schema, String xml) 
                throws SAXException {
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new StreamSource(new StringReader(schema)))
                    .newValidator().validate(
                            new StreamSource(new StringReader(xml)));
            return true;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    @Test
    public void test_valid_xml_document_validates() throws SAXException {
        assertTrue(validate_xml(read_text_resource(get_resource_path(validate_xml.class, "TestSchema.xsd")), 
                read_text_resource(get_resource_path(validate_xml.class, "TestDocument.xml"))));
    }

    @Test(expectedExceptions=SAXException.class)
    public void test_badly_formed_xml_document_throws_an_exception() throws SAXException {
        assertTrue(validate_xml(read_text_resource(get_resource_path(validate_xml.class, "TestSchema.xsd")),
                "not xml"));
    }

    @Test(expectedExceptions=SAXException.class)
    public void test_invalid_formed_xml_document_throws_an_exception() throws SAXException {
        assertTrue(validate_xml(read_text_resource(get_resource_path(validate_xml.class, "TestSchema.xsd")),
                "<invalid>tag</invalid>"));
    }
}
