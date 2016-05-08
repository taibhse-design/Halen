package WebScrapers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.awt.List;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author TAIBHSE
 */
public class SeasonAndEpCountFinder
{

    /**
     * given a series title this method returns a list of season and episode tags from wikipedia
     * @param series
     * @return
     * @throws IOException 
     */
    public static List getFromWikipedia(String series) throws IOException
    {
        List list = new List();
        try
        {
        String html = "";

      //  String series = "bobby book";
        String urlStart = "http://en.wikipedia.org/wiki/List_of_";
        String urlEnd = "_episodes";

        //code needed here to format series name correctly 
        String url = urlStart + series + urlEnd;
System.out.println(url);
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        // String kickass = "https://kat.cr/usearch/" + search + "/?rss=1";
        HtmlPage page = webClient.getPage(url);  //was XmlPage

        html = page.asXml();

        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        org.jsoup.select.Elements table = doc.select("table#seriesoverviewtable");
        org.jsoup.select.Elements rows = table.select("tr");

        List tracking = new List();
        for (int i = 1; i < rows.size(); i++)
        { //first row is the col names so skip it.
            Element row = rows.get(i);
            Elements cols = row.select("td");
            int j = 0;
            String info = "";
            for (org.jsoup.nodes.Element col : cols)
            {
                if (j == 1 || j == 2)
                {
                    info = info + col.text() + " ";
                   if(info.contains("["))
                   {
                       info = info.replace(info.subSequence(info.indexOf("["), info.indexOf("]")+1), "");
                   }
                }
                j++;
            }
            tracking.add(info);

        }
        
        for(int i = 0; i < tracking.getItemCount(); i++)
        {
            String split[] = tracking.getItem(i).trim().split(" ");
            if(split.length > 1)
            {
                if(!split[0].contains("TBA") && !split[1].contains("TBA"))
                for(int j = 1; j <= Integer.parseInt(split[1]); j++)
                {
                    if(split[0].trim().length() < 2)
                    {
                        split[0] = "0" + split[0];
                    }
                    String ep = j + "";
                   if(ep.length() < 2)
                    {
                        ep = "0" + ep;   
                    }
                     list.add("<S"+split[0] + "E" + ep + ">" + "false</S"+split[0] + "E" + ep + ">");
                     System.out.println("<S"+split[0] + "E" + ep + ">" + "false</S"+split[0] + "E" + ep + ">");
                }
            }
        }

        }catch(FailingHttpStatusCodeException e)
        {
            System.out.println("error 404 page not found");
        }
        
        return list;
    }
    
    public static void main(String args[]) throws IOException
    {
        System.out.println(getFromWikipedia("fringe").getItemCount());
    }
     public static List getFromEpGuides(String series) throws IOException
    {
        List list = new List();
      //  try
      //  {
        String html = "";

      //  String series = "bobby book";
        String urlStart = "http://epguides.com/";
        String urlEnd = "/";

        //code needed here to format series name correctly 
        String url = urlStart + series.replace("the", "").replaceAll(" ", "") + urlEnd;

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */

        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        // String kickass = "https://kat.cr/usearch/" + search + "/?rss=1";
        HtmlPage page = webClient.getPage(url);  //was XmlPage

        html = page.asXml();

        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        org.jsoup.select.Elements parse = doc.select("pre");
        
        for(Element item : parse)
        {
            System.out.println(item.text());
        }
      //  org.jsoup.select.Elements rows = table.select("tr");

//        List tracking = new List();
//        for (int i = 1; i < rows.size(); i++)
//        { //first row is the col names so skip it.
//            Element row = rows.get(i);
//            Elements cols = row.select("td");
//            int j = 0;
//            String info = "";
//            for (org.jsoup.nodes.Element col : cols)
//            {
//                if (j == 1 || j == 2)
//                {
//                    info = info + col.text() + " ";
//                   if(info.contains("["))
//                   {
//                       info = info.replace(info.subSequence(info.indexOf("["), info.indexOf("]")+1), "");
//                   }
//                }
//                j++;
//            }
//            tracking.add(info);
//
//        }
//        
//        for(int i = 0; i < tracking.getItemCount(); i++)
//        {
//            String split[] = tracking.getItem(i).trim().split(" ");
//            if(split.length > 1)
//            {
//                if(!split[0].contains("TBA") && !split[1].contains("TBA"))
//                for(int j = 1; j <= Integer.parseInt(split[1]); j++)
//                {
//                    if(split[0].trim().length() < 2)
//                    {
//                        split[0] = "0" + split[0];
//                    }
//                    String ep = j + "";
//                   if(ep.length() < 2)
//                    {
//                        ep = "0" + ep;   
//                    }
//                     list.add("<S"+split[0] + "E" + ep + ">" + "false</S"+split[0] + "E" + ep + ">");
//                     
//                }
//            }
//        }
//
//        }catch(FailingHttpStatusCodeException e)
//        {
//            System.out.println("error 404 page not found");
//        }
//        
//        return list;
        
        return list;
    }
}
