package com.romansj.backend_hwk.configuration.interception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public record InterceptionData(
        HttpServletRequest servletRequest,
        HttpServletResponse response,
        Exception ex) {
}
