package com.epam.finalproject.framework.web;

import java.lang.reflect.Method;
import java.util.Objects;

public class RequestHandler {

    private final Method targetMethod;

    private final String controllerBeanName;

    private final RequestMappingInfo mappingInfo;

    public RequestHandler(Method targetMethod, String controllerBeanName, RequestMappingInfo mappingInfo) {
        this.targetMethod = targetMethod;
        this.controllerBeanName = controllerBeanName;
        this.mappingInfo = mappingInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestHandler handler = (RequestHandler) o;
        return Objects.equals(targetMethod, handler.targetMethod) && Objects.equals(controllerBeanName,
                handler.controllerBeanName) && Objects.equals(mappingInfo, handler.mappingInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetMethod, controllerBeanName, mappingInfo);
    }

    @Override
    public String toString() {
        return "RequestHandler{" +
                "targetMethod=" + targetMethod +
                ", controllerBeanName='" + controllerBeanName + '\'' +
                ", mappingInfo=" + mappingInfo +
                '}';
    }

    public Method getTargetMethod() {
        return this.targetMethod;
    }

    public String getControllerBeanName() {
        return this.controllerBeanName;
    }

    public RequestMappingInfo getMappingInfo() {
        return this.mappingInfo;
    }
}
