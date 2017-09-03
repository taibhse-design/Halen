/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Manga;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgWhite;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgYellow;
import static Halen3.CommandLine.ColorCmd.fgYellowBgWhite;
import Halen3.IO.FileManager;
import static Halen3.Retrievers.Manga.CreateMangaRule.getDetails;
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
        updateAllMangaRules();
    }

    public static void updateAllMangaRules() throws InterruptedException, IOException
    {

        //create list of manga rules found in manga folder
        File[] mangaList = new File(FileManager.launchPath() + "\\rules\\manga\\").listFiles();

        //loop through list of manga rules
        for (int i = 0; i < mangaList.length; i++)
        {
            updateMangaRule(mangaList[i].getPath());
        }

    }

    public static void updateMangaRule(String rulePath) throws InterruptedException, IOException
    {

        //load manga rule to search updates for
        //   String rulePath = "C:/Users/brenn/Documents/NetBeansProjects/Halen/build/rules/manga/think tank v2.xml";
        //System.out.println("#########################################################################################################");
        ColorCmd.println("", fgWhiteBgYellow);
        ColorCmd.printlnCenter("SEARCHING FOR MANGA UPDATES - " + new File(rulePath).getName(), fgWhiteBgYellow);
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
            ColorCmd.println("Skipping rule update, manga series is already completed.....", fgYellowBgWhite);
            ColorCmd.println("", fgYellowBgWhite);
        }
    

    }

}
