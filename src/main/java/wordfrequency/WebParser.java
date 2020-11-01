package wordfrequency;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public final class WebParser {
    private static final int MAX_LEVEL = 4;
    private static final int TIMEOUT = 10000;

    private String host;

    private URL url;

    public WebParser(String urlStr) throws Exception {
        this.url = new URL(urlStr);
        this.host = url.getHost();
    }

    /**
     * Parses the content on the url passed during class initialization
     * Also parses links found inside the web page
     * @return concatenated string of all the content of the web pages parsed
     */
    public String deepParse() {

        StringBuilder responseBuilder = new StringBuilder();

        this.parse(this.url.toString(), 1, responseBuilder, new HashSet<>());

        return responseBuilder.toString();
    }

    /**
     * Recursive function to parse the the given url and all links found inside the parsed url
     * @param urlStr is the url to be fetched and parsed
     * @param level is the level of nested web pages parsed
     * @param responseBuilder is to aggregate the contents from all parsed urls
     * @param visitedLinks Set to keep a track of all visited urls to avoid parsing the same url twice
     */
    private void parse(String urlStr, int level, StringBuilder responseBuilder, Set<String> visitedLinks) {
        if(level > MAX_LEVEL) {
            return;
        }

        // Printing urls being parsed along with the levels from the starting page
        System.out.println("Parsing: " + urlStr + ", level: " + level);

        Document jsoupDoc;
        try {
            jsoupDoc = Jsoup.connect(urlStr)
                    .timeout(TIMEOUT)
                    .get();
        }
        catch (IOException e)  {
            System.out.println("Skipping url as invalid response received: " + urlStr);
            return;
        }

        // append the web page contents
        responseBuilder.append(jsoupDoc.toString());

        // Add all visited links to a set
        visitedLinks.add(urlStr);

        Set<String> links = this.getLinks(jsoupDoc);

        for(String link: links) {
            // if link already visited, skip
            if(visitedLinks.contains(link)) {
                continue;
            }

            parse(link, level+1, responseBuilder, visitedLinks);
        }
    }

    /**
     * Gets the links on a page belonging to the same domain as the main url passed
     * @param jsoupDoc Jsoup Document for a web page url
     * @return Set of all links on a page
     */
    private Set<String> getLinks(Document jsoupDoc) {
        Set<String> links = new HashSet<>();

        Elements elements = jsoupDoc.select("a[href]");
        for (Element element : elements) {
            String hrefVal = element.attr("href");

            // only consider links that belong to the same domain as the main url passed to this class
            hrefVal = (hrefVal != null && hrefVal.contains(this.host)) ? hrefVal : "";

            if(!hrefVal.equals("")) {
                links.add(hrefVal);
            }
        }

        return links;
    }
}
