package FileMover;

import halen.FileManager;
import static halen.FileManager.executeCommand;
import static halen.FileManager.isProcessRunning;
import static halen.FileManager.launchPath;
import static halen.FileManager.readFile;
import halen.Main;
import static halen.Main.handler;
import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import org.apache.commons.io.FileUtils;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

/**
 * @author TAIBHSE
 */
public class SearchAlgorithms
{

    static List tree = new List();

    static List filesToMove = new List();

    static List rules = new List();

    public static void main(String args[]) throws IOException
    {
        //   MoveFiles();
        handler = FileManager.returnTag("handler", readFile(launchPath() + "\\settings.xml").getItem(0));
        System.out.println(new File(Main.handler).getName());
        if (isProcessRunning(new File(Main.handler).getName()) == true | isProcessRunning(new File(Main.handler).getName().toLowerCase()) == true)
        {
            System.out.println("client running, need to terminate before moving files...");
            Runtime.getRuntime().exec("taskkill /F /IM " + new File(Main.handler).getName());
            
            try
            {
                Thread.sleep(5000);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(SearchAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private static boolean wasTorrentRunning = false;
    
    public static void MoveFiles() throws IOException
    {

         if (isProcessRunning(new File(Main.handler).getName()) == true | isProcessRunning(new File(Main.handler).getName().toLowerCase()) == true)
        {
            System.out.println("client running, need to terminate before moving files...");
            Runtime.getRuntime().exec("taskkill /F /IM " + new File(Main.handler).getName());
            wasTorrentRunning = true;
            try
            {
                Thread.sleep(5000);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(SearchAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        rules = getFolderTree(new File(FileManager.launchPath() + "\\rules\\"));

        tree = new List();

        //loop through all rules
        for (int j = 0; j < rules.getItemCount(); j++)
        {
            if (rules.getItem(j).endsWith(".xml"))
            {
                System.out.println("TRYING RULE: " + rules.getItem(j));

                List data = FileManager.readFile(rules.getItem(j));

                String searchString = FileManager.returnTag("searchFor", data.getItem(0));
                String searchFolder = FileManager.returnTag("searchInFolder", data.getItem(0));
                String dest = FileManager.returnTag("moveToFolder", data.getItem(0));

                if (!searchString.trim().equals("") && !searchFolder.trim().equals("") && !dest.trim().equals(""))
                {
                    System.out.println("SEARCH DIRECTORY: " + searchFolder + "   RELOCATION DIRECTORY: " + dest + "   SEARCH STRING: " + searchString);

                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    //loop through all issues or episodes
                    for (int k = 1; k < data.getItemCount(); k++)
                    {

                        System.out.println("\nSEARCHING FOR: " + searchString + " " + data.getItem(k).substring(1, data.getItem(k).indexOf(">")));
                        //get file tree in search folder and then search for matches based on rule
                        //this method adds matches to filesToMove list
                        tree = new List();
                        getAllFilesContaining(searchString + " " + data.getItem(k).substring(1, data.getItem(k).indexOf(">")), getFolderTree(new File(searchFolder)));

                        //loop through results and move files
                        if (filesToMove.getItemCount() != 0)
                        {
                            for (int i = 0; i < filesToMove.getItemCount(); i++)
                            {
                                if (rules.getItem(j).contains("tv show"))
                                {
                                    System.out.println("\nMOVING FILE: " + filesToMove.getItem(i) + "              TO: " + dest + "\\Season " + data.getItem(k).substring(2, data.getItem(k).indexOf("E")));
                                    fileRelocate(filesToMove.getItem(i), dest + "\\Season " + data.getItem(k).substring(2, data.getItem(k).indexOf("E")));
                                } else
                                {
                                    System.out.println("\nMOVING FILE: " + filesToMove.getItem(i) + "              TO: " + dest);
                                    fileRelocate(filesToMove.getItem(i), dest);

                                }

                            }

                            System.out.println("\n----------------------------------------------------------");

                            //blank for next try
                            filesToMove = new List();
                        }

                    }
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                    //purge at end to clear for next rule search
                    tree = new List();
                    filesToMove = new List();
                }
            }
        }
        
        
        if(wasTorrentRunning == true)
        {
           
             try
             {
                 //relaunch torrent client after done moving files
                 executeCommand("\"\"" + Main.handler + "\"");
             } catch (InterruptedException ex)
             {
                 Logger.getLogger(SearchAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    }

    public static void fileRelocate(String file, String dest) throws IOException
    {
        File in = new File(file); //file to move
        File out = null; //where to move file too

        //test if destination directory exists
        //if not exist, attempt to create it
        if (!(new File(dest).exists()))
        {
            System.out.println("MAKING DIRECTORY");
            new File(dest).mkdirs();
        }

        //check if dest ends in slash and add appropriate one before adding file output
        if (dest.endsWith("\\") || dest.endsWith("/"))
        {
            out = new File(dest + in.getName());
        } else if (dest.contains("\\"))
        {
            out = new File(dest + "\\" + in.getName());
        } else
        {
            out = new File(dest + "/" + in.getName());
        }

        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try
        {

            // magic number for Windows, 64Mb - 32Kb)
            int maxCount = (64 * 1024 * 1024) - (32 * 1024);
            long size = inChannel.size();
            long position = 0;
            while (position < size)
            {
                //progress of file while copying
                System.out.println((position / 1024) + " kb / " + (size / 1024) + " kb");
                // progress.setText("MOVING:    " + in.getName() + "   ( " + (position / 1024) + " kb / " + (size / 1024) + " kb )");
                position += inChannel.transferTo(position, maxCount, outChannel);
            }

            //shows file full copy
            System.out.println((position / 1024) + " kb / " + (size / 1024) + " kb");
            // progress.setText("MOVING:    " + in.getName() + "   ( " + (position / 1024) + " kb / " + (size / 1024) + " kb )");
        } finally
        {
            if (inChannel != null)
            {
                inChannel.close();
            }
            if (outChannel != null)
            {
                outChannel.close();
            }

            //check if output file exists
            if (out.exists())
            {

                // progress.setText("Checking   " + out.getName() + "    for corruption after moving.....");
                //get md5 checksum of input file
                FileInputStream fisA = new FileInputStream(in);
                String md5A = md5Hex(fisA);
                fisA.close();

                //get md5 checksum of output file
                FileInputStream fisB = new FileInputStream(out);
                String md5B = md5Hex(fisB);
                fisB.close();

                System.out.println("MD5 CHECK:  \noriginal >> " + md5A + " \nmoved    >> " + md5B);
                //if checksums equal, then copy has no errors or corruption
                if (md5A.equals(md5B))
                {
                    //   progress.setText(out.getName() + " passed corruption test....");

                    //delete input since copy successful 
                    deleteFileAndIdenticalNamedParentFolder(in);

                    System.out.println("MOVE SUCCESSFUL");

                } else
                {
                    //if checksums dont match, copy is corrupted, delete and alert user
                    out.delete();
                    System.out.println("MOVE FAILED - MD5 CHECKSUM FAIL!");
                    //  progress.setText(out.getName() + " failed corruption test....");
                }
            } else
            {
                System.out.println("MOVE FAILED - OUTPUT DOES NOT EXIST");
            }
        }

    }

    public static long folderSize(File directory)
    {
        long length = 0;
        for (File file : directory.listFiles())
        {
            if (file.isFile())
            {
                length += file.length();
            } else
            {
                length += folderSize(file);
            }
        }
        return length;
    }

    /**
     * given a file checks if the folder containing has an identical name, if
     * yes and folder is under 600kb, then the folder is just a container often
     * used by torrents, ie tv show episodes distributed in folder, this method
     * will then delete the folder cleaning up.
     *
     * @param file
     */
    public static void deleteFileAndIdenticalNamedParentFolder(File file)
    {
        String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
        String folderName = file.getParentFile().getName();

        File directory = file.getParentFile();
        int length = 0;
        System.out.println(fileName);

        System.out.println(folderName);

        if (fileName.equals(folderName))
        {
            System.out.println("Parent folder Matches file........performing waste file cleanup.....");
            System.out.println((folderSize(directory) / 1024 / 1024));
            file.delete();
            if ((folderSize(directory) / 1024 / 1024) <= 600)  //600kb
            {
                System.out.println("Parent folder under 600kb.....deleting parent folder");
                try
                {
                    FileUtils.deleteDirectory(directory);
                } catch (IOException ex)
                {
                    System.out.println("FAILED TO DELETE PARENT FOLDER: " + directory);
                }
            }

        } else
        {
            //System.out.println("No Match");
            file.delete();
        }

        //  file.delete();
    }

    /**
     * given a file list and text to search with, this method will return a list
     * of file paths where the file contains that text, this method ignores
     * case, ie A == a so Text == text
     *
     * providing a blank text string will make this method return all
     *
     *
     * @param text - text to search for
     * @param fileList - file list to search through
     *
     *
     */
    public static void getAllFilesContaining(String text, List fileList)
    {
        if (!(text.equals(""))) //no search needed if text is empty, skip to else and just return given list back
        {
            // List found = new List();

            for (int i = 0; i < fileList.getItemCount(); i++)
            {

                if (new File(fileList.getItem(i)).isFile())
                {

                    if (!(new File(fileList.getItem(i)).getName().toLowerCase().endsWith(".!qb")) & !(new File(fileList.getItem(i)).getName().toLowerCase().endsWith(".!ut")) & !(new File(fileList.getItem(i)).getName().toLowerCase().endsWith(".!bt")) & !(new File(fileList.getItem(i)).getName().toLowerCase().endsWith(".az!"))) //supports utorrent, qbittorrent, vuze and bittorrent
                    {
                        String[] words = text.split("\\s+");
                        boolean match = true;
                        testLoop:
                        for (int j = 0; j < words.length; j++)
                        {
                            if (!(new File(fileList.getItem(i)).getName().toLowerCase().contains(words[j].toLowerCase())))
                            {
                                match = false;
                                break testLoop;

                            }
                        }

                        if (match == true)
                        {
                            System.out.println("MATCH FOUND: " + fileList.getItem(i));
                            filesToMove.add(fileList.getItem(i));
                        }
                    }
                }
            }

            // return found;
        }
    }

    /**
     * given a directory to search this method will add all folders and sub
     * folders as well as files to the list tree
     *
     * @param dir
     * @return
     * @throws NullPointerException
     *
     */
    public static List getFolderTree(File dir) throws NullPointerException
    {

        File listFile[] = dir.listFiles();
        if (listFile != null)
        {
            for (int i = 0; i < listFile.length; i++)
            {
                if (listFile[i].isDirectory())
                {

                    tree.add(listFile[i].getPath()); //add folder to tree
                    getFolderTree(listFile[i]); //enter folder and find files and sub folders

                } else
                {
                    //dont include known torrent file extensions that indicate a file is still being downloaded               

                    tree.add(listFile[i].getPath()); //add file to tree

                }
            }
        }

        return tree;
    }

    /**
     * Given a file to save to, this method saves the list tree to that file
     *
     * @param saveFile
     * @throws IOException
     */
    public static void saveTree(File saveFile) throws IOException
    {
        try
        {
            if (!(saveFile.exists()))
            {
                saveFile.createNewFile();
            }

            PrintWriter out = new PrintWriter(saveFile);

            for (int i = 0; i < tree.getItemCount(); i++)
            {
                out.println(tree.getItem(i));
            }

            out.close();

        } catch (FileNotFoundException ex)
        {
            System.out.println("ERROR SAVING TREE....SAVE FILE NOT FOUND");
        }
    }
}
