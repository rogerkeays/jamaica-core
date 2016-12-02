package jamaica.core.xml;

import java.util.*;
import java.io.*;
import javax.xml.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import static jamaica.core.collections.*;
import static jamaica.core.io.*;
import static jamaica.core.lang.*;
import static jamaica.core.testing.*;

public class xml {
    private final static String UNICODE_LOW = "" + ((char) 0x20); //space
    private final static String UNICODE_HIGH = "" + ((char) 0x7f);
    public final static String ESCAPE_CHARS = "<>&\"\'";
    public final static List<String> ESCAPE_STRINGS = as_list("&lt;", "&gt;", "&amp;", "&quot;", "&apos;");


    // escape_xml
    @Test public void test_special_xml_characters_are_escaped() {
        assert_equals("&lt;&gt;&amp;&quot;&apos;", escape_xml("<>&\"\'"));
    }
    public static String escape_xml(String content) {
        String result = content;
        if (content != null && content.length() > 0) {
            boolean modified = false;
            StringBuilder stringBuilder = new StringBuilder(content.length());
            for (int i = 0, count = content.length(); i < count; ++i) {
                String character = content.substring(i, i + 1);
                int pos = ESCAPE_CHARS.indexOf(character);
                if (pos > -1) {
                    stringBuilder.append(ESCAPE_STRINGS.get(pos));
                    modified = true;
                } else if ((character.compareTo(UNICODE_LOW) > -1)
                        && (character.compareTo(UNICODE_HIGH) < 1)) {
                    stringBuilder.append(character);
                } else {
                    stringBuilder.append("&#" + ((int) character.charAt(0)) + ";");
                    modified = true;
                }
            }
            if (modified) {
                result = stringBuilder.toString();
            }
        }
        return result;
    }


    // validate_xml
    @Test public void test_valid_xml_document_validates() throws SAXException {
        assert_true(validate_xml(read_text_resource("xml_test_schema.xsd"), 
            read_text_resource("xml_test_document.xml")));
    }
    @Test public void test_badly_formed_xml_document_throws_an_exception() throws SAXException {
        assert_throws(SAXException.class, ()-> 
            validate_xml(read_text_resource("xml_test_schema.xsd"), "not xml"));
    }
    @Test public void test_invalid_formed_xml_document_throws_an_exception() throws SAXException {
        assert_throws(SAXException.class, ()-> 
            validate_xml(read_text_resource("xml_test_schema.xsd"), "<invalid>tag</invalid>"));
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
