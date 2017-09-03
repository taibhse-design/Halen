package Halen3.Retrievers.Films;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgBlueBgWhite;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgBlue;
import static Halen3.CommandLine.ColorCmd.fgWhiteBgWhite;
import Halen3.IO.FileManager;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.awt.List;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author TAIBHSE
 */
public class SkyTorrentsFilmScraper 
{

    public static void main(String args[]) throws IOException, InterruptedException
    {
             skyTrntFilmRssSearchMagnetRetriever("wonder woman 2017");
    }
    
    public static String skyTrntFilmRssSearchMagnetRetriever(String search) throws IOException, InterruptedException
    {

        ColorCmd.println(" ", fgWhiteBgBlue);
        ColorCmd.printlnCenter("Searching for Film: " + search, fgWhiteBgBlue);
        ColorCmd.println(" ", fgWhiteBgBlue);

        String[] prefferedGroups =
        {
            "yify", "rarbg", "etrg", "AC3-EVO"
        };
        String[] allowedFormats =
        {
            "bdrip", "bluray", "hdrip", "1080p", "1080i", "webrip", "dvdrip", "xvid"
        };

        String[] bannedFormats =
        {
            "hdtc", "hdts", "hd.ts", "hd-ts", "hd.tc", "hd-tc", "hdcam", "hc.tc",
            "hd.tc", "hd.cam", "hd-cam", "hd cam", "(cam)", "cam", "camrip", "hindi",
            "Hindi", "480p", "3d", "3D", "DTS-HD", "Spanish", "SPANISH", "spanish",
            "Latino", "Italian", "Sub Ita", "Ita Eng", "ENG-ITA", "ITA-ENG", "PORTUGUESE",
            "[Greek]", "NL Subs", "iTA.ENG", "Multi-Subs", "4K", "4k", "UltraHD", "trailer"
        };

        //
        //String results[] = {"empty", "empty", "empty", "empty", "empty", "empty", };
        // String results[] = new String[allowedFormats.length];
        List results = new List();
       
        String site = "https://www.skytorrents.in/search/all/ed/1/" + search.replaceAll(" ", "%20") + "?l=en-us";

        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //disable javascript to speed up site loading
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setRedirectEnabled(true); //was false
        webClient.getOptions().setUseInsecureSSL(true);
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */
        webClient.waitForBackgroundJavaScript(100000);
        HtmlPage page;
        String html;

        page = webClient.getPage(site);  //was XmlPage
        html = page.asXml();

        // System.out.println(html);
        Document doc = Jsoup.parse(html);
        Elements mlinks = doc.select("a[href*=magnet:]");
        Elements data = doc.select("tr");
        Elements sizes = doc.select("td:contains(Size)");

//        if (data.size() == mlinks.size())
//        {
//            System.out.println("SCORE");
//        } else
//        {
//            System.out.println(mlinks.size() + "   " + data.size());
//        }
        if (!html.contains("Found 0 results, Please rephrase query"))
        {
            //loop search results
            for (int i = 0; i < mlinks.size(); i++)
            {
                String title = FileManager.getNameFromMagnet(mlinks.get(i).attr("href"));
                float size = Float.parseFloat(data.get(i + 1).select("td").get(1).text().substring(0, data.get(i + 1).select("td").get(1).text().indexOf(" ")));
                //convert size to kb for testing
                if (data.get(i + 1).select("td").get(1).text().toLowerCase().contains("gb"))
                {
                    size = size * 1000000;
                } else if (data.get(i + 1).select("td").get(1).text().toLowerCase().contains("mb"))
                {
                    size = size * 1000;
                }
                String magnet = mlinks.get(i).attr("href");
                int seeds = Integer.parseInt(data.get(i + 1).select("td").get(4).text());
                int leeches = Integer.parseInt(data.get(i + 1).select("td").get(5).text());

                ColorCmd.println("", fgBlueBgWhite);
                ColorCmd.println(title, fgBlueBgWhite);

                ColorCmd.println("Torrent Size: " + data.get(i + 1).select("td").get(1).text(), fgBlueBgWhite);
                ColorCmd.println("Torrent Seeds: " + seeds, fgBlueBgWhite);
                ColorCmd.println("Torrent Leechers: " + leeches, fgBlueBgWhite);
                String searchTerms[] = search.split(" ");
                boolean badresult = false;
                for (int j = 0; j < searchTerms.length; j++)
                {
                    if (!title.toLowerCase().contains(searchTerms[j].toLowerCase()))
                    {
                        badresult = true;
                        ColorCmd.println("Name does not match search", fgRedBgWhite);
                        ColorCmd.println("Skipping.....", fgRedBgWhite);
                        ColorCmd.println(" ", fgBlueBgWhite);
                        break;
                    }
                }

                if (leeches < 10)
                {
                    badresult = true;
                    ColorCmd.println("Leeches very low....torrent may be fake or a trap.....", fgRedBgWhite);
                    ColorCmd.println("Skipping.....", fgRedBgWhite);
                    ColorCmd.println(" ", fgBlueBgWhite);
                }

                if (seeds > 5 && badresult == false)
                {
                    if (size < 8000000)
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
                        ColorCmd.println("Torrent file greater than 8gb, skipping for alternative result with better file size.....", fgRedBgWhite);
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
            ColorCmd.println("RESULTS - first match in set will be used if none of the results are released by a preffered group", fgBlueBgWhite);
            //print out retrieved magnet links in preferred order of group tracking
            for (int j = 0; j < results.getItemCount(); j++)
            {
                // System.out.println(results[j] + "\n\n\n");
                if (results.getItem(j) != null)
                {

                    //  String magnet = "magnet:?xt=urn:btih:ba8da43b6aa1f51f896cf203dcb3705978d0b77a&amp;dn=The+Avengers+%282012%29+1080p+ENG-ITA+Multisub+x264+bluray&amp;tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80%2Fannounce&amp;tr=udp%3A%2F%2Fglotorrents.pw%3A6969%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337%2Fannounce&amp;tr=udp%3A%2F%2Fzer0day.to%3A1337%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce";
                    String magnetName = FileManager.getNameFromMagnet(results.getItem(j));//results.getItem(j).substring(results.getItem(j).indexOf(";dn=") + 4, results.getItem(j).indexOf("&amp;tr="));
                    //  magnetName = java.net.URLDecoder.decode(magnetName, "UTF-8");

                    ColorCmd.println(j + 1 + "  " + magnetName + ": " + results.getItem(j), fgWhiteBgBlue);
                    // return results[j];
                }
            }
            //loop through set and return first match
            for (int j = 0; j < results.getItemCount(); j++)
            {
                if (results.getItem(j) != null)
                {

                    for (int k = 0; k < prefferedGroups.length; k++)
                    {
                        if (FileManager.getNameFromMagnet(results.getItem(j)).toLowerCase().contains(prefferedGroups[k].toLowerCase()))
                        {
                            ColorCmd.println("", fgWhiteBgWhite);
                            ColorCmd.println("Retrieved:  " + FileManager.getNameFromMagnet(results.getItem(j)) + "   " + results.getItem(j), fgWhiteBgBlue);
                            ColorCmd.println("", fgWhiteBgWhite);
                            return results.getItem(j);
                        }
                    }

                }

            }

            for (int j = 0; j < results.getItemCount(); j++)
            {
                if (results.getItem(j) != null)
                {
                    ColorCmd.println("", fgWhiteBgWhite);
                    ColorCmd.println("Retrieved:  " + FileManager.getNameFromMagnet(results.getItem(j)) + "   " + results.getItem(j), fgWhiteBgBlue);
                    ColorCmd.println("", fgWhiteBgWhite);
                    return results.getItem(j);
                }
            }

        } else
        {
            ColorCmd.println("NO RESULT FOR SEARCH: " + search + "   |   USING SITE: " + site.substring(site.indexOf("/") + 2, site.lastIndexOf("/")), fgRedBgWhite);
        }

        return "";
    }
}
