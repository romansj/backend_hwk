package com.romansj.backend_hwk.email;


import com.romansj.backend_hwk.notifications.email.Email;
import com.romansj.backend_hwk.notifications.email.EmailConfig;
import com.romansj.backend_hwk.notifications.email.templating.EmailService;
import com.romansj.backend_hwk.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.romansj.backend_hwk.notifications.email.templating.EmailProperty.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    User user;


    @Autowired
    private EmailService emailSenderService;

    @Test
    void sendHtmlMessage() {
        var email = getEmail(getProperties()).build();
        Assertions.assertDoesNotThrow(() -> emailSenderService.sendHtmlEmail(email));
    }


    @Test
    void emailIsNotEmpty() {
        var email = getEmail(getProperties()).build();
        var html = emailSenderService.getHtml(email);

        Assertions.assertFalse(html.isEmpty());
    }

    @Test
    void emailHasNeededContent() {
        var email = getEmail(getProperties()).build();
        var html = emailSenderService.getHtml(email);

        System.out.println(html);
        Assertions.assertTrue(
                html.contains("Jānis")
                        && html.contains("2022-07-20 23:59:59")
                        && html.contains("account1")
                        && html.contains("John Doe")
                        && html.contains("2.00")
        );
    }


    @Test
    void linkIsClickable() {
        var email = getEmail(getProperties()).build();
        var html = emailSenderService.getHtml(email);

        // not included as plain text. Not checking whether layout/html is broken
        Assertions.assertTrue(html.contains("href=\"https://www.google.com\""));
    }

    @ParameterizedTest()
    @MethodSource("getDecimals")
    void decimalFormatIsCorrect(double amount, String expectedFormattedAmount) {
        Map<String, Object> properties = getProperties();
        properties.put(AMOUNT.name(), amount);

        var email = getEmail(properties).build();
        var html = emailSenderService.getHtml(email);
        System.out.println(html);

        Assertions.assertTrue(html.contains(expectedFormattedAmount));
    }

    private static Stream<Arguments> getDecimals() {
        return Stream.of(
                Arguments.of(1, "1.00"), // doesn't butcher .00
                Arguments.of(10, "10.00"), // same, higher value
                Arguments.of(10000, "10,000.00"), // even higher value + separator
                Arguments.of(1000000, "1,000,000.00"), // even higher value + separator
                Arguments.of(23.59, "23.59"), // different remainder than .00
                Arguments.of(0.01, "0.01"), // value is not rounded down
                Arguments.of(0.00, "0.00") // zero is not special case
        );
    }

    private Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(NAME.name(), "Jānis");
        properties.put(PAYMENT_DATE.name(), "2022-07-20 23:59:59");
        properties.put(ACCOUNT_DESTINATION.name(), "account1");
        properties.put(NAME_DESTINATION.name(), "John Doe");
        properties.put(AMOUNT.name(), 2.00);
        properties.put(LINK_CONFIRM.name(), "https://www.google.com");
        return properties;
    }

    // change templates (unless feature is implemented: https://github.com/junit-team/junit5/issues/871 )
    private Email.EmailBuilder getEmail(Map<String, Object> properties) {
        var email = Email.builder()
                .from(emailConfig.getHost())
                .to(user.getEmail())
                .subject("Payment confirmation")
                .template("payment-authorization-email.html")
                .properties(properties);
        return email;
    }
}