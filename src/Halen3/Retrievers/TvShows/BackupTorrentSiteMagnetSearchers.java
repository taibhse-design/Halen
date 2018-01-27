package Halen3.Retrievers.TvShows;

import Halen3.IO.FileManager;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.Arrays;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author TAIBHSE
 */
public class BackupTorrentSiteMagnetSearchers
{

    public static void main(String args[])
    {
        System.out.println(kickassTvSearch("the walking dead s08e02"));
    }
    
      static String bannedWords[] =
            {
                "480p", "5.1c", "e-subs", "micromkv", "hdtc", "hdts", "hd.ts", "hd-ts", "hd.tc", "hd-tc", "hdcam", "hc.tc",
            "hd.tc", "hd.cam", "hd-cam", "hd cam", "(cam)", "cam", "camrip", "hindi", "3d", 
            "dts-hd", "spanish",
            "latino", "italian", "sub ita", "ita eng", "eng-ita", "portuguese",
            "[greek]", "nl subs", "ita.eng", "multi-subs", "4k", "ultrahd", "trailer"
            };
      static String allowedGroups[] =
            {
                "ettv", "rartv", "x264-dimension", "x264-lol", "x264-fleet", "eztv", "yify", "rarbg", "etrg", "ac3-evo"
            };

    public static String kickassTvSearch(String search)
    {

        try
        {
            String magnet = "";

            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            //disable javascript to speed up site loading
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setRedirectEnabled(true); //was false
            webClient.getOptions().setUseInsecureSSL(true);
            java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

            webClient.waitForBackgroundJavaScript(10000);
            HtmlPage page;
            String html;
            //                                                  replace spaces with %20 for url
            page = webClient.getPage("https://kickass.cd/usearch/" + search.replaceAll(" ", "%20") + "%20category:tv/?field=size&sorder=asec");  //was XmlPage
            html = page.asXml();

            Document doc = Jsoup.parse(html);
            Elements links = doc.select("a.icon16");

         

            //loop links
            for (Element link : links)
            {
                
                //only work with links that have magnet url
                if (link.attr("href").startsWith("magnet:?xt=") && link.attr("title").equals("Torrent magnet link"))
                {
                    System.out.println("kickass TRYING: " + FileManager.getNameFromMagnet(link.attr("href")).toLowerCase());
                    //ensure magnet contains search string (magnet can uses spaces or periods in names)
                    if (FileManager.getNameFromMagnet(link.attr("href")).toLowerCase().contains(search.toLowerCase()) || 
                            FileManager.getNameFromMagnet(link.attr("href")).toLowerCase().contains(search.replaceAll(" ", ".").toLowerCase()))
                    {
                        System.out.println("valid name");
                        //test if magnet name contains banned elements
                        if (!Arrays.stream(bannedWords).parallel().anyMatch(FileManager.getNameFromMagnet(link.attr("href").toLowerCase())::contains))
                        {
                            //only use magnets released by allowed quality groups
                            if (Arrays.stream(allowedGroups).parallel().anyMatch(FileManager.getNameFromMagnet(link.attr("href").toLowerCase())::contains))
                            {
                             //   System.out.println(FileManager.getNameFromMagnet(link.attr("href")) + ": " + link.attr("href") + "\n");
                                magnet = link.attr("href");
                            }
                        }
                    }
                }
            }

            return magnet;

        } catch (IOException e)
        {
            
            return null;
        }
    }
    
    
     public static String zooqleTvSearch(String search)
    {

        try
        {
            String magnet = "";

            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            //disable javascript to speed up site loading
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setRedirectEnabled(true); //was false
            webClient.getOptions().setUseInsecureSSL(true);
            java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

            webClient.waitForBackgroundJavaScript(10000);
            HtmlPage page;
            String html;
            //page = webClient.getPage("https://kickass.cd/usearch/" + search.replaceAll(" ", "%20") + "%20category:tv/?field=size&sorder=asec");  //was XmlPage
           page = webClient.getPage("https://zooqle.com/search?q=" + search.replaceAll(" ", "%20") + "%20200MB-800MB&s=sz&v=t&sd=d");  //was XmlPage
           
            
            html = page.asXml();

          //  System.out.println(html);
            Document doc = Jsoup.parse(html);
            Elements links = doc.select("a");

         

            //loop links
            for (Element link : links)
            {
                
                //only work with links that have magnet url
                if (link.attr("href").startsWith("magnet:?xt=") && link.attr("title").equals("Magnet link"))
                {
                    System.out.println("zooqle TRYING: " + FileManager.getNameFromMagnet(link.attr("href")).toLowerCase());
                    //ensure magnet contains search string (magnet can uses spaces or periods in names)
                    if (FileManager.getNameFromMagnet(link.attr("href")).toLowerCase().contains(search.toLowerCase()) || 
                            FileManager.getNameFromMagnet(link.attr("href")).toLowerCase().contains(search.replaceAll(" ", ".").toLowerCase()))
                    {
                        //test if magnet name contains banned elements
                        if (!Arrays.stream(bannedWords).parallel().anyMatch(FileManager.getNameFromMagnet(link.attr("href").toLowerCase())::contains))
                        {
                            //only use magnets released by allowed quality groups
                            if (Arrays.stream(allowedGroups).parallel().anyMatch(FileManager.getNameFromMagnet(link.attr("href").toLowerCase())::contains))
                            {
                             //   System.out.println(FileManager.getNameFromMagnet(link.attr("href")) + ": " + link.attr("href") + "\n");
                                magnet = link.attr("href");
                            }
                        }
                    }
                }
            }

            return magnet;

        } catch (IOException e)
        {
            return null;
        }
    }
}
