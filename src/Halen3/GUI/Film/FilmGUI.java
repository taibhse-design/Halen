/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI.Film;

import static Halen3.GUI.Film.FilmGUIControls.initFilmControls;
import Halen3.GUI.GUIBase;
import static Halen3.GUI.GUIBase.primary;
import static Halen3.GUI.GUIBase.secondary;
import static Halen3.GUI.GUIBase.tertiary;
import Halen3.IO.FileManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author brenn
 */
public class FilmGUI
{

    public static JPanel filmPanel, rulesPane, rulesListPanel, RetrievedListPane, RetrievedListEmptyPanel;
    public static JScrollPane RetrievedListScroll, rulesScroll;

    public static JTextField ruleName, ruleNameInput, searchIn, search, traktURL, traktURLInput, dividerOne, dividerTwo, searchInFolder, searchInFolderText, moveToFolder, moveToFolderText, searchFor, searchForText;//, dividerTwo, searchInFolder;
    public static JButton save, delete, run, update;

    public static void addFilmPanel(int width, int height, int x, int y)
    {
        Border border = new LineBorder(secondary, 3);

        filmPanel = new JPanel();
        filmPanel.setSize(width, height);
        filmPanel.setLocation(x, y);
        filmPanel.setLayout(null);
        filmPanel.setBackground(secondary);

        //########################################################################
        //######################################################################################
        // create list to hold buttons for rules
        rulesPane = new JPanel();
        rulesPane.setLayout(new BorderLayout());
        rulesPane.setSize(filmPanel.getWidth() / 3, filmPanel.getHeight());
        rulesPane.setLocation((filmPanel.getWidth() - (filmPanel.getWidth() / 4)) - rulesPane.getWidth(), 0);

        // Set layout for contactListPane
        rulesListPanel = new JPanel();
        BoxLayout layout = new BoxLayout(rulesListPanel, BoxLayout.Y_AXIS);
        rulesListPanel.setLayout(layout);
        rulesListPanel.setBackground(secondary.darker().darker());

        rulesScroll = new JScrollPane(rulesListPanel);
        rulesScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rulesScroll.getVerticalScrollBar().setUnitIncrement(16);
        rulesScroll.setBorder(border);
        rulesScroll.setBackground(secondary.darker());
        rulesScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI()
        {
            @Override
            protected JButton createDecreaseButton(int orientation)
            {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation)
            {
                return createZeroButton();
            }

            private JButton createZeroButton()
            {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
            {
                g.setColor(secondary.brighter().brighter());
                g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                //  paintDecreaseHighlight(g);
                //  paintIncreaseHighlight(g);

            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
            {

                g.setColor(secondary.darker().darker());
                g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);

                if (thumbBounds.isEmpty() || !scrollbar.isEnabled())
                {
                    return;
                }

                g.translate(thumbBounds.x, thumbBounds.y);
                g.translate(-thumbBounds.x, -thumbBounds.y);
            }
        });

        rulesPane.add(rulesScroll, null);

        rulesPane.setVisible(true);

        //#################################################################################
        //add panel that holds list of episodes, ie list showing whats been downloaded and not for the 
        //selected rule
        //#################################################################################
        RetrievedListPane = new JPanel();
        RetrievedListPane.setLayout(new BorderLayout());
        // RetrievedListPane.setSize(rulesPane.getX() - (search.getX() + search.getWidth() + (search.getHeight() / 6) * 2), (int) (frame.getHeight() / 1.3));
        RetrievedListPane.setSize(filmPanel.getWidth() / 4, filmPanel.getHeight());
        //episodeListPane.setLocation((int) (search.getX() + search.getWidth() + search.getHeight() / 6), cb.getY());
        RetrievedListPane.setLocation(filmPanel.getWidth() - RetrievedListPane.getWidth(), 0);

        // Set layout for contactListPane
        RetrievedListEmptyPanel = new JPanel();
        BoxLayout layout2 = new BoxLayout(RetrievedListEmptyPanel, BoxLayout.Y_AXIS);
        RetrievedListEmptyPanel.setLayout(layout2);
        RetrievedListEmptyPanel.setBackground(secondary.darker());
        //   contactListPanel.setSize(sequencePane.getWidth(), sequencePane.getHeight()/8);

        //   createRuleButtons(GUI.cb.getSelectedItem().toString().toLowerCase().trim());
        RetrievedListScroll = new JScrollPane(RetrievedListEmptyPanel);
        RetrievedListScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        RetrievedListScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        RetrievedListScroll.getHorizontalScrollBar().setModel(rulesScroll.getHorizontalScrollBar().getModel());
        RetrievedListScroll.getVerticalScrollBar().setModel(rulesScroll.getVerticalScrollBar().getModel());
       // RetrievedListScroll.getVerticalScrollBar().setUnitIncrement(16);

        //  Border border = new LineBorder(secondary, 2);
        RetrievedListScroll.setBorder(border);
        RetrievedListScroll.setBackground(secondary.darker());
        RetrievedListScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI()
        {
            @Override
            protected JButton createDecreaseButton(int orientation)
            {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation)
            {
                return createZeroButton();
            }

            private JButton createZeroButton()
            {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
            {
                g.setColor(secondary.brighter().brighter());
                g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                //  paintDecreaseHighlight(g);
                //  paintIncreaseHighlight(g);

            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
            {

                g.setColor(secondary.darker().darker());
                g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);

                if (thumbBounds.isEmpty() || !scrollbar.isEnabled())
                {
                    return;
                }

                g.translate(thumbBounds.x, thumbBounds.y);
                g.translate(-thumbBounds.x, -thumbBounds.y);
            }
        });

        RetrievedListPane.add(RetrievedListScroll, null);

        RetrievedListPane.setVisible(true);

        filmPanel.add(rulesPane);
        filmPanel.add(RetrievedListPane);

        try
        {
            createRuleButtons();
        } catch (IOException ex)
        {
            Logger.getLogger(FilmGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        dividerOne = new JTextField(20);
        dividerOne.setHorizontalAlignment(SwingConstants.CENTER);
        dividerOne.setText(" File Download Settings ");
        dividerOne.setEditable(false);
        dividerOne.setFont(dividerOne.getFont().deriveFont(Font.BOLD));
        dividerOne.setForeground(primary);
        dividerOne.setBackground(secondary);
        dividerOne.setBorder(null);
        dividerOne.setSize(filmPanel.getWidth() - (rulesPane.getWidth() + RetrievedListPane.getWidth()), filmPanel.getHeight() / 14);
        dividerOne.setLocation(0, 0);
        dividerOne.setVisible(true);

        ruleName = new JTextField(20);
        ruleName.setText(" FILM NAME ");
        ruleName.setHorizontalAlignment(SwingConstants.CENTER);
        ruleName.setEditable(false);
        ruleName.setFont(ruleName.getFont().deriveFont(Font.BOLD));
        ruleName.setForeground(primary);
        ruleName.setBackground(secondary);
        ruleName.setBorder(null);
        ruleName.setSize(dividerOne.getWidth() / 4, dividerOne.getHeight());
        ruleName.setLocation(dividerOne.getX(), dividerOne.getY() + dividerOne.getHeight());
        ruleName.setVisible(true);

        ruleNameInput = new JTextField(20);
        ruleNameInput.setText("...");
        ruleNameInput.setEditable(true);
        ruleNameInput.setFont(ruleNameInput.getFont().deriveFont(Font.BOLD));
        ruleNameInput.setForeground(primary);
        ruleNameInput.setBackground(secondary.brighter());
        ruleNameInput.setBorder(border);
        ruleNameInput.setCaretColor(primary);
        ruleNameInput.setSize(dividerOne.getWidth() - ruleName.getWidth(), ruleName.getHeight());
        ruleNameInput.setLocation(ruleName.getLocation().x + ruleName.getWidth(), ruleName.getLocation().y);
        ruleNameInput.setVisible(true);

        searchIn = new JTextField(20);
        searchIn.setHorizontalAlignment(SwingConstants.CENTER);
        searchIn.setText(" SEARCH ");
        searchIn.setEditable(false);
        searchIn.setFont(searchIn.getFont().deriveFont(Font.BOLD));
        searchIn.setForeground(primary);
        searchIn.setBackground(secondary);
        searchIn.setBorder(null);
        searchIn.setSize(ruleName.getWidth(), ruleName.getHeight());
        searchIn.setLocation(ruleName.getLocation().x, ruleName.getLocation().y + ruleName.getHeight());
        searchIn.setVisible(true);

        search = new JTextField(20);
        search.setText("...");
        search.setEditable(true);
        search.setFont(search.getFont().deriveFont(Font.BOLD));
        search.setBorder(border);
        search.setForeground(primary);
        search.setBackground(secondary.brighter());
        search.setCaretColor(primary);
        search.setSize(ruleNameInput.getWidth(), ruleName.getHeight());
        search.setLocation(searchIn.getLocation().x + searchIn.getWidth(), searchIn.getLocation().y);
        search.setVisible(true);

        traktURL = new JTextField(20);
        traktURL.setHorizontalAlignment(SwingConstants.CENTER);
        traktURL.setText(" Trackt URL ");
        traktURL.setEditable(false);
        traktURL.setFont(traktURL.getFont().deriveFont(Font.BOLD));
        traktURL.setForeground(primary);
        traktURL.setBackground(secondary);
        traktURL.setBorder(null);
        traktURL.setSize(ruleName.getWidth(), ruleName.getHeight());
        traktURL.setLocation(searchIn.getLocation().x, searchIn.getLocation().y + searchIn.getHeight());
        traktURL.setVisible(true);

        traktURLInput = new JTextField(20);
        traktURLInput.setText("...");
        traktURLInput.setEditable(true);
        traktURLInput.setFont(traktURLInput.getFont().deriveFont(Font.BOLD));
        traktURLInput.setBorder(border);
        traktURLInput.setForeground(primary);
        traktURLInput.setBackground(secondary.brighter());
        traktURLInput.setCaretColor(primary);
        traktURLInput.setSize(ruleNameInput.getWidth(), ruleName.getHeight());
        traktURLInput.setLocation(traktURL.getLocation().x + traktURL.getWidth(), traktURL.getLocation().y);
        traktURLInput.setVisible(true);

        dividerTwo = new JTextField(20);
        dividerTwo.setHorizontalAlignment(SwingConstants.CENTER);
        dividerTwo.setText(" File Relocation Settings ");
        dividerTwo.setEditable(false);
        dividerTwo.setFont(dividerTwo.getFont().deriveFont(Font.BOLD));
        dividerTwo.setForeground(primary);
        dividerTwo.setBackground(secondary);
        dividerTwo.setBorder(null);
        dividerTwo.setSize(ruleName.getWidth() + ruleNameInput.getWidth(), ruleName.getHeight());
        dividerTwo.setLocation(traktURL.getX(), traktURL.getY() + traktURL.getHeight());
        dividerTwo.setVisible(true);

        searchInFolder = new JTextField(20);
        searchInFolder.setHorizontalAlignment(SwingConstants.CENTER);
        searchInFolder.setText(" Search In ");
        searchInFolder.setEditable(false);
        searchInFolder.setFont(searchInFolder.getFont().deriveFont(Font.BOLD));
        searchInFolder.setForeground(primary);
        searchInFolder.setBackground(secondary);
        searchInFolder.setBorder(null);
        searchInFolder.setSize(ruleName.getWidth(), ruleName.getHeight());
        searchInFolder.setLocation(dividerTwo.getLocation().x, dividerTwo.getLocation().y + dividerTwo.getHeight());
        searchInFolder.setVisible(true);

        searchInFolderText = new JTextField(20);
        searchInFolderText.setText("...");
        searchInFolderText.setEditable(true);
        searchInFolderText.setFont(searchInFolderText.getFont().deriveFont(Font.BOLD));
        searchInFolderText.setBorder(border);
        searchInFolderText.setForeground(primary);
        searchInFolderText.setBackground(secondary.brighter());
        searchInFolderText.setCaretColor(primary);
        searchInFolderText.setSize(ruleNameInput.getWidth(), ruleName.getHeight());
        searchInFolderText.setLocation(searchInFolder.getLocation().x + searchInFolder.getWidth(), searchInFolder.getLocation().y);
        searchInFolderText.setVisible(true);

        moveToFolder = new JTextField(20);
        moveToFolder.setHorizontalAlignment(SwingConstants.CENTER);
        moveToFolder.setText(" Move To ");
        moveToFolder.setEditable(false);
        moveToFolder.setFont(moveToFolder.getFont().deriveFont(Font.BOLD));
        moveToFolder.setForeground(primary);
        moveToFolder.setBackground(secondary);
        moveToFolder.setBorder(null);
        moveToFolder.setSize(ruleName.getWidth(), ruleName.getHeight());
        moveToFolder.setLocation(searchIn.getLocation().x, searchInFolder.getLocation().y + searchInFolder.getHeight());
        moveToFolder.setVisible(true);

        moveToFolderText = new JTextField(20);
        moveToFolderText.setText("...");
        moveToFolderText.setEditable(true);
        moveToFolderText.setFont(moveToFolderText.getFont().deriveFont(Font.BOLD));
        moveToFolderText.setBorder(border);
        moveToFolderText.setForeground(primary);
        moveToFolderText.setBackground(secondary.brighter());
        moveToFolderText.setCaretColor(primary);
        moveToFolderText.setSize(ruleNameInput.getWidth(), ruleName.getHeight());
        moveToFolderText.setLocation(moveToFolder.getLocation().x + moveToFolder.getWidth(), moveToFolder.getLocation().y);
        moveToFolderText.setVisible(true);

        searchFor = new JTextField(20);
        searchFor.setHorizontalAlignment(SwingConstants.CENTER);
        searchFor.setText(" Search For ");
        searchFor.setEditable(false);
        searchFor.setFont(searchFor.getFont().deriveFont(Font.BOLD));
        searchFor.setForeground(primary);
        searchFor.setBackground(secondary);
        searchFor.setBorder(null);
        searchFor.setSize(ruleName.getWidth(), ruleName.getHeight());
        searchFor.setLocation(moveToFolder.getLocation().x, moveToFolder.getLocation().y + moveToFolder.getHeight());
        searchFor.setVisible(true);

        searchForText = new JTextField(20);
        searchForText.setText("...");
        searchForText.setEditable(true);
        searchForText.setFont(searchForText.getFont().deriveFont(Font.BOLD));
        searchForText.setBorder(border);
        searchForText.setForeground(primary);
        searchForText.setBackground(secondary.brighter());
        searchForText.setCaretColor(primary);
        searchForText.setSize(ruleNameInput.getWidth(), ruleName.getHeight());
        searchForText.setLocation(searchFor.getLocation().x + searchFor.getWidth(), searchFor.getLocation().y);
        searchForText.setVisible(true);

        save = new JButton();
        // save.setFont(save.getFont().deriveFont(Font.BOLD));
        save.setContentAreaFilled(false);
        save.setOpaque(false);
        save.setFocusPainted(false);
        save.setSize(searchForText.getWidth() / 4, searchForText.getWidth() / 4);
        ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/save.png"));
        ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
        save.setIcon(ss);
        save.setLocation(searchForText.getX(), searchForText.getY() + searchForText.getHeight());
        save.setVisible(true);

        delete = new JButton();
        //  delete.setFont(delete.getFont().deriveFont(Font.BOLD));
        delete.setContentAreaFilled(false);
        delete.setOpaque(false);
        delete.setFocusPainted(false);
        delete.setSize(save.getWidth(), save.getWidth());
        ImageIcon d = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/delete.png"));
        ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
        delete.setIcon(ds);
        delete.setLocation((int) (((save.getX() + save.getWidth()))), save.getY());
        delete.setVisible(true);

        run = new JButton();
        //  run.setFont(run.getFont().deriveFont(Font.BOLD));
        run.setContentAreaFilled(false);
        run.setOpaque(false);
        run.setFocusPainted(false);
        run.setSize(save.getWidth(), save.getWidth());
        ImageIcon r = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/run.png"));
        ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
        run.setIcon(rs);
        run.setLocation((int) ((delete.getX() + delete.getWidth())), save.getY());
        run.setVisible(true);

        update = new JButton();
        //  run.setFont(run.getFont().deriveFont(Font.BOLD));
        update.setContentAreaFilled(false);
        update.setOpaque(false);
        update.setFocusPainted(false);
        update.setSize(save.getWidth(), save.getWidth());
        ImageIcon u = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/refresh.png"));
        ImageIcon us = new ImageIcon(u.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
        update.setIcon(us);
        update.setLocation((int) ((run.getX() + run.getWidth())), save.getY());
        update.setVisible(true);

        filmPanel.add(save);
        filmPanel.add(run);
        filmPanel.add(delete);
        filmPanel.add(update);
        filmPanel.add(searchForText);
        filmPanel.add(searchFor);
        filmPanel.add(moveToFolderText);
        filmPanel.add(moveToFolder);
        filmPanel.add(searchInFolderText);
        filmPanel.add(searchInFolder);
        filmPanel.add(dividerTwo);
        filmPanel.add(dividerOne);
        filmPanel.add(traktURLInput);
        filmPanel.add(traktURL);
        filmPanel.add(search);
        filmPanel.add(searchIn);
        filmPanel.add(ruleNameInput);
        filmPanel.add(ruleName);

        filmPanel.setVisible(true);

        initFilmControls();

    }

    public static void refreshFilmPanel()
    {
        //blank rules in ui
        rulesListPanel.removeAll();
        rulesListPanel.repaint();
        rulesScroll.revalidate();
        rulesScroll.repaint();

        RetrievedListEmptyPanel.removeAll();
        RetrievedListEmptyPanel.repaint();
        RetrievedListScroll.revalidate();
        RetrievedListScroll.repaint();

        try
        {
            createRuleButtons();
        } catch (IOException ex)
        {
            System.out.println(ex);
            Logger.getLogger(FilmGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  System.out.println(GUI.cb.getSelectedItem().toString().toLowerCase().trim());
        rulesListPanel.revalidate();
        rulesListPanel.repaint();
        rulesScroll.revalidate();
        rulesScroll.repaint();

        RetrievedListEmptyPanel.revalidate();
        RetrievedListEmptyPanel.repaint();
        RetrievedListScroll.revalidate();
        RetrievedListScroll.repaint();
    }

    private static void createRuleButtons() throws IOException
    {
        //Create rules list
        File[] rulesList = null;
        //load film show rules
        File folder = new File(Halen3.IO.FileManager.launchPath() + "/rules/films");
        //only load xml files,
        rulesList = folder.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File ruleNameInput)
            {
                return ruleNameInput.getName().toLowerCase().endsWith(".xml");
            }
        });
        //loop through rules and create buttons
        for (int i = 0; i < rulesList.length; i++)
        {

            makeRuleButton(i, rulesList);
            makeRuleTrackButton(i, rulesList);

//            final JButton button = new JButton();
//
//            // button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//            button.setName("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//            button.setForeground(primary);
//            button.setBackground(secondary.darker());
//
//            button.setMaximumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));
//            button.setMinimumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));
//            button.setPreferredSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));
//
//          //  System.out.println(new File(FileManager.returnTag("image", new Scanner(rulesList[i]).nextLine())).exists());
//            //if(new File(FileManager.launchPath() + "\\rules\\film show\\" + rulesList[i].getName().replace(".xml", ".png")).exists())
//            if (new File(FileManager.returnTag("image", new Scanner(rulesList[i]).nextLine())).exists())
//            {
//                //  File sourceimage = new File(FileManager.launchPath() + "\\rules\\film show\\" + rulesList[i].getName().replace(".xml", ".png"));
//
//                File sourceimage = new File(FileManager.returnTag("image", new Scanner(rulesList[i]).nextLine()));
//
//                Image image = ImageIO.read(sourceimage);
//
//                try
//                {
//                    button.setIcon(new ImageIcon(image.getScaledInstance(-1, rulesPane.getHeight() / 4,
//                            java.awt.Image.SCALE_SMOOTH)));
//                } catch (NullPointerException e)
//                {
//                    button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//
//                }
//            } else
//            {
//                button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//
//            }
//
//            button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont((float) button.getMaximumSize().height / 10));
//            Border border = new LineBorder(secondary, 3);
//            button.setBorder(border);
//            button.setContentAreaFilled(false);
//            button.setOpaque(true);
//            button.setFocusPainted(false);
//
//            button.addMouseListener(new MouseListener()
//            {
//
//                @Override
//                public void mouseClicked(MouseEvent me)
//                {
//
//                    showRule(button.getName().replace(button.getName().subSequence(button.getName().indexOf("R"), button.getName().indexOf(":") + 1), "").trim());
//     //loadSequenceContent(button.getName().replace(button.getName().subSequence(button.getName().indexOf("R"), button.getName().indexOf(":") + 1), "").trim());
//                }
//
//                @Override
//                public void mousePressed(MouseEvent me)
//                {
//                    button.setBackground(secondary.brighter().brighter());
//                }
//
//                @Override
//                public void mouseReleased(MouseEvent me)
//                {
//                    button.setBackground(secondary.darker());
//                }
//
//                @Override
//                public void mouseEntered(MouseEvent me)
//                {
//                    button.setBackground(secondary.darker().darker());
//                }
//
//                @Override
//                public void mouseExited(MouseEvent me)
//                {
//                    button.setBackground(secondary.darker());
//                }
//            });
//
//            rulesListPanel.add(button);
//
//            try
//            {
//                rulesListPanel.repaint();
//                rulesScroll.revalidate();
//                rulesScroll.repaint();
//            } catch (NullPointerException e)
//            {
//
//            }
        }
    }

    public static void makeRuleButton(int i, File[] rulesList) throws FileNotFoundException, IOException
    {
        final JButton button = new JButton();

        // button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
        button.setName("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
        button.setForeground(primary);
        button.setBackground(secondary.darker());

        button.setMaximumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));
        button.setMinimumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));
        button.setPreferredSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));

        //  System.out.println(new File(FileManager.returnTag("image", new Scanner(rulesList[i]).nextLine())).exists());
        //if(new File(FileManager.launchPath() + "\\rules\\film show\\" + rulesList[i].getName().replace(".xml", ".png")).exists())
        try
        {
            if (new File(FileManager.returnTag("image", new Scanner(rulesList[i]).nextLine())).exists())
            {
                //  File sourceimage = new File(FileManager.launchPath() + "\\rules\\film show\\" + rulesList[i].getName().replace(".xml", ".png"));

                File sourceimage = new File(FileManager.returnTag("image", new Scanner(rulesList[i]).nextLine()));

                Image image = ImageIO.read(sourceimage);

                try
                {
                    button.setIcon(new ImageIcon(image.getScaledInstance(-1, rulesPane.getHeight() / 4,
                            java.awt.Image.SCALE_SMOOTH)));
                } catch (NullPointerException e)
                {
                    button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());

                }
            } else
            {
                button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());

            }
        } catch (NoSuchElementException e)
        {
            button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());

        }

        button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont((float) button.getMaximumSize().height / 10));
        Border border = new LineBorder(secondary, 3);
        button.setBorder(border);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

                showRule(button.getName().replace(button.getName().subSequence(button.getName().indexOf("R"), button.getName().indexOf(":") + 1), "").trim());
                //loadSequenceContent(button.getName().replace(button.getName().subSequence(button.getName().indexOf("R"), button.getName().indexOf(":") + 1), "").trim());
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                button.setBackground(secondary.brighter().brighter());
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                button.setBackground(secondary.darker());
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                button.setBackground(secondary.darker().darker());
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                button.setBackground(secondary.darker());
            }
        });

        rulesListPanel.add(button);

        try
        {
            rulesListPanel.repaint();
            rulesScroll.revalidate();
            rulesScroll.repaint();
        } catch (NullPointerException e)
        {

        }
    }

    public static void makeRuleTrackButton(int i, File[] rulesList) throws FileNotFoundException, IOException
    {

        // System.out.println(FileManager.returnTag("retrieved", new Scanner(rulesList[i]).nextLine()));
        final JButton button = new JButton();

        // button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
        button.setName("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());

        button.setMaximumSize(new Dimension((int) (rulesPane.getWidth() / 1.3), rulesPane.getHeight() / 4));
        button.setMinimumSize(new Dimension((int) (rulesPane.getWidth() / 1.3), rulesPane.getHeight() / 4));
        button.setPreferredSize(new Dimension((int) (rulesPane.getWidth() / 1.3), rulesPane.getHeight() / 4));

        //  System.out.println(new File(FileManager.returnTag("image", new Scanner(rulesList[i]).nextLine())).exists());
        //if(new File(FileManager.launchPath() + "\\rules\\film show\\" + rulesList[i].getName().replace(".xml", ".png")).exists())
//            if (new File(FileManager.returnTag("image", new Scanner(rulesList[i]).nextLine())).exists())
//            {
//                //  File sourceimage = new File(FileManager.launchPath() + "\\rules\\film show\\" + rulesList[i].getName().replace(".xml", ".png"));
//
//                File sourceimage = new File(FileManager.returnTag("image", new Scanner(rulesList[i]).nextLine()));
//
//                Image image = ImageIO.read(sourceimage);
//
//                try
//                {
//                    button.setIcon(new ImageIcon(image.getScaledInstance(-1, rulesPane.getHeight() / 4,
//                            java.awt.Image.SCALE_SMOOTH)));
//                } catch (NullPointerException e)
//                {
//                    button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//
//                }
//            } else
//            {
        System.out.println(rulesList[i]);
        System.out.println(new Scanner(rulesList[i]).nextLine());
        System.out.println(FileManager.returnTag("retrieved", new Scanner(rulesList[i]).nextLine()));

        if (FileManager.returnTag("retrieved", new Scanner(rulesList[i]).nextLine()).equals("false"))
        {
            button.setText("Not Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
            button.setForeground(primary);
            button.setBackground(secondary.darker());
        } else if (FileManager.returnTag("retrieved", new Scanner(rulesList[i]).nextLine()).equals("true"))
        {
            button.setText("Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
            button.setForeground(primary);
            button.setBackground(tertiary.darker());
        }
        // }

        button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont((float) button.getMaximumSize().height / 10));
        Border border = new LineBorder(secondary, 3);
        button.setBorder(border);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

                if (button.getText().equals("Not Downloaded"))
                {
                    //set to downloaded
                    button.setText("Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
                    button.setForeground(primary);
                    button.setBackground(tertiary.darker());

                    try
                    {
                        //update tag and print out
                      String update = FileManager.updateTag("retrieved", new Scanner(rulesList[i]).nextLine(), "true");
                      PrintWriter out = new PrintWriter(rulesList[i]);
                        out.println(update);
                        out.close();

                       
                    } catch (FileNotFoundException ex)
                    {
                        Logger.getLogger(FilmGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                  
                } else if (button.getText().equals("Downloaded"))
                {
                    //set to not downloaded
                    button.setText("Not Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
                    button.setForeground(primary);
                    button.setBackground(secondary.darker());
                    try
                    {
                        //update tag and print out
                          String update = FileManager.updateTag("retrieved", new Scanner(rulesList[i]).nextLine(), "false");
                    
                        PrintWriter out = new PrintWriter(rulesList[i]);
                        out.println(update);
                      out.close();

                    } catch (FileNotFoundException ex)
                    {
                        Logger.getLogger(FilmGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

            @Override
            public void mousePressed(MouseEvent me)
            {

                //  button.setBackground(secondary.brighter().brighter());
                if (button.getText().equals("Not Downloaded"))
                {
                    // button.setText("Not Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
                    button.setForeground(primary);
                    button.setBackground(secondary.brighter());
                } else if (button.getText().equals("Downloaded"))
                {
                    // button.setText("Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
                    button.setForeground(primary);
                    button.setBackground(tertiary.brighter());
                }
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                // button.setBackground(secondary.darker());
                if (button.getText().equals("Not Downloaded"))
                {
                    // button.setText("Not Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
                    button.setForeground(primary);
                    button.setBackground(secondary.darker());
                } else if (button.getText().equals("Downloaded"))
                {
                    // button.setText("Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
                    button.setForeground(primary);
                    button.setBackground(tertiary.darker());
                }
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                // button.setBackground(secondary.darker().darker());
                if (button.getText().equals("Not Downloaded"))
                {
                    // button.setText("Not Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
                    button.setForeground(primary);
                    button.setBackground(secondary.darker().darker());
                } else if (button.getText().equals("Downloaded"))
                {
                    // button.setText("Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
                    button.setForeground(primary);
                    button.setBackground(tertiary.darker().darker());
                }
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                // button.setBackground(secondary.darker());

                if (button.getText().equals("Not Downloaded"))
                {
                    // button.setText("Not Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
                    button.setForeground(primary);
                    button.setBackground(secondary.darker());
                } else if (button.getText().equals("Downloaded"))
                {
                    // button.setText("Downloaded"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
                    button.setForeground(primary);
                    button.setBackground(tertiary.darker());
                }
            }
        });

        RetrievedListEmptyPanel.add(button);

        try
        {
            RetrievedListEmptyPanel.repaint();
            RetrievedListScroll.revalidate();
            RetrievedListScroll.repaint();
        } catch (NullPointerException e)
        {

        }
    }

//    private static void loadSequenceContent(final String file)
//    {
//
//        RetrievedListEmptyPanel.removeAll();
//        RetrievedListEmptyPanel.repaint();
//        RetrievedListScroll.revalidate();
//        RetrievedListScroll.repaint();
//
//        final List data = FileManager.readFile(FileManager.launchPath() + "/rules/films/" + file + ".xml");
//        //    System.out.println(data.getItemCount());
//        //  System.out.println(FileManager.launchPath() + "/" + type + "/" + file + ".xml");
//        for (int i = 1; i < data.getItemCount(); i++)
//        {
//            //  if (FileManager.returnTag("type", rules.getItem(i)).equals(type.trim()))
//            {
//                final JButton button = new JButton();
//
//                if (data.getItem(i).contains("true"))
//                {
//                    button.setText(data.getItem(i).subSequence(data.getItem(i).indexOf("<") + 1, data.getItem(i).indexOf(">")) + " - DOWNLOADED"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//                } else
//                {
//                    button.setText(data.getItem(i).subSequence(data.getItem(i).indexOf("<") + 1, data.getItem(i).indexOf(">")) + " - PENDING"); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//
//                }
//
//                if (button.getText().contains("PENDING"))
//                {
//                    button.setForeground(primary);
//                    button.setBackground(secondary.darker());
//                } else
//                {
//                    button.setForeground(primary);
//                    button.setBackground(tertiary.darker());
//                }
//
//                button.setMaximumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));
//                button.setMinimumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));
//                button.setPreferredSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));
//
//                // button.setMaximumSize(new Dimension(RetrievedListPane.getWidth() - 10, RetrievedListPane.getHeight() / 7));
//                //  button.setMinimumSize(new Dimension(RetrievedListPane.getWidth() - 10, RetrievedListPane.getHeight() / 7));
//                //  button.setPreferredSize(new Dimension(RetrievedListPane.getWidth() - 10, RetrievedListPane.getHeight() / 7));
//                button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont((float) button.getMaximumSize().height / 6));
//                Border border = new LineBorder(secondary, 3);
//                button.setBorder(border);
//                button.setContentAreaFilled(false);
//                button.setOpaque(true);
//                button.setFocusPainted(false);
//
//                button.addMouseListener(new MouseListener()
//                {
//
//                    @Override
//                    public void mouseClicked(MouseEvent me)
//                    {
//
//                        PrintWriter out = null;
//                        try
//                        {
//                            if (button.getText().contains("PENDING"))
//                            {
//                                button.setText(button.getText().replace("PENDING", "DOWNLOADED")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//                                button.setBackground(tertiary.darker());
//                            } else
//                            {
//                                button.setText(button.getText().replace("DOWNLOADED", "PENDING")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//                                button.setBackground(secondary.darker());
//                            }
//                            out = new PrintWriter(FileManager.launchPath() + "/rules/films/" + file + ".xml");
//                            for (int i = 0; i < data.getItemCount(); i++)
//                            {
//                                if (button.getText().contains(data.getItem(i).subSequence(data.getItem(i).indexOf("<") + 1, data.getItem(i).indexOf(">"))))
//                                {
//                                    if (button.getText().contains("DOWNLOADED"))
//                                    {
//                                        data.replaceItem(data.getItem(i).replace("false", "true"), i);
//                                    } else
//                                    {
//                                        data.replaceItem(data.getItem(i).replace("true", "false"), i);
//                                    }
//                                }
//
//                                out.println(data.getItem(i));
//                            }
//                            out.close();
//                        } catch (FileNotFoundException ex)
//                        {
//                            Logger.getLogger(FilmGUI.class.getName()).log(Level.SEVERE, null, ex);
//                        } finally
//                        {
//                            out.close();
//                        }
//                    }
//
//                    @Override
//                    public void mousePressed(MouseEvent me)
//                    {
//
//                        if (button.getText().contains("DOWNLOADED"))
//                        {
//                            //button.setText(button.getText().replace("PENDING", "DOWNLOADED")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//                            button.setBackground(tertiary.brighter().brighter());
//                        } else
//                        {
//                            // button.setText(button.getText().replace("DOWNLOADED", "PENDING")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//                            button.setBackground(secondary.brighter().brighter());
//                        }
//                    }
//
//                    @Override
//                    public void mouseReleased(MouseEvent me)
//                    {
//                        if (button.getText().contains("DOWNLOADED"))
//                        {
//                            //  button.setText(button.getText().replace("PENDING", "DOWNLOADED")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//                            button.setBackground(tertiary.darker());
//                        } else
//                        {
//                            // button.setText(button.getText().replace("DOWNLOADED", "PENDING")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//                            button.setBackground(secondary.darker());
//                        }
//                    }
//
//                    @Override
//                    public void mouseEntered(MouseEvent me)
//                    {
//
//                        if (button.getText().contains("DOWNLOADED"))
//                        {
//                            //  button.setText(button.getText().replace("PENDING", "DOWNLOADED")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//                            button.setBackground(tertiary.darker().darker());
//                        } else
//                        {
//                            // button.setText(button.getText().replace("DOWNLOADED", "PENDING")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//                            button.setBackground(secondary.darker().darker());
//                        }
//                    }
//
//                    @Override
//                    public void mouseExited(MouseEvent me)
//                    {
//
//                        if (button.getText().contains("DOWNLOADED"))
//                        {
//                            // button.setText(button.getText().replace("PENDING", "DOWNLOADED")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//                            button.setBackground(tertiary.darker());
//                        } else
//                        {
//                            //button.setText(button.getText().replace("DOWNLOADED", "PENDING")); //contactList.get(i).getSurruleNameInput() + ", " + contactList.get(i).getGivenName());
//                            button.setBackground(secondary.darker());
//                        }
//                    }
//                });
//
//                RetrievedListEmptyPanel.add(button);
//
//                try
//                {
//                    RetrievedListEmptyPanel.revalidate();
//                    RetrievedListEmptyPanel.repaint();
//                    RetrievedListScroll.revalidate();
//                    RetrievedListScroll.repaint();
//
//                } catch (NullPointerException e)
//                {
//
//                }
//
//            }
//        }
//
//        try
//        {
//            RetrievedListEmptyPanel.revalidate();
//            RetrievedListEmptyPanel.repaint();
//            RetrievedListScroll.revalidate();
//            RetrievedListScroll.repaint();
//
//        } catch (NullPointerException e)
//        {
//
//        }
//    }
    public static void showRule(String rule)
    {
        ruleNameInput.setText(rule);

        search.setText(FileManager.returnTag("search", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/films/" + rule.trim() + ".xml").getItem(0)));

        traktURLInput.setText(FileManager.returnTag("url", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/films/" + rule.trim() + ".xml").getItem(0)));

        searchInFolderText.setText(FileManager.returnTag("searchInFolder", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/films/" + rule.trim() + ".xml").getItem(0)));

        moveToFolderText.setText(FileManager.returnTag("moveToFolder", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/films/" + rule.trim() + ".xml").getItem(0)));

        searchForText.setText(FileManager.returnTag("searchFor", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/films/" + rule.trim() + ".xml").getItem(0)));

    }
}
