package Rules;

import halen.Controls;
import halen.FileManager;
import halen.GUI;
import static halen.GUI.cb;
import static halen.GUI.rulesListPanel;
import static halen.GUI.rulesScroll;
import static halen.MetroUI.createRuleButtons;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import static halen.MetroUI.episodeListEmptyPanel;

/**
 * @author TAIBHSE
 */
public class Save
{

    private static boolean editWithBlankSeason;

    public static void main(String args[])
    {
        // System.out.println(validData("comics", "tink tank", "search", "10", "2"));
        //   saveAsNew("comics", "name", "search", "10", "1");
    }

    /**
     * checks if all data is valid for saving
     *
     * @param cat
     * @param name
     * @param search
     * @param numberOrURL
     * @param season
     * @return
     */
    private static boolean validData(String cat, String name, String search, String numberOrURL, String searchInFolder, String moveToFolder, String searchFor)
    {
        boolean answer = false;
        Pattern namePattern = Pattern.compile("^[a-zA-Z0-9_. -]+$", Pattern.CASE_INSENSITIVE);
        Pattern numberPattern = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);
        Matcher nameMatch = namePattern.matcher(name);
        Matcher numMatch = numberPattern.matcher(numberOrURL);
        //  Matcher seaMatch = numberPattern.matcher(season);

        editWithBlankSeason = false;

        boolean nameValid = nameMatch.find();
        boolean numValid = numMatch.find();

        if (!(name.equals("...")) && !(name.trim().equals("")) && nameValid == true) //rule must have a valid name
        {

//            if (!(search.equals("...")) && !(search.trim().equals(""))) //search must have a valid entry
//            {
            if (cat.equals("tv show"))
            {

            //    if (!(search.equals("...")) && !(search.trim().equals(""))) //search must have a valid entry
              //  {
                    if (numberOrURL.toLowerCase().contains("trakt.tv"))
                    {
                     //   List test = FileManager.readFile(FileManager.launchPath() + "/rules/tv show/" + name + ".xml");

                      //  if (test.getItemCount() > 2)
                       // {
                        //  numValid = true;
                            //  seaValid = true;
                            editWithBlankSeason = true;

                         //   test.removeAll();
                            answer = true;
                       // //} else
                       // {
                        //    System.out.println("ENTER VALID URL");
                         //   JOptionPane.showMessageDialog(null, "ENTER VALID URL", "ERROR", JOptionPane.WARNING_MESSAGE);
                         //   test.removeAll();
                         //   return false;

                      //  }
                    } else
                    {
                        System.out.println("ENTER VALID TRAKT URL TO PARSE SHOW INFO!");
                        JOptionPane.showMessageDialog(null, "ENTER VALID TRAKT URL TO PARSE SHOW INFO!", "ERROR", JOptionPane.WARNING_MESSAGE);

                        return false;
                    }

            //    } else
            //    {

                 //   System.out.println("SEARCH PARAMETER MUST NOT BE NULL");
                //    JOptionPane.showMessageDialog(null, "SEARCH PARAMETER MUST NOT BE NULL", "ERROR", JOptionPane.WARNING_MESSAGE);
                //    return false;
             //   }

            } else

            {

                if (!(search.equals("...")) && !(search.trim().equals(""))) //search must have a valid entry
                {

                    if (cat.equals("anime"))
                    {
                        if (numValid == false)
                        {
                            List test = FileManager.readFile(FileManager.launchPath() + "/rules/anime/" + name + ".xml");

                            if (test.getItemCount() > 2)
                            {
                                //  numValid = true;
                                //  seaValid = true;
                                editWithBlankSeason = true;

                                test.removeAll();
                                answer = true;
                            } else
                            {
                                System.out.println("ENTER VALID NUMBERS!");
                                JOptionPane.showMessageDialog(null, "ENTER VALID NUMBERS!", "ERROR", JOptionPane.WARNING_MESSAGE);
                                return false;
                            }
                        }
                    } else if (cat.equals("comics"))
                    {
                        if (numValid == false)
                        {
                            List test = FileManager.readFile(FileManager.launchPath() + "/rules/comics/" + name + ".xml");

                            if (test.getItemCount() > 2)
                            {
                                //  numValid = true;
                                //  seaValid = true;
                                editWithBlankSeason = true;

                                test.removeAll();
                                answer = true;
                            } else
                            {
                                System.out.println("ENTER VALID NUMBERS!");
                                JOptionPane.showMessageDialog(null, "ENTER VALID NUMBERS!", "ERROR", JOptionPane.WARNING_MESSAGE);
                                return false;
                            }
                        }
                    }

                } else
                {
                    System.out.println("SEARCH PARAMETER MUST NOT BE NULL");
                    JOptionPane.showMessageDialog(null, "SEARCH PARAMETER MUST NOT BE NULL", "ERROR", JOptionPane.WARNING_MESSAGE);
                    return false;
                }

            }

        } else
        {
            System.out.println("NAME MUST BE VALID! ONLY CHARACTERS A-Z a-z 0-9 _ . - ARE ALLOWED");
            JOptionPane.showMessageDialog(null, "NAME MUST BE VALID! ONLY CHARACTERS A-Z a-z 0-9 _ . - ARE ALLOWED", "ERROR", JOptionPane.WARNING_MESSAGE);

            return false;
        }

        //####################################################################################################################################
        if (searchInFolder.equals("...") || searchInFolder.trim().equals(""))
        {
            System.out.println("ENTER VALID FOLDER PATH TO SEARCH IN!");
            JOptionPane.showMessageDialog(null, "ENTER VALID FOLDER PATH TO SEARCH IN!", "ERROR", JOptionPane.WARNING_MESSAGE);

            return false;
        } else if (!(new File(searchInFolder).isDirectory()))
        {
            System.out.println("ENTER VALID FOLDER PATH TO SEARCH IN!");
            JOptionPane.showMessageDialog(null, "ENTER VALID FOLDER PATH TO SEARCH IN!", "ERROR", JOptionPane.WARNING_MESSAGE);

            return false;
        } else if (moveToFolder.equals("...") || moveToFolder.trim().equals(""))
        {
            System.out.println("ENTER VALID FOLDER PATH TO MOVE FILES TO!");
            JOptionPane.showMessageDialog(null, "ENTER VALID FOLDER PATH TO MOVE FILES TO!", "ERROR", JOptionPane.WARNING_MESSAGE);

            return false;
        } else if (!(new File(moveToFolder).mkdirs()) && !(new File(moveToFolder).isDirectory()))
        {
            System.out.println("ENTER VALID FOLDER PATH TO MOVE FILES TO!");
            JOptionPane.showMessageDialog(null, "ENTER VALID FOLDER PATH TO MOVE FILES TO!", "ERROR", JOptionPane.WARNING_MESSAGE);

            return false;

        } else if (searchFor.equals("...") || searchFor.trim().equals(""))
        {
            System.out.println("ENTER VALID SEARCH TEXT!");
            JOptionPane.showMessageDialog(null, "ENTER VALID SEARCH TEXT!", "ERROR", JOptionPane.WARNING_MESSAGE);

            return false;
        } else
        {
            answer = true;
        }

        return answer;
    }

    /**
     *
     * checks if rule file already exists
     *
     * @param cat
     * @param name
     * @return
     */
    private static boolean fileExists(String cat, String name)
    {
        File file = null;
        if (cat.equals("tv show"))
        {
            file = new File(halen.FileManager.launchPath() + "/rules/tv show/" + name.trim() + ".xml");

        } else if (cat.equals("anime"))
        {
            file = new File(halen.FileManager.launchPath() + "/rules/anime/" + name.trim() + ".xml");

        } else if (cat.equals("comics"))
        {
            file = new File(halen.FileManager.launchPath() + "/rules/comics/" + name.trim() + ".xml");

        }

        return file.exists();

    }

    /**
     * given data this method saves a new file with said data
     *
     * @param cat
     * @param name
     * @param search
     * @param numberOrUrl
     * @param season
     */
    private static void saveAsNew(String cat, String name, String search, String numberOrUrl, String searchInFolder, String moveToFolder, String searchFor)
    {

        // if (validData(cat, name, search, numberOrUrl, season) && fileExists(cat, name) == false)
        {
            if (cat.equals("tv show"))
            {
                File file = new File(halen.FileManager.launchPath() + "/rules/tv show/" + name.trim() + ".xml");

                try
                {
                    List data = Trakt.TraktParser.getData(numberOrUrl);

                    PrintWriter out = new PrintWriter(file);

                    if (search.equals("...") || search.trim().equals(""))
                    {
                        data.replaceItem(data.getItem(0) + "<searchInFolder>" + searchInFolder + "</searchInFolder><moveToFolder>" + moveToFolder + "</moveToFolder><searchFor>" + searchFor + "</searchFor>", 0);
                    } else
                    {
                        data.replaceItem("<search>" + search + "</search><url>" + numberOrUrl + "</url>" + "<searchInFolder>" + searchInFolder + "</searchInFolder><moveToFolder>" + moveToFolder + "</moveToFolder><searchFor>" + searchFor + "</searchFor>", 0);

                    }

                    for (int i = 0; i < data.getItemCount(); i++)
                    {
                        out.println(data.getItem(i));
                    }

                    out.close();

//                try
//                {
//                    PrintWriter out = new PrintWriter(file);
//                    out.println("<search>" + search + "</search>");
//                    String s = season;
//                    if (s.length() < 2)
//                    {
//                        s = "0" + s;
//                    }
//                    for (int i = 1; i < Integer.parseInt(numberOrUrl) + 1; i++)
//                    {
//                        String e = i + "";
//                        if (e.length() < 2)
//                        {
//                            e = "0" + e;
//                        }
//                        out.println("<s" + s + "e" + e + ">false" + "</s" + s + "e" + e + ">");
//                    }
//                    out.close();
//                } catch (FileNotFoundException ex)
//                {
//                    Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
//                }
                } catch (IOException ex)
                {
                    Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (cat.equals("anime"))
            {
                File file = new File(halen.FileManager.launchPath() + "/rules/anime/" + name.trim() + ".xml");

                try
                {
                    PrintWriter out = new PrintWriter(file);
                    out.println("<search>" + search + "</search>" + "<searchInFolder>" + searchInFolder + "</searchInFolder><moveToFolder>" + moveToFolder + "</moveToFolder><searchFor>" + searchFor + "</searchFor>");

                    for (int i = 1; i < Integer.parseInt(numberOrUrl) + 1; i++)
                    {
                        String e = i + "";
                        if (e.length() < 2)
                        {
                            e = "0" + e;
                        }
                        out.println("<" + e + ">false" + "</" + e + ">");
                    }
                    out.close();
                } catch (FileNotFoundException ex)
                {
                    Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (cat.equals("comics"))
            {
                File file = new File(halen.FileManager.launchPath() + "/rules/comics/" + name.trim() + ".xml");

                try
                {
                    PrintWriter out = new PrintWriter(file);
                    out.println("<search>" + search + "</search>" + "<searchInFolder>" + searchInFolder + "</searchInFolder><moveToFolder>" + moveToFolder + "</moveToFolder><searchFor>" + searchFor + "</searchFor>");

                    for (int i = 1; i < Integer.parseInt(numberOrUrl) + 1; i++)
                    {
                        String e = i + "";

                        if (e.length() < 2)
                        {
                            e = "00" + e;
                        } else if (e.length() < 3)
                        {
                            e = "0" + e;
                        }
                        out.println("<" + e + ">false" + "</" + e + ">");
                    }
                    out.close();
                } catch (FileNotFoundException ex)
                {
                    Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

   
    private static void saveOverExisting(String cat, String name, String search, String numberOrUrl, String searchInFolder, String moveToFolder, String searchFor)
    {
        File file = null;
        List print = null;
        if (cat.equals("tv show"))
        {
            
            

            file = new File(halen.FileManager.launchPath() + "/rules/tv show/" + name.trim() + ".xml");
            print = FileManager.readFile(halen.FileManager.launchPath() + "/rules/tv show/" + name.trim() + ".xml");
           
            String change = print.getItem(0);
            change = change.replace("<search>" + change.subSequence(change.indexOf("<search>")+8, change.indexOf("</search>")) + "</search>","<search>"+ search + "</search>");
            change = change.replace( "<searchInFolder>" + change.subSequence(change.indexOf("<searchInFolder>")+16, change.indexOf("</searchInFolder>")) + "</searchInFolder>", "<searchInFolder>" + searchInFolder + "</searchInFolder>");
            change = change.replace("<moveToFolder>" + change.subSequence(change.indexOf("<moveToFolder>")+14, change.indexOf("</moveToFolder>")) + "</moveToFolder>", "<moveToFolder>" + moveToFolder + "</moveToFolder>");
             change = change.replace("<searchFor>" + change.subSequence(change.indexOf("<searchFor>")+11, change.indexOf("</searchFor>")) + "</searchFor>","<searchFor>"+ searchFor+"</searchFor>");
            System.out.println(change);
            
            print.replaceItem(change, 0);
            
            try
            {
                PrintWriter out = new PrintWriter(halen.FileManager.launchPath() + "/rules/tv show/" + name.trim() + ".xml");
                
                for(int i = 0; i < print.getItemCount();i++)
                {
                    out.println(print.getItem(i));
                }
                
                out.close();
                
            } catch (FileNotFoundException ex)
            {
                Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (cat.equals("anime"))
        {
            file = new File(halen.FileManager.launchPath() + "/rules/anime/" + name.trim() + ".xml");
            print = FileManager.readFile(halen.FileManager.launchPath() + "/rules/anime/" + name.trim() + ".xml");
            
           String change = print.getItem(0);
            change = change.replace("<search>" + change.subSequence(change.indexOf("<search>")+8, change.indexOf("</search>")) + "</search>","<search>"+ search + "</search>");
            change = change.replace( "<searchInFolder>" + change.subSequence(change.indexOf("<searchInFolder>")+16, change.indexOf("</searchInFolder>")) + "</searchInFolder>", "<searchInFolder>" + searchInFolder + "</searchInFolder>");
            change = change.replace("<moveToFolder>" + change.subSequence(change.indexOf("<moveToFolder>")+14, change.indexOf("</moveToFolder>")) + "</moveToFolder>", "<moveToFolder>" + moveToFolder + "</moveToFolder>");
             change = change.replace("<searchFor>" + change.subSequence(change.indexOf("<searchFor>")+11, change.indexOf("</searchFor>")) + "</searchFor>","<searchFor>"+ searchFor+"</searchFor>");
            System.out.println(change);
            
            print.replaceItem(change, 0);
            
            
            boolean shrink = false, extend = false;

            if (Integer.parseInt(numberOrUrl) > print.getItemCount() - 1)
            {
                extend = true;
            } else
            {
                shrink = true;
            }

            //##################################################################
            if (extend == true)
            {
                System.out.println("EXTENDING RULE!");
                for (int i = Integer.parseInt("" + print.getItem(print.getItemCount() - 1).subSequence(print.getItem(print.getItemCount() - 1).indexOf("<") + 1, print.getItem(print.getItemCount() - 1).indexOf(">"))) + 1; i < Integer.parseInt(numberOrUrl) + 1; i++)
                {
                    String e = "" + i;
                    if (e.length() < 2)
                    {
                        e = "0" + e;
                    }

                    print.add("<" + e + ">false</" + e + ">");

                }

            } else if (shrink == true)
            {
                System.out.println("SHRINKING RULE!");
                for (int i = 1; i < print.getItemCount(); i++)
                {
                    if (Integer.parseInt("" + print.getItem(i).subSequence(print.getItem(i).indexOf("<") + 1, print.getItem(i).indexOf(">"))) > Integer.parseInt(numberOrUrl))
                    {
                        print.replaceItem("", i);
                    }
                }
            }
        } else if (cat.equals("comics"))
        {
            file = new File(halen.FileManager.launchPath() + "/rules/comics/" + name.trim() + ".xml");
            print = FileManager.readFile(halen.FileManager.launchPath() + "/rules/comics/" + name.trim() + ".xml");
           
          String change = print.getItem(0);
            change = change.replace("<search>" + change.subSequence(change.indexOf("<search>")+8, change.indexOf("</search>")) + "</search>","<search>"+ search + "</search>");
            change = change.replace( "<searchInFolder>" + change.subSequence(change.indexOf("<searchInFolder>")+16, change.indexOf("</searchInFolder>")) + "</searchInFolder>", "<searchInFolder>" + searchInFolder + "</searchInFolder>");
            change = change.replace("<moveToFolder>" + change.subSequence(change.indexOf("<moveToFolder>")+14, change.indexOf("</moveToFolder>")) + "</moveToFolder>", "<moveToFolder>" + moveToFolder + "</moveToFolder>");
             change = change.replace("<searchFor>" + change.subSequence(change.indexOf("<searchFor>")+11, change.indexOf("</searchFor>")) + "</searchFor>","<searchFor>"+ searchFor+"</searchFor>");
            System.out.println(change);
            
            print.replaceItem(change, 0);
            
            boolean shrink = false, extend = false;

            if (editWithBlankSeason == false)
            {
                if (Integer.parseInt(numberOrUrl) > print.getItemCount() - 1)
                {
                    extend = true;
                } else
                {
                    shrink = true;
                }
            }
            //##################################################################
            if (extend == true)
            {
                System.out.println("EXTENDING RULE!");
                for (int i = Integer.parseInt("" + print.getItem(print.getItemCount() - 1).subSequence(print.getItem(print.getItemCount() - 1).indexOf("<") + 1, print.getItem(print.getItemCount() - 1).indexOf(">"))) + 1; i < Integer.parseInt(numberOrUrl) + 1; i++)
                {
                    String e = "" + i;
                    if (e.length() < 2)
                    {
                        e = "00" + e;
                    } else if (e.length() < 3)
                    {
                        e = "0" + e;
                    }

                    print.add("<" + e + ">false</" + e + ">");

                }

            } else if (shrink == true)
            {
                System.out.println("SHRINKING RULE!");
                for (int i = 1; i < print.getItemCount(); i++)
                {
                    if (Integer.parseInt("" + print.getItem(i).subSequence(print.getItem(i).indexOf("<") + 1, print.getItem(i).indexOf(">"))) > Integer.parseInt(numberOrUrl))
                    {
                        print.replaceItem("", i);
                    }
                }
            }
        }

        try
        {
            PrintWriter out = new PrintWriter(file);
            for (int i = 0; i < print.getItemCount(); i++)
            {
                if (!print.getItem(i).equals(""))
                {
                    out.println(print.getItem(i));
                }
            }
            out.close();
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void save(String cat, String name, String search, String number, String searchInFolder, String moveToFolder, String searchFor)
    {
        if (validData(cat, name, search, number, searchInFolder, moveToFolder, searchFor))
        {
            if (fileExists(cat, name) == false) //if file does not exist then save as new rule
            {
                System.out.println("SAVING AS NEW RULE FILE.....");
                saveAsNew(cat, name, search, number, searchInFolder, moveToFolder, searchFor);

                rulesListPanel.removeAll();
                rulesListPanel.repaint();
                rulesScroll.revalidate();
                rulesScroll.repaint();
                try
                {
                    createRuleButtons(GUI.cb.getSelectedItem().toString().toLowerCase().trim());
                } catch (IOException ex)
                {
                    Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else
            {
                int reply = JOptionPane.showConfirmDialog(null, "THIS RULE ALREADY EXISTS! \nCLICK YES IF YOU WISH TO COMMIT CHANGES.", "RULE ALREADY EXISTS", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION)
                {
                    System.out.println("SAVING OVER EXISTING RULE FILE....."); //if file exists then edit existing file
                    saveOverExisting(cat, name, search, number, searchInFolder, moveToFolder, searchFor);

                    rulesListPanel.removeAll();
                    rulesListPanel.repaint();
                    rulesScroll.revalidate();
                    rulesScroll.repaint();
                    try
                    {
                        createRuleButtons(GUI.cb.getSelectedItem().toString().toLowerCase().trim());
                    } catch (IOException ex)
                    {
                        Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
    }

}
