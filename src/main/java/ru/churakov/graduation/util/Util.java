package ru.churakov.graduation.util;

public class Util {
    public static <T> T orElse(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
