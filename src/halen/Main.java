package halen;

import static FileMover.SearchAlgorithms.MoveFiles;
import MagnetHandler.Handler;
import Rules.CreateRulesFolder;
import Rules.Run;
import static halen.Controls.controls;
import static halen.FileManager.launchPath;
import static halen.FileManager.readFile;
import static halen.GUI.screenH;
import static halen.GUI.screenW;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author TAIBHSE
 */
public class Main
{

    //  public static List rules = readFile(launchPath() + "\\rules.xml");
    public static int frameW = 6400 /* was 1280 */, frameH = 3175 /* was 635 */;
    //aspect ratio of program ui
    public static final float aspectRatio = 2.02f;

    public static String jarName = new java.io.File(Main.class.getProtectionDomain()
            .getCodeSource()
            .getLocation()
            .getPath())
            .getName();

    //  private static List settings = readFile(launchPath() + "\\settings.xml");
    public static String n = "", ser = "", start = "", end = "", handler = "";
    public static Color primary = new Color(0, 0, 0, 230), secondary = new Color(51, 51, 51), tertiary = new Color(255, 255, 255);

    /**
     * tests the screens resolution before running noa to see if noas default
     * resolution will fit inside the screen, if not then this program scales
     * noa to fit
     */
    public static void getSuitableFrameSize()
    {

        System.out.println("Scaling UI to fit default screen.....");
        //account for windows task bar on screen
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int taskBarHeight = scrnSize.height - winSize.height;
        int taskBarWidth = scrnSize.width - winSize.width;
        int accountForTaskBar = 0;
        //for when task bar is at bottom or top
        if(taskBarHeight != 0)
        {
           accountForTaskBar =  taskBarHeight;
        }//for when task bar is on left or right
        else if(taskBarWidth != 0)
        {
            accountForTaskBar = taskBarWidth;
        }
      //  System.out.println(taskBarHeight + "     " + taskBarWidth);

        System.out.println("Screen Width: " + screenW + " Screen Height: " + screenH);
        //get width that is less than screen width
        if (screenW <= frameW)
        {
            System.out.println("Screen width too small for default size (" + frameW + "), rescaling GUI width to fit.....");
            do
            {
                frameW -= 1;
                //  frameH -= 1;
            } while ((frameW + 10) > screenW);
        }

        //get height that is less that screen height
        if (screenH <= frameH)
        {
            System.out.println("Screen height too small for default size (" + frameH + "), rescaling GUI height to fit.....");
            do
            {
                //  frameW -= 1;
                frameH -= 1;
            } while ((frameH + 10) > screenH);
        }
        
        //remove task bar size (times 2 to account for frame being screen centred)
        frameW -= accountForTaskBar*2;
        frameH -= accountForTaskBar*2;
        System.out.println("Frame Width: " + frameW + "frame Height: " + frameH);
        
        System.out.println("Aspect Ratio: " + ((float) ((float)frameW / (float)frameH)));
        
       do
       {
           frameH -= 1;
       }while(((float) ((float)frameW / (float)frameH)) < aspectRatio);
            
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException
    {

        //code prevents multiple instances of halen running
        String appId = "Taibhse.Halen";
        boolean alreadyRunning;
        try
        {
            JUnique.acquireLock(appId);
            alreadyRunning = false;
        } catch (AlreadyLockedException e)
        {
            alreadyRunning = true;
            System.out.println("ALREADY RUNNING");
        }

        int i = 0;
        if (!alreadyRunning)
        {

            if (args.length != 0)
            {
                if (args[0].equals("-help"))
                {
                    System.out.println(""
                            + "Double click Halen.jar to launch GUI interface, to run this program from the command line, the following are supported.\n"
                            + "(NOTE: HALEN DOES NOT SUPPORT MULTIPLE COMMANDS AT ONCE (IE, NO JAVA -JAR HALEN.JAR -COMMAND1 -COMMAND2)\n\n"
                            + "The following are all supported commands and what each does:\n\n"
                            + "-help         | you should know this one, its the only way you could be reading this text, bravo for figuring it out.\n"
                            + "-relocate     | searches through all rules and trys to find matches on drive and organise them into folders per each rules settings.\n"
                            + "-search       | this command makes halen search for the latest episodes without needing the GUI running.\n"
                            + "-update_rules | this command updates rules (tv) by downloading latest season information from trakt.");
                } else if (args[0].equals("-relocate"))
                {
                    try
                    {
                        handler = FileManager.returnTag("handler", readFile(launchPath() + "\\settings.xml").getItem(0));
                        MoveFiles();
                        
                    } catch (IOException ex)
                    {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if (args[0].equals("-search"))
                {
                    System.out.println("RUNNING RULES HEADLESS.....");
                    try
                    {
                        handler = FileManager.returnTag("handler", readFile(launchPath() + "\\settings.xml").getItem(0));
                        Run.runRules();
                    } catch (IOException | URISyntaxException ex)
                    {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("ERROR RUNNING SEARCH.....\n" + ex);
                    }
                }else if (args[0].equals("-update_rules"))
                {
                    try
                    {
                        Trakt.UpdateTraktData.updateTvRules();
                    } catch (IOException ex)
                    {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println(ex);
                    }
                }
            } else
            {
                runHalen(args);
            }

        }

    }

    public static void runHalen(String[] args) throws FileNotFoundException
    {

        //ensure gui scales correctly
        getSuitableFrameSize();
        //create rules folder if does not already exist
        CreateRulesFolder.create();

        //check if theme file exists, otherwise opt user to set theme
        if (new File(FileManager.launchPath() + "/theme.ini").exists() == false)
        {

            ThemeEditor.openThemeEditor(true);

        } else //otherwise if theme file exists, load theme
        {
            List values = FileManager.readFile(FileManager.launchPath() + "/theme.ini");

            primary = new Color(Integer.parseInt(values.getItem(0)), Integer.parseInt(values.getItem(1)), Integer.parseInt(values.getItem(2)), 230);
            secondary = new Color(Integer.parseInt(values.getItem(3)), Integer.parseInt(values.getItem(4)), Integer.parseInt(values.getItem(5)));
            tertiary = new Color(Integer.parseInt(values.getItem(6)), Integer.parseInt(values.getItem(7)), Integer.parseInt(values.getItem(8)));
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {

                    MetroUI.UI();
                    controls();
                }
            });
        }

        //load settings
        try
        {
            handler = FileManager.returnTag("handler", readFile(launchPath() + "\\settings.xml").getItem(0));
        } catch (ArrayIndexOutOfBoundsException e)
        {
            handler = "";
            // System.out.println("ERROR SETTING HANDLER");
            //  JOptionPane.showMessageDialog(null, "ERROR SETTING HANDLER!", "ERROR", JOptionPane.WARNING_MESSAGE);

        }

        //ensure settings file is set, otherwise force loop until settings are made
        boolean validSettings = false;
        while (validSettings == false)
        {
            //forse set handler
            if (handler.equals(""))
            {
                setSettings();
            } else
            {
                validSettings = true;
            }
        }

        Handler.launchHandler();

        Rules.Convert.convertOldToNewRulesFormat();
        //load controls
        //  Controls.controls();
    }

    public static void setSettings() throws FileNotFoundException
    {
        String[] options = new String[]
        {
            "Set Magnet Handler"
        };
        int response = JOptionPane.showOptionDialog(null, "Select which program (torrent client) \nyou want to use to download magnet links.", "SETTINGS", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (response == 0)
        {

            JFileChooser fileChooser = new JFileChooser("");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("TORRENT HANDLER");
            fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
            fileChooser.setFileFilter(new FileNameExtensionFilter(".jar  (java jar file)", "jar"));
            fileChooser.setFileFilter(new FileNameExtensionFilter(".exe  (executable file)", "exe"));

            if (fileChooser.showSaveDialog(GUI.frame) == JFileChooser.APPROVE_OPTION)

            {
                try
                {
                    handler = fileChooser.getSelectedFile().toString();
                    PrintWriter out = new PrintWriter(FileManager.launchPath() + "/settings.xml");
                    out.println("<handler>" + handler + "</handler>");
                    out.close();
                } catch (NullPointerException ex)
                {

                }
            }
        }
    }
}
