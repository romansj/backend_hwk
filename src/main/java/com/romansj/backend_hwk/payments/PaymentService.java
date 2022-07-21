package com.romansj.backend_hwk.payments;

import com.romansj.backend_hwk.accounts.Account;
import com.romansj.backend_hwk.accounts.IAccount;
import com.romansj.backend_hwk.notifications.email.PaymentData;
import com.romansj.backend_hwk.payments.authorization.AuthorizationProvider;
import com.romansj.backend_hwk.payments.authorization.AuthorizationRepository;
import com.romansj.backend_hwk.payments.authorization.AuthorizationRequest;
import com.romansj.backend_hwk.payments.authorization.AuthorizationValidator;
import com.romansj.backend_hwk.payments.entity.Payment;
import com.romansj.backend_hwk.payments.entity.Status;
import com.romansj.backend_hwk.payments.validation.PaymentValidator;
import com.romansj.backend_hwk.users.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.sun.activation.registries.LogSupport.log;

// Did not create delete mapping for payments to keep a history of all of them
// If the payment is not accurate, it is not approved by user and stays in database as "PENDING"
// Would probably schedule some job to update the auth requests which weren't authorised and set payments to "REJECTED"
@Slf4j
@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Autowired
    private AuthorizationProvider authorizationProvider;


    @Autowired
    private User user;
    private IAccount account = new Account(100, "LV10PARX0012345678911");

    /**
     * Validates given payment and stores it in database
     *
     * @param providedPayment Payment data
     * @return Returns created Payment with any modifications, e.g., filled Id field
     */
    public Payment addPayment(Payment providedPayment) {
        PaymentValidator.validate(account, providedPayment);


        var savedPayment = paymentRepository.save(providedPayment);


        var authorizationRequest = authorizationProvider.getAuthorizationRequest(providedPayment);
        authorizationRepository.save(authorizationRequest);


        PaymentObservable.getInstance().notify(new PaymentData(providedPayment, authorizationRequest, user));


        return savedPayment;
    }


    public Optional<Payment> findPaymentById(long id) {
        return paymentRepository.findById(id);
    }



    /**
     * Allows
     *
     * @param token Token which is generated when creating a payment (see {@link PaymentService#addPayment(Payment)}) that is used for finding the associated payment
     * @return Returns updated payment if all checks were successful. If not, the returned Optional is empty
     */
    public Optional<Payment> updatePaymentByToken(String token) {
        // find the auth request
        var authorizationRequest = getAuthorizationRequest(token);
        if (authorizationRequest == null) {
            log.warn("authorizationRequest == null");
            return Optional.empty();
        }
        // update auth request first. In case we fail to update the payment, we should at least invalidate the request, so it can't be used again
        deactivateAuthorizationRequest(authorizationRequest);


        // find payment by auth.payment_id, approve it
        var payment = authorizationRequest.getPayment();
        if (payment == null) {
            log.warn("payment == null");
            return Optional.empty();
        }
        payment.setStatus(Status.APPROVED);
        var savedPayment = paymentRepository.save(payment);


        PaymentObservable.getInstance().notify(new PaymentData(savedPayment, null, user));


        return Optional.of(savedPayment);
    }


    /**
     * Validates {@link AuthorizationRequest} found through parameter
     *
     * @param token Token generated when creating {@link AuthorizationRequest}
     * @return Returns found request if valid. If found, but invalid, returns null
     */
    private AuthorizationRequest getAuthorizationRequest(String token) {
        var authorizationRequest = authorizationRepository.findAuthorizationRequestByToken(token);
        if (authorizationRequest==null) return null;

        var requestExpired = new AuthorizationValidator().isExpired(authorizationRequest);
        if (!requestExpired) return authorizationRequest;

        return null;
    }

    /**
     * Marks given authorization request as used, by specifying use date & time. By effect marks the token string used as well, as this way next requests will fail,
     * ensuring no token can be used more than once
     *
     * @param authorizationRequest Request retrieved by token string
     */
    private void deactivateAuthorizationRequest(AuthorizationRequest authorizationRequest) {
        var timeNow = LocalDateTime.now();

        authorizationRequest.setUsedAt(timeNow);
        authorizationRepository.save(authorizationRequest);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
}
