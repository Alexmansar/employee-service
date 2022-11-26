package org.alexmansar.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
@Slf4j
public class StringUtil {

    public static String firstUpperCase(String word) {
        if (word == null || word.isEmpty()) return "";
        word = word.toLowerCase(Locale.ROOT);
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
