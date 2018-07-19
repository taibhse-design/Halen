package Halen3.Retrievers.Anime;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.awt.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author TAIBHSE
 */
public class Nyaa
{

  //  private static final WebClient webClient = new WebClient(BrowserVersion.CHROME);
    //  private static HtmlPage page;
    // private static String html = "";
    //  private static Document doc;
    //  private static Elements parse, links;
    private static final String urlStart = "http://nyaa.si/?f=0&c=1_2&q=";
    private static final String urlEnd = "";

    //   private static 
    public static void main(String args[]) throws IOException
    {

        for (float i = 1; i < 13; i += 0.5f)
        {

                Nyaa.getAnimeEpisodeMagnet("-720 -480 1080 horriblesubs full metal panic victory", i);
        }

    }
//   static String s = "";
//   public static String getMagnet(final String name, final String ep)
//   {
//        
//        Thread t;
//        t = new Thread(new Runnable() {
//            
//            @Override
//            public void run()
//            {
//                try
//                {
//                    s = getMagnett( name,  ep);
//                    System.out.println(name + " " + ep + " :     " + s);
//                } catch (IOException ex)
//                {
//                    Logger.getLogger(Nyaa.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }); t.start();
//        
//        return s;
//   }

    public static String getAnimeEpisodeMagnet(String name, float ep) throws IOException
    {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //disable javascript to speed up site loading
        webClient.getOptions().setJavaScriptEnabled(false);
        String magnet = "";
        String series = "";
        if (ep % 2 == 1.5 || ep % 2 == 0.5)
        {
            if(ep < 10)
            {
               series = name + " 0" + ep;
               
            }else
            {
               series = name + " " + ep; 
            }
        } else
        {
             if(ep < 10)
            {
               series = name + " 0" + (int)ep;
               
            }else
            {
               series = name + " " + (int)ep; 
            }
        }

        //code needed here to format series name correctly 
        String url = urlStart + series + urlEnd;

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

        HtmlPage page = webClient.getPage(url);  //was XmlPage

        //check for url redirect, if url returned contains view, then has been directed to page for episode already
        if (page.getUrl().toString().contains("page=view"))
        {
           // System.out.println("Link Autofound - " + page.getUrl().toString().replace("view", "download"));
            //  System.out.println(page.getUrl().toString().replace("view", "download"));
            //replace view with downlaod and return as magnet link
            return page.getUrl().toString().replace("view", "download");
        }

        //if page returned is a list of eps, then grab url to topmost as it is the most
        //recent version of an episode, grab url and replace view with download to get magnet link
        String html = page.asXml();

        Document doc = Jsoup.parse(html);
        Elements parse = doc.select("td");
        Elements links = parse.select("a");

        linkSearch:
        for (Element link : links)
        {
            
           // System.out.println(link.attr("href"));
            //add space before and after text to allow exact word checks for start and end of text
            String title = " " + link.ownText() + " ";
            
           // System.out.println("Trying " + title);
            //split search name into individual words to be used for checking
            String split[] = name.split("\\s+");
            //fix ep format to prevent miss ids with tags such as [1080] [720] [480]
            String fixEp = " - " + ep;

            if (ep < 10)
            {
                fixEp = " - 0" + ep;
            }
            
             if (ep % 2 == 1.5 || ep % 2 == 0.5)
        {
            if(ep < 10)
            {
               fixEp =  " - 0" + ep;
               
            }else
            {
               fixEp =  " - " + ep; 
            }
        } else
        {
             if(ep < 10)
            {
               fixEp = " - 0" + (int)ep;
               
            }else
            {
               fixEp = " - " + (int)ep; 
            }
        }
             
             
            // System.out.println("Searching ep " + fixEp);
             
            //add space to start and end of words to guarantee exact match
            for (int i = 0; i < split.length; i++)
            {
                split[i] = " " + split[i] + " ";
                // System.out.println("[" + split[i] + "]");
            }

            //loop to test link test with all words to find latest correct magnet link
            test:
            for (int i = 0; i < split.length; i++)
            {
                
                //skip words starting with - as these are already handled by nyaa to remove series with words you dont want
                if (split[i].charAt(1) != '-') //char at 1 as space added to start in above loop
                {
                  //  System.out.println(split[i]);
                    //testing url and . not allowed in url so remove it if in search string before testing
                    if (split[i].charAt(0) == '.')
                    {
                        split[i] = split[i].replaceFirst(".", "");
                    }

                   // System.out.println(fixEp);
                    //test if text contains word and episode
                   // if (title.toLowerCase().contains(split[i].toLowerCase().trim()) && title.contains(fixEp))
                    if (title.toLowerCase().contains(split[i].toLowerCase().trim()) && title.contains(fixEp))
                    
                    {
                        //if contains then set magnet link to this link
                        magnet = "http://nyaa.si" + link.attr("href").replace("view", "download") + ".torrent";
                        //  System.out.println("MAGNET: " + magnet);
                        //if test is true on last loop then break link loop as most recent magnet is found
                        if ((i + 1) == split.length)
                        {
                            break linkSearch;
                        }
                    }//if link text does not contain word then blank magnet and break from test loop to move onto testing next link
                    else
                    {
                        magnet = "";
                        break test;
                    }
                }

            }

        }

        return magnet;
    }

    public static String getAnimeOvaOrMovieMagnet(String name) throws IOException
    {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //disable javascript to speed up site loading
        webClient.getOptions().setJavaScriptEnabled(false);
        String magnet = "";

        String series = name;

        //code needed here to format series name correctly 
        String url = urlStart + series + urlEnd;

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

        HtmlPage page = webClient.getPage(url);  //was XmlPage

        //check for url redirect, if url returned contains view, then has been directed to page for episode already
        if (page.getUrl().toString().contains("page=view"))
        {
            //  System.out.println(page.getUrl().toString().replace("view", "download"));
            //replace view with downlaod and return as magnet link
            return page.getUrl().toString().replace("view", "download");
        }

        //if page returned is a list of eps, then grab url to topmost as it is the most
        //recent version of an episode, grab url and replace view with download to get magnet link
        String html = page.asXml();

        Document doc = Jsoup.parse(html);
        Elements parse = doc.select("td.tlistname");
        Elements links = parse.select("a");

        linkSearch:
        for (Element link : links)
        {
            //add space before and after text to allow exact word checks for start and end of text
            String title = " " + link.ownText() + " ";
            //split search name into individual words to be used for checking
            String split[] = name.split("\\s+");
            //fix ep format to prevent miss ids with tags such as [1080] [720] [480]
            // String fixEp = " - " + ep;
            //add space to start and end of words to guarantee exact match
            for (int i = 0; i < split.length; i++)
            {
                split[i] = " " + split[i] + " ";
                // System.out.println("[" + split[i] + "]");
            }

            //loop to test link test with all words to find latest correct magnet link
            test:
            for (int i = 0; i < split.length; i++)
            {
                //skip words starting with - as these are already handled by nyaa to remove series with words you dont want
                if (split[i].charAt(1) != '-') //char at 1 as space added to start in above loop
                {
                    //testing url and . not allowed in url so remove it if in search string before testing
                    if (split[i].charAt(0) == '.')
                    {
                        split[i] = split[i].replaceFirst(".", "");
                    }
                    //test if text contains word and episode
                    if (title.toLowerCase().contains(split[i].toLowerCase()))// && title.contains(fixEp))
                    {
                        //if contains then set magnet link to this link
                        magnet = "http:" + link.attr("href").replace("view", "download");
                        //  System.out.println("MAGNET: " + magnet);
                        //if test is true on last loop then break link loop as most recent magnet is found
                        if ((i + 1) == split.length)
                        {
                            break linkSearch;
                        }
                    }//if link text does not contain word then blank magnet and break from test loop to move onto testing next link
                    else
                    {
                        magnet = "";
                        break test;
                    }
                }

            }

        }

        return magnet;
    }
}
