/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Halen3.Retrievers.TvShows;

import Halen3.Testing.Testing;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.awt.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TAIBHSE
 */
public class ShowRSS
{

    static List rawData = new List();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
    }
    
    public static String showRSSSearch(String seriesName, String ep)
    {
        String magnet = "";

        search: for (int i = 0; i < rawData.getItemCount(); i++)
        {
          //  String data[] = search.split("\\s+");
            boolean found = false;
           
                if (rawData.getItem(i).toLowerCase().contains(seriesName.toLowerCase().trim()))
                {
                    String epxxexx = ep.toLowerCase().trim();
                    String epxxx = ep.toLowerCase().trim().replace("s", "").replace("e", "");
                    if(epxxx.charAt(0) == '0')
                    {
                       epxxx = epxxx.replaceFirst("0", "");
                    }
                    String epxXxx = ep.toLowerCase().trim().replace("s", "").replace("e", "x");
                    if(epxXxx.charAt(0) == '0')
                    {
                       epxXxx = epxXxx.replaceFirst("0", "");
                    }
                    
                    String textToSearch = rawData.getItem(i).replace(rawData.getItem(i).substring(rawData.getItem(i).indexOf("magnet:?xt="), rawData.getItem(i).length()), "");
                    if(textToSearch.toLowerCase().contains(epxxexx)) //test for ep format SxxExx
                    {
                        
                        System.out.println(epxxexx + "   " + textToSearch.toLowerCase());
                       found = true;
                    }else if(textToSearch.toLowerCase().contains(epxxx)) //test for ep format xxx
                    {
                        System.out.println(epxxx + "   " + textToSearch.toLowerCase());
                       found = true;
                    }else if(textToSearch.toLowerCase().contains(epxXxx)) //test for ep format xXxx
                    {
                        System.out.println(epxXxx + "   " + textToSearch.toLowerCase());
                       found = true;
                    }else
                    {
                        found = false;
                    }
                 
                    
                } else
                {
                  //  System.out.println("NO MATCH: " + data[j].toLowerCase().trim() + "    in     " + rawData.getItem(i).toLowerCase());
                    found = false;
                 
                }
            
            
            if(found == true)
            {
                magnet = rawData.getItem(i).substring(rawData.getItem(i).indexOf("magnet:?xt="), rawData.getItem(i).length());
             //  System.out.println("FOUND: " + magnet);
                return magnet;
            }

        }

        return magnet;
    }

    public static void showRSSScraper()
    {

        PrintWriter out = null;
        try
        {
            for (int j = 0; j <= 1000; j++)
            {
                try
                {
                    Thread.sleep(5);
                } catch (InterruptedException ex)
                {
                    System.out.println("ERROR DELAYING SEARCH");
                    Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String num = j + "";
                if (num.length() == 1)
                {
                    num = "00" + num;
                } else if (num.length() == 2)
                {
                    num = "0" + num;
                }
                
                try
                {
                    URL feedUrl = new URL("https://showrss.info/show/" + num + ".rss");
                    
                    SyndFeedInput input = new SyndFeedInput();
                    SyndFeed feed = input.build(new XmlReader(feedUrl));
                   
                    
                    for (int i = 0; i < feed.getEntries().size(); i++)
                    {
                        String data = feed.getEntries().get(i).toString();
                        if (data.contains("magnet:?xt="))
                        {
                            
                            String magnet = data.substring(data.indexOf("<a href=\"") + 9, data.indexOf("</a>"));
                            String magnetName = magnet.substring(magnet.indexOf("&dn=") + 4, magnet.indexOf("&tr="));
                            magnetName = magnetName.replace("+", " ");
                            
                            rawData.add(num + " " + magnetName + " " + magnet);
                            //  System.out.println(magnetName);
                            //  System.out.println(magnet);
                            //  System.out.println();
                            
                            // System.out.println(data);
                        }
                    }
                    
                  //  System.out.println(num + "  content found...");
                    
                } catch (IOException e)
                {
                    //System.err.println(e);
                 //   System.out.println(num + "  no content...");
                } catch (IllegalArgumentException ex)
                {
                    System.out.println(ex);
                    Logger.getLogger(ShowRSS.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FeedException ex)
                {
                     System.out.println(ex);
                    Logger.getLogger(ShowRSS.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } //  System.out.println(rawData.getItemCount());
            out = new PrintWriter(Halen3.IO.FileManager.launchPath() + "\\scrapedShows.ini");
            for (int i = 0; i < rawData.getItemCount(); i++)
            {
                out.println(rawData.getItem(i));
            }   out.close();
        //  System.out.println(feed);

        // System.out.println("RETRUNED MAGNET: " + extrntFilmRssSearchMagnetRetriever(film));
        //   String title = "this is a full \n\t\r\b\fsentence here";
        //  System.out.print(title);
        } catch (FileNotFoundException ex)
        {
             System.out.println(ex);
            Logger.getLogger(ShowRSS.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            out.close();
        }
    }
    
    
 
}
