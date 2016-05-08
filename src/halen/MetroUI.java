package halen;

import Rules.Show;
import static halen.GUI.anim;
import static halen.GUI.cb;
import static halen.GUI.close;
import static halen.GUI.color;
import static halen.GUI.createImage;
import static halen.GUI.delete;
import static halen.GUI.frame;
import static halen.GUI.name;
import static halen.GUI.progressBar;
import static halen.GUI.ruleName;
import static halen.GUI.rulesListPanel;
import static halen.GUI.rulesPane;
import static halen.GUI.rulesScroll;
import static halen.GUI.run;
import static halen.GUI.runningRule;
import static halen.GUI.save;
import static halen.GUI.screenH;
import static halen.GUI.screenW;
import static halen.GUI.search;
import static halen.GUI.searchIn;
import static halen.GUI.settings;
import static halen.Main.primary;
import static halen.Main.secondary;
import static halen.Main.tertiary;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * @author TAIBHSE
 */
public class MetroUI
{

    public static JButton setTheme, maximise, minimise;

    public static JPanel sequencePane, sequenceListPanel, inputs;
    public static JScrollPane sequenceScroll;

    public static JTextField textQuantity, quantity, dividerOne, dividerTwo, searchInFolder, searchInFolderText, moveToFolder, moveToFolderText, searchFor, searchForText;//, dividerTwo, searchInFolder;

    // public static Color tertiary = new Color(100,100,250);
    public static void UI()
    {

        //create main frame
        frame = new JFrame("HALEN");

        frame.setSize(Main.frameW, Main.frameH); //ensure frame fits within screen
        frame.setLocation(screenW / 2 - frame.getWidth() / 2, screenH / 2 - frame.getHeight() / 2); //default frame to be located at centre of screen
        //    frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(null);
        frame.setResizable(false); //prevent frame resizing as this messes up orientation of buttons and other elements
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setIconImage(createImage("Resources/icon.png")); //create icon
        frame.setUndecorated(true);
        frame.setBackground(primary);

        UIManager.put("ComboBox.background", new ColorUIResource(secondary));
        UIManager.put("ComboBox.foreground", new ColorUIResource(primary));
        // UIManager.put("JTextField.background", new ColorUIResource(Color.yellow));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(secondary.darker()));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(primary));

        UIManager.put("ProgressBar.foreground", secondary);
        UIManager.put("ProgressBar.selectionBackground", secondary);
        UIManager.put("ProgressBar.selectionForeground", primary);

        String[] choices =
        {
            " TV SHOW", " COMICS", " ANIME"
        };

        cb = new JComboBox<String>(choices);
        cb.setSize(frame.getWidth() / 6, frame.getHeight() / 15);
        cb.setLocation((int) (cb.getHeight() * 0.2), (int) (cb.getHeight() * 1.5));
        cb.setFont(cb.getFont().deriveFont(Font.BOLD).deriveFont(32.5f));
        cb.setBorder(null);
        cb.setUI(ColorArrowUI.createUI(cb));
        cb.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        cb.setVisible(true);

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

        ImageIcon f = new ImageIcon(color(Main.secondary, "Resources/metro/back/back.png"));
        ImageIcon fs = new ImageIcon(f.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), java.awt.Image.SCALE_DEFAULT));

        JLabel back = new JLabel();
        back.setSize(frame.getWidth(), frame.getHeight());
        back.setIcon(fs);
        back.setVisible(true);

        ImageIcon a = new ImageIcon(color(secondary, "Resources/metro/types/tv.png"));
        ImageIcon as = new ImageIcon(a.getImage().getScaledInstance((int) (frame.getHeight() / 1.2), (int) (frame.getHeight() / 1.2), java.awt.Image.SCALE_DEFAULT));

        anim = new JLabel();
        anim.setLayout(null);
        anim.setIcon(as);
        anim.setSize(anim.getIcon().getIconWidth(), anim.getIcon().getIconHeight());
        anim.setLocation((frame.getWidth() / 2) - (anim.getWidth() / 2), frame.getHeight() / 11);
        anim.setVisible(false);

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
        
           inputs = new JPanel();
        inputs.setLayout(null);
        inputs.setSize(cb.getWidth() + (int) (frame.getWidth() / 3.55), cb.getHeight() * 8);
        inputs.setLocation(cb.getX(), cb.getY() + cb.getHeight() + cb.getHeight() / 2);
        inputs.setBackground(secondary);
        inputs.setVisible(true);
        
         dividerOne = new JTextField(20);
        dividerOne.setHorizontalAlignment(SwingConstants.CENTER);
        dividerOne.setText(" File Download Settings ");
        dividerOne.setEditable(false);
        dividerOne.setFont(dividerOne.getFont().deriveFont(Font.BOLD));
        dividerOne.setForeground(primary);
        dividerOne.setBackground(secondary);
        dividerOne.setBorder(null);
        dividerOne.setSize(cb.getWidth() + (int) (frame.getWidth() / 3.5), frame.getHeight() / 15);
     //   dividerOne.setLocation(cb.getX(), cb.getY() + cb.getHeight() + cb.getHeight() / 2);
        dividerOne.setVisible(true);

        ruleName = new JTextField(20);
        ruleName.setText(" TV SHOW NAME ");
        ruleName.setHorizontalAlignment(SwingConstants.CENTER);
        ruleName.setEditable(false);
        ruleName.setFont(ruleName.getFont().deriveFont(Font.BOLD));
        ruleName.setForeground(primary);
        ruleName.setBackground(secondary);
        ruleName.setBorder(null);
        ruleName.setSize(cb.getWidth(), frame.getHeight() / 15);
        ruleName.setLocation(dividerOne.getX(), dividerOne.getY() + dividerOne.getHeight() );
        ruleName.setVisible(true);

        Border border = new LineBorder(secondary, 3);
        name = new JTextField(20);
        name.setText("...");
        name.setEditable(true);
        name.setFont(name.getFont().deriveFont(Font.BOLD));
        name.setForeground(primary);
        name.setBackground(secondary.brighter());
        name.setBorder(border);
        name.setCaretColor(primary);
        name.setSize((int) (frame.getWidth() / 3.5), frame.getHeight() / 15);
        name.setLocation(ruleName.getLocation().x + ruleName.getWidth(), ruleName.getLocation().y);
        name.setVisible(true);

        searchIn = new JTextField(20);
        searchIn.setHorizontalAlignment(SwingConstants.CENTER);
        searchIn.setText(" SEARCH ");
        searchIn.setEditable(false);
        searchIn.setFont(searchIn.getFont().deriveFont(Font.BOLD));
        searchIn.setForeground(primary);
        searchIn.setBackground(secondary);
        searchIn.setBorder(null);
        searchIn.setSize(ruleName.getWidth(), frame.getHeight() / 15);
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
        search.setSize(name.getWidth(), frame.getHeight() / 15);
        search.setLocation(searchIn.getLocation().x + searchIn.getWidth(), searchIn.getLocation().y);
        search.setVisible(true);

     

        textQuantity = new JTextField(20);
        textQuantity.setHorizontalAlignment(SwingConstants.CENTER);
        textQuantity.setText(" Trackt URL ");
        textQuantity.setEditable(false);
        textQuantity.setFont(textQuantity.getFont().deriveFont(Font.BOLD));
        textQuantity.setForeground(primary);
        textQuantity.setBackground(secondary);
        textQuantity.setBorder(null);
        textQuantity.setSize(ruleName.getWidth(), frame.getHeight() / 15);
        textQuantity.setLocation(searchIn.getLocation().x, searchIn.getLocation().y + searchIn.getHeight());
        textQuantity.setVisible(true);

        quantity = new JTextField(20);
        quantity.setText("...");
        quantity.setEditable(true);
        quantity.setFont(quantity.getFont().deriveFont(Font.BOLD));
        quantity.setBorder(border);
        quantity.setForeground(primary);
        quantity.setBackground(secondary.brighter());
        quantity.setCaretColor(primary);
        quantity.setSize(name.getWidth(), frame.getHeight() / 15);
        quantity.setLocation(textQuantity.getLocation().x + textQuantity.getWidth(), textQuantity.getLocation().y);
        quantity.setVisible(true);

        dividerTwo = new JTextField(20);
        dividerTwo.setHorizontalAlignment(SwingConstants.CENTER);
        dividerTwo.setText(" File Relocation Settings ");
        dividerTwo.setEditable(false);
        dividerTwo.setFont(dividerTwo.getFont().deriveFont(Font.BOLD));
        dividerTwo.setForeground(primary);
        dividerTwo.setBackground(secondary);
        dividerTwo.setBorder(null);
        dividerTwo.setSize(ruleName.getWidth() + name.getWidth(), frame.getHeight() / 15);
        dividerTwo.setLocation(textQuantity.getX(), textQuantity.getY() + textQuantity.getHeight());
        dividerTwo.setVisible(true);

        searchInFolder = new JTextField(20);
        searchInFolder.setHorizontalAlignment(SwingConstants.CENTER);
        searchInFolder.setText(" Search In ");
        searchInFolder.setEditable(false);
        searchInFolder.setFont(searchInFolder.getFont().deriveFont(Font.BOLD));
        searchInFolder.setForeground(primary);
        searchInFolder.setBackground(secondary);
        searchInFolder.setBorder(null);
        searchInFolder.setSize(ruleName.getWidth(), frame.getHeight() / 15);
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
        searchInFolderText.setSize(name.getWidth(), frame.getHeight() / 15);
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
        moveToFolder.setSize(ruleName.getWidth(), frame.getHeight() / 15);
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
        moveToFolderText.setSize(name.getWidth(), frame.getHeight() / 15);
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
        searchFor.setSize(ruleName.getWidth(), frame.getHeight() / 15);
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
        searchForText.setSize(name.getWidth(), frame.getHeight() / 15);
        searchForText.setLocation(searchFor.getLocation().x + searchFor.getWidth(), searchFor.getLocation().y);
        searchForText.setVisible(true);

        
        rulesPane = new JPanel();
        rulesPane.setLayout(new BorderLayout());
        rulesPane.setSize((int) (frame.getWidth() / 3), (int) (frame.getHeight() / 1.3));
        rulesPane.setLocation((int) (frame.getWidth() - (rulesPane.getWidth() * 1.05f)), cb.getY());

        // Set layout for contactListPane
        rulesListPanel = new JPanel();
        BoxLayout layout = new BoxLayout(rulesListPanel, BoxLayout.Y_AXIS);
        rulesListPanel.setLayout(layout);
        rulesListPanel.setBackground(secondary);
        //   contactListPanel.setSize(rulesPane.getWidth(), rulesPane.getHeight()/8);

        createRuleButtons(GUI.cb.getSelectedItem().toString().toLowerCase().trim());

        rulesScroll = new JScrollPane(rulesListPanel);
        rulesScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rulesScroll.getVerticalScrollBar().setUnitIncrement(16);
        //   Border border = new LineBorder(secondary, 2);
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

        //#################################################################################
        sequencePane = new JPanel();
        sequencePane.setLayout(new BorderLayout());
        sequencePane.setSize(rulesPane.getX() - (search.getX() + search.getWidth() + (search.getHeight() / 6) * 2), (int) (frame.getHeight() / 1.3));
        sequencePane.setLocation((int) (search.getX() + search.getWidth() + search.getHeight() / 6), cb.getY());

        // Set layout for contactListPane
        sequenceListPanel = new JPanel();
        BoxLayout layout2 = new BoxLayout(sequenceListPanel, BoxLayout.Y_AXIS);
        sequenceListPanel.setLayout(layout2);
        sequenceListPanel.setBackground(secondary);
        //   contactListPanel.setSize(sequencePane.getWidth(), sequencePane.getHeight()/8);

        //   createRuleButtons(GUI.cb.getSelectedItem().toString().toLowerCase().trim());
        sequenceScroll = new JScrollPane(sequenceListPanel);
        sequenceScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sequenceScroll.getVerticalScrollBar().setUnitIncrement(16);
        //  Border border = new LineBorder(secondary, 2);
        sequenceScroll.setBorder(border);
        sequenceScroll.setBackground(secondary);
        sequenceScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI()
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

        sequencePane.add(sequenceScroll, null);

        sequencePane.setVisible(true);

        setTheme = new JButton();
        setTheme.setFont(setTheme.getFont().deriveFont(Font.BOLD));
        setTheme.setContentAreaFilled(false);
        setTheme.setOpaque(false);
        setTheme.setFocusPainted(false);
        setTheme.setSize(frame.getWidth() / 22, frame.getWidth() / 22);
        ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/theme.png"));
        ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(setTheme.getWidth(), setTheme.getHeight(), java.awt.Image.SCALE_DEFAULT));
        setTheme.setIcon(ts);
        setTheme.setLocation((rulesPane.getX() + rulesPane.getWidth()) - setTheme.getWidth(), rulesPane.getY() + rulesPane.getHeight() + (setTheme.getHeight() / 8));
        setTheme.setVisible(true);

        settings = new JLabel();
        ImageIcon settingsFullScale = new ImageIcon(color(tertiary, "Resources/metro/buttons/settings.png"));
        int settingsW = setTheme.getHeight();
        int settingsH = setTheme.getHeight();
        ImageIcon settingsScaled = new ImageIcon(settingsFullScale.getImage().getScaledInstance(settingsW, settingsH, java.awt.Image.SCALE_DEFAULT));
        settings.setIcon(settingsScaled);
        settings.setSize(settings.getIcon().getIconWidth(), settings.getIcon().getIconHeight());
        settings.setLocation((int) (setTheme.getX() - settings.getWidth() * 1.5), setTheme.getY());
        settings.setVisible(true);

        GUI.updateRulesData = new JLabel();
        ImageIcon updateRulesDataFullScale = new ImageIcon(color(tertiary, "Resources/metro/buttons/refresh.png"));
        int updateRulesDataW = setTheme.getHeight();
        int updateRulesDataH = setTheme.getHeight();
        ImageIcon updateRulesDataScaled = new ImageIcon(updateRulesDataFullScale.getImage().getScaledInstance(updateRulesDataW, updateRulesDataH, java.awt.Image.SCALE_DEFAULT));
        GUI.updateRulesData.setIcon(updateRulesDataScaled);
        GUI.updateRulesData.setSize(GUI.updateRulesData.getIcon().getIconWidth(), GUI.updateRulesData.getIcon().getIconHeight());
        GUI.updateRulesData.setLocation((int) (setTheme.getX() - settings.getWidth() * 3), setTheme.getY());
        GUI.updateRulesData.setVisible(true);

        GUI.trakt = new JLabel();
        ImageIcon traktScale = new ImageIcon(color(tertiary, "Resources/metro/buttons/trakt.png"));
        int traktW = setTheme.getHeight();
        int traktH = setTheme.getHeight();
        ImageIcon traktScaled = new ImageIcon(traktScale.getImage().getScaledInstance(traktW, traktH, java.awt.Image.SCALE_DEFAULT));
        GUI.trakt.setIcon(traktScaled);
        GUI.trakt.setSize(GUI.trakt.getIcon().getIconWidth(), GUI.trakt.getIcon().getIconHeight());
        GUI.trakt.setLocation((int) (setTheme.getX() - settings.getWidth() * 9), setTheme.getY());
        GUI.trakt.setVisible(true);

        save = new JButton();
        save.setFont(save.getFont().deriveFont(Font.BOLD));
        save.setContentAreaFilled(false);
        save.setOpaque(false);
        save.setFocusPainted(false);
        save.setSize(frame.getWidth() / 8, frame.getWidth() / 8);
        ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/save.png"));
        ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
        save.setIcon(ss);
        save.setLocation(ruleName.getX() + ruleName.getHeight(), (int) ((rulesPane.getY() + rulesPane.getHeight()) - (save.getHeight()/2)));
        save.setVisible(true);

        delete = new JButton();
        delete.setFont(delete.getFont().deriveFont(Font.BOLD));
        delete.setContentAreaFilled(false);
        delete.setOpaque(false);
        delete.setFocusPainted(false);
        delete.setSize(frame.getWidth() / 8, frame.getWidth() / 8);
        ImageIcon d = new ImageIcon(color(tertiary, "Resources/metro/buttons/delete.png"));
        ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
        delete.setIcon(ds);
        delete.setLocation((int) (((save.getX() + save.getWidth()))), save.getY());
        delete.setVisible(true);

        run = new JButton();
        run.setFont(run.getFont().deriveFont(Font.BOLD));
        run.setContentAreaFilled(false);
        run.setOpaque(false);
        run.setFocusPainted(false);
        run.setSize(frame.getWidth() / 8, frame.getWidth() / 8);
        ImageIcon r = new ImageIcon(color(tertiary, "Resources/metro/buttons/run.png"));
        ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
        run.setIcon(rs);
        run.setLocation((int) ((delete.getX() + delete.getWidth())), save.getY());
        run.setVisible(true);

        try
        {

            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            System.out.println("\n\n------------------------------------------------------------\nsetting ui: " + UIManager.getSystemLookAndFeelClassName() + "\n\n------------------------------------------------------------\n");

        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            //  System.out.println("\n\n------------------------------------------------------------\nUNKNOWN UI - DEFAULTING\n\n------------------------------------------------------------\n");

        }

        progressBar = new JProgressBar(0, 100);
        progressBar.setSize((int) (frame.getWidth() / 1.5), frame.getHeight() / 7);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setLocation((frame.getWidth() / 2) - (progressBar.getWidth() / 2), (int) (frame.getHeight() - (progressBar.getHeight() * 1.5)));
        progressBar.setBackground(primary);
        progressBar.setForeground(secondary);
//        runningRule.setBorder(null);
        progressBar.setVisible(false);
//  try
//        {
//
//            // Set System L&F
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            System.out.println("\n\n------------------------------------------------------------\nsetting ui: " + UIManager.getSystemLookAndFeelClassName() + "\n\n------------------------------------------------------------\n");
//
//        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
//        {
//          //  System.out.println("\n\n------------------------------------------------------------\nUNKNOWN UI - DEFAULTING\n\n------------------------------------------------------------\n");
//
//        }
        runningRule = new JTextField(20);
        runningRule.setText("NO RULE RUNNING.....");
        runningRule.setEditable(false);
        runningRule.setFont(runningRule.getFont().deriveFont(Font.BOLD).deriveFont(13.5f));
        runningRule.setForeground(new Color(primary.brighter().getRed(), primary.brighter().getGreen(), primary.brighter().getBlue()));
        runningRule.setBackground(secondary);
        runningRule.setBorder(null);
        runningRule.setSize(progressBar.getWidth(), frame.getHeight() / 15);
        runningRule.setLocation(progressBar.getX(), (int) (progressBar.getY() - (runningRule.getHeight() * 1.2f)));
        runningRule.setVisible(false);

        inputs.add(searchForText);
        inputs.add(searchFor);
        inputs.add(moveToFolderText);
        inputs.add(moveToFolder);
        inputs.add(searchInFolderText);
        inputs.add(searchInFolder);
        inputs.add(dividerTwo);
        inputs.add(dividerOne);
        inputs.add(quantity);
        inputs.add(textQuantity);
        inputs.add(search);
        inputs.add(searchIn);
        inputs.add(name);
        inputs.add(ruleName);
        
        frame.add(runningRule);
        frame.add(progressBar);
        frame.add(sequencePane);
        frame.add(rulesPane);
        frame.add(setTheme);
        frame.add(GUI.trakt);
        frame.add(save);
        frame.add(delete);
        frame.add(run);
        frame.add(inputs);
        frame.add(cb);
        frame.add(GUI.updateRulesData);
        frame.add(settings);
        frame.add(close);
        frame.add(maximise);
        frame.add(minimise);
        frame.add(anim);
        frame.add(back);
        frame.setVisible(true);

    }

    public static void createRuleButtons(String type)
    {
        File[] rulesList = null;
        if (type.equals("tv show"))
        {
            File folder = new File(halen.FileManager.launchPath() + "/rules/tv show");
            rulesList = folder.listFiles();
        } else if (type.equals("anime"))
        {
            File folder = new File(halen.FileManager.launchPath() + "/rules/anime");
            rulesList = folder.listFiles();
        } else if (type.equals("comics"))
        {
            File folder = new File(halen.FileManager.launchPath() + "/rules/comics");
            rulesList = folder.listFiles();
        }

        for (int i = 0; i < rulesList.length; i++)
        {
            //  if (FileManager.returnTag("type", rules.getItem(i)).equals(type.trim()))
            {
                final JButton button = new JButton();

                button.setText("  RULE   " + (i + 1) + ": " + rulesList[i].getName().replace(".xml", "")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());

                button.setForeground(primary);
                button.setBackground(secondary.darker());

                button.setMaximumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));
                button.setMinimumSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));
                button.setPreferredSize(new Dimension(rulesPane.getWidth() - 10, rulesPane.getHeight() / 4));
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

                        Show.showRule(button.getText().replace(button.getText().subSequence(button.getText().indexOf("R"), button.getText().indexOf(":") + 1), "").trim());
                        loadSequenceContent(button.getText().replace(button.getText().subSequence(button.getText().indexOf("R"), button.getText().indexOf(":") + 1), "").trim(), cb.getSelectedItem().toString().toLowerCase().trim());
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
        }
    }

    public static void loadSequenceContent(final String file, final String type)
    {

        sequenceListPanel.removeAll();
        sequenceListPanel.repaint();
        sequenceScroll.revalidate();
        sequenceScroll.repaint();

        final List data = FileManager.readFile(FileManager.launchPath() + "/rules/" + type + "/" + file + ".xml");
        //    System.out.println(data.getItemCount());
        //  System.out.println(FileManager.launchPath() + "/" + type + "/" + file + ".xml");
        for (int i = 1; i < data.getItemCount(); i++)
        {
            //  if (FileManager.returnTag("type", rules.getItem(i)).equals(type.trim()))
            {
                final JButton button = new JButton();

                if (data.getItem(i).contains("true"))
                {
                    button.setText(data.getItem(i).subSequence(data.getItem(i).indexOf("<") + 1, data.getItem(i).indexOf(">")) + " - DOWNLOADED"); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                } else
                {
                    button.setText(data.getItem(i).subSequence(data.getItem(i).indexOf("<") + 1, data.getItem(i).indexOf(">")) + " - PENDING"); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());

                }
                button.setForeground(primary);
                button.setBackground(secondary.darker());

                button.setMaximumSize(new Dimension(sequencePane.getWidth() - 10, sequencePane.getHeight() / 7));
                button.setMinimumSize(new Dimension(sequencePane.getWidth() - 10, sequencePane.getHeight() / 7));
                button.setPreferredSize(new Dimension(sequencePane.getWidth() - 10, sequencePane.getHeight() / 7));
                button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont((float) button.getMaximumSize().height/6));
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
                            } else
                            {
                                button.setText(button.getText().replace("DOWNLOADED", "PENDING")); //contactList.get(i).getSurname() + ", " + contactList.get(i).getGivenName());
                            }
                            out = new PrintWriter(FileManager.launchPath() + "/rules/" + type + "/" + file + ".xml");
                            for (int i = 0; i < data.getItemCount(); i++)
                            {
                                if (button.getText().contains(data.getItem(i).subSequence(data.getItem(i).indexOf("<") + 1, data.getItem(i).indexOf(">"))))
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
                            Logger.getLogger(MetroUI.class.getName()).log(Level.SEVERE, null, ex);
                        } finally
                        {
                            out.close();
                        }
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

                sequenceListPanel.add(button);

                try
                {
                    sequenceListPanel.revalidate();
                    sequenceListPanel.repaint();
                    sequenceScroll.revalidate();
                    sequenceScroll.repaint();

                } catch (NullPointerException e)
                {

                }

            }
        }

        try
        {
            sequenceListPanel.revalidate();
            sequenceListPanel.repaint();
            sequenceScroll.revalidate();
            sequenceScroll.repaint();

        } catch (NullPointerException e)
        {

        }
    }
}
