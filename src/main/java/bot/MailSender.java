package bot;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import static bot.Keys.*;

public class MailSender {


    public static void sendMail(String receiver, Character playersOnServer) {

        try {
            Email email = new SimpleEmail();

            // Configuration
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(CMS_MAIL,
                    PASSWORD_MAIL));

            // Required for gmail
            email.setSSLOnConnect(true);

            // Sender
            email.setFrom(CMS_MAIL);

            // Email title
            email.setSubject("SERVER IS READY!");

            // Email message.
            email.setMsg("Server meets the requirements to count statistics. PLAY NOW!\n" +
                    "Players on server: " + playersOnServer);

            // Receivers
            email.addTo(MAIL_RECEIVER);

            email.send();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
