/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI.NoticePanels;

import static Halen3.GUI.Anime.AnimeGUI.animePanel;
import static Halen3.GUI.Comics.ComicsGUI.comicPanel;
import static Halen3.GUI.Film.FilmGUI.filmPanel;
import Halen3.GUI.GUIBase;
import static Halen3.GUI.GUIBase.color;
import static Halen3.GUI.GUIBase.secondary;
import static Halen3.GUI.GUIBase.tertiary;
import static Halen3.GUI.Manga.MangaGUI.mangaPanel;
import static Halen3.GUI.TV.TvGUI.tvPanel;
import Halen3.IO.FileManager;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import org.apache.commons.io.output.TeeOutputStream;

/**
 *
 * @author brenn
 */
public class LoggingPanel
{

    public static JPanel loggingPanel;
    public static JLabel anim;

    public static JTextArea logs;

    public static void addLoggingPanel(int width, int height, int x, int y) throws FileNotFoundException, IOException
    {
        Border border = new LineBorder(secondary, 3);

        loggingPanel = new JPanel();
        loggingPanel.setSize(width, height);
        loggingPanel.setLocation(x, y);
        loggingPanel.setBackground(secondary);
        loggingPanel.setLayout(null);
        loggingPanel.setVisible(false);

        ImageIcon a = new ImageIcon(color(tertiary, "Resources/metro/saving.png"));
        ImageIcon as = new ImageIcon(a.getImage().getScaledInstance((int) (loggingPanel.getHeight() / 3), (int) (loggingPanel.getHeight() / 3), java.awt.Image.SCALE_DEFAULT));

        anim = new JLabel();
        anim.setLayout(null);
        anim.setIcon(as);
        anim.setSize(anim.getIcon().getIconWidth(), anim.getIcon().getIconHeight());
        anim.setLocation((loggingPanel.getWidth() / 2) - (anim.getWidth() / 2), (int) ((loggingPanel.getHeight() / 2) - (anim.getHeight() * 1.5)));
        anim.setVisible(true);

        logs = new JTextArea();
        logs.setSize((int) (loggingPanel.getWidth() / 1.1), loggingPanel.getHeight() / 2);
        Font font = new Font("Verdana", Font.PLAIN, 24);
        logs.setFont(font);
        logs.setForeground(tertiary);
        logs.setText("");
        logs.setBackground(secondary.brighter());
        logs.setLocation((loggingPanel.getWidth() / 2) - (logs.getWidth() / 2), anim.getY() + anim.getHeight());
        logs.setVisible(true);

        // JScrollPane scroll = new JScrollPane(logs);
        //  scroll.setAutoscrolls(true);
        loggingPanel.add(logs);
        MessageConsole mc = new MessageConsole(logs);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");

        File f = new File(FileManager.launchPath() + "\\logs\\log-" + FileManager.getCurrentDate() + "_" + sdf.format(cal.getTime()) + ".txt");
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        //we will want to print in standard "System.out" and in "file"
        TeeOutputStream myOut = new TeeOutputStream(System.out, fos);
        PrintStream ps = new PrintStream(myOut);

        mc.redirectOut(secondary, ps);
        mc.redirectErr(tertiary, null);
        mc.setMessageLines(17);

        loggingPanel.add(logs);
        loggingPanel.add(anim);

    }

    public static void showFilmLogging()
    {
        logs.setText("");
        GUIBase.hideButtons();
        ImageIcon a = new ImageIcon(color(tertiary, "Resources/metro/types/film.png"));
        ImageIcon as = new ImageIcon(a.getImage().getScaledInstance((int) (loggingPanel.getHeight() / 3), (int) (loggingPanel.getHeight() / 3), java.awt.Image.SCALE_DEFAULT));
        anim.setIcon(as);
        filmPanel.setVisible(false);
        loggingPanel.setVisible(true);
    }
    
      public static void hideFilmLogging()
    {
        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(LoggingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        GUIBase.showButtons();
        filmPanel.setVisible(true);
        loggingPanel.setVisible(false);

    }
      
         public static void showMangaLogging()
    {
        logs.setText("");
        GUIBase.hideButtons();
        ImageIcon a = new ImageIcon(color(tertiary, "Resources/metro/types/manga.png"));
        ImageIcon as = new ImageIcon(a.getImage().getScaledInstance((int) (loggingPanel.getHeight() / 3), (int) (loggingPanel.getHeight() / 3), java.awt.Image.SCALE_DEFAULT));
        anim.setIcon(as);
        mangaPanel.setVisible(false);
        loggingPanel.setVisible(true);
    }
    
      public static void hideMangaLogging()
    {
        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(LoggingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        GUIBase.showButtons();
        mangaPanel.setVisible(true);
        loggingPanel.setVisible(false);

    }
     //''''''' 
    public static void showTVLogging()
    {
        logs.setText("");
        GUIBase.hideButtons();
        ImageIcon a = new ImageIcon(color(tertiary, "Resources/metro/types/tv.png"));
        ImageIcon as = new ImageIcon(a.getImage().getScaledInstance((int) (loggingPanel.getHeight() / 3), (int) (loggingPanel.getHeight() / 3), java.awt.Image.SCALE_DEFAULT));
        anim.setIcon(as);
        tvPanel.setVisible(false);
        loggingPanel.setVisible(true);
    }

    public static void hideTVLogging()
    {
        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(LoggingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        GUIBase.showButtons();
        tvPanel.setVisible(true);
        loggingPanel.setVisible(false);

    }

    public static void showComicLogging()
    {
        logs.setText("");
        GUIBase.hideButtons();
        ImageIcon a = new ImageIcon(color(tertiary, "Resources/metro/types/comics.png"));
        ImageIcon as = new ImageIcon(a.getImage().getScaledInstance((int) (loggingPanel.getHeight() / 3), (int) (loggingPanel.getHeight() / 3), java.awt.Image.SCALE_DEFAULT));
        anim.setIcon(as);
        comicPanel.setVisible(false);
        loggingPanel.setVisible(true);
    }

    public static void hideComicLogging()
    {
        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(LoggingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        GUIBase.showButtons();
        comicPanel.setVisible(true);
        loggingPanel.setVisible(false);

    }
    
     public static void showAnimeLogging()
    {
        logs.setText("");
        GUIBase.hideButtons();
        ImageIcon a = new ImageIcon(color(tertiary, "Resources/metro/types/anime.png"));
        ImageIcon as = new ImageIcon(a.getImage().getScaledInstance((int) (loggingPanel.getHeight() / 3), (int) (loggingPanel.getHeight() / 3), java.awt.Image.SCALE_DEFAULT));
        anim.setIcon(as);
        animePanel.setVisible(false);
        loggingPanel.setVisible(true);
    }

    public static void hideAnimeLogging()
    {
        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(LoggingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        GUIBase.showButtons();
        animePanel.setVisible(true);
        loggingPanel.setVisible(false);

    }

}
