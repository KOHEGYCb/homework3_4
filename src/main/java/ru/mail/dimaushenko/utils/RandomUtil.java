package ru.mail.dimaushenko.utils;

import java.util.Random;

public class RandomUtil {

    private static Random rand = new Random();

    public static int getInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
}
