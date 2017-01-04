/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.IO;

import Halen3.IO.FileManager;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author brenn
 */
public class GlobalSharedVariables
{

    public static String pathToChromePortable = FileManager.returnTag("portablechromeexe", FileManager.readFile(FileManager.launchPath() + "\\settings.xml").getItem(0));
    public static String pathToChromeDriver = FileManager.returnTag("chromedriverexe", FileManager.readFile(FileManager.launchPath() + "\\settings.xml").getItem(0));
    public static String magnetHandler = FileManager.returnTag("handler", FileManager.readFile(FileManager.launchPath() + "\\settings.xml").getItem(0));
    public static String email = FileManager.returnTag("email", FileManager.readFile(FileManager.launchPath() + "\\settings.xml").getItem(0));
    public static String emails[] = email.split(", ");
    public static String testing = FileManager.returnTag("testing", FileManager.readFile(FileManager.launchPath() + "\\settings.xml").getItem(0));

    public static ChromeDriver driver;

    public static boolean moveDriverOffScreen = false;
    public static void main(String args[]) throws InterruptedException
    {
        startChromeDriver();
        Thread.sleep(5000);
        endChromeDriver();
    }

    public static boolean areSettingsValid()
    {
        boolean check = true;
        if (pathToChromePortable.trim().equals(""))
        {
            check = false;
        } else if (pathToChromeDriver.trim().equals(""))
        {
            check = false;
        } else if (magnetHandler.trim().equals(""))
        {
            check = false;
        } else if (email.trim().equals(""))
        {
            check = false;
        } else if (testing.trim().equals(""))
        {
            check = false;
        }

        if (check == false)
        {
            System.out.println("INVALID SETTINGS DETECTED - HALEN WILL NOT RUN UNTIL SETTINGS ARE PROPERLY SET!!!!!");
        }
        return check;
    }

    public static void startChromeDriver()
    {
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        //set options to use portable chrome to avoid messing with main browser
        ChromeOptions options = new ChromeOptions();
        options.setBinary(pathToChromePortable);
        System.out.println("PROFILE: " + pathToChromePortable.replace("GoogleChromePortable.exe","") + "Data\\profile");
        options.addArguments("--user-data-dir=" + pathToChromePortable.replace("GoogleChromePortable.exe","") + "Data\\profile");
        // options.addArguments("no-sandbox");
       // options.addExtensions(new File(pathToChromePortable.replace("GoogleChromePortable.exe", "adblockpluschrome-1.8.3.crx")));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        //start driver
        driver = new ChromeDriver(capabilities);
        if(moveDriverOffScreen == true)
        {
        driver.manage().window().setPosition(new Point(-2000, 0));
        }
    }

    public static void endChromeDriver()
    {

        // driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
        //driver.close();
        //  driver.quit();
        // driver.close();
        for (String handle : driver.getWindowHandles())
        {
            driver.switchTo().window(handle);
            driver.close();
        }
        driver.quit();
    }
}
