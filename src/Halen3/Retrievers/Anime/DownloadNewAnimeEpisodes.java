/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Anime;

import Halen3.IO.FileManager;
import Halen3.EmailNotifier.SendEmailNotification;
import static Halen3.Retrievers.Anime.SearchForUpdates.updateAllAnimeRules;
import Halen3.Retrievers.MagnetHandler;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brenn
 */
public class DownloadNewAnimeEpisodes
{

    public static void main(String args[]) throws FileNotFoundException, IOException
    {
          getNewAnimeEpisodeMagnets();
          MagnetHandler.sendToClient();
          SendEmailNotification.sendEmailNotice(SendEmailNotification.createUpdateListMessage());
    }

    public static boolean saveResults = true;
    
    public static void getNewAnimeEpisodeMagnets()
    {
      

        File[] animeList = new File(FileManager.launchPath() + "\\rules\\anime\\").listFiles();

        for (int i = 0; i < animeList.length; i++)
        {
            PrintWriter out = null;
            try
            {
                
                List eps = FileManager.readFile(animeList[i].getPath());
                System.out.println("\n#######################################################################################");
                System.out.println("Searching for new episodes of " + FileManager.returnTag("title", eps.getItem(0)));
                System.out.println("#######################################################################################\n");
                
                
                
                SendEmailNotification.retrievedAnime.add(eps.getItem(0) + "<retEps></retEps>");
                
                //loop send eps to magnet handler
                for (int j = 1; j < eps.getItemCount(); j++)
                {
                    System.out.println("Loop J pos " + j);
                    if (FileManager.returnTag("retrieved", eps.getItem(j)).equals("false"))
                    {
                        System.out.println("TRUE");
                        String magnet = "";
                        try
                        {
                         magnet = Nyaa.getAnimeEpisodeMagnet(FileManager.returnTag("nyaaSearch", eps.getItem(i)), j);
                        
                        } catch (IOException ex)
                        {
                            Logger.getLogger(DownloadNewAnimeEpisodes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        if(!magnet.equals(""))
                        {
                        System.out.println(FileManager.returnTag("name", eps.getItem(j)) + " added to download queue.....");
                        MagnetHandler.addLinkTOMAgnetList(magnet);

                        eps.replaceItem(FileManager.updateTag("retrieved", eps.getItem(j), "true"), j);

                         SendEmailNotification.retrievedAnime.replaceItem(FileManager.updateTag("retEps",
                                    SendEmailNotification.retrievedAnime.getItem(i),
                                    FileManager.returnTag("retEps", SendEmailNotification.retrievedAnime.getItem(i)) + FileManager.returnTag("name", eps.getItem(j)).replace(" [720p][AAC].mp4", "") + "-!SPLIT!-"),
                                    i); //item to replace
                         
                        }
                    }
                }
                if(saveResults == true)
                {
                //loop print updated list
                out = new PrintWriter(animeList[i]);
                for (int j = 0; j < eps.getItemCount(); j++)
                {
                    out.println(eps.getItem(j));
                }
                out.close();
                }
            } catch (FileNotFoundException ex)
            {
                Logger.getLogger(DownloadNewAnimeEpisodes.class.getName()).log(Level.SEVERE, null, ex);
            } finally
            {
                try
                {
                out.close();
                }catch(NullPointerException e)
                {
                    
                }
            }

        }
    }

}
