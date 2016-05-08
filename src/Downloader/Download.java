//package Downloader;
//
//import MagnetHandler.Handler;
//import halen.FileManager;
//import static halen.GUI.anim;
//import static halen.GUI.color;
//import static halen.GUI.frame;
//import static halen.GUI.progressBar;
//import static halen.GUI.runningRule;
//import static halen.Main.secondary;
//import java.awt.List;
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Random;
//import javax.swing.ImageIcon;
//import javax.swing.JOptionPane;
//
///**
// * @author TAIBHSE
// */
//public class Download
//{
//
//    public static void getMagnets() throws IOException, InterruptedException
//    {
//        File tvFolder = new File(halen.FileManager.launchPath() + "/rules/tv show/");
//        File[] tvList = tvFolder.listFiles();
//        File animeFolder = new File(halen.FileManager.launchPath() + "/rules/anime/");
//        File[] animeList = animeFolder.listFiles();
//        File comicsFolder = new File(halen.FileManager.launchPath() + "/rules/comics/");
//        File[] comicsList = comicsFolder.listFiles();
//
//        double percent = 100.0 / (tvList.length + animeList.length + comicsList.length);
//        // System.out.println(percent);
//        double value = 0;
//
//        /**
//         * ****************************************************************************
//         */
//        /**
//         * ************************TV RULES
//         * RUNNING************************************
//         */
//        /**
//         * ****************************************************************************
//         */
//        //tv rules first
//        // File[] rulesList = tvFolder.listFiles();
//        ImageIcon a;
//        ImageIcon as;
//        try
//        {
//            a = new ImageIcon(color(secondary, "Resources/metro/types/tv.png"));
//            as = new ImageIcon(a.getImage().getScaledInstance((int) (frame.getHeight() / 1.2), (int) (frame.getHeight() / 1.2), java.awt.Image.SCALE_DEFAULT));
//            anim.setIcon(as);
//        } catch (NullPointerException e)
//        {
//
//        }
//        for (int i = 0; i < tvList.length; i++)
//        {
//            try
//            {
//                runningRule.setText("Running " + tvList[i].getName().replace(".xml", "") + "..........");
//            } catch (NullPointerException e)
//            {
//
//            }
//            List eps = halen.FileManager.readFile(tvList[i].getAbsolutePath());
//
//            for (int j = 1; j < eps.getItemCount(); j++)
//            {
//
//                //only get magnet if not already marked as retrieved
//                if (eps.getItem(j).contains("false"))
//                {
//                    //get magnet link into string
//                    String magnet = WebScrapers.ExtraTorrent.getMagnet(FileManager.returnTag("search", eps.getItem(0)), eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
//
//                    //print results
//                    try
//                    {
//                        runningRule.setText("SEARCING FOR " + tvList[i].getName().replace(".xml", "") + "   " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
//                        runningRule.setText(runningRule.getText() + " :  " + magnet);
//                    } catch (NullPointerException e)
//                    {
//
//                    }
//                    System.out.print(tvList[i].getName().replace(".xml", "") + "   " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
//                    System.out.print(" :       " + magnet);
//                    System.out.println("\n\n");
//
////if string magnet has magnet url then update ep list to indcare its retrieved
//                    if (magnet.contains("magnet:?xt="))
//                    {
//                        Handler.addLinkTOMAgnetList(magnet);
//                     //   eps.replaceItem(eps.getItem(j).replace("false", "true"), j);
//                    }
//                }
//                value = value + (percent / (eps.getItemCount() - 1));
//                try
//                {
//                    progressBar.setValue((int) value);
//                } catch (NullPointerException e)
//                {
//
//                }
//            }
//
//            //loop and print out updated tv eps
//            PrintWriter out = new PrintWriter(tvList[i].getAbsolutePath());
//            for (int j = 0; j < eps.getItemCount(); j++)
//            {
//                out.println(eps.getItem(j));
//            }
//            out.close();
//        }
//
//        /**
//         * ****************************************************************************
//         */
//        /**
//         * **********************ANIME RULES
//         * RUNNING***********************************
//         */
//        /**
//         * ****************************************************************************
//         */
//        //load anime rules
//        //  rulesList = animeFolder.listFiles();
//        //set image to anime
//        try
//        {
//            a = new ImageIcon(color(secondary, "Resources/metro/types/anime.png"));
//            as = new ImageIcon(a.getImage().getScaledInstance((int) (frame.getHeight() / 1.2), (int) (frame.getHeight() / 1.2), java.awt.Image.SCALE_DEFAULT));
//            anim.setIcon(as);
//        } catch (NullPointerException e)
//        {
//
//        }
//        //reset progress bar
//        //  progressBar.setValue(0);
//
//        for (int i = 0; i < animeList.length; i++)
//        {
//            try
//            {
//                runningRule.setText("Running " + animeList[i].getName().replace(".xml", "") + "..........");
//            } catch (NullPointerException e)
//            {
//
//            }
//            List eps = halen.FileManager.readFile(animeList[i].getAbsolutePath());
//
//            for (int j = 1; j < eps.getItemCount(); j++)
//            {
//
//                //only get magnet if not already marked as retrieved
//                if (eps.getItem(j).contains("false"))
//                {
//                    //get magnet link into string
//                    String magnet = WebScrapers.Nyaa.getMagnet(FileManager.returnTag("search", eps.getItem(0)), eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
//
//                    //print results
//                    try
//                    {
//                        runningRule.setText("SEARCING FOR " + animeList[i].getName().replace(".xml", "") + "   " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
//                        runningRule.setText(runningRule.getText() + " :  " + magnet);
//                    } catch (NullPointerException e)
//                    {
//
//                    }
//
//                    System.out.print(animeList[i].getName().replace(".xml", "") + "   " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
//                    System.out.print(" :       " + magnet);
//                    System.out.println("\n\n");
//
////if string magnet has magnet url then update ep list to indcare its retrieved
//                    if (magnet.contains("nyaa.eu/?page=download"))
//                    {
//                        Handler.addLinkTOMAgnetList(magnet);
//                      //  eps.replaceItem(eps.getItem(j).replace("false", "true"), j);
//                    }
//                }
//                value = value + (percent / (eps.getItemCount() - 1));
//                try
//                {
//                    progressBar.setValue((int) value);
//                } catch (NullPointerException e)
//                {
//
//                }
//            }
//
//            //loop and print out updated tv eps
//            PrintWriter out = new PrintWriter(animeList[i].getAbsolutePath());
//            for (int j = 0; j < eps.getItemCount(); j++)
//            {
//                out.println(eps.getItem(j));
//            }
//            out.close();
//        }
//
//        /**
//         * ****************************************************************************
//         */
//        /**
//         * ************************COMICS RULES
//         * RUNNING********************************
//         */
//        /**
//         * ****************************************************************************
//         */
//        //tv rules first
//        //   rulesList = comicsFolder.listFiles();
//        try
//        {
//            a = new ImageIcon(color(secondary, "Resources/metro/types/comics.png"));
//            as = new ImageIcon(a.getImage().getScaledInstance((int) (frame.getHeight() / 1.2), (int) (frame.getHeight() / 1.2), java.awt.Image.SCALE_DEFAULT));
//            anim.setIcon(as);
//        } catch (NullPointerException e)
//        {
//
//        }
//        // progressBar.setValue(0);
//        for (int i = 0; i < comicsList.length; i++)
//        {
//            try
//            {
//                runningRule.setText("Running " + comicsList[i].getName().replace(".xml", "") + "..........");
//            } catch (NullPointerException e)
//            {
//
//            }
//
//            List eps = halen.FileManager.readFile(comicsList[i].getAbsolutePath());
//
//            for (int j = 1; j < eps.getItemCount(); j++)
//            {
//
//                //only get magnet if not already marked as retrieved
//                if (eps.getItem(j).contains("false"))
//                {
//                    //get magnet link into string
//                    String magnet = WebScrapers.Kickass.getMagnet(FileManager.returnTag("search", eps.getItem(0)), eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
//
//                    //print results
//                    try
//                    {
//                        runningRule.setText("SEARCING FOR " + comicsList[i].getName().replace(".xml", "") + "   " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
//                        runningRule.setText(runningRule.getText() + " :  " + magnet);
//                    } catch (NullPointerException e)
//                    {
//
//                    }
//                    System.out.print(comicsList[i].getName().replace(".xml", "") + "   " + eps.getItem(j).substring(eps.getItem(j).indexOf("<") + 1, eps.getItem(j).indexOf(">")));
//                    System.out.print(" :       " + magnet);
//                    System.out.println("\n\n");
//                    //if string magnet has magnet url then update ep list to indcare its retrieved
//                    if (magnet.contains("magnet:?xt="))
//                    {
//                        Handler.addLinkTOMAgnetList(magnet);
//                      //  eps.replaceItem(eps.getItem(j).replace("false", "true"), j);
//                    }
//                }
//                value = value + (percent / (eps.getItemCount() - 1));
//
//                try
//                {
//                    progressBar.setValue((int) value);
//                } catch (NullPointerException e)
//                {
//
//                }
//            }
//
//            //loop and print out updated tv eps
//            PrintWriter out = new PrintWriter(comicsList[i].getAbsolutePath());
//            for (int j = 0; j < eps.getItemCount(); j++)
//            {
//                out.println(eps.getItem(j));
//            }
//            out.close();
//        }
//
//        try
//        {
//            progressBar.setValue((int) value + 1); //end at 100%
//        } catch (NullPointerException e)
//        {
//
//        }
//
////send all links to torrent client
//        //      Handler.sendToClient();
//   //     JOptionPane.showMessageDialog(null, "Finished running rules, \nany found magnet links have \nbeen sent to torrent client.", "FINISHED", 1);
//    }
//
//    public static void main(String args[]) throws IOException, InterruptedException
//    {
//
//        getMagnets();
//
//    }
//
//}
