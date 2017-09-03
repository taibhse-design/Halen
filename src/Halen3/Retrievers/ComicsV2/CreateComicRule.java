/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Comics;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import static Halen3.CommandLine.ColorCmd.fgYellowBgWhite;
import static Halen3.IO.GlobalSharedVariables.driver;
import static Halen3.IO.GlobalSharedVariables.endChromeDriver;
import static Halen3.IO.GlobalSharedVariables.startChromeDriver;
import Halen3.IO.FileManager;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

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
                ColorCmd.println("ERROR PRINTING OUT RULES! - FILE NOT FOUND", fgRedBgWhite);
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
            ColorCmd.println("ENTER VALID URL - ONLY READCOMICONLINE AND KISSMANGA ARE SUPPORTED!", fgRedBgWhite);
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
        //get url
        driver.get(URL);
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       
        System.out.println("poop");
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
        ColorCmd.println("Title: " + title.text(), fgYellowBgWhite);
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
                    ColorCmd.println(image.attr("src").trim(), fgYellowBgWhite);
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

                    imageURL = "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9.-]", " ").replaceAll("\\s+", " ").trim() + ".png";
                     FileManager.trimWhiteSpaceFromImage(FileManager.launchPath() + imageURL);
                    ColorCmd.println("Image URL:  " + imageURL, fgYellowBgWhite);
                    
                    try
                    {
                    imgurURL = UploadToImgur.uploadImageToImgurAndGetImageURL(FileManager.launchPath() + imageURL);
                    ColorCmd.println("Imgur URL:  " + imgurURL, fgYellowBgWhite);
                    }catch(IOException e)
                    {
                        ColorCmd.println("Error uploading image to imgur....." + e, fgRedBgWhite);
                       imgurURL = ""; 
                    }
                    
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
                ColorCmd.println("Genres:  " + detail.select("a").text(), fgYellowBgWhite);
                genres = detail.select("a").text();

            } else if (detail.select("span.info").text().contains("Publisher:"))
            {
                ColorCmd.println("Publisher:  " + detail.select("a").text(), fgYellowBgWhite);
                publisher = detail.select("a").text();

            } else if (detail.select("span.info").text().contains("Writer:"))
            {
                ColorCmd.println("Writer:  " + detail.select("a").text(), fgYellowBgWhite);
                writer = detail.select("a").text();

            } else if (detail.select("span.info").text().contains("Author:"))
            {
                ColorCmd.println("Author:  " + detail.select("a").text(), fgYellowBgWhite);
                author = detail.select("a").text();

            } else if (detail.select("span.info").text().contains("Artist:"))
            {
                ColorCmd.println("Artist:  " + detail.select("a").text(), fgYellowBgWhite);
                artist = detail.select("a").text();

            } else if (detail.select("span.info").text().contains("Publication date:"))
            {
                ColorCmd.println(detail.select("p").text(), fgYellowBgWhite);
                publicationDate = detail.select("p").text();

            } else if (detail.select("span.info").text().contains("Status:"))
            {
                ColorCmd.println("Status:  " + detail.select("p").text().trim().replace("Bookmark", "").split("           ")[0].replace("Status: ", ""), fgYellowBgWhite);
                status = detail.select("p").text().trim().replace("Bookmark", "").split("           ")[0].replace("Status: ", "");

            } else if (detail.select("span.info").text().contains("Summary:"))
            {
                detail = details.get(i + 1);
                ColorCmd.println("Summary:  " + detail.select("p").text(), fgYellowBgWhite);
                summary = detail.select("p").text();
            }

        }

        ColorCmd.println("", fgYellowBgWhite);
        
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
                    ColorCmd.println(tds.text() + ":   " + completeURL, fgYellowBgWhite);
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
