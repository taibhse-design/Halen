package WebScrapers;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import halen.FileManager;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author TAIBHSE
 */
public class ExtraTorrent
{

  //  private static final WebClient webClient = new WebClient(BrowserVersion.CHROME);
  //  private static HtmlPage page;
  //  private static String html = "";
  //  private static Document doc;
  //  private static Elements parse, links;

    private static final String urlStart = "http://extratorrent.cc/search/?search=";
    private static final String urlEnd = "&new=1&x=0&y=0";

    //   private static 
    public static void main(String args[]) throws IOException
    {
        
       Document doc = Jsoup.parse(new String(Files.readAllBytes(FileSystems.getDefault().getPath("G:\\Desktop\\html.html"))));
        Elements links = doc.select("a");
        
        int i = 0;
        
        for (Element link : links)
        {
            if(link.attr("href").contains("/store/apps/details?id="))
            {
                i+=1;
                if((i%6) == 0)
                {
                    System.out.println("https://play.google.com" + link.attr("href"));
                }
            }
        }
    }
    
//        static String s = "";
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
  
    public static String getMagnet(String name, String sxxexx) throws IOException
    {
       WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //disable javascript to speed up site loading
        webClient.getOptions().setJavaScriptEnabled(false);
        String magnet = "";

        String series = name + " " + sxxexx;

        //code needed here to format series name correctly 
        String url = urlStart + series + urlEnd;

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

        //WebClient webClient = new WebClient(BrowserVersion.CHROME);

        // String kickass = "https://kat.cr/usearch/" + search + "/?rss=1";
       // System.out.println(url);
        HtmlPage page = webClient.getPage(url);  //was XmlPage

        String html = page.asXml();

        Document doc = Jsoup.parse(html);
        Elements parse = doc.select("td.tli");
        Elements links = parse.select("a");

        String subURLa = "http://extratorrent.cc";
        String subURLb = "";
//        linkSearch: for(Element link : links)
//        {
//            if(link.attr("title").contains("view") && link.attr("title").contains("torrent") && link.attr("title").toLowerCase().contains(sxxexx.toLowerCase()))
//            {
//                
//                 subURL = subURL + link.attr("href");
//                 System.out.println(subURL);
//                 break linkSearch;
//            }
//        }
        String split[] = name.split("\\s+");
        linkSearch:
        for (Element link : links)
        {
            //add space before and after text to allow exact word checks for start and end of text
            String title = " " + link.attr("title").replace("\n", "").replace("\r", "").replace("\t", "").replace("View comments", "").replace("Browse Other", "") + " ";
         //   System.out.println("TITLE:   " + title);
            //split search name into individual words to be used for checking

            //fix ep format to prevent miss ids with tags such as [1080] [720] [480]
            // String fixEp = " - " + ep;
            //add space to start and end of words to guarantee exact match
//            for (int i = 0; i < split.length; i++)
//            {
//                split[i] = " " + split[i] + " ";
//               // System.out.println("[" + split[i] + "]");
//            }
            //loop to test link test with all words to find latest correct magnet link
            test:
            for (int i = 0; i < split.length; i++)
            {
                //skip words starting with - as these are already handled by nyaa to remove series with words you dont want
                //  System.out.println("TESTING WORD:   " + split[i]);
                if (split[i].charAt(1) != '-') //char at 1 as space added to start in above loop
                {
                    //testing url and . not allowed in url so remove it if in search string before testing
                    if (split[i].charAt(0) == '.')
                    {
                        split[i] = split[i].replace(".", "");
                    }
                    //test if text contains word and episode
                    if (title.toLowerCase().contains(split[i].toLowerCase()) && title.toLowerCase().contains(sxxexx.toLowerCase()))
                    {
                        //if contains then set magnet link to this link
                        subURLb = link.attr("href");
                        //   System.out.println("VALID");
                        //if test is true on last loop then break link loop as most recent magnet is found
                        if ((i + 1) == split.length)
                        {
                            break linkSearch;
                        }
                    }//if link text does not contain word then blank magnet and break from test loop to move onto testing next link
                    else
                    {
                        // System.out.println("INVALID");
                        subURLb = "";
                        break test;
                    }
                }

            }

        }

        page = webClient.getPage(subURLa + subURLb);

        html = page.asXml();
        doc = Jsoup.parse(html);
        parse = doc.select("td.tabledata0");
        links = parse.select("a");

        magnetSearch:
        for (Element link : links)
        {
            if (link.attr("title").equals("Magnet link"))
            {
                // System.out.println("<" + sxxexx + ">" + link.attr("href") + "</" + sxxexx + ">");
                //magnet = "<" + sxxexx + ">" + link.attr("href") + "</" + sxxexx + ">";
                magnet = link.attr("href");

                break magnetSearch;
            }
        }

        return magnet;
    }
}
