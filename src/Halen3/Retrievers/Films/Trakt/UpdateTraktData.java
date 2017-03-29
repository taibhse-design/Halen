package Halen3.Retrievers.Films.Trakt;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgBlueBgBlue;
import static Halen3.CommandLine.ColorCmd.fgBlueBgWhite;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgBlue;
import Halen3.IO.FileManager;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

/**
 * @author TAIBHSE
 */
public class UpdateTraktData
{

    public static void main(String args[]) throws IOException
    {
        updateFilmRules();
    }

    public static volatile boolean currentlyUpdatingFilmRules = true;

    public static void updateFilmRules() throws IOException
    {

        currentlyUpdatingFilmRules = true;

        Thread thread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {

                try
                {

                    currentlyUpdatingFilmRules = true;

                    String[] extensions = new String[]
                    {
                        "xml"
                    };
                    IOFileFilter filter = new SuffixFileFilter(extensions, IOCase.INSENSITIVE);

                    Iterator it = FileUtils.iterateFiles(new File(FileManager.launchPath() + "/rules/films"), filter, DirectoryFileFilter.DIRECTORY);

                    while (it.hasNext())
                    {

                        //  String url = "https://trakt.tv/shows/the-simpsons";
                        // String file = FileManager.launchPath() + "/rules/tv show/simpsons.xml";
                        String file = ((File) it.next()).getPath();

                        String url = FileManager.returnTag("url", FileManager.readFile(file).getItem(0));

                        //url = FileManager.readFile(file).getItem(0);
                        ColorCmd.println("", fgBlueBgBlue);
                        ColorCmd.printlnCenter("UPDATING RULE:  " + file.substring(file.lastIndexOf("\\") + 1, file.length()), fgWhiteBgBlue);
                        ColorCmd.println("", fgBlueBgBlue);
                        ColorCmd.println("", fgBlueBgWhite);
                        ColorCmd.println("" + file + " | " + url, fgBlueBgWhite);
                        ColorCmd.println("", fgBlueBgWhite);

                        try
                        {
                            String original = FileManager.readFile(file).getItem(0);
                            //skip rule if it is already downloaded, no point updating data as no longer relevant
                            if (FileManager.returnTag("retrieved", original).equals("true"))
                            {
                                ColorCmd.println("SKIPPING THIS RULE UPDATE AS FILM ALREADY DOWNLOADED.....", fgBlueBgWhite);
                                //System.out.println("SKIPPING THIS RULE UPDATE AS FILM ALREADY DOWNLOADED.....");
                            } else
                            {
                                ColorCmd.println("Proceeding to update rule.....", fgBlueBgWhite);

                                //  System.out.println("Proceeding to update rule...");
                                //get updated data from trakt.tv
                                String updated = Halen3.Retrievers.Films.Trakt.FilmTraktParser.getData(url).getItem(0);

                                //copy over user set data from old data, ie relocation settings
                                updated = updated + "<searchInFolder>" + FileManager.returnTag("searchInFolder", original) + "</searchInFolder>";
                                updated = updated + "<moveToFolder>" + FileManager.returnTag("moveToFolder", original) + "</moveToFolder>";
                                updated = updated + "<searchFor>" + FileManager.returnTag("searchFor", original) + "</searchFor>";

                                //======================================================================
                                PrintWriter out = new PrintWriter(file);
                                out.println(updated);
                                out.close();

                            }

                            ColorCmd.println("", fgBlueBgWhite);
                            ColorCmd.println("FINISHED UPDATING RULE " + file.substring(file.lastIndexOf("\\") + 1, file.length()), fgBlueBgWhite);
                            ColorCmd.println("", fgBlueBgWhite);
                            ColorCmd.println("", fgBlueBgWhite);
                        } catch (StringIndexOutOfBoundsException e)
                        {
                            ColorCmd.println("ERROR WITH RULE UPDATE:  " + file.substring(file.lastIndexOf("\\") + 1, file.length()) + "  CONSIDER DELETING AND REBUILDING RULE.....", fgRedBgWhite);
                            ColorCmd.println("", fgBlueBgWhite);
                        } catch (IOException ex)
                        {
                            Logger.getLogger(UpdateTraktData.class.getName()).log(Level.SEVERE, null, ex);
                            ColorCmd.println("ERROR WITH RULE UPDATE:  " + file.substring(file.lastIndexOf("\\") + 1, file.length()) + "  CONSIDER DELETING AND REBUILDING RULE.....", fgRedBgWhite);
                            ColorCmd.println("", fgBlueBgWhite);
                        }
                    }

                    currentlyUpdatingFilmRules = false;
                } catch (HeadlessException ex)
                {

                    currentlyUpdatingFilmRules = false;
                    System.out.println("CRITICAL ERROR: " + ex);

                }

            }
        });

        thread.start();
    }
}
