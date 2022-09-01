package com.zoulj.msapp.infrastructure.utils;

import java.util.Locale;

/**
 * @author leonard
 * @date 2021-12-25 15:42
 */
public class Convert {

    /**
     *
     * @param language en-US
     * @return
     */
    public static Locale getResultLocale(String language) {
        String[] split = language.split("-");
        Locale locale;
        if (split.length > 1) {
            locale = new Locale(split[0], split[1]);
        } else {
            locale = new Locale(split[0]);
        }
        return locale;
    }
}
