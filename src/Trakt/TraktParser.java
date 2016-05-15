package Trakt;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.awt.List;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author TAIBHSE
 */
public class TraktParser
{

    public static void main(String args[]) throws IOException
    {
        String url = "https://trakt.tv/shows/blindspot";
               
        List data = getData(url);
        
        for(int i = 0; i < data.getItemCount(); i++)
        {
            System.out.println(data.getItem(i));
        }
    }

    public static List getData(String url) throws IOException
    {
        List data = new List();

        
        data.add("<search>" + getSearchParams(url) + "</search><url>" + url + "</url>");
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions()
                .setThrowExceptionOnScriptError(false);
        //disable javascript to speed up site loading
        webClient.getOptions()
                .setJavaScriptEnabled(false);

        java.util.logging.Logger.getLogger(
                "com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

        HtmlPage page;
        String html;
        Document doc;

        for (int i = 1; i <= getSeasonCount(url); i++)
        {

            //System.out.println("Trying Season " + i);
            
            try
            {
            page = webClient.getPage(url + "/seasons/" + i);  //was XmlPage
            html = page.asXml();

            doc = Jsoup.parse(html);
            Elements episodes = doc.select("div[class=col-md-8 col-sm-7 under-info]");
            Elements dates = doc.select("td.tlistname");

            for (Element episode : episodes)
            {
               
                
                String season = episode.select("h3").select("span").text().substring(0, episode.select("h3").select("span").text().indexOf("x"));
                if(season.length() == 1){ season = "0" + season; }
                
                 String ep = episode.select("h3").select("span").text().substring(episode.select("h3").select("span").text().indexOf("x")+1, episode.select("h3").select("span").text().length());
                
                 String retEp = "S" + season + "E" + ep;
                // System.out.print(retEp);
                 
                 String date = "";
                 
                 //get season date
                 if(!episode.select("ul").select("span").text().equals(""))
                 {
                     date = episode.select("ul").select("span").text().substring(0, episode.select("ul").select("span").text().indexOf("T"));
                // System.out.println(date);
                         }
                 
                 data.add("<" + retEp + ">false</" + retEp + "><release>" + date + "</release>");
            }
            
            }catch(FailingHttpStatusCodeException e)
            {
                
            }

        }

        return data;
    }

    public static int getSeasonCount(String url) throws IOException
    {
        //  String url = "https://trakt.tv/shows/the-simpsons";
        int numSeasons = 0;
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions()
                .setThrowExceptionOnScriptError(false);
        //disable javascript to speed up site loading
        webClient.getOptions()
                .setJavaScriptEnabled(false);

        java.util.logging.Logger.getLogger(
                "com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

        HtmlPage page = webClient.getPage(url);  //was XmlPage

        String html = page.asXml();

        Document doc = Jsoup.parse(html);
        Elements seasons = doc.select("a");

        for (Element value : seasons)
        {
            if (value.attr("href").equals("#seasons"))
            {

                numSeasons = Integer.parseInt(value.text().replace(" Seasons", "").replace(" Season", ""));
             
                break;
            }
        }

        return numSeasons;
    }
    
    
     public static String getSearchParams(String url) throws IOException 
    {
        //  String url = "https://trakt.tv/shows/the-simpsons";
        String search = "";
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions()
                .setThrowExceptionOnScriptError(false);
        //disable javascript to speed up site loading
        webClient.getOptions()
                .setJavaScriptEnabled(false);

        java.util.logging.Logger.getLogger(
                "com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

        HtmlPage page = webClient.getPage(url);  //was XmlPage

        String html = page.asXml();

        Document doc = Jsoup.parse(html);
        Elements titles = doc.select("div[class=col-md-10 col-md-offset-2 col-sm-9 col-sm-offset-3 mobile-title]");

        for (Element value : titles)
        {
           search = value.select("h1").text().replace(value.select("h1").select("span").text(), "").replace(value.select("h1").select("div").text(), "").trim() + " ettv";
            
        }

        return search;
    }
}
