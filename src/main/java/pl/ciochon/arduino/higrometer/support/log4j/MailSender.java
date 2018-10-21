package pl.ciochon.arduino.higrometer.support.log4j;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by Konrad Ciochoń on 2018-10-21.
 */
public class MailSender {

    private static Properties mailServerProperties;

    private static String PASSWORD = System.getProperty("mail.password");

    private static String MAIL_ADDRESS = System.getProperty("mail.address");

    private static String USER = System.getProperty("mail.user");

    static {
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
    }

    public static void generateAndSendEmail(Collection<String> fileNames) throws MessagingException {

        Session session = Session.getDefaultInstance(mailServerProperties, null);

        Message message = prepareMessage(session, fileNames);

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", USER, PASSWORD);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    private static Message prepareMessage(Session session, Collection<String> fileNames) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(MAIL_ADDRESS));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(MAIL_ADDRESS));
        message.setSubject(fileNames.stream().collect(Collectors.joining(",")));

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Newest logs");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        addAttachments(multipart, fileNames);
        message.setContent(multipart);

        return message;
    }

    private static void addAttachments(Multipart multipart, Collection<String> fileNames) throws MessagingException {
        for (String fileName : fileNames) {
            BodyPart messageBodyPart;
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(fileName);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
        }
    }


}
