package com.romansj.backend_hwk.notifications;

import com.romansj.backend_hwk.notifications.email.Email;
import com.romansj.backend_hwk.notifications.email.EmailConfig;
import com.romansj.backend_hwk.notifications.email.PaymentData;
import com.romansj.backend_hwk.notifications.email.PaymentEmailConverter;
import com.romansj.backend_hwk.notifications.email.templating.EmailService;
import com.romansj.backend_hwk.payments.PaymentObservable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class NotificationService {


    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailConfig emailConfig;


    /**
     * Upon creation, subscribes to {@link PaymentObservable} to listen to new payment events, such as sending and authorization
     */
    public NotificationService() {
        PaymentObservable.getInstance().getObservable().subscribe(paymentData -> {
            sendEmail(paymentData);

        }, Throwable::printStackTrace, () -> System.out.println("stopped listening to successful payments"));

    }


    private void sendEmail(PaymentData paymentData) throws MessagingException {
        var email = PaymentEmailConverter.getEmailFromPaymentData(emailConfig.getEmail(), paymentData);
        sendEmail(email);
    }

    private void sendEmail(Email email) throws MessagingException {
        emailService.sendHtmlEmail(email);
    }


}
