package Halen3.Retrievers.TvShows.Trakt;

import Halen3.GUI.NoticePanels.LoggingPanel;
import Halen3.IO.FileManager;
//import halen.GUI;
//import static halen.GUI.anim;
//import static halen.GUI.cb;
//import static halen.GUI.delete;
//import static halen.GUI.name;
//import static halen.GUI.ruleName;
//import static halen.GUI.rulesPane;
//import static halen.GUI.save;
//import static halen.GUI.search;
//import static halen.GUI.searchIn;
//import static halen.GUI.settings;
//import static halen.MetroUI.setTheme;
import java.awt.HeadlessException;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
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
        updateTvRules();
    }

    public static volatile boolean currentlyUpdatingTVRules = true;
    public static void updateTvRules() throws IOException
    {

        currentlyUpdatingTVRules = true;
        
        Thread thread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {

                try
                {
                    
                    currentlyUpdatingTVRules = true;

                    String[] extensions = new String[]
                    {
                        "xml"
                    };
                    IOFileFilter filter = new SuffixFileFilter(extensions, IOCase.INSENSITIVE);

                    Iterator it = FileUtils.iterateFiles(new File(FileManager.launchPath() + "/rules/tv show"), filter, DirectoryFileFilter.DIRECTORY);

                    while (it.hasNext())
                    {

                        //  String url = "https://trakt.tv/shows/the-simpsons";
                        // String file = FileManager.launchPath() + "/rules/tv show/simpsons.xml";
                        String file = ((File) it.next()).getPath();

                        String url = FileManager.returnTag("url", FileManager.readFile(file).getItem(0));

                        //url = FileManager.readFile(file).getItem(0);
                        System.out.println("#################################################################################################################");
                        System.out.println("\nUPDATING RULE:  " + file.substring(file.lastIndexOf("\\") + 1, file.length()));
                        System.out.println("\n" + file + "   |   " + url);

                        try
                        {
                            List original = FileManager.readFile(file);

                            List updated = Halen3.Retrievers.TvShows.Trakt.TraktParser.getData(url);

                            List output = new List();
                            //add first item that is search term
                            //output.add(original.getItem(0));
                            
                            if(!original.getItem(0).contains("<image>"))
                            {
                            output.add(original.getItem(0).replace(original.getItem(0).substring(original.getItem(0).indexOf("<search>"), original.getItem(0).indexOf("</url>")+6), updated.getItem(0)));
                            }else
                            {
                                 output.add(original.getItem(0).replace(original.getItem(0).substring(original.getItem(0).indexOf("<search>"), original.getItem(0).indexOf("</image>")+8), updated.getItem(0)));
                           
                            }
                            // ArrayList list = new ArrayList();

                            System.out.println(original.getItem(0));
                            System.out.println(updated.getItem(0));
                            
                            
                            
                            //Collections.sort(list);
                            //new episodes or season added
                            if (original.getItemCount() < updated.getItemCount())
                            {

                                //identify if a new seasons been added 
                               // int origMaxSeason = Integer.parseInt(original.getItem(original.getItemCount() - 1).substring(original.getItem(original.getItemCount() - 1).indexOf("S") + 1, original.getItem(original.getItemCount() - 1).indexOf("E")));
                              //  int updMaxSeason = Integer.parseInt(updated.getItem(updated.getItemCount() - 1).substring(updated.getItem(updated.getItemCount() - 1).indexOf("S") + 1, updated.getItem(original.getItemCount() - 1).indexOf("E")));

                                for (int i = 1; i < updated.getItemCount(); i++)
                                {
                                    //add original list to keep track of downloaded state
                                    if (i < original.getItemCount())
                                    {
                                        output.add(original.getItem(i));
                                    } //add new seasons after end of original list
                                    else
                                    {
                                        output.add(updated.getItem(i));
                                    }
                                }

                                //loop through and update release dates
                                for (int i = 1; i < output.getItemCount(); i++)
                                {

                                    String updateDate = FileManager.updateTag("release", output.getItem(i), FileManager.returnTag("release", updated.getItem(i)));

                                    output.replaceItem(updateDate, i);
                                }

                            }//equal but still ckeck for updated release dates
                            else if (original.getItemCount() == updated.getItemCount())
                            {
                                //loop through and update release dates
                                for (int i = 1; i < original.getItemCount(); i++)
                                {

                                    String updateDate = FileManager.updateTag("release", original.getItem(i), FileManager.returnTag("release", updated.getItem(i)));

                                    output.add(updateDate);
                                }
                            }

                            //======================================================================
                            PrintWriter out = new PrintWriter(file);
                            for (int i = 0; i < output.getItemCount(); i++)
                            {
                                out.println(output.getItem(i));
                            }

                            out.close();

                            System.out.println("\nFINISHED UPDATING RULE " + file.substring(file.lastIndexOf("\\") + 1, file.length()) + "\n\n#################################################################################################################");

                        } catch (StringIndexOutOfBoundsException e)
                        {
                            System.out.println("\nERROR WITH RULE:  " + file.substring(file.lastIndexOf("\\") + 1, file.length()) + "  CONSIDER DELETING AND REBUILDING RULE\n");
                        }
                    }

                    
//                    try
//                    {
//                        //restore gui
//                        anim.setVisible(false);
//                        rulesPane.setVisible(true);
//                        save.setVisible(true);
//                        cb.setVisible(true);
//                        GUI.run.setVisible(true);
//                        ruleName.setVisible(true);
//                        name.setVisible(true);
//                        search.setVisible(true);
//                        searchIn.setVisible(true);
//                        episodeListPane.setVisible(true);
//                        delete.setVisible(true);
//                        inputsPane.setVisible(true);
//                        settings.setVisible(true);
//                        setTheme.setVisible(true);
//                        GUI.trakt.setVisible(true);
//                        GUI.updateRulesData.setVisible(true);
//                    } catch (NullPointerException e)
//                    {
//                        System.out.println("No UI to reload, trakt update finished");
//                    }

                    currentlyUpdatingTVRules = false;
                } catch (IOException | HeadlessException ex)
                {
                    
                    
                    currentlyUpdatingTVRules = false;
                    System.out.println("CRITICAL ERROR: " + ex);
                    
//                          //restore gui
//                        anim.setVisible(false);
//                        rulesPane.setVisible(true);
//                        save.setVisible(true);
//                        cb.setVisible(true);
//                        GUI.run.setVisible(true);
//                        ruleName.setVisible(true);
//                        name.setVisible(true);
//                        search.setVisible(true);
//                        searchIn.setVisible(true);
//                        episodeListPane.setVisible(true);
//                        delete.setVisible(true);
//                        inputsPane.setVisible(true);
//                        settings.setVisible(true);
//                        setTheme.setVisible(true);
//                        GUI.trakt.setVisible(true);
//                        GUI.updateRulesData.setVisible(true);
                }

            }
        });

        thread.start();
    }
}
