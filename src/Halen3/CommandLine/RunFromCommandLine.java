/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.CommandLine;

import Halen3.EmailNotifier.SendEmailNotification;
import static Halen3.FileMover.SearchAlgorithms.moveFilesFromAllRules;
import Halen3.IO.FileManager;
import Halen3.IO.Log;
import Halen3.Retrievers.ComicsV2.DownloadNewIssues;
import Halen3.Retrievers.ComicsV2.SearchForUpdates;
import Halen3.Retrievers.Films.DownloadNewFilms;
import Halen3.Retrievers.Manga.DownloadNewMangaVolumes;
import Halen3.Retrievers.TvShows.DownloadNewEpisodes;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author brenn
 */
public class RunFromCommandLine
{
  
    public static void runFromCommandLine(String args[]) throws IOException, InterruptedException
    {
    //################################################################################
    //            create log file for record keeping
    //################################################################################
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH.mm.ss");
        
        File d = new File(FileManager.launchPath() + "\\logs");
        d.mkdirs();
        File f = new File(FileManager.launchPath() + "\\logs\\Halen_log-date_of_run_" + FileManager.getCurrentDate() + "_____run_time_" + sdf.format(cal.getTime()) + ".txt");
        f.createNewFile();
        FileOutputStream file = new FileOutputStream(f);
        Log tee = new Log(file, System.out);
        System.setOut(tee);
        
    
    //################################################################################
    
    switch (args[0].toLowerCase())
            {
                case "-help": //***********************************************
                                      System.out.println(""
                            + "Double click Halen.jar to launch GUI interface, to run this program from\nthe command line, the following are supported."
                            + "(NOTE: HALEN DOES NOT SUPPORT\nMULTIPLE COMMANDS AT ONCE (IE, NO JAVA -JAR HALEN.JAR -COMMAND1 -COMMAND2)\n\n"
                            + "The following are all supported commands and what each does:\n\n"
                            + "-help         | you should know this one, its the only way you could be reading\n                this text, bravo for figuring it out.\n\n"
                            + "-relocate     | searches through all rules and trys to find matches on drive and\n                organise them into folders per each rules settings. (ignores\n                comics since they can download directly to required locations)\n\n"
                            + "-search       | this command makes halen search for the latest episodes without\n                needing the GUI running.\n\n"
                            + "-update_rules | this command updates rules (tv) by downloading latest season\n                information from trakt.");
              
                                  break;
                               //***********************************************
                                  
                case "-search": //***********************************************
                                  
                                  //download tv show episodes
                                  DownloadNewEpisodes.downloadNewEpisodes();
                                  //wait for episode downloads to complete before moving to next
                                  while(DownloadNewEpisodes.searchingForTvEpisodes == true)
                                  { 
                                      Thread.sleep(15000);
                                  }
                                    DownloadNewFilms.downloadNewFilms();
                                  //wait for Film downloads to complete before moving to next
                                  while(DownloadNewFilms.searchingForFilms == true)
                                  { 
                                      Thread.sleep(15000);
                                  }
                                  
                                  //download new comic issues
                                  DownloadNewIssues.downloadNewIssues();
                                  //download any new manga issues
                                  DownloadNewMangaVolumes.downloadNewVolumes();
                                  
                                  //download new anime episodes
                                 // DownloadNewAnimeEpisodes.getNewAnimeEpisodeMagnets();
                                  
                                  SendEmailNotification.sendEmailNotice(SendEmailNotification.createUpdateListMessage());
                                  break;
                                //***********************************************
                                  
                case "-relocate": //***********************************************
                               
                                  moveFilesFromAllRules();
                                  
                                  break;
                                  //***********************************************                                  //***********************************************
                                  
                case "-update_rules": //***********************************************
                                  
                                   
                                   Halen3.Retrievers.TvShows.Trakt.UpdateTraktData.updateTvRules();
                                   while(Halen3.Retrievers.TvShows.Trakt.UpdateTraktData.currentlyUpdatingTVRules == true)
                                   {
                                       Thread.sleep(5000);
                                   }
                                   Halen3.Retrievers.Films.Trakt.UpdateTraktData.updateFilmRules();
                                   while(Halen3.Retrievers.Films.Trakt.UpdateTraktData.currentlyUpdatingFilmRules == true)
                                   {
                                       Thread.sleep(5000);
                                   }
                                   //update comic rules
                                   SearchForUpdates.updateAllComicRules();
                                   //update manga rules
                                   Halen3.Retrievers.Manga.SearchForUpdates.updateAllMangaRules();
                                  // Halen3.Retrievers.Anime.SearchForUpdates.updateAllAnimeRules();
                                   
                                   break;
                                  //***********************************************
                 
                                  
                default: //***********************************************
                                  //code here to do something
                                  System.out.println("COMMAND SWITCH NOT RECOGNIZED!!!");
                               //***********************************************
            }
    }
}
