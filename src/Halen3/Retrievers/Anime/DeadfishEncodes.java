/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Anime;

import Halen3.IO.FileManager;
import Halen3.IO.GlobalSharedVariables;
import java.awt.List;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author brenn
 */
public class DeadfishEncodes
{

    public static void main(String args[]) throws FileNotFoundException
    {
      //  GlobalSharedVariables.startChromeDriver();
       // createNewDeadFishRule("91 days", "http://deadfishencod.es/91_Days/", "c://test");
        createNewDeadFishRule("91 days", "http://deadfishencod.es/91_Days/", "c://test//91 days", "c://test", "91 days");
        checkForDeadfishUpdates(FileManager.launchPath() + "\\rules\\anime\\91 days.xml");
        GlobalSharedVariables.endChromeDriver();
    }

    public static void checkForDeadfishUpdates(String pathTORule) throws FileNotFoundException
    {
        List oldData = FileManager.readFile(pathTORule);
        String seriesURL = FileManager.returnTag("seriesURL", oldData.getItem(0));
        String moveToFolder = FileManager.returnTag("moveToFolder", oldData.getItem(0));
        String searchInFolder = FileManager.returnTag("searchInFolder", oldData.getItem(0));
        String searchFor = FileManager.returnTag("searchFor", oldData.getItem(0));

        List newData = new List();
        newData.add(getSeriesData(seriesURL, moveToFolder, searchInFolder, searchFor));
        List eps = getListOfEpisodes(seriesURL);
        System.out.println("\n\n\n");
        if ((eps.getItemCount() + 1) > oldData.getItemCount())
        {
            for (int i = 0; i < eps.getItemCount(); i++)
            {
                if (i >= (oldData.getItemCount() - 1))
                {
                    System.out.println(i + "   " + eps.getItem(i));
                } else
                {
                    eps.replaceItem(FileManager.updateTag("downloaded", eps.getItem(i), FileManager.returnTag("downloaded", oldData.getItem(i + 1))), i);
                    System.out.println(i + "   " + eps.getItem(i));
                    System.out.println(i + "   " + oldData.getItem(i + 1));
                }

                newData.add(eps.getItem(i));
            }

            PrintWriter out = new PrintWriter(pathTORule);
      //  out.println(newData.getItem(0));

            for (int i = 0; i < newData.getItemCount(); i++)
            {
                out.println(newData.getItem(i));
            }

            out.close();
            System.out.println("NEW EPS EXIST");
        }

    }

    public static void createNewDeadFishRule(String ruleName, String seriesURL, String moveToFolder, String searchInFolder, String searchFor) throws FileNotFoundException
    {
        GlobalSharedVariables.startChromeDriver();

        String seriesData = getSeriesData(seriesURL, moveToFolder, searchInFolder, searchFor);
        List episodes = getListOfEpisodes(FileManager.returnTag("episodesLink", seriesData));//seriesURL);

        GlobalSharedVariables.endChromeDriver();

        PrintWriter out = new PrintWriter(FileManager.launchPath() + "\\rules\\anime\\" + ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim() + ".xml");
        out.println(seriesData);

        for (int i = 0; i < episodes.getItemCount(); i++)
        {
            out.println(episodes.getItem(i));
        }

        out.close();
    }

    private static String getSeriesData(String seriesURL, String moveToFolder, String searchInFolder, String searchFor)
    {

        System.out.println("\n");
        String title, genre, plot, imageURL, director = null, publisher = null, rated = null, duration = null, status = null, episodesLink = null;
        String url = seriesURL;//"http://deadfishencod.es/91_Days/";
        //   GlobalSharedVariables.moveDriverOffScreen = false;
        //  GlobalSharedVariables.startChromeDriver();
        GlobalSharedVariables.driver.get(url);

        WebDriverWait wait = new WebDriverWait(GlobalSharedVariables.driver, 20);

     //   wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ttl")));
        while (ExpectedConditions.visibilityOfElementLocated(By.className("ttl")).equals(false))
        {
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(DeadfishEncodes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String html = GlobalSharedVariables.driver.getPageSource();
        //System.out.println(html);

        Document doc = Jsoup.parse(html);
        Element i = doc.select("img.cvr").first();
        Elements t = doc.select("h1.ttl");
        Element g = doc.select("div.animeGenreList").select("a").first();
        Element p = doc.select("div.related_anime").first();
        Elements details = doc.select("div.anm_ifo").select("p");
        Elements as = doc.select("a");
        //  Elements details = detailList.select("p");
        System.out.println("TITLE: " + t.text());
        title = t.text();
        System.out.println("GENRE: " + g.text());
        genre = g.text();
        System.out.println("PLOT: " + p.text());
        plot = p.text();
        System.out.println("IMAGE URL: " + i.attr("src"));
        imageURL = i.attr("src");

        for (Element a : as)
        {
            if (a.attr("title").contains("Go to Downloads"))
            {
                episodesLink = a.attr("href");
                System.out.println("EPISODE LIST LINK: " + a.attr("href"));
                break;
            }
        }
        for (Element detail : details)
        {
            if (detail.text().toLowerCase().contains("director"))
            {
                System.out.println(detail.text());
                director = detail.text();
            } else if (detail.text().toLowerCase().contains("publisher"))
            {
                System.out.println(detail.text());
                publisher = detail.text();
            } else if (detail.text().toLowerCase().contains("rated"))
            {
                System.out.println(detail.text());
                rated = detail.text();
            } else if (detail.text().toLowerCase().contains("duration"))
            {
                System.out.println(detail.text());
                duration = detail.text();
            } else if (detail.text().toLowerCase().contains("status"))
            {
                System.out.println(detail.text() + "\n\n\n");
                status = detail.text();
            }

        }

        //  GlobalSharedVariables.endChromeDriver();
        String imagePath = FileManager.launchPath() + "\\graphics\\anime-posters\\" + title.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim() + ".jpg";
        try
        {
            InputStream in = new BufferedInputStream(new URL(imageURL).openStream());
            OutputStream out = new BufferedOutputStream(new FileOutputStream(imagePath));

            for (int x; (x = in.read()) != -1;)
            {
                out.write(x);
            }
            in.close();
            out.close();
        } catch (MalformedURLException e)
        {
            imagePath = "null";
        } catch (IOException e)
        {
            imagePath = "null";
        }
        String dataLine = FileManager.makeTag("title", title)
                + FileManager.makeTag("genre", genre)
                + FileManager.makeTag("plot", plot)
                + FileManager.makeTag("imageURL", imageURL)
                + FileManager.makeTag("image", imagePath)
                + FileManager.makeTag("director", director)
                + FileManager.makeTag("publisher", publisher)
                + FileManager.makeTag("rated", rated)
                + FileManager.makeTag("duration", duration)
                + FileManager.makeTag("status", status)
                + FileManager.makeTag("seriesURL", seriesURL)
                + FileManager.makeTag("moveToFolder", moveToFolder)
                + FileManager.makeTag("searchInFolder", searchInFolder)
                + FileManager.makeTag("searchFor", searchFor)
                + FileManager.makeTag("episodesLink", episodesLink);
        System.out.println("\n" + dataLine + "\n");
        //title, genre, plot, imageURL, director, publisher, rated, duration, status;
        return dataLine;
    }

    private static List getListOfEpisodes(String seriesURL)
    {
        List episodes = new List();
        if (seriesURL.endsWith("/"))
        {
            // System.out.println(seriesURL);
            seriesURL = seriesURL.substring(0, seriesURL.length() - 1);
            //  System.out.println(seriesURL);
        }
        seriesURL = seriesURL.replace("http://", "");
        seriesURL = seriesURL.replace("/", "/anime-downloads/");
        seriesURL = "http://" + seriesURL;
        seriesURL = seriesURL.replace("_", "-");

        System.out.println("URL TO FIND EPISODE LIST: " + seriesURL);

        GlobalSharedVariables.driver.get(seriesURL);

        WebDriverWait wait = new WebDriverWait(GlobalSharedVariables.driver, 20);
      //  wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("val-ep")));

        while (ExpectedConditions.visibilityOfElementLocated(By.className("val-ep")).equals(true))
        {
            try
            {
                Thread.sleep(1000);
                System.out.println("NOT FOUND");
            } catch (InterruptedException ex)
            {
                Logger.getLogger(DeadfishEncodes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String html = GlobalSharedVariables.driver.getPageSource();

        Document doc = Jsoup.parse(html);
        Elements links = doc.select("a.lst");

        for (Element link : links)
        {
            // System.out.println(link.attr("href"));
            GlobalSharedVariables.driver.get(link.attr("href"));
            wait = new WebDriverWait(GlobalSharedVariables.driver, 20);
         //   wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("slide-name")));

            while (ExpectedConditions.visibilityOfElementLocated(By.className("slide-name")).equals(false))
            {
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(DeadfishEncodes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            html = GlobalSharedVariables.driver.getPageSource();
            doc = Jsoup.parse(html);
            Element data = doc.select("div.slide-name").first();
            Elements downloads = doc.select("a");
            String magnet = "";
            for (Element download : downloads)
            {
                //  System.out.println(download.attr("href"));
                if (download.attr("href").contains("nyaa") && download.attr("href").contains("page=download"))
                {
                    magnet = download.attr("href");
                    break;
                }
            }

            String name = data.text();

            String epData = FileManager.makeTag("name", name.replace("sub ", ""))
                    + FileManager.makeTag("magnet", magnet)
                    + FileManager.makeTag("downloaded", "false");
            System.out.println(epData + "\n");

            episodes.add(epData);
        }

    //    GlobalSharedVariables.endChromeDriver();
        return episodes;
    }
}
