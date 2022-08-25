package com.epam.finalproject.framework.data.transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class TransactionSynchronizationManager {
    private static final ThreadLocal<Map<Object, Object>> resources = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> actualTransactionActive =  new ThreadLocal<>();

    private TransactionSynchronizationManager() {
    }

    public static Map<Object, Object> getResourceMap() {
        Map<Object, Object> map = resources.get();
        return map != null ? Collections.unmodifiableMap(map) : Collections.emptyMap();
    }

    public static boolean hasResource(Object key) {
        Object value = doGetResource(key);
        return value != null;
    }

    public static Object getResource(Object key) {
        return doGetResource(key);
    }

    private static Object doGetResource(Object actualKey) {
        Map<Object, Object> map = resources.get();
        if (map == null) {
            return null;
        } else {
            return map.get(actualKey);
        }
    }

    public static Object bindResource(Object key, Object value) throws IllegalStateException {
        Objects.requireNonNull(value, "Value must not be null");
        Map<Object, Object> map = resources.get();
        if (map == null) {
            map = new HashMap<>();
            resources.set(map);
        }
        return map.put(key, value);
    }

    public static Object unbindResource(Object key) throws IllegalStateException {
        return doUnbindResource(key);
    }

    private static Object doUnbindResource(Object actualKey) {
        Map<Object, Object> map = resources.get();
        if (map == null) {
            return null;
        } else {
            Object value = map.remove(actualKey);
            if (map.isEmpty()) {
                resources.remove();
            }
            return value;
        }
    }


    public static void setActualTransactionActive(boolean active) {
        actualTransactionActive.set(active ? Boolean.TRUE : null);
    }

    public static boolean isActualTransactionActive() {
        return actualTransactionActive.get() != null;
    }

    public static void clear() {
        resources.remove();
        actualTransactionActive.remove();
    }
}