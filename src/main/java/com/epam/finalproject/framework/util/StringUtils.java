package com.epam.finalproject.framework.util;

import java.util.*;

public class StringUtils {

    public static boolean hasText(String str) {
        return str != null && !str.isBlank();
    }

    public static String fromLower(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static String fromUpper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static long countOccurrences(String string, char target) {
        return string.chars().filter(ch -> ch == target).count();
    }
}
