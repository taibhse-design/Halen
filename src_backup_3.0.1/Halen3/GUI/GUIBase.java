/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI;

import static Halen3.GUI.Anime.AnimeGUI.addAnimePanel;
import static Halen3.GUI.Anime.AnimeGUI.animePanel;
import static Halen3.GUI.Comics.ComicsGUI.addComicsPanel;
import static Halen3.GUI.Comics.ComicsGUI.comicPanel;
import static Halen3.GUI.GUIBaseControls.initBaseControls;
import Halen3.GUI.NoticePanels.LoggingPanel;
import static Halen3.GUI.NoticePanels.LoggingPanel.loggingPanel;
import Halen3.GUI.NoticePanels.SavingPanel;
import static Halen3.GUI.NoticePanels.SavingPanel.savePanel;
import static Halen3.GUI.Settings.SettingsGUI.addSettingsPanel;
import static Halen3.GUI.Settings.SettingsGUI.settingsPanel;
import static Halen3.GUI.TV.TvGUI.addTvPanel;
import static Halen3.GUI.TV.TvGUI.tvPanel;
import Halen3.GUI.ThemeEditor.ThemeEditor;
import Halen3.IO.FileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author brenn
 */
public class GUIBase
{

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  //get system screen res
    public static int screenW = (int) screenSize.getWidth();  // variable holds max screen width
    public static int screenH = (int) screenSize.getHeight();  //variable holds max screen height
    public static JFrame frame;
    public static JPanel themePanel;
    public static JButton close, maximise, minimise, tv, comics, anime, settings, theme;

     //          old default theme           0, 0, 0, 230                          51, 51, 51                        255, 255, 255
    public static Color primary = new Color(4, 4, 4, 230), secondary = new Color(225, 225, 225), tertiary = new Color(5, 114, 164);
    
    public static void loadTheme()
    {
        if(new File(FileManager.launchPath() + "/theme.ini").exists())
        {
         List values = FileManager.readFile(FileManager.launchPath() + "/theme.ini");

            primary = new Color(Integer.parseInt(values.getItem(0)), Integer.parseInt(values.getItem(1)), Integer.parseInt(values.getItem(2)), 230);
            secondary = new Color(Integer.parseInt(values.getItem(3)), Integer.parseInt(values.getItem(4)), Integer.parseInt(values.getItem(5)));
            tertiary = new Color(Integer.parseInt(values.getItem(6)), Integer.parseInt(values.getItem(7)), Integer.parseInt(values.getItem(8)));
        }else{} 
    }
    public static Image createImage(String path)
    {
        URL imageURL = GUIBase.class.getResource(path);

        if (imageURL == null)
        {
            System.err.println("Resource not found: " + path);
            return null;
        } else
        {
            return (new ImageIcon(imageURL, "")).getImage();
        }
    }

    public static BufferedImage color(Color tint, String image)
    {

        try
        {
            BufferedImage src = ImageIO.read(GUIBase.class.getResource(image));

            BufferedImage newImage = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TRANSLUCENT);
            Graphics2D graphics = newImage.createGraphics();
            graphics.drawImage(src, 0, 0, null);
            graphics.dispose();

            // Color image
            for (int i = 0; i < newImage.getWidth(); i++)
            {
                for (int j = 0; j < newImage.getHeight(); j++)
                {
                    int ax = newImage.getColorModel().getAlpha(newImage.getRaster().getDataElements(i, j, null));
                    int rx = (int) tint.getRed();
                    int gx = (int) tint.getGreen();
                    int bx = (int) tint.getBlue();
                    newImage.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx << 0));
                }
            }
            return newImage;
        } catch (IOException ex)
        {
            System.out.println(ex);
            Logger.getLogger(GUIBase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * tests the screens resolution before running noa to see if noas default
     * resolution will fit inside the screen, if not then this program scales
     * noa to fit
     */
//    public static void getSuitableFrameSize()
//    {
//
//        System.out.println("Scaling UI to fit default screen.....");
//        //account for windows task bar on screen
//        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
//        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
//        int taskBarHeight = scrnSize.height - winSize.height;
//        int taskBarWidth = scrnSize.width - winSize.width;
//        int accountForTaskBar = 0;
//        //for when task bar is at bottom or top
//        if (taskBarHeight != 0)
//        {
//            accountForTaskBar = taskBarHeight;
//        }//for when task bar is on left or right
//        else if (taskBarWidth != 0)
//        {
//            accountForTaskBar = taskBarWidth;
//        }
//        //  System.out.println(taskBarHeight + "     " + taskBarWidth);
//
//        System.out.println("Screen Width: " + screenW + " Screen Height: " + screenH);
//        //get width that is less than screen width
//        if (screenW <= frameW)
//        {
//            System.out.println("Screen width too small for default size (" + frameW + "), rescaling GUI width to fit.....");
//            do
//            {
//                frameW -= 1;
//                //  frameH -= 1;
//            } while ((frameW + 10) > screenW);
//        }
//
//        //get height that is less that screen height
//        if (screenH <= frameH)
//        {
//            System.out.println("Screen height too small for default size (" + frameH + "), rescaling GUI height to fit.....");
//            do
//            {
//                //  frameW -= 1;
//                frameH -= 1;
//            } while ((frameH + 10) > screenH);
//        }
//
//        //remove task bar size (times 2 to account for frame being screen centred)
//        frameW -= accountForTaskBar * 2;
//        frameH -= accountForTaskBar * 2;
//        System.out.println("Frame Width: " + frameW + "frame Height: " + frameH);
//
//        System.out.println("Aspect Ratio: " + ((float) ((float) frameW / (float) frameH)));
//
//        do
//        {
//            frameH -= 1;
//        } while (((float) ((float) frameW / (float) frameH)) < aspectRatio);
//
//    }

    public static void main(String args[]) throws InterruptedException, InvocationTargetException, IOException
    {
       
        initGUI();
        
     
    }
    
    public static void initGUI() throws IOException
    {
        //   getSuitableFrameSize();

        //make ui look like other programs on native system
        //****************************************************************
        try
        {

            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("\n\n------------------------------------------------------------\nsetting ui: " + UIManager.getSystemLookAndFeelClassName() + "\n\n------------------------------------------------------------\n");

        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            System.out.println("\n\n------------------------------------------------------------\nUNKNOWN UI - DEFAULTING\n\n------------------------------------------------------------\n");

        }

        loadTheme();
        
        //create main frame
        frame = new JFrame("HALEN");

        frame.setSize(screenW, screenH); //ensure frame fits within screen
        //frame.setLocation(screenW / 2 - frame.getWidth() / 2, screenH / 2 - frame.getHeight() / 2); //default frame to be located at centre of screen
        frame.setLocation(0, 0); //default frame to be located at centre of screen

        //    frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(null);
        frame.setResizable(false); //prevent frame resizing as this messes up orientation of buttons and other elements
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setIconImage(createImage("Resources/icon.png")); //create icon
        frame.setUndecorated(true);
        frame.setBackground(primary);

        ImageIcon f = new ImageIcon(color(secondary, "Resources/metro/back/back.png"));
        ImageIcon fs = new ImageIcon(f.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), java.awt.Image.SCALE_DEFAULT));

        JLabel back = new JLabel();
        back.setSize(frame.getWidth(), frame.getHeight());
        back.setIcon(fs);
        back.setVisible(true);

        //########################################################################
        close = new JButton();
        close.setContentAreaFilled(false);
        close.setOpaque(false);
        close.setFocusPainted(false);
        ImageIcon closeFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/close.png"));
        ImageIcon closeScaled = new ImageIcon(closeFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
        close.setIcon(closeScaled);
        close.setSize(close.getIcon().getIconWidth(), close.getIcon().getIconHeight());
        close.setLocation((int) (frame.getWidth() - close.getWidth() * 1.5f), (int) (close.getHeight() / 2));
        close.setVisible(true);
        close.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent me)
            {

            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon closeFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/closeHover.png"));
                ImageIcon closeScaled = new ImageIcon(closeFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                close.setIcon(closeScaled);

            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon closeFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/close.png"));
                ImageIcon closeScaled = new ImageIcon(closeFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                close.setIcon(closeScaled);

            }
        });

        maximise = new JButton();
        maximise.setContentAreaFilled(false);
        maximise.setOpaque(false);
        maximise.setFocusPainted(false);
        ImageIcon maxFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/maximise.png"));
        ImageIcon maxScaled = new ImageIcon(maxFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
        maximise.setIcon(maxScaled);
        maximise.setSize(maximise.getIcon().getIconWidth(), maximise.getIcon().getIconHeight());
        maximise.setLocation((int) (frame.getWidth() - maximise.getWidth() * 2.8f), (int) (maximise.getHeight() / 2));
        maximise.setVisible(true);
        maximise.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon maxFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/maximise.png"));
                ImageIcon maxScaled = new ImageIcon(maxFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                maximise.setIcon(maxScaled);
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon maxFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/maximiseHover.png"));
                ImageIcon maxScaled = new ImageIcon(maxFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                maximise.setIcon(maxScaled);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon maxFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/maximise.png"));
                ImageIcon maxScaled = new ImageIcon(maxFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                maximise.setIcon(maxScaled);
            }
        });

        minimise = new JButton();
        minimise.setContentAreaFilled(false);
        minimise.setOpaque(false);
        minimise.setFocusPainted(false);
        ImageIcon minFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/minimise.png"));
        ImageIcon minScaled = new ImageIcon(minFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
        minimise.setIcon(minScaled);
        minimise.setSize(minimise.getIcon().getIconWidth(), minimise.getIcon().getIconHeight());
        minimise.setLocation((int) (frame.getWidth() - minimise.getWidth() * 4.07), (int) (minimise.getHeight() / 2));
        minimise.setVisible(true);
        minimise.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                frame.setState(JFrame.ICONIFIED);
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon minFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/minimiseHover.png"));
                ImageIcon minScaled = new ImageIcon(minFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                minimise.setIcon(minScaled);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon minFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/minimise.png"));
                ImageIcon minScaled = new ImageIcon(minFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                minimise.setIcon(minScaled);
            }
        });
        //########################################################################

//        save = new JButton();
//        // save.setFont(save.getFont().deriveFont(Font.BOLD));
//        save.setContentAreaFilled(false);
//        save.setOpaque(false);
//        save.setFocusPainted(false);
//        save.setSize(frame.getWidth() / 13, frame.getWidth() / 13);
//        ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/save.png"));
//        ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
//        save.setIcon(ss);
//        save.setLocation(save.getHeight() / 4, (int) ((save.getHeight()*2) + (frame.getHeight() - ((save.getHeight()) * 3.5))));
//        save.setVisible(true);
//
//        delete = new JButton();
//        //  delete.setFont(delete.getFont().deriveFont(Font.BOLD));
//        delete.setContentAreaFilled(false);
//        delete.setOpaque(false);
//        delete.setFocusPainted(false);
//        delete.setSize(save.getWidth(), save.getWidth());
//        ImageIcon d = new ImageIcon(color(tertiary, "Resources/metro/buttons/delete.png"));
//        ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
//        delete.setIcon(ds);
//        delete.setLocation((int) (((save.getX() + save.getWidth()))), save.getY());
//        delete.setVisible(true);
//
//        run = new JButton();
//        //  run.setFont(run.getFont().deriveFont(Font.BOLD));
//        run.setContentAreaFilled(false);
//        run.setOpaque(false);
//        run.setFocusPainted(false);
//        run.setSize(save.getWidth(), save.getWidth());
//        ImageIcon r = new ImageIcon(color(tertiary, "Resources/metro/buttons/run.png"));
//        ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
//        run.setIcon(rs);
//        run.setLocation((int) ((delete.getX() + delete.getWidth())), save.getY());
//        run.setVisible(true);
//
//        update = new JButton();
//        //  run.setFont(run.getFont().deriveFont(Font.BOLD));
//        update.setContentAreaFilled(false);
//        update.setOpaque(false);
//        update.setFocusPainted(false);
//        update.setSize(save.getWidth(), save.getWidth());
//        ImageIcon u = new ImageIcon(color(tertiary, "Resources/metro/buttons/refresh.png"));
//        ImageIcon us = new ImageIcon(u.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
//        update.setIcon(us);
//        update.setLocation((int) ((run.getX() + run.getWidth())), save.getY());
//        update.setVisible(true);

        theme = new JButton();
        //  anime.setFont(anime.getFont().deriveFont(Font.BOLD));
        theme.setContentAreaFilled(false);
        theme.setOpaque(false);
        theme.setFocusPainted(false);
        theme.setSize(frame.getWidth() / 13, frame.getWidth() / 13);
        ImageIcon z = new ImageIcon(color(tertiary, "Resources/metro/buttons/theme.png"));
        ImageIcon zs = new ImageIcon(z.getImage().getScaledInstance(theme.getWidth(), theme.getHeight(), java.awt.Image.SCALE_DEFAULT));
        theme.setIcon(zs);
        theme.setLocation((int) ((frame.getWidth() - (theme.getWidth() + (theme.getHeight() / 4)))), (int) ((theme.getHeight()*2) + (frame.getHeight() - ((theme.getHeight()) * 3.5))));
        theme.setVisible(true);

        //#######################################################################################
        tv = new JButton();
        // tv.setFont(tv.getFont().deriveFont(Font.BOLD));
        tv.setContentAreaFilled(false);
        tv.setOpaque(true);
        tv.setFocusPainted(false);
        tv.setSize(frame.getWidth() / 13, frame.getWidth() / 13);
        ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/tv-normal.png"));
        ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(tv.getWidth(), tv.getHeight(), java.awt.Image.SCALE_DEFAULT));
        tv.setIcon(ts);
        tv.setBackground(primary);
        tv.setLocation(tv.getHeight() / 4, (tv.getHeight()));
        tv.setVisible(true);

        comics = new JButton();
        //  comics.setFont(comics.getFont().deriveFont(Font.BOLD));
        comics.setContentAreaFilled(false);
        comics.setOpaque(false);
        comics.setFocusPainted(false);
        comics.setSize(tv.getWidth(), tv.getWidth());
        ImageIcon c = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/comics-normal.png"));
        ImageIcon cs = new ImageIcon(c.getImage().getScaledInstance(comics.getWidth(), comics.getHeight(), java.awt.Image.SCALE_DEFAULT));
        comics.setIcon(cs);
        comics.setBackground(primary);
        comics.setLocation((int) (((tv.getX() + tv.getWidth()))), tv.getY());
        comics.setVisible(true);

        anime = new JButton();
        //  anime.setFont(anime.getFont().deriveFont(Font.BOLD));
        anime.setContentAreaFilled(false);
        anime.setOpaque(false);
        anime.setFocusPainted(false);
        anime.setSize(tv.getWidth(), tv.getWidth());
        ImageIcon a = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/anime-normal.png"));
        ImageIcon as = new ImageIcon(a.getImage().getScaledInstance(anime.getWidth(), anime.getHeight(), java.awt.Image.SCALE_DEFAULT));
        anime.setIcon(as);
        anime.setBackground(primary);
        anime.setLocation((int) ((comics.getX() + comics.getWidth())), tv.getY());
        anime.setVisible(true);

        settings = new JButton();
        //  anime.setFont(anime.getFont().deriveFont(Font.BOLD));
        settings.setContentAreaFilled(false);
        settings.setOpaque(false);
        settings.setFocusPainted(false);
        settings.setSize(tv.getWidth(), tv.getWidth());
        ImageIcon x = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/settings-normal.png"));
        ImageIcon xs = new ImageIcon(x.getImage().getScaledInstance(anime.getWidth(), anime.getHeight(), java.awt.Image.SCALE_DEFAULT));
        settings.setIcon(xs);
        settings.setBackground(primary);
        settings.setLocation((int) ((anime.getX() + anime.getWidth())), tv.getY());
        settings.setVisible(true);

        addTvPanel(frame.getWidth() - ((theme.getHeight() / 4) * 2), (int) (frame.getHeight() - ((theme.getHeight()) * 3.5)), theme.getHeight() / 4, tv.getY() + tv.getHeight());
        addComicsPanel(frame.getWidth() - ((theme.getHeight() / 4) * 2), (int) (frame.getHeight() - ((theme.getHeight()) * 3.5)), theme.getHeight() / 4, tv.getY() + tv.getHeight());
        addAnimePanel(frame.getWidth() - ((theme.getHeight() / 4) * 2), (int) (frame.getHeight() - ((theme.getHeight()) * 3.5)), theme.getHeight() / 4, tv.getY() + tv.getHeight());
        addSettingsPanel(frame.getWidth() - ((theme.getHeight() / 4) * 2), (int) (frame.getHeight() - ((theme.getHeight()) * 3.5)), theme.getHeight() / 4, tv.getY() + tv.getHeight());

        themePanel = new JPanel();
        themePanel.setSize(frame.getWidth() - ((theme.getHeight() / 4) * 2), (int) (frame.getHeight() - ((theme.getHeight()) * 3.5)));
        themePanel.setLocation(theme.getHeight() / 4, tv.getY() + tv.getHeight());
        themePanel.setLayout(null);
        themePanel.setBackground(secondary);
        ThemeEditor.initEditor(themePanel);
        themePanel.setVisible(false);
        
        SavingPanel.addSavePanel(frame.getWidth() - ((theme.getHeight() / 4) * 2), (int) (frame.getHeight() - ((theme.getHeight()) * 3.5)), theme.getHeight() / 4, tv.getY() + tv.getHeight());
         LoggingPanel.addLoggingPanel(frame.getWidth() - ((theme.getHeight() / 4) * 2), (int) (frame.getHeight() - ((theme.getHeight()) * 3.5)), theme.getHeight() / 4, tv.getY() + tv.getHeight());

        frame.add(loggingPanel);
        frame.add(savePanel);
        frame.add(themePanel);
        frame.add(settingsPanel);
        frame.add(animePanel);
        frame.add(comicPanel);
        frame.add(tvPanel);
        frame.add(settings);
        frame.add(comics);
        frame.add(anime);
        frame.add(tv);
        frame.add(theme);
//        frame.add(update);
   //     frame.add(delete);
  //      frame.add(run);
  //      frame.add(save);
        frame.add(maximise);
        frame.add(minimise);
        frame.add(close);
        frame.add(back);
        
        
        tv.setBackground(secondary);
        comics.setBackground(primary);
        anime.setBackground(primary);
        settings.setBackground(primary);

        tvPanel.setVisible(true);
        comicPanel.setVisible(false);
        animePanel.setVisible(false);
        settingsPanel.setVisible(false);

        frame.setVisible(true);

        initBaseControls();
    }
    
    public static void hideButtons()
    {
        tv.setVisible(false);
        comics.setVisible(false);
        anime.setVisible(false);
        settings.setVisible(false);
        theme.setVisible(false);
    }
    
     public static void showButtons()
    {
        tv.setVisible(true);
        comics.setVisible(true);
        anime.setVisible(true);
        settings.setVisible(true);
        theme.setVisible(true);
    }
}
