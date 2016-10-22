/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI.ThemeEditor;

import static Halen3.GUI.Anime.AnimeGUI.animePanel;
import static Halen3.GUI.Comics.ComicsGUI.comicPanel;
import Halen3.GUI.GUIBase;
import static Halen3.GUI.GUIBase.anime;
import static Halen3.GUI.GUIBase.color;
import static Halen3.GUI.GUIBase.comics;
import static Halen3.GUI.GUIBase.primary;
import static Halen3.GUI.GUIBase.secondary;
import static Halen3.GUI.GUIBase.settings;
import static Halen3.GUI.GUIBase.tertiary;
import static Halen3.GUI.GUIBase.theme;
import static Halen3.GUI.GUIBase.themePanel;
import static Halen3.GUI.GUIBase.tv;
import static Halen3.GUI.Settings.SettingsGUI.settingsPanel;
import static Halen3.GUI.TV.TvGUI.tvPanel;
import Halen3.IO.FileManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author brenn
 */
public class ThemeEditor
{
    public static void initEditor(final JPanel colsetup)
    {
         Color swatch[] = new Color[22];
        //white
        swatch[0] = new Color(255, 255, 255);
        //blues
        swatch[1] = new Color(170, 255, 255);
        swatch[2] = new Color(85, 204, 255);
        swatch[3] = new Color(0, 0, 255);

        //green
        swatch[4] = new Color(170, 255, 170);
        swatch[5] = new Color(85, 255, 204);
        swatch[6] = new Color(0, 255, 0);
        //yellow
        swatch[7] = new Color(248, 255, 182);
        swatch[8] = new Color(243, 255, 107);
        swatch[9] = new Color(255, 255, 0);
        //orange
        swatch[10] = new Color(255, 223, 145);
        swatch[11] = new Color(255, 195, 64);
        swatch[12] = new Color(230, 150, 27);
        //red
        swatch[13] = new Color(255, 103, 108);
        swatch[14] = new Color(240, 90, 70);
        swatch[15] = new Color(255, 0, 0);
        //pink
        swatch[16] = new Color(255, 175, 221);
        swatch[17] = new Color(255, 69, 224);
        swatch[18] = new Color(214, 0, 178);
        //purple
        swatch[19] = new Color(219, 171, 251);
        swatch[20] = new Color(187, 130, 255);
        swatch[21] = new Color(180, 77, 251);
//        
//        swatch[22] = new Color(112, 112, 112);
//        swatch[23] = new Color(140, 140, 140);
//        swatch[24] = new Color(168, 168, 168);
//        swatch[25] = new Color(196, 196, 196);
//        swatch[26] = new Color(224, 224, 224);
//        swatch[27] = new Color(0, 0, 0);
//        swatch[28] = new Color(28, 28, 28);
//        swatch[29] = new Color(56, 56, 56);
//        swatch[30] = new Color(84, 84, 84);
//        swatch[31] = new Color(112, 112, 112);

        //make ui look like other programs on native system
        //****************************************************************
        try
        {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("\n\n------------------------------------------------------------\nsetting ui: " + UIManager.getSystemLookAndFeelClassName() + "\n\n------------------------------------------------------------\n");

        } catch (UnsupportedLookAndFeelException e)
        {
            System.out.println("\n\n------------------------------------------------------------\n               UNKNOWN UI - DEFAULTING\n\n------------------------------------------------------------\n");

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex)
        {
            Logger.getLogger(ThemeEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

//        colsetup = new JFrame("Java Swing Examples");
//        colsetup.setSize(frameW, frameH);
//        colsetup.setLocation((screenW / 2) - (colsetup.getWidth() / 2), (screenH / 2) - (colsetup.getHeight() / 2));
//        colsetup.setLayout(null);
//        colsetup.setUndecorated(true);
//        colsetup.setBackground(primary);

        ImageIcon f = new ImageIcon(color(secondary, "Resources/metro/back/back.png"));
        ImageIcon fs = new ImageIcon(f.getImage().getScaledInstance(colsetup.getWidth(), colsetup.getHeight(), java.awt.Image.SCALE_DEFAULT));

        JLabel back = new JLabel();
        back.setSize(colsetup.getWidth(), colsetup.getHeight());
        back.setIcon(fs);
        back.setVisible(true);

        final JPanel samples = new JPanel();

        // mainFrame.add(colchoose);
        int swatchSize = colsetup.getHeight() / 21;

        int count = 0;

        samples.setLayout(null);
        samples.setSize(swatchSize * 22 + (swatchSize / 5) * 23, swatchSize * 8 + (swatchSize / 5) * 9);
        samples.setLocation(colsetup.getHeight() / 60, colsetup.getHeight() / 10);
        samples.setBackground(secondary);

        JLabel preview = new JLabel("  COLOUR PREVIEW");
        preview.setFont(preview.getFont().deriveFont(Font.BOLD));
        preview.setOpaque(true);
        preview.setForeground(primary);
        preview.setBackground(secondary);
        preview.setSize((int) (colsetup.getWidth() / 3.5), colsetup.getHeight() / 15);
        preview.setLocation((int) (samples.getX() + samples.getWidth()), samples.getY());
        preview.setVisible(true);

        Border border = new LineBorder(secondary, 3);

        final JLabel primaryPreview = new JLabel();
        primaryPreview.setFont(primaryPreview.getFont().deriveFont(Font.BOLD));
        primaryPreview.setOpaque(true);
        primaryPreview.setBorder(border);
        primaryPreview.setBackground(primary);
        primaryPreview.setSize((int) (colsetup.getWidth() / 10), colsetup.getHeight() / 15);
        primaryPreview.setLocation((preview.getX() + preview.getWidth()) - primaryPreview.getWidth(), preview.getY() + preview.getHeight());
        primaryPreview.setVisible(true);

        final JLabel secondaryPreview = new JLabel();
        secondaryPreview.setFont(secondaryPreview.getFont().deriveFont(Font.BOLD));
        secondaryPreview.setOpaque(true);
        secondaryPreview.setBorder(border);
        secondaryPreview.setBackground(secondary);
        secondaryPreview.setSize((int) (colsetup.getWidth() / 10), colsetup.getHeight() / 15);
        secondaryPreview.setLocation((preview.getX() + preview.getWidth()) - secondaryPreview.getWidth(), primaryPreview.getY() + primaryPreview.getHeight());
        secondaryPreview.setVisible(true);

        final JLabel tertiaryPreview = new JLabel();
        tertiaryPreview.setFont(tertiaryPreview.getFont().deriveFont(Font.BOLD));
        tertiaryPreview.setOpaque(true);
        tertiaryPreview.setBorder(border);
        tertiaryPreview.setBackground(tertiary);
        tertiaryPreview.setSize((int) (colsetup.getWidth() / 10), colsetup.getHeight() / 15);
        tertiaryPreview.setLocation((preview.getX() + preview.getWidth()) - tertiaryPreview.getWidth(), secondaryPreview.getY() + secondaryPreview.getHeight());
        tertiaryPreview.setVisible(true);

        ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/unchecked.png"));
        ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(preview.getHeight(), preview.getHeight(), java.awt.Image.SCALE_DEFAULT));

        ImageIcon p = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checked.png"));
        ImageIcon ps = new ImageIcon(p.getImage().getScaledInstance(preview.getHeight(), preview.getHeight(), java.awt.Image.SCALE_DEFAULT));

        final JLabel primaryCheck = new JLabel();
        primaryCheck.setOpaque(false);
        primaryCheck.setName("true");
        primaryCheck.setSize(preview.getHeight(), preview.getHeight());
        primaryCheck.setIcon(ps);
        primaryCheck.setLocation(primaryPreview.getX() - primaryCheck.getWidth(), primaryPreview.getY());
        primaryCheck.setVisible(true);

        final JLabel secondaryCheck = new JLabel();
        secondaryCheck.setOpaque(false);
        secondaryCheck.setName("false");
        secondaryCheck.setSize(preview.getHeight(), preview.getHeight());
        secondaryCheck.setIcon(os);
        secondaryCheck.setLocation(secondaryPreview.getX() - secondaryCheck.getWidth(), secondaryPreview.getY());
        secondaryCheck.setVisible(true);

        final JLabel tertiaryCheck = new JLabel();
        tertiaryCheck.setOpaque(false);
        tertiaryCheck.setName("false");
        tertiaryCheck.setSize(preview.getHeight(), preview.getHeight());
        tertiaryCheck.setIcon(os);
        tertiaryCheck.setLocation(tertiaryPreview.getX() - tertiaryCheck.getWidth(), tertiaryPreview.getY());
        tertiaryCheck.setVisible(true);

        JLabel primaryText = new JLabel("  Primary Colour");
        primaryText.setFont(primaryText.getFont().deriveFont(Font.BOLD));
        primaryText.setOpaque(true);
        primaryText.setForeground(primary);
        primaryText.setBackground(secondary);
        primaryText.setSize((int) (colsetup.getWidth() / 3.5) - primaryPreview.getWidth(), colsetup.getHeight() / 15);
        primaryText.setLocation(preview.getX(), primaryPreview.getY());
        primaryText.setVisible(true);

        JLabel secondaryText = new JLabel("  Secondary Colour");
        secondaryText.setFont(secondaryText.getFont().deriveFont(Font.BOLD));
        secondaryText.setOpaque(true);
        secondaryText.setForeground(primary);
        secondaryText.setBackground(secondary);
        secondaryText.setSize((int) (colsetup.getWidth() / 3.5) - primaryPreview.getWidth(), colsetup.getHeight() / 15);
        secondaryText.setLocation(preview.getX(), secondaryPreview.getY());
        secondaryText.setVisible(true);

        JLabel tertiaryText = new JLabel("  Tertiary Colour");
        tertiaryText.setFont(tertiaryText.getFont().deriveFont(Font.BOLD));
        tertiaryText.setOpaque(true);
        tertiaryText.setForeground(primary);
        tertiaryText.setBackground(secondary);
        tertiaryText.setSize((int) (colsetup.getWidth() / 3.5) - primaryPreview.getWidth(), colsetup.getHeight() / 15);
        tertiaryText.setLocation(preview.getX(), tertiaryPreview.getY());
        tertiaryText.setVisible(true);

        JLabel red = new JLabel("  RED");
        red.setFont(red.getFont().deriveFont(Font.BOLD));
        red.setOpaque(true);
        red.setForeground(primary);
        red.setBackground(secondary);
        red.setSize(preview.getWidth() / 4, (int) (colsetup.getHeight() / 15.7f));
        red.setLocation(preview.getX(), tertiaryText.getY() + tertiaryText.getHeight());
        red.setVisible(true);

        JLabel green = new JLabel("  GREEN");
        green.setFont(green.getFont().deriveFont(Font.BOLD));
        green.setOpaque(true);
        green.setForeground(primary);
        green.setBackground(secondary);
        green.setSize(preview.getWidth() / 4, (int) (colsetup.getHeight() / 16f));
        green.setLocation(preview.getX(), red.getY() + red.getHeight());
        green.setVisible(true);

        JLabel blue = new JLabel("  BLUE");
        blue.setFont(blue.getFont().deriveFont(Font.BOLD));
        blue.setOpaque(true);
        blue.setForeground(primary);
        blue.setBackground(secondary);
        blue.setSize(preview.getWidth() / 4, (int) (colsetup.getHeight() / 15.7f));
        blue.setLocation(preview.getX(), green.getY() + green.getHeight());
        blue.setVisible(true);

        final JTextField redIn = new JTextField(".....");
        redIn.setFont(redIn.getFont().deriveFont(Font.BOLD));
        redIn.setOpaque(true);
        redIn.setForeground(primary);
        redIn.setBackground(tertiary);
        redIn.setSize(preview.getWidth() / 6, (int) (colsetup.getHeight() / 15.7f));
        redIn.setLocation(preview.getX() + red.getWidth(), tertiaryText.getY() + tertiaryText.getHeight());
        redIn.setBorder(border);
        redIn.setEditable(false);
        redIn.setVisible(true);

        final JTextField greenIn = new JTextField(".....");
        greenIn.setFont(greenIn.getFont().deriveFont(Font.BOLD));
        greenIn.setOpaque(true);
        greenIn.setForeground(primary);
        greenIn.setBackground(tertiary);
        greenIn.setSize(preview.getWidth() / 6, (int) (colsetup.getHeight() / 16f));
        greenIn.setLocation(preview.getX() + red.getWidth(), redIn.getY() + redIn.getHeight());
        greenIn.setBorder(border);
        greenIn.setEditable(false);
        greenIn.setVisible(true);

        final JTextField blueIn = new JTextField(".....");
        blueIn.setFont(blueIn.getFont().deriveFont(Font.BOLD));
        blueIn.setOpaque(true);
        blueIn.setForeground(primary);
        blueIn.setBackground(tertiary);
        blueIn.setSize(preview.getWidth() / 6, (int) (colsetup.getHeight() / 15.7f));
        blueIn.setLocation(preview.getX() + red.getWidth(), greenIn.getY() + greenIn.getHeight());
        blueIn.setBorder(border);
        blueIn.setEditable(false);
        blueIn.setVisible(true);

        final JSlider redSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 25);
        redSlider.setLayout(null);
        redSlider.setSize(preview.getWidth() - (red.getWidth() + redIn.getWidth()), redIn.getHeight());
        redSlider.setLocation(redIn.getX() + redIn.getWidth(), redIn.getY());
        // redSlider.setMinorTickSpacing(2);
        redSlider.setBackground(secondary);
        //  redSlider.setMajorTickSpacing(10);
        redSlider.setPaintTicks(false);
        redSlider.setPaintLabels(false);
        redSlider.setFocusable(false);
      //  redSlider.setPaintTrack(false);

        redSlider.setValue(0);
//        SwingUtilities.invokeLater(new Runnable(){
//        @Override public void run() {
//        try{
//        redSlider.setUI(new BasicSliderUI(redSlider)
//        {
//            @Override
//            public void paintTrack(Graphics g)
//            {
//                
//                Graphics2D g2d = (Graphics2D) g;
//                Rectangle t = trackRect;
//                g2d.setPaint(secondary.brighter().brighter());
//                g2d.fillRect(t.x, t.y, t.width, t.height);
//            }
//
//            @Override
//            public void paintThumb(Graphics g)
//            {
//                 g.drawImage( InitFrame.color(tertiary,"resources/metro/icon.png"), thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height, null );
//                
//            }
//            
//           
//
//        });
//     }catch(IllegalArgumentException | NullPointerException e)
//        {
//            
//        }
//        
//        }});
        redSlider.addMouseMotionListener(new MouseMotionListener()
        {

            @Override
            public void mouseDragged(MouseEvent me)
            {
                redIn.setText(" " + redSlider.getValue());

                if (primaryCheck.getName().equals("true"))
                {
                    primaryPreview.setBackground(new Color(redSlider.getValue(), primaryPreview.getBackground().getGreen(), primaryPreview.getBackground().getBlue()));
                    samples.setBackground(new Color(redSlider.getValue(), primaryPreview.getBackground().getGreen(), primaryPreview.getBackground().getBlue()));

                } else if (secondaryCheck.getName().equals("true"))
                {
                    secondaryPreview.setBackground(new Color(redSlider.getValue(), secondaryPreview.getBackground().getGreen(), secondaryPreview.getBackground().getBlue()));
                    samples.setBackground(new Color(redSlider.getValue(), secondaryPreview.getBackground().getGreen(), secondaryPreview.getBackground().getBlue()));

                } else if (tertiaryCheck.getName().equals("true"))
                {
                    tertiaryPreview.setBackground(new Color(redSlider.getValue(), tertiaryPreview.getBackground().getGreen(), tertiaryPreview.getBackground().getBlue()));
                    samples.setBackground(new Color(redSlider.getValue(), tertiaryPreview.getBackground().getGreen(), tertiaryPreview.getBackground().getBlue()));

                }

            }

            @Override
            public void mouseMoved(MouseEvent me)
            {
            }
        });

        final JSlider greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 25);
        greenSlider.setLayout(null);
        greenSlider.setSize(preview.getWidth() - (green.getWidth() + greenIn.getWidth()), greenIn.getHeight());
        greenSlider.setLocation(greenIn.getX() + greenIn.getWidth(), greenIn.getY());
        // greenSlider.setMinorTickSpacing(2);
        greenSlider.setBackground(secondary);
        //  greenSlider.setMajorTickSpacing(10);
        greenSlider.setPaintTicks(false);
        greenSlider.setFocusable(false);
        greenSlider.setPaintLabels(false);
      //  greenSlider.setPaintTrack(false);

        greenSlider.setValue(0);
//        SwingUtilities.invokeLater(new Runnable(){
//        @Override public void run() {
//        try{
//        greenSlider.setUI(new BasicSliderUI(greenSlider)
//        {
//            @Override
//            public void paintTrack(Graphics g)
//            {
//                
//                Graphics2D g2d = (Graphics2D) g;
//                Rectangle t = trackRect;
//                g2d.setPaint(secondary.brighter().brighter());
//                g2d.fillRect(t.x, t.y, t.width, t.height);
//            }
//
//            @Override
//            public void paintThumb(Graphics g)
//            {
//                 g.drawImage( InitFrame.color(tertiary,"resources/metro/icon.png"), thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height, null );
//                
//            }
//
//        });
//     }catch(IllegalArgumentException | NullPointerException e)
//        {
//            
//        }
//        }});
        greenSlider.addMouseMotionListener(new MouseMotionListener()
        {

            @Override
            public void mouseDragged(MouseEvent me)
            {
                greenIn.setText(" " + greenSlider.getValue());

                if (primaryCheck.getName().equals("true"))
                {
                    primaryPreview.setBackground(new Color(primaryPreview.getBackground().getRed(), greenSlider.getValue(), primaryPreview.getBackground().getBlue()));
                    samples.setBackground(new Color(primaryPreview.getBackground().getRed(), greenSlider.getValue(), primaryPreview.getBackground().getBlue()));

                } else if (secondaryCheck.getName().equals("true"))
                {
                    secondaryPreview.setBackground(new Color(secondaryPreview.getBackground().getRed(), greenSlider.getValue(), secondaryPreview.getBackground().getBlue()));
                    samples.setBackground(new Color(secondaryPreview.getBackground().getRed(), greenSlider.getValue(), secondaryPreview.getBackground().getBlue()));

                } else if (tertiaryCheck.getName().equals("true"))
                {
                    tertiaryPreview.setBackground(new Color(tertiaryPreview.getBackground().getRed(), greenSlider.getValue(), tertiaryPreview.getBackground().getBlue()));
                    samples.setBackground(new Color(tertiaryPreview.getBackground().getRed(), greenSlider.getValue(), tertiaryPreview.getBackground().getBlue()));

                }

            }

            @Override
            public void mouseMoved(MouseEvent me)
            {
            }
        });

        final JSlider blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 25);
        blueSlider.setLayout(null);
        blueSlider.setSize(preview.getWidth() - (blue.getWidth() + blueIn.getWidth()), blueIn.getHeight());
        blueSlider.setLocation(blueIn.getX() + blueIn.getWidth(), blueIn.getY());
        // blueSlider.setMinorTickSpacing(2);
        blueSlider.setBackground(secondary);
        //  blueSlider.setMajorTickSpacing(10);
        blueSlider.setFocusable(false);
        blueSlider.setPaintTicks(false);
        blueSlider.setPaintLabels(false);
      //  blueSlider.setPaintTrack(false);

        blueSlider.setValue(0);
//        SwingUtilities.invokeLater(new Runnable(){
//        @Override public void run() {
//        try{
//        blueSlider.setUI(new BasicSliderUI(blueSlider)
//        {
//            @Override
//            public void paintTrack(Graphics g)
//            {
//                
//                Graphics2D g2d = (Graphics2D) g;
//                Rectangle t = trackRect;
//                g2d.setPaint(secondary.brighter().brighter());
//                g2d.fillRect(t.x, t.y, t.width, t.height);
//            }
//
//            @Override
//            public void paintThumb(Graphics g)
//            {
//                 g.drawImage( InitFrame.color(tertiary,"resources/metro/icon.png"), thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height, null );
//                
//            }
//
//        });
//        
//        }catch(IllegalArgumentException | NullPointerException e)
//        {
//            
//        }
//        }});
        blueSlider.addMouseMotionListener(new MouseMotionListener()
        {
            

            @Override
            public void mouseDragged(MouseEvent me)
            {
                blueIn.setText(" " + blueSlider.getValue());

                if (primaryCheck.getName().equals("true"))
                {
                    primaryPreview.setBackground(new Color(primaryPreview.getBackground().getRed(), primaryPreview.getBackground().getGreen(), blueSlider.getValue()));
                    samples.setBackground(new Color(primaryPreview.getBackground().getRed(), primaryPreview.getBackground().getGreen(), blueSlider.getValue()));

                } else if (secondaryCheck.getName().equals("true"))
                {
                    secondaryPreview.setBackground(new Color(secondaryPreview.getBackground().getRed(), secondaryPreview.getBackground().getGreen(), blueSlider.getValue()));
                    samples.setBackground(new Color(secondaryPreview.getBackground().getRed(), secondaryPreview.getBackground().getGreen(), blueSlider.getValue()));

                } else if (tertiaryCheck.getName().equals("true"))
                {
                    tertiaryPreview.setBackground(new Color(tertiaryPreview.getBackground().getRed(), tertiaryPreview.getBackground().getGreen(), blueSlider.getValue()));
                    samples.setBackground(new Color(tertiaryPreview.getBackground().getRed(), tertiaryPreview.getBackground().getGreen(), blueSlider.getValue()));

                }

            }

            @Override
            public void mouseMoved(MouseEvent me)
            {
            }
        });
        
       

        final JButton cancel = new JButton();
        cancel.setContentAreaFilled(false);
        cancel.setOpaque(false);
        cancel.setFocusPainted(false);
        cancel.setSize(colsetup.getWidth() / 8, colsetup.getWidth() / 8);
        ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/cancel.png"));
        ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(cancel.getWidth(), cancel.getHeight(), java.awt.Image.SCALE_DEFAULT));
        cancel.setIcon(ss);
        cancel.setLocation(((colsetup.getWidth() / 2) - (cancel.getWidth() / 2)) - cancel.getWidth(), (int) (colsetup.getHeight() - cancel.getHeight() * 1.2));
        cancel.setVisible(true);

        final JButton accept = new JButton();
        accept.setContentAreaFilled(false);
        accept.setOpaque(false);
        accept.setFocusPainted(false);
        accept.setSize(colsetup.getWidth() / 8, colsetup.getWidth() / 8);
        ImageIcon a = new ImageIcon(color(tertiary, "Resources/metro/buttons/accept.png"));
        ImageIcon as = new ImageIcon(a.getImage().getScaledInstance(accept.getWidth(), accept.getHeight(), java.awt.Image.SCALE_DEFAULT));
        accept.setIcon(as);
        accept.setLocation(((colsetup.getWidth() / 2) - (accept.getWidth() / 2)) + accept.getWidth(), (int) (colsetup.getHeight() - accept.getHeight() * 1.2));
        accept.setVisible(true);

        Color current = null;
        for (int j = 0; j < 22; j++)
        {

            //  System.out.println(j);
            current = swatch[j];
            count = 0;

            for (int i = 0; i < 8; i++)
            {
                final JLabel col = new JLabel();
                col.setSize(swatchSize, swatchSize);
                col.setLocation((swatchSize / 5) + col.getWidth() * j + ((col.getWidth() / 5) * j), (swatchSize / 5) + col.getHeight() * i + ((col.getWidth() / 5) * i));
                col.setOpaque(true);
                if (count < 8)
                {

                    int r = (int) current.getRed() - 27;
                    int g = (int) current.getGreen() - 27;
                    int b = (int) current.getBlue() - 27;
                    if (r < 0)
                    {
                        r = 0;
                    }
                    if (g < 0)
                    {
                        g = 0;
                    }
                    if (b < 0)
                    {
                        b = 0;
                    }
                    col.setBackground(new Color(r, g, b));
                    count++;
                }
                col.setVisible(true);

                col.addMouseListener(new MouseAdapter()
                {

                    @Override
                    public void mouseClicked(MouseEvent me)
                    {
                        samples.setBackground(col.getBackground().brighter().brighter());

                        if (primaryCheck.getName().equals("true"))
                        {
                            primaryPreview.setBackground(col.getBackground().brighter().brighter());
                        } else if (secondaryCheck.getName().equals("true"))
                        {
                            secondaryPreview.setBackground(col.getBackground().brighter().brighter());
                        } else if (tertiaryCheck.getName().equals("true"))
                        {
                            tertiaryPreview.setBackground(col.getBackground().brighter().brighter());
                        }

                        redIn.setText(" " + col.getBackground().brighter().brighter().getRed());
                        greenIn.setText(" " + col.getBackground().brighter().brighter().getGreen());
                        blueIn.setText(" " + col.getBackground().brighter().brighter().getBlue());

                        redSlider.setValue(col.getBackground().brighter().brighter().getRed());
                        greenSlider.setValue(col.getBackground().brighter().brighter().getGreen());
                        blueSlider.setValue(col.getBackground().brighter().brighter().getBlue());
                    }

                    @Override
                    public void mouseReleased(MouseEvent me)
                    {
                    }

                    @Override
                    public void mouseEntered(MouseEvent me)
                    {
                        col.setBackground(col.getBackground().darker().darker());
                    }

                    @Override
                    public void mouseExited(MouseEvent me)
                    {
                        col.setBackground(col.getBackground().brighter().brighter());
                    }
                });
                samples.add(col);

                current = col.getBackground();
            }

        }

        samples.setVisible(true);

        colsetup.add(blueSlider);
        colsetup.add(greenSlider);
        colsetup.add(redSlider);
        colsetup.add(blueIn);
        colsetup.add(greenIn);
        colsetup.add(redIn);
        colsetup.add(green);
        colsetup.add(accept);
        colsetup.add(blue);
        colsetup.add(cancel);
        colsetup.add(red);
        colsetup.add(tertiaryCheck);
        colsetup.add(secondaryCheck);
        colsetup.add(primaryCheck);
        colsetup.add(tertiaryText);
        colsetup.add(secondaryText);
        colsetup.add(primaryText);
        colsetup.add(tertiaryPreview);
        colsetup.add(secondaryPreview);
        colsetup.add(primaryPreview);
        colsetup.add(preview);
        colsetup.add(samples);
        colsetup.add(back);
        colsetup.setVisible(true);

//        colsetup.addMouseListener(new MouseAdapter()
//        {
//            @Override
//            public void mousePressed(MouseEvent e)
//            {
//                posX = e.getX();
//                posY = e.getY();
//            }
//
//        });

//        colsetup.addMouseMotionListener(new MouseAdapter()
//        {
//            public void mouseDragged(MouseEvent evt)
//            {
//                //sets frame position when mouse dragged			
//                colsetup.setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY);
//
//            }
//        });

        primaryCheck.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                if (!primaryCheck.getName().equals("true"))
                {
                    primaryCheck.setName("true");
                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(primaryCheck.getWidth(), primaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    primaryCheck.setIcon(os);

                    ImageIcon p = new ImageIcon(color(tertiary, "Resources/metro/checkBox/unchecked.png"));
                    ImageIcon ps = new ImageIcon(p.getImage().getScaledInstance(primaryCheck.getWidth(), primaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));

                    secondaryCheck.setName("false");
                    secondaryCheck.setIcon(ps);

                    tertiaryCheck.setName("false");
                    tertiaryCheck.setIcon(ps);

                    redIn.setText(" " + primaryPreview.getBackground().getRed());
                    greenIn.setText(" " + primaryPreview.getBackground().getGreen());
                    blueIn.setText(" " + primaryPreview.getBackground().getBlue());

                    redSlider.setValue(primaryPreview.getBackground().getRed());
                    greenSlider.setValue(primaryPreview.getBackground().getGreen());
                    blueSlider.setValue(primaryPreview.getBackground().getBlue());
                    
                    samples.setBackground(primaryPreview.getBackground());

                }
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                if (primaryCheck.getName().equals("true"))
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(primaryCheck.getWidth(), primaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    primaryCheck.setIcon(os);
                } else
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/unchecked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(primaryCheck.getWidth(), primaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    primaryCheck.setIcon(os);
                }
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                if (primaryCheck.getName().equals("true"))
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checkedHover.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(primaryCheck.getWidth(), primaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    primaryCheck.setIcon(os);
                } else
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/uncheckedHover.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(primaryCheck.getWidth(), primaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    primaryCheck.setIcon(os);
                }
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                if (primaryCheck.getName().equals("true"))
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(primaryCheck.getWidth(), primaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    primaryCheck.setIcon(os);
                } else
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/unchecked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(primaryCheck.getWidth(), primaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    primaryCheck.setIcon(os);
                }
            }
        });

        secondaryCheck.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                if (!secondaryCheck.getName().equals("true"))
                {

                    secondaryCheck.setName("true");
                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(secondaryCheck.getWidth(), secondaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    secondaryCheck.setIcon(os);

                    ImageIcon p = new ImageIcon(color(tertiary, "Resources/metro/checkBox/unchecked.png"));
                    ImageIcon ps = new ImageIcon(p.getImage().getScaledInstance(primaryCheck.getWidth(), primaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));

                    primaryCheck.setName("false");
                    primaryCheck.setIcon(ps);

                    tertiaryCheck.setName("false");
                    tertiaryCheck.setIcon(ps);

                    redIn.setText(" " + secondaryPreview.getBackground().getRed());
                    greenIn.setText(" " + secondaryPreview.getBackground().getGreen());
                    blueIn.setText(" " + secondaryPreview.getBackground().getBlue());

                    redSlider.setValue(secondaryPreview.getBackground().getRed());
                    greenSlider.setValue(secondaryPreview.getBackground().getGreen());
                    blueSlider.setValue(secondaryPreview.getBackground().getBlue());
                    
                    samples.setBackground(secondaryPreview.getBackground());

                }
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                if (secondaryCheck.getName().equals("true"))
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(secondaryCheck.getWidth(), secondaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    secondaryCheck.setIcon(os);
                } else
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/unchecked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(secondaryCheck.getWidth(), secondaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    secondaryCheck.setIcon(os);
                }
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                if (secondaryCheck.getName().equals("true"))
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checkedHover.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(secondaryCheck.getWidth(), secondaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    secondaryCheck.setIcon(os);
                } else
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/uncheckedHover.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(secondaryCheck.getWidth(), secondaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    secondaryCheck.setIcon(os);
                }
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                if (secondaryCheck.getName().equals("true"))
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(secondaryCheck.getWidth(), secondaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    secondaryCheck.setIcon(os);
                } else
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/unchecked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(secondaryCheck.getWidth(), secondaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    secondaryCheck.setIcon(os);
                }
            }
        });

        tertiaryCheck.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                if (!tertiaryCheck.getName().equals("true"))
                {

                    tertiaryCheck.setName("true");
                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(tertiaryCheck.getWidth(), tertiaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    tertiaryCheck.setIcon(os);

                    ImageIcon p = new ImageIcon(color(tertiary, "Resources/metro/checkBox/unchecked.png"));
                    ImageIcon ps = new ImageIcon(p.getImage().getScaledInstance(primaryCheck.getWidth(), primaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));

                    secondaryCheck.setName("false");
                    secondaryCheck.setIcon(ps);

                    primaryCheck.setName("false");
                    primaryCheck.setIcon(ps);

                    redIn.setText(" " + tertiaryPreview.getBackground().getRed());
                    greenIn.setText(" " + tertiaryPreview.getBackground().getGreen());
                    blueIn.setText(" " + tertiaryPreview.getBackground().getBlue());

                    redSlider.setValue(tertiaryPreview.getBackground().getRed());
                    greenSlider.setValue(tertiaryPreview.getBackground().getGreen());
                    blueSlider.setValue(tertiaryPreview.getBackground().getBlue());
                    
                    samples.setBackground(tertiaryPreview.getBackground());
                }
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                if (tertiaryCheck.getName().equals("true"))
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(tertiaryCheck.getWidth(), tertiaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    tertiaryCheck.setIcon(os);
                } else
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/unchecked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(tertiaryCheck.getWidth(), tertiaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    tertiaryCheck.setIcon(os);
                }
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                if (tertiaryCheck.getName().equals("true"))
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checkedHover.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(tertiaryCheck.getWidth(), tertiaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    tertiaryCheck.setIcon(os);
                } else
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/uncheckedHover.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(tertiaryCheck.getWidth(), tertiaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    tertiaryCheck.setIcon(os);
                }
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                if (tertiaryCheck.getName().equals("true"))
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/checked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(tertiaryCheck.getWidth(), tertiaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    tertiaryCheck.setIcon(os);
                } else
                {

                    ImageIcon o = new ImageIcon(color(tertiary, "Resources/metro/checkBox/unchecked.png"));
                    ImageIcon os = new ImageIcon(o.getImage().getScaledInstance(tertiaryCheck.getWidth(), tertiaryCheck.getHeight(), java.awt.Image.SCALE_DEFAULT));
                    tertiaryCheck.setIcon(os);
                }
            }
        });

        cancel.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

                //PrintWriter out = null;
                try
                {
                    if (new File(FileManager.launchPath() + "/theme.ini").exists() == false)
                    {

                        PrintWriter out = new PrintWriter(FileManager.launchPath() + "/theme.ini");
                        out.println(primary.getRed());
                        out.println(primary.getGreen());
                        out.println(primary.getBlue());

                        out.println(secondary.getRed());
                        out.println(secondary.getGreen());
                        out.println(secondary.getBlue());

                        out.println(tertiary.getRed());
                        out.println(tertiary.getGreen());
                        out.println(tertiary.getBlue());
                        out.close();

                    }
                    theme.setOpaque(false);
                 theme.setBackground(null);
                      themePanel.setVisible(false);
                  tvPanel.setVisible(true);
                comicPanel.setVisible(false);
                animePanel.setVisible(false);
                settingsPanel.setVisible(false);
                GUIBase.tv.setVisible(true);
                GUIBase.comics.setVisible(true);
                GUIBase.anime.setVisible(true);
                GUIBase.settings.setVisible(true);
                
                 tv.setOpaque(true);
                comics.setOpaque(false);
                anime.setOpaque(false);
                settings.setOpaque(false);
                 tv.setBackground(secondary);
                comics.setBackground(primary);
                anime.setBackground(primary);
                settings.setBackground(primary);
                 //   colsetup.dispose();

//                    if (startup == true)
//                    {
//                        MetroUI.UI();
//                        Controls.controls();
//                    } else
//                    {
//                        frame.setVisible(true);
//                    }

                } catch (FileNotFoundException ex)
                {
                    Logger.getLogger(ThemeEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/cancelPressed.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(cancel.getWidth(), cancel.getHeight(), java.awt.Image.SCALE_DEFAULT));
                cancel.setIcon(ss);
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/cancel.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(cancel.getWidth(), cancel.getHeight(), java.awt.Image.SCALE_DEFAULT));
                cancel.setIcon(ss);
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/cancelHover.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(cancel.getWidth(), cancel.getHeight(), java.awt.Image.SCALE_DEFAULT));
                cancel.setIcon(ss);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/cancel.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(cancel.getWidth(), cancel.getHeight(), java.awt.Image.SCALE_DEFAULT));
                cancel.setIcon(ss);
            }
        });

        accept.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                try
                {

                    PrintWriter out = new PrintWriter(FileManager.launchPath() + "/theme.ini");

                    out.println(primaryPreview.getBackground().getRed());
                    out.println(primaryPreview.getBackground().getGreen());
                    out.println(primaryPreview.getBackground().getBlue());

                    out.println(secondaryPreview.getBackground().getRed());
                    out.println(secondaryPreview.getBackground().getGreen());
                    out.println(secondaryPreview.getBackground().getBlue());

                    out.println(tertiaryPreview.getBackground().getRed());
                    out.println(tertiaryPreview.getBackground().getGreen());
                    out.println(tertiaryPreview.getBackground().getBlue());

                    out.close();
                    primary = new Color(primaryPreview.getBackground().getRed(), primaryPreview.getBackground().getGreen(), primaryPreview.getBackground().getBlue(), 230);
                    secondary = secondaryPreview.getBackground();
                    tertiary = tertiaryPreview.getBackground();

                    GUIBase.frame.removeAll();
                    GUIBase.initGUI();
                    
//                             themePanel.setVisible(false);
//                  tvPanel.setVisible(true);
//                comicPanel.setVisible(false);
//                animePanel.setVisible(false);
//                settingsPanel.setVisible(false);
//                save.setVisible(true);
//                GUIBase.run.setVisible(true);
//                GUIBase.update.setVisible(true);
//                GUIBase.delete.setVisible(true);
//                GUIBase.tv.setVisible(true);
//                GUIBase.comics.setVisible(true);
//                GUIBase.anime.setVisible(true);
//                GUIBase.settings.setVisible(true);
//                
//                
//                 tv.setBackground(secondary);
//                comics.setBackground(primary);
//                anime.setBackground(primary);
//                settings.setBackground(primary);
                
                
                  //  colsetup.dispose();

//                    try
//                    {
////                        if (startup == true)
////                        {
////                            MetroUI.UI();
////                            Controls.controls();
////                        } else
//                        {
//
//                            Runtime.getRuntime().exec("java -jar \"" + FileManager.launchPath() + "\\" + jarName + "\"");
//                            System.exit(0);
//                        }
//                    } catch (IOException ex)
//                    {
//                        Logger.getLogger(ThemeEditor.class.getName()).log(Level.SEVERE, null, ex);
//                    }

                } catch (FileNotFoundException ex)
                {
                } catch (IOException ex)
                {
                    Logger.getLogger(ThemeEditor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/acceptPressed.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(accept.getWidth(), accept.getHeight(), java.awt.Image.SCALE_DEFAULT));
                accept.setIcon(ss);
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/accept.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(accept.getWidth(), accept.getHeight(), java.awt.Image.SCALE_DEFAULT));
                accept.setIcon(ss);
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/acceptHover.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(accept.getWidth(), accept.getHeight(), java.awt.Image.SCALE_DEFAULT));
                accept.setIcon(ss);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/accept.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(accept.getWidth(), accept.getHeight(), java.awt.Image.SCALE_DEFAULT));
                accept.setIcon(ss);
            }
        });

       

          redIn.setText(" " + primaryPreview.getBackground().getRed());
        greenIn.setText(" " + primaryPreview.getBackground().getGreen());
        blueIn.setText(" " + primaryPreview.getBackground().getBlue());

        redSlider.setValue(primaryPreview.getBackground().getRed());
        greenSlider.setValue(primaryPreview.getBackground().getGreen());
        blueSlider.setValue(primaryPreview.getBackground().getBlue());
        
        samples.setBackground(primaryPreview.getBackground());
    }
}
