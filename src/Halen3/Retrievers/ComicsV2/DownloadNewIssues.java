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
import Halen3.EmailNotifier.SendEmailNotification;
import Halen3.IO.FileManager;
import static Halen3.IO.FileManager.delete;
import static Halen3.IO.GlobalSharedVariables.testing;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.awt.AWTException;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
    private static void downloadIssuePagesConvertToCBZ(String[] pages, String issueName, String savePath) throws InterruptedException, MalformedURLException
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
        ColorCmd.println("", fgWhiteBgWhite);
        ColorCmd.println("Finished all threads", fgWhiteBgYellow);
        ColorCmd.println("", fgWhiteBgWhite);

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

        ColorCmd.println("COMPRESSING IMAGES TO ZIP FILE..........", fgYellowBgWhite);
        File folder = new File(path);
        ZipUtil.pack(folder, zip);
        //rename zip file to cbz to make comic file
        ColorCmd.println("CONVERTING ZIP FILE TO CBZ COMIC FILE..........", fgYellowBgWhite);
        ColorCmd.println("", fgYellowBgWhite);
        zip.renameTo(new File(zip.getPath().replace(".zip", ".cbz")));
        //delete the folder that has the images after the cbz file is made
        delete(folder);
    }

    public static void main(String args[]) throws InterruptedException, AWTException, IOException
    {
        testing = "false";
        downloadNewIssues();
    }

    public static void downloadNewIssues()
    {
        //get list of comic rules to work with
        File[] comicsList = new File(FileManager.launchPath() + "\\rules\\comics\\").listFiles();

        //loop through list of comic rules
        for (int i = 0; i < comicsList.length; i++)
        {
            

            try{
                //search for updates to rule prior to downloading new issues
                SearchForUpdates.updateComicRule(comicsList[i].getPath());
            }catch(InterruptedException | IOException e)
            {
                  ColorCmd.println("Error getting updates for rule " + comicsList[i].getName() + ".....", fgRedBgWhite);
            }

                ColorCmd.println("", fgWhiteBgYellow);
                ColorCmd.printlnCenter("SEARCHING FOR NEW ISSUES OF " + comicsList[i].getName() + " TO DOWNLOAD", fgWhiteBgYellow);
                ColorCmd.println("", fgWhiteBgYellow);
                ColorCmd.println("", fgWhiteBgWhite);
                
         
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
                            // System.out.println("\n#########################################################################################################");
                            ColorCmd.println("DOWNLOADING: " + FileManager.returnTag("name", issues.getItem(j)), fgYellowBgWhite);
                           // System.out.println("#########################################################################################################");

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
                    if (testing.equals("false")) //update rules results only if not testing
                    {
                        try{
                        //print out update to rules
                        PrintWriter out = new PrintWriter(comicsList[i].getAbsolutePath());
                        for (int k = 0; k < issues.getItemCount(); k++)
                        {
                            out.println(issues.getItem(k));
                        }
                        out.close();
                        
                        }catch(FileNotFoundException e)
                        {
                             ColorCmd.println("Error Saving out " + comicsList[i].getAbsolutePath() + ".....", fgRedBgWhite);
                             ColorCmd.println(e.getMessage(), fgRedBgWhite);
               
                        }
                    }
                }

            
        }
        // SendEmailNotification.sendEmailNotice(SendEmailNotification.createUpdateListMessage());

    }

    private static boolean downloadIssue(String url, String name, String downloadTo)
    {
      
        try
        {

            ColorCmd.println("TRYING " + url, fgYellowBgWhite);

            WebClient webClient = new WebClient(BrowserVersion.CHROME);

            webClient.getOptions().setThrowExceptionOnScriptError(false);
            //disable javascript to speed up site loading
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setRedirectEnabled(true); //was false
            webClient.getOptions().setUseInsecureSSL(true);
            java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

            webClient.waitForBackgroundJavaScript(10000);
            HtmlPage page;
            String html;

            page = webClient.getPage(url);  //was XmlPage
            html = page.asXml();

            Document doc = Jsoup.parse(html);

            String pageLinks[] = new String[(doc.select("ul.dropdown-menu.inner.selectpicker").select("li").size()/2)];

            System.out.println(doc.select("img.scan-page").attr("src"));
            String pageUrl = doc.select("img.scan-page").attr("src").substring(0, doc.select("img.scan-page").attr("src").lastIndexOf("/")) + "/";

          //  System.out.println(pageUrl + "  " + pageLinks.length);
           // System.exit(0);
            for (int i = 0; i < pageLinks.length; i++)
            {
                if (i + 1 < 10)
                {
                    pageLinks[i] = pageUrl + "0" + (i + 1) + ".jpg";
                } else
                {
                    pageLinks[i] = pageUrl + (i + 1) + ".jpg";
                }
                
                  ColorCmd.println("Page " + (i+1) + ": " + pageLinks[i], fgYellowBgWhite);
                          
            }
            
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
                try
                {
                downloadIssuePagesConvertToCBZ(pageLinks, name, downloadTo);
                }catch(MalformedURLException | InterruptedException e)
                {
                    
                } 
            }
            



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
        
        }catch(Exception e)
                {
                
                     ColorCmd.println("FAILED " + url, fgRedBgWhite);
                      ColorCmd.println("error preventing downloading this issue, aborting for future attempt... ", fgRedBgWhite);
                       ColorCmd.println(e.getMessage(), fgRedBgWhite);
                       System.out.println(e);
                    return false;
                }

    }

}
