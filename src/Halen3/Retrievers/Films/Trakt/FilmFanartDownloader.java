/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Films.Trakt;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgBlueBgWhite;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import Halen3.IO.FileManager;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
public class FilmFanartDownloader
{

   public static String imagePath;
    
    public static void main(String args[]) throws IOException
    {
        download("https://fanart.tv/movie/263115");
    }
    
    
    public static void download(String fanarturl) throws IOException
    {
        
        new File(FileManager.launchPath() + "\\graphics\\film-logos\\").mkdirs();
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
                ColorCmd.println("https://fanart.tv" + value.attr("href"), fgBlueBgWhite);

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

                FileOutputStream fos = new FileOutputStream(FileManager.launchPath() + "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png");
                fos.write(response);
                fos.close();
                imagePath =  "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png";
                break;
            }else if (value.attr("href").contains("clearlogo"))
            {
                ColorCmd.println("https://fanart.tv" + value.attr("href"), fgBlueBgWhite);

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

                FileOutputStream fos = new FileOutputStream(FileManager.launchPath() + "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png");
                fos.write(response);
                fos.close();
                imagePath =  "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png";
                break;
            }else if (value.attr("href").contains("hdclearart"))
            {
                ColorCmd.println("https://fanart.tv" + value.attr("href"), fgBlueBgWhite);

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

                FileOutputStream fos = new FileOutputStream(FileManager.launchPath() + "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png");
                fos.write(response);
                fos.close();
                imagePath =  "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png";
                break;
            }else if (value.attr("href").contains("tvthumb"))
            {
                ColorCmd.println("https://fanart.tv" + value.attr("href"), fgBlueBgWhite);

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

                FileOutputStream fos = new FileOutputStream(FileManager.launchPath() + "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png");
                fos.write(response);
                fos.close();
                imagePath =  "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png";
                break;
            }else  if (value.attr("href").contains("hdmovielogo"))
            {
               ColorCmd.println("https://fanart.tv" + value.attr("href"), fgBlueBgWhite);

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

                FileOutputStream fos = new FileOutputStream(FileManager.launchPath() + "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png");
                fos.write(response);
                fos.close();
                imagePath =  "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png";
                break;
            }else  if (value.attr("href").contains("hdmovieclearart"))
            {
              ColorCmd.println("https://fanart.tv" + value.attr("href"), fgBlueBgWhite);

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

                FileOutputStream fos = new FileOutputStream(FileManager.launchPath() + "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png");
                fos.write(response);
                fos.close();
                imagePath =  "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png";
                break;
            }else if (value.attr("href").contains("hdmovieclearart"))
            {
               ColorCmd.println("https://fanart.tv" + value.attr("href"), fgBlueBgWhite);

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

                FileOutputStream fos = new FileOutputStream(FileManager.launchPath() + "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png");
                fos.write(response);
                fos.close();
                imagePath =  "\\graphics\\film-logos\\"+ value.attr("href").substring(value.attr("href").lastIndexOf("/")+1, value.attr("href").lastIndexOf("-")).replace("-", " ") + ".png";
                break;
            }
            
        }
        
        
        

    }

}
