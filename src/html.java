package jamaica.core;

import org.testng.annotations.*;
import static org.testng.AssertJUnit.*;

public class html {

    // make_urls_absolute
    @Test public void make_urls_absolute__processes_relative_urls() {
        assertEquals(make_urls_absolute(
                "<a href=\"foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"http://example.com/foo/bar.jpg\"/>");
    }
    @Test public void make_urls_absolute__processes_src_attributes() {
        assertEquals(make_urls_absolute(
                "<img src=\"foo/bar.jpg\"/>", "http://example.com"), 
                "<img src=\"http://example.com/foo/bar.jpg\"/>");
    }
    @Test public void make_urls_absolute__processes_href_attributes() {
        assertEquals(make_urls_absolute(
                "<a href=\"foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"http://example.com/foo/bar.jpg\"/>");
    }
    @Test public void make_urls_absolute__does_not_process_plain_text() {
        assertEquals(make_urls_absolute(
                "foo/bar.jpg", "http://example.com"), 
                "foo/bar.jpg");
    }
    @Test public void make_urls_absolute__processes_single_quoted_attributes() {
        assertEquals(make_urls_absolute(
                "<a href='foo/bar.jpg'/>", "http://example.com"), 
                "<a href='http://example.com/foo/bar.jpg'/>");
    }
    @Test public void make_urls_absolute__does_not_process_absolute_http_urls() {
        assertEquals(make_urls_absolute(
                "<a href=\"http://helloworld.com/foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"http://helloworld.com/foo/bar.jpg\"/>");
    }
    @Test public void make_urls_absolute__does_not_process_absolute_https_urls() {
        assertEquals(make_urls_absolute(
                "<a href=\"https://helloworld.com/foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"https://helloworld.com/foo/bar.jpg\"/>");
    }
    @Test public void make_urls_absolute__does_not_process_mailto_urls() {
        assertEquals(make_urls_absolute(
                "<a href=\"mailto:hello@world.com\"/>", "http://example.com"), 
                "<a href=\"mailto:hello@world.com\"/>");
    }
    @Test public void make_urls_absolute__does_not_process_ftp_urls() {
        assertEquals(make_urls_absolute(
                "<a href=\"ftp://helloworld.com/foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"ftp://helloworld.com/foo/bar.jpg\"/>");
    }
    @Test public void make_urls_absolute__does_not_process_urls_starting_with_a_slash() {
        assertEquals(make_urls_absolute(
                "<a href=\"/foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"/foo/bar.jpg\"/>");
    }
    @Test public void make_urls_absolute__does_not_process_urls_starting_with_a_double_slash() {
        assertEquals(make_urls_absolute(
                "<a href=\"//helloworld.com/foo/bar.jpg\"/>", "http://example.com"), 
                "<a href=\"//helloworld.com/foo/bar.jpg\"/>");
    }
    @Test public void make_urls_absolute__handles_base_urls_with_a_trailing_slash() {
        assertEquals(make_urls_absolute(
                "<a href=\"foo/bar.jpg\"/>", "http://example.com/"), 
                "<a href=\"http://example.com/foo/bar.jpg\"/>");
    }
    public static String make_urls_absolute(String html, String baseURL) {
        if (baseURL.endsWith("/")) {
            baseURL = baseURL.substring(0, baseURL.length() - 1);
        }
        return html.replaceAll("<(.*?)(src|href)=(\"|')([^/][^:]*)(\"|')(.*?)>",
                "<$1$2=$3" + baseURL + "/$4$5$6>");
    }
}
