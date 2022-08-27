package com.epam.finalproject.framework.web.support;

import com.epam.finalproject.framework.beans.factory.support.DefaultSingletonBeanRegistry;
import com.epam.finalproject.framework.web.*;
import com.epam.finalproject.framework.web.support.target.TestTargetController;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestHandlerResolverTest {

    List<RequestHandler> handlers = new ArrayList<>();

    RequestHandlerResolver resolver;

    @Mock
    HttpServletRequest request;

    @BeforeEach
    void init() throws NoSuchMethodException {
        handlers.add(new RequestHandler(TestTargetController.class.getMethod("test1", HttpServletRequest.class),
                "testTargetController",
                new RequestMappingInfo(Set.of(new URLPattern("/test/1")), Set.of("GET"))));
        handlers.add(new RequestHandler(TestTargetController.class.getMethod("testId", HttpServletRequest.class,Long.class),
                "testTargetController",
                new RequestMappingInfo(Set.of(URLPatternUtil.buildPattern("/test/{id}")), Set.of("GET"))));
        resolver = new RequestHandlerResolver();
        handlers.forEach(h -> resolver.addHandler(h));
    }

    @Test
    void resolveShouldReturnCorrectContainerWhenByExistMapping() {
        when(request.getRequestURI()).thenReturn("/final/test/1");
        when(request.getContextPath()).thenReturn("/final");
        when(request.getMethod()).thenReturn("GET");
        RequestHandlerContainer container = resolver.resolve(request).orElseThrow();
        assertEquals(handlers.get(0),container.getHandler());
        assertNull(container.getPattern());
        assertTrue(container.getPathVariables().isEmpty());
    }
    @Test
    void resolveShouldReturnOptionalEmptyWhenByNonExistMapping() {
        when(request.getRequestURI()).thenReturn("/final/test/1");
        when(request.getContextPath()).thenReturn("/final");
        when(request.getMethod()).thenReturn("POST");
        Optional<RequestHandlerContainer> container = resolver.resolve(request);
        assertTrue(container.isEmpty());
    }
    @Test
    void resolveShouldReturnContainerWithPatternWhenByWildcardPatternMapping() {
        when(request.getRequestURI()).thenReturn("/final/test/404");
        when(request.getContextPath()).thenReturn("/final");
        when(request.getMethod()).thenReturn("GET");
        RequestHandlerContainer container = resolver.resolve(request).orElseThrow();
        assertEquals(handlers.get(1),container.getHandler());
        assertTrue(handlers.get(1).getMappingInfo().getUrlPatterns().contains(container.getPattern()));
        assertEquals("/test/{id}",container.getPattern().getOriginalValue());
        assertEquals("404",container.getPathVariables().get("id"));
    }
}