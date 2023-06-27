package template.jwttemplate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {
    @Autowired
    private final JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("chapagana@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

            // Load the image from the resources folder
            ClassPathResource imageResource = new ClassPathResource("email/welcome.jpeg");

            // Add the image as an attachment
            helper.addAttachment("image.png", imageResource);

            mailSender.send(message);
            log.info("Welcome email sent successfully...");
        } catch (Exception e) {
            log.error("Welcom email sending failed", e);
        }
    }
}
