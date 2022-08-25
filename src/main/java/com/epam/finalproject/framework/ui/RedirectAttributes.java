package com.epam.finalproject.framework.ui;

import java.util.Map;

public interface RedirectAttributes {
    RedirectAttributes addAttribute(String attributeName, Object attributeValue);

    RedirectAttributes addFlashAttribute(String attributeName, Object attributeValue);

    Map<String, Object> getFlashAttributes();

    RedirectAttributes addAllAttributes(Map<String, ?> attributes);

    boolean containsAttribute(String attributeName);

    Object getAttribute(String attributeName);

    <T> T getAttribute(String attributeName, Class<T> clazz);

    Map<String, Object> asMap();
}