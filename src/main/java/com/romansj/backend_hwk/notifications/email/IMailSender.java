package com.romansj.backend_hwk.notifications.email;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.internet.MimeMessage;

/**
 * Interface that chosen email provider should implement.
 */
public interface IMailSender {


    JavaMailSenderImpl getMailSender();


    void send(MimeMessage message);

    MimeMessage createMimeMessage();
}
