package com.epam.finalproject.framework.web.support;

import com.epam.finalproject.framework.util.StringUtils;
import com.epam.finalproject.framework.web.URLPattern;
import com.epam.finalproject.framework.web.URLWildcardPattern;
import org.slf4j.Logger;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class URLPatternUtil {

    public static final String PATH_VARIABLE_REGEX = "[{][^/*]+[}]";
    public static final String ANY_REGEX = "[*]";
    public static final String DEEP_ANY_REGEX = "[*][*]";
    static final Pattern urlWildCardPatternRegex = Pattern.compile("((/((" + ANY_REGEX + ")|(" + PATH_VARIABLE_REGEX + ")|(" + "[^/*{}]+" + "))(?=/|$))|(/" + DEEP_ANY_REGEX + "(?=$)))+");
    static final Pattern urlRegularPatternRegex = Pattern.compile("(/[^*{}]*)");

    static final Map<Pattern, String> wildcardPatterns = new HashMap<>();
    static final Pattern PATH_VARIABLE_PATTERN = Pattern.compile("/" + PATH_VARIABLE_REGEX);
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(URLPatternUtil.class);

    static {
        wildcardPatterns.put(PATH_VARIABLE_PATTERN, "/([^/]+/?)?");
        wildcardPatterns.put(Pattern.compile("/" + DEEP_ANY_REGEX), ".*\\$");
        wildcardPatterns.put(Pattern.compile("/" + ANY_REGEX), "/[^/]+/?");
    }

    private URLPatternUtil() {
    }

    public static boolean isWildCartPattern(String target) {
        return urlWildCardPatternRegex.matcher(target).matches();
    }

    public static boolean isRegularPattern(String target) {
        return urlRegularPatternRegex.matcher(target).matches();
    }

    public static URLPattern buildPattern(String target) {
        if (isRegularPattern(target)) {
            return new URLPattern(target);
        } else if (isWildCartPattern(target)) {
            String regex = compileRegex(target);
            log.trace("Build regex {} for target {}", regex, target);
            Pattern pattern = Pattern.compile(regex);
            return new URLWildcardPattern(target, pattern, (int) StringUtils.countOccurrences(target, '/'));
        } else {
            throw new IllegalArgumentException("Not pattern string " + target);
        }
    }

    private static String compileRegex(String target) {
        List<String> parts = Arrays.stream(target.split("(?=/)")).collect(Collectors.toList());

        if (parts.size() == 1){
            PATH_VARIABLE_PATTERN.matcher(parts.get(0)).matches();
            return "/([^/]*/?)?";
        }

        Map<Integer, String> partsMap = IntStream.range(0, parts.size())
                .boxed()
                .collect(Collectors.toMap(i -> i, parts::get));
        for (Map.Entry<Integer, String> part : partsMap.entrySet()) {
            Optional<String> replace = wildcardPatterns.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().matcher(part.getValue()).matches())
                    .map(entry -> entry.getKey().matcher(part.getValue()).replaceAll(entry.getValue()))
                    .findFirst();
            if (replace.isPresent()) {
                partsMap.put(part.getKey(), replace.orElseThrow());
            }

        }
        return partsMap.values().stream().reduce("", (s, s2) -> s + s2);

    }
}
