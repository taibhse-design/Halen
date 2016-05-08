package halen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static halen.Main.primary;
import static halen.Main.secondary;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author TAIBHSE
 */
public class GUI
{

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  //get system screen res
    public static int screenW = (int) screenSize.getWidth();  // variable holds max screen width
    public static int screenH = (int) screenSize.getHeight();  //variable holds max screen height
    public static JFrame frame;
    public static JTextField runningRule, ruleName, name, searchIn, search, menuTitle;
    public static JLabel anim, settings, updateRulesData, trakt;
    public static JButton save, run, delete, close;
    public static JScrollPane rulesScroll;
    public static JPanel rulesPane, rulesListPanel;
    public static JProgressBar progressBar;
    public static JComboBox<String> cb;

 //   public static Color primary = new Color(1, 1, 1);
  //  public static Color secondary = new Color(51, 51, 51);

       public static BufferedImage color(Color tint, String image) 
    {

        try
        {
            BufferedImage src = ImageIO.read(Main.class.getResource(image));
            
            BufferedImage newImage = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TRANSLUCENT);
            Graphics2D graphics = newImage.createGraphics();
            graphics.drawImage(src, 0, 0, null);
            graphics.dispose();
            
            // Color image
            for (int i = 0; i < newImage.getWidth(); i++) {
                for (int j = 0; j < newImage.getHeight(); j++) {
                    int ax = newImage.getColorModel().getAlpha(newImage.getRaster().getDataElements(i, j, null));
                    int  rx = (int) tint.getRed();
                    int  gx = (int) tint.getGreen();
                    int  bx = (int) tint.getBlue();
                    newImage.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx << 0));
                }
            }
            return newImage;
        } catch (IOException ex)
        {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return null;
}
       
    public static Image createImage(String path)
    {
        URL imageURL = GUI.class.getResource(path);

        if (imageURL == null)
        {
            System.err.println("Resource not found: " + path);
            return null;
        } else
        {
            return (new ImageIcon(imageURL, "")).getImage();
        }
    }

   /** public static void gui()
    {

        //create main frame
        frame = new JFrame("HALEN");

        frame.setSize((int) (screenW / 1.5f), (int) (screenH / 1.7)); //ensure frame fits within screen
        frame.setLocation(screenW / 2 - frame.getWidth() / 2, screenH / 2 - frame.getHeight() / 2); //default frame to be located at centre of screen
        //    frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(null);
        frame.setResizable(false); //prevent frame resizing as this messes up orientation of buttons and other elements
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setIconImage(createImage("Resources/icon.png")); //create icon
        frame.setUndecorated(true);
        frame.setBackground(primary);

        UIManager.put("ComboBox.background", new ColorUIResource(secondary));
        UIManager.put("ComboBox.foreground", new ColorUIResource(new Color(255, 255, 255)));
        // UIManager.put("JTextField.background", new ColorUIResource(Color.yellow));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(secondary));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(new Color(255, 255, 255)));

        UIManager.put("ProgressBar.foreground", secondary);
        UIManager.put("ProgressBar.selectionBackground", secondary);
        UIManager.put("ProgressBar.selectionForeground", Color.white);

        String[] choices =
        {
            " TV SHOW", " COMIC", " ANIME"
        };

        cb = new JComboBox<String>(choices);
        cb.setSize(frame.getWidth() / 6, frame.getHeight() / 15);
        cb.setLocation((int) (cb.getHeight()*0.2), (int) (cb.getHeight()*1.5));
        cb.setFont(cb.getFont().deriveFont(Font.BOLD).deriveFont(32.5f));

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

        ImageIcon f = new ImageIcon(createImage("Resources/metro/back/back.png"));
        ImageIcon fs = new ImageIcon(f.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), java.awt.Image.SCALE_DEFAULT));

        JLabel back = new JLabel();
        back.setSize(frame.getWidth(), frame.getHeight());
        back.setIcon(fs);
        back.setVisible(true);

        ImageIcon a = new ImageIcon(createImage("Resources/anim.gif"));
        ImageIcon as = new ImageIcon(a.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), java.awt.Image.SCALE_DEFAULT));

        anim = new JLabel();
        anim.setSize(frame.getWidth(), frame.getHeight());
        anim.setIcon(as);
        anim.setVisible(false);

       close = new JButton();
        close.setContentAreaFilled(false);
        close.setOpaque(false);
        close.setFocusPainted(false);
        ImageIcon closeFullScale = new ImageIcon(color(primary,"Resources/metro/frameControls/close.png"));
        ImageIcon closeScaled = new ImageIcon(closeFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
        close.setIcon(closeScaled);
        close.setSize(close.getIcon().getIconWidth(), close.getIcon().getIconHeight());
        close.setLocation((int) (frame.getWidth() - close.getWidth() * 1.5f), (int) (close.getHeight() / 2));
        close.setVisible(true);

        settings = new JLabel();
        ImageIcon settingsFullScale = new ImageIcon(createImage("Resources/settings.png"));
        int settingsW = close.getHeight();
        int settingsH = close.getHeight();
        ImageIcon settingsScaled = new ImageIcon(settingsFullScale.getImage().getScaledInstance(settingsW, settingsH, java.awt.Image.SCALE_DEFAULT));
        settings.setIcon(settingsScaled);
        settings.setSize(settings.getIcon().getIconWidth(), settings.getIcon().getIconHeight());
        settings.setLocation((int) (close.getX() - settings.getWidth() * 1.5), close.getY());
        settings.setVisible(true);

        ruleName = new JTextField(20);
        ruleName.setText("             RULE NAME  ");
        ruleName.setEditable(false);
        ruleName.setFont(ruleName.getFont().deriveFont(Font.BOLD));
        ruleName.setForeground(Color.white);
        ruleName.setBackground(secondary);
        ruleName.setBorder(null);
        ruleName.setSize(frame.getWidth() / 8, frame.getHeight() / 15);
        ruleName.setLocation(cb.getX(), (int) (frame.getHeight() / 3.5) + ruleName.getHeight() / 2);
        ruleName.setVisible(true);

        name = new JTextField(20);
        name.setText("...");
        name.setEditable(true);
        name.setFont(name.getFont().deriveFont(Font.BOLD));
        name.setForeground(Color.black);
        name.setBackground(Color.WHITE);
        name.setCaretColor(Color.black);
        name.setSize((int) (frame.getWidth() / 3.5), frame.getHeight() / 15);
        name.setLocation(ruleName.getLocation().x + ruleName.getWidth(), ruleName.getLocation().y);
        name.setVisible(true);

        searchIn = new JTextField(20);
        searchIn.setText("             SEARCH  ");
        searchIn.setEditable(false);
        searchIn.setFont(searchIn.getFont().deriveFont(Font.BOLD));
        searchIn.setForeground(Color.white);
        searchIn.setBackground(secondary);
        searchIn.setBorder(null);
        searchIn.setSize(frame.getWidth() / 8, frame.getHeight() / 15);
        searchIn.setLocation(ruleName.getLocation().x, ruleName.getLocation().y + ruleName.getHeight() + ruleName.getHeight() / 2);
        searchIn.setVisible(true);

        search = new JTextField(20);
        search.setText("...");
        search.setEditable(true);
        search.setFont(search.getFont().deriveFont(Font.BOLD));
        search.setForeground(Color.black);
        search.setBackground(Color.WHITE);
        search.setCaretColor(Color.black);
        search.setSize(frame.getWidth() / 3, frame.getHeight() / 15);
        search.setLocation(searchIn.getLocation().x + searchIn.getWidth(), searchIn.getLocation().y);
        search.setVisible(true);

        startPoint = new JTextField(20);
        startPoint.setText("              START  ");
        startPoint.setEditable(false);
        startPoint.setFont(startPoint.getFont().deriveFont(Font.BOLD));
        startPoint.setForeground(Color.white);
        startPoint.setBackground(secondary);
        startPoint.setBorder(null);
        startPoint.setSize(frame.getWidth() / 8, frame.getHeight() / 15);
        startPoint.setLocation(searchIn.getLocation().x, searchIn.getLocation().y + searchIn.getHeight() + searchIn.getHeight() / 2);
        startPoint.setVisible(true);

        start = new JTextField(20);
        start.setText("...");
        start.setEditable(true);
        start.setFont(start.getFont().deriveFont(Font.BOLD));
        start.setForeground(Color.black);
        start.setBackground(Color.WHITE);
        start.setCaretColor(Color.black);
        start.setSize(frame.getWidth() / 3 / 3, frame.getHeight() / 15);
        start.setLocation(startPoint.getLocation().x + startPoint.getWidth(), startPoint.getLocation().y);
        start.setVisible(true);

        endPoint = new JTextField(20);
        endPoint.setText("                    END ");
        endPoint.setEditable(false);
        endPoint.setFont(endPoint.getFont().deriveFont(Font.BOLD));
        endPoint.setForeground(Color.white);
        endPoint.setBackground(secondary);
        endPoint.setBorder(null);
        endPoint.setSize(frame.getWidth() / 3 / 3, frame.getHeight() / 15);
        endPoint.setLocation(start.getLocation().x + start.getWidth(), start.getLocation().y);
        endPoint.setVisible(true);

        end = new JTextField(20);
        end.setText("...");
        end.setEditable(true);
        end.setFont(end.getFont().deriveFont(Font.BOLD));
        end.setForeground(Color.black);
        end.setBackground(Color.WHITE);
        end.setCaretColor(Color.black);
        end.setSize(frame.getWidth() / 3 / 3, frame.getHeight() / 15);
        end.setLocation(endPoint.getLocation().x + endPoint.getWidth(), endPoint.getLocation().y);
        end.setVisible(true);

        save = new JButton();
        save.setFont(save.getFont().deriveFont(Font.BOLD));
        save.setForeground(Color.black);
        save.setBackground(Color.green.darker());
        save.setContentAreaFilled(false);
        save.setOpaque(false);
        save.setFocusPainted(false);
        save.setSize(frame.getWidth() / 21, frame.getWidth() / 21);
        ImageIcon s = new ImageIcon(createImage("Resources/save.png"));
        ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
        save.setIcon(ss);
        save.setLocation(frame.getWidth() / 90, (int) (frame.getHeight() / 1.295));
        save.setVisible(true);

        delete = new JButton();
        delete.setFont(delete.getFont().deriveFont(Font.BOLD));
        delete.setForeground(Color.black);
        delete.setBackground(Color.red.darker());
        delete.setContentAreaFilled(false);
        delete.setOpaque(false);
        delete.setFocusPainted(false);
        delete.setSize(frame.getWidth() / 30, frame.getWidth() / 30);
        ImageIcon d = new ImageIcon(createImage("Resources/delete.png"));
        ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
        delete.setIcon(ds);
        delete.setLocation(frame.getWidth() / 16, (int) (frame.getHeight() / 1.429));
        delete.setVisible(true);

        run = new JButton();
        run.setFont(run.getFont().deriveFont(Font.BOLD));
        run.setForeground(Color.black);
        run.setBackground(Color.BLUE.darker());
        run.setContentAreaFilled(false);
        run.setOpaque(false);
        run.setFocusPainted(false);
        run.setSize(frame.getWidth() / 13, frame.getWidth() / 13);
        ImageIcon r = new ImageIcon(createImage("Resources/run.png"));
        ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
        run.setIcon(rs);
        run.setLocation(frame.getWidth() / 12, (int) (frame.getHeight() / 1.25));
        run.setVisible(true);

        rulesPane = new JPanel();
        rulesPane.setLayout(new BorderLayout());
        rulesPane.setSize((int) (frame.getWidth() / 2.4), (int) (frame.getHeight() / 1.78));
        rulesPane.setLocation((int) (frame.getWidth() - (rulesPane.getWidth() * 1.05f)), ruleName.getY() - ruleName.getHeight());

        // Set layout for contactListPane
        rulesListPanel = new JPanel();
        BoxLayout layout = new BoxLayout(rulesListPanel, BoxLayout.Y_AXIS);
        rulesListPanel.setLayout(layout);
        rulesListPanel.setBackground(secondary);
        //   contactListPanel.setSize(rulesPane.getWidth(), rulesPane.getHeight()/8);

      //  createRuleButtons(GUI.cb.getSelectedItem().toString().toLowerCase());

        rulesScroll = new JScrollPane(rulesListPanel);
        rulesScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rulesScroll.getVerticalScrollBar().setUnitIncrement(16);
        Border border = new LineBorder(secondary, 2);
        rulesScroll.setBorder(border);
        rulesScroll.setBackground(secondary);

        rulesPane.add(rulesScroll, null);

        rulesPane.setVisible(true);

        progressBar = new JProgressBar(0, 100);
        progressBar.setSize(screenW / 4, screenH / 17);
        progressBar.setFont(progressBar.getFont().deriveFont(Font.BOLD).deriveFont(18.5f));
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        Border borderTwo = new LineBorder(new Color(255, 255, 255), 4);
        progressBar.setBorder(borderTwo);
        progressBar.setLocation((int) (frame.getWidth() / 2 - progressBar.getWidth() / 1.3), (int) (rulesPane.getLocation().y + rulesPane.getHeight() / 1.2));
        progressBar.setVisible(false);

        runningRule = new JTextField(20);
        runningRule.setText("NO RULE RUNNING.....");
        runningRule.setEditable(false);
        runningRule.setFont(runningRule.getFont().deriveFont(Font.BOLD).deriveFont(22.5f));
        runningRule.setForeground(Color.white);
        runningRule.setBackground(secondary);
        runningRule.setBorder(null);
        runningRule.setSize(progressBar.getWidth(), frame.getHeight() / 15);
        runningRule.setLocation(progressBar.getX(), (int) (progressBar.getY() - (runningRule.getHeight() * 1.2f)));
        runningRule.setVisible(false);

        frame.add(run);
        frame.add(runningRule);
        frame.add(progressBar);
        frame.add(rulesPane);
        frame.add(delete);
        frame.add(save);
        frame.add(endPoint);
        frame.add(end);
        frame.add(start);
        frame.add(startPoint);
        frame.add(search);
        frame.add(searchIn);
        frame.add(name);
        frame.add(ruleName);
        frame.add(cb);
        frame.add(settings);
        frame.add(close);
        frame.add(anim);
        frame.add(back);
        frame.setVisible(true);
    }  **/

   

}
