
package Halen3.IO;

import Halen3.Main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author STARK
 */
public class FileManager
{

    public static void main(String args[]) throws InterruptedException, ParseException, IOException
    {

        removeWordMethod("ettv");

        //   System.out.println(howManyDaysSince(""));
        // String processName = "qbittorrent";
        // System.out.println("is " + processName + " Running: " + isProcessRunning(processName));
        //     System.out.println(hasDatePassed("hgjhgjh"));
        //  trimWhiteSpaceFromImage("C:\\Users\\brenn\\Documents\\NetBeansProjects\\Halen\\build\\graphics\\comic-book-covers\\Ghost in the Shell ARISE.png");
    }

    public static void saveURLImageToFile(String URL, String folderToSaveTo)
    {
    // This will get input data from the server
    InputStream inputStream = null;

    // This will read the data from the server;
    OutputStream outputStream = null;

    try {
        // This will open a socket from client to server
        URL url = new URL(URL);

       // This user agent is for if the server wants real humans to visit
        String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

       // This socket type will allow to set user_agent
        URLConnection con = url.openConnection();

        // Setting the user agent
        con.setRequestProperty("User-Agent", USER_AGENT);

        // Requesting input data from server
        inputStream = con.getInputStream();

        // Open local file writer
        outputStream = new FileOutputStream(folderToSaveTo + URL.substring(URL.lastIndexOf("/")));

        // Limiting byte written to file per loop
        byte[] buffer = new byte[2048];

        // Increments file size
        int length;

        // Looping until server finishes
        while ((length = inputStream.read(buffer)) != -1) {
            // Writing data
            outputStream.write(buffer, 0, length);
        }
    } catch (Exception ex) {
      //  Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
     }

        try
        {
            // closing used resources
            // The computer will not be able to use the image
            // This is a must
            
            outputStream.close();
            inputStream.close();
        } catch (IOException ex)
        {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }

    public static boolean isValidEmailId(String email)
    {
        String emailPattern = "^[\\p{L}\\p{N}\\._%+-]+@[\\p{L}\\p{N}\\.\\-]+\\.[\\p{L}]{2,}$";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = null;
        String emails[] = email.split(", ");
        boolean valid = true;
        for (int i = 0; i < emails.length; i++)
        {
            m = p.matcher(emails[i]);
            valid = m.matches();
            if (valid == false)
            {
                break;
            }
        }
        return valid;
    }

    /**
     * given a process name detects if it is currently running
     *
     * @param processName
     * @return
     * @throws IOException
     */
    public static boolean isProcessRunning(String processName) throws IOException
    {
        String line;
        String pidInfo = "";

        Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

        while ((line = input.readLine()) != null)
        {
            pidInfo += line;
        }

        input.close();

        if (pidInfo.contains(processName))
        {
            return true;
        } else
        {
            return false;
        }

    }

    public static String updateTag(String tag, String source, String newValue)
    {
        String link = source;
        String mod = "";
        try
        {
            link = link.substring(link.indexOf("<" + tag + ">"), link.indexOf("</" + tag + ">"));
            link = link.replace("<" + tag + ">", "");

            mod = source.replace("<" + tag + ">" + link, "<" + tag + ">" + newValue);
        } catch (StringIndexOutOfBoundsException e)
        {
            mod = source;
        }
        return mod;
    }

    public static String makeTag(String tagName, String value)
    {
        String tag = "<" + tagName + ">" + value + "</" + tagName + ">";
        return tag;
    }

    public static String returnTag(String tag, String source)
    {
        String link = source;
        try
        {
            link = link.substring(link.indexOf("<" + tag + ">"), link.indexOf("</" + tag + ">"));
            link = link.replace("<" + tag + ">", "");
            link = link.trim();
        } catch (StringIndexOutOfBoundsException e)
        {
            link = "";
        }
        return link;
    }

    /**
     * given a file containing text, this method returns an array list of each
     * line of the file
     *
     * @param file
     * @return
     */
    public static List readFile(String file)
    {
        try
        {
            // Open the file
            FileInputStream fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;
            List list = new List();
//Read File Line By Line
            while ((strLine = br.readLine()) != null)
            {
                // Print the content on the console
                //System.out.println (strLine);
                list.add(strLine);
            }

//Close the input stream
            br.close();
            return list;
        } catch (HeadlessException | IOException e)
        {
            e.printStackTrace();
            System.out.println(e + "ERROR WITH READ METHOD UNDER FILE MANAGER......");
            List empty = new List();
            return empty;
        }
//        try
//        {
//            Scanner s = new Scanner(new File(file));
//            List list = new List();
//            while (s.hasNextLine())
//            {
//
//                list.add(s.nextLine());
//
//            }
//            s.close();
//
//            return list;
//        } catch (FileNotFoundException e)
//        {
//            System.out.println("ERROR");
//            List empty = new List();
//            return empty;
//        }
    }

    /**
     * returns full path in which program is launched from
     *
     * @return
     */
    public static String launchPath()
    {
        try
        {

            String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");

            //remove first slash if path is letter path for windows
            if (decodedPath.charAt(1) != '/')
            {
                decodedPath = decodedPath.replaceFirst("/", "");
            }

            //remove classes folder for ide
            //    decodedPath = decodedPath.(decodedPath.charAt(decodedPath.length()-1)+"", "");
            // decodedPath = decodedPath.substring(0, decodedPath.lastIndexOf("/")+1);
            decodedPath = new File(decodedPath).getParentFile().getPath();
            //  System.out.println("LAUNCHPATH     " + decodedPath);

            return decodedPath;
            // return jarDir;
        } catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "error";

//        try
//        {
//            CodeSource codeSource = FileManager.class
//                    .getProtectionDomain().getCodeSource();
//            File jarFile = new File(codeSource.getLocation().toURI().getPath());
//            String jarDir = jarFile.getParentFile().getPath();
//
//            return jarDir;
//        } catch (URISyntaxException ex)
//        {
//            return "error";
//        }
    }

    /**
     * runs the given string as a command, ie in terminal or within the command
     * prompt (cmd).
     *
     * @param command
     */
    public static void executeCommand(String command) throws InterruptedException
    {

        Process p;
        try
        {
            p = Runtime.getRuntime().exec("cmd /c " + command);
            Thread.sleep(2000);
            p.destroy();

        } catch (IOException e)
        {
            System.out.println("ERROR RUNNING COMMAND -execute command failed" + "command was: " + command);
        }

    }

    /**
     * tests if a provided date is passed the current date, true if passed,
     * false if not, note date provided must be in the format dd-MM-yyyy to
     * avoid errors
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Boolean hasDatePassed(String date) throws ParseException
    {
        Boolean passed = false;
        try
        {
            if (!date.trim().equals(""))
            {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date testDate = format.parse(date);
                Date currentDate = format.parse(getCurrentDate());

                if (testDate.before(currentDate))
                {
                    passed = true;
                } else if (testDate.equals(currentDate))
                {
                    passed = true;
                }

            }//if provided date variable is blank, set true 
            else
            {
                passed = true;
            }
        } catch (ParseException e)
        {
            System.out.println("-----------------------------------------------------------------------------------------------"
                    + "\nERROR WITH DATE FORMAT...........PASSING TO ALLOW CONTINUED RUN...\n"
                    + "-----------------------------------------------------------------------------------------------");
            passed = true;
        }
        return passed;
    }

    public static int howManyDaysSince(String date)
    {

        // Date in String format.
        // String dateString = "2015-03-01";
        if (!date.trim().equals(""))
        {
            try
            {
                // Converting date to Java8 Local date
                LocalDate startDate = LocalDate.parse(date);
                LocalDate endtDate = LocalDate.now();
                // Range = End date - Start date
                return (int) ChronoUnit.DAYS.between(startDate, endtDate);
            } catch (Exception e)
            {
                System.out.println(e + "ERROR WITH HOWMANYDAYSSINCE METHOD - LINES 293-302; EXCEPTION CAUGHT RETURNING VALUE 0");
                return 0;
            }
            //  System.out.println("Number of days between the start date : " + dateString + " and end date : " + endtDate + " is  ==> " + range);

//        range = ChronoUnit.DAYS.between(endtDate, startDate);
//        System.out.println("Number of days between the start date : " + endtDate + " and end date : " + dateString
//                + " is  ==> " + range);
        } else
        {
            return 0;
        }
    }

    /**
     * returns a string containing the current date in the format DD-MM-YYYY
     *
     * @return
     */
    public static String getCurrentDate()
    {
        String date;
        Calendar cal = Calendar.getInstance();

        String day, month, year;

        year = cal.get(Calendar.YEAR) + "";

        if (cal.get(Calendar.DAY_OF_MONTH) <= 9)
        {
            day = "0" + cal.get(Calendar.DAY_OF_MONTH);
        } else
        {
            day = "" + cal.get(Calendar.DAY_OF_MONTH);
        }

        if ((cal.get(Calendar.MONTH) + 1) <= 9)
        {
            month = "0" + (cal.get(Calendar.MONTH) + 1);
        } else
        {
            month = "" + (cal.get(Calendar.MONTH) + 1);
        }

        //  date = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.YEAR);
        date = year + "-" + month + "-" + day;
        return date;
    }

    /**
     * given a path to a file or directory, this deletes that file or path
     *
     * @param file
     */
    public static void delete(File file)
    {

        if (file.isDirectory())
        {

            //directory is empty, then delete it
            if (file.list().length == 0)
            {

                file.delete();
                //    System.out.println("Directory is deleted : " + file.getAbsolutePath());

            } else
            {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files)
                {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0)
                {
                    file.delete();
                    // System.out.println("Directory is deleted : " + file.getAbsolutePath());
                }
            }

        } else
        {
            //if file, then delete it
            file.delete();
            //  System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }

    public static void trimWhiteSpaceFromImage(String image)
    {
        try
        {
            BufferedImage img = ImageIO.read(new File(image));

            int width = img.getWidth();
            int height = img.getHeight();
            int trimmedWidth = 0;
            int trimmedHeight = 0;

            for (int i = 0; i < height; i++)
            {
                for (int j = width - 1; j >= 0; j--)
                {
                    if (img.getRGB(j, i) != Color.WHITE.getRGB()
                            && j > trimmedWidth)
                    {
                        trimmedWidth = j;
                        break;
                    }
                }
            }

            for (int i = 0; i < width; i++)
            {
                for (int j = height - 1; j >= 0; j--)
                {
                    if (img.getRGB(i, j) != Color.WHITE.getRGB()
                            && j > trimmedHeight)
                    {
                        trimmedHeight = j;
                        break;
                    }
                }
            }

            BufferedImage newImg = new BufferedImage(trimmedWidth, trimmedHeight,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = newImg.createGraphics();
            g.drawImage(img, 0, 0, null);
            img = newImg;

            ImageIO.write(img, image.substring(image.lastIndexOf(".") + 1, image.length()), new File(image));

        } catch (IOException ex)
        {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void trimColorSpaceFromImage(String image, Color color)
    {
        try
        {
            BufferedImage img = ImageIO.read(new File(image));

            int width = img.getWidth();
            int height = img.getHeight();
            int trimmedWidth = 0;
            int trimmedHeight = 0;

            for (int i = 0; i < height; i++)
            {
                for (int j = width - 1; j >= 0; j--)
                {
                    if (img.getRGB(j, i) != color.getRGB()
                            && j > trimmedWidth)
                    {
                        trimmedWidth = j;
                        break;
                    }
                }
            }

            for (int i = 0; i < width; i++)
            {
                for (int j = height - 1; j >= 0; j--)
                {
                    if (img.getRGB(i, j) != color.getRGB()
                            && j > trimmedHeight)
                    {
                        trimmedHeight = j;
                        break;
                    }
                }
            }

            BufferedImage newImg = new BufferedImage(trimmedWidth, trimmedHeight,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = newImg.createGraphics();
            g.drawImage(img, 0, 0, null);
            img = newImg;

            ImageIO.write(img, image.substring(image.lastIndexOf(".") + 1, image.length()), new File(image));

        } catch (IOException ex)
        {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * given a string, this method returns it with decoded html escapes if any
     * are present
     *
     * @param str
     * @return
     */
    public static String unescapeHtml3(String str)
    {
        try
        {
            HTMLDocument doc = new HTMLDocument();
            new HTMLEditorKit().read(new StringReader("<html><body>" + str), doc, 0);
            return doc.getText(1, doc.getLength());
        } catch (Exception ex)
        {
            return str;
        }
    }

    public static void removeWordMethod(String wordstoremove) throws IOException
    {

        File folder = new File(launchPath() + "//rules//tv show");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++)
        {
            File inputFile = listOfFiles[i];
            File tempFile = new File(launchPath() + "//rules//TempWordlist.xml");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;

            while ((currentLine = reader.readLine()) != null)
            {
                String trimmedLine = currentLine.trim();
                if (trimmedLine.contains(wordstoremove))
                {

                    trimmedLine = trimmedLine.replace(wordstoremove, "");
                }
                writer.write(trimmedLine + "\n");
            }
            reader.close();
            writer.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);

        }
    }

    /**
     * Calculates the similarity (a number within 0 and 1) between two strings.
     */
    public static double similarity(String s1, String s2)
    {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length())
        { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0)
        {
            return 1.0;
            /* both strings are zero length */ }
        /* // If you have StringUtils, you can use it to calculate the edit distance:
         return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) /
         (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    // Example implementation of the Levenshtein Edit Distance
    // See http://rosettacode.org/wiki/Levenshtein_distance#Java
    public static int editDistance(String s1, String s2)
    {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++)
        {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++)
            {
                if (i == 0)
                {
                    costs[j] = j;
                } else
                {
                    if (j > 0)
                    {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                        {
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        }
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
            {
                costs[s2.length()] = lastValue;
            }
        }
        return costs[s2.length()];
    }

    public static String getNameFromMagnet(String magnet)
    {
        String magnetName = magnet.substring(magnet.indexOf("&dn=") + 4, magnet.indexOf("&tr="));
        magnetName = magnetName.replace("+", " ");
        magnetName = magnetName.replace("%20", " ");
        try
        {
            magnetName = java.net.URLDecoder.decode(magnetName, "UTF-8");
        } catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return magnetName;
    }
}
