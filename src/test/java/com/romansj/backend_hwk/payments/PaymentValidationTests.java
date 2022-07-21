package com.romansj.backend_hwk.payments;

import com.romansj.backend_hwk.accounts.Account;
import com.romansj.backend_hwk.configuration.exceptions.MyConstraintException;
import com.romansj.backend_hwk.payments.entity.Payment;
import com.romansj.backend_hwk.payments.validation.PaymentValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class PaymentValidationTests {


    @Test
    void correctPaymentIsValidated() {
        var account = new Account(100, "LV10PARX0012345678911");
        var payment = Payments.aPayment();

        Assertions.assertDoesNotThrow(() -> PaymentValidator.validate(account, payment));
    }


    @ParameterizedTest()
    @MethodSource("getBadPayments")
    void incorrectPaymentsCauseException(Payment payment, Class exceptionClass) {
        var account = new Account(100, "LV10PARX0012345678911");
        Assertions.assertThrows(exceptionClass, () -> PaymentValidator.validate(account, payment));
    }

    private static Stream<Arguments> getBadPayments() {
        return Stream.of(
                Arguments.of(Payments.aPayment().toBuilder().withNameDestination("").build(), MyConstraintException.class), // no destination name
                Arguments.of(Payments.aPayment().toBuilder().withAmount(-1.00).build(), MyConstraintException.class), // negative amount
                Arguments.of(Payments.aPayment().toBuilder().withAccountDestination("").build(), MyConstraintException.class), // no destination account
                Arguments.of(Payments.aPayment().toBuilder().withAmount(10000001.00).build(), MyConstraintException.class), // payment too large
                Arguments.of(Payments.aPayment().toBuilder().withAmount(0.00).build(), MyConstraintException.class) // payment of nothing
        );
    }


}
