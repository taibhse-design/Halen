/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.TvShows;

import Halen3.Retrievers.MagnetHandler;
import Halen3.EmailNotifier.SendEmailNotification;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import Halen3.IO.FileManager;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author brenn
 */
public class DownloadNewEpisodes
{

    public static boolean saveResults = true; //variable used for testing, set to false to prevent saving retrieved magnets to file
    static final File tvFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/tv show/");
    public static volatile boolean searchingForTvEpisodes = false;

    static final File[] tvList = tvFolder.listFiles(new FilenameFilter()
    {
        public boolean accept(File dir, String name)
        {
            return name.toLowerCase().endsWith(".xml");
        }
    });

    static Thread tv[] = new Thread[tvList.length];
    static volatile int i = 0;

    public static void main(String args[]) throws InterruptedException, IOException, MessagingException
    {
        saveResults = false;
        downloadNewEpisodes();
       // downloadNewIssues();
        //  SendEmailNotification.test();
    }

    public static void downloadNewEpisodes()
    {

        searchingForTvEpisodes = true;
        ExecutorService executor = Executors.newFixedThreadPool(15); //was 35
        for (i = 0; i < tvList.length; i++)
        {

            //add main show details to send email notice
            SendEmailNotification.retrievedTVShows.add(FileManager.readFile(tvList[i].getPath()).getItem(0) + "<retEps></retEps>");
            tv[i] = new Thread(new Runnable()
            {

                int i = DownloadNewEpisodes.i;

                @Override
                public void run()
                {

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

                        for (int j = 1; j < eps.getItemCount(); j++)
                        {

                            //only get magnet if not already marked as retrieved
                            //only run rule if date for ep release has passed, save processing time
                            if (eps.getItem(j).contains("false") && FileManager.hasDatePassed(FileManager.returnTag("release", eps.getItem(j))))
                            {
                                //get magnet link into string
                                String magnet = "";

                                try
                                {
                                    magnet = Halen3.Retrievers.TvShows.ExtraTorrentMagnetLinksScraper.getMagnet(FileManager.returnTag("search", eps.getItem(0)), eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                                    System.out.println(tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + " : " + magnet + "\n");

                                    if (magnet.contains("magnet:?xt="))
                                    {
                                        SendEmailNotification.retrievedTVShows.replaceItem(FileManager.updateTag("retEps",
                                                SendEmailNotification.retrievedTVShows.getItem(i),
                                                FileManager.returnTag("retEps", SendEmailNotification.retrievedTVShows.getItem(i)) + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">"))),
                                                i); //item to replace
                                        if (saveResults == true)
                                        {
                                            // SendEmailNotification.retrievedTVShows.add(tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                                            MagnetHandler.addLinkTOMAgnetList(magnet);
                                            //   count++;
                                            eps.replaceItem(eps.getItem(j).replace("false", "true"), j);
                                        }
                                    }

                                } catch (FailingHttpStatusCodeException e)
                                {
                                    System.out.print(tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + ": ERROR 504 GATEWAY TIME-OUT FOR http://extratorrent.cc/...error caught and ignored.....\n\n");
                                }
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

                    } catch (IOException ex)
                    {
                        System.out.println(ex);
                        Logger.getLogger(DownloadNewEpisodes.class.getName()).log(Level.SEVERE, null, ex);
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

                }
            });
            executor.execute(tv[i]);

        }

        executor.shutdown();
        while (!executor.isTerminated())
        {
            //pauses code execution here until all threads terminate
        }

        searchingForTvEpisodes = false;
        //  System.out.println(count + "   " + Handler.getMagnetCount());
//send all links to torrent client
        if (saveResults == true)
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
