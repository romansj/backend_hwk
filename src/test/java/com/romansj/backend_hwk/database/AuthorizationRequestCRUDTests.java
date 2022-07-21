package com.romansj.backend_hwk.database;

import com.romansj.backend_hwk.payments.Payments;
import com.romansj.backend_hwk.payments.authorization.AuthorizationProvider;
import com.romansj.backend_hwk.payments.authorization.AuthorizationRepository;
import com.romansj.backend_hwk.payments.authorization.AuthorizationRequest;
import com.romansj.backend_hwk.payments.entity.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

// Annotation allows us to use test .properties file and connect to H2 in memory database
// Structure of in-memory database is created the same way as permanent database
@DataJpaTest
public class AuthorizationRequestCRUDTests {

    @Autowired
    AuthorizationRepository repository;

    // Since we can find it, we can consider this test outcome to be both Create and Read operation outcome
    @Test
    void requestIsCreated() {
        var authorizationRequest = getAuthorizationRequest();

        var savedEntity = repository.save(authorizationRequest);
        var byId = repository.findById(savedEntity.getId());

        Assertions.assertTrue(byId.isPresent());
    }

    @Test
    void requestIsDeleted() {
        var authorizationRequest = getAuthorizationRequest();
        var savedEntity = repository.save(authorizationRequest);
        repository.delete(savedEntity);

        var byId = repository.findById(savedEntity.getId());

        Assertions.assertTrue(byId.isEmpty());
    }

    @Test
    void requestIsUpdated() {
        var authorizationRequest = getAuthorizationRequest();
        var savedEntity = repository.save(authorizationRequest);

        var now = LocalDateTime.now();


        authorizationRequest.setUsedAt(now.plusMinutes(1));
        var savedUpdatedPayment = repository.save(savedEntity);


        Assertions.assertEquals(now.plusMinutes(1), savedUpdatedPayment.getUsedAt());
    }

    private AuthorizationRequest getAuthorizationRequest() {
        var payment = Payments.aPayment();
        return getAuthorizationRequest(payment);
    }


    private AuthorizationRequest getAuthorizationRequest(Payment payment) {
        return new AuthorizationProvider().getAuthorizationRequest(payment);
    }
}
