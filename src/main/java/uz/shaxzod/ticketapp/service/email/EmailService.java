package uz.shaxzod.ticketapp.service.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomEmailSendException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${app.mail.from}")
    private String from;

    @Async
    public void sendResetPasswordMail(String email, String resetLink) {

        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("Reset your password");
            helper.setText(buildResetPasswordHtml(resetLink), true);

            javaMailSender.send(mimeMessage);

            log.info("Password reset email sent to {}", email);
        }catch (Exception e){
            log.error("Failed to send password reset email to {}", email, e);
            throw new CustomEmailSendException("Couldn't send email");
        }
    }

    private String buildResetPasswordHtml(String resetLink) {
        return """
                <html>
                  <body>
                    <p>You requested to reset your password.</p>
                    <p>
                      <a href="%s">Click here to reset your password</a>
                    </p>
                    <p>This link will expire in 15 minutes.</p>
                  </body>
                </html>
                """.formatted(resetLink);
    }
}
