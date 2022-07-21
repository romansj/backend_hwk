package com.romansj.backend_hwk.payments;

import com.romansj.backend_hwk.payments.authorization.AuthorizationProvider;
import com.romansj.backend_hwk.payments.authorization.AuthorizationRepository;
import com.romansj.backend_hwk.payments.entity.Payment;
import com.romansj.backend_hwk.users.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {


    @InjectMocks
    PaymentService paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    AuthorizationRepository authorizationRepository;

    @Mock
    AuthorizationProvider provider;

    @Mock
    User user;


    @Test
    void addingPaymentSavesIt() {
        Payment payment = Payments.aPayment();
        paymentService.addPayment(payment);

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }


    @Test
    void addingPaymentCreatesToken() {
        Payment payment = Payments.aPayment();
        paymentService.addPayment(payment);

        verify(provider, times(1)).getAuthorizationRequest(any(Payment.class));
    }
}