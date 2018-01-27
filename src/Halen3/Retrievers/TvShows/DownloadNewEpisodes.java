/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.TvShows;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgGreenBgWhite;
import static Halen3.CommandLine.ColorCmd.fgRedBgRed;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgGreen;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgRed;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgWhite;
import Halen3.EmailNotifier.SendEmailNotification;
import Halen3.IO.FileManager;
import Halen3.IO.GlobalSharedVariables;
import Halen3.Retrievers.MagnetHandler;
import static Halen3.Retrievers.TvShows.BackupTorrentSiteMagnetSearchers.kickassTvSearch;
import static Halen3.Retrievers.TvShows.BackupTorrentSiteMagnetSearchers.zooqleTvSearch;
import Halen3.Testing.Testing;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author taibhse
 */
public class DownloadNewEpisodes
{

    // public static boolean saveResults = true; //variable used for testing, set to false to prevent saving retrieved magnets to file
    static final File tvFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/tv show/");
    public static volatile boolean searchingForTvEpisodes = false;

    static final File[] tvList = tvFolder.listFiles(new FilenameFilter()
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
        GlobalSharedVariables.testing = "true";
        downloadNewEpisodes();
        // downloadNewIssues();
        //  SendEmailNotification.test();
    }

    public static void downloadNewEpisodes()
    {
        ColorCmd.println(" ", fgWhiteBgGreen);
            ColorCmd.printlnCenter("Compiling New Releases From ShowRSS", fgWhiteBgGreen);
            ColorCmd.println(" ", fgWhiteBgGreen);
            ColorCmd.println(" ", fgWhiteBgWhite);
        //scrape show rss first for all content
        ShowRSS.showRSSScraper();

        searchingForTvEpisodes = true;
        //set to 1 to run as single thread, set higher for more threads
        //*     ExecutorService executor = Executors.newFixedThreadPool(1); //normally set to 15 //was 35
        for (int i = 0; i < tvList.length; i++)
        {

            ColorCmd.println(" ", fgWhiteBgGreen);
            ColorCmd.printlnCenter("Searching for Episodes From: " + tvList[i].getName(), fgWhiteBgGreen);
            ColorCmd.println(" ", fgWhiteBgGreen);
            ColorCmd.println(" ", fgWhiteBgWhite);

            //add main show details to send email notice
            SendEmailNotification.retrievedTVShows.add(FileManager.readFile(tvList[i].getPath()).getItem(0) + "<retEps></retEps>");
           //*  tv[i] = new Thread(new Runnable()
            //*  {

               // int i = DownloadNewEpisodes.i;
            //*    @Override
            //*  public void run()
            //*  {
            PrintWriter out = null;
            try
            {
//                        try
//                        {
                //    System.out.println("Running " + tvList[i].getName().replace(".xml", "") + "..........");
//                        } catch (NullPointerException e)
//                        {
//
//                        }
                List eps = Halen3.IO.FileManager.readFile(tvList[i].getAbsolutePath());
                if (eps.getItemCount() <= 1)
                {
                    ColorCmd.println("", fgRedBgRed);
                    ColorCmd.println(" " + tvList[i].getName() + ": Error with rule...Ep tracking wiped...rule restoration required.....", fgWhiteBgRed);
                    ColorCmd.println("", fgRedBgRed);
                }
                
                //loop through episodes and retrieve magnets for those not already retrieved
                for (int j = 1; j < eps.getItemCount(); j++)
                {

                            //only get magnet if not already marked as retrieved
                    //only run rule if date for ep release has passed, save processing time
                    if (eps.getItem(j).contains("false") && FileManager.hasDatePassed(FileManager.returnTag("release", eps.getItem(j))))
                    {
                        //get magnet link into string
                        String magnet = "";

                               // try
                        //{
                        try
                        {

                                   // magnet = Halen3.Retrievers.TvShows.ExtraTorrentMagnetLinksScraper.getMagnet(FileManager.returnTag("search", eps.getItem(0)), eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                            //magnet = Testing.extrntTvRssSearchMagnetRetriever(FileManager.returnTag("search", eps.getItem(0)) + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                           String search = FileManager.returnTag("search", eps.getItem(0)) + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")).trim();
                          
                         System.out.println("search terms: " + search);
                            //try to find ep from showRSS
                            magnet = ShowRSS.showRSSSearch(FileManager.returnTag("search", eps.getItem(0)).trim() , eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")).trim());
                           
                            //backup magnet searches if magnet still blank
                            if(!magnet.contains("magnet:?xt="))
                            {
                              //kickass search backup
                              magnet = kickassTvSearch(search); 
                            }
                            
                             if(!magnet.contains("magnet:?xt="))
                            {
                              //zooqle search backup
                              magnet = zooqleTvSearch(search); 
                            }
                            
//   System.out.println("poop"+tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + " : " + magnet + "\n");
                        } catch (FailingHttpStatusCodeException e)
                        {

                            if (e.getStatusCode() == 504)
                            {
                                ColorCmd.print(" " + tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + ": ERROR 504 GATEWAY TIME-OUT FOR http://extratorrent.cc/...error caught and ignored.....", fgRedBgWhite);
                            } else if (e.getStatusCode() == 503)
                            {
                                ColorCmd.print(" " + tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + ": ERROR 503 SERVICE UNAVAILABLE / NOT FOUND...error caught and ignored.....", fgRedBgWhite);
                            } else
                            {
                                ColorCmd.print(" " + tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + ": ERROR " + e.getStatusCode() + "...error caught and ignored", fgRedBgWhite);

                            }

                                  //  System.out.println(magnet);
                        }

                        if (magnet.contains("magnet:?xt=")) //handle magnet if found
                        {
                            ColorCmd.println(eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + " : " + magnet, fgGreenBgWhite);
                            
                            SendEmailNotification.retrievedTVShows.replaceItem(FileManager.updateTag("retEps",
                                    SendEmailNotification.retrievedTVShows.getItem(i),
                                    FileManager.returnTag("retEps", SendEmailNotification.retrievedTVShows.getItem(i)) + "  " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">"))),
                                    i); //item to replace
                            if (GlobalSharedVariables.testing.equals("false")) //save output if not in testing mode
                            {
                                
                                
                                //create a .magnet file in the search in folder (client should auto load .torrents and .magnets from here)
                                PrintWriter save = new PrintWriter(FileManager.returnTag("searchInFolder", eps.getItem(0)).trim() + "\\" + tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + ".magnet");
                                save.println(magnet);
                                save.close();
                                
                                //save backup of .magnet into magnets folder in halen
                                new File(FileManager.launchPath() + "\\magnets\\").mkdirs(); //make magnet directory if not exist
                                PrintWriter save2 = new PrintWriter(FileManager.launchPath() + "\\magnets\\" + tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + ".magnet");
                                save2.println(magnet);
                                save2.close();
                                
                                // SendEmailNotification.retrievedTVShows.add(tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                                //start magnet client and directly send to client incase direct save file fails
                                MagnetHandler.addLinkTOMAgnetList(magnet);
                                //   count++;
                                eps.replaceItem(eps.getItem(j).replace("false", "true"), j);
                            }
                        } else  //check if episode should be passed by now and handle possible issues
                        {

                         //   System.out.println(eps.getItem(j).contains("false"));
                            if (eps.getItem(j).contains("false") && FileManager.hasDatePassed(FileManager.returnTag("release", eps.getItem(j)))) //if episode is past release date but no magnet, there may be an issue the user should know about
                            {

                                //check how many days since episodes release, if more than 3 days and not retrieved,
                                // there are issues with the rule setup, eg, group may no longer provide show
                                //send user email of errors
                                if (FileManager.howManyDaysSince(FileManager.returnTag("release", eps.getItem(j))) > 3)
                                {

                                    ColorCmd.println(" " + tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + " : " + "ERROR EPISODE PAST RELEASE DATE - STILL NOT RETRIEVED - USER NOTIFIED...please revise rules to correct for issue.....", fgRedBgWhite);

                                    SendEmailNotification.retrievedTVShows.replaceItem(FileManager.updateTag("retEps",
                                            SendEmailNotification.retrievedTVShows.getItem(i),
                                            FileManager.returnTag("retEps", SendEmailNotification.retrievedTVShows.getItem(i)) + " <br> " + "\n<font style=\"color: red;\">ERRORS WITH " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + " CHECK RULES</font>"),
                                            i); //item to replace

                                }

                            }

                        }

                             //   System.out.println();
                        //} //catch (FailingHttpStatusCodeException e)
//                                {
//                                    System.out.print(tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + ": ERROR 504 GATEWAY TIME-OUT FOR http://extratorrent.cc/...error caught and ignored.....\n\n");
//                                }
                        //print results
//                                try
//                                {
//                                    runningRule.setText("SEARCING FOR " + tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
//                                    runningRule.setText(runningRule.getText() + " :  " + magnet);
//                                } catch (NullPointerException e)
//                                {
//
//                                }
                        //  System.out.print(" : " + magnet);
                        //   System.out.println("\n");
//if string magnet has magnet url then update ep list to indcare its retrieved
                    }

                    //update value of percentage complete
//                            value = value + (percent / (eps.getItemCount() - 1));
//                            try
//                            {
//                                progressBar.setValue((int) value);
//                            } catch (NullPointerException e)
//                            {
//
//                            }
                }
                out = new PrintWriter(tvList[i].getAbsolutePath());
                for (int j = 0; j < eps.getItemCount(); j++)
                {
                    out.println(eps.getItem(j));
                }
                out.close();
            } catch (FileNotFoundException ex)
            {

                System.out.println(ex);
                Logger.getLogger(DownloadNewEpisodes.class.getName()).log(Level.SEVERE, null, ex);

//                    } catch (IOException ex)
//                    {
//                        System.out.println(ex);
//                        Logger.getLogger(DownloadNewEpisodes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex)
            {
                Logger.getLogger(DownloadNewEpisodes.class.getName()).log(Level.SEVERE, null, ex);
            } finally
            {
                try
                {
                    out.close();
                } catch (NullPointerException e)
                {
                }
            }

             //*   }
            //* });
            //*    executor.execute(tv[i]);
        }

    //*    executor.shutdown();
        //*   while (!executor.isTerminated())
        //*   {
        //*       //pauses code execution here until all threads terminate
        //*  }
        searchingForTvEpisodes = false;
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
//            Logger.getLogger(DownloadNewEpisodes.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
