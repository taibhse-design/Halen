package Halen3.Retrievers;


import Halen3.GUI.GUIBase;
import static Halen3.IO.GlobalSharedVariables.magnetHandler;
import Halen3.IO.FileManager;
import static Halen3.IO.FileManager.executeCommand;
import static Halen3.IO.FileManager.isProcessRunning;
import static Halen3.IO.FileManager.launchPath;
import static Halen3.IO.FileManager.readFile;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author TAIBHSE
 */
public class MagnetHandler 
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
        magnetHandler = FileManager.returnTag("handler", readFile(launchPath() + "\\settings.xml").getItem(0));
        launchHandler();
    }
    
    public static void launchHandler()
    {
               try
        {
            //launch torrent client
            
            if(isProcessRunning(new File(magnetHandler).getName()) == false)
            {
                System.out.println("---------------------------------------------------------");
                System.out.println("                Launching Torrent Client");
                System.out.println("---------------------------------------------------------");
                executeCommand("\"\"" + magnetHandler + "\"");
            }else
            {
                System.out.println("---------------------------------------------------------");
                System.out.println("           Torrent Client Already Running");
                System.out.println("---------------------------------------------------------");
            }
            
            
            Thread.sleep(1000);
            try{
            GUIBase.frame.setAlwaysOnTop(true);
            GUIBase.frame.setAlwaysOnTop(false);
            }catch(NullPointerException e)
            {
               //System.out.println("GUI NOT BUILT IN TIME, COULD NOT BRING UI TO MAIN FOCUS OVER TORRENT CLIENT"); 
            }
        } catch (InterruptedException ex)
        {
           System.out.println("\n\n\nERROR LAUNCHING TORRENT/MAGNET CLIENT.....\n" + magnetHandler +"\n\n\n");
            JOptionPane.showMessageDialog(null, "ERROR LAUNCHING TORRENT/MAGNET CLIENT! \n\nPlease launch handler manually before attempting to run halen.", "ERROR", JOptionPane.WARNING_MESSAGE);

        } catch (IOException ex)
        {
            Logger.getLogger(MagnetHandler.class.getName()).log(Level.SEVERE, null, ex);
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
                executeCommand("\"\"" + magnetHandler + "\" \"" + magnets.getItem(i).replace("amp;", "") + "\"\"");
                Thread.sleep(5);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(MagnetHandler.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("FAILED TO SEND TO MAGNET CLIENT");
                // JOptionPane.showMessageDialog(null, "ERROR SENDING MAGNET LINK!", "ERROR", JOptionPane.WARNING_MESSAGE);

            }
          
        }
        
        magnets.removeAll();
    }
}
