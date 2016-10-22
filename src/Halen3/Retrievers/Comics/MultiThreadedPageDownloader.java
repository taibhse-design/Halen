/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Comics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MultiThreadedPageDownloader implements Runnable {

    private String pageURL, name, path;
    private int issueNumber;

    public MultiThreadedPageDownloader(String u, String n, String p, int i){
        this.pageURL=u;
        this.name=n;
        this.path=p;
        this.issueNumber=i;
    }

    @Override
    public void run() {
      //  System.out.println(Thread.currentThread().getName()+" Start. Command = "+name);
        try
        {
            processCommand();
        } catch (IOException ex)
        {
            System.out.println(name + " - error retrieving issue, skipped this round.....");
            System.out.println(ex);
           // Logger.getLogger(MultiThreadedPageDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(Thread.currentThread().getName()+" End.");
    }

    private void processCommand() throws MalformedURLException, IOException {
        //System.out.println("                         " + pageURL);
        URL url = new URL(pageURL);
        URLConnection conn = url.openConnection();
        String type = conn.getContentType();
        System.out.println("RETRIEVING: " + name.replaceAll("[^a-zA-Z0-9.-]", "_")+ "_" + String.format("%03d", issueNumber) + "." + type.replace("image/", ""));
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(path + String.format("%03d", issueNumber) + "." + type.replace("image/", ""));
        byte[] b = new byte[2048];
        int length;
        while ((length = is.read(b)) != -1)
        {
            os.write(b, 0, length);
        }
        is.close();
        os.close();
    }

//    @Override
//    public String toString(){
//        return this.command;
//    }
}
