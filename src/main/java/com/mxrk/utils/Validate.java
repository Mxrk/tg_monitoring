package com.mxrk.utils;

public class Validate {

    public static String domain(String domain) {
        // TODO: validate domain (http, www, com)
        if (!domain.startsWith("http://") || !domain.startsWith("https://")) {
            return "http://" + domain;
        } else {
            return domain;
        }
    }
}
