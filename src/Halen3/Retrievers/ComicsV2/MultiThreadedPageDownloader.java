/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.ComicsV2;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

public class MultiThreadedPageDownloader implements Runnable
{

    private String pageURL, name, path;
    private int issueNumber;

    public MultiThreadedPageDownloader(String u, String n, String p, int i)
    {
        this.pageURL = u;
        this.name = n;
        this.path = p;
        this.issueNumber = i;
    }

    @Override
    public void run()
    {
        
        try
        {
            processCommand();
        } catch (IOException ex)
        {
            ColorCmd.println(name + " - error retrieving issue, skipped this round.....", fgRedBgWhite);
            ColorCmd.println(" " + ex, fgRedBgWhite);
            
        }
  
    }

    private void processCommand() throws MalformedURLException, IOException
    {

        try
        {
            URL url = new URL(pageURL);
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "");
            BufferedImage image = ImageIO.read(httpcon.getInputStream());
            File outputfile = new File(path + String.format("%03d", issueNumber) + ".jpg");
            ImageIO.write(image, "jpg", outputfile);
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

}
