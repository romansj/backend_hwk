package com.romansj.backend_hwk.payments.authorization;

import com.romansj.backend_hwk.payments.entity.Payment;
import com.romansj.backend_hwk.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthorizationProvider {

    private String generateToken() {
        return StringUtils.getRandomString();
    }


    // todo force creation of class through here
    public AuthorizationRequest getAuthorizationRequest(Payment providedPayment) {
        var dateTime = LocalDateTime.now();
        var expirationDateTime = dateTime.plusMinutes(2); // todo from config.json

        return AuthorizationRequest.aRequest()
                .withToken(generateToken())
                .withPayment(providedPayment)
                .withCreatedAt(dateTime)
                .withExpiresAt(expirationDateTime)
                .build();

    }

    public static AuthorizationType getType(AuthorizationRequest request) {
        if (request == null) return AuthorizationType.CONFIRMATION;
        else return AuthorizationType.AUTHORIZATION;
    }
}
