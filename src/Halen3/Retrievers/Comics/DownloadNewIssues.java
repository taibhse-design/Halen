/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Comics;

import Halen3.EmailNotifier.SendEmailNotification;
import static Halen3.IO.GlobalSharedVariables.driver;
import static Halen3.IO.GlobalSharedVariables.endChromeDriver;
import static Halen3.IO.GlobalSharedVariables.pathToChromeDriver;
import static Halen3.IO.GlobalSharedVariables.pathToChromePortable;
import static Halen3.IO.GlobalSharedVariables.startChromeDriver;
import Halen3.IO.FileManager;
import static Halen3.IO.FileManager.delete;
import Halen3.IO.GlobalSharedVariables;
import static Halen3.IO.GlobalSharedVariables.driver;
import java.awt.AWTException;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.zeroturnaround.zip.ZipUtil;

/**
 *
 * @author brenn
 */
public class DownloadNewIssues
{
    //    public static boolean saveResults = false;
    static volatile boolean repeat = true;
    static volatile boolean runTimeCheck = true;

//    public static void downloadPage(String pageURL, String name, String path, int pageNumber) throws MalformedURLException, IOException
//    {
//        URL url = new URL(pageURL);
//        URLConnection conn = url.openConnection();
//        String type = conn.getContentType();
//        System.out.println("RETRIEVING: " + name.replaceAll("[^a-zA-Z0-9.-]", "_") + "_" + String.format("%03d", pageNumber) + "." + type.replace("image/", ""));
//        InputStream is = url.openStream();
//        OutputStream os = new FileOutputStream(path + String.format("%03d", pageNumber) + "." + type.replace("image/", ""));
//        byte[] b = new byte[2048];
//        int length;
//        while ((length = is.read(b)) != -1)
//        {
//            os.write(b, 0, length);
//        }
//        is.close();
//        os.close();
//    }
    private static void downloadIssuePagesConvertToCBZ(String[] pages, String issueName, String savePath) throws InterruptedException, MalformedURLException, IOException
    {
        //  List text = getLinksToPages(linkToIssue);

        String path;
        if (savePath.endsWith("/") || savePath.endsWith("\\"))
        {
            if (savePath.contains("\\"))
            {
                path = savePath + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "\\";
            } else
            {
                path = savePath + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "/";
            }
        } else if (savePath.contains("\\"))
        {
            path = savePath + "\\" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "\\";
        } else
        {
            path = savePath + "/" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "/";
        }
        //make folder to hold pages
        new File(path).mkdirs();
        //download pages to created folder
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < pages.length; i++)
        {
            Runnable worker = new MultiThreadedPageDownloader(pages[i], issueName, path, i);

            executor.execute(worker);
        }

        executor.shutdown();
        while (!executor.isTerminated())
        {
        }
        System.out.println("Finished all threads");

        File zip;

        if (savePath.endsWith("/") || savePath.endsWith("\\"))
        {

            zip = new File(savePath + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + ".zip");
            // path = savePath + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "\\";

        } else if (savePath.contains("\\"))
        {
            zip = new File(savePath + "\\" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + ".zip");

            //   path = savePath + "\\" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "\\";
        } else
        {
            zip = new File(savePath + "/" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + ".zip");

            // path = savePath + "/" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "/";
        }

        System.out.println("\nCOMPRESSING IMAGES TO ZIP FILE..........");
        File folder = new File(path);
        ZipUtil.pack(folder, zip);
        //rename zip file to cbz to make comic file
        System.out.println("\nCONVERTING ZIP FILE TO CBZ COMIC FILE..........");
        zip.renameTo(new File(zip.getPath().replace(".zip", ".cbz")));
        //delete the folder that has the images after the cbz file is made
        delete(folder);
    }

    private static void timeOutControl(final long startTime)
    {
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                while (runTimeCheck)
                {
                    long runningTime = System.currentTimeMillis();
                    long totalTime = runningTime - startTime;
                    //   System.out.println("Running Time: " + totalTime);
                    if (totalTime > 600000)
                    {
                        repeat = false;
                        runTimeCheck = false;
                        System.out.println("TIMEOUT REACHED AT 10 MINUTES!!!!!");

                    }

                }
            }
        });
        t.start();
    }

    public static void main(String args[]) throws InterruptedException, AWTException, IOException
    {

    }

    public static void downloadNewIssues()
    {
        GlobalSharedVariables.moveDriverOffScreen = false;
        //start chrome driver
        GlobalSharedVariables.startChromeDriver();
        //get list of comic rules to work with
        File[] comicsList = new File(FileManager.launchPath() + "\\rules\\comics\\").listFiles();

        //loop through list of comic rules
        for (int i = 0; i < comicsList.length; i++)
        {
            try
            {

                //search for updates to rule prior to downloading new issues
                SearchForUpdates.updateComicRule(comicsList[i].getPath());

                System.out.println("#########################################################################################################");
                System.out.println("   SEARCHING FOR NEW ISSUES OF " + comicsList[i].getName() + " TO DOWNLOAD");
                System.out.println("#########################################################################################################\n");

                //load list of issues
                List issues = FileManager.readFile(comicsList[i].getAbsolutePath());
                //add comic data to email list, if issues retrieved, it will email notification
                SendEmailNotification.retrievedComics.add(issues.getItem(0) + "<retIssues></retIssues>");
                //loop through comics list of issues
                for (int j = 1; j < issues.getItemCount(); j++) //start at 1 since 0 line is comic data
                {
                    //if issue status is false, download it
                    if (FileManager.returnTag("downloaded", issues.getItem(j)).equals("false"))
                    {
                        System.out.println("\n#########################################################################################################");
                        System.out.println("DOWNLOADING: " + FileManager.returnTag("name", issues.getItem(j)) + "\n");
                        System.out.println("#########################################################################################################");

                        //#############################################################################
                        //download issue, returns true or false if issue has been downloaded
                        boolean downloaded = downloadIssue(FileManager.returnTag("link", issues.getItem(j)), FileManager.returnTag("name", issues.getItem(j)), FileManager.returnTag("downloadTo", issues.getItem(0)));
                        //update this issue with download status,
                        issues.replaceItem(
                                FileManager.updateTag(
                                        "downloaded", //update this tag
                                        issues.getItem(j), //get line data to update
                                        String.valueOf(downloaded)), //use value from attempt to download
                                j); //issue number to update

                        //#############################################################################
                        //add issue to email list to notify if it has been downloaded
                        if (downloaded == true)
                        {
                            SendEmailNotification.retrievedComics.replaceItem(FileManager.updateTag("retIssues",
                                    SendEmailNotification.retrievedComics.getItem(i),
                                    FileManager.returnTag("retIssues", SendEmailNotification.retrievedComics.getItem(i)) + FileManager.returnTag("name", issues.getItem(j)) + "-!SPLIT!-"),
                                    i); //item to replace
                        }

                    }

                    //print update to file if not saveResults
                    if (GlobalSharedVariables.testing.equals("false")) //update rules results only if not testing
                    {
                        //print out update to rules
                        PrintWriter out = new PrintWriter(comicsList[i].getAbsolutePath());
                        for (int k = 0; k < issues.getItemCount(); k++)
                        {
                            out.println(issues.getItem(k));
                        }
                        out.close();
                    }
                }

            } catch (InterruptedException | IOException e)
            {
                System.out.println(e);
            }
        }
        //end chrome driver
        GlobalSharedVariables.endChromeDriver();
        // SendEmailNotification.sendEmailNotice(SendEmailNotification.createUpdateListMessage());

    }

    private static boolean downloadIssue(String url, String name, String downloadTo) throws InterruptedException, IOException
    {

        try
        {
            url = url.replace("&readType=1", "");
            url = url.replace("&readType=0", "");
            url = url.replace("&&quality=hq", "");
            url = url.replace("&&quality=lq", "");

            //  String url = "http://readcomiconline.to/Comic/Lazarus-2013/HC-1-The-First-Collection?id=2894";
            //start driver and set to be off screen or not
            // GlobalSharedVariables.moveDriverOffScreen = false;
            // GlobalSharedVariables.startChromeDriver();
            driver.manage().timeouts().implicitlyWait(9, TimeUnit.MINUTES);
            System.out.println("TRYING " + url);
            //used to test running time and kill if too long
            long startTime = System.currentTimeMillis();
            long runningTime = 0;
            //get expected page count

//            driver.get(url + "&readType=0&quality=hq");
//
//             WebDriverWait wait = new WebDriverWait(driver, 30);
//             wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectPage")));
            // try{Thread.sleep(7000);} catch (InterruptedException ex){Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);}
//            driver.findElement(By.id("selectReadType")).click();
//            Select select = new Select(driver.findElement(By.id("selectReadType")));
//            //    select.deselectAll();
//            select.selectByValue("0");//.selectByVisibleText("Value1");
//            try{Thread.sleep(7000);} catch (InterruptedException ex){Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);}
//        
//            String html = GlobalSharedVariables.driver.getPageSource();
//            Document doc = Jsoup.parse(html);
//            Element selectPage = doc.getElementById("selectPage");
//            int pageCount = selectPage.select("option").size();
            String pageLinks[] = null;
//
//            System.out.println("EXPECTED PAGE COUNT: " + pageCount);

//            try//this one
//            {
            //try retrieving pages
            driver.get(url + "&readType=1&quality=hq");

            //select = new Select(driver.findElement(By.id("selectReadType")));
            //select.selectByValue("1");//.selectByVisibleText("Value1");
            //if runs longer than 10 minutes, kills loop, this method runs on its own thread
            timeOutControl(startTime);

            //loop check code until page fully loads
            opLoop:
            while (repeat)
            {

                //loop += 1;
                //System.out.println(loop);
                //catch exceptions until page is fully loaded
                try
                {
                    //get page html
                    String html = driver.getPageSource();

                    //prep to parse code
                    Document doc = Jsoup.parse(html);
                    //get main div holding collection of images
                    Element main = doc.select("div#divImage").first(); //narrow code search down
                    // parse for all images
                    Elements pages = main.select("img");
                    pageLinks = new String[pages.size()];
                    //loop to get image source addresses (only loop if pages equal found page count)
                    // if (pages.size() == pageCount)
                    {
                        //loop to get image source addresses
                        pageSearch:
                        for (int i = 0; i < pages.size(); i++)
                        {
                            //System.out.println(pages.get(i).attr("src"));

                            // System.out.println("PAGE: " + pages.get(i).attr("src").trim());
                            //blank list if null to start over
                            if (pages.get(i).attr("src").trim().equals(""))
                            {
                                pageLinks = new String[pages.size()];
                                repeat = true;
                                runTimeCheck = true;
                                break pageSearch;

                            } else//add to list if not null
                            {
                                pageLinks[i] = pages.get(i).attr("src");
                                repeat = false;
                                runTimeCheck = false;
                            }
                        }
                    }
                } catch (NullPointerException | IllegalArgumentException e)
                {
                    // System.out.println(e);
                }
            }
            //reset for next rule to be able to run successfully
            repeat = true;

            boolean download = true;
            for (int i = 0; i < pageLinks.length; i++)
            {
                //page downloads carried out here
                //  System.out.println(link);
                if (pageLinks[i].equals(""))
                {
                    download = false;
                    break;
                }
                // downloadPage(pageLinks[i], "test", "D:\\test\\", i);
            }
            if (download = true)
            {
                downloadIssuePagesConvertToCBZ(pageLinks, name, downloadTo);
            }

//            } catch (Exception e)
//            {
//                System.out.println("error" + e.getLocalizedMessage());
//            }
        } catch (TimeoutException e)
        {
            System.out.println("TIMEOUT EXCEPTION!! SKIPPING " + name + " FOR NOW");
        }
        //end chrome driver
        //  GlobalSharedVariables.endChromeDriver();

        //create cbz path of issue to check if it exists
        if (downloadTo.endsWith("/") || downloadTo.endsWith("\\"))
        {

            if (new File(downloadTo + name.replaceAll("[^a-zA-Z0-9.-]", "_") + ".cbz").exists())
            {   //return true if issue created
                return true;
            } else
            {   //return false if issue not created
                return false;
            }
        } else if (downloadTo.contains("\\"))
        {
            if (new File(downloadTo + "\\" + name.replaceAll("[^a-zA-Z0-9.-]", "_") + ".cbz").exists())
            {   //return true if issue created
                return true;
            } else
            {   //return false if issue not created
                return false;
            }
        } else if (downloadTo.contains("/"))
        {
            if (new File(downloadTo + "/" + name.replaceAll("[^a-zA-Z0-9.-]", "_") + ".cbz").exists())
            {   //return true if issue created
                return true;
            } else
            {   //return false if issue not created
                return false;
            }
        } else
        {   //return false if issue not created
            return false;
        }

    }

//    public static void main(String[] args) throws InterruptedException, MalformedURLException, IOException
//    {
//        GlobalSharedVariables.moveDriverOffScreen = false;
//        startChromeDriver();
//        String links[] =
//        {
//            "http://readcomiconline.to/Comic/Lazarus-2013/HC-1-The-First-Collection?id=2894&readType=1&quality=hq",
//        /**
//         * "http://readcomiconline.to/Comic/Invincible-Iron-Man-2015/Issue-12?id=79499&quality=lq&readType=1"*
//         */
//        };
//        for (int i = 0; i < links.length; i++)
//        {
//            downloadIssue(links[i], "lazarus_hq", "D:\\test\\");
//        }
//        endChromeDriver();
//    }
//
//    public static void downloadNewIssues() throws InterruptedException, IOException
//    {
//
//        startChromeDriver();
//
//        //create list of comic rules found in comics folder
//        File[] comicsList = new File(FileManager.launchPath() + "\\rules\\comics\\").listFiles();
//
//        //loop through list of comic rules
//        for (int i = 0; i < comicsList.length; i++)
//        {
//            SearchForUpdates.updateComicRule(comicsList[i].getPath());
//
//            System.out.println("#########################################################################################################");
//            System.out.println("   SEARCHING FOR NEW ISSUES OF " + comicsList[i].getName() + " TO DOWNLOAD");
//            System.out.println("#########################################################################################################\n");
//
//            //load list of issues
//            List issues = FileManager.readFile(comicsList[i].getAbsolutePath());
//            SendEmailNotification.retrievedComics.add(issues.getItem(0) + "<retIssues></retIssues>");
//            for (int j = 1; j < issues.getItemCount(); j++)
//            {
//                //if issue status is false, download it
//                if (FileManager.returnTag("downloaded", issues.getItem(j)).equals("false"))
//                {
//                    try
//                    {
//                        System.out.println("\n#########################################################################################################");
//                        System.out.println("DOWNLOADING: " + FileManager.returnTag("name", issues.getItem(j)) + "\n");
//                        System.out.println("#########################################################################################################");
//
//                        retryOnFail:
//                        for (int x = 0; x < 5; x++)
//                        {
//                            try
//                            {
//
//                                //download issue from              given link                             use given name                              download to set location
//                                downloadIssue(FileManager.returnTag("link", issues.getItem(j)), FileManager.returnTag("name", issues.getItem(j)), FileManager.returnTag("downloadTo", issues.getItem(0)));
//                                //update download status
//                                issues.replaceItem(FileManager.updateTag("downloaded", issues.getItem(j), "true"), j);
//                                //add to email list to notify
//                                SendEmailNotification.retrievedComics.replaceItem(FileManager.updateTag("retIssues",
//                                        SendEmailNotification.retrievedComics.getItem(i),
//                                        FileManager.returnTag("retIssues", SendEmailNotification.retrievedComics.getItem(i)) + FileManager.returnTag("name", issues.getItem(j)) + "-!SPLIT!-"),
//                                        i); //item to replace
//
//                                //break loop if successful
//                                break retryOnFail;
//
//                            } catch (NullPointerException e)
//                            {
//                                if (i == 4)
//                                {
//                                    System.out.println("#########################################################################################################");
//                                    System.out.println(FileManager.returnTag("name", issues.getItem(j)) + " - error retrieving issue, URL crash, issue skipped on this attempt, error relates to nullpointerexception");
//                                    System.out.println(e);
//                                    System.out.println("#########################################################################################################");
//
//                                }
//                            }
//
//                        }
//                    } catch (MalformedURLException e)
//                    {
//                        System.out.println("#########################################################################################################");
//                        System.out.println(FileManager.returnTag("name", issues.getItem(j)) + " - error retrieving issue, URL crash, issue skipped on this attempt");
//                        System.out.println("#########################################################################################################");
//
//                    }
//                }
//                //print out update to rules
//                PrintWriter out = new PrintWriter(comicsList[i].getAbsolutePath());
//                for (int k = 0; k < issues.getItemCount(); k++)
//                {
//                    out.println(issues.getItem(k));
//                }
//                out.close();
//                //System.out.println(issues.getItem(i));
//
//            }
//
//            System.out.println("\n#########################################################################################################\n");
//
//        }
//
//        endChromeDriver();
////        try
////        {
////            SendEmailNotification.test();
////        } catch (MessagingException ex)
////        {
////            Logger.getLogger(DownloadNewIssues.class.getName()).log(Level.SEVERE, null, ex);
////        }
//    }
//
//    public static void downloadIssue(String linkToIssue, String issueName, String savePath) throws InterruptedException, MalformedURLException, IOException
//    {
//        List text = getLinksToPages(linkToIssue);
//
//        String path;
//        if (savePath.endsWith("/") || savePath.endsWith("\\"))
//        {
//            if (savePath.contains("\\"))
//            {
//                path = savePath + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "\\";
//            } else
//            {
//                path = savePath + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "/";
//            }
//        } else if (savePath.contains("\\"))
//        {
//            path = savePath + "\\" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "\\";
//        } else
//        {
//            path = savePath + "/" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "/";
//        }
//        //make folder to hold pages
//        new File(path).mkdirs();
//        //download pages to created folder
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < text.getItemCount(); i++)
//        {
//            Runnable worker = new MultiThreadedPageDownloader(text.getItem(i), issueName, path, i);
//
//            executor.execute(worker);
//
//
//        }
//
//        executor.shutdown();
//        while (!executor.isTerminated())
//        {
//        }
//        System.out.println("Finished all threads");
//
//        File zip = null;
//
//        if (savePath.endsWith("/") || savePath.endsWith("\\"))
//        {
//
//            zip = new File(savePath + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + ".zip");
//            // path = savePath + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "\\";
//
//        } else if (savePath.contains("\\"))
//        {
//            zip = new File(savePath + "\\" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + ".zip");
//
//            //   path = savePath + "\\" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "\\";
//        } else
//        {
//            zip = new File(savePath + "/" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + ".zip");
//
//            // path = savePath + "/" + issueName.replaceAll("[^a-zA-Z0-9.-]", "_") + "/";
//        }
//
//        System.out.println("\nCOMPRESSING IMAGES TO ZIP FILE..........");
//        File folder = new File(path);
//        ZipUtil.pack(folder, zip);
//        //rename zip file to cbz to make comic file
//        System.out.println("\nCONVERTING ZIP FILE TO CBZ COMIC FILE..........");
//        zip.renameTo(new File(zip.getPath().replace(".zip", ".cbz")));
//        //delete the folder that has the images after the cbz file is made
//        delete(folder);
//    }
//
//    private static List getLinksToPages(String URL) throws InterruptedException
//    {
//        //list to store found links into
//        List imageURLS = new List();
//        //set driver
////        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
////        //set options to use portable chrome to avoid messing with main browser
////        ChromeOptions options = new ChromeOptions();
////        options.setBinary(pathToChromePortable);
////        //start driver
////        ChromeDriver driver = new ChromeDriver();
//        //get url
//        try
//        {
//
//            Thread t = new Thread(new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    int i = 0;
//                    int checks = 0;
//                     Actions a = new Actions(driver);
//                    while (true)
//                    {
//                        i+=1;
//                        
//                       System.out.println("scrolling " + i);
//                       
//                        a.sendKeys("SPACE");
//                       
//                        try{Thread.sleep(3000);}catch(Exception e){}
//                        
//
//                    }
//                }
//            });
//
//            t.start();
//
//            driver.get(URL);
//
//        } catch (Exception e)
//        {
//            System.out.println(e);
//        }
//        //wait for pages 5 second load, 
//        //get page html
//        Thread.sleep(7000);
//        String html = driver.getPageSource();
//        //end driver
//        //driver.close();
//        //prep to parse code
//        Document doc = Jsoup.parse(html);
//        Element main = doc.select("div#divImage").first(); //narrow code search down
//        // System.out.println(main);
//        Elements pages = main.select("img");
//        for (Element page : pages)
//        {
//            // System.out.println(page.attr("src"));
//            imageURLS.add(page.attr("src"));
//        }
//
//        return imageURLS;
//    }

}
