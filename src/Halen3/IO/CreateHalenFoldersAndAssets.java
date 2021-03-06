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
        File mangaFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/manga/");
        File moviesFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/films/");

        File graphicsTVFolder = new File(Halen3.IO.FileManager.launchPath() + "/graphics/tv-show-logos/");
        File graphicsFilmFolder = new File(Halen3.IO.FileManager.launchPath() + "/graphics/film-logos/");
        File graphicsComicsFolder = new File(Halen3.IO.FileManager.launchPath() + "/graphics/comic-book-covers/");
        File graphicsMangaFolder = new File(Halen3.IO.FileManager.launchPath() + "/graphics/manga-covers/");
         File graphicsAnimeFolder = new File(Halen3.IO.FileManager.launchPath() + "/graphics/anime-covers/");
       
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
         if (mangaFolder.exists() == false)
        {
            mangaFolder.mkdirs();
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
          if (graphicsMangaFolder.exists() == false)
        {
            graphicsMangaFolder.mkdirs();
        }
         if (graphicsAnimeFolder.exists() == false)
        {
            graphicsAnimeFolder.mkdirs();
        }
        if(graphicsFilmFolder.exists() == false)
        {
            graphicsFilmFolder.mkdirs();
        }
        if (logsFolder.exists() == false)
        {
            logsFolder.mkdirs();
        }
    }
}
