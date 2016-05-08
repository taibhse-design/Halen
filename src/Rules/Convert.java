package Rules;

import halen.FileManager;
import static halen.Main.jarName;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author TAIBHSE
 */
public class Convert
{

    public static void convertOldToNewRulesFormat() throws FileNotFoundException
    {
        int reply = -1;
        File oldRules = new File(FileManager.launchPath() + "\\rules.xml");

        if (oldRules.exists())
        {
            reply = JOptionPane.showConfirmDialog(null, "Halen has detected an old rules list, \nclick yes if you would like halen \nto convert these rules to the new standard.\n(If you dont wish to convert and \nwould like this message to stop showing \nthen please delete rules.xml in the \nhalen folder)", "OLD RULES DETECTED", JOptionPane.YES_NO_OPTION);

            if (reply == JOptionPane.YES_OPTION)
            {
                List rules = FileManager.readFile("G:\\Desktop\\portables\\myPrograms\\Halen\\rules.xml");
                Rules.CreateRulesFolder.create();

                for (int i = 0; i < rules.getItemCount(); i++)
                {
                    System.out.println("\n\n" + rules.getItem(i));

                    if (FileManager.returnTag("type", rules.getItem(i)).equals("tv show"))
                    {
                        PrintWriter out = new PrintWriter(halen.FileManager.launchPath() + "/rules/tv show/" + FileManager.returnTag("name", rules.getItem(i)).replaceAll("[^A-Za-z0-9 ]", "") + ".xml");
                        out.println("<search>" + FileManager.returnTag("search", rules.getItem(i)) + "</search>");

                        String max = FileManager.returnTag("end", rules.getItem(i)).replace(FileManager.returnTag("end", rules.getItem(i)).substring(FileManager.returnTag("end", rules.getItem(i)).indexOf("s"), FileManager.returnTag("end", rules.getItem(i)).indexOf("e") + 1), " ").trim();
                        String ret = FileManager.returnTag("start", rules.getItem(i)).replace(FileManager.returnTag("start", rules.getItem(i)).substring(FileManager.returnTag("start", rules.getItem(i)).indexOf("s"), FileManager.returnTag("end", rules.getItem(i)).indexOf("e") + 1), " ").trim();
                        String season = FileManager.returnTag("end", rules.getItem(i)).replace(FileManager.returnTag("end", rules.getItem(i)).substring(FileManager.returnTag("end", rules.getItem(i)).indexOf("e"), FileManager.returnTag("end", rules.getItem(i)).length()), " ").trim();
                        for (int j = 1; j < Integer.parseInt(max); j++)
                        {
                            if (j < Integer.parseInt(ret))
                            {
                                String e = j + "";
                                if (e.length() < 2)
                                {
                                    e = "0" + e;
                                }
                                out.println("<" + season + "e" + e + ">true" + "</" + season + "e" + e + ">");
                            } else
                            {
                                String e = j + "";
                                if (e.length() < 2)
                                {
                                    e = "0" + e;
                                }
                                out.println("<" + season + "e" + e + ">false" + "</" + season + "e" + e + ">");
                            }
                        }

                        out.close();
                    }
                    if (FileManager.returnTag("type", rules.getItem(i)).equals("comic"))
                    {
                        PrintWriter out = new PrintWriter(halen.FileManager.launchPath() + "/rules/comics/" + FileManager.returnTag("name", rules.getItem(i)).replaceAll("[^A-Za-z0-9 ]", "") + ".xml");
                        out.println("<search>" + FileManager.returnTag("search", rules.getItem(i)) + "</search>");

                        String max = FileManager.returnTag("end", rules.getItem(i));
                        String ret = FileManager.returnTag("start", rules.getItem(i));

                        for (int j = 1; j < Integer.parseInt(max); j++)
                        {
                            if (j < Integer.parseInt(ret))
                            {
                                String e = j + "";
                                if (e.length() < 2)
                                {
                                    e = "00" + e;
                                }
                                if (e.length() < 3)
                                {
                                    e = "0" + e;
                                }
                                out.println("<" + e + ">true" + "</" + e + ">");
                            } else
                            {
                                String e = j + "";
                                if (e.length() < 2)
                                {
                                    e = "00" + e;
                                }
                                if (e.length() < 3)
                                {
                                    e = "0" + e;
                                }
                                out.println("<" + e + ">false" + "</" + e + ">");
                            }
                        }

                        out.close();
                    }
                    if (FileManager.returnTag("type", rules.getItem(i)).equals("anime"))
                    {
                        PrintWriter out = new PrintWriter(halen.FileManager.launchPath() + "/rules/anime/" + FileManager.returnTag("name", rules.getItem(i)).replaceAll("[^A-Za-z0-9 ]", "") + ".xml");
                        out.println("<search>" + FileManager.returnTag("search", rules.getItem(i)) + "</search>");

                        String max = FileManager.returnTag("end", rules.getItem(i));
                        String ret = FileManager.returnTag("start", rules.getItem(i));

                        for (int j = 1; j < Integer.parseInt(max); j++)
                        {
                            if (j < Integer.parseInt(ret))
                            {
                                String e = j + "";
                                if (e.length() < 2)
                                {
                                    e = "0" + e;
                                }

                                out.println("<" + e + ">true" + "</" + e + ">");
                            } else
                            {
                                String e = j + "";
                                if (e.length() < 2)
                                {
                                    e = "0" + e;
                                }

                                out.println("<" + e + ">false" + "</" + e + ">");
                            }
                        }

                        out.close();
                    }
                }

                oldRules.delete();

                try
                {
                    Runtime.getRuntime().exec("java -jar \"" + FileManager.launchPath() + "\\" + jarName + "\"");
                    System.exit(0);
                } catch (IOException ex)
                {
                    Logger.getLogger(Convert.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }
}
