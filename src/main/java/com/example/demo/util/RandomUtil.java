package com.example.demo.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@UtilityClass
public class RandomUtil {

    private Random random = new Random();

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

    /**
     * Generates a random name string.
     * The generated string will be a UUID string without hyphens.
     *
     * @return a random name string
     */
    public static String generateRandomString() {
        return generateUUID().replace("-", "");
    }

    /**
     * Generates a random integer within a specified range.
     *
     * @param min the minimum value of the range
     * @param max the maximum value of the range
     * @return a random integer within the specified range
     */
    public static Integer generateRandomInteger(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generates a random BigDecimal within a specified range.
     *
     * @param min the minimum value of the range
     * @param max the maximum value of the range
     * @return a random BigDecimal within the specified range
     */
    public static BigDecimal generateRandomBigDecimal(double min, double max) {

        final int scale = 2;
        double randomDouble = min + (max - min) * random.nextDouble();
        return new BigDecimal(randomDouble, new java.math.MathContext(scale));
    }
}
