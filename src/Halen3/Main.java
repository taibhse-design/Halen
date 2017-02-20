/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3;

import static Halen3.CommandLine.RunFromCommandLine.runFromCommandLine;
import static Halen3.GUI.Anime.AnimeGUI.animePanel;
import static Halen3.GUI.Comics.ComicsGUI.comicPanel;
import static Halen3.GUI.GUIBase.anime;
import static Halen3.GUI.GUIBase.comics;
import static Halen3.GUI.GUIBase.initGUI;
import static Halen3.GUI.GUIBase.primary;
import static Halen3.GUI.GUIBase.secondary;
import static Halen3.GUI.GUIBase.settings;
import static Halen3.GUI.GUIBase.theme;
import static Halen3.GUI.GUIBase.tv;
import static Halen3.GUI.Settings.SettingsGUI.settingsPanel;
import static Halen3.GUI.TV.TvGUI.tvPanel;
import static Halen3.IO.GlobalSharedVariables.areSettingsValid;
import Halen3.EmailNotifier.SendEmailNotification;
import Halen3.Retrievers.TvShows.DownloadNewEpisodes;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import java.io.IOException;

/**
 *
 * @author brenn
 */
public class Main
{
    public static String halenVersion = "3.0.3";
    public static void main(String args[]) throws IOException, InterruptedException
    {
        
         //####################################################################
    //     create halen folder and assets if they do not exist
    Halen3.IO.CreateHalenFoldersAndAssets.create();
    //####################################################################
    
    
      //  args = new String[1];
        //args[0] = "-update_rules";
       // args[0] = "-help";
     //  args[0] = "-search";
     //   DownloadNewEpisodes.saveResults = false;
 //    Halen3.Retrievers.TvShows.DownloadNewEpisodes.saveResults = false;
  //   Halen3.Retrievers.Anime.DownloadNewAnimeEpisodes.saveResults = false;
 //    Halen3.Retrievers.Comics.DownloadNewIssues.saveResults = false;
    //####################################################################
    //      only allow one instance of halen to run at a time
    //####################################################################
        
         String appId = "Taibhse.Halen";
        boolean alreadyRunning;
        try
        {
            JUnique.acquireLock(appId);
            alreadyRunning = false;
        } catch (AlreadyLockedException e)
        {
            alreadyRunning = true;
            System.out.println("ALREADY RUNNING");
            System.exit(0);
        }
        
   
    
    if(args.length == 0)
    {
         initGUI();
         if(areSettingsValid() == false)
         {
                 tv.setVisible(false);
                comics.setVisible(false);
                anime.setVisible(false);
                theme.setVisible(false);
                settings.setOpaque(true);
                
                 tv.setBackground(primary);
                comics.setBackground(primary);
                anime.setBackground(primary);
                settings.setBackground(secondary);
                
                tvPanel.setVisible(false);
                comicPanel.setVisible(false);
                animePanel.setVisible(false);
                settingsPanel.setVisible(true);
         }
         
    }else //run windowless command line
    {
        //only run if valid settings exist
        if(areSettingsValid() == true)
        {
            runFromCommandLine(args);
            
        }else //if no valid settings email notice to warn operator
        {
            System.out.println("ERROR - Invalid settings detected, halen will not run until this is fixed, please access Halen through its GUI and configure valid settings!");
           //this may crash if one of the invalid settings is the emails setting themselves
           SendEmailNotification.sendEmailNotice(SendEmailNotification.error("Invalid settings detected, halen will not run until this is fixed, please access Halen through its GUI and configure valid settings!"));
        }
    }
    }
}
