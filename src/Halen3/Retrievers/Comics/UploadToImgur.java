/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.Retrievers.Comics;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.imageio.ImageIO;
import org.apache.xerces.impl.dv.util.Base64;

/**
 *
 * @author brenn
 */
public class UploadToImgur
{
    
    public static void main(String args[]) throws IOException
    {
        System.out.println(uploadImageToImgurAndGetImageURL("C:\\Users\\TAIBHSE\\Documents\\NetBeansProjects\\Halen\\build\\graphics\\comic-book-covers\\Cyberforce Strykeforce Opposing Forces.png"));
    }
    public static String uploadImageToImgurAndGetImageURL(String pathToImage) throws MalformedURLException, IOException
    {
        URL url;
    url = new URL("https://api.imgur.com/3/image");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

   // String data = URLEncoder.encode("image", "UTF-8") + "="
   //         + URLEncoder.encode("file:///C:/Users/brenn/Documents/NetBeansProjects/Halen/build/graphics/comic-book-covers/Lazarus%202013.png", "UTF-8");

    //create base64 image
    BufferedImage image = null;
    File file = new File(pathToImage);
    //read image
    image = ImageIO.read(file);
    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
    ImageIO.write(image, "png", byteArray);
    byte[] byteImage = byteArray.toByteArray();
    String dataImage = Base64.encode(byteImage);
    String data = URLEncoder.encode("image", "UTF-8") + "="
    + URLEncoder.encode(dataImage, "UTF-8");
    
    conn.setDoOutput(true);
    conn.setDoInput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Authorization", "Client-ID " +  "cf90665bf4a7546");// old id "240124a4ac02c34");
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type",
            "application/x-www-form-urlencoded");

    conn.connect();
    StringBuilder stb = new StringBuilder();
    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    wr.write(data);
    wr.flush();

    // Get the response
    BufferedReader rd = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
    String line;
    while ((line = rd.readLine()) != null) {
        stb.append(line).append("\n");
    }
    wr.close();
    rd.close();

    return("https://i.imgur.com/" + stb.toString().substring(15, 22) + file.getName().substring(file.getName().lastIndexOf("."), file.getName().length()));
    }
}
