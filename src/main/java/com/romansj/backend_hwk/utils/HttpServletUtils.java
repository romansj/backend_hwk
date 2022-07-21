package com.romansj.backend_hwk.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Created so printing settings would be defined in one place
public class HttpServletUtils {

    private static String NEW_LINE;

    public static String getHeaderData(HttpServletRequest request) {
        var builder = new StringBuilder("request\n");
        builder.append("method: ").append(request.getMethod()).append(NEW_LINE);
        builder.append("url: ").append(request.getRequestURL()).append(NEW_LINE);




        var headerNames = request.getHeaderNames().asIterator();
        headerNames.forEachRemaining(name -> {
            var headers = request.getHeaders(name).asIterator();
            headers.forEachRemaining(header -> builder.append(name).append(": ").append(header).append(NEW_LINE));
        });

        return builder.toString();
    }

    public static String getHeaderData(HttpServletResponse response) {
        var builder = new StringBuilder("response\n");

        builder.append("status: ").append(response.getStatus()).append(NEW_LINE);
        response.getHeaderNames().forEach(name -> {
            var headers = response.getHeaders(name);
            headers.forEach(header -> builder.append(name).append(": ").append(header).append(NEW_LINE));
        });

        return builder.toString();
    }
}

