package com.romansj.backend_hwk.payments.validation;

import com.romansj.backend_hwk.accounts.IAccount;
import com.romansj.backend_hwk.payments.entity.Payment;

public interface IPaymentValidator {
    boolean validate(IAccount account, Payment payment);
}
