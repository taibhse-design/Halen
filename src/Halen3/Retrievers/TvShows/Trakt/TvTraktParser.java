package Halen3.Retrievers.TvShows.Trakt;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgGreenBgWhite;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import Halen3.IO.FileManager;
import java.awt.List;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author TAIBHSE
 */
public class TvTraktParser
{

    public static void main(String args[]) throws IOException
    {
        String url = "https://trakt.tv/shows/counterpart-2018";
       
        List data = getData(url);
        
        for(int i = 0; i < data.getItemCount(); i++)
        {
            System.out.println(data.getItem(i));
        }
    }

    
    public static List getData(String url) throws IOException
    {
        List data = new List();

        
        
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions()
                .setThrowExceptionOnScriptError(false);
        //disable javascript to speed up site loading
        webClient.getOptions()
                .setJavaScriptEnabled(false);
        
        
        webClient.getOptions().setRedirectEnabled(true); //was false

        java.util.logging.Logger.getLogger(
                "com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

        HtmlPage page;
        String html;
        Document doc;
        ColorCmd.println("Retrieving Show data.....", fgGreenBgWhite);
        int seasonCount = getSeasonCount(url);
        ColorCmd.println("Number of Seasons in Show: " + seasonCount, fgGreenBgWhite);
        data.add(FileManager.makeTag("search", getSearchParams(url)) + FileManager.makeTag("url", url) + getShowData(url) + FileManager.makeTag("image", Halen3.Retrievers.TvShows.Trakt.TvFanartDownloader.imagePath));
     //   data.add("<search>" + getSearchParams(url) + "</search><url>" + url + "</url><image>" + TvFanartDownloader.TvFanartDownloader.imagePath + "</image>");
        //for (int i = 1; i <= getSeasonCount(url); i++)
        for (int i = 1; i <= seasonCount; i++)
        {

            //System.out.println("Trying Season " + i);
            
            try
            {
              //  System.out.println("trying: " + url + "/seasons/" + i);
            page = webClient.getPage(url + "/seasons/" + i);  //was XmlPage
            html = page.asXml();

            doc = Jsoup.parse(html);
           Elements episodes = doc.select("div[class=col-md-8 col-sm-7 under-info]");
         //  Elements episodes = doc.select("span.main-title-sxe");
            Elements dates = doc.select("td.tlistname");

            
            for (Element episode : episodes)
            {
              // System.out.println(episode.html());
               // String season = episode.select("div").select("h3").select("a").select("span").text().substring(0, episode.text().indexOf("x"));
                String season = episode.select("h3").select("span.main-title-sxe").text().substring(0, episode.select("h3").select("span.main-title-sxe").text().indexOf("x"));
                if(season.length() == 1){ season = "0" + season; }
                
              //  String ep = episode.select("div").select("h3").select("a").select("span").text().substring(episode.text().indexOf("x")+1,episode.text().length());
                 String ep = episode.select("h3").select("span.main-title-sxe").text().substring(episode.select("h3").select("span.main-title-sxe").text().indexOf("x")+1, episode.select("h3").select("span.main-title-sxe").text().length());
                
                 String retEp = "S" + season + "E" + ep;
                // System.out.print(retEp);
                 
                 String date = "";
                 
                 //get season date
                 if(!episode.select("ul").select("span").text().equals("") && !episode.select("ul").select("span").text().contains("no air date"))
                 {
                     date = episode.select("ul").select("span").text().substring(0, episode.select("ul").select("span").text().indexOf("T"));
                // System.out.println(date);
                         }
                 
                 data.add("<" + retEp + ">false</" + retEp + "><release>" + date + "</release>");
            }
            
            }catch(FailingHttpStatusCodeException e)
            {
                System.out.println(e);
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

        loop: for (Element value : seasons)
        {
            //thrown in here for convenience 
            //this grabs the fanart link and attampts to download all fanart
            if(value.attr("href").contains("fanart"))
            {
                ColorCmd.println("Fanart Page: " + value.attr("href"), fgGreenBgWhite);
                
                Halen3.Retrievers.TvShows.Trakt.TvFanartDownloader.download(value.attr("href"));
                break loop;
            }
            
        }
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
    

     public static String getShowData(String url) throws IOException 
    {
        
        String tags = "";
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

       // System.out.println(html);
        
        Document doc = Jsoup.parse(html);
        Elements titles = doc.select("div[class=col-md-10 col-md-offset-2 col-sm-9 col-sm-offset-3 mobile-title]");

        Elements images = doc.select("img");
        Element image = null;
        for(Element i : images)
        {
           // System.out.println
            if(i.attr("data-original").contains("/posters/thumb/"))
            {
                image = i;
              ColorCmd.println("Poster Image URL: " + image.attr("data-original"), fgGreenBgWhite);
                break;
            }
        }
        Elements genres = doc.select("span[itemprop=genre]");
        Element plot = doc.select("div[itemprop=description]").first();
        
        try
        {
        tags = tags + FileManager.makeTag("posterURL", image.attr("data-original"));
        //catch error where no fan poster exists
        //store blank
        }catch(NullPointerException e)
                {
                     tags = tags + FileManager.makeTag("posterURL", "");
       
                }
        
        String showGenres = "";
        for(Element genre : genres)
        {
            showGenres = genre.text() + ", " + showGenres;
        }
        ColorCmd.println("Genres: " + showGenres, fgGreenBgWhite);
        ColorCmd.println("Plot: " + plot.text(), fgGreenBgWhite);
        tags = tags + FileManager.makeTag("genres", showGenres) + FileManager.makeTag("plot", plot.text());
       
      

        return tags;
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
           search = value.select("h1").text().replace(value.select("h1").select("span").text(), "").replace(value.select("h1").select("div").text(), "").trim();// + " ettv";
            
        }

        return search;
    }
}
