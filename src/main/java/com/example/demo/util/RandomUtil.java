package com.example.demo.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class RandomUtil {

    /**
     * Generates a random UUID (Universally Unique Identifier) string.
     * The generated string will be a unique identifier with a length of 36 characters, consisting of hexadecimal digits
     * separated by hyphens.
     *
     * @return a random UUID string
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }


}
