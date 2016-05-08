package WebScrapers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.awt.List;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author TAIBHSE
 */
public class Kickass
{

   // private static final WebClient webClient = new WebClient(BrowserVersion.CHROME);
    //  private static HtmlPage page;
    //  private static String html = "";
    //  private static Document doc;
    //  private static Elements parse, links;
    private static final String urlMain = "http://kat.proxy-x.com";
    private static final String urlStart = urlMain + "/usearch/";
    private static final String urlEnd = "%20category%3Acomics/";

    //   private static 
    public static void main(String args[]) throws IOException
    {
        List eps = new List();
        
        for(int i = 1; i < 100; i++)
        {
            String num = i + "";
            if(num.length()==1)
            {
                num = "00" + num;
            }else if(num.length()==2)
            {
                num = "0" + num;
            }
        eps.add("ixth generation cbr_" + num);
        }

        for (int i = 0; i < eps.getItemCount(); i++)
        {
            String split[] = eps.getItem(i).split("_");
            System.out.println(getMagnet(split[0], split[1]));
        }

    }

    public static String getMagnet(String name, String issue) throws IOException
    {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //disable javascript to speed up site loading
        webClient.getOptions().setJavaScriptEnabled(false);

        String magnet = "";

        String series = name + " " + issue;

        //code needed here to format series name correctly 
        String url = urlStart + series + urlEnd;
     //   System.out.println("SEARCHING FOR: " + name + " " + issue + "                                " + url);

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
       

        try
        {
            HtmlPage page = webClient.getPage(url);

        //if page returned is a list of eps, then grab url to topmost as it is the most
            //recent version of an episode, grab url and replace view with download to get magnet link
            String html = page.asXml();

            Document doc = Jsoup.parse(html);
            Elements parse = doc.select("div.torrentname");
            Elements links = parse.select("a.normalgrey.font12px.plain.bold");

            String suburl = "";
            linkSearch:
            for (Element link : links)
            {

                //    System.out.println(link.attr("href"));
                String split[] = name.split("\\s+");

                test:
                for (int i = 0; i < split.length; i++)
                {
                    if (split[i].charAt(0) != '-')
                    {
                        //testing url and . not allowed in url so remove it if in search string before testing
                        if (split[i].charAt(0) == '.')
                        {
                            split[i] = split[i].replaceFirst(".", "");
                        }

                        if (link.attr("href").toLowerCase().contains(split[i].toLowerCase()) && link.attr("href").contains(issue))
                        {
                            suburl = link.attr("href");
                        } else
                        {
                            suburl = "";
                            break test;
                        }
                    }

                }

            }

            //   System.out.println("SUB: " + suburl);
            page = webClient.getPage(urlMain + suburl);

            html = page.asXml();

            doc = Jsoup.parse(html);
      //  parse = doc.select("a.siteButton.giantIcon.magnetlinkButton");
            parse = doc.select("a.kaGiantButton");

            for (Element p : parse)
            {
              //  System.out.println("SELECTED MAGNET:     " + p.attr("href"));
                magnet = p.attr("href");
                if (magnet.contains("magnet:?xt="))
                {
                    break;
                }
            }

        } catch (FailingHttpStatusCodeException e)
        {
          //  System.out.println("ERROR 404 no results, skipping.....");
        }
        return magnet;
    }
}
