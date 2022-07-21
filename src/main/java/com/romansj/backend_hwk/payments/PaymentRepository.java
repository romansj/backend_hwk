package com.romansj.backend_hwk.payments;

import com.romansj.backend_hwk.payments.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
