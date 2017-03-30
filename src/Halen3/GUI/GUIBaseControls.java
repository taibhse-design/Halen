/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.GUI;

import static Halen3.GUI.Anime.AnimeGUI.animePanel;
import Halen3.GUI.Comics.ComicsGUI;
import static Halen3.GUI.Comics.ComicsGUI.comicPanel;
import static Halen3.GUI.GUIBase.anime;
import static Halen3.GUI.GUIBase.theme;
import static Halen3.GUI.GUIBase.color;
import static Halen3.GUI.GUIBase.comics;
import static Halen3.GUI.GUIBase.film;
import static Halen3.GUI.GUIBase.primary;
import static Halen3.GUI.GUIBase.secondary;
import static Halen3.GUI.GUIBase.settings;
import static Halen3.GUI.GUIBase.tertiary;
import static Halen3.GUI.GUIBase.themePanel;
import static Halen3.GUI.GUIBase.tv;
import static Halen3.GUI.Settings.SettingsGUI.settingsPanel;
import Halen3.GUI.Film.FilmGUI;
import static Halen3.GUI.Film.FilmGUI.filmPanel;
import Halen3.GUI.TV.TvGUI;
import static Halen3.GUI.TV.TvGUI.tvPanel;
import Halen3.IO.FileManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.ImageIcon;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author brenn
 */
public class GUIBaseControls
{

    static long tvFolderSize = FileUtils.sizeOfDirectory(new File(FileManager.launchPath() + "/rules/tv show/"));
    static long filmFolderSize = FileUtils.sizeOfDirectory(new File(FileManager.launchPath() + "/rules/films/"));
    static long comicFolderSize = FileUtils.sizeOfDirectory(new File(FileManager.launchPath() + "/rules/comics/"));

    public static void initBaseControls()
    {
        theme.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

                themePanel.setVisible(true);
                theme.setOpaque(true);
                theme.setBackground(secondary);
                tvPanel.setVisible(false);
                filmPanel.setVisible(false);
                comicPanel.setVisible(false);
                animePanel.setVisible(false);
                settingsPanel.setVisible(false);
                GUIBase.tv.setVisible(false);
                GUIBase.film.setVisible(false);
                GUIBase.comics.setVisible(false);
                GUIBase.anime.setVisible(false);
                GUIBase.settings.setVisible(false);
                // frame.setVisible(false);
                //  ThemeEditor.openThemeEditor(false);

            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/themePressed.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(theme.getWidth(), theme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                theme.setIcon(ts);

            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/theme.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(theme.getWidth(), theme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                theme.setIcon(ts);

            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/themeHover.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(theme.getWidth(), theme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                theme.setIcon(ts);

            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/theme.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(theme.getWidth(), theme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                theme.setIcon(ts);

            }
        });

        tv.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                tv.setOpaque(true);
                film.setOpaque(false);
                comics.setOpaque(false);
                anime.setOpaque(false);
                settings.setOpaque(false);
                tv.setBackground(secondary);
                comics.setBackground(primary);
                anime.setBackground(primary);
                settings.setBackground(primary);

                //if folder size changes then new rule added/removed, refresh list
                long size = FileUtils.sizeOfDirectory(new File(FileManager.launchPath() + "/rules/tv show/"));
                if (tvFolderSize != size)
                {
                    tvFolderSize = size;
                    TvGUI.refreshTvPanel();
                }
                tvPanel.setVisible(true);
                filmPanel.setVisible(false);
                comicPanel.setVisible(false);
                animePanel.setVisible(false);
                settingsPanel.setVisible(false);

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/tv-clicked.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(tv.getWidth(), tv.getHeight(), java.awt.Image.SCALE_DEFAULT));
                tv.setIcon(ts);
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/tv-normal.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(tv.getWidth(), tv.getHeight(), java.awt.Image.SCALE_DEFAULT));
                tv.setIcon(ts);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/tv-hover.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(tv.getWidth(), tv.getHeight(), java.awt.Image.SCALE_DEFAULT));
                tv.setIcon(ts);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/tv-normal.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(tv.getWidth(), tv.getHeight(), java.awt.Image.SCALE_DEFAULT));
                tv.setIcon(ts);
            }
        });

        comics.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                tv.setOpaque(false);
                film.setOpaque(false);
                comics.setOpaque(true);
                anime.setOpaque(false);
                settings.setOpaque(false);
                tv.setBackground(primary);
                comics.setBackground(secondary);
                anime.setBackground(primary);
                settings.setBackground(primary);

                //if folder size changes then new rule added/removed, refresh list
                long size = FileUtils.sizeOfDirectory(new File(FileManager.launchPath() + "/rules/comics/"));
                if (comicFolderSize != size)
                {
                    comicFolderSize = size;
                    ComicsGUI.refreshComicsPanel();
                }

                tvPanel.setVisible(false);
                filmPanel.setVisible(false);
                comicPanel.setVisible(true);
                animePanel.setVisible(false);
                settingsPanel.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/comics-clicked.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(comics.getWidth(), comics.getHeight(), java.awt.Image.SCALE_DEFAULT));
                comics.setIcon(ts);
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/comics-normal.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(comics.getWidth(), comics.getHeight(), java.awt.Image.SCALE_DEFAULT));
                comics.setIcon(ts);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/comics-hover.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(comics.getWidth(), comics.getHeight(), java.awt.Image.SCALE_DEFAULT));
                comics.setIcon(ts);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/comics-normal.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(comics.getWidth(), comics.getHeight(), java.awt.Image.SCALE_DEFAULT));
                comics.setIcon(ts);
            }
        });

        film.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                film.setOpaque(true);
                tv.setOpaque(false);
                comics.setOpaque(false);
                anime.setOpaque(false);
                settings.setOpaque(false);
                film.setBackground(secondary);
                tv.setBackground(primary);
                comics.setBackground(primary);
                anime.setBackground(primary);
                settings.setBackground(primary);

                //if folder size changes then new rule added/removed, refresh list
                long size = FileUtils.sizeOfDirectory(new File(FileManager.launchPath() + "/rules/films/"));
                if (filmFolderSize != size)
                {
                    filmFolderSize = size;
                       FilmGUI.refreshFilmPanel();
                }
                filmPanel.setVisible(true);
                tvPanel.setVisible(false);
                comicPanel.setVisible(false);
                animePanel.setVisible(false);
                settingsPanel.setVisible(false);

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/film-clicked.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(film.getWidth(), film.getHeight(), java.awt.Image.SCALE_DEFAULT));
                film.setIcon(ts);
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/film-normal.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(film.getWidth(), film.getHeight(), java.awt.Image.SCALE_DEFAULT));
                film.setIcon(ts);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/film-hover.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(film.getWidth(), film.getHeight(), java.awt.Image.SCALE_DEFAULT));
                film.setIcon(ts);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/film-normal.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(film.getWidth(), film.getHeight(), java.awt.Image.SCALE_DEFAULT));
                film.setIcon(ts);
            }
        });

        anime.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                tv.setOpaque(false);
                film.setOpaque(false);
                comics.setOpaque(false);
                anime.setOpaque(true);
                settings.setOpaque(false);

                tv.setBackground(primary);
                comics.setBackground(primary);
                anime.setBackground(secondary);
                settings.setBackground(primary);

                tvPanel.setVisible(false);
                filmPanel.setVisible(false);
                comicPanel.setVisible(false);
                animePanel.setVisible(true);
                settingsPanel.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/anime-clicked.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(anime.getWidth(), anime.getHeight(), java.awt.Image.SCALE_DEFAULT));
                anime.setIcon(ts);
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/anime-normal.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(anime.getWidth(), anime.getHeight(), java.awt.Image.SCALE_DEFAULT));
                anime.setIcon(ts);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/anime-hover.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(anime.getWidth(), anime.getHeight(), java.awt.Image.SCALE_DEFAULT));
                anime.setIcon(ts);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/anime-normal.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(anime.getWidth(), anime.getHeight(), java.awt.Image.SCALE_DEFAULT));
                anime.setIcon(ts);
            }
        });

        settings.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                film.setOpaque(false);
                tv.setOpaque(false);
                comics.setOpaque(false);
                anime.setOpaque(false);
                settings.setOpaque(true);

                tv.setBackground(primary);
                comics.setBackground(primary);
                anime.setBackground(primary);
                settings.setBackground(secondary);

                tvPanel.setVisible(false);
                filmPanel.setVisible(false);
                comicPanel.setVisible(false);
                animePanel.setVisible(false);
                settingsPanel.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/settings-clicked.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(settings.getWidth(), settings.getHeight(), java.awt.Image.SCALE_DEFAULT));
                settings.setIcon(ts);
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/settings-normal.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(settings.getWidth(), settings.getHeight(), java.awt.Image.SCALE_DEFAULT));
                settings.setIcon(ts);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/settings-hover.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(settings.getWidth(), settings.getHeight(), java.awt.Image.SCALE_DEFAULT));
                settings.setIcon(ts);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/MenuSelectionButtons/settings-normal.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(settings.getWidth(), settings.getHeight(), java.awt.Image.SCALE_DEFAULT));
                settings.setIcon(ts);
            }
        });

//            save.addMouseListener(new MouseListener()
//        {
//
//            @Override
//            public void mouseClicked(MouseEvent me)
//            {
//
//               // anim.setVisible(true);
//                //rulesPane.setVisible(false);
//              //  save.setVisible(false);
//               // GUI.trakt.setVisible(false);
//                //cb.setVisible(false);
//                //GUI.run.setVisible(false);
//                //ruleName.setVisible(false);
//                //name.setVisible(false);
//                //search.setVisible(false);
//                //searchIn.setVisible(false);
//                //episodeListPane.setVisible(false);
//                //delete.setVisible(false);
//                //inputsPane.setVisible(false);
//                //                      start.setVisible(false);
//                //                    endPoint.setVisible(false);
//                //settings.setVisible(false);
//                //setTheme.setVisible(false);
//                //GUI.updateRulesData.setVisible(false);
//
////                ImageIcon a;
////                ImageIcon as;
////                try
////                {
////                    a = new ImageIcon(color(secondary, "Resources/metro/types/addNewRule.png"));
////                    as = new ImageIcon(a.getImage().getScaledInstance((int) (frame.getHeight() / 1.2), (int) (frame.getHeight() / 1.2), java.awt.Image.SCALE_DEFAULT));
////                    anim.setIcon(as);
////                } catch (NullPointerException e)
////                {
////
////                }
////
////                Thread thread = new Thread(new Runnable()
////                {
////
////                    @Override
////                    public void run()
////                    {
////
////                        try
////                        {
////                            //  System.out.println(cb.getSelectedItem().toString().toLowerCase().trim());
////                            Save.save(cb.getSelectedItem().toString().toLowerCase().trim(), name.getText(), search.getText(), quantity.getText(), moveToFolderText.getText(), moveToFolderText.getText(), searchForText.getText());
////                            //reload 
////                            loadSequenceContent(name.getText(), cb.getSelectedItem().toString().toLowerCase().trim());
////
////                            //restore gui
////                            anim.setVisible(false);
////                            rulesPane.setVisible(true);
////                            save.setVisible(true);
////                            cb.setVisible(true);
////                            GUI.run.setVisible(true);
////                            ruleName.setVisible(true);
////                            name.setVisible(true);
////                            search.setVisible(true);
////                            searchIn.setVisible(true);
////                            RetrievedListPane.setVisible(true);
////                            delete.setVisible(true);
////                            inputsPane.setVisible(true);
////                            settings.setVisible(true);
////                            setTheme.setVisible(true);
////                            GUI.trakt.setVisible(true);
////                            GUI.updateRulesData.setVisible(true);
////
////                        } catch (Exception ex)
////                        {
////                            System.out.println(ex);
////                        }
////
////                    }
////                });
////
////                thread.start();
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent me
//            )
//            {
//                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/savePressed.png"));
//                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                save.setIcon(ss);
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent me
//            )
//            {
//                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/save.png"));
//                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                save.setIcon(ss);
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent me
//            )
//            {
//                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/saveHover.png"));
//                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                save.setIcon(ss);
//            }
//
//            @Override
//            public void mouseExited(MouseEvent me
//            )
//            {
//                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/save.png"));
//                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                save.setIcon(ss);
//            }
//        }
//        );
//            
//                  delete.addMouseListener(new MouseListener()
//        {
//
//            @Override
//            public void mouseClicked(MouseEvent me)
//            {
//
////                int opcion = JOptionPane.showConfirmDialog(null, "Do you wish to delete this rule?", "Delete Rule", JOptionPane.YES_NO_OPTION);
////
////                if (opcion == 0)
////                { //The ISSUE is here
////                    Delete.deleteRule();
////                } else
////                {
////                    
////                }
//              //  Delete.deleteRule();
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent me)
//            {
//                ImageIcon d = new ImageIcon(color(tertiary, "Resources/metro/buttons/deletePressed.png"));
//                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                delete.setIcon(ds);
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent me)
//            {
//                ImageIcon d = new ImageIcon(color(tertiary, "Resources/metro/buttons/delete.png"));
//                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                delete.setIcon(ds);
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent me)
//            {
//                ImageIcon d = new ImageIcon(color(tertiary, "Resources/metro/buttons/deleteHover.png"));
//                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                delete.setIcon(ds);
//            }
//
//            @Override
//            public void mouseExited(MouseEvent me)
//            {
//                ImageIcon d = new ImageIcon(color(tertiary, "Resources/metro/buttons/delete.png"));
//                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                delete.setIcon(ds);
//            }
//        });
//                  
//      GUIBase.run.addMouseListener(new MouseListener()
//        {
//
//            @Override
//            public void mouseClicked(MouseEvent me)
//            {
////                System.out.println("RUNNING RULES");
////
//////                Thread run = new Thread(new Runnable()
//////                {
//////                    @Override
//////                    public void run()
//////                    {
////                try
////                {
////                    runningRule.setVisible(true);
////                    progressBar.setVisible(true);
////                    anim.setVisible(true);
////                    rulesPane.setVisible(false);
////                    save.setVisible(false);
////                    GUI.trakt.setVisible(false);
////                    cb.setVisible(false);
////                    GUI.run.setVisible(false);
////                    ruleName.setVisible(false);
////                    name.setVisible(false);
////                    search.setVisible(false);
////                    searchIn.setVisible(false);
////                  RetrievedListPanene.setVisible(false);
////                    delete.setVisible(false);
////                    inputsPane.setVisible(false);
////                    //                      start.setVisible(false);
////                    //                    endPoint.setVisible(false);
////                    settings.setVisible(false);
////                    setTheme.setVisible(false);
////                    GUI.updateRulesData.setVisible(false);
////                    Run.runRules();
////
////                } catch (IOException | URISyntaxException ex)
////                {
////                    JOptionPane.showMessageDialog(null, "Rules failed to run, unknown cause", "ERROR", JOptionPane.WARNING_MESSAGE);
////                }
//
////                    }
////                });
////                run.start();
//            }
//
//            @Override
//            public void mousePressed(MouseEvent me)
//            {
//                ImageIcon r = new ImageIcon(color(tertiary, "Resources/metro/buttons/runPressed.png"));
//                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(GUIBase.run.getWidth(), GUIBase.run.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                GUIBase.run.setIcon(rs);
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent me)
//            {
//                ImageIcon r = new ImageIcon(color(tertiary, "Resources/metro/buttons/run.png"));
//                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(GUIBase.run.getWidth(), GUIBase.run.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                GUIBase.run.setIcon(rs);
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent me)
//            {
//                ImageIcon r = new ImageIcon(color(tertiary, "Resources/metro/buttons/runHover.png"));
//                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(GUIBase.run.getWidth(), GUIBase.run.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                GUIBase.run.setIcon(rs);
//            }
//
//            @Override
//            public void mouseExited(MouseEvent me)
//            {
//                ImageIcon r = new ImageIcon(color(tertiary, "Resources/metro/buttons/run.png"));
//                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(GUIBase.run.getWidth(), GUIBase.run.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                GUIBase.run.setIcon(rs);
//            }
//        });
//      
//        GUIBase.update.addMouseListener(new MouseListener()
//        {
//
//            @Override
//            public void mouseClicked(MouseEvent me)
//            {
////                try
////                {
////
////                    rulesPane.setVisible(false);
////                    save.setVisible(false);
////                    GUI.trakt.setVisible(false);
////                    cb.setVisible(false);
////                    GUI.run.setVisible(false);
////                    ruleName.setVisible(false);
////                    name.setVisible(false);
////                    search.setVisible(false);
////                    searchIn.setVisible(false);
////                RetrievedListPanePane.setVisible(false);
////                    delete.setVisible(false);
////                    inputsPane.setVisible(false);
////                    settings.setVisible(false);
////                    setTheme.setVisible(false);
////                    GUI.updateRulesData.setVisible(false);
////
////                    anim.setVisible(true);
////                    ImageIcon a;
////                    ImageIcon as;
////                    try
////                    {
////                        a = new ImageIcon(color(secondary, "Resources/metro/types/updatingRules.png"));
////                        as = new ImageIcon(a.getImage().getScaledInstance((int) (frame.getHeight() / 1.2), (int) (frame.getHeight() / 1.2), java.awt.Image.SCALE_DEFAULT));
////                        anim.setIcon(as);
////                    } catch (NullPointerException e)
////                    {
////
////                    }
////
////                    Trakt.UpdateTraktData.updateTvRules();
////
////                } catch (IOException ex)
////                {
////                    Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
////                }
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent me)
//            {
//                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/refreshPressed.png"));
//                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(GUIBase.update.getWidth(), GUIBase.update.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                GUIBase.update.setIcon(ts);
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent me)
//            {
//                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/refresh.png"));
//                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(GUIBase.update.getWidth(), GUIBase.update.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                GUIBase.update.setIcon(ts);
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent me)
//            {
//                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/refreshHover.png"));
//                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(GUIBase.update.getWidth(), GUIBase.update.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                GUIBase.update.setIcon(ts);
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent me)
//            {
//                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/refresh.png"));
//                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(GUIBase.update.getWidth(), GUIBase.update.getHeight(), java.awt.Image.SCALE_DEFAULT));
//                GUIBase.update.setIcon(ts);
//
//            }
//        });
    }
}
