package Halen3.IO;

import java.io.File;

/**
 * @author TAIBHSE
 */
public class CreateHalenFoldersAndAssets
{

    public static void main(String args[])
    {
        create();
    }

    public static void create()
    {
        File tvFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/tv show/");
        File animeFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/anime/");
        File comicsFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/comics/");
        File moviesFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/movies/");

        File graphicsTVFolder = new File(Halen3.IO.FileManager.launchPath() + "/graphics/tv-show-logos/");
        File graphicsComicsFolder = new File(Halen3.IO.FileManager.launchPath() + "/graphics/comic-book-covers/");
        File logsFolder = new File(Halen3.IO.FileManager.launchPath() + "/logs/");

        if (tvFolder.exists() == false)
        {
            tvFolder.mkdirs();
        }
        if (animeFolder.exists() == false)
        {
            animeFolder.mkdirs();
        }
        if (comicsFolder.exists() == false)
        {
            comicsFolder.mkdirs();
        }
        if (moviesFolder.exists() == false)
        {
            moviesFolder.mkdirs();
        }
        if (graphicsTVFolder.exists() == false)
        {
            graphicsTVFolder.mkdirs();
        }
        if (graphicsComicsFolder.exists() == false)
        {
            graphicsComicsFolder.mkdirs();
        }
        if (logsFolder.exists() == false)
        {
            logsFolder.mkdirs();
        }
    }
}
