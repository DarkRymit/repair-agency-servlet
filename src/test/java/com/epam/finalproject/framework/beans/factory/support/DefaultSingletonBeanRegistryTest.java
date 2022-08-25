package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DefaultSingletonBeanRegistryTest {

    DefaultSingletonBeanRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new DefaultSingletonBeanRegistry();
    }

    @Test
    void registerSingletonShouldReturnSameObject() {
        Object object = Instant.now();
        registry.registerSingleton("testObj", object);
        assertSame(object, registry.getSingleton("testObj"));
    }

    @Test
    void registerSingletonShouldThrowNullPointerExceptionWhenNull() {
        assertThrows(NullPointerException.class, () -> {
            registry.registerSingleton("testObj", null);
        });
    }
    @Test
    void registerSingletonShouldThrowIllegalStateExceptionWhenAlreadyExist() {
        Object object = Instant.now();
        registry.registerSingleton("testObj", object);
        assertThrows(IllegalStateException.class, () -> {
            registry.registerSingleton("testObj", object);
        });
    }

    @Test
    void getSingletonShouldReturnObject() {
        Object object = Instant.now();
        registry.registerSingleton("testObj", object);
        assertNotNull(registry.getSingleton("testObj"));
    }

    @Test
    void getSingletonShouldReturnEqualObject() {
        Object object = Instant.now();
        registry.registerSingleton("testObj", object);
        assertEquals(object, registry.getSingleton("testObj"));
    }

    @Test
    void getSingletonShouldReturnNull() {
        Object object = Instant.now();
        registry.registerSingleton("testObj", object);
        assertNull(registry.getSingleton("tesdddkdtObj"));
    }
}