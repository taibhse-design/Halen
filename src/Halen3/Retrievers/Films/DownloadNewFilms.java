/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Films;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgBlue;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgGreen;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgWhite;
import Halen3.EmailNotifier.SendEmailNotification;
import Halen3.IO.FileManager;
import Halen3.IO.GlobalSharedVariables;
import static Halen3.Retrievers.Films.SkyTorrentsFilmScraper.skyTrntFilmRssSearchMagnetRetriever;
import Halen3.Retrievers.MagnetHandler;
import Halen3.Testing.Testing;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author taibhse
 */
public class DownloadNewFilms
{

    // public static boolean saveResults = true; //variable used for testing, set to false to prevent saving retrieved magnets to file
    static final File filmFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/films/");
    public static volatile boolean searchingForFilms = false;

    static final File[] filmList = filmFolder.listFiles(new FilenameFilter()
    {
        public boolean accept(File dir, String name)
        {
            return name.toLowerCase().endsWith(".xml");
        }
    });

    //* static Thread tv[] = new Thread[tvList.length];
    //  static volatile int i = 0;
    public static void main(String args[]) throws InterruptedException, IOException, MessagingException
    {
      //   saveResults = false;
        downloadNewFilms();
        // downloadNewIssues();
        //  SendEmailNotification.test();
    }

    public static void downloadNewFilms()
    {

        searchingForFilms = true;
        //set to 1 to run as single thread, set higher for more threads
        //*     ExecutorService executor = Executors.newFixedThreadPool(1); //normally set to 15 //was 35
       System.out.println(filmList.length);
        for (int i = 0; i < filmList.length; i++)
        {
            
            if(FileManager.returnTag("retrieved", FileManager.readFile(filmList[i].getAbsolutePath()).getItem(0)).equals("false"))
            {
                
            

//            ColorCmd.println(" ", fgWhiteBgBlue);
//            ColorCmd.printlnCenter("Searching for Film: " + filmList[i].getName(), fgWhiteBgBlue);
//            ColorCmd.println(" ", fgWhiteBgBlue);
//            ColorCmd.println(" ", fgWhiteBgWhite);

            //add main show details to send email notice
            // SendEmailNotification.retrievedTVShows.add(FileManager.readFile(filmList[i].getPath()).getItem(0) + "<retEps></retEps>");
            //get magnet link into string
            String magnet = "";

            // try
            //{
            try
            {

                // magnet = Halen3.Retrievers.TvShows.ExtraTorrentMagnetLinksScraper.getMagnet(FileManager.returnTag("search", eps.getItem(0)), eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                magnet = skyTrntFilmRssSearchMagnetRetriever(FileManager.returnTag("search", FileManager.readFile(filmList[i].getPath()).getItem(0)));

                //   System.out.println("poop"+tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + " : " + magnet + "\n");
            } catch (FailingHttpStatusCodeException e)
            {

                if (e.getStatusCode() == 504)
                {
                    ColorCmd.print(" " + filmList[i].getName().replace(".xml", "") + ": ERROR 504 GATEWAY TIME-OUT FOR http://extratorrent.cc/...error caught and ignored.....", fgRedBgWhite);
                } else if (e.getStatusCode() == 503)
                {
                    ColorCmd.print(" " + filmList[i].getName().replace(".xml", "") + ": ERROR 503 SERVICE UNAVAILABLE / NOT FOUND...error caught and ignored.....", fgRedBgWhite);
                } else
                {
                    ColorCmd.print(" " + filmList[i].getName().replace(".xml", "") + ": ERROR " + e.getStatusCode() + "...error caught and ignored", fgRedBgWhite);

                }

                //  System.out.println(magnet);
            }   catch (IOException ex)
                {
                    Logger.getLogger(DownloadNewFilms.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(DownloadNewFilms.class.getName()).log(Level.SEVERE, null, ex);
                }

            if (magnet.contains("magnet:?xt=")) //handle magnet if found
            {
                //add retrieved films to email notifier list
                SendEmailNotification.retrievedFilms.add(FileManager.readFile(filmList[i].getAbsolutePath()).getItem(0));
           
                if (GlobalSharedVariables.testing.equals("false")) //save output if not in testing mode
                {
                    // SendEmailNotification.retrievedTVShows.add(tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                    MagnetHandler.addLinkTOMAgnetList(magnet);

                    //update rule to indicate film downloaded
                    String updatedRule = FileManager.updateTag("retrieved", FileManager.readFile(filmList[i].getAbsolutePath()).getItem(0), "true");
                  
                    try
                    {
                        
                    PrintWriter out = new PrintWriter(filmList[i].getAbsolutePath());
                    out.println(updatedRule);
                    out.close();
                    
                    }catch(FileNotFoundException e)
                    {
                        System.out.println(e);
                    }
                }
            } else  //check if episode should be passed by now and handle possible issues
            {

//                    if (FileManager.hasDatePassed(FileManager.returnTag("release", eps.getItem(j)))) //if episode is past release date but no magnet, there may be an issue the user should know about
//                    {
//
//                                            //check how many days since episodes release, if more than 3 days and not retrieved,
//                        // there are issues with the rule setup, eg, group may no longer provide show
//                        //send user email of errors
//                        if (FileManager.howManyDaysSince(FileManager.returnTag("release", eps.getItem(j))) > 3)
//                        {
//
//                            ColorCmd.println(" " + tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + " : " + "ERROR EPISODE PAST RELEASE DATE - STILL NOT RETRIEVED - USER NOTIFIED...please revise rules to correct for issue.....", fgRedBgWhite);
//
//                            SendEmailNotification.retrievedTVShows.replaceItem(FileManager.updateTag("retEps",
//                                    SendEmailNotification.retrievedTVShows.getItem(i),
//                                    FileManager.returnTag("retEps", SendEmailNotification.retrievedTVShows.getItem(i)) + " <br> " + "\n<font style=\"color: red;\">ERRORS WITH " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + " CHECK RULES</font>"),
//                                    i); //item to replace
//
//                        }
//
//                    }
            }
            
            }

        }

        searchingForFilms = false;

        //  System.out.println(count + "   " + Handler.getMagnetCount());
//send all links to torrent client
        if (GlobalSharedVariables.testing.equals("false")) //only send to client if not in testing mode
        {
            MagnetHandler.sendToClient();
        }

//        try
//        {
//            SendEmailNotification.test();
//        } catch (MessagingException | IOException ex)
//        {
//            Logger.getLogger(DownloadNewFilms.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
