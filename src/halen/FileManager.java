package halen;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

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

        // String processName = "qbittorrent";
        // System.out.println("is " + processName + " Running: " + isProcessRunning(processName));
        System.out.println(hasDatePassed("hgjhgjh"));
    }

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
            Scanner s = new Scanner(new File(file));
            List list = new List();
            while (s.hasNextLine())
            {

                list.add(s.nextLine());

            }
            s.close();

            return list;
        } catch (FileNotFoundException e)
        {
            System.out.println("ERROR");
            List empty = new List();
            return empty;
        }
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
            CodeSource codeSource = FileManager.class
                    .getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();

            return jarDir;
        } catch (URISyntaxException ex)
        {
            return "error";
        }

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
            System.out.println("ERROR RUNNING COMMAND -execute command failed");
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
}
