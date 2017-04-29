/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Anime;

import java.io.IOException;

/**
 *
 * @author brenn
 */
public class CreateAnimeRule
{
    public static void main(String args[]) throws InterruptedException, IOException
    {
        saveNewRule("attack on titan 2", "https://anilist.co/anime/20958/ShingekinoKyojin2", "testMoveTo", "testSearchIn", "search for", "nyaa search");
    }
    public static void saveNewRule(String name, String url, String moveTo, String searchIn, String searchFor, String nyaaSearch) throws InterruptedException, IOException
    {
        if(url.contains("anilist")) //make sure using anilist
        {
            AnilistDataScraper.createNewAnimeRule(name, url, moveTo, searchIn, searchFor, nyaaSearch);
          //  DeadfishEncodes.createNewDeadFishRule(name, url, moveTo, searchIn, searchFor);
        }else
        {
            
        }
    }
}
