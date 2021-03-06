/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI.Settings;

import Halen3.GUI.GUIBase;
import static Halen3.GUI.GUIBase.color;
import static Halen3.GUI.GUIBase.primary;
import static Halen3.GUI.GUIBase.secondary;
import static Halen3.GUI.GUIBase.tertiary;
import static Halen3.GUI.Settings.SettingsGUIControls.initSettingsControls;
import Halen3.IO.FileManager;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author brenn
 */
public class SettingsGUI
{

    public static JPanel settingsPanel;
    public static JButton selectMagnetHandler, selectPortableChromeExe, selectChromeDriverExe, save;
    public static JTextField notifyEmailText, notifyEmail, magnetHandler, magnetHandlerText, portableChromeExe, portableChromeExeText, chromeDriverExe, chromeDriverExeText;

    public static void addSettingsPanel(int width, int height, int x, int y)
    {
        if (!(new File(FileManager.launchPath() + "/settings.xml").exists()))
        {
            try
            {
                new File(FileManager.launchPath() + "/settings.xml").createNewFile();

                PrintWriter out = new PrintWriter(FileManager.launchPath() + "/settings.xml");
                out.println("<handler></handler><email></email><chromedriverexe></chromedriverexe><portablechromeexe></portablechromeexe>");
                out.close();
            } catch (IOException ex)
            {
                Logger.getLogger(SettingsGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Border border = new LineBorder(secondary, 3);
        settingsPanel = new JPanel();
        settingsPanel.setSize(width, height);
        settingsPanel.setLayout(null);
        settingsPanel.setLocation(x, y);
        settingsPanel.setBackground(secondary);

        magnetHandler = new JTextField(20);
        magnetHandler.setText(" Magnet Link Handler ");
        magnetHandler.setHorizontalAlignment(SwingConstants.CENTER);
        magnetHandler.setEditable(false);
        magnetHandler.setFont(magnetHandler.getFont().deriveFont(Font.BOLD));
        magnetHandler.setForeground(primary);
        magnetHandler.setBackground(secondary);
        magnetHandler.setBorder(null);
        magnetHandler.setSize(settingsPanel.getWidth() / 10, settingsPanel.getHeight() / 15);
        magnetHandler.setLocation(magnetHandler.getHeight(), magnetHandler.getHeight());
        magnetHandler.setVisible(true);

        magnetHandlerText = new JTextField(20);
        magnetHandlerText.setText(FileManager.returnTag("handler", FileManager.readFile(FileManager.launchPath() + "\\settings.xml").getItem(0)));
        magnetHandlerText.setEditable(true);
        magnetHandlerText.setFont(magnetHandlerText.getFont().deriveFont(Font.BOLD));
        magnetHandlerText.setForeground(primary);
        magnetHandlerText.setBackground(secondary.brighter());
        magnetHandlerText.setBorder(border);
        magnetHandlerText.setCaretColor(primary);
        magnetHandlerText.setSize(settingsPanel.getWidth() / 2, magnetHandler.getHeight());
        magnetHandlerText.setLocation(magnetHandler.getLocation().x + magnetHandler.getWidth(), magnetHandler.getLocation().y);
        magnetHandlerText.setVisible(true);

        selectMagnetHandler = new JButton();
        // save.setFont(save.getFont().deriveFont(Font.BOLD));
        selectMagnetHandler.setContentAreaFilled(false);
        selectMagnetHandler.setOpaque(false);
        selectMagnetHandler.setFocusPainted(false);
        selectMagnetHandler.setSize(magnetHandler.getHeight(), magnetHandler.getHeight());
        ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/selectFolder.png"));
        ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(selectMagnetHandler.getWidth(), selectMagnetHandler.getHeight(), java.awt.Image.SCALE_DEFAULT));
        selectMagnetHandler.setIcon(ss);
        selectMagnetHandler.setLocation(magnetHandlerText.getLocation().x + magnetHandlerText.getWidth() + (selectMagnetHandler.getWidth() / 2), magnetHandlerText.getY());
        selectMagnetHandler.setVisible(true);

        portableChromeExe = new JTextField(20);
        portableChromeExe.setText(" Portable Chrome Exe ");
        portableChromeExe.setHorizontalAlignment(SwingConstants.CENTER);
        portableChromeExe.setEditable(false);
        portableChromeExe.setFont(portableChromeExe.getFont().deriveFont(Font.BOLD));
        portableChromeExe.setForeground(primary);
        portableChromeExe.setBackground(secondary);
        portableChromeExe.setBorder(null);
        portableChromeExe.setSize(settingsPanel.getWidth() / 10, settingsPanel.getHeight() / 15);
        portableChromeExe.setLocation(magnetHandler.getX(), (int) (magnetHandler.getY() + (magnetHandler.getHeight() * 1.2)));
        portableChromeExe.setVisible(true);

        portableChromeExeText = new JTextField(20);
        portableChromeExeText.setText(FileManager.returnTag("portablechromeexe", FileManager.readFile(FileManager.launchPath() + "\\settings.xml").getItem(0)));
        portableChromeExeText.setEditable(true);
        portableChromeExeText.setFont(portableChromeExeText.getFont().deriveFont(Font.BOLD));
        portableChromeExeText.setForeground(primary);
        portableChromeExeText.setBackground(secondary.brighter());
        portableChromeExeText.setBorder(border);
        portableChromeExeText.setCaretColor(primary);
        portableChromeExeText.setSize(settingsPanel.getWidth() / 2, portableChromeExe.getHeight());
        portableChromeExeText.setLocation(portableChromeExe.getLocation().x + portableChromeExe.getWidth(), portableChromeExe.getLocation().y);
        portableChromeExeText.setVisible(true);

        selectPortableChromeExe = new JButton();
        // save.setFont(save.getFont().deriveFont(Font.BOLD));
        selectPortableChromeExe.setContentAreaFilled(false);
        selectPortableChromeExe.setOpaque(false);
        selectPortableChromeExe.setFocusPainted(false);
        selectPortableChromeExe.setSize(portableChromeExe.getHeight(), portableChromeExe.getHeight());
        ImageIcon c = new ImageIcon(color(tertiary, "Resources/metro/buttons/selectFolder.png"));
        ImageIcon cs = new ImageIcon(c.getImage().getScaledInstance(selectPortableChromeExe.getWidth(), selectPortableChromeExe.getHeight(), java.awt.Image.SCALE_DEFAULT));
        selectPortableChromeExe.setIcon(cs);
        selectPortableChromeExe.setLocation(portableChromeExeText.getLocation().x + portableChromeExeText.getWidth() + (selectPortableChromeExe.getWidth() / 2), portableChromeExeText.getY());
        selectPortableChromeExe.setVisible(true);

        chromeDriverExe = new JTextField(20);
        chromeDriverExe.setText(" Chrome Driver Exe ");
        chromeDriverExe.setHorizontalAlignment(SwingConstants.CENTER);
        chromeDriverExe.setEditable(false);
        chromeDriverExe.setFont(chromeDriverExe.getFont().deriveFont(Font.BOLD));
        chromeDriverExe.setForeground(primary);
        chromeDriverExe.setBackground(secondary);
        chromeDriverExe.setBorder(null);
        chromeDriverExe.setSize(settingsPanel.getWidth() / 10, settingsPanel.getHeight() / 15);
        chromeDriverExe.setLocation(portableChromeExe.getX(), (int) (portableChromeExe.getY() + (portableChromeExe.getHeight() * 1.2)));
        chromeDriverExe.setVisible(true);

        chromeDriverExeText = new JTextField(20);
        chromeDriverExeText.setText(FileManager.returnTag("chromedriverexe", FileManager.readFile(FileManager.launchPath() + "\\settings.xml").getItem(0)));
        chromeDriverExeText.setEditable(true);
        chromeDriverExeText.setFont(chromeDriverExeText.getFont().deriveFont(Font.BOLD));
        chromeDriverExeText.setForeground(primary);
        chromeDriverExeText.setBackground(secondary.brighter());
        chromeDriverExeText.setBorder(border);
        chromeDriverExeText.setCaretColor(primary);
        chromeDriverExeText.setSize(settingsPanel.getWidth() / 2, chromeDriverExe.getHeight());
        chromeDriverExeText.setLocation(chromeDriverExe.getLocation().x + chromeDriverExe.getWidth(), chromeDriverExe.getLocation().y);
        chromeDriverExeText.setVisible(true);

        selectChromeDriverExe = new JButton();
        // save.setFont(save.getFont().deriveFont(Font.BOLD));
        selectChromeDriverExe.setContentAreaFilled(false);
        selectChromeDriverExe.setOpaque(false);
        selectChromeDriverExe.setFocusPainted(false);
        selectChromeDriverExe.setSize(chromeDriverExe.getHeight(), chromeDriverExe.getHeight());
        ImageIcon d = new ImageIcon(color(tertiary, "Resources/metro/buttons/selectFolder.png"));
        ImageIcon ds = new ImageIcon(c.getImage().getScaledInstance(selectChromeDriverExe.getWidth(), selectChromeDriverExe.getHeight(), java.awt.Image.SCALE_DEFAULT));
        selectChromeDriverExe.setIcon(ds);
        selectChromeDriverExe.setLocation(chromeDriverExeText.getLocation().x + chromeDriverExeText.getWidth() + (selectChromeDriverExe.getWidth() / 2), chromeDriverExeText.getY());
        selectChromeDriverExe.setVisible(true);

        notifyEmail = new JTextField(20);
        notifyEmail.setText(" Email Updates To ");
        notifyEmail.setHorizontalAlignment(SwingConstants.CENTER);
        notifyEmail.setEditable(false);
        notifyEmail.setFont(notifyEmail.getFont().deriveFont(Font.BOLD));
        notifyEmail.setForeground(primary);
        notifyEmail.setBackground(secondary);
        notifyEmail.setBorder(null);
        notifyEmail.setSize(settingsPanel.getWidth() / 10, settingsPanel.getHeight() / 15);
        notifyEmail.setLocation(chromeDriverExe.getX(), (int) (chromeDriverExe.getY() + (chromeDriverExe.getHeight() * 1.2)));
        notifyEmail.setVisible(true);

        notifyEmailText = new JTextField(20);
        notifyEmailText.setText(FileManager.returnTag("email", FileManager.readFile(FileManager.launchPath() + "\\settings.xml").getItem(0)));
        notifyEmailText.setEditable(true);
        notifyEmailText.setFont(notifyEmailText.getFont().deriveFont(Font.BOLD));
        notifyEmailText.setForeground(primary);
        notifyEmailText.setBackground(secondary.brighter());
        notifyEmailText.setBorder(border);
        notifyEmailText.setCaretColor(primary);
        notifyEmailText.setSize(settingsPanel.getWidth() / 2, notifyEmail.getHeight());
        notifyEmailText.setLocation(notifyEmail.getLocation().x + notifyEmail.getWidth(), notifyEmail.getLocation().y);
        notifyEmailText.setVisible(true);

        save = new JButton();
        // save.setFont(save.getFont().deriveFont(Font.BOLD));
        save.setContentAreaFilled(false);
        save.setOpaque(false);
        save.setFocusPainted(false);
        save.setSize(notifyEmailText.getWidth() / 4, notifyEmailText.getWidth() / 4);
        ImageIcon z = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/save.png"));
        ImageIcon zs = new ImageIcon(z.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
        save.setIcon(zs);
        save.setLocation(notifyEmailText.getLocation().x, notifyEmailText.getY() + notifyEmailText.getHeight());
        save.setVisible(true);

        settingsPanel.add(save);
        settingsPanel.add(notifyEmailText);
        settingsPanel.add(notifyEmail);
        settingsPanel.add(selectChromeDriverExe);
        settingsPanel.add(chromeDriverExeText);
        settingsPanel.add(chromeDriverExe);
        settingsPanel.add(selectPortableChromeExe);
        settingsPanel.add(portableChromeExeText);
        settingsPanel.add(portableChromeExe);
        settingsPanel.add(selectMagnetHandler);
        settingsPanel.add(magnetHandlerText);
        settingsPanel.add(magnetHandler);

        settingsPanel.setVisible(true);

        initSettingsControls();

    }
}
