package Halen3.Retrievers.Films.Trakt;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgBlueBgWhite;
import com.gargoylesoftware.htmlunit.BrowserVersion;
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
public class FilmTraktParser
{

    public static void main(String args[]) throws IOException
    {
        String url = "https://trakt.tv/movies/logan-2017";

        List data = getData(url);

        for (int i = 0; i < data.getItemCount(); i++)
        {
            System.out.println(data.getItem(i));
        }

    }

    public static List getData(String url) throws IOException
    {

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
        Elements aTags = doc.select("a");

        loop:
        for (Element value : aTags)
        {
            //this grabs the fanart link and attampts to download all fanart
            if (value.attr("href").contains("fanart"))
            {
                ColorCmd.println("Fanart Page URL: " + value.attr("href"), fgBlueBgWhite);

                Halen3.Retrievers.Films.Trakt.FilmFanartDownloader.download(value.attr("href"));
                break loop;
            }

        }

        List data = new List();

        data.add(FileManager.makeTag("search", getSearchParams(url)) + FileManager.makeTag("url", url) + getFilmData(url) + FileManager.makeTag("image", Halen3.Retrievers.Films.Trakt.FilmFanartDownloader.imagePath) + "<retrieved>false</retrieved>");
        // data.add("<retrieved>false</retrieved>");

        return data;
    }

    public static String getFilmData(String url) throws IOException
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
        for (Element i : images)
        {
            // System.out.println
            if (i.attr("data-original").contains("/posters/thumb/"))
            {
                
                image = i;
                ColorCmd.println("Film Poster URL: " + image.attr("data-original"), fgBlueBgWhite);
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
        } catch (NullPointerException e)
        {
            tags = tags + FileManager.makeTag("posterURL", "");

        }

        String filmGenres = "";
        for (Element genre : genres)
        {
            filmGenres = genre.text() + ", " + filmGenres;
        }
        ColorCmd.println("Genres: " + filmGenres, fgBlueBgWhite);
        tags = tags + FileManager.makeTag("genres", filmGenres) + FileManager.makeTag("plot", plot.text());

        
        String filmRunTime = "";
        String filmLanguage = "";
        String filmDirector = "";
        String filmReleased = "";

        Elements lis = doc.select("li");
        for (Element li : lis)
        {
            if (li.text().contains("Director"))
            {
                filmDirector = li.text();
            } else if (li.text().contains("Released"))
            {
                filmReleased = li.text();
            } else if (li.text().contains("Language"))
            {
                filmLanguage = li.text();
            }else if (li.text().contains("Runtime"))
            {
                filmRunTime = li.text();
            }
        }
        
        ColorCmd.println("Director: " + filmDirector, fgBlueBgWhite);
        ColorCmd.println("ReleaseDate: " + filmReleased, fgBlueBgWhite);
        ColorCmd.println("Language: " + filmLanguage, fgBlueBgWhite);
        ColorCmd.println("Run Time: " + filmRunTime, fgBlueBgWhite);

        tags = tags + FileManager.makeTag("director", filmDirector);
        tags = tags + FileManager.makeTag("released", filmReleased);
        tags = tags + FileManager.makeTag("language", filmLanguage);
        tags = tags + FileManager.makeTag("runtime", filmRunTime);
        //Element plot = doc.select("div[itemprop=description]").first();

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
            //long text piece here retrieves the title of the film                                                                                          small section here retrieves the films year of release                       
            search = value.select("h1").text().replace(value.select("h1").select("span").text(), "").replace(value.select("h1").select("div").text(), "").trim() + " " + value.select("h1").select("span").text();

        }

        return search;
    }
}
