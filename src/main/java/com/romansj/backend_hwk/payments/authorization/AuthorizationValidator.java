package com.romansj.backend_hwk.payments.authorization;

import com.romansj.backend_hwk.utils.DateTimeUtil;
import com.romansj.backend_hwk.utils.IDateTimeUtil;

import java.time.LocalDateTime;

public class AuthorizationValidator {

    private IDateTimeUtil dateTimeUtil = new DateTimeUtil();

    public boolean isExpired(AuthorizationRequest request) {
        LocalDateTime now = dateTimeUtil.getCurrentTime();
        System.out.println("now " + now);

        return now.isAfter(request.getExpiresAt()) // time has passed
                ||
                now.isEqual(request.getExpiresAt()) // now is exactly expiration time
                ||
                request.getUsedAt() != null; // time could still be valid, but it's already used
    }


}
