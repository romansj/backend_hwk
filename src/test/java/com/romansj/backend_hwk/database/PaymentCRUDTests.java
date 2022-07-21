package com.romansj.backend_hwk.database;

import com.romansj.backend_hwk.payments.PaymentRepository;
import com.romansj.backend_hwk.payments.Payments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

// Annotation allows us to use test .properties file and connect to H2 in memory database
// Structure of in-memory database is created the same way as permanent database
@DataJpaTest
public class PaymentCRUDTests {
    @Autowired
    PaymentRepository paymentRepository;

    // Since we can find it, we can consider this test outcome to be both Create and Read operation outcome
    @Test
    void paymentIsCreated() {
        var payment = Payments.aPayment();
        var savedEntity = paymentRepository.save(payment);
        var byId = paymentRepository.findById(savedEntity.getId());

        Assertions.assertTrue(byId.isPresent());
    }

    @Test
    void paymentIsDeleted() {
        var payment = Payments.aPayment();
        var savedEntity = paymentRepository.save(payment);
        paymentRepository.delete(savedEntity);

        var byId = paymentRepository.findById(savedEntity.getId());

        Assertions.assertTrue(byId.isEmpty());
    }

    @Test
    void paymentIsUpdated() {
        var payment = Payments.aPayment().toBuilder().withAmount(10.00).build();
        var savedEntity = paymentRepository.save(payment);


        savedEntity.setAmount(9.00);
        var savedUpdatedPayment = paymentRepository.save(savedEntity);


        Assertions.assertEquals(9.00, savedUpdatedPayment.getAmount());
    }


}
