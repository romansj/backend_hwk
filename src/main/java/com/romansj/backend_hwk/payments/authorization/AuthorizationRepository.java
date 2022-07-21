package com.romansj.backend_hwk.payments.authorization;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationRepository extends JpaRepository<AuthorizationRequest, Long> {
//    AuthorizationRequest findAuthorizationRequestsByPayment(Payment payment);


    AuthorizationRequest findByPayment_Id(long paymentId);

    AuthorizationRequest findAuthorizationRequestByToken(@NonNull String token);

}
