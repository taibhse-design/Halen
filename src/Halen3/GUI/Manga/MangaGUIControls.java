/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI.Manga;

import Halen3.GUI.GUIBase;
import static Halen3.GUI.GUIBase.tertiary;
import static Halen3.GUI.Manga.MangaGUI.DownloadToFolderText;
import static Halen3.GUI.Manga.MangaGUI.delete;
import static Halen3.GUI.Manga.MangaGUI.issueListEmptyPanel;
import static Halen3.GUI.Manga.MangaGUI.issueListScroll;
import static Halen3.GUI.Manga.MangaGUI.mangaPanel;
import static Halen3.GUI.Manga.MangaGUI.mangaURLInput;
import static Halen3.GUI.Manga.MangaGUI.ruleNameInput;
import static Halen3.GUI.Manga.MangaGUI.rulesListPanel;
import static Halen3.GUI.Manga.MangaGUI.rulesScroll;
import static Halen3.GUI.Manga.MangaGUI.run;
import static Halen3.GUI.Manga.MangaGUI.save;
import static Halen3.GUI.Manga.MangaGUI.update;
import Halen3.GUI.NoticePanels.LoggingPanel;
import static Halen3.GUI.NoticePanels.SavingPanel.savePanel;
import Halen3.GUI.RuleManager;
import Halen3.IO.FileManager;
import Halen3.Retrievers.Manga.DownloadNewMangaVolumes;
import Halen3.Retrievers.Manga.SearchForUpdates;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author brenn
 */
public class MangaGUIControls
{

    public static void initmangaControls()
    {
        ruleNameInput.addMouseListener(
                new MouseAdapter()
        {

            @Override
            public void mouseClicked(MouseEvent e
            )
            {
                //only blank if the text is the default instructions
                if (ruleNameInput.getText().equals("..."))
                {
                    ruleNameInput.setText("");
                }
            }
        }
        );

        mangaURLInput.addMouseListener(
                new MouseAdapter()
        {

            @Override
            public void mouseClicked(MouseEvent e
            )
            {
                //only blank if the text is the default instructions
                if (mangaURLInput.getText().equals("..."))
                {
                    mangaURLInput.setText("");
                }
            }
        }
        );

        DownloadToFolderText.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseClicked(MouseEvent e
            )
            {
                //only blank if the text is the default instructions
                if (DownloadToFolderText.getText().equals("..."))
                {
                    DownloadToFolderText.setText("");
                }
            }
        }
        );

//        moveToFolderText.addMouseListener(
//                new MouseAdapter()
//        {
//
//            @Override
//            public void mouseClicked(MouseEvent e
//            )
//            {
//                //only blank if the text is the default instructions
//                if (moveToFolderText.getText().equals("..."))
//                {
//                    moveToFolderText.setText("");
//                }
//            }
//        }
//        );
//
//        searchForText.addMouseListener(
//                new MouseAdapter()
//        {
//
//            @Override
//            public void mouseClicked(MouseEvent e
//            )
//            {
//                //only blank if the text is the default instructions
//                if (searchForText.getText().equals("..."))
//                {
//                    searchForText.setText("");
//                }
//            }
//        }
//        );
//
//        replaceThisText.addMouseListener(
//                new MouseAdapter()
//        {
//
//            @Override
//            public void mouseClicked(MouseEvent e
//            )
//            {
//                //only blank if the text is the default instructions
//                if (replaceThisText.getText().equals("..."))
//                {
//                    replaceThisText.setText("");
//                }
//            }
//        }
//        );
//        withThisText.addMouseListener(
//                new MouseAdapter()
//        {
//
//            @Override
//            public void mouseClicked(MouseEvent e
//            )
//            {
//                //only blank if the text is the default instructions
//                if (withThisText.getText().equals("..."))
//                {
//                    withThisText.setText("");
//                }
//            }
//        }
//        );
        save.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

                try
                {
                    GUIBase.hideButtons();
                    mangaPanel.setVisible(false);
                    savePanel.setVisible(true);
                    RuleManager.saveMangaRule(MangaGUI.ruleNameInput.getText().trim());
                } catch (InterruptedException | IOException ex)
                {
                    System.out.println(ex);
                    Logger.getLogger(MangaGUIControls.class.getName()).log(Level.SEVERE, null, ex);
                }

                GUIBase.showButtons();
                mangaPanel.setVisible(true);
                savePanel.setVisible(false);

            }

            @Override
            public void mousePressed(MouseEvent me
            )
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/savePressed.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);
            }

            @Override
            public void mouseReleased(MouseEvent me
            )
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/save.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);
            }

            @Override
            public void mouseEntered(MouseEvent me
            )
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/saveHover.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);
            }

            @Override
            public void mouseExited(MouseEvent me
            )
            {
                ImageIcon s = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/save.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);
            }
        }
        );

        delete.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

                if (!ruleNameInput.getText().trim().equals("") & !ruleNameInput.getText().trim().equals("..."))
                {
                    int option = JOptionPane.showConfirmDialog(null, "Do you wish to delete this rule?", "Delete Rule", JOptionPane.YES_NO_OPTION);

                    if (option == 0)
                    {
                        File im = new File(FileManager.returnTag("imageURL", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/manga/" + ruleNameInput.getText().trim() + ".xml").getItem(0)));
                        File del = new File(Halen3.IO.FileManager.launchPath() + "/rules/manga/" + ruleNameInput.getText().trim() + ".xml");
                       
//                        for (int i = 0; i < 100; i++)
//                        {
                            try
                            {
                                //  del.delete();
                                Files.delete(del.toPath());
                                im.delete();
                            } catch (IOException ex)
                            {
                                JOptionPane.showConfirmDialog(null, "This Rule Will be deleted on exit", "Delete Rule", JOptionPane.OK_OPTION);

                                del.deleteOnExit();
                                im.deleteOnExit();
                                
                                System.out.println(ex);
                                //Logger.getLogger(MangaGUIControls.class.getName()).log(Level.SEVERE, null, ex);
                            }
                         
//                            if (del.exists() == false)
//                            {
//                                break;
//                            }
//                        }
                        //blank rules in ui
                        rulesListPanel.removeAll();
                        rulesListPanel.repaint();
                        rulesScroll.revalidate();
                        rulesScroll.repaint();

                        MangaGUI.refreshMangaPanel();

                        rulesListPanel.revalidate();
                        rulesListPanel.repaint();
                        rulesScroll.revalidate();
                        rulesScroll.repaint();

                        ruleNameInput.setText("...");
                        //  search.setText("...");
                        mangaURLInput.setText("...");
                        DownloadToFolderText.setText("...");
                        // moveToFolderText.setText("...");
                        // searchForText.setText("...");
                        // replaceThisText.setText("...");
                        // withThisText.setText("...");

                        issueListEmptyPanel.removeAll();
                        issueListEmptyPanel.revalidate();
                        issueListEmptyPanel.repaint();
                        issueListScroll.revalidate();
                        issueListScroll.repaint();
                    } else
                    {

                    }
                }
                //  Delete.deleteRule();

            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon d = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/deletePressed.png"));
                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
                delete.setIcon(ds);
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon d = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/delete.png"));
                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
                delete.setIcon(ds);
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon d = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/deleteHover.png"));
                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
                delete.setIcon(ds);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon d = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/delete.png"));
                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
                delete.setIcon(ds);
            }
        });

        run.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

                LoggingPanel.showMangaLogging();
                SwingWorker myWorker = new SwingWorker<String, Void>()
                {
                    @Override
                    protected String doInBackground() throws Exception
                    {
                        DownloadNewMangaVolumes.downloadNewVolumes();

                        LoggingPanel.hideMangaLogging();
                        GUIBase.frame.revalidate();
                        GUIBase.frame.repaint();
                        return null;
                    }
                };
                myWorker.execute();
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon r = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/runPressed.png"));
                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
                run.setIcon(rs);
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon r = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/run.png"));
                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
                run.setIcon(rs);
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon r = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/runHover.png"));
                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
                run.setIcon(rs);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon r = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/run.png"));
                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
                run.setIcon(rs);
            }
        });

        update.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

                LoggingPanel.showMangaLogging();
                SwingWorker myWorker = new SwingWorker<String, Void>()
                {
                    @Override
                    protected String doInBackground() throws Exception
                    {
                        SearchForUpdates.updateAllMangaRules();

                        LoggingPanel.hideMangaLogging();
                        return null;
                    }
                };
                myWorker.execute();

            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/refreshPressed.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(update.getWidth(), update.getHeight(), java.awt.Image.SCALE_DEFAULT));
                update.setIcon(ts);

            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/refresh.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(update.getWidth(), update.getHeight(), java.awt.Image.SCALE_DEFAULT));
                update.setIcon(ts);
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/refreshHover.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(update.getWidth(), update.getHeight(), java.awt.Image.SCALE_DEFAULT));
                update.setIcon(ts);

            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(GUIBase.color(tertiary, "Resources/metro/buttons/refresh.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(update.getWidth(), update.getHeight(), java.awt.Image.SCALE_DEFAULT));
                update.setIcon(ts);

            }
        });
    }
}
