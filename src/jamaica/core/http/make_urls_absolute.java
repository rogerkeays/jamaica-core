package jamaica.core.http;

import jamaica.core.testing.TestGrouper.NetworkLayer;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

public class make_urls_absolute implements NetworkLayer {

    /**
     * Convert page-relative URLs (e.g. "foo/bar") in the given HTML content
     * to absolute URLs by preprending the given base URL. URLs relative to
     * the domain root are not modified (e.g. "/foo/bar").
     * 
     * Only src and href attributes are modified. A full list of possible
     * attributes with URL values can be found at
     * http://stackoverflow.com/questions/2725156
     * 
     * This function does not validate the baseURL.
     */
    public static String make_urls_absolute(String html, String baseURL) {
        if (baseURL.endsWith("/")) {
            baseURL = baseURL.substring(0, baseURL.length() - 1);
        }
        return html.replaceAll("<(.*?)(src|href)=(\"|')([^/][^:]*)(\"|')(.*?)>",
                "<$1$2=$3" + baseURL + "/$4$5$6>");
    }


    @Test
    public void test_src_attributes_are_processed() {
        assertEquals(make_urls_absolute(
                "<img src=\"foo/bar.jpg\"/>", "http://example.com"), 
                "<img src=\"http://example.com/foo/bar.jpg\"/>");
    }

    @Test
    public void test_href_attributes_are_processed() {
        assertEquals(make_urls_absolute(
                "<a href=\"foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"http://example.com/foo/bar.jpg\"/>");
    }

    @Test
    public void test_plain_text_is_not_processed() {
        assertEquals(make_urls_absolute(
                "foo/bar.jpg", "http://example.com"), 
                "foo/bar.jpg");
    }

    @Test
    public void test_single_quoted_attributes_are_processed() {
        assertEquals(make_urls_absolute(
                "<a href='foo/bar.jpg'/>", "http://example.com"), 
                "<a href='http://example.com/foo/bar.jpg'/>");
    }

    @Test
    public void test_http_url_is_left_unmodified() {
        assertEquals(make_urls_absolute(
                "<a href=\"http://helloworld.com/foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"http://helloworld.com/foo/bar.jpg\"/>");
    }

    @Test
    public void test_https_url_is_left_unmodified() {
        assertEquals(make_urls_absolute(
                "<a href=\"https://helloworld.com/foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"https://helloworld.com/foo/bar.jpg\"/>");
    }

    @Test
    public void test_mailto_url_is_left_unmodified() {
        assertEquals(make_urls_absolute(
                "<a href=\"mailto:hello@world.com\"/>", "http://example.com"), 
                "<a href=\"mailto:hello@world.com\"/>");
    }

    @Test
    public void test_ftp_url_is_left_unmodified() {
        assertEquals(make_urls_absolute(
                "<a href=\"ftp://helloworld.com/foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"ftp://helloworld.com/foo/bar.jpg\"/>");
    }

    @Test
    public void test_slash_url_is_left_unmodified() {
        assertEquals(make_urls_absolute(
                "<a href=\"/foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"/foo/bar.jpg\"/>");
    }

    @Test
    public void test_double_slash_url_is_left_unmodified() {
        assertEquals(make_urls_absolute(
                "<a href=\"//helloworld.com/foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"//helloworld.com/foo/bar.jpg\"/>");
    }

    @Test
    public void test_no_slash_urls_are_made_absolute() {
        assertEquals(make_urls_absolute(
                "<a href=\"foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"http://example.com/foo/bar.jpg\"/>");
    }

    @Test
    public void trailing_slash_from_baseURL_is_not_duplicated() {
        assertEquals(make_urls_absolute(
                "<a href=\"foo/bar.jpg\"/>", "http://example.com/"), 
                "<a href=\"http://example.com/foo/bar.jpg\"/>");
    }
}