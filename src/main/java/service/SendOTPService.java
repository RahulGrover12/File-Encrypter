// https://github.com/RahulGrover12/
// Rahul Grover
package service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendOTPService {
    public static void sendOTP(String email, String genOTP) {
        String to = email;

        String from = "xxxxxx@gmail.com";

        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "APP_PASSWORD");
            }
        });

        session.setDebug(false);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("File Encrypter OTP");
            message.setText("Hi,\n" + "Use the One Time Password (OTP) " +
                    "to log in to your File Encrypter account: " + genOTP);
            System.out.println("Sending OTP, Please Wait...");

            Transport.send(message);
            System.out.println("OTP Sent successfully....");
            System.out.println();
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
