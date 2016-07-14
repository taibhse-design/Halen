/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emailer;

import halen.FileManager;
import java.awt.List;
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

/**
 *
 * @author brenn
 */
public class SendEmail
{
    
    private static Properties mailServerProperties;
	private static Session getMailSession;
	private static MimeMessage generateMailMessage;
        
        public static List retrievedEpisodes = new List();
        
        public static List retrievedAnime = new List();
        
        public static List retrievedcomics = new List();
        
        private static String[] emails = {"brennan92F@gmail.com", "mariebrennan60@gmail.com"};
        public static void main(String args[]) throws AddressException, MessagingException 
        {
		//generateAndSendEmail("","","");
		//System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
	
        System.out.println(createMessage());
        }
        
        public static String createMessage()
        {
            String message = "";
             if(retrievedEpisodes.getItemCount() > 0 || retrievedAnime.getItemCount() > 0 || retrievedcomics.getItemCount() > 0)
            {
                
                 message = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%;height: 100%\"> <tbody bgcolor=\"#231f20\"> <tr valign=\"top\"> <td> <center> <h1 style=\"color:#0067e0; font-family:tahoma;\"><br>HALEN SERVER UPDATE</h1> </center> <center> <h2 style=\"color:#2072d1; font-family:tahoma;\"><!DATE!></h2> </center> <hr style=\"border-top: 1px solid #0067e0; border-bottom: 1px solid #0067e0; margin-left:30px; margin-right:30px\" /> <center> <p style=\"color:#fff; font-family:tahoma;\">Here is a list of media retrieved by Halen on <!DATE!></p> </center> <hr style=\"border-top: 1px solid #0067e0; border-bottom: 1px solid #0067e0; margin-left:30px; margin-right:30px\" /> <br>";
                 message = message.replaceAll("<!DATE!>", FileManager.getCurrentDate());
                 
                 if(retrievedEpisodes.getItemCount() > 0)
                 {
                     message = message + "<center> <h2 style=\"color:#fff; display: inline-block; font-family:tahoma;\"> <img src=\"https://i.imgur.com/wi7eJzd.png\" style=\"width:200px; vertical-align: middle;\" /> TV SHOWS</h2> </center>";
                  
                     for(int i = 0; i < retrievedEpisodes.getItemCount(); i++)
                     {
                         message = message + "<center><h3 style=\"color:#2072d1; font-family:tahoma;\">" + retrievedEpisodes.getItem(i) + "</h3></center>";
                     }
                 }
                 
                  if(retrievedcomics.getItemCount() > 0)
                 {
                     message = message + " <center> <h2 style=\"color:#fff; display: inline-block; font-family:tahoma;\"> <img src=\"https://i.imgur.com/cRZyR3I.png\" style=\"width:200px; vertical-align: middle;\" /> COMICS</h2> </center>";
                  
                     for(int i = 0; i < retrievedcomics.getItemCount(); i++)
                     {
                         message = message + "<center><h3 style=\"color:#2072d1; font-family:tahoma;\">" + retrievedcomics.getItem(i) + "</h3></center>";
                     }
                 }
                  
                   if(retrievedAnime.getItemCount() > 0)
                 {
                     message = message + "<center> <h2 style=\"color:#fff; display: inline-block; font-family:tahoma;\"> <img src=\"https://i.imgur.com/gGnNHoT.png\" style=\"width:200px; vertical-align: middle;\" /> ANIME</h2> </center>";
                  
                     for(int i = 0; i < retrievedAnime.getItemCount(); i++)
                     {
                         message = message + "<center><h3 style=\"color:#2072d1; font-family:tahoma;\">" + retrievedAnime.getItem(i) + "</h3></center>";
                     }
                 }
                   
                   message = message + " <br><br><br></td> </tr> </tbody></table>";
                 
             
            }
             
             return message;
        }
        
        public static void sendEmailNotice()
        {
           
            String message = createMessage();
            
            if(!message.equals(""))
            {
                String subject = "Halen Server Update - " + FileManager.getCurrentDate();
              for(int i = 0; i < emails.length; i++)
                 {
                    try
                    {
                        generateAndSendEmail( subject,  message, emails[i]);
                    } catch (MessagingException ex)
                    {
                        Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }
              
            }
            
        }
    public static void generateAndSendEmail(String subject, String message, String recipient) throws AddressException, MessagingException {
 
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
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
