package util;
import java.util.*;

// import javax.activation.DataSource;
// import javax.mail.Message;
// import javax.mail.MessagingException;
// import javax.mail.PasswordAuthentication;
// import javax.mail.Session;
// import javax.mail.Transport;
// import javax.mail.internet.InternetAddress;
// import javax.mail.internet.MimeMessage;
// import javax.mail.Authenticator;

import jakarta.activation.DataSource;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Authenticator;

public class SendMail {
    // protected String hostname = "smtp-mail.outlook.com"; 
    protected String hostname = "outlook.office365.com";
    protected String username = "yangyimike@outlook.com"; 
    protected String password = "8b3ea0ce093993d50bf535627a156a737f5253611bf48c6f339e4a1984ab9882"; 
    protected Session session = null;
    public SendMail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", hostname);
        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "true");
        // props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.debug", "true");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        session = Session.getInstance(props, auth);
    }

    public int send_test () {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("yangyimike@outlook.com"));
            InternetAddress[] address = {new InternetAddress("18604713262@163.com")};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Jakarta Mail APIs Test");
            msg.addHeader("x-cloudmta-class", "standard");
            msg.addHeader("x-cloudmta-tags", "demo, example");
            msg.setText("Test Message Content, hahhaha!");

            Transport.send(msg);

            System.out.println("[ Send Mail ] Message Sent.");
        } catch (jakarta.mail.MessagingException ex) {
            System.out.println("[ Send Mail ] Send Email Failure.");
            throw new RuntimeException(ex);
        }
        return 1;
    }
}
