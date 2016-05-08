package MagnetHandler;


import halen.FileManager;
import static halen.FileManager.executeCommand;
import static halen.FileManager.isProcessRunning;
import static halen.FileManager.launchPath;
import static halen.FileManager.readFile;
import halen.GUI;
import halen.Main;
import static halen.Main.handler;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author TAIBHSE
 */
public class Handler 
{

    private static List magnets = new List();
    
    public static void addLinkTOMAgnetList(String link)
    {
        magnets.add(link);
    }
    
    public static int getMagnetCount()
    {
        return magnets.getItemCount();
    }
    
    public static void main(String args[])
    {
        handler = FileManager.returnTag("handler", readFile(launchPath() + "\\settings.xml").getItem(0));
        launchHandler();
    }
    
    public static void launchHandler()
    {
               try
        {
            //launch torrent client
            
            if(isProcessRunning(new File(Main.handler).getName()) == false)
            {
                System.out.println("---------------------------------------------------------");
                System.out.println("                Launching Torrent Client");
                System.out.println("---------------------------------------------------------");
                executeCommand("\"\"" + Main.handler + "\"");
            }else
            {
                System.out.println("---------------------------------------------------------");
                System.out.println("           Torrent Client Already Running");
                System.out.println("---------------------------------------------------------");
            }
            
            
            Thread.sleep(1000);
            try{
            GUI.frame.setAlwaysOnTop(true);
            GUI.frame.setAlwaysOnTop(false);
            }catch(NullPointerException e)
            {
               //System.out.println("GUI NOT BUILT IN TIME, COULD NOT BRING UI TO MAIN FOCUS OVER TORRENT CLIENT"); 
            }
        } catch (InterruptedException ex)
        {
           System.out.println("\n\n\nERROR LAUNCHING TORRENT/MAGNET CLIENT.....\n" + Main.handler+"\n\n\n");
            JOptionPane.showMessageDialog(null, "ERROR LAUNCHING TORRENT/MAGNET CLIENT! \n\nPlease launch handler manually before attempting to run halen.", "ERROR", JOptionPane.WARNING_MESSAGE);

        } catch (IOException ex)
        {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void sendToClient()
    {
      // launchHandler();

        //loop and send magnet links to torrent client
        for (int i = 0; i < magnets.getItemCount(); i++)
        {
            try
            {
                Thread.sleep(5);
                executeCommand("\"\"" + Main.handler + "\" \"" + magnets.getItem(i).replace("amp;", "") + "\"\"");
                Thread.sleep(5);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("FAILED TO SEND TO MAGNET CLIENT");
                // JOptionPane.showMessageDialog(null, "ERROR SENDING MAGNET LINK!", "ERROR", JOptionPane.WARNING_MESSAGE);

            }
          
        }
        
        magnets.removeAll();
    }
}
