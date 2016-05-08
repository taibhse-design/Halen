package TorrentClient;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.Client.ClientState;
import com.turn.ttorrent.client.SharedTorrent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TAIBHSE
 */
public class TorrentClient
{

    static float prev = 0, now = 0, speed = 0;
    static int c = 0;
    public static void main(String args[]) throws IOException
    {
//   //     BasicConfigurator.configure();
//        // Get options
//        File output = new File("G:\\My Documents\\NetBeansProjects\\Halen\\torrentClient");
//
//
//        // Get the .torrent file path
//        File torrentPath = new File(
//                "G:\\My Documents\\NetBeansProjects\\Halen\\torrentClient\\file.torrent");
//
//        // Start downloading file
//        try {
//            SharedTorrent torrent = SharedTorrent.fromFile(torrentPath, output);
//            System.out.println("Starting client for torrent: "
//                    + torrent.getName());
//            Client client = new Client(InetAddress.getLocalHost(), torrent);
//
//            try {
//                System.out.println("Start to download: " + torrent.getName());
//                client.share(); // SEEDING for completion signal
//                // client.download() // DONE for completion signal
//
//                while (!ClientState.SEEDING.equals(client.getState())) {
//                    // Check if there's an error
//                    if (ClientState.ERROR.equals(client.getState())) {
//                        throw new Exception("ttorrent client Error State");
//                    }
//
//                    // Display statistics
//                    System.out
//                            .printf("%f %% - %d bytes downloaded - %d bytes uploaded\n",
//                                    torrent.getCompletion(),
//                                    torrent.getDownloaded(),
//                                    torrent.getUploaded());
//                    
//                    c+=1;
//                    if(c%2==0)
//                    {
//                        now = torrent.getDownloaded();
//                       
//                    }else
//                    {
//                        prev = torrent.getDownloaded();
//                    }
//                    
//                     speed = now - prev;
//                     
//                    System.out.println((Math.abs(speed)/1024) + " kbs");
//
//                    // Wait one second
//                    TimeUnit.SECONDS.sleep(1);
//                }
//
//                System.out.println("download completed.");
//            } catch (Exception e) {
//                System.err.println("An error occurs...");
//                e.printStackTrace(System.err);
//            } finally {
//                System.out.println("stop client.");
//                client.stop();
//                
//                System.exit(0);
//            }
//        } catch (Exception e) {
//            System.err.println("An error occurs...");
//            e.printStackTrace(System.err);
//        }
        
        // First, instantiate the Client object.
        final Client client = new Client(
                // This is the interface the client will listen on (you might need something
                // else than localhost here).
                InetAddress.getLocalHost(),
                // Load the torrent from the torrent file and use the given
                // output directory. Partials downloads are automatically recovered.
                SharedTorrent.fromFile(
                        new File("G:\\My Documents\\NetBeansProjects\\Halen\\torrentClient\\file.torrent"),
                        new File("G:\\My Documents\\NetBeansProjects\\Halen\\torrentClient")));

// You can optionally set download/upload rate limits
// in kB/second. Setting a limit to 0.0 disables rate
// limits.
        client.setMaxDownloadRate(0.0);
        client.setMaxUploadRate(0.0);

// At this point, can you either call download() to download the torrent and
// stop immediately after...
        client.download();

       // client.waitForCompletion();
        Thread t = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (true)
                {
                    try
                    {
                        Thread.sleep(1000);
                      
                           c+=1;
                    if(c%2==0)
                    {
                        now = client.getTorrent().getDownloaded();
                       
                    }else
                    {
                        prev = client.getTorrent().getDownloaded();
                    }
                    
                     speed = now - prev;
                     
                  

                    
                    
                        System.out.println(client.getTorrent().getCompletion() + "%        " + (Math.abs(speed)/1024) + " kbs" );
                    } catch (InterruptedException ex)
                    {
                        Logger.getLogger(TorrentClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        t.start();

// Or call client.share(...) with a seed time in seconds:
// client.share(3600);
// Which would seed the torrent for an hour after the download is complete.
// Downloading and seeding is done in background threads.
// To wait for this process to finish, call:
            client.waitForCompletion();
// At any time you can call client.stop() to interrupt the download.
    }
}
