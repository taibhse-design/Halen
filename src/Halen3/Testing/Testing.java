/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Testing;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgBlueBgBlue;
import static Halen3.CommandLine.ColorCmd.fgBlueBgWhite;
import static Halen3.CommandLine.ColorCmd.fgGreenBgWhite;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgBlue;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgGreen;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgWhite;
import static Halen3.IO.FileManager.unescapeHtml3;
import java.awt.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 *
 * @author brenn
 */

public class Testing
{
//public static String film = "";
    public static void main(String args[]) throws UnsupportedEncodingException
    {
      
        System.out.println("RETRUNED MAGNET: " + extrntFilmRssSearchMagnetRetriever(film));
        //   String title = "this is a full \n\t\r\b\fsentence here";
        //  System.out.print(title);
    }

    /**
     * this code is under testing, if all works relocate this code in place of
     * old extratorrent code for magnet retriever, this method can also be
     * adapted to work for anime
     *
     * @param search
     * @return
     */
    public static String extrntTvRssSearchMagnetRetriever(String search)
    {

//          ColorCmd.println(" ", fgWhiteBgGreen);
//        ColorCmd.printlnCenter("Searching for Film: " + search, fgWhiteBgGreen);
//        ColorCmd.println(" ", fgWhiteBgGreen);
        String[] groups =
        {
            "fum[ettv]", "lol[ettv]", "batv[ettv]", "[prime]", "[srigga]", "utr", "rick", "etc", "killers[ettv]", "dimension[ettv]"
        };
        //
        //String results[] = {"empty", "empty", "empty", "empty", "empty", "empty", };

        String results[] = new String[groups.length];

        // String search = "apb s01e02";
        //extratorrent
        String site = "http://www.extratorrent.cc/rss.xml?type=search&search=" + search.replaceAll(" ", "+");
        //nyaa
        // site = "https://www.nyaa.se/?page=rss&term=" + search.replaceAll(" ", "+") + "&offset=1";

        try
        {
            URLConnection connection = new URL(site).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null)
            {
                //  System.out.println(line);
                sb.append(line);
            }

            //  System.out.println(sb.toString());
            if (sb.toString().contains("<item>"))
            {
                String result = sb.toString().substring(sb.toString().indexOf("<item>"), sb.toString().lastIndexOf("</item>"));

                //System.out.println(sb.toString());
                String elements[] = result.split("</item>");

                ColorCmd.println("", fgWhiteBgWhite);
                //loop search results
                for (int i = 0; i < elements.length; i++)
                {
                    //  System.out.println(" Raw Data: " + elements[i]);
                    ColorCmd.println(" Testing Title: " + elements[i].substring(elements[i].indexOf("<title><![CDATA[") + 16, elements[i].lastIndexOf("]]></title>")).trim(), fgGreenBgWhite);

                    String title = "";
                    String size = elements[i].substring(elements[i].indexOf("length=\"") + 8, elements[i].lastIndexOf("\" type")).trim();
                    size = size.replaceAll("[^0-9]", "");
                    long mb = Long.parseLong(size) / 1024 / 1024;
                    ColorCmd.println(" File Size: " + mb + "mb", fgGreenBgWhite);
                    String magnet = "";
                    //title
                    if (elements[i].contains("<title><![CDATA[") == false)
                    {
                        title = unescapeHtml3(elements[i].substring(elements[i].indexOf("<title>") + 7, elements[i].lastIndexOf("</title>")));
                        // System.out.println(unescapeHtml3(elements[i].substring(elements[i].indexOf("<title>") + 7, elements[i].lastIndexOf("</title>"))));
                    } else
                    {
                        title = elements[i].substring(elements[i].indexOf("<title><![CDATA[") + 16, elements[i].lastIndexOf("]]></title>")).trim();
                        //  System.out.println(elements[i].substring(elements[i].indexOf("<title><![CDATA[") + 16, elements[i].lastIndexOf("]]></title>")).trim());
                    }
//                    try
//                    {
//                        //size
//                        size = Integer.parseInt(elements[i].substring(elements[i].indexOf("<size>") + 6, elements[i].lastIndexOf("</size>"))) / 1024 / 1024;
//                        //    System.out.println(Integer.parseInt(elements[i].substring(elements[i].indexOf("<size>") + 6, elements[i].lastIndexOf("</size>"))) / 1024 / 1024 + "mb");
//                    } catch (StringIndexOutOfBoundsException | NumberFormatException e)
//                    {
//                        //    System.out.println("?????mb");
//                    }
                    //magnet
                    if (elements[i].contains("magnetURI"))
                    {
                        magnet = elements[i].substring(elements[i].indexOf("<magnetURI><![CDATA[") + 20, elements[i].lastIndexOf("]]></magnetURI>"));
                        //  System.out.println(elements[i].substring(elements[i].indexOf("<magnetURI><![CDATA[") + 20, elements[i].lastIndexOf("]]></magnetURI>")));
                    } else
                    {
                        magnet = unescapeHtml3(elements[i].substring(elements[i].indexOf("<link>") + 6, elements[i].lastIndexOf("</link>")));
                        //  System.out.println(unescapeHtml3(elements[i].substring(elements[i].indexOf("<link>") + 6, elements[i].lastIndexOf("</link>"))));

                    }
                    boolean containsValidGroup = false;
                    for (int j = 0; j < groups.length; j++)
                    {

                        if (title.toLowerCase().contains(groups[j].toLowerCase()))
                        {
                            containsValidGroup = true;
                            ColorCmd.println(" Valid group found in title: " + groups[j], fgGreenBgWhite);
                            //test if ep file size is under 1gb
                            if (mb <= 1024)
                            {
                                ColorCmd.println(" File Under 1gb....Adding to results", fgGreenBgWhite);
                                ColorCmd.println("", fgWhiteBgWhite);
                                results[j] = magnet;
                            } else
                            {
                                ColorCmd.println(" File Over 1gb....Skipping", fgRedBgWhite);
                                ColorCmd.println("", fgWhiteBgWhite);

                            }
                            //         System.out.println("VALID");
                        }

                    }

                    if (containsValidGroup == false)
                    {
                        ColorCmd.println(" No Valid group found in title.....", fgRedBgWhite);
                        ColorCmd.println("", fgWhiteBgWhite);
                    }

                    //   System.out.println("\n\n");
                }

                //print out retrieved magnet links in preferred order of group tracking
                for (int j = 0; j < results.length; j++)
                {

                    if (results[j] != null)
                    {
                        ColorCmd.println(" Sending Magnet to Client: " + results[j], fgWhiteBgGreen);
                        ColorCmd.println("", fgWhiteBgWhite);
                        return results[j];
                    }
                }

            } else
            {
                ColorCmd.println("", fgWhiteBgWhite);
                ColorCmd.println(" " + search + " : No Results Found Using Site - " + site.substring(site.indexOf("/") + 2, site.lastIndexOf("/")), fgGreenBgWhite);
            }

        } catch (Exception e)
        {
            //e.printStackTrace();
            System.out.println("line 196 testing.java " + e);
        }
        return "";
    }
                                                                                                                                                                               
    public static   String film = "the hunger games 2012";
    public static String extrntFilmRssSearchMagnetRetriever(String search)
    {

        ColorCmd.println(" ", fgWhiteBgBlue);
        ColorCmd.printlnCenter("Searching for Film: " + search, fgWhiteBgBlue);
        ColorCmd.println(" ", fgWhiteBgBlue);
        String[] allowedFormats =
        {
            "bdrip", "bluray", "hdrip", "1080p", "1080i", "webrip", "dvdrip", "xvid"
        };

        String[] bannedFormats =
        {
            "hdtc", "hdts", "hd.ts", "hd-ts", "hd.tc", "hd-tc", "hdcam", "hc.tc",
            "hd.tc", "hd.cam", "hd-cam", "hd cam", "(cam)", "cam", "camrip", "hindi",
            "Hindi", "480p", "3d", "3D", "DTS-HD", "Spanish", "SPANISH", "spanish", 
            "Latino", "Italian", "Sub Ita", "Ita Eng","ENG-ITA","ITA-ENG", "PORTUGUESE",
            "[Greek]", "NL Subs", "iTA.ENG", "Multi-Subs", "4K", "4k", "UltraHD"
        };

        //
        //String results[] = {"empty", "empty", "empty", "empty", "empty", "empty", };
       // String results[] = new String[allowedFormats.length];
        List results = new List();
        // String search = "apb s01e02";
        //extratorrent
        String site = "http://www.extratorrent.cc/rss.xml?type=search&cid=4&search=" + search.replaceAll(" ", "+");
        //nyaa
        // site = "https://www.nyaa.se/?page=rss&term=" + search.replaceAll(" ", "+") + "&offset=1";

        try
        {
            URLConnection connection = new URL(site).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null)
            {
                //  System.out.println(line);
                sb.append(line);
            }

            //  System.out.println(sb.toString());
            if (sb.toString().contains("<item>"))
            {
                String result = sb.toString().substring(sb.toString().indexOf("<item>"), sb.toString().lastIndexOf("</item>"));

                //System.out.println(sb.toString());
                String elements[] = result.split("</item>");

                //loop search results
                for (int i = 0; i < elements.length; i++)
                {
                    //System.out.println(elements[i]+"\n\n");

                    String title = "";
                    int size = 0;
                    String magnet = "";
                    //title
                    if (elements[i].contains("<title><![CDATA[") == false)
                    {
                        title = unescapeHtml3(elements[i].substring(elements[i].indexOf("<title>") + 7, elements[i].lastIndexOf("</title>")));
                        ColorCmd.println("", fgBlueBgWhite);
                        ColorCmd.println(title, fgBlueBgWhite);
                    } else
                    {
                        title = elements[i].substring(elements[i].indexOf("<title><![CDATA[") + 16, elements[i].lastIndexOf("]]></title>")).trim();
                        ColorCmd.println("", fgBlueBgWhite);
                        ColorCmd.println(title, fgBlueBgWhite);
                    }
                    try
                    {
                        //size
                        size = Integer.parseInt(elements[i].substring(elements[i].indexOf("<size>") + 6, elements[i].lastIndexOf("</size>"))) / 1024 / 1024;
                        //    System.out.println(Integer.parseInt(elements[i].substring(elements[i].indexOf("<size>") + 6, elements[i].lastIndexOf("</size>"))) / 1024 / 1024 + "mb");
                    } catch (StringIndexOutOfBoundsException | NumberFormatException e)
                    {
                        //System.out.println("?????mb");
                    }
                    //magnet
                    if (elements[i].contains("magnetURI"))
                    {
                        magnet = elements[i].substring(elements[i].indexOf("<magnetURI><![CDATA[") + 20, elements[i].lastIndexOf("]]></magnetURI>"));
                        //    System.out.print(elements[i].substring(elements[i].indexOf("<magnetURI><![CDATA[") + 20, elements[i].lastIndexOf("]]></magnetURI>")));
                    } else
                    {
                        magnet = unescapeHtml3(elements[i].substring(elements[i].indexOf("<link>") + 6, elements[i].lastIndexOf("</link>")));
                        // System.out.print(unescapeHtml3(elements[i].substring(elements[i].indexOf("<link>") + 6, elements[i].lastIndexOf("</link>"))));

                    }

                    //only proceed if seeds greater than zero or preset minimum value
                    int seeds = 0;
                    int leeches = 0;
                    try
                    {
                        seeds = Integer.parseInt(unescapeHtml3(elements[i].substring(elements[i].indexOf("<seeders>") + 9, elements[i].lastIndexOf("</seeders>"))).trim());

                    } catch (Exception e)
                    {
                        // System.out.println(e);
                    }
                    try
                    {
                        leeches = Integer.parseInt(unescapeHtml3(elements[i].substring(elements[i].indexOf("<leechers>") + 10, elements[i].lastIndexOf("</leechers>"))).trim());

                    } catch (Exception e)
                    {
                        // System.out.println(e);
                    }
                    ColorCmd.println("Torrent Seeds: " + seeds, fgBlueBgWhite);
                    ColorCmd.println("Torrent Leechers: " + leeches, fgBlueBgWhite);

                    if (seeds > 5)
                    {
                        boolean allowedFound = false;
                        allowedLoop:
                        for (int j = 0; j < allowedFormats.length; j++)
                        {
                            if (title.toLowerCase().contains(allowedFormats[j].toLowerCase()))
                            {
                                allowedFound = true;
                                // ColorCmd.println("", fgBlueBgBlue);
                                ColorCmd.println("Possible match candidate found, proceeding to test.....", fgBlueBgWhite);
                                //ColorCmd.println("", fgBlueBgBlue);
                                boolean add = true;
                            //loop and check banned list to prevent cam rips and fake torrents getting added
                                //to results
                                bannedLoop:
                                for (int k = 0; k < bannedFormats.length; k++)
                                {
                                    ColorCmd.println("TESTING " + title + " FOR " + bannedFormats[k], fgBlueBgWhite);

                                    if (title.toLowerCase().contains(bannedFormats[k].toLowerCase()))
                                    {
                                        //  ColorCmd.println("", fgWhiteBgWhite);
                                        ColorCmd.println("Parameter in Ban List caught in title", fgRedBgWhite);
                                        // ColorCmd.println("", fgWhiteBgWhite);
                                        add = false;
                                        break bannedLoop;
                                    }
                                }

                                if (add == true)
                                {
                                   // results[j] = magnet;
                                    results.add(magnet);
                                    // System.out.println("VALID\n\n");
                                    ColorCmd.println("VALID", fgWhiteBgBlue);
                                    break allowedLoop;
                                } else
                                {
                                //  results[j] = null;
                                    //System.out.println("Skipping.....\n\n");
                                    ColorCmd.println("Skipping.....", fgRedBgWhite);
                                    ColorCmd.println("", fgWhiteBgWhite);
                                }
                            }

                        }

                        if (allowedFound == false)
                        {
                            ColorCmd.println("Does not meet standard requirements", fgRedBgWhite);
                            ColorCmd.println("Skipping.....", fgRedBgWhite);
                            ColorCmd.println(" ", fgBlueBgWhite);
                        }

                    } else
                    {
                        ColorCmd.println("Torrent Seeds Less than 5, no validity testing will be conducted.....", fgRedBgWhite);
                        ColorCmd.println("Skipping.....", fgRedBgWhite);
                        ColorCmd.println(" ", fgBlueBgWhite);
                    }

                    //   System.out.println("\n\n");
                }

                ColorCmd.println("", fgWhiteBgWhite);
                ColorCmd.println("RESULTS - first match in set will be used", fgBlueBgWhite);
                //print out retrieved magnet links in preferred order of group tracking
                for (int j = 0; j < results.getItemCount(); j++)
                {
                    // System.out.println(results[j] + "\n\n\n");
                    if (results.getItem(j) != null)
                    {

                      //  String magnet = "magnet:?xt=urn:btih:ba8da43b6aa1f51f896cf203dcb3705978d0b77a&amp;dn=The+Avengers+%282012%29+1080p+ENG-ITA+Multisub+x264+bluray&amp;tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80%2Fannounce&amp;tr=udp%3A%2F%2Fglotorrents.pw%3A6969%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337%2Fannounce&amp;tr=udp%3A%2F%2Fzer0day.to%3A1337%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce";

                        String magnetName = results.getItem(j).substring(results.getItem(j).indexOf(";dn=") + 4, results.getItem(j).indexOf("&amp;tr="));
                        magnetName = java.net.URLDecoder.decode(magnetName, "UTF-8");

                        ColorCmd.println(j + 1 + "  " + magnetName + ": "+ results.getItem(j), fgWhiteBgBlue);
                        // return results[j];
                    }
                }
                //loop through set and return first match
                for (int j = 0; j < results.getItemCount(); j++)
                {
                    if (results.getItem(j) != null)
                    {

                        //   ColorCmd.println(0 + "  " + results[0], fgWhiteBgBlue);
                        return results.getItem(j);
                    }

                }

            } else
            {
                ColorCmd.println("NO RESULT FOR SEARCH: " + search + "   |   USING SITE: " + site.substring(site.indexOf("/") + 2, site.lastIndexOf("/")), fgRedBgWhite);
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }
        return "";
    }

//    public static String unescapeHtml3(String str)
//    {
//        try
//        {
//            HTMLDocument doc = new HTMLDocument();
//            new HTMLEditorKit().read(new StringReader("<html><body>" + str), doc, 0);
//            return doc.getText(1, doc.getLength());
//        } catch (Exception ex)
//        {
//            return str;
//        }
//    }
}
