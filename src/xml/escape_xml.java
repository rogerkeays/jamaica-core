package jamaica.core.xml;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

public class escape_xml {
    public final static String ESCAPE_CHARS = "<>&\"\'";
    public final static List<String> ESCAPE_STRINGS = unmodifiableList(asList(
            new String[] { "&lt;", "&gt;", "&amp;", "&quot;", "&apos;" }));

    private final static String UNICODE_LOW = "" + ((char) 0x20); //space
    private final static String UNICODE_HIGH = "" + ((char) 0x7f);


    /**
     * Escape a string so it can be used as an XML attribute or content of
     * and XML tag. Author: chaotic3quilibrium
     * http://stackoverflow.com/questions/439298/best-way-to-encode-text-data-for-xml-in-java
     */
    public static String escape_xml(String content) {
        String result = content;
        if ((content != null) && (content.length() > 0)) {
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


    @Test
    public void test_special_xml_characters_are_escaped() {
        assertEquals(escape_xml("<>&\"\'"), "&lt;&gt;&amp;&quot;&apos;");
    }
}
