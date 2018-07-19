/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Anime;

import Halen3.IO.FileManager;
import Halen3.IO.GlobalSharedVariables;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author brenn
 */
public class SearchForUpdates
{

    public static void updateAllAnimeRules()
    {
        File[] animeList = new File(FileManager.launchPath() + "\\rules\\anime\\").listFiles();
        GlobalSharedVariables.startChromeDriver();
        for (int i = 0; i < animeList.length; i++)
        {
            System.out.println("#########################################################################################################\n"
                    + "   SEARCHING FOR ANIME UPDATES - " + animeList[i].getName() + "\n"
                    + "#########################################################################################################");
            updateAnimeRule(animeList[i].getPath());
          //  System.out.println("#######################################################################################");
        }
        GlobalSharedVariables.endChromeDriver();
    }

    private static void updateAnimeRule(String rulePath)
    {
        List data = FileManager.readFile(rulePath);

        if (FileManager.returnTag("seriesURL", data.getItem(0)).contains("deadfishencod"))
        {
            try
            {
                DeadfishEncodes.checkForDeadfishUpdates(rulePath);
            } catch (FileNotFoundException e)
            {

            }
        } else
        {

        }
    }
}
