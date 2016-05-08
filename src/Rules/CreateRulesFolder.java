package Rules;

import java.io.File;

/**
 * @author TAIBHSE
 */
public class CreateRulesFolder 
{

    public static void main(String args[])
    {
        create();
    }
    public static void create()
    {
         File tvFolder = new File(halen.FileManager.launchPath() + "/rules/tv show/");
        File animeFolder = new File(halen.FileManager.launchPath() + "/rules/anime/");
        File comicsFolder = new File(halen.FileManager.launchPath() + "/rules/comics/");
        
        if(tvFolder.exists() == false)
        {
            tvFolder.mkdirs();
        }
         if(animeFolder.exists() == false)
        {
            animeFolder.mkdirs();
        }
          if(comicsFolder.exists() == false)
        {
            comicsFolder.mkdirs();
        }
    }
}
