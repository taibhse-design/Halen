/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI.NoticePanels;

import static Halen3.GUI.GUIBase.color;
import static Halen3.GUI.GUIBase.secondary;
import static Halen3.GUI.GUIBase.tertiary;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author brenn
 */
public class SavingPanel
{
     public static JPanel savePanel;
    public static void addSavePanel(int width, int height, int x, int y)
    {
        Border border = new LineBorder(secondary, 3);

        savePanel = new JPanel();
        savePanel.setSize(width, height);
        savePanel.setLocation(x, y);
        savePanel.setBackground(secondary);
        savePanel.setLayout(null);
        savePanel.setVisible(false);
        
         ImageIcon a = new ImageIcon(color(tertiary, "Resources/metro/saving.png"));
        ImageIcon as = new ImageIcon(a.getImage().getScaledInstance((int) (savePanel.getHeight() / 3), (int) (savePanel.getHeight() / 3), java.awt.Image.SCALE_DEFAULT));

        JLabel anim = new JLabel();
        anim.setLayout(null);
        anim.setIcon(as);
        anim.setSize(anim.getIcon().getIconWidth(), anim.getIcon().getIconHeight());
        anim.setLocation((savePanel.getWidth() / 2) - (anim.getWidth() / 2), (savePanel.getHeight() / 2) - (anim.getHeight() / 2));
        anim.setVisible(true);
        
        savePanel.add(anim);
        
    }
    
  
}
