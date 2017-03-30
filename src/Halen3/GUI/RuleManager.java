/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI;

import Halen3.GUI.Anime.AnimeGUI;
import static Halen3.GUI.Anime.AnimeGUI.refreshAnimePanel;
import Halen3.GUI.Comics.ComicsGUI;
import static Halen3.GUI.Comics.ComicsGUI.refreshComicsPanel;
import Halen3.GUI.Film.FilmGUI;
import static Halen3.GUI.Film.FilmGUI.refreshFilmPanel;
import Halen3.GUI.TV.TvGUI;
import static Halen3.GUI.TV.TvGUI.refreshTvPanel;
import Halen3.IO.FileManager;
import Halen3.IO.GlobalSharedVariables;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author brenn
 */
public class RuleManager
{

    public static void saveTVRule(String ruleName)
    {
        //########################################################################
        //                   update existing rule
        //########################################################################
        if (new File(Halen3.IO.FileManager.launchPath() + "/rules/tv show/" + ruleName.trim() + ".xml").exists())
        {
            //only update first line
            int reply = JOptionPane.showConfirmDialog(null, "THIS RULE ALREADY EXISTS! \nCLICK YES IF YOU WISH TO COMMIT CHANGES.", "RULE ALREADY EXISTS", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {

                System.out.println("SAVING OVER EXISTING RULE FILE....."); //if file exists then edit existing file

                //get list of tags
                List update = FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/tv show/" + ruleName.trim() + ".xml");
                //get line one with tags
                String updateLine = update.getItem(0);
                //message 
                String message = "DATA VALID";

                //#############################################################################
                //                    go through tags and check if valid
                //#############################################################################
                if (!ruleName.equals("") | !ruleName.equals("..."))
                {
                    //remove any illegal characters
                    ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();

                } else
                {
                    message = "DATA INVALID - please provide a valid rule name!";
                }

                if (!TvGUI.search.getText().equals("") | !TvGUI.search.getText().equals("..."))
                {

                    // ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();
                    //this is the name used to save the make sure its still valid
                    //tag - source  - new value
                    updateLine = FileManager.updateTag("search", updateLine, TvGUI.search.getText());
                } else
                {
                    message = "DATA INVALID - please provide valid search terms!";
                }

                if (!TvGUI.traktURLInput.getText().equals("") | !TvGUI.traktURLInput.getText().equals("..."))
                {

                    if (TvGUI.traktURLInput.getText().contains("trakt.tv/shows"))
                    {
                        //tag - source  - new value
                        updateLine = FileManager.updateTag("url", updateLine, TvGUI.traktURLInput.getText());
                    } else
                    {
                        message = "DATA INVALID - please provide a valid trakt link!";
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid trakt link!";
                }

                if (!TvGUI.searchInFolderText.getText().equals("") | !TvGUI.searchInFolderText.getText().equals("..."))
                {

                    if (new File(TvGUI.searchInFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        updateLine = FileManager.updateTag("searchInFolder", updateLine, TvGUI.searchInFolderText.getText());
                    } else
                    {
                        message = "DATA INVALID - please provide a valid search in directory!";
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid search in directory!";
                }

                if (!TvGUI.moveToFolderText.getText().equals("") | !TvGUI.moveToFolderText.getText().equals("..."))
                {

                    if (new File(TvGUI.moveToFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        updateLine = FileManager.updateTag("moveToFolder", updateLine, TvGUI.moveToFolderText.getText());
                    } else
                    {
                        new File(TvGUI.moveToFolderText.getText()).mkdirs();
                        if (new File(TvGUI.moveToFolderText.getText()).exists())
                        {
                            //tag - source  - new value
                            updateLine = FileManager.updateTag("moveToFolder", updateLine, TvGUI.moveToFolderText.getText());

                        } else
                        {
                            message = "DATA INVALID - provided move to directory does not exist nor can it be created!";
                        }

                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid move to directory directory!";
                }

                if (!TvGUI.searchForText.getText().equals("") | !TvGUI.searchForText.getText().equals("..."))
                {
                    //tag - source  - new value
                    updateLine = FileManager.updateTag("searchFor", updateLine, TvGUI.searchForText.getText());

                } else
                {
                    message = "DATA INVALID - please provide valid search text!";
                }

                //offer warning that folder checks on search in and move to folders 
                //are disabled, proceed with caution as this can cause drive damage
                if (GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                {

                    String[] options = new String[]
                    {
                        "Yes Proceed", "No Don't"
                    };
                    int response = JOptionPane.showOptionDialog(null, "<html><body><p style='width: 500px;'>Warning - Code to check that set search in folder and move to folders both exist has been disabled. Do you wish to proceed with saving this rule? Be aware that saving a rule with invalid set folders can cause damae to files on this drive as halen may move files unintentionally or may mix files into the wrong location.</p></body></html>", "CRITICAL WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (response == 1)
                    {
                        message = "DATA INVALID - please provide valid search text!";

                    }
                }
                //#############################################################################
                //                              save out rule
                //#############################################################################
                if (message.contains("DATA VALID"))
                {

                    PrintWriter out = null;
                    try
                    {
                        //replace line one with updated tags
                        update.replaceItem(updateLine, 0);
                        //where to save rule
                        out = new PrintWriter(Halen3.IO.FileManager.launchPath() + "/rules/tv show/" + ruleName.trim() + ".xml");
                        //loop and print lines
                        for (int i = 0; i < update.getItemCount(); i++)
                        {
                            out.println(update.getItem(i));
                        }
                        //close writer on end
                        out.close();

                    } catch (FileNotFoundException ex)
                    {
                        Logger.getLogger(RuleManager.class.getName()).log(Level.SEVERE, null, ex);
                    } finally
                    {
                        out.close();
                    }

                } else
                {
                    System.out.println(message);
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.WARNING_MESSAGE);
                }

                //end
            }
        } else
        {
            //########################################################################
            //                   create new rule
            //########################################################################
            //create new rule entirely from scratch
            //only update first line
            int reply = JOptionPane.showConfirmDialog(null, "CLICK YES IF YOU WISH \nTO SAVE THIS NEW RULE.", "CREATE NEW RULE", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {

                System.out.println("SAVING NEW RULE FILE....."); //if file exists then edit existing file
                String tags = "";
                List episodesAndFirstLineTraktData = null;
                String message = "DATA VALID";

                //#############################################################################
                //                    go through tags and check if valid
                //#############################################################################
                if (!ruleName.equals("") | !ruleName.equals("..."))
                {
                    //remove any illegal characters
                    ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();

                } else
                {
                    message = "DATA INVALID - please provide a valid rule name!";
                }

                if (!TvGUI.search.getText().equals("") | !TvGUI.search.getText().equals("..."))
                {

                    // ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();
                    //this is the name used to save the make sure its still valid
                    //tag - source  - new value
                    tags = tags + FileManager.makeTag("search", TvGUI.search.getText());
                } else
                {
                    message = "DATA INVALID - please provide valid search terms!";
                }

                if (!TvGUI.traktURLInput.getText().equals("") | !TvGUI.traktURLInput.getText().equals("..."))
                {

                    if (TvGUI.traktURLInput.getText().contains("trakt.tv/shows"))
                    {
                        //tag - source  - new value
                        tags = tags + FileManager.makeTag("url", TvGUI.traktURLInput.getText());
                        try
                        {
                            episodesAndFirstLineTraktData = Halen3.Retrievers.TvShows.Trakt.TvTraktParser.getData(TvGUI.traktURLInput.getText());
                        } catch (IOException ex)
                        {
                            Logger.getLogger(RuleManager.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else
                    {
                        message = "DATA INVALID - please provide a valid trakt link!";
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid trakt link!";
                }

                if (!TvGUI.searchInFolderText.getText().equals("") | !TvGUI.searchInFolderText.getText().equals("..."))
                {

                    if (new File(TvGUI.searchInFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        tags = FileManager.makeTag("searchInFolder", TvGUI.searchInFolderText.getText());
                    } else
                    {
                        message = "DATA INVALID - please provide a valid search in directory!";
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid search in directory!";
                }

                if (!TvGUI.moveToFolderText.getText().equals("") | !TvGUI.moveToFolderText.getText().equals("..."))
                {

                    if (new File(TvGUI.moveToFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        tags = tags + FileManager.makeTag("moveToFolder", TvGUI.moveToFolderText.getText());
                    } else
                    {

                        new File(TvGUI.moveToFolderText.getText()).mkdirs();

                        if (new File(TvGUI.moveToFolderText.getText()).exists())
                        {
                            //tag - source  - new value
                            tags = tags + FileManager.makeTag("moveToFolder", TvGUI.moveToFolderText.getText());
                        } else
                        {
                            message = "DATA INVALID - provided move to directory does not exist nor can it be created!";
                        }

                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid move to directory!";
                }

                if (!TvGUI.searchForText.getText().equals("") | !TvGUI.searchForText.getText().equals("..."))
                {
                    tags = tags + FileManager.makeTag("searchFor", TvGUI.searchForText.getText());
                } else
                {
                    message = "DATA INVALID - please provide valid search text!";
                }

                //offer warning that folder checks on search in and move to folders 
                //are disabled, proceed with caution as this can cause drive damage
                if (GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                {

                    String[] options = new String[]
                    {
                        "Yes Proceed", "No Don't"
                    };
                    int response = JOptionPane.showOptionDialog(null, "<html><body><p style='width: 500px;'>Warning - Code to check that set search in folder and move to folders both exist has been disabled. Do you wish to proceed with saving this rule? Be aware that saving a rule with invalid set folders can cause damae to files on this drive as halen may move files unintentionally or may mix files into the wrong location.</p></body></html>", "CRITICAL WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (response == 1)
                    {
                        message = "DATA INVALID - please provide valid search text!";

                    }
                }
                //#############################################################################
                //                              save out rule
                //#############################################################################
                if (message.contains("DATA VALID"))
                {

                    PrintWriter out = null;
                    try
                    {
                        //replace line one with updated tags
                        episodesAndFirstLineTraktData.replaceItem(episodesAndFirstLineTraktData.getItem(0) + tags, 0);
                        //where to save rule
                        out = new PrintWriter(Halen3.IO.FileManager.launchPath() + "/rules/tv show/" + ruleName.trim() + ".xml");
                        //loop and print lines
                        for (int i = 0; i < episodesAndFirstLineTraktData.getItemCount(); i++)
                        {
                            out.println(episodesAndFirstLineTraktData.getItem(i));
                        }
                        //close writer on end
                        out.close();

                    } catch (FileNotFoundException ex)
                    {
                        Logger.getLogger(RuleManager.class.getName()).log(Level.SEVERE, null, ex);
                    } finally
                    {
                        out.close();
                    }

                } else
                {
                    System.out.println(message);
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.WARNING_MESSAGE);
                }

                refreshTvPanel();

                //end
            }
        }
    }

    public static void saveFilmRule(String ruleName)
    {
        //########################################################################
        //                   update existing rule
        //########################################################################
        if (new File(Halen3.IO.FileManager.launchPath() + "/rules/films/" + ruleName.trim() + ".xml").exists())
        {
            //only update first line
            int reply = JOptionPane.showConfirmDialog(null, "THIS RULE ALREADY EXISTS! \nCLICK YES IF YOU WISH TO COMMIT CHANGES.", "RULE ALREADY EXISTS", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {

                System.out.println("SAVING OVER EXISTING RULE FILE....."); //if file exists then edit existing file

                //get list of tags
                List update = FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/films/" + ruleName.trim() + ".xml");
                //get line one with tags
                String updateLine = update.getItem(0);
                //message 
                String message = "DATA VALID";

                //#############################################################################
                //                    go through tags and check if valid
                //#############################################################################
                if (!ruleName.equals("") | !ruleName.equals("..."))
                {
                    //remove any illegal characters
                    ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();

                } else
                {
                    message = "DATA INVALID - please provide a valid rule name!";
                }

                if (!FilmGUI.search.getText().equals("") | !FilmGUI.search.getText().equals("..."))
                {

                    // ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();
                    //this is the name used to save the make sure its still valid
                    //tag - source  - new value
                    updateLine = FileManager.updateTag("search", updateLine, FilmGUI.search.getText());
                } else
                {
                    message = "DATA INVALID - please provide valid search terms!";
                }

                if (!FilmGUI.traktURLInput.getText().equals("") | !FilmGUI.traktURLInput.getText().equals("..."))
                {

                    if (FilmGUI.traktURLInput.getText().contains("trakt.tv/movies"))
                    {
                        //tag - source  - new value
                        updateLine = FileManager.updateTag("url", updateLine, FilmGUI.traktURLInput.getText());
                    } else
                    {
                        message = "DATA INVALID - please provide a valid trakt link!";
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid trakt link!";
                }

                if (!FilmGUI.searchInFolderText.getText().equals("") | !FilmGUI.searchInFolderText.getText().equals("..."))
                {

                    if (new File(FilmGUI.searchInFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        updateLine = FileManager.updateTag("searchInFolder", updateLine, FilmGUI.searchInFolderText.getText());
                    } else
                    {
                        message = "DATA INVALID - please provide a valid search in directory!";
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid search in directory!";
                }

                if (!FilmGUI.moveToFolderText.getText().equals("") | !FilmGUI.moveToFolderText.getText().equals("..."))
                {

                    if (new File(FilmGUI.moveToFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        updateLine = FileManager.updateTag("moveToFolder", updateLine, FilmGUI.moveToFolderText.getText());
                    } else
                    {
                        new File(FilmGUI.moveToFolderText.getText()).mkdirs();
                        if (new File(FilmGUI.moveToFolderText.getText()).exists())
                        {
                            //tag - source  - new value
                            updateLine = FileManager.updateTag("moveToFolder", updateLine, FilmGUI.moveToFolderText.getText());

                        } else
                        {
                            message = "DATA INVALID - provided move to directory does not exist nor can it be created!";
                        }

                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid move to directory directory!";
                }

                if (!FilmGUI.searchForText.getText().equals("") | !FilmGUI.searchForText.getText().equals("..."))
                {
                    //tag - source  - new value
                    updateLine = FileManager.updateTag("searchFor", updateLine, FilmGUI.searchForText.getText());

                } else
                {
                    message = "DATA INVALID - please provide valid search text!";
                }

                //offer warning that folder checks on search in and move to folders 
                //are disabled, proceed with caution as this can cause drive damage
                if (GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                {

                    String[] options = new String[]
                    {
                        "Yes Proceed", "No Don't"
                    };
                    int response = JOptionPane.showOptionDialog(null, "<html><body><p style='width: 500px;'>Warning - Code to check that set search in folder and move to folders both exist has been disabled. Do you wish to proceed with saving this rule? Be aware that saving a rule with invalid set folders can cause damae to files on this drive as halen may move files unintentionally or may mix files into the wrong location.</p></body></html>", "CRITICAL WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (response == 1)
                    {
                        message = "DATA INVALID - please provide valid search text!";

                    }
                }

                //#############################################################################
                //                              save out rule
                //#############################################################################
                if (message.contains("DATA VALID"))
                {

                    PrintWriter out = null;
                    try
                    {
                        //replace line one with updated tags
                        update.replaceItem(updateLine, 0);
                        //where to save rule
                        out = new PrintWriter(Halen3.IO.FileManager.launchPath() + "/rules/films/" + ruleName.trim() + ".xml");
                        //loop and print lines
                        for (int i = 0; i < update.getItemCount(); i++)
                        {
                            out.println(update.getItem(i));
                        }
                        //close writer on end
                        out.close();

                    } catch (FileNotFoundException ex)
                    {
                        Logger.getLogger(RuleManager.class.getName()).log(Level.SEVERE, null, ex);
                    } finally
                    {
                        out.close();
                    }

                } else
                {
                    System.out.println(message);
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.WARNING_MESSAGE);
                }

                //end
            }
        } else
        {
            //########################################################################
            //                   create new rule
            //########################################################################
            //create new rule entirely from scratch
            //only update first line
            int reply = JOptionPane.showConfirmDialog(null, "CLICK YES IF YOU WISH \nTO SAVE THIS NEW RULE.", "CREATE NEW RULE", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {

                System.out.println("SAVING NEW RULE FILE....."); //if file exists then edit existing file
                String tags = "";
                List firstLineTraktData = null;
                String message = "DATA VALID";

                //#############################################################################
                //                    go through tags and check if valid
                //#############################################################################
                if (!ruleName.equals("") | !ruleName.equals("..."))
                {
                    //remove any illegal characters
                    ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();

                } else
                {
                    message = "DATA INVALID - please provide a valid rule name!";
                }

                if (!FilmGUI.search.getText().equals("") | !FilmGUI.search.getText().equals("..."))
                {

                    // ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();
                    //this is the name used to save the make sure its still valid
                    //tag - source  - new value
                    tags = tags + FileManager.makeTag("search", FilmGUI.search.getText());
                } else
                {
                    message = "DATA INVALID - please provide valid search terms!";
                }

                if (!FilmGUI.traktURLInput.getText().equals("") | !FilmGUI.traktURLInput.getText().equals("..."))
                {

                    if (FilmGUI.traktURLInput.getText().contains("trakt.tv/movies"))
                    {
                        //tag - source  - new value
                        tags = tags + FileManager.makeTag("url", FilmGUI.traktURLInput.getText());
                        try
                        {
                            firstLineTraktData = Halen3.Retrievers.Films.Trakt.FilmTraktParser.getData(FilmGUI.traktURLInput.getText());
                        } catch (IOException ex)
                        {
                            Logger.getLogger(RuleManager.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else
                    {
                        message = "DATA INVALID - please provide a valid trakt link!";
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid trakt link!";
                }

                if (!FilmGUI.searchInFolderText.getText().equals("") | !FilmGUI.searchInFolderText.getText().equals("..."))
                {

                    if (new File(FilmGUI.searchInFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        tags = FileManager.makeTag("searchInFolder", FilmGUI.searchInFolderText.getText());
                    } else
                    {
                        message = "DATA INVALID - please provide a valid search in directory!";
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid search in directory!";
                }

                if (!FilmGUI.moveToFolderText.getText().equals("") | !FilmGUI.moveToFolderText.getText().equals("..."))
                {

                    if (new File(FilmGUI.moveToFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        tags = tags + FileManager.makeTag("moveToFolder", FilmGUI.moveToFolderText.getText());
                    } else
                    {

                        new File(FilmGUI.moveToFolderText.getText()).mkdirs();

                        if (new File(FilmGUI.moveToFolderText.getText()).exists())
                        {
                            //tag - source  - new value
                            tags = tags + FileManager.makeTag("moveToFolder", FilmGUI.moveToFolderText.getText());
                        } else
                        {
                            message = "DATA INVALID - provided move to directory does not exist nor can it be created!";
                        }

                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid move to directory!";
                }

                if (!FilmGUI.searchForText.getText().equals("") | !FilmGUI.searchForText.getText().equals("..."))
                {
                    tags = tags + FileManager.makeTag("searchFor", FilmGUI.searchForText.getText());
                } else
                {
                    message = "DATA INVALID - please provide valid search text!";
                }

                //offer warning that folder checks on search in and move to folders 
                //are disabled, proceed with caution as this can cause drive damage
                if (GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                {

                    String[] options = new String[]
                    {
                        "Yes Proceed", "No Don't"
                    };
                    int response = JOptionPane.showOptionDialog(null, "<html><body><p style='width: 500px;'>Warning - Code to check that set search in folder and move to folders both exist has been disabled. Do you wish to proceed with saving this rule? Be aware that saving a rule with invalid set folders can cause damae to files on this drive as halen may move files unintentionally or may mix files into the wrong location.</p></body></html>", "CRITICAL WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (response == 1)
                    {
                        message = "DATA INVALID - please provide valid search text!";

                    }
                }

                //#############################################################################
                //                              save out rule
                //#############################################################################
                if (message.contains("DATA VALID"))
                {

                    PrintWriter out = null;
                    try
                    {
                        //replace line one with updated tags
                        firstLineTraktData.replaceItem(firstLineTraktData.getItem(0) + tags, 0);
                        //where to save rule
                        out = new PrintWriter(Halen3.IO.FileManager.launchPath() + "/rules/films/" + ruleName.trim() + ".xml");
                        //loop and print lines
                        for (int i = 0; i < firstLineTraktData.getItemCount(); i++)
                        {
                            out.println(firstLineTraktData.getItem(i));
                        }
                        //close writer on end
                        out.close();

                    } catch (FileNotFoundException ex)
                    {
                        Logger.getLogger(RuleManager.class.getName()).log(Level.SEVERE, null, ex);
                    } finally
                    {
                        out.close();
                    }

                } else
                {
                    System.out.println(message);
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.WARNING_MESSAGE);
                }

                refreshFilmPanel();

                //end
            }
        }
    }

    public static void saveComicRule(String ruleName) throws InterruptedException, IOException
    {
        System.out.println(ruleName.trim() + ".xml");
        //########################################################################
        //                   update existing rule
        //########################################################################
        if (new File(Halen3.IO.FileManager.launchPath() + "/rules/comics/" + ruleName.trim() + ".xml").exists())
        {
            //only update first line
            int reply = JOptionPane.showConfirmDialog(null, "THIS RULE ALREADY EXISTS! \nCLICK YES IF YOU WISH TO COMMIT CHANGES.", "RULE ALREADY EXISTS", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {

                System.out.println("SAVING OVER EXISTING RULE FILE....."); //if file exists then edit existing file

                //get list of tags
                List update = FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/comics/" + ruleName.trim() + ".xml");
                //get line one with tags
                String updateLine = update.getItem(0);
                //message 
                String message = "DATA VALID";

                //#############################################################################
                //                    go through tags and check if valid
                //#############################################################################
                if (!ruleName.equals("") | !ruleName.equals("..."))
                {
                    //remove any illegal characters
                    ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();

                } else
                {
                    message = "DATA INVALID - please provide a valid rule name!";
                }

                if (!ComicsGUI.comicURLInput.getText().equals("") | !ComicsGUI.comicURLInput.getText().equals("..."))
                {

                    if (ComicsGUI.comicURLInput.getText().contains("readcomiconline.to/Comic/") | ComicsGUI.comicURLInput.getText().contains("kissmanga.com/Manga/"))
                    {
                        //tag - source  - new value
                        updateLine = FileManager.updateTag("url", updateLine, ComicsGUI.comicURLInput.getText());
                    } else
                    {
                        message = "DATA INVALID - please provide a valid readcomiconline or kissmanga url!";
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid readcomiconline or kissmanga url!";
                }

                if (!ComicsGUI.DownloadToFolderText.getText().equals("") | !ComicsGUI.DownloadToFolderText.getText().equals("..."))
                {

                    if (new File(ComicsGUI.DownloadToFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        updateLine = FileManager.updateTag("downloadTo", updateLine, ComicsGUI.DownloadToFolderText.getText());
                    } else
                    {
                        new File(ComicsGUI.DownloadToFolderText.getText()).mkdirs();
                        if (new File(ComicsGUI.DownloadToFolderText.getText()).exists())
                        {
                            updateLine = FileManager.updateTag("downloadTo", updateLine, ComicsGUI.DownloadToFolderText.getText());
                        } else
                        {
                            message = "DATA INVALID - provided path does not exist nor can it be created!";
                        }
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid folder to download comics too!";
                }

                //offer warning that folder checks on search in and move to folders 
                //are disabled, proceed with caution as this can cause drive damage
                if (GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                {

                    String[] options = new String[]
                    {
                        "Yes Proceed", "No Don't"
                    };
                    int response = JOptionPane.showOptionDialog(null, "<html><body><p style='width: 500px;'>Warning - Code to check that set search in folder and move to folders both exist has been disabled. Do you wish to proceed with saving this rule? Be aware that saving a rule with invalid set folders can cause damae to files on this drive as halen may move files unintentionally or may mix files into the wrong location.</p></body></html>", "CRITICAL WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (response == 1)
                    {
                        message = "DATA INVALID - please provide valid search text!";

                    }
                }

                //#############################################################################
                //                              save out rule
                //#############################################################################
                if (message.contains("DATA VALID"))
                {

                    PrintWriter out = null;
                    try
                    {
                        //replace line one with updated tags
                        update.replaceItem(updateLine, 0);
                        //where to save rule
                        out = new PrintWriter(Halen3.IO.FileManager.launchPath() + "/rules/comics/" + ruleName.trim() + ".xml");
                        //loop and print lines
                        for (int i = 0; i < update.getItemCount(); i++)
                        {
                            out.println(update.getItem(i));
                        }
                        //close writer on end
                        out.close();

                    } catch (FileNotFoundException ex)
                    {
                        Logger.getLogger(RuleManager.class.getName()).log(Level.SEVERE, null, ex);
                    } finally
                    {
                        out.close();
                    }

                } else
                {
                    System.out.println(message);
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.WARNING_MESSAGE);
                }

                //end
            }
        } else
        {
            //########################################################################
            //                   create new rule
            //########################################################################
            //create new rule entirely from scratch
            //only update first line
            int reply = JOptionPane.showConfirmDialog(null, "CLICK YES IF YOU WISH \nTO SAVE THIS NEW RULE.", "CREATE NEW RULE", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {

                System.out.println("SAVING NEW RULE FILE....."); //if file exists then edit existing file
                String tags = "";
                List issues = null;
                String message = "DATA VALID";

                //#############################################################################
                //                    go through tags and check if valid
                //#############################################################################
                if (!ruleName.equals("") | !ruleName.equals("..."))
                {
                    //remove any illegal characters
                    ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();

                } else
                {
                    message = "DATA INVALID - please provide a valid rule name!";
                }

                if (!ComicsGUI.comicURLInput.getText().equals("") | !ComicsGUI.comicURLInput.getText().equals("..."))
                {

                    if (ComicsGUI.comicURLInput.getText().contains("readcomiconline.to/Comic/") | ComicsGUI.comicURLInput.getText().contains("kissmanga.com/Manga/"))
                    {
                        //tag - source  - new value
                     //   tags = tags + FileManager.makeTag("url", ComicsGUI.comicURLInput.getText());

                    } else
                    {
                        message = "DATA INVALID - please provide a valid readcomiconline or kissmanga url!";
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid readcomiconline or kissmanga url!";
                }

                if (!ComicsGUI.DownloadToFolderText.getText().equals("") | !ComicsGUI.DownloadToFolderText.getText().equals("..."))
                {

                    if (new File(ComicsGUI.DownloadToFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                       // tags = tags + FileManager.makeTag("moveToFolder", ComicsGUI.DownloadToFolderText.getText());
                    } else
                    {
                        new File(ComicsGUI.DownloadToFolderText.getText()).mkdirs();
                        if (new File(ComicsGUI.DownloadToFolderText.getText()).exists())
                        {
                            //tag - source  - new value
                          //  tags = tags + FileManager.makeTag("downloadTo", ComicsGUI.DownloadToFolderText.getText());
                        } else
                        {
                            message = "DATA INVALID - provided download directory does not exist nor can it be created!";
                        }

                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid move to directory directory!";
                }

                //offer warning that folder checks on search in and move to folders 
                //are disabled, proceed with caution as this can cause drive damage
                if (GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                {

                    String[] options = new String[]
                    {
                        "Yes Proceed", "No Don't"
                    };
                    int response = JOptionPane.showOptionDialog(null, "<html><body><p style='width: 500px;'>Warning - Code to check that set search in folder and move to folders both exist has been disabled. Do you wish to proceed with saving this rule? Be aware that saving a rule with invalid set folders can cause damae to files on this drive as halen may move files unintentionally or may mix files into the wrong location.</p></body></html>", "CRITICAL WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (response == 1)
                    {
                        message = "DATA INVALID - please provide valid search text!";

                    }
                }
                //#############################################################################
                //                              save out rule
                //#############################################################################
                if (message.contains("DATA VALID"))
                {

                    // PrintWriter out = null;
                    try
                    {
                        //replace line one with updated tags
                        Halen3.Retrievers.Comics.CreateComicRule.saveNewSeries(ComicsGUI.comicURLInput.getText(), ruleName, ComicsGUI.DownloadToFolderText.getText());
                        //where to save rule
//                        out = new PrintWriter(halen.FileManager.launchPath() + "/rules/tv show/" + ruleName.trim() + ".xml");
//                        //loop and print lines
//                        for (int i = 0; i < issues.getItemCount(); i++)
//                        {
//                            out.println(issues.getItem(i));
//                        }
//                        //close writer on end
//                        out.close();

                    } catch (FileNotFoundException ex)
                    {
                        System.out.println(ex);
                        Logger.getLogger(RuleManager.class.getName()).log(Level.SEVERE, null, ex);
                    } //finally
//                    {
//                        out.close();
//                    }

                } else
                {
                    System.out.println(message);
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.WARNING_MESSAGE);
                }

                refreshComicsPanel();
                //end

            }
        }
    }

    public static void saveAnimeRule(String ruleName) throws InterruptedException, IOException
    {
        System.out.println(ruleName.trim() + ".xml");
        //########################################################################
        //                   update existing rule
        //########################################################################
        if (new File(Halen3.IO.FileManager.launchPath() + "/rules/anime/" + ruleName.trim() + ".xml").exists())
        {
            //only update first line
            int reply = JOptionPane.showConfirmDialog(null, "THIS RULE ALREADY EXISTS! \nCLICK YES IF YOU WISH TO COMMIT CHANGES.", "RULE ALREADY EXISTS", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {

                System.out.println("SAVING OVER EXISTING RULE FILE....."); //if file exists then edit existing file

                //get list of tags
                List update = FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/anime/" + ruleName.trim() + ".xml");
                //get line one with tags
                String updateLine = update.getItem(0);
                //message 
                String message = "DATA VALID";

                //#############################################################################
                //                    go through tags and check if valid
                //#############################################################################
                if (!ruleName.equals("") | !ruleName.equals("..."))
                {
                    //remove any illegal characters
                    ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();

                } else
                {
                    message = "DATA INVALID - please provide a valid rule name!";
                }

                if (!AnimeGUI.animeURLInput.getText().equals("") | !AnimeGUI.animeURLInput.getText().equals("..."))
                {

                    if (AnimeGUI.animeURLInput.getText().contains("deadfishencod"))
                    {
                        //tag - source  - new value
                        updateLine = FileManager.updateTag("seriesURL", updateLine, AnimeGUI.animeURLInput.getText());
                    } else
                    {
                        message = "DATA INVALID - please provide a valid deadfishencod.es series url!";
                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid deadfishencod.es series url!";
                }

                if (!AnimeGUI.searchForText.getText().equals("") | !AnimeGUI.searchForText.getText().equals("..."))
                {
                    updateLine = FileManager.updateTag("searchFor", updateLine, AnimeGUI.searchForText.getText());

                } else
                {
                    message = "DATA INVALID - please enter search for text!";

                }
                if (!AnimeGUI.moveToFolderText.getText().equals("") | !AnimeGUI.moveToFolderText.getText().equals("..."))
                {

                    if (new File(AnimeGUI.moveToFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        updateLine = FileManager.updateTag("moveToFolder", updateLine, AnimeGUI.moveToFolderText.getText());
                    } else
                    {
                        new File(AnimeGUI.moveToFolderText.getText()).mkdirs();
                        if (new File(AnimeGUI.moveToFolderText.getText()).exists())
                        {
                            updateLine = FileManager.updateTag("moveToFolder", updateLine, AnimeGUI.moveToFolderText.getText());
                        } else
                        {
                            message = "DATA INVALID - 002 - provided path does not exist nor can it be created!";
                        }
                    }

                } else
                {
                    message = "DATA INVALID - 001 - please provide a valid folder to move anime episodes too!";
                }

                if (!AnimeGUI.searchInFolderText.getText().equals("") | !AnimeGUI.searchInFolderText.getText().equals("..."))
                {

                    if (new File(AnimeGUI.searchInFolderText.getText()).exists() | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        updateLine = FileManager.updateTag("searchInFolder", updateLine, AnimeGUI.searchInFolderText.getText());
                    } else
                    {

                        message = "DATA INVALID - 002 - provided search in path does not exist!";

                    }

                } else
                {
                    message = "DATA INVALID - 001 - please provide a valid search in folder!";
                }

                //offer warning that folder checks on search in and move to folders 
                //are disabled, proceed with caution as this can cause drive damage
                if (GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                {

                    String[] options = new String[]
                    {
                        "Yes Proceed", "No Don't"
                    };
                    int response = JOptionPane.showOptionDialog(null, "<html><body><p style='width: 500px;'>Warning - Code to check that set search in folder and move to folders both exist has been disabled. Do you wish to proceed with saving this rule? Be aware that saving a rule with invalid set folders can cause damae to files on this drive as halen may move files unintentionally or may mix files into the wrong location.</p></body></html>", "CRITICAL WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (response == 1)
                    {
                        message = "DATA INVALID - please provide valid search text!";

                    }
                }
                //#############################################################################
                //                              save out rule
                //#############################################################################
                if (message.contains("DATA VALID"))
                {

                    PrintWriter out = null;
                    try
                    {
                        //replace line one with updated tags
                        update.replaceItem(updateLine, 0);
                        //where to save rule
                        out = new PrintWriter(Halen3.IO.FileManager.launchPath() + "/rules/anime/" + ruleName.trim() + ".xml");
                        //loop and print lines
                        for (int i = 0; i < update.getItemCount(); i++)
                        {
                            out.println(update.getItem(i));
                        }
                        //close writer on end
                        out.close();

                    } catch (FileNotFoundException ex)
                    {
                        Logger.getLogger(RuleManager.class.getName()).log(Level.SEVERE, null, ex);
                    } finally
                    {
                        out.close();
                    }

                } else
                {
                    System.out.println(message);
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.WARNING_MESSAGE);
                }

                //end
            }
        } else
        {
            //########################################################################
            //                   create new rule
            //########################################################################
            //create new rule entirely from scratch
            //only update first line
            int reply = JOptionPane.showConfirmDialog(null, "CLICK YES IF YOU WISH \nTO SAVE THIS NEW RULE.", "CREATE NEW RULE", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {

                System.out.println("SAVING NEW RULE FILE....."); //if file exists then edit existing file
                //  String tags = "";
                List issues = null;
                String message = "DATA VALID";

                System.out.println("X: " + ruleName);
                //#############################################################################
                //                    go through tags and check if valid
                //#############################################################################
                if (!(ruleName.equals("")) && !(ruleName.equals("...")))
                {
                    //remove any illegal characters
                    ruleName = ruleName.replaceAll("[^a-zA-Z0-9. -]", " ").replaceAll("\\s+", " ").trim();

                } else
                {
                    message = "DATA INVALID - please provide a valid rule name!";
                }

                if (!AnimeGUI.animeURLInput.getText().equals("") && !AnimeGUI.animeURLInput.getText().equals("..."))
                {

                    if (AnimeGUI.animeURLInput.getText().contains("deadfishencod"))
                    {
                        //tag - source  - new value
                        //  tags = tags + FileManager.makeTag("seriesURL", ComicsGUI.comicURLInput.getText());

                    } else
                    {
                        message = "DATA INVALID - 002 - please provide a valid deadfishencod.es series url!";
                    }

                } else
                {
                    message = "DATA INVALID - 001 - please provide a valid deadfishencod.es series url!";
                }

                if (!AnimeGUI.searchForText.getText().equals("") && !AnimeGUI.searchForText.getText().equals("..."))
                {

                } else
                {
                    message = "DATA INVALID - search for text cannot be blank!";

                }
                if (!AnimeGUI.searchForText.getText().equals("") && !AnimeGUI.searchForText.getText().equals("..."))
                {

                } else
                {
                    message = "DATA INVALID - please enter search for text!";
                }

                if (!AnimeGUI.searchInFolderText.getText().equals("") && !AnimeGUI.searchInFolderText.getText().equals("..."))
                {

                    if (new File(AnimeGUI.searchInFolderText.getText()).exists()  | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        // tags = tags + FileManager.makeTag("moveToFolder", ComicsGUI.DownloadToFolderText.getText());
                    } else
                    {

                        message = "DATA INVALID - provided search in directory does not exist!";

                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid search in directory!";
                }

                if (!AnimeGUI.moveToFolderText.getText().equals("") && !AnimeGUI.moveToFolderText.getText().equals("..."))
                {

                    if (new File(AnimeGUI.moveToFolderText.getText()).exists()  | GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                    {
                        //tag - source  - new value
                        // tags = tags + FileManager.makeTag("moveToFolder", ComicsGUI.DownloadToFolderText.getText());
                    } else
                    {
                        new File(AnimeGUI.moveToFolderText.getText()).mkdirs();
                        if (new File(AnimeGUI.moveToFolderText.getText()).exists())
                        {
                            //tag - source  - new value
                            // tags = tags + FileManager.makeTag("downloadTo", ComicsGUI.DownloadToFolderText.getText());
                        } else
                        {
                            message = "DATA INVALID - provided download directory does not exist nor can it be created!";
                        }

                    }

                } else
                {
                    message = "DATA INVALID - please provide a valid move to directory!";
                }

                     //offer warning that folder checks on search in and move to folders 
                //are disabled, proceed with caution as this can cause drive damage
                if (GlobalSharedVariables.bypassFolderValidationOnRuleSave.equals("true"))
                {

                    String[] options = new String[]
                    {
                        "Yes Proceed", "No Don't"
                    };
                    int response = JOptionPane.showOptionDialog(null, "<html><body><p style='width: 500px;'>Warning - Code to check that set search in folder and move to folders both exist has been disabled. Do you wish to proceed with saving this rule? Be aware that saving a rule with invalid set folders can cause damae to files on this drive as halen may move files unintentionally or may mix files into the wrong location.</p></body></html>", "CRITICAL WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (response == 1)
                    {
                        message = "DATA INVALID - please provide valid search text!";

                    }
                }
                
                //#############################################################################
                //                              save out rule
                //#############################################################################
                if (message.contains("DATA VALID"))
                {

                    // PrintWriter out = null;
                    try
                    {
                        //replace line one with updated tags
                        Halen3.Retrievers.Anime.CreateAnimeRule.saveNewRule(ruleName, AnimeGUI.animeURLInput.getText(), AnimeGUI.moveToFolderText.getText(), AnimeGUI.searchInFolderText.getText(), AnimeGUI.searchForText.getText());                  //.saveNewSeries(ComicsGUI.comicURLInput.getText(), ruleName, ComicsGUI.DownloadToFolderText.getText());
                        //where to save rule
//                        out = new PrintWriter(halen.FileManager.launchPath() + "/rules/tv show/" + ruleName.trim() + ".xml");
//                        //loop and print lines
//                        for (int i = 0; i < issues.getItemCount(); i++)
//                        {
//                            out.println(issues.getItem(i));
//                        }
//                        //close writer on end
//                        out.close();

                    } catch (FileNotFoundException ex)
                    {
                        Logger.getLogger(RuleManager.class.getName()).log(Level.SEVERE, null, ex);
                    } 

                } else
                {
                    System.out.println(message);
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.WARNING_MESSAGE);
                }

                refreshAnimePanel();
                //end

            }
        }
    }
}
