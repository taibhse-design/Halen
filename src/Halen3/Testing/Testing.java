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
import Halen3.IO.FileManager;
import static Halen3.IO.FileManager.unescapeHtml3;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author brenn
 */
public class Testing
{
//public static String film = "";

    public static void main(String args[]) throws MalformedURLException, IllegalArgumentException, FeedException, IOException, InterruptedException
    {

      //  skyTrntFilmRssSearchMagnetRetriever("wonder woman 2017");
//        showRSSScraper();
//        
//        System.out.println(showRSSSearch("dark matter"));

//  System.out.println(extrntTvRssSearchMagnetRetriever("silicon valley s04e07"));
    }

    static List rawData = new List();

    public static String showRSSSearch(String search)
    {
        String magnet = "";

        for (int i = 0; i < rawData.getItemCount(); i++)
        {
            String data[] = search.split("\\S+");
            boolean found = true;
            compare:
            for (int j = 0; j < data.length; j++)
            {
                if (rawData.getItem(i).toLowerCase().contains(data[j].toLowerCase()))
                {
                    found = true;
                } else
                {
                    found = false;
                    break compare;
                }
            }

            if (found == true)
            {
                magnet = rawData.getItem(i).substring(rawData.getItem(i).indexOf("magnet:?xt="), rawData.getItem(i).length());
                return magnet;
            }

        }

        return magnet;
    }

    public static void showRSSScraper() throws UnsupportedEncodingException, IllegalArgumentException, FeedException, IOException
    {

        for (int j = 0; j <= 1000; j++)
        {
            try
            {
                Thread.sleep(5);
            } catch (InterruptedException ex)
            {
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

                System.out.println(num + "  content found...");

            } catch (IOException e)
            {
                //System.err.println(e);
                System.out.println(num + "  no content...");
            }

        }

        System.out.println(rawData.getItemCount());

        //String eztvRSS = "https://eztv.ag/ezrss.xml";
        PrintWriter out = new PrintWriter(Halen3.IO.FileManager.launchPath() + "\\scrapedShows.ini");
        for (int i = 0; i < rawData.getItemCount(); i++)
        {
            out.println(rawData.getItem(i));
        }
        out.close();
      //  System.out.println(feed);

        // System.out.println("RETRUNED MAGNET: " + skyTrntFilmRssSearchMagnetRetriever(film));
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
            "fum[ettv]", "lol[ettv]", "killers[ettv]", "dimension[ettv]", "deflate{ettv]", "batv[ettv]", "[prime]", "[srigga]", "utr", "rick", "etc", "rmteam", "x264-rbb", "x264-w4f", "x264-nogrp"
        };
        //
        //String results[] = {"empty", "empty", "empty", "empty", "empty", "empty", };

        String results[] = new String[groups.length];

        // String search = "apb s01e02";
        //extratorrent
        //  String site = "http://www.extratorrent.cc/rss.xml?type=search&search=" + search.replaceAll(" ", "+");
        String site = "http://torrentproject.se/rss/" + search.replaceAll(" ", "+") + "/";

        System.out.println(site);
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
                    String size = elements[i].substring(elements[i].indexOf("Size:") + 5, elements[i].lastIndexOf("bytes,")).trim();
                    String sizes[] = size.split(" ");
                    size = sizes[sizes.length - 1];

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
                        magnet = unescapeHtml3(elements[i].substring(elements[i].indexOf("<enclosure url=\"") + 16, elements[i].lastIndexOf("\"  length=")));
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

//    public static String skyTrntFilmRssSearchMagnetRetriever(String search) throws IOException, InterruptedException
//    {
//
//        ColorCmd.println(" ", fgWhiteBgBlue);
//        ColorCmd.printlnCenter("Searching for Film: " + search, fgWhiteBgBlue);
//        ColorCmd.println(" ", fgWhiteBgBlue);
//
//        String[] prefferedGroups =
//        {
//            "yify", "rarbg", "etrg", "AC3-EVO"
//        };
//        String[] allowedFormats =
//        {
//            "bdrip", "bluray", "hdrip", "1080p", "1080i", "webrip", "dvdrip", "xvid"
//        };
//
//        String[] bannedFormats =
//        {
//            "hdtc", "hdts", "hd.ts", "hd-ts", "hd.tc", "hd-tc", "hdcam", "hc.tc",
//            "hd.tc", "hd.cam", "hd-cam", "hd cam", "(cam)", "cam", "camrip", "hindi",
//            "Hindi", "480p", "3d", "3D", "DTS-HD", "Spanish", "SPANISH", "spanish",
//            "Latino", "Italian", "Sub Ita", "Ita Eng", "ENG-ITA", "ITA-ENG", "PORTUGUESE",
//            "[Greek]", "NL Subs", "iTA.ENG", "Multi-Subs", "4K", "4k", "UltraHD", "trailer"
//        };
//
//        //
//        //String results[] = {"empty", "empty", "empty", "empty", "empty", "empty", };
//        // String results[] = new String[allowedFormats.length];
//        List results = new List();
//        // String search = "apb s01e02";
//        //extratorrent
//        String site = "https://www.skytorrents.in/search/all/ed/1/" + search.replaceAll(" ", "%20") + "?l=en-us";
//
//        WebClient webClient = new WebClient(BrowserVersion.CHROME);
//
//        webClient.getOptions()
//                .setThrowExceptionOnScriptError(false);
//        //disable javascript to speed up site loading
//        webClient.getOptions()
//                .setJavaScriptEnabled(true);
//
//        webClient.getOptions().setRedirectEnabled(true); //was false
//
//        webClient.getOptions().setUseInsecureSSL(true);
//
//        java.util.logging.Logger.getLogger(
//                "com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */
//
//        webClient.waitForBackgroundJavaScript(100000);
//        HtmlPage page;
//        String html;
//
//        // site = "https://torrentproject.se/rss/"  + search.replaceAll(" ", "+") + "/";
//        //nyaa
//        // site = "https://www.nyaa.se/?page=rss&term=" + search.replaceAll(" ", "+") + "&offset=1";
//        // System.out.println(site);
//        page = webClient.getPage(site);  //was XmlPage
//        html = page.asXml();
//
//        // System.out.println(html);
//        Document doc = Jsoup.parse(html);
//        Elements mlinks = doc.select("a[href*=magnet:]");
//        Elements data = doc.select("tr");
//        Elements sizes = doc.select("td:contains(Size)");
//
////        if (data.size() == mlinks.size())
////        {
////            System.out.println("SCORE");
////        } else
////        {
////            System.out.println(mlinks.size() + "   " + data.size());
////        }
//        if (!html.contains("Found 0 results, Please rephrase query"))
//        {
//            //loop search results
//            for (int i = 0; i < mlinks.size(); i++)
//            {
//                String title = FileManager.getNameFromMagnet(mlinks.get(i).attr("href"));
//                float size = Float.parseFloat(data.get(i + 1).select("td").get(1).text().substring(0, data.get(i + 1).select("td").get(1).text().indexOf(" ")));
//                //convert size to kb for testing
//                if (data.get(i + 1).select("td").get(1).text().toLowerCase().contains("gb"))
//                {
//                    size = size * 1000000;
//                } else if (data.get(i + 1).select("td").get(1).text().toLowerCase().contains("mb"))
//                {
//                    size = size * 1000;
//                }
//                String magnet = mlinks.get(i).attr("href");
//                int seeds = Integer.parseInt(data.get(i + 1).select("td").get(4).text());
//                int leeches = Integer.parseInt(data.get(i + 1).select("td").get(5).text());
//
//                ColorCmd.println("", fgBlueBgWhite);
//                ColorCmd.println(title, fgBlueBgWhite);
//
//                ColorCmd.println("Torrent Size: " + data.get(i + 1).select("td").get(1).text(), fgBlueBgWhite);
//                ColorCmd.println("Torrent Seeds: " + seeds, fgBlueBgWhite);
//                ColorCmd.println("Torrent Leechers: " + leeches, fgBlueBgWhite);
//                String searchTerms[] = search.split(" ");
//                boolean badresult = false;
//                for (int j = 0; j < searchTerms.length; j++)
//                {
//                    if (!title.toLowerCase().contains(searchTerms[j].toLowerCase()))
//                    {
//                        badresult = true;
//                        ColorCmd.println("Name does not match search", fgRedBgWhite);
//                        ColorCmd.println("Skipping.....", fgRedBgWhite);
//                        ColorCmd.println(" ", fgBlueBgWhite);
//                        break;
//                    }
//                }
//
//                if (leeches < 10)
//                {
//                    badresult = true;
//                    ColorCmd.println("Leeches very low....torrent may be fake or a trap.....", fgRedBgWhite);
//                    ColorCmd.println("Skipping.....", fgRedBgWhite);
//                    ColorCmd.println(" ", fgBlueBgWhite);
//                }
//
//                if (seeds > 5 && badresult == false)
//                {
//                    if (size < 8000000)
//                    {
//                        boolean allowedFound = false;
//                        allowedLoop:
//                        for (int j = 0; j < allowedFormats.length; j++)
//                        {
//                            if (title.toLowerCase().contains(allowedFormats[j].toLowerCase()))
//                            {
//                                allowedFound = true;
//                                // ColorCmd.println("", fgBlueBgBlue);
//                                ColorCmd.println("Possible match candidate found, proceeding to test.....", fgBlueBgWhite);
//                                //ColorCmd.println("", fgBlueBgBlue);
//                                boolean add = true;
//                                //loop and check banned list to prevent cam rips and fake torrents getting added
//                                //to results
//                                bannedLoop:
//                                for (int k = 0; k < bannedFormats.length; k++)
//                                {
//                                    ColorCmd.println("TESTING " + title + " FOR " + bannedFormats[k], fgBlueBgWhite);
//
//                                    if (title.toLowerCase().contains(bannedFormats[k].toLowerCase()))
//                                    {
//                                        //  ColorCmd.println("", fgWhiteBgWhite);
//                                        ColorCmd.println("Parameter in Ban List caught in title", fgRedBgWhite);
//                                        // ColorCmd.println("", fgWhiteBgWhite);
//                                        add = false;
//                                        break bannedLoop;
//                                    }
//                                }
//
//                                if (add == true)
//                                {
//                                    // results[j] = magnet;
//                                    results.add(magnet);
//                                    // System.out.println("VALID\n\n");
//                                    ColorCmd.println("VALID", fgWhiteBgBlue);
//                                    break allowedLoop;
//                                } else
//                                {
//                                    //  results[j] = null;
//                                    //System.out.println("Skipping.....\n\n");
//                                    ColorCmd.println("Skipping.....", fgRedBgWhite);
//                                    ColorCmd.println("", fgWhiteBgWhite);
//                                }
//                            }
//
//                        }
//
//                        if (allowedFound == false)
//                        {
//                            ColorCmd.println("Does not meet standard requirements", fgRedBgWhite);
//                            ColorCmd.println("Skipping.....", fgRedBgWhite);
//                            ColorCmd.println(" ", fgBlueBgWhite);
//                        }
//
//                    } else
//                    {
//                        ColorCmd.println("Torrent file greater than 8gb, skipping for alternative result with better file size.....", fgRedBgWhite);
//                        ColorCmd.println("Skipping.....", fgRedBgWhite);
//                        ColorCmd.println(" ", fgBlueBgWhite);
//                    }
//                } else
//                {
//                    ColorCmd.println("Torrent Seeds Less than 5, no validity testing will be conducted.....", fgRedBgWhite);
//                    ColorCmd.println("Skipping.....", fgRedBgWhite);
//                    ColorCmd.println(" ", fgBlueBgWhite);
//                }
//
//                //   System.out.println("\n\n");
//            }
//
//            ColorCmd.println("", fgWhiteBgWhite);
//            ColorCmd.println("RESULTS - first match in set will be used if none of the results are released by a preffered group", fgBlueBgWhite);
//            //print out retrieved magnet links in preferred order of group tracking
//            for (int j = 0; j < results.getItemCount(); j++)
//            {
//                // System.out.println(results[j] + "\n\n\n");
//                if (results.getItem(j) != null)
//                {
//
//                    //  String magnet = "magnet:?xt=urn:btih:ba8da43b6aa1f51f896cf203dcb3705978d0b77a&amp;dn=The+Avengers+%282012%29+1080p+ENG-ITA+Multisub+x264+bluray&amp;tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80%2Fannounce&amp;tr=udp%3A%2F%2Fglotorrents.pw%3A6969%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337%2Fannounce&amp;tr=udp%3A%2F%2Fzer0day.to%3A1337%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce";
//                    String magnetName = FileManager.getNameFromMagnet(results.getItem(j));//results.getItem(j).substring(results.getItem(j).indexOf(";dn=") + 4, results.getItem(j).indexOf("&amp;tr="));
//                    //  magnetName = java.net.URLDecoder.decode(magnetName, "UTF-8");
//
//                    ColorCmd.println(j + 1 + "  " + magnetName + ": " + results.getItem(j), fgWhiteBgBlue);
//                    // return results[j];
//                }
//            }
//            //loop through set and return first match
//            for (int j = 0; j < results.getItemCount(); j++)
//            {
//                if (results.getItem(j) != null)
//                {
//
//                    for (int k = 0; k < prefferedGroups.length; k++)
//                    {
//                        if (FileManager.getNameFromMagnet(results.getItem(j)).toLowerCase().contains(prefferedGroups[k].toLowerCase()))
//                        {
//                            ColorCmd.println("", fgWhiteBgWhite);
//                            ColorCmd.println("Retrieved:  " + FileManager.getNameFromMagnet(results.getItem(j)) + "   " + results.getItem(j), fgWhiteBgBlue);
//                            ColorCmd.println("", fgWhiteBgWhite);
//                            return results.getItem(j);
//                        }
//                    }
//
//                }
//
//            }
//
//            for (int j = 0; j < results.getItemCount(); j++)
//            {
//                if (results.getItem(j) != null)
//                {
//                    ColorCmd.println("", fgWhiteBgWhite);
//                    ColorCmd.println("Retrieved:  " + FileManager.getNameFromMagnet(results.getItem(j)) + "   " + results.getItem(j), fgWhiteBgBlue);
//                    ColorCmd.println("", fgWhiteBgWhite);
//                    return results.getItem(j);
//                }
//            }
//
//        } else
//        {
//            ColorCmd.println("NO RESULT FOR SEARCH: " + search + "   |   USING SITE: " + site.substring(site.indexOf("/") + 2, site.lastIndexOf("/")), fgRedBgWhite);
//        }
//
//        return "";
//    }

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
