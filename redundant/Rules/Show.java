package Rules;

import Halen3.IO.FileManager;
import static halen.GUI.cb;
import static halen.GUI.name;
import static halen.GUI.search;
import static halen.MetroUI.moveToFolderText;
import static halen.MetroUI.quantity;
import static halen.MetroUI.searchForText;
import static halen.MetroUI.searchInFolderText;

/**
 * @author TAIBHSE
 */
public class Show 
{

    public static void main(String args[])
    {
             System.out.println("HELLO WORLD");
    }
    
      public static void showRule(String rule)
    {
        name.setText(rule);
      
        search.setText(FileManager.returnTag("search", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/"+ cb.getSelectedItem().toString().toLowerCase().trim() +"/" + rule.trim() + ".xml").getItem(0)));
        
        quantity.setText(FileManager.returnTag("url", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/"+ cb.getSelectedItem().toString().toLowerCase().trim() +"/" + rule.trim() + ".xml").getItem(0)));
    
        searchInFolderText.setText(FileManager.returnTag("searchInFolder", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/"+ cb.getSelectedItem().toString().toLowerCase().trim() +"/" + rule.trim() + ".xml").getItem(0)));
    
         moveToFolderText.setText(FileManager.returnTag("moveToFolder", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/"+ cb.getSelectedItem().toString().toLowerCase().trim() +"/" + rule.trim() + ".xml").getItem(0)));
    
          searchForText.setText(FileManager.returnTag("searchFor", FileManager.readFile(Halen3.IO.FileManager.launchPath() + "/rules/"+ cb.getSelectedItem().toString().toLowerCase().trim() +"/" + rule.trim() + ".xml").getItem(0)));
    
    
    }
}
