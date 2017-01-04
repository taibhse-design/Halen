/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI.Settings;

import Halen3.GUI.GUIBase;
import static Halen3.GUI.Settings.SettingsGUI.chromeDriverExeText;
import static Halen3.GUI.Settings.SettingsGUI.magnetHandlerText;
import static Halen3.GUI.Settings.SettingsGUI.notifyEmailText;
import static Halen3.GUI.Settings.SettingsGUI.portableChromeExeText;
import Halen3.IO.GlobalSharedVariables;
import Halen3.IO.FileManager;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author brenn
 */
public class SaveSettings
{
     public static String selectMagnetHandler(String magnetHandler)
    {
        //String magnetHandler = "";
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

            if (fileChooser.showSaveDialog(GUIBase.frame) == JFileChooser.APPROVE_OPTION)

            {
                    magnetHandler = fileChooser.getSelectedFile().toString();
                    
                    return magnetHandler;
                      
            }
        }
        
        return magnetHandler;
    }
     
      public static String selectPortableChromeExe(String handler)
    {
        //String handler = "";
        String[] options = new String[]
        {
            "Select Portable Chrome EXE"
        };
        int response = JOptionPane.showOptionDialog(null, "Select exe file to chrome browser to use for \nparsing sites for updates.", "SETTINGS", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (response == 0)
        {

            JFileChooser fileChooser = new JFileChooser("");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("PORTABLE CHROME");
            fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
            fileChooser.setFileFilter(new FileNameExtensionFilter(".exe  (executable file)", "exe"));

            if (fileChooser.showSaveDialog(GUIBase.frame) == JFileChooser.APPROVE_OPTION)

            {
                    handler = fileChooser.getSelectedFile().toString();
                    
                    return handler;
                      
            }
        }
        
        return handler;
    }
      
          public static String selectChromeDriver(String handler)
    {
        //String handler = "";
        String[] options = new String[]
        {
            "Select Chrome Driver EXE"
        };
        int response = JOptionPane.showOptionDialog(null, "Select exe file to chrome driver for selenium \nto use to control browser.", "SETTINGS", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (response == 0)
        {

            JFileChooser fileChooser = new JFileChooser("");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("CHROME DRIVER");
            fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
            fileChooser.setFileFilter(new FileNameExtensionFilter(".exe  (executable file)", "exe"));

            if (fileChooser.showSaveDialog(GUIBase.frame) == JFileChooser.APPROVE_OPTION)

            {
                    handler = fileChooser.getSelectedFile().toString();
                    
                    return handler;
                      
            }
        }
        
        return handler;
    }
          
          
           public static void saveSettings() throws FileNotFoundException
    {
        String[] options = new String[]
        {
            "Save Settings"
        };
        
        if(!magnetHandlerText.getText().trim().equals("") && !chromeDriverExeText.getText().trim().equals("") && !portableChromeExeText.getText().trim().equals("") && !notifyEmailText.getText().trim().equals(""))
        {
            if(FileManager.isValidEmailId(notifyEmailText.getText().trim()) == true)
            {
        int response = JOptionPane.showOptionDialog(null, "Do you wish to save these settings?", "SETTINGS", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (response == 0)
        {

                try
                {
                    
                      GlobalSharedVariables.pathToChromePortable = portableChromeExeText.getText().trim();
                      GlobalSharedVariables.pathToChromeDriver = chromeDriverExeText.getText().trim();
                       GlobalSharedVariables.magnetHandler = magnetHandlerText.getText().trim();
                      GlobalSharedVariables.email = notifyEmailText.getText();

                   // handler = fileChooser.getSelectedFile().toString();
                    PrintWriter out = new PrintWriter(FileManager.launchPath() + "/settings.xml");
                    out.println( FileManager.makeTag("handler", GlobalSharedVariables.magnetHandler) + FileManager.makeTag("email", GlobalSharedVariables.email) + FileManager.makeTag("chromedriverexe", GlobalSharedVariables.pathToChromeDriver) + FileManager.makeTag("portablechromeexe", GlobalSharedVariables.pathToChromePortable));
                    out.close();
                } catch (NullPointerException ex)
                {
                       
                }
            
        }
            }else
            {
                JOptionPane.showMessageDialog(null, "A GIVEN EMAIL ADDRESS IS INVALID!", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        
        }else
        {
            JOptionPane.showMessageDialog(null, "A GIVEN VALUE IS BLANK!", "ERROR", JOptionPane.WARNING_MESSAGE);
            
            
        }
        
    }
}
