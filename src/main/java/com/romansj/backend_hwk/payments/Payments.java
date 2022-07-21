package com.romansj.backend_hwk.payments;

import com.romansj.backend_hwk.payments.entity.Payment;
import com.romansj.backend_hwk.payments.entity.PaymentType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;

/**
 * Class which provides test/dummy data via test data builder pattern
 */
public class Payments {
    /**
     * @return Returns {@link Payment} instance.
     * <br><br>
     * Call <code>aPayment().toBuilder()</code> to customize the default values, overriding what you need by calling {@code withX} methods
     */
    public static Payment aPayment() {
        return Payment.aPayment()
                .withAmount(1.00)
                .withPaymentType(PaymentType.LOCAL)
                .withAccountOrigin("LV10PARX0012345678910")
                .withPaymentDate(LocalDateTime.of(2022, 7, 15, 12, 15, 10).atZone(ZoneId.systemDefault()))
                .withAccountDestination("LV10PARX0012345678911")
                .withNameDestination("Jānis Romāns")
                .withSwift("LVPARX22")
                .withAmount(1.00)
                .withCurrency(Currency.getInstance("EUR"))
                .withReason("Test transfer")
                .build();
    }


}
