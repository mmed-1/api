package centre.ajial.webApi.mail;

import centre.ajial.webApi.models.Person;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@AllArgsConstructor
public class SendEmail {

    private final JavaMailSender mailSender;
    private final TemplateEngine engine;

    public void sendEmail(Person person, String password) throws Exception {
        //param to send email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //create the context
        Context context = new Context();
        context.setVariable("person", person);
        context.setVariable("passwd", password);
        //take the content
        String htmlMessage = engine.process("mail", context);
        //config the sending
        helper.setFrom("YOUR_EMAIL_ADDRESS", "apiTest");
        helper.setTo(person.getEmail());
        helper.setSubject("Welcome to our service!");
        helper.setText(htmlMessage, true);
        mailSender.send(message);
    }
}
