/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Manga;

import Halen3.CommandLine.ColorCmd;
import static Halen3.CommandLine.ColorCmd.fgRedBgWhite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

public class MultiThreadedVolumePageDownloader implements Runnable
{

    private String pageURL, name, path;
    private int issueNumber;

    public MultiThreadedVolumePageDownloader(String u, String n, String p, int i)
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
            ColorCmd.println(name + " - error retrieving pages, skipped this round.....", fgRedBgWhite);
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
            File outputfile = new File(path + String.format("%03d", issueNumber) + ".png");
            ImageIO.write(image, "png", outputfile);
        } catch (Exception e)
        {
            try
            {
            URL url = new URL(pageURL.replace(".png", ".jpg"));
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "");
            BufferedImage image = ImageIO.read(httpcon.getInputStream());
            File outputfile = new File(path + String.format("%03d", issueNumber) + ".jpg");
            ImageIO.write(image, "jpg", outputfile);
            }catch(Exception ex)
            {
                System.out.println("FAILED TO GET PAGE....." + pageURL);
            }
        }

    }

}
