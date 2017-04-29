package Halen3.Retrievers.Anime;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import static Halen3.CommandLine.ColorCmd.fgYellowBgWhite;
import Halen3.IO.FileManager;
import Halen3.IO.GlobalSharedVariables;
import static Halen3.IO.GlobalSharedVariables.driver;
import Halen3.Retrievers.Comics.UploadToImgur;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * @author TAIBHSE
 */
public class AnilistDataScraper
{

    public static void main(String args[]) throws IOException, InterruptedException
    {
        
    }
    
    public static void createNewAnimeRule(String ruleName, String seriesURL, String moveToFolder, String searchInFolder, String searchFor, String nyaaSearch) throws FileNotFoundException
    {
        String firstLine = getData(seriesURL) + FileManager.makeTag("seriesURL", seriesURL) + FileManager.makeTag("moveToFolder", moveToFolder) + FileManager.makeTag("searchInFolder", searchInFolder) + FileManager.makeTag("searchFor", searchFor) + FileManager.makeTag("nyaaSearch", nyaaSearch);
    
        PrintWriter out = new PrintWriter(FileManager.launchPath() + "\\rules\\anime\\" + ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim() + ".xml");
        out.println(firstLine);
       
        int epCount = Integer.parseInt(FileManager.returnTag("episodes", firstLine));
        for(int i = 1; i <= epCount; i++)
        {
            out.println("<ep>" + i + "</ep><retrieved>false<retrieved>");
        }
        
        out.close();
    
    
    }
    public static String getData(String url)
    {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

      //  String url = "http://anilist.co/anime/20958/ShingekinoKyojin2";
        
        String dataTag = "";
        GlobalSharedVariables.startChromeDriver();

        driver.get(url);
        //wait for pages 5 second load, 
        //get page html
        
        if(!driver.getPageSource().contains("<h1>"))
        {
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(AnilistDataScraper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        String html = driver.getPageSource();
        
    //    System.out.println(html);
        
        Document doc = Jsoup.parse(html);
        Element title = doc.select("h1").first();
        Element description = doc.select("div.series__description").first();
        Elements seriesData = doc.select("div.series__data").select("div");
        Elements images = doc.select("img");
        
        
        
        System.out.println("Title: " + title.text());
        dataTag = dataTag + FileManager.makeTag("title", title.text());
         System.out.println("description: " + description.text());
         dataTag = dataTag + FileManager.makeTag("description", description.text());
         
         for(Element image : images)
         {
             if(image.attr("src").contains("https://cdn.anilist.co/img/dir/anime"))
             {
                 System.out.println("Image URL: " + image.attr("src"));
                 dataTag = dataTag + FileManager.makeTag("ImageURL", image.attr("src"));
                 
                //navigate to page with image
                    driver.navigate().to(image.attr("src").trim());
                 try
                 {
                     Thread.sleep(10000);
                 } catch (InterruptedException ex)
                 {
                     Logger.getLogger(AnilistDataScraper.class.getName()).log(Level.SEVERE, null, ex);
                 }
                    //take screenshot
                    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                 try
                 {
                     // save screenshot to file
                     FileUtils.copyFile(scrFile, new File(FileManager.launchPath() + "\\graphics\\anime-covers\\" + title.text().replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim() + ".jpg"));
                 } catch (IOException ex)
                 {
                     System.out.println("Failed to download anime image.....");
                     
                     Logger.getLogger(AnilistDataScraper.class.getName()).log(Level.SEVERE, null, ex);
                 }

                   // Image src = ImageIO.read(new File(FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim() + ".png"));

                  //  int x = 0, y = 0, w = 400, h = 520;

                  //  BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                  //  dst.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);

                  //  ImageIO.write(dst, "png", new File(FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim() + ".png"));

                     FileManager.trimWhiteSpaceFromImage(FileManager.launchPath() + "\\graphics\\anime-covers\\" + title.text().replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim() + ".jpg");
                     dataTag = dataTag + FileManager.makeTag("image", "\\graphics\\anime-covers\\" + title.text().replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim() + ".jpg");
                     
             }
         }
         int skipTrack = 0;
         for(Element data : seriesData)
         {
             if( skipTrack != 0)
             {
           //  System.out.println(data.select("div") + "\n");
           //  System.out.println(data.text() + "\n");
             
             
             if(data.text().contains("Episodes"))
             {
                System.out.println("Episodes: " + data.select("span").get(2).text());
                  dataTag = dataTag + FileManager.makeTag("episodes", data.select("span").get(2).text());
                     
             }else  if(data.text().contains("Genres"))
             {
                System.out.println("Genres: " + data.select("span").get(2).text().replaceAll("([A-Z])", " $1"));
                dataTag = dataTag + FileManager.makeTag("genres", data.select("span").get(2).text().replaceAll("([A-Z])", " $1"));
             }else  if(data.text().contains("Romaji"))
             {
                System.out.println("Romaji: " + data.select("span").get(2).text());
                   dataTag = dataTag + FileManager.makeTag("romaji", data.select("span").get(2).text());
            
             }else  if(data.text().contains("English"))
             {
                System.out.println("English: " + data.select("span").get(2).text());
                 dataTag = dataTag + FileManager.makeTag("english", data.select("span").get(2).text());
            
             }else  if(data.text().contains("Avg Score"))
             {
                System.out.println("Rating: " + data.select("span").get(2).text());
                 dataTag = dataTag + FileManager.makeTag("rating", data.select("span").get(2).text());
            
             }
             
             
             }
             
             skipTrack+=1;
         }
        
        System.out.println(dataTag);
        GlobalSharedVariables.endChromeDriver();
        
        return dataTag;

    }

    }
