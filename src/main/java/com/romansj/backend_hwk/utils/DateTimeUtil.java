package com.romansj.backend_hwk.utils;

import java.time.LocalDateTime;

// Interface implementation used in token verification.
// Created so it could be mocked and its behavior changed in tests.
public class DateTimeUtil implements IDateTimeUtil {
    @Override
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }


}
