package com.romansj.backend_hwk.notifications.email;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Map;

@Data
@Builder
public class Email {

    @NonNull
    private String to;

    @NonNull
    private String from;

    @NonNull
    private String subject;

    private String text;

    @NonNull
    private String template;

    @NonNull
    private Map<String, Object> properties;
}