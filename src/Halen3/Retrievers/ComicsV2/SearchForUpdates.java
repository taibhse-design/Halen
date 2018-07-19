 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.ComicsV2;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgWhite;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgYellow;
import static Halen3.CommandLine.ColorCmd.fgYellowBgWhite;
import Halen3.IO.FileManager;
import static Halen3.Retrievers.ComicsV2.CreateComicRule.getDetails;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author brenn
 */
public class SearchForUpdates
{

    public static void main(String args[]) throws InterruptedException, IOException
    {
        updateAllComicRules();
    }

    public static void updateAllComicRules() throws InterruptedException, IOException
    {

        //create list of comic rules found in comics folder
        File[] comicsList = new File(FileManager.launchPath() + "\\rules\\comics\\").listFiles();

        //loop through list of comic rules
        for (int i = 0; i < comicsList.length; i++)
        {
            updateComicRule(comicsList[i].getPath());
        }

    }

    public static void updateComicRule(String rulePath) throws InterruptedException, IOException
    {

        //load comic rule to search updates for
        //   String rulePath = "C:/Users/brenn/Documents/NetBeansProjects/Halen/build/rules/comics/think tank v2.xml";
        //System.out.println("#########################################################################################################");
        ColorCmd.println("", fgWhiteBgYellow);
        ColorCmd.printlnCenter("SEARCHING FOR COMIC UPDATES - " + new File(rulePath).getName(), fgWhiteBgYellow);
        ColorCmd.println("", fgWhiteBgYellow);
        ColorCmd.println("", fgWhiteBgWhite);
//  System.out.println("#########################################################################################################\n");

        //list to hold data already in file
        List saved = FileManager.readFile(rulePath);
        if (!FileManager.returnTag("status", saved.getItem(0)).contains("Completed"))
        {
            //list to hold results from online                use url from rules, grab from first line of saved list
            List downloaded = getDetails(FileManager.returnTag("url", saved.getItem(0)));
            //online proceed if there is a line size difference meaning new issues added
            if (downloaded.getItemCount() > saved.getItemCount())
            {
                //create a list to hold updated data
                List updated = new List();
                //add first line of downloaded, this is done incase things like authors or status changes or updated plot summary
                updated.add(downloaded.getItem(0) + "<downloadTo>" + FileManager.returnTag("downloadTo", saved.getItem(0)) + "</downloadTo>");// + "<moveToFolder>" + FileManager.returnTag("moveToFolder", saved.getItem(0)) + "</moveToFolder>");

                //create a string array just to hold list of issues, use loop to add them from the saved list, 
                String[] comSaved = new String[saved.getItemCount() - 1];
                for (int i = 1; i < saved.getItemCount(); i++)
                {
                    comSaved[i - 1] = saved.getItem(i);
                    // System.out.println("ORIGINAL LIST: " + comSaved[i - 1]);
                }

           // System.out.println("\n\n");
                //same as above, create a string array to hold list of issues from downloaded results, use loop to save, 
                //doing this to exclude first item which is always just series data
                String[] comDownloaded = new String[downloaded.getItemCount()];
                for (int i = 1; i < downloaded.getItemCount(); i++)
                {
                    comDownloaded[i - 1] = downloaded.getItem(i);
                    //  System.out.println("NEW LIST: " + comDownloaded[i - 1]);
                }

           // System.out.println("\n\n\n");
                //loop through downloaded list and compare against whats on file
                for (int i = 0; i < comDownloaded.length - 1; i++)
                {
                    //loop and compare each download result against saved to find matches,
                    inner:
                    for (int j = 0; j < comSaved.length; j++)
                    {
                    //if a match is found, update its state in the download list, 
                        //ie if it is marked as downloaded true in saved list, mark it as such in the download list
                        if (FileManager.returnTag("name", comDownloaded[i]).equals(FileManager.returnTag("name", comSaved[j])))
                        {
                            comDownloaded[i] = "<name>" + FileManager.returnTag("name", comDownloaded[i]) + "</name><link>" + FileManager.returnTag("link", comDownloaded[i]) + "</link><downloaded>" + FileManager.returnTag("downloaded", comSaved[j]) + "</downloaded>";
                            //   System.out.println("EXISTS   : " + comDownloaded[i]);
                            break inner;
                        }
                    }
                    //add each item in downloaded list to updated list, after it has been tested against whats on file
                    updated.add(comDownloaded[i]);
                }

                ColorCmd.println("", fgYellowBgWhite);
                ColorCmd.println("New issues found, added to list pending download.....", fgYellowBgWhite);
                ColorCmd.println("", fgYellowBgWhite);

                try
                {
                    //print updated list to file
                    PrintWriter out = new PrintWriter(rulePath);
                    for (int i = 0; i < updated.getItemCount(); i++)
                    {
                        //  System.out.println(updated.getItem(i));
                        out.println(updated.getItem(i));
                    }

                    out.close();
                } catch (FileNotFoundException e)
                {
                    ColorCmd.println("Error saving rule " + rulePath + ".....", fgRedBgWhite);
                }

            } else
            {
                //just update first tag
                saved.replaceItem(downloaded.getItem(0) + "<downloadTo>" + FileManager.returnTag("downloadTo", saved.getItem(0)) + "</downloadTo>" + "<moveToFolder>" + FileManager.returnTag("moveToFolder", saved.getItem(0)) + "</moveToFolder>", 0);

                try
                {
                    PrintWriter out = new PrintWriter(rulePath);
                    for (int i = 0; i < saved.getItemCount(); i++)
                    {
                        //  System.out.println(updated.getItem(i));
                        out.println(saved.getItem(i));
                    }

                    out.close();
                    ColorCmd.println("", fgYellowBgWhite);
                    ColorCmd.println("Skipping rule, no new updated issues, main stats updated instead.....", fgYellowBgWhite);
                    ColorCmd.println("", fgYellowBgWhite);

                } catch (FileNotFoundException e)
                {
                    ColorCmd.println("Error saving rule " + rulePath + ".....", fgRedBgWhite);
                }
            }
        } else
        {
            ColorCmd.println("", fgYellowBgWhite);
            ColorCmd.println("Skipping rule update, comic series is already completed.....", fgYellowBgWhite);
            ColorCmd.println("", fgYellowBgWhite);
        }
      //    System.out.println("\n#########################################################################################################\n");

    }

//    /**
//     * works with getting issues from readcomiconline and kissmanga
//     *
//     * @param URL
//     * @return
//     * @throws InterruptedException
//     */
//    private static List getDetails(String URL) throws InterruptedException
//    {
//        String seriesTitle, imageURL = null, genres = null, publisher = null, writer = null, author = null, artist = null, publicationDate = null, status = null, summary = null;
//
//        // String URL = "http://kissmanga.com/Manga/Full-Metal-Panic-Another";
//        //set driver
//        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
//        //set options to use portable chrome to avoid messing with main browser
//        ChromeOptions options = new ChromeOptions();
//        options.setBinary(pathToChromePortable);
//        //start driver
//        ChromeDriver driver = new ChromeDriver();
//
//        //tell to wait 10 seconds for page load
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        //get url
//        driver.get(URL);
//        //wait for pages 5 second load, 
//        WebElement myDynamicElement = driver.findElement(By.className("listing"));
//        //get page html
//        String html = driver.getPageSource();
//        //end driver
//        driver.close();
//
//        //prep to parse code
//        Document doc = Jsoup.parse(html);
//        //create list to store issues in
//        List issues = new List();
//
//        //get comic title
//        Element detailCode = doc.select("div.barContent").first();
//        Element title = detailCode.select("a.bigChar").first();
//        System.out.println("Title: " + title.text());
//        seriesTitle = title.text();
//        //get comic preview image
//        Elements images = doc.select("img");
//        for (Element image : images)
//        {
//            if (image.attr("width").equals("190px"))
//            {
//                System.out.println("Image URL:  " + image.attr("src"));
//                imageURL = image.attr("src");
//                break;
//            }
//        }
//
//        //get comic details and stats
//        Elements details = detailCode.select("p");
//        for (int i = 0; i < details.size(); i++)
//        {
//            Element detail = details.get(i);
//
//            if (detail.select("span.info").text().contains("Genres:"))
//            {
//                System.out.println("Genres:  " + detail.select("a").text());
//                genres = detail.select("a").text();
//
//            } else if (detail.select("span.info").text().contains("Publisher:"))
//            {
//                System.out.println("Publisher:  " + detail.select("a").text());
//                publisher = detail.select("a").text();
//
//            } else if (detail.select("span.info").text().contains("Writer:"))
//            {
//                System.out.println("Writer:  " + detail.select("a").text());
//                writer = detail.select("a").text();
//
//            } else if (detail.select("span.info").text().contains("Author:"))
//            {
//                System.out.println("Author:  " + detail.select("a").text());
//                author = detail.select("a").text();
//
//            } else if (detail.select("span.info").text().contains("Artist:"))
//            {
//                System.out.println("Artist:  " + detail.select("a").text());
//                artist = detail.select("a").text();
//
//            } else if (detail.select("span.info").text().contains("Publication date:"))
//            {
//                System.out.println(detail.select("p").text());
//                publicationDate = detail.select("p").text();
//
//            } else if (detail.select("span.info").text().contains("Status:"))
//            {
//                System.out.println("Status:  " + detail.select("p").text().trim().replace("Bookmark", "").split("           ")[0].replace("Status: ", ""));
//                status = detail.select("p").text().trim().replace("Bookmark", "").split("           ")[0].replace("Status: ", "");
//
//            } else if (detail.select("span.info").text().contains("Summary:"))
//            {
//                detail = details.get(i + 1);
//                System.out.println("Summary:  " + detail.select("p").text());
//                summary = detail.select("p").text();
//            }
//
//        }
//
//        for (Element table : doc.select("table.listing"))
//        {
//            for (Element row : table.select("tr"))
//            {
//                Elements tds = row.select("td").select("a");
//                if (!tds.text().isEmpty())
//                {
//                    String completeURL = URL;
//                    completeURL = completeURL.replace("http://", "");
//                    completeURL = completeURL.substring(0, completeURL.indexOf("/"));
//                    completeURL = "http://" + completeURL + tds.attr("href") + "&readType=1";
//
//                    issues.add("<name>" + tds.text() + "</name><link>" + completeURL + "</link><downloaded>false</downloaded>");
//                    System.out.println(tds.text() + ":   " + completeURL);
//                }
//            }
//        }
//
//        List data = new List();
//        data.add("<title>" + seriesTitle + "</title><imageURL><url>" + URL + "</url>" + imageURL + "</imageURL><genres>" + genres + "</genres><publisher>" + publisher + "</publisher><author>" + author + "</author><writer>" + writer + "</writer><artist>" + artist + "</artist><publicationDate>" + publicationDate + "</publicationDate><status>" + status + "</status><summary>" + summary + "</sumary>");
//
//        for (int i = 0; i < issues.getItemCount(); i++)
//        {
//            data.add(issues.getItem(i));
//        }
//
////        System.out.println("\n\n");
////
////        for (int i = 0; i < data.getItemCount(); i++)
////        {
////            System.out.println(data.getItem(i));
////        }
//        return data;
//    }
}
