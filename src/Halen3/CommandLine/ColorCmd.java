package Halen3.CommandLine;

import org.fusesource.jansi.AnsiConsole;

/**
 *
 * @author TAIBHSE
 */
public class ColorCmd
{

   
 public static final String fgDefaultBgDefault = "\u001b[0m";
    public static final int consoleWidth = 148;  //150 for surface console, 114 for netbeans
    
    public static String fgBlackBgBlack = "\u001b[30;40m";
    public static String fgRedBgBlack = "\u001b[31;40m";
    public static String fgGreenBgBlack = "\u001b[32;40m";
    public static String fgYellowBgBlack = "\u001b[33;40m";
    public static String fgBlueBgBlack = "\u001b[34;40m";
    public static String fgPinkBgBlack = "\u001b[35;40m";
    public static String fgCyanBgBlack = "\u001b[36;40m";
    public static String fgWhiteBgBlack = "\u001b[37;40m";
    
    public static String fgBlackBgRed = "\u001b[30;41m";
    public static String fgRedBgRed = "\u001b[31;41m";
    public static String fgGreenBgRed = "\u001b[32;41m";
    public static String fgYellowBgRed = "\u001b[33;41m";
    public static String fgBlueBgRed = "\u001b[34;41m";
    public static String fgPinkBgRed = "\u001b[35;41m";
    public static String fgCyanBgRed = "\u001b[36;41m";
    public static String fgWhiteBgRed = "\u001b[37;41m";
    public static String fgBlackBgGreen = "\u001b[30;42m";
    public static String fgRedBgGreen = "\u001b[31;42m";
    public static String fgGreenBgGreen = "\u001b[32;42m";
    public static String fgYellowBgGreen = "\u001b[33;42m";
    public static String fgBlueBgGreen = "\u001b[34;42m";
    public static String fgPinkBgGreen = "\u001b[35;42m";
    public static String fgCyanBgGreen = "\u001b[36;42m";
    public static String fgWhiteBgGreen = "\u001b[37;42m";
    public static String fgBlackBgYellow = "\u001b[30;43m";
    public static String fgRedBgYellow = "\u001b[31;43m";
    public static String fgGreenBgYellow = "\u001b[32;43m";
    public static String fgYellowBgYellow = "\u001b[33;43m";
    public static String fgBlueBgYellow = "\u001b[34;43m";
    public static String fgPinkBgYellow = "\u001b[35;43m";
    public static String fgCyanBgYellow = "\u001b[36;43m";
    public static String fgWhiteBgYellow = "\u001b[37;43m";
    public static String fgBlackBgBlue = "\u001b[30;44m";
    public static String fgRedBgBlue = "\u001b[31;44m";
    public static String fgGreenBgBlue = "\u001b[32;44m";
    public static String fgYellowBgBlue = "\u001b[33;44m";
    public static String fgBlueBgBlue = "\u001b[34;44m";
    public static String fgPinkBgBlue = "\u001b[35;44m";
    public static String fgCyanBgBlue = "\u001b[36;44m";
    public static String fgWhiteBgBlue = "\u001b[37;44m";
    public static String fgBlackBgPink = "\u001b[30;45m";
    public static String fgRedBgPink = "\u001b[31;45m";
    public static String fgGreenBgPink = "\u001b[32;45m";
    public static String fgYellowBgPink = "\u001b[33;45m";
    public static String fgBlueBgPink = "\u001b[34;45m";
    public static String fgPinkBgPink = "\u001b[35;45m";
    public static String fgCyanBgPink = "\u001b[36;45m";
    public static String fgWhiteBgPink = "\u001b[37;45m";
    public static String fgBlackBgCyan = "\u001b[30;46m";
    public static String fgRedBgCyan = "\u001b[31;46m";
    public static String fgGreenBgCyan = "\u001b[32;46m";
    public static String fgYellowBgCyan = "\u001b[33;46m";
    public static String fgBlueBgCyan = "\u001b[34;46m";
    public static String fgPinkBgCyan = "\u001b[35;46m";
    public static String fgCyanBgCyan = "\u001b[36;46m";
    public static String fgWhiteBgCyan = "\u001b[37;46m";
    public static String fgBlackBgWhite = "\u001b[30;47m";
    public static String fgRedBgWhite = "\u001b[31;47m";
    public static String fgGreenBgWhite = "\u001b[32;47m";
    public static String fgYellowBgWhite = "\u001b[33;47m";
    public static String fgBlueBgWhite = "\u001b[34;47m";
    public static String fgPinkBgWhite = "\u001b[35;47m";
    public static String fgCyanBgWhite = "\u001b[36;47m";
    public static String fgWhiteBgWhite = "\u001b[37;47m";
    
    public static String textCapSize(String text)
    {
         if(text.length() < consoleWidth)
        {
            int spacesToAdd = consoleWidth - text.length();
            
            for(int i = 0; i <= spacesToAdd; i++)
            {
                text = text + " ";
            }
        }else if(text.length() > consoleWidth)
        {
           text = text.substring(0, consoleWidth-3) + "....";
        }
         
         return text;
    } 
    
    public static String textCapSizeCenter(String text)
    {
         if(text.length() < consoleWidth)
        {
            int spacesToAdd = consoleWidth - text.length();
            
            for(int i = 0; i <= spacesToAdd; i++)
            {
                if(i < spacesToAdd/2)
                {
                text = " " + text;
                }else
                {
                    text = text + " ";
                }
            }
        }else if(text.length() > consoleWidth)
        {
           text = text.substring(0, consoleWidth-3) + "....";
        }
         
         return text;
    } 
    
        public static String textCapSizeRight(String text)
    {
         if(text.length() < consoleWidth)
        {
            int spacesToAdd = consoleWidth - text.length();
            
            for(int i = 0; i <= spacesToAdd; i++)
            {
                
                text = " " + text;
               
            }
        }else if(text.length() > consoleWidth)
        {
           text = text.substring(0, consoleWidth-3) + "....";
        }
         
         return text;
    }
    
    public static void print(String text)
    {

         //cap text size to max console size
      //  text = textCapSize(text);
        
        //if NetBeansProjects in path run regular system prints as ide console supports ansi colours directly,
        if(System.getProperty("user.dir").contains("NetBeansProjects"))
        {
            System.out.print(text + fgDefaultBgDefault);
        }else //if NetBeansProjects not in path, use ansi,
        {
        AnsiConsole.systemInstall();
        AnsiConsole.out.print(text + fgDefaultBgDefault);
        AnsiConsole.systemUninstall();
        }
    }
    public static void println(String text)
    {
        //cap text size to max console size
        text = textCapSize(text);
        
         //if NetBeansProjects in path run regular system prints as ide console supports ansi colours directly,
        if(System.getProperty("user.dir").contains("NetBeansProjects"))
        {
            System.out.println(text + fgDefaultBgDefault);
        }else //if NetBeansProjects not in path, use ansi,
        {
        AnsiConsole.systemInstall();
        AnsiConsole.out.println(text + fgDefaultBgDefault);
        AnsiConsole.systemUninstall();
        }

    }
    
      public static void printlnCenter(String text)
    {
        //cap text size to max console size
        text = textCapSizeCenter(text);
        
         //if NetBeansProjects in path run regular system prints as ide console supports ansi colours directly,
        if(System.getProperty("user.dir").contains("NetBeansProjects"))
        {
            System.out.println(text + fgDefaultBgDefault);
        }else //if NetBeansProjects not in path, use ansi,
        {
        AnsiConsole.systemInstall();
        AnsiConsole.out.println(text + fgDefaultBgDefault);
        AnsiConsole.systemUninstall();
        }

    }
      
           public static void printlnRight(String text)
    {
        //cap text size to max console size
        text = textCapSizeRight(text);
        
         //if NetBeansProjects in path run regular system prints as ide console supports ansi colours directly,
        if(System.getProperty("user.dir").contains("NetBeansProjects"))
        {
            System.out.println(text + fgDefaultBgDefault);
        }else //if NetBeansProjects not in path, use ansi,
        {
        AnsiConsole.systemInstall();
        AnsiConsole.out.println(text + fgDefaultBgDefault);
        AnsiConsole.systemUninstall();
        }

    }

    public static void print(String text, String color)
    {
        //cap text size to max console size
       // text = textCapSize(text);
          //if NetBeansProjects in path run regular system prints as ide console supports ansi colours directly,
        if(System.getProperty("user.dir").contains("NetBeansProjects"))
        {
           System.out.print(color + text + fgDefaultBgDefault); 
        }else //if NetBeansProjects not in path, use ansi,
        {
        AnsiConsole.systemInstall();
        AnsiConsole.out.print(color + text + fgDefaultBgDefault);
        AnsiConsole.systemUninstall();
        }

    }

    public static void println(String text, String color)
    {
        //cap text size to max console size
        text = textCapSize(text);
        
           //if NetBeansProjects in path run regular system prints as ide console supports ansi colours directly,
        if(System.getProperty("user.dir").contains("NetBeansProjects"))
        {
            System.out.println(color + text + fgDefaultBgDefault);
        }else //if NetBeansProjects not in path, use ansi,
        {
        AnsiConsole.systemInstall();
        AnsiConsole.out.println(color + text + fgDefaultBgDefault);
        AnsiConsole.systemUninstall();
        }
    }
    
       public static void printlnCenter(String text, String color)
    {
        //cap text size to max console size
        text = textCapSizeCenter(text);
        
           //if NetBeansProjects in path run regular system prints as ide console supports ansi colours directly,
        if(System.getProperty("user.dir").contains("NetBeansProjects"))
        {
            System.out.println(color + text + fgDefaultBgDefault);
        }else //if NetBeansProjects not in path, use ansi,
        {
        AnsiConsole.systemInstall();
        AnsiConsole.out.println(color + text + fgDefaultBgDefault);
        AnsiConsole.systemUninstall();
        }
    }
       
           public static void printlnRight(String text, String color)
    {
        //cap text size to max console size
        text = textCapSizeRight(text);
        
           //if NetBeansProjects in path run regular system prints as ide console supports ansi colours directly,
        if(System.getProperty("user.dir").contains("NetBeansProjects"))
        {
            System.out.println(color + text + fgDefaultBgDefault);
        }else //if NetBeansProjects not in path, use ansi,
        {
        AnsiConsole.systemInstall();
        AnsiConsole.out.println(color + text + fgDefaultBgDefault);
        AnsiConsole.systemUninstall();
        }
    }

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws InterruptedException
    {
      //  println("this is a sample sentence", fgBlueBgRed);
        //println("this is a sentence", fgBlueBgRed);
        //println("this is a another sample sentence", fgBlueBgRed);
        //println("this is a another sentence", fgBlueBgRed);
        //println("this is getting pretty oring now", fgBlueBgRed);
        //println("made a big typo in that last one, this is really really really getting boring to type out fully now, i dont like typing very long sentences like this now, its torture", fgBlueBgRed);
        //println("this should be normal text");
       
//        System.out.println("\u001b[10;47m" + " testing text");
//        
//        print(" this is ", fgYellowBgBlue);
//        print(" getting pretty ",fgBlueBgYellow);
//        print(" boring now",fgRedBgCyan);
//        print("\n");
//        print(" this is ", fgRedBgBlue);
//        print(" getting pretty ",fgCyanBgYellow);
        printlnCenter("boring now");
        


    }

}
