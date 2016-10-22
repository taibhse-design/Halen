/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Anime;

import Halen3.IO.GlobalSharedVariables;
import static Halen3.IO.GlobalSharedVariables.driver;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author brenn
 */
public class AniChartScraper
{

    public static void main(String args[]) throws IOException, InterruptedException
    {

        GlobalSharedVariables.startChromeDriver();

        driver.get("http://anichart.net/fall");
        //wait for pages 5 second load, 
        //get page html
        Thread.sleep(7000);
        String html = driver.getPageSource();

        Document doc = Jsoup.parse(html);
        Elements cards = doc.select("div.card.ng-scope");
        int x = 0;
        for (Element card : cards)
        {
            x += 1;

            //  if(x > 0)
            {
                System.out.println(x);
                Document doc2 = Jsoup.parse(card.html());
// 

                Element name = doc2.select("div.card__title").select("a").first();
                String n = name.text();
                String l = name.attr("href");

                 System.out.println("Name: " + n);
                
                System.out.println("Link: " + l);
                
                driver.get(l);
                //wait for pages 5 second load, 
                //get page html
                Thread.sleep(7000);
                String html2 = driver.getPageSource();

                Document doc3 = Jsoup.parse(html2);
                Element type = doc3.select("div[ng-show=seriesVm.s.type]").last();
                String t = type.text();
                Element genre = doc2.select("div.genFilter").first();
                String g = genre.text();
                Element image = doc2.select("a.thumb").first();
                String i = image.attr("style").substring(image.attr("style").indexOf("url('") + 5, image.attr("style").indexOf("');"));
                Element airing = doc2.select("div.card__count").first();
                String a = airing.attr("tooltip-title");
                Element plot = doc2.select("div.des.ng-binding").first();
                String p = plot.text();
                Element eps = doc2.select("span[ng-show=a.anime.episodes]").first();
                String e = eps.text();
                
                

               
                System.out.println("Type: " + t);
                System.out.println("Genre: " + g);
                System.out.println("Image: " + i);
                System.out.println("Airing: " + a);
                System.out.println("Episodes: " + e);
                System.out.println("Plot: " + p);

                System.out.println("\n\n\n\n\n");
            }
        }

        GlobalSharedVariables.endChromeDriver();

        // System.out.println(html);
    }

}
