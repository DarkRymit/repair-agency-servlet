package com.epam.finalproject.framework.web;

import java.util.Objects;
import java.util.Set;

public class RequestMappingInfo {
    private Set<URLPattern> urlPatterns;
    private Set<String> requestMethods;

    public RequestMappingInfo(Set<URLPattern> urlPatterns, Set<String> requestMethods) {
        this.urlPatterns = urlPatterns;
        this.requestMethods = requestMethods;
    }

    public static RequestMappingInfoBuilder builder() {
        return new RequestMappingInfoBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestMappingInfo that = (RequestMappingInfo) o;
        return Objects.equals(urlPatterns, that.urlPatterns) && Objects.equals(requestMethods,
                that.requestMethods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlPatterns, requestMethods);
    }

    @Override
    public String toString() {
        return "RequestMappingInfo{" +
                "urlPatterns=" + urlPatterns +
                ", requestMethods=" + requestMethods +
                '}';
    }

    public Set<URLPattern> getUrlPatterns() {
        return this.urlPatterns;
    }

    public Set<String> getRequestMethods() {
        return this.requestMethods;
    }

    public static class RequestMappingInfoBuilder {
        private Set<URLPattern> urlPatterns;
        private Set<String> requestMethods;

        RequestMappingInfoBuilder() {
        }

        public RequestMappingInfoBuilder urlPatterns(Set<URLPattern> urlPatterns) {
            this.urlPatterns = urlPatterns;
            return this;
        }

        public RequestMappingInfoBuilder requestMethods(Set<String> requestMethods) {
            this.requestMethods = requestMethods;
            return this;
        }

        public RequestMappingInfo build() {
            return new RequestMappingInfo(urlPatterns, requestMethods);
        }

        public String toString() {
            return "RequestMappingInfo.RequestMappingInfoBuilder(urlPatterns=" + this.urlPatterns + ", requestMethods=" + this.requestMethods + ")";
        }
    }
}
