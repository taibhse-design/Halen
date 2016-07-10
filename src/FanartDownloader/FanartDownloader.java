/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanartDownloader;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import halen.FileManager;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author brenn
 */
public class FanartDownloader
{

   public static String imagePath;
    
    public static void main(String args[]) throws IOException
    {
        download("https://fanart.tv/series/295647/blindspot/");
    }
    
    
    public static void download(String fanarturl) throws IOException
    {
        imagePath = "";

        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions()
                .setThrowExceptionOnScriptError(false);
        //disable javascript to speed up site loading
        webClient.getOptions()
                .setJavaScriptEnabled(false);

        java.util.logging.Logger.getLogger(
                "com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        /* comment out to turn off annoying htmlunit warnings */

        HtmlPage page = webClient.getPage(fanarturl);  //was XmlPage

        String html = page.asXml();

        Document doc = Jsoup.parse(html);
        Elements seasons = doc.select("a");

        loop:
        for (Element value : seasons)
        {
            if (value.attr("href").contains("hdtvlogo"))
            {
                System.out.println("https://fanart.tv" + value.attr("href"));

                URL url = new URL("https://fanart.tv" + value.attr("href"));
                InputStream in = new BufferedInputStream(url.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = in.read(buf)))
                {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();

                FileOutputStream fos = new FileOutputStream(FileManager.launchPath() + "\\rules\\tv show\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png");
                fos.write(response);
                fos.close();
                imagePath = FileManager.launchPath() + "\\rules\\tv show\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png";
                break;
            }
        }
        
        
        

    }

}
