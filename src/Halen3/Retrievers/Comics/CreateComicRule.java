/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Comics;

import static Halen3.IO.GlobalSharedVariables.driver;
import static Halen3.IO.GlobalSharedVariables.endChromeDriver;
import static Halen3.IO.GlobalSharedVariables.pathToChromeDriver;
import static Halen3.IO.GlobalSharedVariables.pathToChromePortable;
import static Halen3.IO.GlobalSharedVariables.startChromeDriver;
import Halen3.IO.FileManager;
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 *
 * @author brenn
 */
public class CreateComicRule
{

    public static void main(String args[]) throws InterruptedException, FileNotFoundException, IOException
    {
        System.out.println(System.getProperty("user.home") +"\\Downloads");
        String text = "new! name";
        
      //  startChromeDriver();
        System.out.println(text.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim());
        saveNewSeries("http://readcomiconline.to/Comic/Think-Tank-Creative-Destruction", "", "download");
       saveNewSeries("http://kissmanga.com/Manga/Platina-End", "", "download");
        saveNewSeries("http://readcomiconline.to/Comic/Iron-Man-Bad-Blood", "", "download");
        saveNewSeries("http://readcomiconline.to/Comic/Invincible-Iron-Man-2015", "", "download");
        saveNewSeries("http://readcomiconline.to/Comic/IXth-Generation", "", "download");
       saveNewSeries("http://readcomiconline.to/Comic/Lazarus-2013", "", "download");
       saveNewSeries("http://readcomiconline.to/Comic/Spider-Girl-2011", "", "download");
        saveNewSeries("http://readcomiconline.to/Comic/Spider-Gwen-I", "", "download");
        saveNewSeries("http://readcomiconline.to/Comic/Spider-Gwen-II", "", "download");
       // endChromeDriver();
    }

    public static void saveNewSeries(String url, String name, String downloadTo) throws InterruptedException, IOException
    {
       
        System.out.println("#########################################################################################################");
        System.out.println("   CREATING NEW COMIC RULE - " + url);
        System.out.println("#########################################################################################################\n");
        //only run if provided url is readcomiconline or kissmanga
        if (url.toLowerCase().contains("readcomiconline") || url.toLowerCase().contains("kissmanga"))
        {
            startChromeDriver();
            PrintWriter print = null;
            try
            {
                //get list of all comic issues and current details of series
                List data = getDetails(url);

                //if given name for rule is blank, use name of comic series
                if (name.trim().equals("") || name == null)
                {
                    name = FileManager.returnTag("title", data.getItem(0));
                }
                name = name.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();
                //start printer for rule
                print = new PrintWriter(FileManager.launchPath() + "\\rules\\comics\\" + name + ".xml");
                //loop and print all rule data
                for (int i = 0; i < data.getItemCount(); i++)
                {
                    if (i == 0)
                    {
                        if(!new File(downloadTo).isDirectory() || !new File(downloadTo).exists())
                        {
                            downloadTo = System.getProperty("user.home") +"\\Downloads";
                        }
                        print.println(data.getItem(i) + "<downloadTo>" + downloadTo + "</downloadTo>");
                    } else
                    {
                        print.println(data.getItem(i));
                    }
                }
                //close printer on completion
                print.close();
            } catch (FileNotFoundException ex)
            {
                //report error
                System.out.println("ERROR PRINTING OUT RULES! - FILE NOT FOUND");
                JOptionPane.showMessageDialog(null, "ERROR PRINTING OUT RULES! - FILE NOT FOUND", "ERROR", JOptionPane.WARNING_MESSAGE);

                Logger.getLogger(CreateComicRule.class.getName()).log(Level.SEVERE, null, ex);
            } finally
            {
                //close printer on serious error
              //  print.close();
            }
            
            endChromeDriver();
        } else
        {
            //alert that url is invalid
            System.out.println("ENTER VALID URL - ONLY READCOMICONLINE AND KISSMANGA ARE SUPPORTED!");
            JOptionPane.showMessageDialog(null, "ENTER VALID URL - ONLY READCOMICONLINE AND KISSMANGA ARE SUPPORTED!", "ERROR", JOptionPane.WARNING_MESSAGE);

        }

        System.out.println("\n#########################################################################################################\n");
    }

    /**
     * works with getting issues from readcomiconline and kissmanga
     *
     * @param URL
     * @return
     * @throws InterruptedException
     * @throws java.io.IOException
     */
    public static List getDetails(String URL) throws InterruptedException, IOException
    {
        String seriesTitle, imageURL = null, imgurURL = null, genres = null, publisher = null, writer = null, author = null, artist = null, publicationDate = null, status = null, summary = null;

        // String URL = "http://kissmanga.com/Manga/Full-Metal-Panic-Another";
        //set driver
//        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
//        //set options to use portable chrome to avoid messing with main browser
//        ChromeOptions options = new ChromeOptions();
//        options.setBinary(pathToChromePortable);
//
//        //start driver
//        ChromeDriver driver = new ChromeDriver();
//        driver.manage().window().setPosition(new Point(-2000, 0));
        //tell to wait 10 seconds for page load
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //get url
        driver.get(URL);
        //wait for pages 5 second load, 
        WebElement myDynamicElement = driver.findElement(By.className("listing"));
        //get page html
        String html = driver.getPageSource();

        //prep to parse code
        Document doc = Jsoup.parse(html);
        //create list to store issues in
        List issues = new List();

        //get comic title
        Element detailCode = doc.select("div.barContent").first();
        Element title = detailCode.select("a.bigChar").first();
        System.out.println("\nTitle: " + title.text());
        seriesTitle = title.text();
        //get comic preview image
        Elements images = doc.select("img");

        for (Element image : images)
        {
//            if (URL.contains("readcomiconline"))
//            {
              //  if (image.attr("width").equals("190px"))
              //  {
                      //navigate to page with image
//                    driver.navigate().to(image.attr("src").trim());
//                    //take screenshot
//                    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//                    // save screenshot to file
//                    FileUtils.copyFile(scrFile, new File(FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9. -]", "_") + ".jpg"));

                    
//                    BufferedImage bufImgOne = ImageIO.read(new URL(image.attr("src")));
//                   ImageIO.write(bufImgOne, "jpg", new File(FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9. -]", "_") + ".jpg"));
//                     imageURL = FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle + ".jpg";
//                    System.out.println("Image URL:  " + imageURL);
//                    break;
               // }
//            } else if (URL.contains("kissmanga"))
//            {
                //only retrieve or update image if one does not already exist
                if (image.attr("width").equals("190px"))// && !(new File(FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9.-]", " ").replaceAll("\\s+", " ").trim() + ".png").exists()))
                {
                    //navigate to page with image
                    System.out.println(image.attr("src").trim());
                    driver.navigate().to(image.attr("src").trim());
                    Thread.sleep(10000);
                    //take screenshot
                    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    // save screenshot to file
                    FileUtils.copyFile(scrFile, new File(FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim() + ".png"));

                   // Image src = ImageIO.read(new File(FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim() + ".png"));

                  //  int x = 0, y = 0, w = 400, h = 520;

                  //  BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                  //  dst.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);

                  //  ImageIO.write(dst, "png", new File(FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim() + ".png"));

                    imageURL = FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9.-]", " ").replaceAll("\\s+", " ").trim() + ".png";
                     FileManager.trimWhiteSpaceFromImage(imageURL);
                    System.out.println("Image URL:  " + imageURL);
                    
                    imgurURL = UploadToImgur.uploadImageToImgurAndGetImageURL(imageURL);
                    System.out.println("Imgur URL:  " + imgurURL);
               }//else
//                {
//                      imageURL = FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9.-]", " ").replaceAll("\\s+", " ").trim() + ".png";
//                      System.out.println("Image URL:  " + imageURL);
//                      imgurURL = UploadToImgur.uploadImageToImgurAndGetImageURL(imageURL);
//                }
           // }
        }
        //end driver
     //   driver.close();
        //get comic details and stats
        Elements details = detailCode.select("p");
        for (int i = 0; i < details.size(); i++)
        {
            Element detail = details.get(i);

            if (detail.select("span.info").text().contains("Genres:"))
            {
                System.out.println("Genres:  " + detail.select("a").text());
                genres = detail.select("a").text();

            } else if (detail.select("span.info").text().contains("Publisher:"))
            {
                System.out.println("Publisher:  " + detail.select("a").text());
                publisher = detail.select("a").text();

            } else if (detail.select("span.info").text().contains("Writer:"))
            {
                System.out.println("Writer:  " + detail.select("a").text());
                writer = detail.select("a").text();

            } else if (detail.select("span.info").text().contains("Author:"))
            {
                System.out.println("Author:  " + detail.select("a").text());
                author = detail.select("a").text();

            } else if (detail.select("span.info").text().contains("Artist:"))
            {
                System.out.println("Artist:  " + detail.select("a").text());
                artist = detail.select("a").text();

            } else if (detail.select("span.info").text().contains("Publication date:"))
            {
                System.out.println(detail.select("p").text());
                publicationDate = detail.select("p").text();

            } else if (detail.select("span.info").text().contains("Status:"))
            {
                System.out.println("Status:  " + detail.select("p").text().trim().replace("Bookmark", "").split("           ")[0].replace("Status: ", ""));
                status = detail.select("p").text().trim().replace("Bookmark", "").split("           ")[0].replace("Status: ", "");

            } else if (detail.select("span.info").text().contains("Summary:"))
            {
                detail = details.get(i + 1);
                System.out.println("Summary:  " + detail.select("p").text() + "\n");
                summary = detail.select("p").text();
            }

        }

        for (Element table : doc.select("table.listing"))
        {
            for (Element row : table.select("tr"))
            {
                Elements tds = row.select("td").select("a");
                if (!tds.text().isEmpty())
                {
                    String completeURL = URL;
                    completeURL = completeURL.replace("http://", "");
                    completeURL = completeURL.substring(0, completeURL.indexOf("/"));
                    completeURL = "http://" + completeURL + tds.attr("href") + "&readType=1";

                    issues.add("<name>" + tds.text() + "</name><link>" + completeURL + "</link><downloaded>false</downloaded>");
                    System.out.println(tds.text() + ":   " + completeURL);
                }
            }
        }

        List data = new List();
        data.add("<title>" + seriesTitle + "</title><url>" + URL + "</url><imageURL>" + imageURL + "</imageURL><imgurURL>" + imgurURL + "</imgurURL><genres>" + genres + "</genres><publisher>" + publisher + "</publisher><author>" + author + "</author><writer>" + writer + "</writer><artist>" + artist + "</artist><publicationDate>" + publicationDate + "</publicationDate><status>" + status + "</status><summary>" + summary + "</summary>");

        for (int i = 0; i < issues.getItemCount(); i++)
        {
            data.add(issues.getItem(i));
        }

//        System.out.println("\n\n");
//
//        for (int i = 0; i < data.getItemCount(); i++)
//        {
//            System.out.println(data.getItem(i));
//        }
        return data;
    }
}
