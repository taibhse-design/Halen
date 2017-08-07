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
import static Halen3.IO.GlobalSharedVariables.areSettingsValid;
import Halen3.EmailNotifier.SendEmailNotification;
import static Halen3.GUI.Film.FilmGUI.filmPanel;
import static Halen3.GUI.GUIBase.film;
import static Halen3.GUI.TV.TvGUI.tvPanel;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import java.io.IOException;

/**
 *
 * @author brenn
 */
public class Main
{
    
    /**
     *   Numbering & Naming System
     * x1.x2.x3 - GoddessName Build
     * 
     * x3 increments only when minor bugs are patched, eg, if build is 3.0.0 and 
     * a minor bug is patched, build is now 3.0.1, bugs fixed within a day count 
     * as minor bugs.
     * 
     * x2 only increments when major bugs and issues are patched, bugs and issues 
     * requiring more than one day or major method / class rebuilds will increment 
     * x2.
     * 
     * x1 only increments when new features are added or the underlaying code 
     * has been drastically reworked for better performance rather than bug fixing.
     * The build name should also be changed if the improved performance or added 
     * features drastically change the build from the previous version.
     * 
     * GoddessName - build names should be given alphabetically using Goddess names 
     * from any mythology. Name should not be reflected in jar file name. this is 
     * purely for UI.
     */
    
    private static String buildVersion = "4.4.4";
    private static String buildName = "Kore";
    private static String buildNameDescription = "goddess of the harvest";
    public static String halenVersion = buildVersion + " - " + buildName; 
    
    
    public static void main(String args[]) throws IOException, InterruptedException
    {
        
    //#########################################################################
    // Create halen folder and assets if they do not exist
    Halen3.IO.CreateHalenFoldersAndAssets.create();
    //#########################################################################
    
    
      //  args = new String[1];
       // args[0] = "-update_rules";
       // args[0] = "-help";
      //  args[0] = "-relocate";
    // args[0] = "-search";
     //   DownloadNewEpisodes.saveResults = false;
 //    Halen3.Retrievers.TvShows.DownloadNewEpisodes.saveResults = false;
  //   Halen3.Retrievers.Anime.DownloadNewAnimeEpisodes.saveResults = false;
 //    Halen3.Retrievers.Comics.DownloadNewIssues.saveResults = false;
    //####################################################################
    //      only allow one instance of halen to run at a time
    //####################################################################
        
         String appId = "Taibhse.Halen";
         
         System.out.println("APP-ID: Taibhse.Halen");
         System.out.println("Build Version: " + buildVersion);
         System.out.println("Build Name: " + buildName);
         System.out.println("Build Name Description: " + buildNameDescription);
         
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
                  film.setVisible(false);
                comics.setVisible(false);
                anime.setVisible(false);
                theme.setVisible(false);
                settings.setOpaque(true);
                
                 tv.setBackground(primary);
                 film.setBackground(primary);
                comics.setBackground(primary);
                anime.setBackground(primary);
                settings.setBackground(secondary);
                
                tvPanel.setVisible(false);
                filmPanel.setVisible(false);
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
