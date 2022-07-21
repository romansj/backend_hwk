package com.romansj.backend_hwk.notifications.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Qualifier("gmail")
@Service
public class GoogleMailSender implements IMailSender {

    @Autowired
    private EmailConfig emailConfig;

    /**
     * Configuration method of {@link JavaMailSenderImpl}, setting defaults from {@link EmailConfig}
     *
     * @return {@link JavaMailSenderImpl} instance with set parameters
     */
    @Override
    public JavaMailSenderImpl getMailSender() {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfig.getHost());
        mailSender.setPort(emailConfig.getPort());
        mailSender.setUsername(emailConfig.getEmail());
        mailSender.setPassword(emailConfig.getPassword());

        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // props.put("mail.debug", "true");

        return mailSender;
    }

    @Override
    public void send(MimeMessage message) {
        getMailSender().send(message);
    }

    @Override
    public MimeMessage createMimeMessage() {
        return getMailSender().createMimeMessage();
    }


}
