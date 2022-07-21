package com.romansj.backend_hwk.utils;

import java.util.Random;

public class StringUtils {

    // https://www.programiz.com/java-programming/examples/generate-random-string
    public static String getRandomString() {
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;


        var sb = new StringBuilder();
        var random = new Random();
        int length = 10;

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphaNumeric.length());
            char randomChar = alphaNumeric.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
