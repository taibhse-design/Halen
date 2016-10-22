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
        saveNewRule("Planetarian: Chiisana Hoshi no Yume", "http://deadfishencod.es/Planetarian_Chiisana_Hoshi_no_Yume/", "test", "test", "search for");
    }
    public static void saveNewRule(String name, String url, String moveTo, String searchIn, String searchFor) throws InterruptedException, IOException
    {
        if(url.contains("deadfishencod")) //save deadfish rule
        {
            DeadfishEncodes.createNewDeadFishRule(name, url, moveTo, searchIn, searchFor);
        }else
        {
            
        }
    }
}
