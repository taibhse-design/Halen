/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Comics;

import Halen3.IO.GlobalSharedVariables;
import static Halen3.IO.GlobalSharedVariables.driver;
import java.awt.List;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.SessionNotFoundException;

/**
 *
 * @author brenn
 */
public class Test
{

//    static boolean testing = false;
//    static volatile boolean repeat = true;
//    static volatile boolean runTimeCheck = true;
//
////    public static void downloadPage(String pageURL, String name, String path, int pageNumber) throws MalformedURLException, IOException
////    {
////        URL url = new URL(pageURL);
////        URLConnection conn = url.openConnection();
////        String type = conn.getContentType();
////        System.out.println("RETRIEVING: " + name.replaceAll("[^a-zA-Z0-9.-]", "_") + "_" + String.format("%03d", pageNumber) + "." + type.replace("image/", ""));
////        InputStream is = url.openStream();
////        OutputStream os = new FileOutputStream(path + String.format("%03d", pageNumber) + "." + type.replace("image/", ""));
////        byte[] b = new byte[2048];
////        int length;
////        while ((length = is.read(b)) != -1)
////        {
////            os.write(b, 0, length);
////        }
////        is.close();
////        os.close();
////    }
//    private static void downloadIssuePagesConvertToCBZ(String[] pages, String issueName, String savePath) throws InterruptedException, MalformedURLException, IOException
//    {
//        //  List text = getLinksToPages(linkToIssue);
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
//        for (int i = 0; i < pages.length; i++)
//        {
//            Runnable worker = new MultiThreadedPageDownloader(pages[i], issueName, path, i);
//
//            executor.execute(worker);
//        }
//
//        executor.shutdown();
//        while (!executor.isTerminated())
//        {
//        }
//        System.out.println("Finished all threads");
//
//        File zip;
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
//    private static void timeOutControl(long startTime)
//    {
//        Thread t = new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//
//                while (runTimeCheck)
//                {
//                    long runningTime = System.currentTimeMillis();
//                    long totalTime = runningTime - startTime;
//                    //   System.out.println("Running Time: " + totalTime);
//                    if (totalTime > 600000)
//                    {
//                        repeat = false;
//                        runTimeCheck = false;
//                        System.out.println("TIMEOUT REACHED AT 10 MINUTES!!!!!");
//
//                    }
//
//                }
//            }
//        });
//        t.start();
//    }
//
//    public static void main(String args[]) throws InterruptedException, AWTException, IOException
//    {
//
//    }
//
//    public static void downloadNewIssues()
//    {
//        GlobalSharedVariables.moveDriverOffScreen = false;
//        //start chrome driver
//        GlobalSharedVariables.startChromeDriver();
//        //get list of comic rules to work with
//        File[] comicsList = new File(FileManager.launchPath() + "\\rules\\comics\\").listFiles();
//
//        //loop through list of comic rules
//        for (int i = 0; i < comicsList.length; i++)
//        {
//            try
//            {
//
//                //search for updates to rule prior to downloading new issues
//                SearchForUpdates.updateComicRule(comicsList[i].getPath());
//
//                System.out.println("#########################################################################################################");
//                System.out.println("   SEARCHING FOR NEW ISSUES OF " + comicsList[i].getName() + " TO DOWNLOAD");
//                System.out.println("#########################################################################################################\n");
//
//                //load list of issues
//                List issues = FileManager.readFile(comicsList[i].getAbsolutePath());
//                //add comic data to email list, if issues retrieved, it will email notification
//                SendEmailNotification.retrievedComics.add(issues.getItem(0) + "<retIssues></retIssues>");
//                //loop through comics list of issues
//                for (int j = 1; j < issues.getItemCount(); j++) //start at 1 since 0 line is comic data
//                {
//                    //if issue status is false, download it
//                    if (FileManager.returnTag("downloaded", issues.getItem(j)).equals("false"))
//                    {
//                        System.out.println("\n#########################################################################################################");
//                        System.out.println("DOWNLOADING: " + FileManager.returnTag("name", issues.getItem(j)) + "\n");
//                        System.out.println("#########################################################################################################");
//
//                        //#############################################################################
//                        //download issue, returns true or false if issue has been downloaded
//                        boolean downloaded = downloadIssue(FileManager.returnTag("link", issues.getItem(j)), FileManager.returnTag("name", issues.getItem(j)), FileManager.returnTag("downloadTo", issues.getItem(0)));
//                        //update this issue with download status,
//                        issues.replaceItem(
//                                FileManager.updateTag(
//                                        "downloaded", //update this tag
//                                        issues.getItem(j), //get line data to update
//                                        String.valueOf(downloaded)), //use value from attempt to download
//                                j); //issue number to update
//
//                        //#############################################################################
//                        //add issue to email list to notify if it has been downloaded
//                        if (downloaded == true)
//                        {
//                            SendEmailNotification.retrievedComics.replaceItem(FileManager.updateTag("retIssues",
//                                    SendEmailNotification.retrievedComics.getItem(i),
//                                    FileManager.returnTag("retIssues", SendEmailNotification.retrievedComics.getItem(i)) + FileManager.returnTag("name", issues.getItem(j)) + "-!SPLIT!-"),
//                                    i); //item to replace
//                        }
//
//                    }
//
//                    //print update to file if not testing
//                    if (testing == false)
//                    {
//                        //print out update to rules
//                        PrintWriter out = new PrintWriter(comicsList[i].getAbsolutePath());
//                        for (int k = 0; k < issues.getItemCount(); k++)
//                        {
//                            out.println(issues.getItem(k));
//                        }
//                        out.close();
//                    }
//                }
//
//            } catch (InterruptedException | IOException e)
//            {
//                System.out.println(e);
//            }
//        }
//        //end chrome driver
//        GlobalSharedVariables.endChromeDriver();
//        // SendEmailNotification.sendEmailNotice(SendEmailNotification.createUpdateListMessage());
//
//    }
//
//    private static boolean downloadIssue(String url, String name, String downloadTo) throws InterruptedException, IOException
//    {
//
//        try
//        {
//            url = url.replace("&readType=1", "");
//            url = url.replace("&readType=0", "");
//            url = url.replace("&&quality=hq", "");
//            url = url.replace("&&quality=lq", "");
//
//            //  String url = "http://readcomiconline.to/Comic/Lazarus-2013/HC-1-The-First-Collection?id=2894";
//            //start driver and set to be off screen or not
//            // GlobalSharedVariables.moveDriverOffScreen = false;
//            // GlobalSharedVariables.startChromeDriver();
//            driver.manage().timeouts().implicitlyWait(9, TimeUnit.MINUTES);
//            System.out.println("TRYING " + url);
//            //used to test running time and kill if too long
//            long startTime = System.currentTimeMillis();
//            long runningTime = 0;
//            //get expected page count
//
////            driver.get(url + "&readType=0&quality=hq");
////
////             WebDriverWait wait = new WebDriverWait(driver, 30);
////             wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectPage")));
//            // try{Thread.sleep(7000);} catch (InterruptedException ex){Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);}
////            driver.findElement(By.id("selectReadType")).click();
////            Select select = new Select(driver.findElement(By.id("selectReadType")));
////            //    select.deselectAll();
////            select.selectByValue("0");//.selectByVisibleText("Value1");
////            try{Thread.sleep(7000);} catch (InterruptedException ex){Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);}
////        
////            String html = GlobalSharedVariables.driver.getPageSource();
////            Document doc = Jsoup.parse(html);
////            Element selectPage = doc.getElementById("selectPage");
////            int pageCount = selectPage.select("option").size();
//            String pageLinks[] = null;
////
////            System.out.println("EXPECTED PAGE COUNT: " + pageCount);
//
////            try//this one
////            {
//            //try retrieving pages
//            driver.get(url + "&readType=1&quality=hq");
//
//            //select = new Select(driver.findElement(By.id("selectReadType")));
//            //select.selectByValue("1");//.selectByVisibleText("Value1");
//            //if runs longer than 10 minutes, kills loop, this method runs on its own thread
//            timeOutControl(startTime);
//
//            //loop check code until page fully loads
//            opLoop:
//            while (repeat)
//            {
//
//                //loop += 1;
//                //System.out.println(loop);
//                //catch exceptions until page is fully loaded
//                try
//                {
//                    //get page html
//                    String html = driver.getPageSource();
//
//                    //prep to parse code
//                    Document doc = Jsoup.parse(html);
//                    //get main div holding collection of images
//                    Element main = doc.select("div#divImage").first(); //narrow code search down
//                    // parse for all images
//                    Elements pages = main.select("img");
//                    pageLinks = new String[pages.size()];
//                    //loop to get image source addresses (only loop if pages equal found page count)
//                    // if (pages.size() == pageCount)
//                    {
//                        //loop to get image source addresses
//                        pageSearch:
//                        for (int i = 0; i < pages.size(); i++)
//                        {
//                            //System.out.println(pages.get(i).attr("src"));
//
//                            // System.out.println("PAGE: " + pages.get(i).attr("src").trim());
//                            //blank list if null to start over
//                            if (pages.get(i).attr("src").trim().equals(""))
//                            {
//                                pageLinks = new String[pages.size()];
//                                repeat = true;
//                                runTimeCheck = true;
//                                break pageSearch;
//
//                            } else//add to list if not null
//                            {
//                                pageLinks[i] = pages.get(i).attr("src");
//                                repeat = false;
//                                runTimeCheck = false;
//                            }
//                        }
//                    }
//                } catch (NullPointerException | IllegalArgumentException e)
//                {
//                    // System.out.println(e);
//                }
//            }
//            //reset for next rule to be able to run successfully
//            repeat = true;
//
//            boolean download = true;
//            for (int i = 0; i < pageLinks.length; i++)
//            {
//                //page downloads carried out here
//                //  System.out.println(link);
//                if (pageLinks[i].equals(""))
//                {
//                    download = false;
//                    break;
//                }
//                // downloadPage(pageLinks[i], "test", "D:\\test\\", i);
//            }
//            if (download = true)
//            {
//                downloadIssuePagesConvertToCBZ(pageLinks, name, downloadTo);
//            }
//
////            } catch (Exception e)
////            {
////                System.out.println("error" + e.getLocalizedMessage());
////            }
//        } catch (TimeoutException e)
//        {
//            System.out.println("TIMEOUT EXCEPTION!! SKIPPING " + name + " FOR NOW");
//        }
//        //end chrome driver
//        //  GlobalSharedVariables.endChromeDriver();
//
//        //create cbz path of issue to check if it exists
//        if (downloadTo.endsWith("/") || downloadTo.endsWith("\\"))
//        {
//
//            if (new File(downloadTo + name.replaceAll("[^a-zA-Z0-9.-]", "_") + ".cbz").exists())
//            {   //return true if issue created
//                return true;
//            } else
//            {   //return false if issue not created
//                return false;
//            }
//        } else if (downloadTo.contains("\\"))
//        {
//            if (new File(downloadTo + "\\" + name.replaceAll("[^a-zA-Z0-9.-]", "_") + ".cbz").exists())
//            {   //return true if issue created
//                return true;
//            } else
//            {   //return false if issue not created
//                return false;
//            }
//        } else if (downloadTo.contains("/"))
//        {
//            if (new File(downloadTo + "/" + name.replaceAll("[^a-zA-Z0-9.-]", "_") + ".cbz").exists())
//            {   //return true if issue created
//                return true;
//            } else
//            {   //return false if issue not created
//                return false;
//            }
//        } else
//        {   //return false if issue not created
//            return false;
//        }
//
//    }
//    public static void pageByPage() throws InterruptedException
//    {
//        GlobalSharedVariables.moveDriverOffScreen = false;
//        GlobalSharedVariables.startChromeDriver();
//        GlobalSharedVariables.driver.get("http://readcomiconline.to/Comic/Lazarus-2013/HC-1-The-First-Collection?id=2894");
//        Thread.sleep(7000);
//        String html = GlobalSharedVariables.driver.getPageSource();
//
//        if (!html.contains("Are you human?"))
//        {
//            Document doc = Jsoup.parse(html);
//            Element selectPage = doc.getElementById("selectPage");
//            int count = selectPage.select("option").size();
//
//            //  boolean run = true;
//            // int i = 1;
//            for (int i = 1; i <= count; i++)
//            {
//                //  try
//                {
//
//                    retry:
//                    for (int x = 0; x < 5; x++)
//                    {
//                        if (i % 10 == 0)
//                        {
//                            System.out.println("PAUSING FOR 10 SECONDS TO COUNTER ANTI BOT");
//                            Thread.sleep(10, 000);
//                        }
//                        try
//                        {
//                            GlobalSharedVariables.driver.get("http://readcomiconline.to/Comic/Lazarus-2013/HC-1-The-First-Collection?id=2894&quality=lq#" + i);
//                            //Thread.sleep(100);
//                            GlobalSharedVariables.driver.get("http://readcomiconline.to/Comic/Lazarus-2013/HC-1-The-First-Collection?id=2894&quality=lq#" + i);
//                            Thread.sleep(7000);
////                            break retry;
////                        } catch (Exception e)
////                        {
////                            System.out.println(e + "\n\nerror getting page, retrying, this is attempt" + x);
////                        }
////                    }
////                    html = GlobalSharedVariables.driver.getPageSource();
////
////                    if (!html.contains("Are you human?"))
////                    {
////                        doc = Jsoup.parse(html);
////                        Element img = doc.getElementById("imgCurrent");
////                        System.out.println(img.attr("src"));
////
////                        //downloadPage(img.attr("src"), "test", "D:\\test\\", i);
////                    } else
////                    {
////                        System.out.println("ANTI BOT ACTIVE");
////                    }
////                    // i+=1;
////                } //catch (IOException e)
////                {
////                    //   System.out.println(e);
////                    //  run = false;
////                }
////            }
////
////        } else
////        {
////            System.out.println("ANTIBOT FEATURE ACTIVE");
////        }
////
////        Thread.sleep(30000);
////
////        GlobalSharedVariables.endChromeDriver();
////    }
//    private static void timeOutControl(final long startTime)
//    {
//        Thread t = new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//
//                while (runTimeCheck)
//                {
//                    long runningTime = System.currentTimeMillis();
//                    long totalTime = runningTime - startTime;
//                    //   System.out.println("Running Time: " + totalTime);
//                    if (totalTime > 600000)
//                    {
//                        repeat = false;
//                        runTimeCheck = false;
//                        ColorCmd.println("TIMEOUT REACHED AT 10 MINUTES!!!!!", fgRedBgWhite);
//
//                    }
//
//                }
//            }
//        });
//        t.start();
//    }
//
//    public static void main(String args[])
//    {
//        
//        GlobalSharedVariables.startChromeDriver();
//        
//        String url = "http://kissmanga.com/Manga/Black-Lagoon/--089?id=371280";
//         try
//        {
//            url = url.replace("&readType=1", "");
//            url = url.replace("&readType=0", "");
//            url = url.replace("&&quality=hq", "");
//            url = url.replace("&&quality=lq", "");
//
//            //  String url = "http://readcomiconline.to/Comic/Lazarus-2013/HC-1-The-First-Collection?id=2894";
//            //start driver and set to be off screen or not
//            // GlobalSharedVariables.moveDriverOffScreen = false;
//            // GlobalSharedVariables.startChromeDriver();
//            driver.manage().timeouts().implicitlyWait(9, TimeUnit.MINUTES);
//            ColorCmd.println("TRYING " + url, fgYellowBgWhite);
//            //used to test running time and kill if too long
//            long startTime = System.currentTimeMillis();
//            long runningTime = 0;
//            //get expected page count
//
////            driver.get(url + "&readType=0&quality=hq");
////
////             WebDriverWait wait = new WebDriverWait(driver, 30);
////             wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectPage")));
//            // try{Thread.sleep(7000);} catch (InterruptedException ex){Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);}
////            driver.findElement(By.id("selectReadType")).click();
////            Select select = new Select(driver.findElement(By.id("selectReadType")));
////            //    select.deselectAll();
////            select.selectByValue("0");//.selectByVisibleText("Value1");
////            try{Thread.sleep(7000);} catch (InterruptedException ex){Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);}
////        
////            String html = GlobalSharedVariables.driver.getPageSource();
////            Document doc = Jsoup.parse(html);
////            Element selectPage = doc.getElementById("selectPage");
////            int pageCount = selectPage.select("option").size();
//            String pageLinks[] = null;
////
////            System.out.println("EXPECTED PAGE COUNT: " + pageCount);
//
////            try//this one
////            {
//            //try retrieving pages
//            driver.get(url + "&readType=1&quality=hq");
//
//            //select = new Select(driver.findElement(By.id("selectReadType")));
//            //select.selectByValue("1");//.selectByVisibleText("Value1");
//            //if runs longer than 10 minutes, kills loop, this method runs on its own thread
//            timeOutControl(startTime);
//
//            int loop = 0;
//            //loop check code until page fully loads
//            opLoop:
//            while (repeat)
//            {
//
//                loop += 1;
//                System.out.println(loop);
//                //catch exceptions until page is fully loaded
//                try
//                {
//                    if(url.toLowerCase().contains("kissmanga"))
//                    {
//                    Select dropdown = new Select(driver.findElement(By.id("selectReadType")));
//                    dropdown.selectByVisibleText("All pages");
//                    }
//                    
//                    //get page html
//                    String html = driver.getPageSource();
//
//                    
//                    //prep to parse code
//                    Document doc = Jsoup.parse(html);
//                    //get main div holding collection of images
//                    Element main = doc.select("div#divImage").first(); //narrow code search down
//                    // parse for all images
//                    Elements pages = main.select("img");
//                    pageLinks = new String[pages.size()];
//                    //loop to get image source addresses (only loop if pages equal found page count)
//                    // if (pages.size() == pageCount)
//                    {
//                        //loop to get image source addresses
//                        pageSearch:
//                        for (int i = 0; i < pages.size(); i++)
//                        {
//                            //System.out.println(pages.get(i).attr("src"));
//
//                            // System.out.println("PAGE: " + pages.get(i).attr("src").trim());
//                            //blank list if null to start over
//                            if (pages.get(i).attr("src").trim().equals(""))
//                            {
//                                pageLinks = new String[pages.size()];
//                                repeat = true;
//                                runTimeCheck = true;
//                                break pageSearch;
//
//                            } else//add to list if not null
//                            {
//                                pageLinks[i] = pages.get(i).attr("src");
//                                repeat = false;
//                                runTimeCheck = false;
//                            }
//                        }
//                    }
//                } catch (NullPointerException | IllegalArgumentException e)
//                {
//                    // System.out.println(e);
//                }
//            }
//            //reset for next rule to be able to run successfully
//            repeat = true;
//
//            boolean download = true;
//            for (int i = 0; i < pageLinks.length; i++)
//            {
//                //page downloads carried out here
//                //  System.out.println(link);
//                if (pageLinks[i].equals(""))
//                {
//                    download = false;
//                    break;
//                }
//                // downloadPage(pageLinks[i], "test", "D:\\test\\", i);
//            }
//            if (download = true)
//            {
//              //  downloadIssuePagesConvertToCBZ(pageLinks, name, downloadTo);
//            }
//
////            } catch (Exception e)
////            {
////                System.out.println("error" + e.getLocalizedMessage());
////            }
//        } catch (TimeoutException e)
//        {
//            ColorCmd.println("TIMEOUT EXCEPTION!! SKIPPING " + /**name**/  " FOR NOW", fgRedBgWhite);
//        }
//        //end chrome driver
//        //  GlobalSharedVariables.endChromeDriver();
//
//        //create cbz path of issue to check if it exists
////        if (downloadTo.endsWith("/") || downloadTo.endsWith("\\"))
////        {
////
////            if (new File(downloadTo + name.replaceAll("[^a-zA-Z0-9.-]", "_") + ".cbz").exists())
////            {   //return true if issue created
////                return true;
////            } else
////            {   //return false if issue not created
////                return false;
////            }
////        } else if (downloadTo.contains("\\"))
////        {
////            if (new File(downloadTo + "\\" + name.replaceAll("[^a-zA-Z0-9.-]", "_") + ".cbz").exists())
////            {   //return true if issue created
////                return true;
////            } else
////            {   //return false if issue not created
////                return false;
////            }
////        } else if (downloadTo.contains("/"))
////        {
////            if (new File(downloadTo + "/" + name.replaceAll("[^a-zA-Z0-9.-]", "_") + ".cbz").exists())
////            {   //return true if issue created
////                return true;
////            } else
////            {   //return false if issue not created
////                return false;
////            }
////        } else
////        {   //return false if issue not created
////            return false;
////        }
//    }
    public static void checkPage(String check)
    {
        Thread t = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                int i = 0;
                String html = "";
                boolean run = true;
                while (run == true)
                {

                    try
                    {

                        // Thread.sleep(500);
                        System.out.println(i += 1);
                        html = GlobalSharedVariables.driver.getPageSource();
                        if (html.contains(check))
                        {
                            System.out.println("true");
                        } else
                        {
                            System.out.println("false");
                        }

                    } catch (SessionNotFoundException e)
                    {
                        System.out.println("ERROR");
                    }

                }

            }
        });

        t.start();
    }

    public static void main(String args[])
    {

        String path = "D:\\Downloads\\";

        String urls[] =
        {
            "http://readcomiconline.to/Comic/Invincible-Iron-Man-2015/Issue-13?id=86837&readType=1&quality=hq",
            "http://readcomiconline.to/Comic/Lazarus-Sourcebook/Vol-1?id=111889",
            "http://readcomiconline.to/Comic/My-Little-Pony-Friendship-is-Magic/Issue-46?id=93286",
            "http://readcomiconline.to/Comic/My-Little-Pony-Friendship-is-Magic/Issue-51?id=107911"
        };

        for (int x = 0; x < 100; x++)
        {
            for (int i = 0; i < urls.length; i++)
            {

                getComic(urls[i]);
                System.out.println("\n");

            }

        }

    }

    public static void getComic(String url)
    {
        try
        {
            int timeWait = 20;

            GlobalSharedVariables.startChromeDriver();

           // GlobalSharedVariables.driver.manage().timeouts().pageLoadTimeout(timeWait, TimeUnit.SECONDS);
            GlobalSharedVariables.driver.manage().timeouts().implicitlyWait(timeWait, TimeUnit.SECONDS);

            System.out.println("LOADING URL...");
            GlobalSharedVariables.driver.navigate().to(url);

            try
            {
                System.out.println("Default page timeout failed....forcing backup timeout...");
                Thread.sleep(timeWait * 1000);
                throw new TimeoutException("FUCKETY");
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (TimeoutException e)
        {
            System.out.println("DONE LOADING....SCRAPING IMAGES IF ALL PRESENT...");

            //   if (!GlobalSharedVariables.driver.getPageSource().contains("<img src=\"/Content/images/loading.gif\" data-pin-nopin=\"true\">") & !GlobalSharedVariables.driver.getPageSource().contains("Please wait 5 seconds..."))
            {
                System.out.println("Loading done, all images scrapable....");

                String parentHandle = driver.getWindowHandle(); // get the current window handle
                System.out.println("Current TAB: " + parentHandle);
                String html = driver.switchTo().window(parentHandle).getPageSource();
                //Document doc = Jsoup.parse(GlobalSharedVariables.driver.getPageSource());
                Document doc = Jsoup.parse(html);
                Elements mlinks = doc.select("img");

                List pages = new List();

                for (int i = 0; i < mlinks.size(); i++)
                {
                    if (mlinks.get(i).attr("src").contains("blogspot"))
                    {
                        System.out.println(mlinks.get(i).attr("src"));
                        pages.add(mlinks.get(i).attr("src"));
                    }
                }

            }// else
            {
                System.out.println("Loading failed.....adding additional 10 seconds for page load.");
                //  timeWait += 10; //add another 10 seconds for wait
            }
        }

    }

    public static void getComicRed(String url)
    {
        GlobalSharedVariables.startChromeDriver();

        //   String check = "<div id=\"imgLoader\" style=\"text-align: center;\"><img src=\"/Content/images/loading.gif\" data-pin-nopin=\"true\"></div>";
        boolean retry = true;
        int attempts = 0;
        int timeWait = 10; //wait in seconds for page to load and check code

        retryLoop:
        while (retry == true)
        {

            // checkPage(check);
            GlobalSharedVariables.driver.manage().timeouts().pageLoadTimeout(timeWait, TimeUnit.SECONDS);
            GlobalSharedVariables.driver.manage().timeouts().implicitlyWait(timeWait, TimeUnit.SECONDS);

            try
            {

                if (attempts < 1)//load url if first loop run
                {
                    System.out.println("LOADING URL...");
                    GlobalSharedVariables.driver.navigate().to(url);

                    try
                    {
                        Thread.sleep((timeWait / 2));
                    } catch (InterruptedException ex)
                    {
                        Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (GlobalSharedVariables.driver.getPageSource().contains("Please wait 5 seconds"))
                    {
                        System.out.println("fuck...");
                        try
                        {
                            Thread.sleep(10000);
                        } catch (InterruptedException ex)
                        {
                            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        throw new TimeoutException("FUCKETY");
                    } else
                    {
                        System.out.println("URL DONE...");
                        throw new TimeoutException("FUCKETY");
                    }

                } else if (attempts >= 5) //max number of retries allowed
                {
                    System.out.println("ENDING RETRIES...");
                    retry = false;

                } else//refresh page if not first run and less than 5 runs done
                {
                    System.out.println("REFRESHING PAGE...");
                    GlobalSharedVariables.driver.navigate().refresh();

                    try
                    {
                        Thread.sleep(timeWait / 2);
                    } catch (InterruptedException ex)
                    {
                        Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (TimeoutException e)
            {
                System.out.println("Page Tiemout hit - on attempt " + attempts);

                try
                {
                    Thread.sleep(timeWait);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }

                //boolean check = true;
                int checkAttempts = 0;
                while (checkAttempts < 10)
                {
                    try
                    {
                        if (!GlobalSharedVariables.driver.getPageSource().contains("<img src=\"/Content/images/loading.gif\" data-pin-nopin=\"true\">") & !GlobalSharedVariables.driver.getPageSource().contains("Please wait 5 seconds..."))
                        {
                            System.out.println("Loading done, all images scrapable....");

                            Document doc = Jsoup.parse(GlobalSharedVariables.driver.getPageSource());
                            Elements mlinks = doc.select("img");

                            List pages = new List();

                            for (int i = 0; i < mlinks.size(); i++)
                            {
                                if (mlinks.get(i).attr("src").contains("blogspot"))
                                {
                                    System.out.println(mlinks.get(i).attr("src"));
                                    pages.add(mlinks.get(i).attr("src"));
                                }
                            }

                            break retryLoop;

                        } else
                        {
                            System.out.println("Loading failed.....adding additional 10 seconds for page load.");
                            timeWait += 10; //add another 10 seconds for wait
                        }

                    } catch (TimeoutException ex)
                    {
                        System.out.println(checkAttempts + ": Timeout exception while checking html....retrying...");

                        GlobalSharedVariables.endChromeDriver();

                        getComic(url);

                    }

                    checkAttempts += 1;

                }

            }

            attempts += 1;

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("return window.stop");

        }

        GlobalSharedVariables.endChromeDriver();
    }
}
