package halen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Rules.Delete;
import Rules.Run;
import Rules.Save;
import static halen.FileManager.executeCommand;
import static halen.GUI.anim;
import static halen.GUI.cb;
import static halen.GUI.close;
import static halen.GUI.color;
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
import static halen.GUI.search;
import static halen.GUI.searchIn;
import static halen.GUI.settings;
import static halen.Main.secondary;
import static halen.Main.tertiary;
import static halen.MetroUI.inputs;
import static halen.MetroUI.loadSequenceContent;
import static halen.MetroUI.maximise;
import static halen.MetroUI.minimise;
import static halen.MetroUI.moveToFolder;
import static halen.MetroUI.moveToFolderText;
import static halen.MetroUI.quantity;
import static halen.MetroUI.searchFor;
import static halen.MetroUI.searchForText;
import static halen.MetroUI.searchInFolder;
import static halen.MetroUI.searchInFolderText;
import static halen.MetroUI.sequenceListPanel;
import static halen.MetroUI.sequencePane;
import static halen.MetroUI.sequenceScroll;
import static halen.MetroUI.setTheme;
import static halen.MetroUI.textQuantity;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author TAIBHSE
 */
public class Controls
{

    static int posX = 0, posY = 0;

    public static void controls()
    {
        close.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent me)
            {

            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon closeFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/closeHover.png"));
                ImageIcon closeScaled = new ImageIcon(closeFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                close.setIcon(closeScaled);

            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon closeFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/close.png"));
                ImageIcon closeScaled = new ImageIcon(closeFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                close.setIcon(closeScaled);

            }
        });

        minimise.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                frame.setState(JFrame.ICONIFIED);
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon minFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/minimiseHover.png"));
                ImageIcon minScaled = new ImageIcon(minFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                minimise.setIcon(minScaled);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon minFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/minimise.png"));
                ImageIcon minScaled = new ImageIcon(minFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                minimise.setIcon(minScaled);
            }
        });

        maximise.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon maxFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/maximise.png"));
                ImageIcon maxScaled = new ImageIcon(maxFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                maximise.setIcon(maxScaled);
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon maxFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/maximiseHover.png"));
                ImageIcon maxScaled = new ImageIcon(maxFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                maximise.setIcon(maxScaled);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon maxFullScale = new ImageIcon(color(tertiary, "Resources/metro/frameControls/maximise.png"));
                ImageIcon maxScaled = new ImageIcon(maxFullScale.getImage().getScaledInstance(frame.getHeight() / 24, frame.getHeight() / 24, java.awt.Image.SCALE_DEFAULT));
                maximise.setIcon(maxScaled);
            }
        });

        settings.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                try
                {
                    Main.setSettings();

                } catch (FileNotFoundException ex)
                {
                    Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon settingsFullScale = new ImageIcon(color(tertiary, "Resources/metro/buttons/settingsPressed.png"));
                int settingsW = setTheme.getHeight();
                int settingsH = setTheme.getHeight();
                ImageIcon settingsScaled = new ImageIcon(settingsFullScale.getImage().getScaledInstance(settingsW, settingsH, java.awt.Image.SCALE_DEFAULT));
                settings.setIcon(settingsScaled);
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon settingsFullScale = new ImageIcon(color(tertiary, "Resources/metro/buttons/settings.png"));
                int settingsW = setTheme.getHeight();
                int settingsH = setTheme.getHeight();
                ImageIcon settingsScaled = new ImageIcon(settingsFullScale.getImage().getScaledInstance(settingsW, settingsH, java.awt.Image.SCALE_DEFAULT));
                settings.setIcon(settingsScaled);
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon settingsFullScale = new ImageIcon(color(tertiary, "Resources/metro/buttons/settingsHover.png"));
                int settingsW = setTheme.getHeight();
                int settingsH = setTheme.getHeight();
                ImageIcon settingsScaled = new ImageIcon(settingsFullScale.getImage().getScaledInstance(settingsW, settingsH, java.awt.Image.SCALE_DEFAULT));
                settings.setIcon(settingsScaled);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon settingsFullScale = new ImageIcon(color(tertiary, "Resources/metro/buttons/settings.png"));
                int settingsW = setTheme.getHeight();
                int settingsH = setTheme.getHeight();
                ImageIcon settingsScaled = new ImageIcon(settingsFullScale.getImage().getScaledInstance(settingsW, settingsH, java.awt.Image.SCALE_DEFAULT));
                settings.setIcon(settingsScaled);
            }
        });

        run.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                System.out.println("RUNNING RULES");

//                Thread run = new Thread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
                try
                {
                    runningRule.setVisible(true);
                    progressBar.setVisible(true);
                    anim.setVisible(true);
                    rulesPane.setVisible(false);
                    save.setVisible(false);
                    GUI.trakt.setVisible(false);
                    cb.setVisible(false);
                    GUI.run.setVisible(false);
                    ruleName.setVisible(false);
                    name.setVisible(false);
                    search.setVisible(false);
                    searchIn.setVisible(false);
                    sequencePane.setVisible(false);
                    delete.setVisible(false);
                    inputs.setVisible(false);
                    //                      start.setVisible(false);
                    //                    endPoint.setVisible(false);
                    settings.setVisible(false);
                    setTheme.setVisible(false);
                    GUI.updateRulesData.setVisible(false);
                    Run.runRules();

                } catch (IOException | URISyntaxException ex)
                {
                    JOptionPane.showMessageDialog(null, "Rules failed to run, unknown cause", "ERROR", JOptionPane.WARNING_MESSAGE);
                }

//                    }
//                });
//                run.start();
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon r = new ImageIcon(color(tertiary, "Resources/metro/buttons/runPressed.png"));
                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
                run.setIcon(rs);
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon r = new ImageIcon(color(tertiary, "Resources/metro/buttons/run.png"));
                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
                run.setIcon(rs);
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon r = new ImageIcon(color(tertiary, "Resources/metro/buttons/runHover.png"));
                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
                run.setIcon(rs);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon r = new ImageIcon(color(tertiary, "Resources/metro/buttons/run.png"));
                ImageIcon rs = new ImageIcon(r.getImage().getScaledInstance(run.getWidth(), run.getHeight(), java.awt.Image.SCALE_DEFAULT));
                run.setIcon(rs);
            }
        });

        delete.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

                Delete.deleteRule();

            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon d = new ImageIcon(color(tertiary, "Resources/metro/buttons/deletePressed.png"));
                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
                delete.setIcon(ds);
            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon d = new ImageIcon(color(tertiary, "Resources/metro/buttons/delete.png"));
                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
                delete.setIcon(ds);
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon d = new ImageIcon(color(tertiary, "Resources/metro/buttons/deleteHover.png"));
                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
                delete.setIcon(ds);
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon d = new ImageIcon(color(tertiary, "Resources/metro/buttons/delete.png"));
                ImageIcon ds = new ImageIcon(d.getImage().getScaledInstance(delete.getWidth(), delete.getHeight(), java.awt.Image.SCALE_DEFAULT));
                delete.setIcon(ds);
            }
        });

        save.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

                anim.setVisible(true);
                rulesPane.setVisible(false);
                save.setVisible(false);
                GUI.trakt.setVisible(false);
                cb.setVisible(false);
                GUI.run.setVisible(false);
                ruleName.setVisible(false);
                name.setVisible(false);
                search.setVisible(false);
                searchIn.setVisible(false);
                sequencePane.setVisible(false);
                delete.setVisible(false);
                inputs.setVisible(false);
                //                      start.setVisible(false);
                //                    endPoint.setVisible(false);
                settings.setVisible(false);
                setTheme.setVisible(false);
                GUI.updateRulesData.setVisible(false);

                ImageIcon a;
                ImageIcon as;
                try
                {
                    a = new ImageIcon(color(secondary, "Resources/metro/types/addNewRule.png"));
                    as = new ImageIcon(a.getImage().getScaledInstance((int) (frame.getHeight() / 1.2), (int) (frame.getHeight() / 1.2), java.awt.Image.SCALE_DEFAULT));
                    anim.setIcon(as);
                } catch (NullPointerException e)
                {

                }

                Thread thread = new Thread(new Runnable()
                {

                    @Override
                    public void run()
                    {

                        try
                        {
                            //  System.out.println(cb.getSelectedItem().toString().toLowerCase().trim());
                            Save.save(cb.getSelectedItem().toString().toLowerCase().trim(), name.getText(), search.getText(), quantity.getText(), searchInFolderText.getText(), moveToFolderText.getText(), searchForText.getText());
                            //reload 
                            loadSequenceContent(name.getText(), cb.getSelectedItem().toString().toLowerCase().trim());

                            //restore gui
                            anim.setVisible(false);
                            rulesPane.setVisible(true);
                            save.setVisible(true);
                            cb.setVisible(true);
                            GUI.run.setVisible(true);
                            ruleName.setVisible(true);
                            name.setVisible(true);
                            search.setVisible(true);
                            searchIn.setVisible(true);
                            sequencePane.setVisible(true);
                            delete.setVisible(true);
                            inputs.setVisible(true);
                            settings.setVisible(true);
                            setTheme.setVisible(true);
                            GUI.trakt.setVisible(true);
                            GUI.updateRulesData.setVisible(true);

                        } catch (Exception ex)
                        {
                            System.out.println(ex);
                        }

                    }
                });

                thread.start();

            }

            @Override
            public void mousePressed(MouseEvent me
            )
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/savePressed.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);
            }

            @Override
            public void mouseReleased(MouseEvent me
            )
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/save.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);
            }

            @Override
            public void mouseEntered(MouseEvent me
            )
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/saveHover.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);
            }

            @Override
            public void mouseExited(MouseEvent me
            )
            {
                ImageIcon s = new ImageIcon(color(tertiary, "Resources/metro/buttons/save.png"));
                ImageIcon ss = new ImageIcon(s.getImage().getScaledInstance(save.getWidth(), save.getHeight(), java.awt.Image.SCALE_DEFAULT));
                save.setIcon(ss);
            }
        }
        );

        name.addMouseListener(
                new MouseAdapter()
                {

                    @Override
                    public void mouseClicked(MouseEvent e
                    )
                    {
                        //only blank if the text is the default instructions
                        if (name.getText().equals("..."))
                        {
                            name.setText("");
                        }
                    }
                }
        );

        search.addMouseListener(
                new MouseAdapter()
                {

                    @Override
                    public void mouseClicked(MouseEvent e
                    )
                    {
                        //only blank if the text is the default instructions
                        if (search.getText().equals("..."))
                        {
                            search.setText("");
                        }
                    }
                }
        );

        quantity.addMouseListener(
                new MouseAdapter()
                {

                    @Override
                    public void mouseClicked(MouseEvent e
                    )
                    {
                        //only blank if the text is the default instructions
                        if (quantity.getText().equals("..."))
                        {
                            quantity.setText("");
                        }
                    }
                }
        );

        searchInFolderText.addMouseListener(
                new MouseAdapter()
                {

                    @Override
                    public void mouseClicked(MouseEvent e
                    )
                    {
                        //only blank if the text is the default instructions
                        if (searchInFolderText.getText().equals("..."))
                        {
                            searchInFolderText.setText("");
                        }
                    }
                }
        );

        moveToFolderText.addMouseListener(
                new MouseAdapter()
                {

                    @Override
                    public void mouseClicked(MouseEvent e
                    )
                    {
                        //only blank if the text is the default instructions
                        if (moveToFolderText.getText().equals("..."))
                        {
                            moveToFolderText.setText("");
                        }
                    }
                }
        );

        searchForText.addMouseListener(
                new MouseAdapter()
                {

                    @Override
                    public void mouseClicked(MouseEvent e
                    )
                    {
                        //only blank if the text is the default instructions
                        if (searchForText.getText().equals("..."))
                        {
                            searchForText.setText("");
                        }
                    }
                }
        );

//        start.addMouseListener(new MouseAdapter()
//        {
//
//            @Override
//            public void mouseClicked(MouseEvent e)
//            {
//                //only blank if the text is the default instructions
//                if (start.getText().equals("..."))
//                {
//                    start.setText("");
//                }
//            }
//        });
//
//        end.addMouseListener(new MouseAdapter()
//        {
//
//            @Override
//            public void mouseClicked(MouseEvent e)
//            {
//                //only blank if the text is the default instructions
//                if (end.getText().equals("..."))
//                {
//                    end.setText("");
//                }
//            }
//        });
        frame.addMouseListener(
                new MouseAdapter()
                {

                    public void mousePressed(MouseEvent e)
                    {
                        posX = e.getX();
                        posY = e.getY();
                    }
                });

        frame.addMouseMotionListener(new MouseAdapter()
        {
            public void mouseDragged(MouseEvent evt)
            {
                //sets frame position when mouse dragged			
                frame.setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY);

            }
        });

        GUI.cb.addItemListener(new ItemListener()
        {

            @Override
            public void itemStateChanged(ItemEvent ie)
            {
                if (ie.getStateChange() == ItemEvent.SELECTED)
                {

                    //blank rules in ui
                    rulesListPanel.removeAll();
                    rulesListPanel.repaint();
                    rulesScroll.revalidate();
                    rulesScroll.repaint();

                    MetroUI.createRuleButtons(GUI.cb.getSelectedItem().toString().toLowerCase().trim());
                  //  System.out.println(GUI.cb.getSelectedItem().toString().toLowerCase().trim());
                    rulesListPanel.revalidate();
                    rulesListPanel.repaint();
                    rulesScroll.revalidate();
                    rulesScroll.repaint();

                    name.setText("...");
                    ruleName.setText(" " + GUI.cb.getSelectedItem().toString().trim() + " NAME ");
                    search.setText("...");
                    quantity.setText("...");
                    searchInFolderText.setText("...");
                    moveToFolderText.setText("...");
                    searchForText.setText("...");
                    
                    sequenceListPanel.removeAll();
                    sequenceListPanel.repaint();
                    sequenceScroll.revalidate();
                    sequenceScroll.repaint();

                    if (cb.getSelectedItem().toString().toLowerCase().trim().equals("tv show"))
                    {
                        textQuantity.setText(" Trackt URL ");
                        quantity.setSize(name.getWidth(), frame.getHeight() / 15);

                    } else if (cb.getSelectedItem().toString().toLowerCase().trim().equals("anime"))
                    {
                        textQuantity.setText(" NUMBER OF EPISODES ");
                        quantity.setSize(name.getWidth() / 5, frame.getHeight() / 15);

                    } else if (cb.getSelectedItem().toString().toLowerCase().trim().equals("comics"))
                    {
                        textQuantity.setText(" NUMBER OF ISSUES ");
                        quantity.setSize(name.getWidth() / 5, frame.getHeight() / 15);

                    }

                }
            }

        });

        setTheme.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                frame.setVisible(false);
                ThemeEditor.openThemeEditor(false);

            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/themePressed.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(setTheme.getWidth(), setTheme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                setTheme.setIcon(ts);

            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/theme.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(setTheme.getWidth(), setTheme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                setTheme.setIcon(ts);

            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/themeHover.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(setTheme.getWidth(), setTheme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                setTheme.setIcon(ts);

            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/theme.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(setTheme.getWidth(), setTheme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                setTheme.setIcon(ts);

            }
        });

        GUI.updateRulesData.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                try
                {

                    rulesPane.setVisible(false);
                    save.setVisible(false);
                    GUI.trakt.setVisible(false);
                    cb.setVisible(false);
                    GUI.run.setVisible(false);
                    ruleName.setVisible(false);
                    name.setVisible(false);
                    search.setVisible(false);
                    searchIn.setVisible(false);
                    sequencePane.setVisible(false);
                    delete.setVisible(false);
                    inputs.setVisible(false);
                    settings.setVisible(false);
                    setTheme.setVisible(false);
                    GUI.updateRulesData.setVisible(false);

                    anim.setVisible(true);
                    ImageIcon a;
                    ImageIcon as;
                    try
                    {
                        a = new ImageIcon(color(secondary, "Resources/metro/types/updatingRules.png"));
                        as = new ImageIcon(a.getImage().getScaledInstance((int) (frame.getHeight() / 1.2), (int) (frame.getHeight() / 1.2), java.awt.Image.SCALE_DEFAULT));
                        anim.setIcon(as);
                    } catch (NullPointerException e)
                    {

                    }

                    Trakt.UpdateTraktData.updateTvRules();

                } catch (IOException ex)
                {
                    Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/refreshPressed.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(GUI.updateRulesData.getWidth(), GUI.updateRulesData.getHeight(), java.awt.Image.SCALE_DEFAULT));
                GUI.updateRulesData.setIcon(ts);

            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/refresh.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(GUI.updateRulesData.getWidth(), GUI.updateRulesData.getHeight(), java.awt.Image.SCALE_DEFAULT));
                GUI.updateRulesData.setIcon(ts);
            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/refreshHover.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(GUI.updateRulesData.getWidth(), GUI.updateRulesData.getHeight(), java.awt.Image.SCALE_DEFAULT));
                GUI.updateRulesData.setIcon(ts);

            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/refresh.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(GUI.updateRulesData.getWidth(), GUI.updateRulesData.getHeight(), java.awt.Image.SCALE_DEFAULT));
                GUI.updateRulesData.setIcon(ts);

            }
        });

        GUI.trakt.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {

                try
                {
                    executeCommand("start http://www.trakt.tv/");
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/traktPressed.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(setTheme.getWidth(), setTheme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                GUI.trakt.setIcon(ts);

            }

            @Override
            public void mouseReleased(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/trakt.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(setTheme.getWidth(), setTheme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                GUI.trakt.setIcon(ts);

            }

            @Override
            public void mouseEntered(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/traktHover.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(setTheme.getWidth(), setTheme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                GUI.trakt.setIcon(ts);

            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                ImageIcon t = new ImageIcon(color(tertiary, "Resources/metro/buttons/trakt.png"));
                ImageIcon ts = new ImageIcon(t.getImage().getScaledInstance(setTheme.getWidth(), setTheme.getHeight(), java.awt.Image.SCALE_DEFAULT));
                GUI.trakt.setIcon(ts);

            }
        });

        GUI.search.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent me)
            {
                if (me.getClickCount() == 4)
                {
                    try
                    {
                       if(cb.getSelectedItem().toString().toLowerCase().contains("tv show"))
                        {
                            FileManager.executeCommand("start http://extratorrent.cc/search/?search=" + GUI.search.getText().replaceAll(" ", "+"));
                        }else if(cb.getSelectedItem().toString().toLowerCase().contains("comic"))
                        {
                            FileManager.executeCommand("start http://kat.cr/usearch/" + GUI.search.getText().replaceAll(" ", "+"));
                        }else if(cb.getSelectedItem().toString().toLowerCase().contains("anime"))
                        {
                            FileManager.executeCommand("start http://www.nyaa.se/?term=" + GUI.search.getText().replaceAll(" ", "+"));
                        }

                    } catch (InterruptedException ex)
                    {
                        Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent me)
            {

            }

            @Override
            public void mouseReleased(MouseEvent me)
            {

            }

            @Override
            public void mouseEntered(MouseEvent me)
            {

            }

            @Override
            public void mouseExited(MouseEvent me)
            {

            }
        });

    }

}
