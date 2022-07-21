package com.romansj.backend_hwk.email;

import com.romansj.backend_hwk.notifications.email.PaymentData;
import com.romansj.backend_hwk.notifications.email.PaymentEmailConverter;
import com.romansj.backend_hwk.payments.Payments;
import com.romansj.backend_hwk.payments.authorization.AuthorizationProvider;
import com.romansj.backend_hwk.payments.authorization.AuthorizationRequest;
import com.romansj.backend_hwk.payments.entity.Payment;
import com.romansj.backend_hwk.users.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PaymentEmailConverterTest {
    private User user = new User("example@gmail.com", "en", "Example Name");


    @Test
    void emailContainsPaymentData() {
        var email = PaymentEmailConverter.getEmailFromPaymentData("example@gmail.com", getPaymentData());
        assertEquals(user.getEmail(), email.getTo());
    }


    private PaymentData getPaymentData() {
        var payment = Payments.aPayment();
        var authorizationRequest = getAuthorizationRequest(payment);
        return new PaymentData(payment, authorizationRequest, user);
    }

    private AuthorizationRequest getAuthorizationRequest(Payment payment) {
        return new AuthorizationProvider().getAuthorizationRequest(payment);
    }

}