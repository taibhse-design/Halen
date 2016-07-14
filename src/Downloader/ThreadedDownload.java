package Downloader;

import Emailer.SendEmail;
import MagnetHandler.Handler;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import halen.FileManager;
import static halen.GUI.anim;
import static halen.GUI.color;
import static halen.GUI.frame;
import static halen.GUI.progressBar;
import static halen.GUI.runningRule;
import static halen.Main.secondary;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * @author TAIBHSE
 */
public class ThreadedDownload
{

    public static int i = 0;

    public static volatile int count = 0;
    private static double value = 0;

    static final File tvFolder = new File(halen.FileManager.launchPath() + "/rules/tv show/");
    static final File[] tvList = tvFolder.listFiles();
    static Thread tv[] = new Thread[tvList.length];
    static final File animeFolder = new File(halen.FileManager.launchPath() + "/rules/anime/");
    static final File[] animeList = animeFolder.listFiles();
    static Thread anime[] = new Thread[animeList.length];
    static final File comicsFolder = new File(halen.FileManager.launchPath() + "/rules/comics/");
    static final File[] comicsList = comicsFolder.listFiles();
    static Thread comics[] = new Thread[comicsList.length];

    static double percent = 100.0 / (tvList.length + animeList.length + comicsList.length);

    public static void getMagnets() throws IOException, InterruptedException
    {

        // System.out.println(percent);
        ExecutorService executor = Executors.newFixedThreadPool(35);

        /**
         * ****************************************************************************
         */
        /**
         * ************************TV RULES
         * RUNNING************************************
         */
        /**
         * ****************************************************************************
         */
        //tv rules first
        // File[] rulesList = tvFolder.listFiles();
        ImageIcon a;
        ImageIcon as;
        try
        {
            a = new ImageIcon(color(secondary, "Resources/metro/types/retMedia.png"));
            as = new ImageIcon(a.getImage().getScaledInstance((int) (frame.getHeight() / 1.2), (int) (frame.getHeight() / 1.2), java.awt.Image.SCALE_DEFAULT));
            anim.setIcon(as);
        } catch (NullPointerException e)
        {

        }
        for (i = 0; i < tvList.length; i++)
        {
            tv[i] = new Thread(new Runnable()
            {

                int i = Downloader.ThreadedDownload.i;

                @Override
                public void run()
                {

                    PrintWriter out = null;
                    try
                    {
                        try
                        {
                            runningRule.setText("Running " + tvList[i].getName().replace(".xml", "") + "..........");
                        } catch (NullPointerException e)
                        {

                        }
                        List eps = halen.FileManager.readFile(tvList[i].getAbsolutePath());
                        for (int j = 1; j < eps.getItemCount(); j++)
                        {

                         
                            //only get magnet if not already marked as retrieved
                            //only run rule if date for ep release has passed, save processing time
                            if (eps.getItem(j).contains("false") && FileManager.hasDatePassed(FileManager.returnTag("release",eps.getItem(j))))
                            {
                                //get magnet link into string
                                String magnet = "";
                                
                                try
                                {
                                magnet= WebScrapers.ExtraTorrent.getMagnet(FileManager.returnTag("search", eps.getItem(0)), eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                                }catch(FailingHttpStatusCodeException e)
                                {
                                   System.out.print(" ERROR 504 GATEWAY TIME-OUT FOR http://extratorrent.cc/...error caught and ignored.....\n"); 
                                }
                                //print results
                                try
                                {
                                    runningRule.setText("SEARCING FOR " + tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                                    runningRule.setText(runningRule.getText() + " :  " + magnet);
                                } catch (NullPointerException e)
                                {

                                }
                                System.out.println(tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + " : " + magnet);
                              //  System.out.print(" : " + magnet);
                             //   System.out.println("\n");

//if string magnet has magnet url then update ep list to indcare its retrieved
                                if (magnet.contains("magnet:?xt="))
                                {
                                    
                                    SendEmail.retrievedEpisodes.add(tvList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                                    Handler.addLinkTOMAgnetList(magnet);
                                    count++;
                                    eps.replaceItem(eps.getItem(j).replace("false", "true"), j);
                                }
                            }
                            
                            //update value of percentage complete
                            value = value + (percent / (eps.getItemCount() - 1));
                            try
                            {
                                progressBar.setValue((int) value);
                            } catch (NullPointerException e)
                            {

                            }
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
                        Logger.getLogger(ThreadedDownload.class.getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex)
                    {
                        System.out.println(ex);
                        Logger.getLogger(ThreadedDownload.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex)
                    {
                        Logger.getLogger(ThreadedDownload.class.getName()).log(Level.SEVERE, null, ex);
                    } finally
                    {
//                        out.close();
                    }

                }
            });
            executor.execute(tv[i]);

        }

        /**
         * ****************************************************************************
         */
        /**
         * **********************ANIME RULES
         * RUNNING***********************************
         */
        /**
         * ****************************************************************************
         */
        //load anime rules
        //  rulesList = animeFolder.listFiles();
        //set image to anime
//        try
//        {
//            a = new ImageIcon(color(secondary, "Resources/metro/types/anime.png"));
//            as = new ImageIcon(a.getImage().getScaledInstance((int) (frame.getHeight() / 1.2), (int) (frame.getHeight() / 1.2), java.awt.Image.SCALE_DEFAULT));
//            anim.setIcon(as);
//        } catch (NullPointerException e)
//        {
//
//        }
        //reset progress bar
        //  progressBar.setValue(0);

        for (i = 0; i < animeList.length; i++)
        {

            anime[i] = new Thread(new Runnable()
            {

                int i = Downloader.ThreadedDownload.i;

                @Override
                public void run()
                {

                    PrintWriter out = null;
                    try
                    {
                        try
                        {
                            runningRule.setText("Running " + animeList[i].getName().replace(".xml", "") + "..........");
                        } catch (NullPointerException e)
                        {

                        }
                        List eps = halen.FileManager.readFile(animeList[i].getAbsolutePath());
                        for (int j = 1; j < eps.getItemCount(); j++)
                        {
                            
                            try
                            {

                            //only get magnet if not already marked as retrieved
                            if (eps.getItem(j).contains("false"))
                            {
                                //get magnet link into string
                                String magnet = WebScrapers.Nyaa.getMagnet(FileManager.returnTag("search", eps.getItem(0)), eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));

                                //print results
                                try
                                {
                                    runningRule.setText("SEARCING FOR " + animeList[i].getName().replace(".xml", "") + "   " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                                    runningRule.setText(runningRule.getText() + " :  " + magnet);
                                } catch (NullPointerException e)
                                {

                                }

                                System.out.println(animeList[i].getName().replace(".xml", "") + "   " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + " :       " + magnet);
                             //   System.out.print(" :       " + magnet);
                             //   System.out.println("\n\n");

//if string magnet has magnet url then update ep list to indcare its retrieved
                                if (magnet.contains("?page=download") && magnet.contains("nyaa"))
                                {
                                    SendEmail.retrievedAnime.add(animeList[i].getName().replace(".xml", "") + "   " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                                    Handler.addLinkTOMAgnetList(magnet);
                                    eps.replaceItem(eps.getItem(j).replace("false", "true"), j);
                                }
                            }
                            value = value + (percent / (eps.getItemCount() - 1));
                            try
                            {
                                progressBar.setValue((int) value);
                            } catch (NullPointerException e)
                            {

                            }
                            
                            }catch(Exception e)
                            {
                                System.out.println("\n\n###############################################################\n\nERROR WITH " + animeList[i].getName().replace(".xml", "") + " " + i + ":   " + e + "\n\n###############################################################\n\n");
                                  eps.replaceItem(eps.getItem(j).replace("true", "false"), j);
                            }
                        }
                        out = new PrintWriter(animeList[i].getAbsolutePath());
                        for (int j = 0; j < eps.getItemCount(); j++)
                        {
                            out.println(eps.getItem(j));
                        }
                        out.close();
                    } catch (FileNotFoundException ex)
                    {
                        System.out.println(ex);
                        Logger.getLogger(ThreadedDownload.class.getName()).log(Level.SEVERE, null, ex);

                    } finally
                    {
                        //   out.close();
                    }

                }
            });
            executor.execute(anime[i]);
        }

        /**
         * ****************************************************************************
         */
        /**
         * ************************COMICS RULES
         * RUNNING********************************
         */
        /**
         * ****************************************************************************
         */
        //tv rules first
        //   rulesList = comicsFolder.listFiles();
//        try
//        {
//            a = new ImageIcon(color(secondary, "Resources/metro/types/comics.png"));
//            as = new ImageIcon(a.getImage().getScaledInstance((int) (frame.getHeight() / 1.2), (int) (frame.getHeight() / 1.2), java.awt.Image.SCALE_DEFAULT));
//            anim.setIcon(as);
//        } catch (NullPointerException e)
//        {
//
//        }
        // progressBar.setValue(0);
        for (i = 0; i < comicsList.length; i++)
        {
            comics[i] = new Thread(new Runnable()
            {

                int i = Downloader.ThreadedDownload.i;

                @Override
                public void run()
                {
                    PrintWriter out = null;
                    try
                    {
                        try
                        {
                            runningRule.setText("Running " + comicsList[i].getName().replace(".xml", "") + "..........");
                        } catch (NullPointerException e)
                        {

                        }
                        List eps = halen.FileManager.readFile(comicsList[i].getAbsolutePath());
                        for (int j = 1; j < eps.getItemCount(); j++)
                        {

                            //   System.out.println(eps.getItem(j));
                            //only get magnet if not already marked as retrieved
                            if (eps.getItem(j).contains("false"))
                            {
                                //get magnet link into string
                                String magnet = WebScrapers.Kickass.getMagnet(FileManager.returnTag("search", eps.getItem(0)), eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));

                                //print results
                                try
                                {
                                    runningRule.setText("SEARCING FOR " + comicsList[i].getName().replace(".xml", "") + "   " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                                    runningRule.setText(runningRule.getText() + " :  " + magnet);
                                } catch (NullPointerException e)
                                {
                                   // System.out.println("ERROR WITH MAGNET");
                                }
                                System.out.println(comicsList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")) + " : " + magnet);
                             //   System.out.print(" : " + magnet);
                             //   System.out.println("\n");

                                //if string magnet has magnet url then update ep list to indcare its retrieved
                                if (magnet.contains("magnet:?xt="))
                                {
                                    SendEmail.retrievedcomics.add(comicsList[i].getName().replace(".xml", "") + " " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
                                    Handler.addLinkTOMAgnetList(magnet);
                                    //update to state ep/comic gotten
                                    eps.replaceItem(eps.getItem(j).replace("false", "true"), j);
                                }
                            }
                            value = value + (percent / (eps.getItemCount() - 1));

                            try
                            {
                                progressBar.setValue((int) value);
                            } catch (NullPointerException e)
                            {

                            }
                        }
                        out = new PrintWriter(comicsList[i].getAbsolutePath());
                        for (int j = 0; j < eps.getItemCount(); j++)
                        {
                            out.println(eps.getItem(j));
                        }
                        out.close();
                    } catch (FileNotFoundException ex)
                    {
                        System.out.println(ex);
                        Logger.getLogger(ThreadedDownload.class.getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex)
                    {
                        System.out.println(ex);
                        Logger.getLogger(ThreadedDownload.class.getName()).log(Level.SEVERE, null, ex);
                    } finally
                    {
                        //  out.close();
                    }

                }
            });
            executor.execute(comics[i]);
        }

        executor.shutdown();
        while (!executor.isTerminated())
        {
            //pauses code execution here until all threads terminate
        }

        //  System.out.println(count + "   " + Handler.getMagnetCount());
        try
        {
            progressBar.setValue((int) value + 1); //end at 100%
        } catch (NullPointerException e)
        {

        }

//send all links to torrent client
        Handler.sendToClient();
        SendEmail.sendEmailNotice();
       // JOptionPane.showMessageDialog(null, "Finished running rules, \nany found magnet links have \nbeen sent to torrent client.", "FINISHED", 1);
    }

    public static void main(String args[]) throws IOException, InterruptedException
    {

        ThreadedDownload.getMagnets();

    }

}
