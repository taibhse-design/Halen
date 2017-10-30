/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.ComicsV2;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import static Halen3.CommandLine.ColorCmd.fgYellowBgWhite;
import Halen3.IO.FileManager;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author brenn
 */
public class CreateComicRule
{

    public static void main(String args[]) throws InterruptedException, FileNotFoundException, IOException
    {
        //  System.out.println(System.getProperty("user.home") +"\\Downloads");
        String text = "new! name";

        saveNewSeries("http://readcomics.website/comic/think-tank-animal-2017", "think tank test", "C:\\Users\\TAIBHSE\\Projects\\NetBeansProjects\\Halen\\build\\rules\\comics\\think tank animal");
    }

    public static void saveNewSeries(String url, String name, String downloadTo) throws InterruptedException, IOException
    {

        System.out.println("#########################################################################################################");
        System.out.println("   CREATING NEW COMIC RULE - " + url);
        System.out.println("#########################################################################################################\n");
        //only run if provided url is readcomiconline or kissmanga
        if (url.toLowerCase().contains("http://readcomics.website"))
        {

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
                        if (!new File(downloadTo).isDirectory() || !new File(downloadTo).exists())
                        {
                            downloadTo = System.getProperty("user.home") + "\\Downloads";
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
        String seriesTitle, imageURL = null, localImagePath = null,  status = null, summary = null;

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

        page = webClient.getPage(URL);  //was XmlPage
        html = page.asXml();

        //prep to parse code
        Document doc = Jsoup.parse(html);
        //create list to store issues in
        List issues = new List();

        //get comic title
        Element title = doc.select("h2").first();
        ColorCmd.println("Title: " + title.text(), fgYellowBgWhite);
        seriesTitle = title.text();
        //get comic preview image
        Element image = doc.select("img.img-responsive").first();
        ColorCmd.println("Image URL: " + image.attr("src"), fgYellowBgWhite);
        imageURL = image.attr("src");

        //download above image as graphic for preview
        HtmlImage imagedownloader = page.<HtmlImage>getFirstByXPath("//img[@src='" + image.attr("src") + "']");
        File imageFile = new File(FileManager.launchPath() + "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9. -]", "_") + ".jpg");
        imagedownloader.saveAs(imageFile);
        localImagePath = "\\graphics\\comic-book-covers\\" + seriesTitle.replaceAll("[^a-zA-Z0-9. -]", "_") + ".jpg";

        //get comic summary
        Element sum = doc.select("div.manga").select("p").first();
        ColorCmd.println("Summary: " + sum.text(), fgYellowBgWhite);
        summary = sum.text();
        //get comic status
        Element stat = doc.select("span.label").first();
        ColorCmd.println("Status: " + stat.text(), fgYellowBgWhite);
        status = stat.text();

        
        ColorCmd.println("", fgYellowBgWhite);
        
        Elements links = doc.select("h5.chapter-title-rtl");
        
        for (Element link : links)
        {
            ColorCmd.println(link.text() + ": " + link.select("a").attr("href"), fgYellowBgWhite);
            issues.add("<name>" + link.text() + "</name><link>" + link.select("a").attr("href") + "</link><downloaded>false</downloaded>");
        }


        List data = new List();
        data.add("<title>" + seriesTitle + "</title><url>" + URL + "</url><localImagePath>" + localImagePath + "</localImagePath><imageURL>" + imageURL + "</imageURL><status>" + status + "</status><summary>" + summary + "</summary>");

        for (int i = 0; i < issues.getItemCount(); i++)
        {
            data.add(issues.getItem(i));
        }


        return data;
    }
}
