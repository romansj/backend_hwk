package com.romansj.backend_hwk.notifications.email;

import com.romansj.backend_hwk.payments.authorization.AuthorizationRequest;
import com.romansj.backend_hwk.payments.entity.Payment;
import com.romansj.backend_hwk.users.User;

public record PaymentData(Payment payment, AuthorizationRequest authorizationRequest, User user) {

}
