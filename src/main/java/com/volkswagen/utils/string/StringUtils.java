package com.volkswagen.utils.string;

public final class StringUtils {
    public static String removeBlanks(String string) {
        return string.replaceAll("\\s+", "");
    }

}
