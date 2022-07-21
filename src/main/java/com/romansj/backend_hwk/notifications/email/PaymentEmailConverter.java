package com.romansj.backend_hwk.notifications.email;

import com.romansj.backend_hwk.notifications.email.templating.SubjectAndTemplate;
import com.romansj.backend_hwk.payments.authorization.AuthorizationProvider;
import com.romansj.backend_hwk.payments.authorization.AuthorizationRequest;
import com.romansj.backend_hwk.payments.authorization.AuthorizationType;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

import static com.romansj.backend_hwk.notifications.email.templating.EmailProperty.*;

public class PaymentEmailConverter {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);


    private PaymentEmailConverter() {
    }

    /**
     * Fills Email instance with parameter data from Payment data
     *
     * @param paymentData Holds relevant payment data, the payment itself, authorization request (if any), and user data
     * @return Email instance with relevant data extracted from Payment data
     */
    public static Email getEmailFromPaymentData(String sender, PaymentData paymentData) {
        var properties = getProperties(paymentData, sender);
        return getEmail(paymentData, properties);
    }


    private static Map<String, Object> getProperties(PaymentData paymentData, String sender) {
        var type = getType(paymentData);


        var properties = getCommonProperties(paymentData, sender);
        setAdditionalProperties(paymentData, type, properties);

        return properties;
    }

    private static AuthorizationType getType(PaymentData paymentData) {
        return AuthorizationProvider.getType(paymentData.authorizationRequest());
    }


    /**
     * Sets all {@link AuthorizationType shared properties}. Type-specific properties ar set in {@link PaymentEmailConverter#setAdditionalProperties(PaymentData, AuthorizationType, Map)}
     *
     * @return Returns Properties map with all common properties
     */
    private static Map<String, Object> getCommonProperties(PaymentData paymentData, String sender) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(SENDER.name(), sender);
        properties.put(NAME.name(), paymentData.user().getName());
        properties.put(PAYMENT_DATE.name(), dateTimeFormatter.format(paymentData.payment().getPaymentDate()));
        properties.put(ACCOUNT_DESTINATION.name(), paymentData.payment().getAccountDestination());
        properties.put(NAME_DESTINATION.name(), paymentData.payment().getNameDestination());
        properties.put(AMOUNT.name(), paymentData.payment().getAmount());
        properties.put(CURRENCY.name(), paymentData.payment().getCurrency().getDisplayName());
        return properties;

    }


    /**
     * Additional properties are added as needed, per {@link AuthorizationType}
     */
    private static void setAdditionalProperties(PaymentData paymentData, AuthorizationType type, Map<String, Object> properties) {
        switch (type) {
            case AUTHORIZATION -> properties.put(LINK_CONFIRM.name(), getAuthorizationLink(paymentData.authorizationRequest()));
            case CONFIRMATION -> {
            }
        }
    }


    private static SubjectAndTemplate getSubjectAndTemplate(AuthorizationType type) {
        return switch (type) {
            case AUTHORIZATION -> new SubjectAndTemplate("Payment authorization", "payment-authorization-email.html");
            case CONFIRMATION -> new SubjectAndTemplate("Payment confirmation", "payment-confirmation-email.html");
        };
    }


    private static Email getEmail(PaymentData paymentData, Map<String, Object> properties) {
        var type = getType(paymentData);

        var emailAddress = paymentData.user().getEmail();
        var subject = getSubjectAndTemplate(type).subject();
        var template = getSubjectAndTemplate(type).template();

        return getEmail(properties.get(SENDER.name()).toString(), emailAddress, subject, template, properties);
    }

    private static Email getEmail(String sender, String recipient, String subject, String template, Map<String, Object> properties) {
        return Email.builder()
                .from(sender)
                .to(recipient)
                .subject(subject)
                .template(template)
                .properties(properties)
                .build();
    }


    // todo should this class know about the link?
    // not exactly no, nor yes. The link leads to frontend.
    private static String getAuthorizationLink(AuthorizationRequest authorizationRequest) {
        return "http://localhost:8081/payment-approve" + "?token=" + authorizationRequest.getToken();
    }

}
