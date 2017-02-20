/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Testing;

import static Halen3.IO.FileManager.unescapeHtml3;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author brenn
 */
public class Testing
{

    /**
     * this code is under testing, if all works relocate this code in place of old extratorrent code for magnet retriever,
     * this method can also be adapted to work for anime
     * @param search
     * @return 
     */
    public static String extratorrentRssSearchAngeMagnetRetriever(String search)
    {

        String[] groups =
        {
            "[ettv]", "[prime]", "[srigga]", "utr", "rick", "etc"
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
                 //   System.out.println(unescapeHtml3(elements[i].substring(elements[i].indexOf("<title>") + 7, elements[i].lastIndexOf("</title>"))));
                } else
                {
                    title = elements[i].substring(elements[i].indexOf("<title><![CDATA[") + 16, elements[i].lastIndexOf("]]></title>")).trim();
                  //  System.out.println(elements[i].substring(elements[i].indexOf("<title><![CDATA[") + 16, elements[i].lastIndexOf("]]></title>")).trim());
                }
                try
                {
                    //size
                    size = Integer.parseInt(elements[i].substring(elements[i].indexOf("<size>") + 6, elements[i].lastIndexOf("</size>"))) / 1024 / 1024;
                //    System.out.println(Integer.parseInt(elements[i].substring(elements[i].indexOf("<size>") + 6, elements[i].lastIndexOf("</size>"))) / 1024 / 1024 + "mb");
                } catch (StringIndexOutOfBoundsException | NumberFormatException e)
                {
                //    System.out.println("?????mb");
                }
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

                for (int j = 0; j < groups.length; j++)
                {
                    if (title.toLowerCase().contains(groups[j].toLowerCase()))
                    {
                        results[j] = magnet;
                       // System.out.println("VALID");
                    }
                }

              //  System.out.println("\n\n");
            }

            //print out retrieved magnet links in preferred order of group tracking
            for (int j = 0; j < results.length; j++)
            {
              //  System.out.println(results[j] + "\n\n\n");
                if (results[j] != null)
                {
                    return results[j];
                }
            }

        } else
        {
            System.out.println("NO RESULT FOR SEARCH: " + search + "   |   USING SITE: " + site.substring(site.indexOf("/") + 2, site.lastIndexOf("/")));
        }

        }catch(Exception e)
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
