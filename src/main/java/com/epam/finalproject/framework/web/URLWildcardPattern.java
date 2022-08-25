package com.epam.finalproject.framework.web;

import java.util.regex.Pattern;

public class URLWildcardPattern extends URLPattern{
    Pattern regexPattern;
    int urlPartCount;

    public URLWildcardPattern(String originalValue, Pattern regexPattern, int urlPartCount) {
        super(originalValue);
        this.regexPattern = regexPattern;
        this.urlPartCount = urlPartCount;
    }

    public Pattern getRegexPattern() {
        return this.regexPattern;
    }

    public int getUrlPartCount() {
        return this.urlPartCount;
    }
}
