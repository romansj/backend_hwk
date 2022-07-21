package com.romansj.backend_hwk.payments.authorization;

import com.romansj.backend_hwk.payments.Payments;
import com.romansj.backend_hwk.utils.DateTimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationValidatorTest {

    @InjectMocks
    private AuthorizationValidator authorizationValidator;

    @Mock
    private DateTimeUtil dateTimeUtil;

    private AuthorizationProvider authorizationProvider = new AuthorizationProvider();

    @Test
    void freshRequestShouldBeValid() {
        var payment = Payments.aPayment();
        var authorizationRequest = authorizationProvider.getAuthorizationRequest(payment);
        Assertions.assertFalse(new AuthorizationValidator().isExpired(authorizationRequest));
    }

    @Test
    void oldRequestShouldBeInvalid() {
        var payment = Payments.aPayment();

        var authorizationRequest = authorizationProvider.getAuthorizationRequest(payment);
        var now = LocalDateTime.now();

        when(dateTimeUtil.getCurrentTime()).thenReturn(now.plusMinutes(2).plusSeconds(1));

        Assertions.assertTrue(authorizationValidator.isExpired(authorizationRequest));
    }


    // could also be a mock-less test
    @Test
    void usedRequestShouldBeInvalid() {
        when(dateTimeUtil.getCurrentTime()).thenReturn(LocalDateTime.now());


        var payment = Payments.aPayment();

        var authorizationRequest = authorizationProvider.getAuthorizationRequest(payment);
        authorizationRequest.setUsedAt(LocalDateTime.now());

        Assertions.assertTrue(authorizationValidator.isExpired(authorizationRequest));
    }
}