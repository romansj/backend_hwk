package com.romansj.backend_hwk.payments.validation;

import com.romansj.backend_hwk.accounts.IAccount;
import com.romansj.backend_hwk.configuration.exceptions.MyConstraintException;
import com.romansj.backend_hwk.configuration.exceptions.MyViolation;
import com.romansj.backend_hwk.payments.entity.Payment;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.cfg.defs.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PaymentValidator { // implements IPaymentValidator
    private static Validator getValidator() {
        var configuration = Validation
                .byProvider(HibernateValidator.class)
                .configure();

        var constraintMapping = configuration.createConstraintMapping();

        constraintMapping
                .type(Payment.class)
                .field("accountDestination")
                .constraint(new NotNullDef())
                .constraint(new SizeDef().min(21).max(34))
                .field("nameDestination")
                .constraint(new NotNullDef())
                .constraint(new NotBlankDef())
                .field("reason")
                .constraint(new NotNullDef())
                .constraint(new NotBlankDef())
                .field("amount")
                .ignoreAnnotations(true)
                .constraint(new NotNullDef())
                .constraint(new DecimalMinDef().value("0.01").inclusive(true))
                .constraint(new DecimalMaxDef().value("1000000").inclusive(true));

        var validator = configuration.addMapping(constraintMapping)
                .buildValidatorFactory()
                .getValidator();

        return validator;
    }

    public static void validate(IAccount account, Payment providedPayment) {
        var violations = getValidator().validate(providedPayment);
        var myViolations = getViolations(violations);


        if (providedPayment.getAccountDestination().equals(providedPayment.getAccountOrigin())) {
            // easier to implement than own Hibernate constraint, but means hardcoded path
            myViolations.add(new MyViolation("accountDestination", "Destination and origin accounts cannot be the same"));
        }

        if (!violations.isEmpty()) throw new MyConstraintException(myViolations);
    }


    private static List<MyViolation> getViolations(Set<ConstraintViolation<Payment>> constraintViolations) {
        var myViolationList = new ArrayList<MyViolation>();
        constraintViolations.forEach(paymentConstraintViolation -> myViolationList.add(
                new MyViolation(
                        paymentConstraintViolation.getPropertyPath().toString(),
                        paymentConstraintViolation.getMessage()
                )));

        return myViolationList;
    }


}
