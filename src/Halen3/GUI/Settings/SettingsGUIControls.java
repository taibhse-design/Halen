/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI.Settings;

import Halen3.GUI.GUIBase;
import static Halen3.GUI.GUIBase.tertiary;
import static Halen3.GUI.Settings.SettingsGUI.chromeDriverExeText;
import static Halen3.GUI.Settings.SettingsGUI.magnetHandlerText;
import static Halen3.GUI.Settings.SettingsGUI.portableChromeExeText;
import static Halen3.GUI.Settings.SettingsGUI.save;
import static Halen3.GUI.Settings.SettingsGUI.selectChromeDriverExe;
import static Halen3.GUI.Settings.SettingsGUI.selectMagnetHandler;
import static Halen3.GUI.Settings.SettingsGUI.selectPortableChromeExe;
import static Halen3.GUI.Settings.SettingsGUI.testingButton;
import Halen3.IO.GlobalSharedVariables;
import static Halen3.IO.GlobalSharedVariables.areSettingsValid;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author brenn
 */
public class SettingsGUIControls
{

    public static void initSettingsControls()
    {
        selectMagnetHandler.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                magnetHandlerText.setText(SaveSettings.selectMagnetHandler(magnetHandlerText.getText()));
             //   try
                //    {
                // Main.setSettings();
                //  magnetHandlerText.setText(FileManager.returnTag("handler", FileManager.readFile(FileManager.launchPath()+"\\settings.xml").getItem(0)));

              //  } catch (FileNotFoundException ex)
                // {
                //    Logger.getLogger(SettingsGUIControls.class.getName()).log(Level.SEVERE, null, ex);
                // }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolderPressed.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectMagnetHandler.getWidth(), selectMagnetHandler.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectMagnetHandler.setIcon(ss);

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolder.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectMagnetHandler.getWidth(), selectMagnetHandler.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectMagnetHandler.setIcon(ss);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolderHover.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectMagnetHandler.getWidth(), selectMagnetHandler.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectMagnetHandler.setIcon(ss);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolder.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectMagnetHandler.getWidth(), selectMagnetHandler.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectMagnetHandler.setIcon(ss);
            }
        });

        selectPortableChromeExe.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                portableChromeExeText.setText(SaveSettings.selectPortableChromeExe(portableChromeExeText.getText()));
               // try
                //  {
                //   Main.setSettings();
                //    magnetHandlerText.setText(FileManager.returnTag("handler", //FileManager.readFile(FileManager.launchPath()+"\\settings.xml").getItem(0)));

              //  } catch (FileNotFoundException ex)
                //   {
                //      Logger.getLogger(SettingsGUIControls.class.getName()).log(Level.SEVERE, null, ex);
                //   }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolderPressed.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectPortableChromeExe.getWidth(), selectPortableChromeExe.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectPortableChromeExe.setIcon(ss);

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolder.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectPortableChromeExe.getWidth(), selectPortableChromeExe.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectPortableChromeExe.setIcon(ss);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolderHover.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectPortableChromeExe.getWidth(), selectPortableChromeExe.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectPortableChromeExe.setIcon(ss);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolder.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectPortableChromeExe.getWidth(), selectPortableChromeExe.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectPortableChromeExe.setIcon(ss);
            }
        });

        selectChromeDriverExe.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                chromeDriverExeText.setText(SaveSettings.selectChromeDriver(chromeDriverExeText.getText()));
               // try
                //  {
                //   Main.setSettings();
                //    magnetHandlerText.setText(FileManager.returnTag("handler", //FileManager.readFile(FileManager.launchPath()+"\\settings.xml").getItem(0)));

              //  } catch (FileNotFoundException ex)
                //   {
                //      Logger.getLogger(SettingsGUIControls.class.getName()).log(Level.SEVERE, null, ex);
                //   }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolderPressed.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectChromeDriverExe.getWidth(), selectChromeDriverExe.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectChromeDriverExe.setIcon(ss);

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolder.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectChromeDriverExe.getWidth(), selectChromeDriverExe.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectChromeDriverExe.setIcon(ss);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolderHover.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectChromeDriverExe.getWidth(), selectChromeDriverExe.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectChromeDriverExe.setIcon(ss);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/selectFolder.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectChromeDriverExe.getWidth(), selectChromeDriverExe.getHeight(), java.awt.Image.SCALE_DEFAULT));
                selectChromeDriverExe.setIcon(ss);
            }
        });

        testingButton.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                if (GlobalSharedVariables.testing.equals("true"))
                {
                    GlobalSharedVariables.testing = "false";

                    ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/checkBox/unchecked.png"));
                    ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(testingButton.getWidth(), testingButton.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    testingButton.setIcon(ss);
                } else
                {
                    GlobalSharedVariables.testing = "true";
                      ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/checkBox/checked.png"));
                    ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(testingButton.getWidth(), testingButton.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    testingButton.setIcon(ss);
                }

              //  chromeDriverExeText.setText(SaveSettings.selectChromeDriver(chromeDriverExeText.getText()));
                // try
                //  {
                //   Main.setSettings();
                //    magnetHandlerText.setText(FileManager.returnTag("handler", //FileManager.readFile(FileManager.launchPath()+"\\settings.xml").getItem(0)));
              //  } catch (FileNotFoundException ex)
                //   {
                //      Logger.getLogger(SettingsGUIControls.class.getName()).log(Level.SEVERE, null, ex);
                //   }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {

//                if(GlobalSharedVariables.testing.equals("true"))
//                {
//                      ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/checkBox/unchecked.png"));
//        ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(testingButton.getWidth(), testingButton.getHeight(), java.awt.Image.SCALE_DEFAULT));
//        testingButton.setIcon(ss);
//        
//                }else
//                {
//                     ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/checkBox/checked.png"));
//        ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(testingButton.getWidth(), testingButton.getHeight(), java.awt.Image.SCALE_DEFAULT));
//        testingButton.setIcon(ss);
//                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
//                    if(GlobalSharedVariables.testing.equals("true"))
//                {
//                      ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/checkBox/unchecked.png"));
//        ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(testingButton.getWidth(), testingButton.getHeight(), java.awt.Image.SCALE_DEFAULT));
//        testingButton.setIcon(ss);
//        
//                }else
//                {
//                     ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/checkBox/checked.png"));
//        ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(testingButton.getWidth(), testingButton.getHeight(), java.awt.Image.SCALE_DEFAULT));
//        testingButton.setIcon(ss);
//                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                if (GlobalSharedVariables.testing.equals("true"))
                {
                    ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/checkBox/checkedHover.png"));
                    ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(testingButton.getWidth(), testingButton.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    testingButton.setIcon(ss);

                } else
                {
                    ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/checkBox/uncheckedHover.png"));
                    ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(testingButton.getWidth(), testingButton.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    testingButton.setIcon(ss);
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if (GlobalSharedVariables.testing.equals("true"))
                {
                    ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/checkBox/checked.png"));
                    ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(testingButton.getWidth(), testingButton.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    testingButton.setIcon(ss);

                } else
                {
                    ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/checkBox/unchecked.png"));
                    ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(testingButton.getWidth(), testingButton.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    testingButton.setIcon(ss);
                }
            }
        });

        save.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                try
                {
                    SaveSettings.saveSettings();
                    if (areSettingsValid() == true)
                    {
                        GUIBase.frame.removeAll();
                        GUIBase.frame.dispose();
                        GUIBase.initGUI();
                    }
                    // try
                    //  {
                    //   Main.setSettings();
                    //    magnetHandlerText.setText(FileManager.returnTag("handler", //FileManager.readFile(FileManager.launchPath()+"\\settings.xml").getItem(0)));

                    //  } catch (FileNotFoundException ex)
                    //   {
                    //      Logger.getLogger(SettingsGUIControls.class.getName()).log(Level.SEVERE, null, ex);
                    //   }
                } catch (FileNotFoundException ex)
                {
                    Logger.getLogger(SettingsGUIControls.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex)
                {
                    Logger.getLogger(SettingsGUIControls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/savePressed.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/save.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/saveHover.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/save.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);
            }
        });

    }
}
