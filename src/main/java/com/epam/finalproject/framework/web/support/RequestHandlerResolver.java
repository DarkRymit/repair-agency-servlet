package com.epam.finalproject.framework.web.support;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.util.CustomCollectionsUtil;
import com.epam.finalproject.framework.web.RequestHandler;
import com.epam.finalproject.framework.web.RequestHandlerContainer;
import com.epam.finalproject.framework.web.URLPattern;
import com.epam.finalproject.framework.web.URLWildcardPattern;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
public class RequestHandlerResolver {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RequestHandlerResolver.class);
    private final List<RequestHandler> handlers = new ArrayList<>();

    public void addHandler(RequestHandler handler) {
        handlers.add(handler);
    }

    public Optional<RequestHandlerContainer> resolve(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String method = request.getMethod();

        Optional<RequestHandler> handlerOptional = handlers.stream()
                .filter(h -> h.getMappingInfo().getRequestMethods().contains(method))
                .filter(h -> matchPath(h, path))
                .collect(CustomCollectionsUtil.toOneOrEmpty());

        if (handlerOptional.isPresent()) {
            return Optional.of(new RequestHandlerContainer(handlerOptional.orElseThrow(), null,Map.of()));
        }

        return handlers.stream()
                .filter(h -> h.getMappingInfo().getRequestMethods().contains(method))
                .flatMap((Function<RequestHandler, Stream<Map.Entry<RequestHandler, URLWildcardPattern>>>) requestHandler -> requestHandler.getMappingInfo()
                        .getUrlPatterns()
                        .stream()
                        .filter(URLWildcardPattern.class::isInstance)
                        .map(urlPattern -> Map.entry(requestHandler,(URLWildcardPattern)urlPattern)))
                .filter(entry -> isPathMatchPattern(path, entry.getValue()))
                .sorted((o1, o2) -> o2.getValue().getUrlPartCount() - o1.getValue().getUrlPartCount())
                .min((o1, o2) -> calculatePathMatchScore(o2.getValue(), path) - calculatePathMatchScore(o1.getValue(), path))
                .map(entry -> new RequestHandlerContainer(entry.getKey(), entry.getValue(),parsePathVariables(entry.getValue(),path)));
    }

    private Map<String, String> parsePathVariables(URLWildcardPattern pattern, String realPath) {
        Map<String,String> result = new HashMap<>();
        String[] realPathParts = realPath.split("/");
        String[] patternPathParts = pattern.getOriginalValue().split("/");
        int length = Integer.min(realPathParts.length, patternPathParts.length);
        for (int i = 0; i < length; i++) {
            if (patternPathParts[i].startsWith("{") && patternPathParts[i].endsWith("}")) {
                result.put(patternPathParts[i].substring( 1, patternPathParts[i].length() - 1 ),realPathParts[i]);
            }
        }
        return  result;
    }

    private boolean matchPath(RequestHandler handler, String realPath) {
        return handler.getMappingInfo()
                .getUrlPatterns()
                .stream()
                .map(URLPattern::getOriginalValue)
                .anyMatch(s -> s.equals(realPath));
    }

    private int calculatePathMatchScore(URLWildcardPattern pattern, String realPath) {
        int score = 0;
        String[] realPathParts = realPath.split("/");
        String[] patternPathParts = pattern.getOriginalValue().split("/");
        int length = Integer.min(realPathParts.length, patternPathParts.length);
        for (int i = 0; i < length; i++) {
            if (realPathParts[i].equals(patternPathParts[i])) {
                score += 2;
            } else if (patternPathParts[i].startsWith("{") && patternPathParts[i].endsWith("}")) {
                score++;
            }
        }
        return score;

    }

    private boolean isPathMatchPattern(String realPath, URLWildcardPattern urlPattern) {
        Pattern pattern = urlPattern.getRegexPattern();
        log.trace("Start match regex {}", pattern.pattern());
        boolean result = pattern.matcher(realPath).matches();
        log.trace("Result {} to regex {} from origin {} for path {} ", result, pattern.pattern(), urlPattern.getOriginalValue(), realPath);
        return result;
    }
}
