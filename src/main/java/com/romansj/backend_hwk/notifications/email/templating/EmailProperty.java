package com.romansj.backend_hwk.notifications.email.templating;

/**
 * <p>Enum constants are referred to in Thymeleaf template and in code</p>
 * <p>So if a change needs to be made, it is done in both(+) places by changing only the enum constant</p>
 */
public enum EmailProperty {
    SENDER,
    NAME,
    PAYMENT_DATE,
    ACCOUNT_DESTINATION,
    NAME_DESTINATION,
    AMOUNT,
    LINK_CONFIRM,
    CURRENCY
}
