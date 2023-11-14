package com.example.hoax.email;

import com.example.hoax.configuration.HoaxProperties;
import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    HoaxProperties hoaxProperties;

    public EmailService(HoaxProperties hoaxProperties) {
        this.hoaxProperties = hoaxProperties;
    }

    JavaMailSenderImpl javaMailSender;

    String activationEmail = """
                <html>
                <body>
                <h1>Activate Account</h1>
            <a href=${url} >Click Here</a>
                </body>
                </html>
                """;

    @PostConstruct
    public void initialize() {
        this.javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(hoaxProperties.getEmail().host());
        javaMailSender.setPort(hoaxProperties.getEmail().port());
        javaMailSender.setUsername(hoaxProperties.getEmail().username());
        javaMailSender.setPassword(hoaxProperties.getEmail().password());

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");

    }

    public void sendActivationMail(String mail, String activationToken) {
        var activationURL = hoaxProperties.getClient().host() + "/activation/" + activationToken;

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

        var mailBody = activationEmail.replace("${url}", activationURL);
        try {
            message.setFrom(hoaxProperties.getEmail().from());
            message.setTo(mail);
            message.setSubject("Activation");
            message.setText(mailBody, true);
        } catch (Exception e) {

        }

        this.javaMailSender.send(mimeMessage);
    }
}
