/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Halen3.EmailNotifier;

import Halen3.IO.FileManager;
import Halen3.IO.GlobalSharedVariables;
import static Halen3.IO.GlobalSharedVariables.emails;
import com.google.common.io.Resources;
import java.awt.List;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.io.Charsets;

/**
 *
 * @author brenn
 */
public class SendEmailNotification
{

    private static Properties mailServerProperties;
    private static Session getMailSession;
    private static MimeMessage generateMailMessage;

    public static List retrievedTVShows = new List();

    public static List retrievedFilms = new List();

    public static List retrievedAnime = new List();

    public static List retrievedComics = new List();

    //   private static String[] emails =
    //  {
    //      "brennan92F@gmail.com"
    // };//, "mariebrennan60@gmail.com"};
    private static String readHTLM(String htmlFileName)
    {
//        try (FileInputStream inputStream = new FileInputStream("Halen3/EmailNotifier/html/"+ htmlFileName))
//        {
//
//            String everything = IOUtils.toString(inputStream);
//            return everything;
//        } catch (IOException e)
//        {
//            System.out.println(e);
//            return "";
//        }

        URL url = Resources.getResource("Halen3/EmailNotifier/html/" + htmlFileName);
        String text = "";
        try
        {
            text = Resources.toString(url, Charsets.UTF_8);
        } catch (IOException ex)
        {
            Logger.getLogger(SendEmailNotification.class.getName()).log(Level.SEVERE, null, ex);
            return text;
        }
        return text;
    }

    public static void main(String args[]) throws IOException, URISyntaxException
    {

        retrievedTVShows.add("<search>Marvel's Agents of S.H.I.E.L.D. ettv</search><url>https://trakt.tv/shows/marvel-s-agents-of-s-h-i-e-l-d</url><posterURL>https://walter.trakt.us/images/shows/000/001/394/posters/thumb/c4a4211e12.jpg</posterURL><genres>Science Fiction, Fantasy, Drama, Adventure, Action, </genres><plot>Phil Coulson (Clark Gregg, reprising his role from \"The Avengers\" and \"Iron Man\" ) heads an elite team of fellow agents with the worldwide law-enforcement organization known as SHIELD (Strategic Homeland Intervention Enforcement and Logistics Division), as they investigate strange occurrences around the globe. Its members -- each of whom brings a specialty to the group -- work with Coulson to protect those who cannot protect themselves from extraordinary and inconceivable threats, including a formidable group known as Hydra.</plot><image>C:\\Users\\brenn\\Documents\\NetBeansProjects\\Halen\\build\\graphics\\tv-show-logos\\marvels agents of shield.png</image><searchInFolder>C:/Users/taibhse/Downloads</searchInFolder><moveToFolder>C:\\Users\\taibhse\\media\\tv\\agents of S.H.I.E.L.D</moveToFolder><searchFor>agents of s.h.i.e.l.d</searchFor><retEps>S01E01-!SPLIT!-S01E02-!SPLIT!-</retEps>");
        retrievedComics.add("<title>Platina End</title><url>http://kissmanga.com/Manga/Platina-End</url><imageURL>C:\\Users\\brenn\\Documents\\NetBeansProjects\\Halen\\build\\graphics\\comic-book-covers\\Platina End.png</imageURL><imgurURL>https://i.imgur.com/RD76Mnk.png</imgurURL><genres>Shounen Supernatural</genres><publisher>null</publisher><author>Ohba Tsugumi OBATA Takeshi</author><writer>null</writer><artist>null</artist><publicationDate>null</publicationDate><status>Ongoing</status><summary>A story of a human and an angel.</summary><downloadTo>C:\\Users\\brenn\\Documents\\NetBeansProjects\\Halen\\test</downloadTo><moveToFolder>relocate</moveToFolder><retIssues>Platina End Ch.001: The Angel's Gift-!SPLIT!-Platina End Ch.000: Special preview-!SPLIT!-</retIssues>");
        retrievedAnime.add("<title>91 Days</title><genre>Drama</genre><plot>Anime Synopsis: During Prohibition, the law held no power and the mafia ruled the town. The story takes place in Lawless, a town thriving on black market sales of illicitly brewed liquor. Avilio returns to Lawless after some time away, following the murder of his family in a mafia dispute there. One day, Avilio receives a letter from a mysterious sender, prompting him to return to Lawless for revenge. He then infiltrates the Vanetti family, the ones responsible for his family's murder, and sets about befriending the don's son, Nero, to set his vengeance in motion. Killing brings more killing, and revenge spawns more revenge. How will the 91-day story of these men guided by a tragic fate end?</plot><imageURL>http://deadfishencod.es/files/anime/series/cover/91_days.jpg</imageURL><image>C:\\Users\\brenn\\Documents\\NetBeansProjects\\Halen\\build\\graphics\\anime-posters\\91 Days.jpg</image><director>Director: Kaburaki Hiro</director><publisher>Publisher(s): Shochiku</publisher><rated>Rated: R - 17+ (violence & profanity)</rated><duration>Duration: 24 Mins Per Episode</duration><status>Status: Ongoing</status><seriesURL>http://deadfishencod.es/91_Days/</seriesURL><moveToFolder>c://test</moveToFolder><retEps>Planetarian: Chiisana Hoshi no Yume - 01 - ONA [720p][AAC].mp4-!SPLIT!-Platina End Ch.000: Special preview-!SPLIT!-</retEps>");
        retrievedFilms.add("<search>Logan 2017</search><url>https://trakt.tv/movies/logan-2017</url><posterURL>https://walter.trakt.tv/images/movies/000/161/972/posters/thumb/829ce56968.jpg</posterURL><genres>Drama, Science Fiction, Action, </genres><plot>In the near future, a weary Logan cares for an ailing Professor X in a hide out on the Mexican border. But Logan's attempts to hide from the world and his legacy are up-ended when a young mutant arrives, being pursued by dark forces.</plot><director>Director James Mangold</director><released>Released March 3, 2017</released><language>Language English</language><runtime>Runtime 141 mins</runtime><image>C:\\Users\\TAIBHSE\\Documents\\NetBeansProjects\\Halen\\build\\graphics\\film-logos\\logan.png</image><retrieved>false</retrieved>");

//        PrintWriter out = new PrintWriter("C:\\Users\\brenn\\Documents\\NetBeansProjects\\Halen\\preview.html");
//        out.println(createUpdateListMessage());
//        out.close();
////  System.out.println(createUpdateListMessage());
//     
        GlobalSharedVariables.email = "brennan92F@gmail.com";

        sendEmailNotice(createUpdateListMessage());
    }
//    public static void test() throws AddressException, MessagingException, IOException
//    {
//        //sendEmailNotice();
//        //generateAndSendEmail("","","");
//        //System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
//        PrintWriter out = new PrintWriter("C:\\Users\\brenn\\Documents\\NetBeansProjects\\Halen\\preview.html");
//        out.println(createUpdateListMessage());
//        out.close();
//    }

    public static String error(String errorNotice)
    {
        return "<div style=\"white-space:wrap;background:#000;\">\n"
                + "    <center>\n"
                + "        <br>\n"
                + "        <img src=\"https://i.imgur.com/GmIHkPv.gif\" width=\"300px\" />\n"
                + "        <h1 style=\"color:#0067e0; font-family:tahoma;\">" + errorNotice + "</h1> </center>\n"
                + "   \n"
                + "  \n"
                + "<br><br><br><br></div>";
    }

    public static String createUpdateListMessage() throws IOException
    {
        String message = "";
//        if (retrievedTVShows.getItemCount() > 0 || retrievedAnime.getItemCount() > 0 || retrievedComics.getItemCount() > 0)
//        {
//
//            message = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%;height: 100%\"> <tbody bgcolor=\"#231f20\"> <tr valign=\"top\"> <td> <center> <h1 style=\"color:#0067e0; font-family:tahoma;\"><br>HALEN SERVER UPDATE</h1> </center> <center> <h2 style=\"color:#2072d1; font-family:tahoma;\"><!DATE!></h2> </center> <hr style=\"border-top: 1px solid #0067e0; border-bottom: 1px solid #0067e0; margin-left:30px; margin-right:30px\" /> <center> <p style=\"color:#fff; font-family:tahoma;\">Here is a list of media retrieved by Halen on <!DATE!></p> </center> <hr style=\"border-top: 1px solid #0067e0; border-bottom: 1px solid #0067e0; margin-left:30px; margin-right:30px\" /> <br>";
//            message = message.replaceAll("<!DATE!>", FileManager.getCurrentDate());
//
//            if (retrievedTVShows.getItemCount() > 0)
//            {
//                message = message + "<center> <h2 style=\"color:#fff; display: inline-block; font-family:tahoma;\"> <img src=\"https://i.imgur.com/wi7eJzd.png\" style=\"width:200px; vertical-align: middle;\" /> TV SHOWS</h2> </center>";
//
//                for (int i = 0; i < retrievedTVShows.getItemCount(); i++)
//                {
//                    message = message + "<center><h3 style=\"color:#2072d1; font-family:tahoma;\">" + retrievedTVShows.getItem(i) + "</h3></center>";
//                }
//            }
//
//            if (retrievedComics.getItemCount() > 0)
//            {
//                message = message + " <center> <h2 style=\"color:#fff; display: inline-block; font-family:tahoma;\"> <img src=\"https://i.imgur.com/cRZyR3I.png\" style=\"width:200px; vertical-align: middle;\" /> COMICS</h2> </center>";
//
//                for (int i = 0; i < retrievedComics.getItemCount(); i++)
//                {
//                    message = message + "<center><h3 style=\"color:#2072d1; font-family:tahoma;\">" + retrievedComics.getItem(i) + "</h3></center>";
//                }
//            }
//
//            if (retrievedAnime.getItemCount() > 0)
//            {
//                message = message + "<center> <h2 style=\"color:#fff; display: inline-block; font-family:tahoma;\"> <img src=\"https://i.imgur.com/gGnNHoT.png\" style=\"width:200px; vertical-align: middle;\" /> ANIME</h2> </center>";
//
//                for (int i = 0; i < retrievedAnime.getItemCount(); i++)
//                {
//                    message = message + "<center><h3 style=\"color:#2072d1; font-family:tahoma;\">" + retrievedAnime.getItem(i) + "</h3></center>";
//                }
//            }
//
//            message = message + " <br><br><br></td> </tr> </tbody></table>";

        //   }
//     
//       File[] files = new File("C:\\Users\\brenn\\Documents\\NetBeansProjects\\Halen\\html").listFiles();
//        for (File file : files)
//        {
//            //update date in file
//            if(file.getName().contains("01_title"))
//            {
//                 message = message + new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
//                 
//                 message = message.replaceAll("-!DATE!-", new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss").format(Calendar.getInstance().getTime()));
//            }else{
//                
//                //add tv shows if any found
//                if(file.getName().contains("02_tv_icon") && retrievedTVShows.getItemCount() > 0)
//                {
//                      message = message + new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
//              
//                }else
//                {
//            //String t_text = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
//          message = message + new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
//                }
//          
//            }
//        }
        //  String path = "C:\\Users\\brenn\\Documents\\NetBeansProjects\\Halen\\html\\";
        //File file = new File(readHTLM("01_title.html"));
        if (retrievedTVShows.getItemCount() > 0 || retrievedComics.getItemCount() > 0 || retrievedAnime.getItemCount() > 0 || retrievedFilms.getItemCount() > 0)
        {
            String title = readHTLM("01_title.html").replaceAll("-!DATE!-", new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss").format(Calendar.getInstance().getTime()));
            // message = message + new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            message = "<div style=\"white-space:wrap;background:#000;\">" + title;

            if (retrievedTVShows.getItemCount() > 0)
            {
                //  file = new File(readHTLM("02_tv_icon.html"));
                message = message + readHTLM("02_tv_icon.html") + "<div style=\"width: 100%;\"><center>";

                for (int i = 0; i < retrievedTVShows.getItemCount(); i++)
                {

                    if (FileManager.returnTag("retEps", retrievedTVShows.getItem(i)).trim().length() > 0)
                    {
                        System.out.println("\n\n" + retrievedTVShows.getItem(i) + "\n\n");
                        //file = new File(readHTLM("03b_tv_show_data.html"));
                        String show = readHTLM("03b_tv_show_data.html");
                        show = show.replace("-!url!-", FileManager.returnTag("posterURL", retrievedTVShows.getItem(i)));
                        show = show.replace("-!title!-", FileManager.returnTag("url", retrievedTVShows.getItem(i)).substring(FileManager.returnTag("url", retrievedTVShows.getItem(i)).lastIndexOf("/") + 1, FileManager.returnTag("url", retrievedTVShows.getItem(i)).length()));
                        show = show.replace("-!genres!-", FileManager.returnTag("genres", retrievedTVShows.getItem(i)));
                        show = show.replace("-!plot!-", FileManager.returnTag("plot", retrievedTVShows.getItem(i)));

                        show = show.replace("-!eps!-", FileManager.returnTag("retEps", retrievedTVShows.getItem(i)));

                        message = message + show;

                    }
                }

                message = message + "</center></div>";

            }

            if (retrievedFilms.getItemCount() > 0)
            {
                //  file = new File(readHTLM("02_tv_icon.html"));
                message = message + readHTLM("09_film_icon.html") + "<div style=\"width: 100%;\"><center>";

                for (int i = 0; i < retrievedFilms.getItemCount(); i++)
                {

                    System.out.println("\n\n" + retrievedFilms.getItem(i) + "\n\n");
                    //file = new File(readHTLM("03b_tv_show_data.html"));
                    String show = readHTLM("09b_film_data.html");
                    show = show.replace("-!url!-", FileManager.returnTag("posterURL", retrievedFilms.getItem(i)));
                    show = show.replace("-!title!-", FileManager.returnTag("url", retrievedFilms.getItem(i)).substring(FileManager.returnTag("url", retrievedFilms.getItem(i)).lastIndexOf("/") + 1, FileManager.returnTag("url", retrievedFilms.getItem(i)).length()));
                    show = show.replace("-!genres!-", FileManager.returnTag("genres", retrievedFilms.getItem(i)));
                    show = show.replace("-!plot!-", FileManager.returnTag("plot", retrievedFilms.getItem(i)));
                    show = show.replace("-!runtime!-", FileManager.returnTag("runtime", retrievedFilms.getItem(i)));
                    show = show.replace("-!language!-", FileManager.returnTag("language", retrievedFilms.getItem(i)));
                    show = show.replace("-!director!-", FileManager.returnTag("director", retrievedFilms.getItem(i)));
                    show = show.replace("-!released!-", FileManager.returnTag("released", retrievedFilms.getItem(i)));

                    message = message + show;

                }

                message = message + "</center></div>";

            }

            if (retrievedComics.getItemCount() > 0)
            {
                //  file = new File(readHTLM("04_comic_icon.html"));
                message = message + readHTLM("04_comic_icon.html") + "<div style=\"width: 100%;\"><center>";

                for (int i = 0; i < retrievedComics.getItemCount(); i++)
                {

                    if (FileManager.returnTag("retIssues", retrievedComics.getItem(i)).trim().length() > 0)
                    {
                        System.out.println("\n\n" + retrievedComics.getItem(i) + "\n\n");
                        //   file = new File(readHTLM("05b_comic_data.html"));
                        String show = readHTLM("05b_comic_data.html");
                        show = show.replace("-!url!-", FileManager.returnTag("imgurURL", retrievedComics.getItem(i)));
                        show = show.replace("-!title!-", FileManager.returnTag("title", retrievedComics.getItem(i)));
                        show = show.replace("-!genres!-", FileManager.returnTag("genres", retrievedComics.getItem(i)));
                        show = show.replace("-!plot!-", FileManager.returnTag("summary", retrievedComics.getItem(i)));

                        //create stats line
                        String stats = "";
                        if (!FileManager.returnTag("publisher", retrievedComics.getItem(i)).equals("null"))
                        {
                            stats = stats + "Publisher: " + FileManager.returnTag("publisher", retrievedComics.getItem(i)) + "<br>";
                        }

                        if (!FileManager.returnTag("writer", retrievedComics.getItem(i)).equals("null"))
                        {
                            stats = stats + "Writer: " + FileManager.returnTag("writer", retrievedComics.getItem(i)) + "<br>";
                        }

                        if (!FileManager.returnTag("artist", retrievedComics.getItem(i)).equals("null"))
                        {
                            stats = stats + "Artist: " + FileManager.returnTag("artist", retrievedComics.getItem(i)) + "<br>";
                        }

                        if (!FileManager.returnTag("publicationDate", retrievedComics.getItem(i)).equals("null"))
                        {
                            stats = stats + "Publication Date: " + FileManager.returnTag("publicationDate", retrievedComics.getItem(i)) + "<br>";
                        }

                        if (!FileManager.returnTag("author", retrievedComics.getItem(i)).equals("null"))
                        {
                            stats = stats + "Author: " + FileManager.returnTag("author", retrievedComics.getItem(i)) + "<br>";
                        }
                        if (!FileManager.returnTag("status", retrievedComics.getItem(i)).equals("null"))
                        {
                            stats = stats + "Status: " + FileManager.returnTag("status", retrievedComics.getItem(i)) + "<br>";
                        }
                        show = show.replace("-!stats!-", stats);

                        String issues[] = FileManager.returnTag("retIssues", retrievedComics.getItem(i)).split("-!SPLIT!-");
                        String list = "";
                        for (int j = 0; j < issues.length; j++)
                        {
                            list = list + "<font style=\"color:#fff; font-family:tahoma;\">&bull;" + issues[j] + "</font><br>";
                        }
                        show = show.replace("-!retIssues!-", list);

                        message = message + show;
                    }

                }

                message = message + "</center></div>";
            }

            if (retrievedAnime.getItemCount() > 0)
            {
                //   file = new File(readHTLM("06_anime_icon.html"));
                message = message + readHTLM("06_anime_icon.html") + "<div style=\"width: 100%;\"><center>";

                for (int i = 0; i < retrievedAnime.getItemCount(); i++)
                {

                    if (FileManager.returnTag("retEps", retrievedAnime.getItem(i)).trim().length() > 0)
                    {
                        System.out.println("\n\n" + retrievedAnime.getItem(i) + "\n\n");
                        // file = new File(readHTLM("07b_anime_data.html"));
                        String show = readHTLM("07b_anime_data.html");
                        show = show.replace("-!url!-", FileManager.returnTag("imageURL", retrievedAnime.getItem(i)));
                        show = show.replace("-!title!-", FileManager.returnTag("title", retrievedAnime.getItem(i)));
                        show = show.replace("-!genres!-", FileManager.returnTag("genres", retrievedAnime.getItem(i)));
                        show = show.replace("-!plot!-", FileManager.returnTag("plot", retrievedAnime.getItem(i)).replace("Anime Synopsis: ", ""));

                        //create stats line
                        String stats = "";
                        if (!FileManager.returnTag("director", retrievedAnime.getItem(i)).equals("null"))
                        {
                            stats = stats + FileManager.returnTag("director", retrievedAnime.getItem(i)) + "<br>";
                        }

                        if (!FileManager.returnTag("publisher", retrievedAnime.getItem(i)).equals("null"))
                        {
                            stats = stats + FileManager.returnTag("publisher", retrievedAnime.getItem(i)) + "<br>";
                        }

                        if (!FileManager.returnTag("rated", retrievedAnime.getItem(i)).equals("null"))
                        {
                            stats = stats + FileManager.returnTag("rated", retrievedAnime.getItem(i)) + "<br>";
                        }

                        if (!FileManager.returnTag("duration", retrievedAnime.getItem(i)).equals("null"))
                        {
                            stats = stats + FileManager.returnTag("duration", retrievedAnime.getItem(i)) + "<br>";
                        }

                        if (!FileManager.returnTag("status", retrievedAnime.getItem(i)).equals("null"))
                        {
                            stats = stats + FileManager.returnTag("status", retrievedAnime.getItem(i)) + "<br>";
                        }
                        if (!FileManager.returnTag("status", retrievedAnime.getItem(i)).equals("null"))
                        {
                            stats = stats + "Status: " + FileManager.returnTag("status", retrievedAnime.getItem(i)) + "<br>";
                        }
                        show = show.replace("-!stats!-", stats);

                        String eps[] = FileManager.returnTag("retEps", retrievedAnime.getItem(i)).split("-!SPLIT!-");
                        String list = "";
                        for (int j = 0; j < eps.length; j++)
                        {
                            list = list + "<font style=\"color:#fff; font-family:tahoma;\">&bull;" + eps[j] + "</font><br>";
                        }
                        show = show.replace("-!retEps!-", list);

                        message = message + show;
                    }

                }

                message = message + "</center></div>";
            }

            message = message + "<br><br>";

            //######################################################################
            List images = new List();

            File tvFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/tv show/");
            final File[] tvList = tvFolder.listFiles(new FilenameFilter()
            {
                @Override
                public boolean accept(File dir, String name)
                {
                    return name.toLowerCase().endsWith(".xml");
                }
            });
            try
            {

                message = message + "<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>" + readHTLM("08_media_icon.html") + "<br>";

                message = message + "<center><div style=\"display: inline-block;\">";
                for (int i = 0; i < tvList.length; i++)
                {
                    // images.add(FileManager.returnTag("posterURL", FileManager.readFile(tvList[i].getPath()).getItem(0)));

                    String imageURL = FileManager.returnTag("posterURL", FileManager.readFile(tvList[i].getPath()).getItem(0));
                    String seriesURL = FileManager.returnTag("url", FileManager.readFile(tvList[i].getPath()).getItem(0));
                    message = message + "<div style=\"width: 20%; display:inline-block;\n"
                            + "  padding-bottom: 1%;\n"
                            + "  margin: 1%;\"> <a href=\"" + seriesURL + "\" target=\"blank\"><img src=\"" + imageURL + "\" width=\"100%\" /></a> </div>";
                }

               

                File animeFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/anime/");
                File[] animeList = animeFolder.listFiles(new FilenameFilter()
                {
                    @Override
                    public boolean accept(File dir, String name)
                    {
                        return name.toLowerCase().endsWith(".xml");
                    }
                });
 
                message = message + "<br>";
                
                for (int i = 0; i < animeList.length; i++)
                {
                    // images.add(FileManager.returnTag("imageURL", FileManager.readFile(animeList[i].getPath()).getItem(0)));

                    String imageURL = FileManager.returnTag("imgurURL", FileManager.readFile(animeList[i].getPath()).getItem(0));
                    String seriesURL = FileManager.returnTag("seriesURL", FileManager.readFile(animeList[i].getPath()).getItem(0));
                    message = message + "<div style=\"width: 20%; display:inline-block;\n"
                            + "  padding-bottom: 1%;\n"
                            + "  margin: 1%;\"> <a href=\"" + seriesURL + "\" target=\"blank\"><img src=\"" + imageURL + "\" width=\"100%\" /></a> </div>";

                }

               
                
                File filmFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/films/");
                File[] filmList = filmFolder.listFiles(new FilenameFilter()
                {
                    @Override
                    public boolean accept(File dir, String name)
                    {
                        return name.toLowerCase().endsWith(".xml");
                    }
                });

                 message = message + "<br>";
                 
                for (int i = 0; i < filmList.length; i++)
                {
                    // images.add(FileManager.returnTag("imageURL", FileManager.readFile(animeList[i].getPath()).getItem(0)));

                    String imageURL = FileManager.returnTag("posterURL", FileManager.readFile(filmList[i].getPath()).getItem(0));
                    String filmURL = FileManager.returnTag("url", FileManager.readFile(filmList[i].getPath()).getItem(0));
                    message = message + "<div style=\"width: 20%; display:inline-block;\n"
                            + "  padding-bottom: 1%;\n"
                            + "  margin: 1%;\"> <a href=\"" + filmURL + "\" target=\"blank\"><img src=\"" + imageURL + "\" width=\"100%\" /></a> </div>";

                }
                
                File comicsFolder = new File(Halen3.IO.FileManager.launchPath() + "/rules/comics/");
                File[] comicsList = comicsFolder.listFiles(new FilenameFilter()
                {
                    @Override
                    public boolean accept(File dir, String name)
                    {
                        return name.toLowerCase().endsWith(".xml");
                    }
                });

                message = message + "<br>";

                for (int i = 0; i < comicsList.length; i++)
                {
                    // images.add(FileManager.returnTag("imgurURL", FileManager.readFile(comicsList[i].getPath()).getItem(0)));

                    String imageURL = FileManager.returnTag("imgurURL", FileManager.readFile(comicsList[i].getPath()).getItem(0));
                    String seriesURL = FileManager.returnTag("url", FileManager.readFile(comicsList[i].getPath()).getItem(0));
                    message = message + "<div style=\"width: 20%; display:inline-block;\n"
                            + "  padding-bottom: 1%;\n"
                            + "  margin: 1%;\"> <a href=\"" + seriesURL + "\" target=\"blank\"><img src=\"" + imageURL + "\" width=\"100%\" /></a> </div>";

                }

      //  file = new File(readHTLM("08_media_icon.html"));
//        message = message + "<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>" + readHTLM("08_media_icon.html") + "<br>";
//
//        //load posters to end of email to show all shows being downloaded
//        message = message + "<center><div style=\"display: inline-block;\">";
//        for (int i = 0; i < images.getItemCount(); i++)
//        {
//            //System.out.println(images.getItem(i));
//
//            message = message + "<div style=\"width: 20%; display:inline-block;\n"
//                    + "  padding-bottom: 1%;\n"
//                    + "  margin: 1%;\"> <img src=\"" + images.getItem(i) + "\" width=\"100%\" /> </div>";
//
//        }
                message = message + "</div></center><br><br>";

            } catch (NullPointerException e)
            {

            }

            message = message + "<img src=\"https://i.imgur.com/tnoxBhK.png\" width=\"100%\" height=\"100%\" alt=\"\" /></div>";

        }
        return message;
    }

    public static void sendEmailNotice(String message) throws IOException
    {

        //   String message = createMessage();
        //     String path = "C:\\Users\\brenn\\Documents\\NetBeansProjects\\Halen\\";
        //   File file = new File(path + "preview.html");
        //   message = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        // String message = "";
        if (!message.equals(""))
        {
            String subject = "Halen Server Report - " + new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss").format(Calendar.getInstance().getTime());
            for (int i = 0; i < emails.length; i++)
            {
                try
                {
                    generateAndSendEmail(subject, message, emails[i]);

                } catch (MessagingException ex)
                {
                    Logger.getLogger(SendEmailNotification.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    public static void generateAndSendEmail(String subject, String message, String recipient) throws AddressException, MessagingException
    {

        // Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        System.out.println("Mail Server Properties have been setup successfully..");

        // Step2
        System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        //generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
        generateMailMessage.setSubject(subject);
        //String emailBody = "Test email by Crunchify.com JavaMail API example. " + "<br><br> Regards, <br>Crunchify Admin";
        generateMailMessage.setContent(message, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Step3
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "halen.update@gmail.com", "3yEzHWyBFs9Ixza");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
