package com.epam.finalproject.framework.ui;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RedirectAttributesModelMap extends LinkedHashMap<String, Object> implements RedirectAttributes {

    private final HashMap<String,Object> flashAttributes = new HashMap<>();

    @Override
    public RedirectAttributesModelMap addFlashAttribute(String attributeName, Object attributeValue) {
        flashAttributes.put(attributeName,attributeValue);
        return this;
    }

    @Override
    public Map<String, Object> getFlashAttributes() {
        return this.flashAttributes;
    }

    @Override
    public RedirectAttributesModelMap addAttribute(String attributeName, Object attributeValue) {
        this.put(attributeName, attributeValue);
        return this;
    }

    @Override
    public RedirectAttributesModelMap addAllAttributes(Map<String, ?> attributes) {
        if (attributes != null) {
            this.putAll(attributes);
        }
        return this;
    }

    @Override
    public Map<String, Object> asMap() {
        return this;
    }

    public boolean containsAttribute(String attributeName) {
        return this.containsKey(attributeName);
    }

    public Object getAttribute(String attributeName) {
        return this.get(attributeName);
    }

    @Override
    public <T> T getAttribute(String attributeName, Class<T> clazz) {
        return (T)getAttribute(attributeName);
    }
}
