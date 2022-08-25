package com.epam.finalproject.framework.web;

import java.util.Map;
import java.util.Objects;

public class RequestHandlerContainer {
    final RequestHandler handler;
    final URLPattern pattern;
    final Map<String,String> pathVariables;

    public RequestHandlerContainer(RequestHandler handler, URLPattern pattern, Map<String, String> pathVariables) {
        this.handler = handler;
        this.pattern = pattern;
        this.pathVariables = pathVariables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestHandlerContainer container = (RequestHandlerContainer) o;
        return Objects.equals(handler, container.handler) && Objects.equals(pattern,
                container.pattern) && Objects.equals(pathVariables, container.pathVariables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handler, pattern, pathVariables);
    }

    @Override
    public String toString() {
        return "RequestHandlerContainer{" +
                "handler=" + handler +
                ", pattern=" + pattern +
                ", pathVariables=" + pathVariables +
                '}';
    }

    public RequestHandler getHandler() {
        return this.handler;
    }

    public URLPattern getPattern() {
        return this.pattern;
    }

    public Map<String, String> getPathVariables() {
        return this.pathVariables;
    }
}
