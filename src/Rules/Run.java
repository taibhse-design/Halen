package Rules;

import Downloader.ThreadedDownload;
import halen.GUI;
import static halen.GUI.anim;
import static halen.GUI.cb;
import static halen.GUI.delete;
import static halen.GUI.name;
import static halen.GUI.progressBar;
import static halen.GUI.ruleName;
import static halen.GUI.rulesPane;
import static halen.GUI.runningRule;
import static halen.GUI.save;
import static halen.GUI.search;
import static halen.GUI.searchIn;
import static halen.GUI.settings;
import static halen.MetroUI.inputs;
import static halen.MetroUI.quantity;
import static halen.MetroUI.sequenceListPanel;
import static halen.MetroUI.sequencePane;
import static halen.MetroUI.sequenceScroll;
import static halen.MetroUI.setTheme;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * @author TAIBHSE
 */
public class Run
{

    public static void main(String args[])
    {
        System.out.println("HELLO WORLD");
    }

    public static void runRules() throws IOException, UnsupportedEncodingException, URISyntaxException
    {

        Thread thread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {

                try
                {

                    ThreadedDownload.getMagnets();

                    //notify finished and reset progressbar
                    //     JOptionPane.showMessageDialog(null, "Finished running through rules", "FINISHED", JOptionPane.INFORMATION_MESSAGE);
                    progressBar.setValue(0);

                    runningRule.setVisible(false);
                    progressBar.setVisible(false);
                    anim.setVisible(false);
                    rulesPane.setVisible(true);
                    save.setVisible(true);
                    GUI.run.setVisible(true);
                    ruleName.setVisible(true);
                    name.setVisible(true);
                    name.setText("...");
                    search.setText("...");
                    quantity.setText("...");
                    sequencePane.setVisible(true);
                    delete.setVisible(true);
                    search.setVisible(true);
                    searchIn.setVisible(true);
                    settings.setVisible(true);
                    setTheme.setVisible(true);
                     GUI.trakt.setVisible(true);
                    GUI.updateRulesData.setVisible(true);
                    cb.setVisible(true);
                    inputs.setVisible(true);
                    sequenceListPanel.removeAll();
                    sequenceListPanel.revalidate();
                    sequenceListPanel.repaint();
                    sequenceScroll.revalidate();
                    sequenceScroll.repaint();

                } catch (Exception ex)
                {
                    System.out.println(ex);
                }

            }
        });

        thread.start();

    }
}
