/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI.Comics;

import static Halen3.GUI.Comics.comicsGUIControls.initComicControls;
import Halen3.GUI.GUIBase;
import static Halen3.GUI.GUIBase.color;
import static Halen3.GUI.GUIBase.primary;
import static Halen3.GUI.GUIBase.secondary;
import static Halen3.GUI.GUIBase.tertiary;
import Halen3.IO.FileManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ComicsGUI
{

    public static JPanel comicPanel, rulesPane, rulesListPanel, issueListPane, issueListEmptyPanel;
      public static JButton save, delete, run, update;
    public static JScrollPane issueListScroll, rulesScroll;
     public static JTextField ruleName, ruleNameInput, comicURL, comicURLInput, dividerOne, /**dividerThree,**/ DownloadToFolder, DownloadToFolderText/**,moveToFolder, moveToFolderText, searchFor, searchForText, replaceThis, replaceThisText, withThis, withThisText**/;

    public static void addComicsPanel(int width, int height, int x, int y)
    {
        Border border = new LineBorder(secondary, 3);

        comicPanel = new JPanel();
        comicPanel.setSize(width, height);
        comicPanel.setLocation(x, y);
        comicPanel.setBackground(secondary);
        comicPanel.setLayout(null);
        comicPanel.setVisible(true);

        //#################################################################################
        //add panel that holds list of issues, ie list showing whats been downloaded and not for the 
        //selected rule
        //#################################################################################
        issueListPane = new JPanel();
        issueListPane.setLayout(new BorderLayout());
        // issueListPane.setSize(rulesPane.getX() - (search.getX() + search.getWidth() + (search.getHeight() / 6) * 2), (int) (frame.getHeight() / 1.3));
        issueListPane.setSize(comicPanel.getWidth() / 3, comicPanel.getHeight());
        //issueListPane.setLocation((int) (search.getX() + search.getWidth() + search.getHeight() / 6), cb.getY());
        issueListPane.setLocation(comicPanel.getWidth() - issueListPane.getWidth(), 0);

        // Set layout for contactListPane
        issueListEmptyPanel = new JPanel();
        BoxLayout layout2 = new BoxLayout(issueListEmptyPanel, BoxLayout.Y_AXIS);
        issueListEmptyPanel.setLayout(layout2);
        issueListEmptyPanel.setBackground(secondary.darker());
        //   contactListPanel.setSize(sequencePane.getWidth(), sequencePane.getHeight()/8);

        //   createRuleButtons(GUI.cb.getSelectedItem().toString().toLowerCase().trim());
        issueListScroll = new JScrollPane(issueListEmptyPanel);
        issueListScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        issueListScroll.getVerticalScrollBar().setUnitIncrement(16);
        //  Border border = new LineBorder(secondary, 2);
        issueListScroll.setBorder(border);
        issueListScroll.setBackground(secondary.darker());
        issueListScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI()
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

        issueListPane.add(issueListScroll, null);

        issueListPane.setVisible(true);
        //########################################################################

        //######################################################################################
        // create list to hold buttons for rules
        rulesPane = new JPanel();
        rulesPane.setLayout(new BorderLayout());
        rulesPane.setSize(comicPanel.getWidth() / 3, comicPanel.getHeight());
        rulesPane.setLocation(issueListPane.getX() - rulesPane.getWidth(), 0);

        // Set layout for contactListPane
        rulesListPanel = new JPanel();
        BoxLayout layout = new BoxLayout(rulesListPanel, BoxLayout.Y_AXIS);
        rulesListPanel.setLayout(layout);
        rulesListPanel.setBackground(secondary);

        rulesScroll = new JScrollPane(rulesListPanel);
        rulesScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rulesScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rulesScroll.getVerticalScrollBar().setUnitIncrement(16);
        rulesScroll.setBorder(border);
        rulesScroll.setBackground(secondary);
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

        try
        {
            createRuleButtons();
        } catch (IOException ex)
        {
            Logger.getLogger(ComicsGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        comicPanel.add(rulesPane);
        comicPanel.add(issueListPane);
        
                  dividerOne = new JTextField(20);
        dividerOne.setHorizontalAlignment(SwingConstants.CENTER);
        dividerOne.setText(" File Download Settings ");
        dividerOne.setEditable(false);
        dividerOne.setFont(dividerOne.getFont().deriveFont(Font.BOLD));
        dividerOne.setForeground(primary);
        dividerOne.setBackground(secondary);
        dividerOne.setBorder(null);
        dividerOne.setSize(comicPanel.getWidth() - (rulesPane.getWidth()+ issueListPane.getWidth()), comicPanel.getHeight() / 14);
        dividerOne.setLocation(0, 0);
        dividerOne.setVisible(true);

        ruleName = new JTextField(20);
        ruleName.setText(" COMIC NAME ");
        ruleName.setHorizontalAlignment(SwingConstants.CENTER);
        ruleName.setEditable(false);
        ruleName.setFont(ruleName.getFont().deriveFont(Font.BOLD));
        ruleName.setForeground(primary);
        ruleName.setBackground(secondary);
        ruleName.setBorder(null);
        ruleName.setSize(dividerOne.getWidth()/4, dividerOne.getHeight());
        ruleName.setLocation(dividerOne.getX(), dividerOne.getY() + dividerOne.getHeight() );
        ruleName.setVisible(true);

       
        ruleNameInput = new JTextField(20);
        ruleNameInput.setText("...");
        ruleNameInput.setEditable(true);
        ruleNameInput.setFont(ruleNameInput.getFont().deriveFont(Font.BOLD));
        ruleNameInput.setForeground(primary);
        ruleNameInput.setBackground(secondary.brighter());
        ruleNameInput.setBorder(border);
        ruleNameInput.setCaretColor(primary);
        ruleNameInput.setSize(dividerOne.getWidth()- ruleName.getWidth(), ruleName.getHeight());
        ruleNameInput.setLocation(ruleName.getLocation().x + ruleName.getWidth(), ruleName.getLocation().y);
        ruleNameInput.setVisible(true);     

        comicURL = new JTextField(20);
        comicURL.setHorizontalAlignment(SwingConstants.CENTER);
        comicURL.setText(" COMIC URL ");
        comicURL.setEditable(false);
        comicURL.setFont(comicURL.getFont().deriveFont(Font.BOLD));
        comicURL.setForeground(primary);
        comicURL.setBackground(secondary);
        comicURL.setBorder(null);
        comicURL.setSize(ruleName.getWidth(), ruleName.getHeight());
        comicURL.setLocation(ruleName.getLocation().x, ruleName.getLocation().y + ruleName.getHeight());
        comicURL.setVisible(true);

        comicURLInput = new JTextField(20);
        comicURLInput.setText("...");
        comicURLInput.setEditable(true);
        comicURLInput.setFont(comicURLInput.getFont().deriveFont(Font.BOLD));
        comicURLInput.setBorder(border);
        comicURLInput.setForeground(primary);
        comicURLInput.setBackground(secondary.brighter());
        comicURLInput.setCaretColor(primary);
        comicURLInput.setSize(ruleNameInput.getWidth(), ruleName.getHeight());
        comicURLInput.setLocation(comicURL.getLocation().x + comicURL.getWidth(), comicURL.getLocation().y);
        comicURLInput.setVisible(true);

        DownloadToFolder = new JTextField(20);
        DownloadToFolder.setHorizontalAlignment(SwingConstants.CENTER);
        DownloadToFolder.setText(" Download To ");
        DownloadToFolder.setEditable(false);
        DownloadToFolder.setFont(DownloadToFolder.getFont().deriveFont(Font.BOLD));
        DownloadToFolder.setForeground(primary);
        DownloadToFolder.setBackground(secondary);
        DownloadToFolder.setBorder(null);
        DownloadToFolder.setSize(ruleName.getWidth(), ruleName.getHeight());
        DownloadToFolder.setLocation(comicURL.getX(), comicURL.getY() + comicURL.getHeight());
        DownloadToFolder.setVisible(true);

        DownloadToFolderText = new JTextField(20);
        DownloadToFolderText.setText("...");
        DownloadToFolderText.setEditable(true);
        DownloadToFolderText.setFont(DownloadToFolderText.getFont().deriveFont(Font.BOLD));
        DownloadToFolderText.setBorder(border);
        DownloadToFolderText.setForeground(primary);
        DownloadToFolderText.setBackground(secondary.brighter());
        DownloadToFolderText.setCaretColor(primary);
        DownloadToFolderText.setSize(ruleNameInput.getWidth(), ruleName.getHeight());
        DownloadToFolderText.setLocation(DownloadToFolder.getLocation().x + DownloadToFolder.getWidth(), DownloadToFolder.getLocation().y);
        DownloadToFolderText.setVisible(true);
        
//           moveToFolder = new JTextField(20);
//        moveToFolder.setHorizontalAlignment(SwingConstants.CENTER);
//        moveToFolder.setText(" Move To ");
//        moveToFolder.setEditable(false);
//        moveToFolder.setFont(moveToFolder.getFont().deriveFont(Font.BOLD));
//        moveToFolder.setForeground(primary);
//        moveToFolder.setBackground(secondary);
//        moveToFolder.setBorder(null);
//        moveToFolder.setSize(ruleName.getWidth(), ruleName.getHeight());
//       moveToFolder.setLocation(DownloadToFolder.getLocation().x, DownloadToFolder.getLocation().y + DownloadToFolder.getHeight());
//        moveToFolder.setVisible(true);
//
//        moveToFolderText = new JTextField(20);
//        moveToFolderText.setText("...");
//        moveToFolderText.setEditable(true);
//        moveToFolderText.setFont(moveToFolderText.getFont().deriveFont(Font.BOLD));
//        moveToFolderText.setBorder(border);
//        moveToFolderText.setForeground(primary);
//        moveToFolderText.setBackground(secondary.brighter());
//        moveToFolderText.setCaretColor(primary);
//        moveToFolderText.setSize(ruleNameInput.getWidth(), ruleName.getHeight());
//        moveToFolderText.setLocation(moveToFolder.getLocation().x + moveToFolder.getWidth(), moveToFolder.getLocation().y);
//        moveToFolderText.setVisible(true);
//        
//           searchFor = new JTextField(20);
//        searchFor.setHorizontalAlignment(SwingConstants.CENTER);
//        searchFor.setText(" Search For ");
//        searchFor.setEditable(false);
//        searchFor.setFont(searchFor.getFont().deriveFont(Font.BOLD));
//        searchFor.setForeground(primary);
//        searchFor.setBackground(secondary);
//        searchFor.setBorder(null);
//        searchFor.setSize(ruleName.getWidth(), ruleName.getHeight());
//       searchFor.setLocation(moveToFolder.getLocation().x, moveToFolder.getLocation().y + moveToFolder.getHeight());
//        searchFor.setVisible(true);
//
//        searchForText = new JTextField(20);
//        searchForText.setText("...");
//        searchForText.setEditable(true);
//        searchForText.setFont(searchForText.getFont().deriveFont(Font.BOLD));
//        searchForText.setBorder(border);
//        searchForText.setForeground(primary);
//        searchForText.setBackground(secondary.brighter());
//        searchForText.setCaretColor(primary);
//        searchForText.setSize(ruleNameInput.getWidth(), ruleName.getHeight());
//        searchForText.setLocation(searchFor.getLocation().x + searchFor.getWidth(), searchFor.getLocation().y);
//        searchForText.setVisible(true);
//        
//         dividerThree = new JTextField(20);
//        dividerThree.setHorizontalAlignment(SwingConstants.CENTER);
//        dividerThree.setText(" Edit File Name Settings ");
//        dividerThree.setEditable(false);
//        dividerThree.setFont(dividerThree.getFont().deriveFont(Font.BOLD));
//        dividerThree.setForeground(primary);
//        dividerThree.setBackground(secondary);
//        dividerThree.setBorder(null);
//        dividerThree.setSize(ruleName.getWidth() + ruleNameInput.getWidth(), ruleName.getHeight());
//        dividerThree.setLocation(comicURL.getX(), searchForText.getY() + searchForText.getHeight());
//        dividerThree.setVisible(true);
//
//        replaceThis = new JTextField(20);
//        replaceThis.setHorizontalAlignment(SwingConstants.CENTER);
//        replaceThis.setText(" Replace This Text ");
//        replaceThis.setEditable(false);
//        replaceThis.setFont(replaceThis.getFont().deriveFont(Font.BOLD));
//        replaceThis.setForeground(primary);
//        replaceThis.setBackground(secondary);
//        replaceThis.setBorder(null);
//        replaceThis.setSize(ruleName.getWidth(), ruleName.getHeight());
//        replaceThis.setLocation(searchFor.getLocation().x, dividerThree.getLocation().y + dividerThree.getHeight());
//        replaceThis.setVisible(true);
//
//        replaceThisText = new JTextField(20);
//        replaceThisText.setText("...");
//        replaceThisText.setEditable(true);
//        replaceThisText.setFont(replaceThisText.getFont().deriveFont(Font.BOLD));
//        replaceThisText.setBorder(border);
//        replaceThisText.setForeground(primary);
//        replaceThisText.setBackground(secondary.brighter());
//        replaceThisText.setCaretColor(primary);
//        replaceThisText.setSize(ruleNameInput.getWidth(), ruleName.getHeight());
//        replaceThisText.setLocation(replaceThis.getLocation().x + replaceThis.getWidth(), replaceThis.getLocation().y);
//        replaceThisText.setVisible(true);
//        
//             withThis = new JTextField(20);
//        withThis.setHorizontalAlignment(SwingConstants.CENTER);
//        withThis.setText(" With This Text ");
//        withThis.setEditable(false);
//        withThis.setFont(withThis.getFont().deriveFont(Font.BOLD));
//        withThis.setForeground(primary);
//        withThis.setBackground(secondary);
//        withThis.setBorder(null);
//        withThis.setSize(ruleName.getWidth(), ruleName.getHeight());
//        withThis.setLocation(searchFor.getLocation().x, replaceThisText.getLocation().y + replaceThisText.getHeight());
//        withThis.setVisible(true);
//
//        withThisText = new JTextField(20);
//        withThisText.setText("...");
//        withThisText.setEditable(true);
//        withThisText.setFont(withThisText.getFont().deriveFont(Font.BOLD));
//        withThisText.setBorder(border);
//        withThisText.setForeground(primary);
//        withThisText.setBackground(secondary.brighter());
//        withThisText.setCaretColor(primary);
//        withThisText.setSize(ruleNameInput.getWidth(), ruleName.getHeight());
//        withThisText.setLocation(withThis.getLocation().x + withThis.getWidth(), withThis.getLocation().y);
//        withThisText.setVisible(true);
        
              save = new JButton();
        // save.setFont(save.getFont().deriveFont(Font.BOLD));
        save.setContentAreaFilled(false);
        save.setOpaque(false);
        save.setFocusPainted(false);
        save.setSize(DownloadToFolderText.getWidth() / 4, DownloadToFolderText.getWidth() / 4);
        ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/save.png"));
        ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
        save.setIcon(ss);
        save.setLocation(DownloadToFolderText.getX(), DownloadToFolderText.getY() + DownloadToFolderText.getHeight());
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
        
        
        comicPanel.add(save);
        comicPanel.add(run);
        comicPanel.add(delete);
        comicPanel.add(update);
        
     //        comicPanel.add(withThisText);
    //    comicPanel.add(withThis);
     //   comicPanel.add(replaceThis);
    //    comicPanel.add(replaceThisText);
      //  comicPanel.add(dividerThree);
     //   comicPanel.add(searchForText);
    //    comicPanel.add(searchFor);
    //    comicPanel.add(moveToFolderText);
    //    comicPanel.add(moveToFolder);
        comicPanel.add(DownloadToFolderText);
        comicPanel.add(DownloadToFolder);
        comicPanel.add(dividerOne);
        comicPanel.add(comicURLInput);
        comicPanel.add(comicURL);
        comicPanel.add(ruleNameInput);
        comicPanel.add(ruleName);
        
        initComicControls();

    }

    public static void refreshComicsPanel()
    {
        //blank rules in ui
        rulesListPanel.removeAll();
        rulesListPanel.repaint();
        rulesScroll.revalidate();
        rulesScroll.repaint();

        issueListEmptyPanel.removeAll();
        issueListEmptyPanel.repaint();
        issueListScroll.revalidate();
        issueListScroll.repaint();

        try
        {
            createRuleButtons();
        } catch (IOException ex)
        {
            Logger.getLogger(ComicsGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  System.out.println(GUI.cb.getSelectedItem().toString().toLowerCase().trim());
        rulesListPanel.revalidate();
        rulesListPanel.repaint();
        rulesScroll.revalidate();
        rulesScroll.repaint();

        issueListEmptyPanel.removeAll();
        issueListEmptyPanel.repaint();
        issueListScroll.revalidate();
        issueListScroll.repaint();
    }

    public static void main(String args[])
    {
        System.out.println(13%3);
    }
    private static void createRuleButtons() throws IOException
    {
        //Create rules list
        File[] rulesList = null;
        //load tv show rules
        File folder = new File(Halen3.IO.FileManager.launchPath() + "/rules/comics");
        //only load xml files,
        rulesList = folder.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File name)
            {
                return name.getName().toLowerCase().endsWith(".xml");
            }
        });

        for (int i = 0; i < rulesList.length; i += 3)
        {

            JPanel background = new JPanel();
            background.setMaximumSize(new Dimension(rulesPane.getWidth() - 50, rulesPane.getHeight() / 3));
            background.setMinimumSize(new Dimension(rulesPane.getWidth() - 50, rulesPane.getHeight() / 3));
            background.setPreferredSize(new Dimension(rulesPane.getWidth() - 50, rulesPane.getHeight() / 3));
            background.setLayout(new GridLayout(1, 3));
            background.setBackground(secondary);
            //   Border border = new LineBorder(secondary, 3);
            //    background.setBorder(border);
            //  background.setContentAreaFilled(false);
            background.setOpaque(true);
            //   background.setFocusPainted(false);

            addComicButton(rulesList, background, i, 0, 0);
            // System.out.print(rulesList[i] + " ");

            try
            {

                //  System.out.print(rulesList[(i+1)] + " ");
                addComicButton(rulesList, background, i + 1, 0, 0);
                addComicButton(rulesList, background, i + 2, 0, 0);
            
                //   System.out.print(rulesList[(i+2)] + " ");
                //  System.out.println("");
            } catch (ArrayIndexOutOfBoundsException e)
            {
                int loop = 1;
                if(rulesList.length%3 == 1)
                {
                   loop = 2; 
                }
                for(int j = 0; j < loop; j++)
                {
                    
                    final JButton button = new JButton();
                    button.setIcon(new ImageIcon(color(secondary.brighter(), "Resources/metro/BlankAssets/blankComic.png").getScaledInstance(-1, (rulesPane.getHeight() / 3) - 30,
                            java.awt.Image.SCALE_SMOOTH)));

                    button.setMaximumSize(new Dimension(button.getIcon().getIconWidth(), (rulesPane.getHeight() / 3)));
                    button.setMinimumSize(new Dimension(button.getIcon().getIconWidth(), (rulesPane.getHeight() / 3)));
                    button.setPreferredSize(new Dimension(button.getIcon().getIconWidth(), (rulesPane.getHeight() / 3)));
                    button.setContentAreaFilled(false);
                    button.setOpaque(true);
                    button.setFocusPainted(false);
                    button.setVisible(true);

                    background.add(button);
                }
            }

            rulesListPanel.add(background);

            try
            {
                rulesListPanel.repaint();
                rulesScroll.revalidate();
                rulesScroll.repaint();
            } catch (NullPointerException e)
            {

            }

        }
        
          rulesList = null;
        folder = null;
       

    }

    private static void addComicButton(File rulesList[], JPanel parent, int i, int x, int y) throws FileNotFoundException, IOException
    {
        final JButton button = new JButton();

        // button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
        button.setName("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
        button.setForeground(primary);
        button.setBackground(secondary.darker());

        //   button.setMaximumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 3));
        //   button.setMinimumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 3));
        //   button.setPreferredSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 3));
        //  button.setLocation(x, y);
        //  System.out.println(new File(FileManager.returnTag("imageURL", new Scanner(rulesList[i]).nextLine())));
        //if(new File(FileManager.launchPath() + "\\rules\\tv show\\" + rulesList[i].getName().replace(".xml", ".png")).exists())
        if (new File(FileManager.launchPath() + FileManager.returnTag("imageURL", new Scanner(rulesList[i]).nextLine())).exists())
        {
            //  File sourceimage = new File(FileManager.launchPath() + "\\rules\\tv show\\" + rulesList[i].getName().replace(".xml", ".png"));

            File sourceimage = new File(FileManager.launchPath() + FileManager.returnTag("imageURL", new Scanner(rulesList[i]).nextLine()));

            Image image = ImageIO.read(sourceimage);

            try
            {
                button.setIcon(new ImageIcon(image.getScaledInstance(-1, (rulesPane.getHeight() / 3) - 30,
                        java.awt.Image.SCALE_SMOOTH)));

                button.setMaximumSize(new Dimension(button.getIcon().getIconWidth(), (rulesPane.getHeight() / 3)));
                button.setMinimumSize(new Dimension(button.getIcon().getIconWidth(), (rulesPane.getHeight() / 3)));
                button.setPreferredSize(new Dimension(button.getIcon().getIconWidth(), (rulesPane.getHeight() / 3)));

            } catch (NullPointerException e)
            {
                button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());

            }
        } else
        {
            button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());

        }

        // button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont((float) button.getMaximumSize().height / 10));
        // Border border = new LineBorder(secondary, 3);
        //  button.setBorder(border);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

               showRule(button.getName().replace(button.getName().subSequence(button.getName().indexOf("R"), button.getName().indexOf(":") + 1), "").trim());
                     loadSequenceContent(button.getName().replace(button.getName().subSequence(button.getName().indexOf("R"), button.getName().indexOf(":") + 1), "").trim());
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
                button.setBackground(tertiary);

//                    Image img = ((ImageIcon) button.getIcon()).getImage();
//
//                    BufferedImage buffered = new BufferedImage(img.getWidth(null),
//                            img.getHeight(null), BufferedImage.TYPE_INT_RGB);
//                    buffered.getGraphics().drawImage(img, 0, 0, null);
//
//                    int darkenValue = 80; //higher = darker
//                    for (int i = 0; i < buffered.getWidth(); i++)
//                    {
//                        for (int j = 0; j < buffered.getHeight(); j++)
//                        {
//                            int rgb = buffered.getRGB(i, j);
//                            int alpha = (rgb >> 24) & 0x000000FF;
//                            Color c = new Color(rgb);
//                            if (alpha != 0)
//                            {
//                               // int red = (c.getRed() - darkenValue) <= 0 ? 0 : c.getRed() - 10;
//                              //  int green = (c.getGreen() - darkenValue) <= 0 ? 0 : c.getGreen() - 10;
//                             //   int blue = (c.getBlue() - darkenValue) <= 0 ? 0 : c.getBlue() - 10;
//                                
//                                 int red = (c.getRed() - darkenValue);
//                                int green = (c.getGreen() - darkenValue);
//                                int blue = (c.getBlue() - darkenValue);
//                                 
//                                if(red > 255)
//                                {
//                                    red = 255;
//                                }else if(red < 0)
//                                {
//                                    red = 0;
//                                }
//                                
//                                 if(blue > 255)
//                                {
//                                    blue = 255;
//                                }else if(blue < 0)
//                                {
//                                    blue = 0;
//                                }
//                                 
//                                  if(green > 255)
//                                {
//                                    green = 255;
//                                }else if(green < 0)
//                                {
//                                    green = 0;
//                                }
//                                c = new Color(red, green, blue);
//                                buffered.setRGB(i, j, c.getRGB());
//                            }
//                        }
//                    }
//                    button.setIcon(new ImageIcon(buffered));
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                button.setBackground(secondary.darker());

//                     Image img = ((ImageIcon) button.getIcon()).getImage();
//
//                    BufferedImage buffered = new BufferedImage(img.getWidth(null),
//                            img.getHeight(null), BufferedImage.TYPE_INT_RGB);
//                    buffered.getGraphics().drawImage(img, 0, 0, null);
//
//                    int darkenValue = 80; //higher = darker
//                    for (int i = 0; i < buffered.getWidth(); i++)
//                    {
//                        for (int j = 0; j < buffered.getHeight(); j++)
//                        {
//                            int rgb = buffered.getRGB(i, j);
//                            int alpha = (rgb >> 24) & 0x000000FF;
//                            Color c = new Color(rgb);
//                            if (alpha != 0)
//                            {
//                               // int red = (c.getRed() - darkenValue) <= 0 ? 0 : c.getRed() - 10;
//                              //  int green = (c.getGreen() - darkenValue) <= 0 ? 0 : c.getGreen() - 10;
//                             //   int blue = (c.getBlue() - darkenValue) <= 0 ? 0 : c.getBlue() - 10;
//                                
//                                 int red = (c.getRed() + darkenValue);
//                                int green = (c.getGreen() + darkenValue);
//                                int blue = (c.getBlue() + darkenValue);
//                                 
//                                if(red > 255)
//                                {
//                                    red = 255;
//                                }else if(red < 0)
//                                {
//                                    red = 0;
//                                }
//                                
//                                 if(blue > 255)
//                                {
//                                    blue = 255;
//                                }else if(blue < 0)
//                                {
//                                    blue = 0;
//                                }
//                                 
//                                  if(green > 255)
//                                {
//                                    green = 255;
//                                }else if(green < 0)
//                                {
//                                    green = 0;
//                                }
//                                c = new Color(red, green, blue);
//                                buffered.setRGB(i, j, c.getRGB());
//                            }
//                        }
//                    }
//                    button.setIcon(new ImageIcon(buffered));
            }
        });
        button.setVisible(true);

        parent.add(button);
        rulesList = null;
    }

//    private static void createRuleButtonsOld() throws IOException
//    {
//        //Create rules list
//        File[] rulesList = null;
//        //load tv show rules
//        File folder = new File(halen.FileManager.launchPath() + "/rules/comics");
//        //only load xml files,
//        rulesList = folder.listFiles(new FileFilter()
//        {
//            @Override
//            public boolean accept(File name)
//            {
//                return name.getName().toLowerCase().endsWith(".xml");
//            }
//        });
//        //loop through rules and create buttons
//        for (int i = 0; i < rulesList.length; i++)
//        {
//
//            final JButton button = new JButton();
//
//            // button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
//            button.setName("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
//            button.setForeground(primary);
//            button.setBackground(secondary.darker());
//
//            button.setMaximumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 3));
//            button.setMinimumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 3));
//            button.setPreferredSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 3));
//
//            System.out.println(new File(FileManager.returnTag("imageURL", new Scanner(rulesList[i]).nextLine())));
//            //if(new File(FileManager.launchPath() + "\\rules\\tv show\\" + rulesList[i].getName().replace(".xml", ".png")).exists())
//            if (new File(FileManager.returnTag("imageURL", new Scanner(rulesList[i]).nextLine())).exists())
//            {
//                //  File sourceimage = new File(FileManager.launchPath() + "\\rules\\tv show\\" + rulesList[i].getName().replace(".xml", ".png"));
//
//                File sourceimage = new File(FileManager.returnTag("imageURL", new Scanner(rulesList[i]).nextLine()));
//
//                Image image = ImageIO.read(sourceimage);
//
//                try
//                {
//                    button.setIcon(new ImageIcon(image.getScaledInstance(-1, rulesPane.getHeight() / 3,
//                            java.awt.Image.SCALE_SMOOTH)));
//
//                    button.setMaximumSize(new Dimension(button.getIcon().getIconWidth(), rulesPane.getHeight() / 3));
//                    button.setMinimumSize(new Dimension(button.getIcon().getIconWidth(), rulesPane.getHeight() / 3));
//                    button.setPreferredSize(new Dimension(button.getIcon().getIconWidth(), rulesPane.getHeight() / 3));
//
//                } catch (NullPointerException e)
//                {
//                    button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
//
//                }
//            } else
//            {
//                button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
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
//                    System.out.println(button.getName().replace(button.getName().subSequence(button.getName().indexOf("R"), button.getName().indexOf(":") + 1), "").trim());
//                    showRule(button.getName().replace(button.getName().subSequence(button.getName().indexOf("R"), button.getName().indexOf(":") + 1), "").trim());
//                    loadSequenceContent(button.getName().replace(button.getName().subSequence(button.getName().indexOf("R"), button.getName().indexOf(":") + 1), "").trim());
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
//
//                    Image img = ((ImageIcon) button.getIcon()).getImage();
//
//                    BufferedImage buffered = new BufferedImage(img.getWidth(null),
//                            img.getHeight(null), BufferedImage.TYPE_INT_RGB);
//                    buffered.getGraphics().drawImage(img, 0, 0, null);
//
//                    int darkenValue = 80; //higher = darker
//                    for (int i = 0; i < buffered.getWidth(); i++)
//                    {
//                        for (int j = 0; j < buffered.getHeight(); j++)
//                        {
//                            int rgb = buffered.getRGB(i, j);
//                            int alpha = (rgb >> 24) & 0x000000FF;
//                            Color c = new Color(rgb);
//                            if (alpha != 0)
//                            {
//                                // int red = (c.getRed() - darkenValue) <= 0 ? 0 : c.getRed() - 10;
//                                //  int green = (c.getGreen() - darkenValue) <= 0 ? 0 : c.getGreen() - 10;
//                                //   int blue = (c.getBlue() - darkenValue) <= 0 ? 0 : c.getBlue() - 10;
//
//                                int red = (c.getRed() - darkenValue);
//                                int green = (c.getGreen() - darkenValue);
//                                int blue = (c.getBlue() - darkenValue);
//
//                                if (red > 255)
//                                {
//                                    red = 255;
//                                } else if (red < 0)
//                                {
//                                    red = 0;
//                                }
//
//                                if (blue > 255)
//                                {
//                                    blue = 255;
//                                } else if (blue < 0)
//                                {
//                                    blue = 0;
//                                }
//
//                                if (green > 255)
//                                {
//                                    green = 255;
//                                } else if (green < 0)
//                                {
//                                    green = 0;
//                                }
//                                c = new Color(red, green, blue);
//                                buffered.setRGB(i, j, c.getRGB());
//                            }
//                        }
//                    }
//                    button.setIcon(new ImageIcon(buffered));
//                }
//
//                @Override
//                public void mouseExited(MouseEvent me)
//                {
//                    button.setBackground(secondary.darker());
//
//                    Image img = ((ImageIcon) button.getIcon()).getImage();
//
//                    BufferedImage buffered = new BufferedImage(img.getWidth(null),
//                            img.getHeight(null), BufferedImage.TYPE_INT_RGB);
//                    buffered.getGraphics().drawImage(img, 0, 0, null);
//
//                    int darkenValue = 80; //higher = darker
//                    for (int i = 0; i < buffered.getWidth(); i++)
//                    {
//                        for (int j = 0; j < buffered.getHeight(); j++)
//                        {
//                            int rgb = buffered.getRGB(i, j);
//                            int alpha = (rgb >> 24) & 0x000000FF;
//                            Color c = new Color(rgb);
//                            if (alpha != 0)
//                            {
//                                // int red = (c.getRed() - darkenValue) <= 0 ? 0 : c.getRed() - 10;
//                                //  int green = (c.getGreen() - darkenValue) <= 0 ? 0 : c.getGreen() - 10;
//                                //   int blue = (c.getBlue() - darkenValue) <= 0 ? 0 : c.getBlue() - 10;
//
//                                int red = (c.getRed() + darkenValue);
//                                int green = (c.getGreen() + darkenValue);
//                                int blue = (c.getBlue() + darkenValue);
//
//                                if (red > 255)
//                                {
//                                    red = 255;
//                                } else if (red < 0)
//                                {
//                                    red = 0;
//                                }
//
//                                if (blue > 255)
//                                {
//                                    blue = 255;
//                                } else if (blue < 0)
//                                {
//                                    blue = 0;
//                                }
//
//                                if (green > 255)
//                                {
//                                    green = 255;
//                                } else if (green < 0)
//                                {
//                                    green = 0;
//                                }
//                                c = new Color(red, green, blue);
//                                buffered.setRGB(i, j, c.getRGB());
//                            }
//                        }
//                    }
//                    button.setIcon(new ImageIcon(buffered));
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
//
//        }
//    }

    private static void loadSequenceContent(final String file)
    {

        issueListEmptyPanel.removeAll();
        issueListEmptyPanel.repaint();
        issueListScroll.revalidate();
        issueListScroll.repaint();

        final List data = FileManager.readFile(FileManager.launchPath() + "/rules/comics/" + file + ".xml");
        //    System.out.println(data.getItemCount());
        //  System.out.println(FileManager.launchPath() + "/" + type + "/" + file + ".xml");
        for (int i = 1; i < data.getItemCount(); i++)
        {
            //  if (FileManager.returnTag("type", rules.getItem(i)).equals(type.trim()))
            {
                final JButton button = new JButton();

                if (data.getItem(i).contains("true"))
                {
                    button.setText(FileManager.returnTag("name", data.getItem(i)) + " - DOWNLOADED"); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                } else
                {
                    button.setText(FileManager.returnTag("name", data.getItem(i)) + " - PENDING"); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());

                }

                if (button.getText().contains("PENDING"))
                {
                    button.setForeground(primary);
                    button.setBackground(secondary.darker());
                } else
                {
                    button.setForeground(primary);
                    button.setBackground(tertiary.darker());
                }

                button.setMaximumSize(new Dimension(issueListPane.getWidth() - 10, issueListPane.getHeight() / 7));
                button.setMinimumSize(new Dimension(issueListPane.getWidth() - 10, issueListPane.getHeight() / 7));
                button.setPreferredSize(new Dimension(issueListPane.getWidth() - 10, issueListPane.getHeight() / 7));
                button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont((float) button.getMaximumSize().height / 6));
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

                        PrintWriter out = null;
                        try
                        {
                            if (button.getText().contains("PENDING"))
                            {
                                button.setText(button.getText().replace("PENDING", "DOWNLOADED")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                                button.setBackground(tertiary.darker());
                            } else
                            {
                                button.setText(button.getText().replace("DOWNLOADED", "PENDING")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                                button.setBackground(secondary.darker());
                            }
                            out = new PrintWriter(FileManager.launchPath() + "/rules/comics/" + file + ".xml");
                            for (int i = 0; i < data.getItemCount(); i++)
                            {
                                if (button.getText().contains(FileManager.returnTag("name", data.getItem(i))))
                                {
                                    if (button.getText().contains("DOWNLOADED"))
                                    {
                                        data.replaceItem(data.getItem(i).replace("false", "true"), i);
                                    } else
                                    {
                                        data.replaceItem(data.getItem(i).replace("true", "false"), i);
                                    }
                                }

                                out.println(data.getItem(i));
                            }
                            out.close();
                        } catch (FileNotFoundException ex)
                        {
                            Logger.getLogger(ComicsGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } finally
                        {
                            out.close();
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent me)
                    {

                        if (button.getText().contains("DOWNLOADED"))
                        {
                            //button.setText(button.getText().replace("PENDING", "DOWNLOADED")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                            button.setBackground(tertiary.brighter().brighter());
                        } else
                        {
                            // button.setText(button.getText().replace("DOWNLOADED", "PENDING")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                            button.setBackground(secondary.brighter().brighter());
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent me)
                    {
                        if (button.getText().contains("DOWNLOADED"))
                        {
                            //  button.setText(button.getText().replace("PENDING", "DOWNLOADED")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                            button.setBackground(tertiary.darker());
                        } else
                        {
                            // button.setText(button.getText().replace("DOWNLOADED", "PENDING")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                            button.setBackground(secondary.darker());
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent me)
                    {

                        if (button.getText().contains("DOWNLOADED"))
                        {
                            //  button.setText(button.getText().replace("PENDING", "DOWNLOADED")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                            button.setBackground(tertiary.darker().darker());
                        } else
                        {
                            // button.setText(button.getText().replace("DOWNLOADED", "PENDING")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                            button.setBackground(secondary.darker().darker());
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent me)
                    {

                        if (button.getText().contains("DOWNLOADED"))
                        {
                            // button.setText(button.getText().replace("PENDING", "DOWNLOADED")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                            button.setBackground(tertiary.darker());
                        } else
                        {
                            //button.setText(button.getText().replace("DOWNLOADED", "PENDING")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                            button.setBackground(secondary.darker());
                        }
                    }
                });

                issueListEmptyPanel.add(button);

                try
                {
                    issueListEmptyPanel.revalidate();
                    issueListEmptyPanel.repaint();
                    issueListScroll.revalidate();
                    issueListScroll.repaint();

                } catch (NullPointerException e)
                {

                }

            }
        }

        try
        {
            issueListEmptyPanel.revalidate();
            issueListEmptyPanel.repaint();
            issueListScroll.revalidate();
            issueListScroll.repaint();

        } catch (NullPointerException e)
        {

        }
    }
    
            public static void showRule(String rule)
    {
        ruleNameInput.setText(rule);
      
       // search.setText(FileManager.returnTag("search", FileManager.readFile(halen.FileManager.launchPath() + "/rules/tv show/" + rule.trim() + ".xml").getItem(0)));
        
        comicURLInput.setText(FileManager.returnTag("url", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/comics/" + rule.trim() + ".xml").getItem(0)));
    
        DownloadToFolderText.setText(FileManager.returnTag("downloadTo", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/comics/" + rule.trim() + ".xml").getItem(0)));
    
       //  moveToFolderText.setText(FileManager.returnTag("moveToFolder", FileManager.readFile(halen.FileManager.launchPath() + "/rules/comics/" + rule.trim() + ".xml").getItem(0)));
    
       //   searchForText.setText(FileManager.returnTag("searchFor", FileManager.readFile(halen.FileManager.launchPath() + "/rules/comics/" + rule.trim() + ".xml").getItem(0)));
    
    
    }
}
