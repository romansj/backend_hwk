package com.romansj.backend_hwk.notifications.email.templating;

import com.romansj.backend_hwk.notifications.email.Email;
import com.romansj.backend_hwk.notifications.email.IMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    // can be substituted (in annotation) for another Sender implementation
    @Autowired
    @Qualifier("gmail")
    private IMailSender emailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;


    public void sendHtmlEmail(Email email) throws MessagingException {
        var html = getHtml(email);
        var message = getMimeMessage(email, html);

        emailSender.send(message);
    }

    public String getHtml(Email email) {
        var context = new Context();
        context.setVariables(email.getProperties());
        return templateEngine.process(email.getTemplate(), context);
    }

    private MimeMessage getMimeMessage(Email email, String html) throws MessagingException {
        var message = emailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.setFrom(email.getFrom());
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setText(html, true);
        return message;
    }


}
