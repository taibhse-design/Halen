package Rules;

import halen.GUI;
import static halen.GUI.cb;
import static halen.GUI.name;
import static halen.GUI.rulesListPanel;
import static halen.GUI.rulesScroll;
import static halen.GUI.search;
import halen.MetroUI;
import static halen.MetroUI.quantity;
import java.io.File;
import static halen.MetroUI.episodeListScroll;
import static halen.MetroUI.episodeListEmptyPanel;

/**
 * @author TAIBHSE
 */
public class Delete
{

    public static void main(String args[])
    {
        System.out.println("HELLO WORLD");
    }

    public static void deleteRule()
    {
        if (!name.getText().equals("...") && !name.getText().trim().equals(""))
        {
            File del;
            if (cb.getSelectedItem().toString().toLowerCase().trim().equals("tv show"))
            {
                del = new File(halen.FileManager.launchPath() + "/rules/tv show/" + name.getText().trim() + ".xml");
                del.delete();
            } else if (cb.getSelectedItem().toString().toLowerCase().trim().equals("anime"))
            {
                del = new File(halen.FileManager.launchPath() + "/rules/anime/" + name.getText().trim() + ".xml");
                del.delete();
            } else if (cb.getSelectedItem().toString().toLowerCase().trim().equals("comics"))
            {
                del = new File(halen.FileManager.launchPath() + "/rules/comics/" + name.getText().trim() + ".xml");
                del.delete();
            }

            //blank rules in ui
            rulesListPanel.removeAll();
            rulesListPanel.repaint();
            rulesScroll.revalidate();
            rulesScroll.repaint();

            MetroUI.createRuleButtons(GUI.cb.getSelectedItem().toString().toLowerCase().trim());

            rulesListPanel.revalidate();
            rulesListPanel.repaint();
            rulesScroll.revalidate();
            rulesScroll.repaint();

            name.setText("...");
            search.setText("...");
            quantity.setText("...");

            episodeListEmptyPanel.removeAll();
            episodeListEmptyPanel.revalidate();
            episodeListEmptyPanel.repaint();
            episodeListScroll.revalidate();
            episodeListScroll.repaint();
        }
    }

}
