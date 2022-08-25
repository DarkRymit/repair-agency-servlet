package com.epam.finalproject.framework.web.resolver;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Beans;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.beans.factory.TypeConverter;
import com.epam.finalproject.framework.util.ClassUtils;
import com.epam.finalproject.framework.util.StringUtils;
import com.epam.finalproject.framework.web.RequestHandlerContainer;
import com.epam.finalproject.framework.web.WebHttpPair;
import com.epam.finalproject.framework.web.annotation.RequestObject;
import com.epam.finalproject.framework.web.resolver.annotation.CollectionType;
import com.epam.finalproject.util.CustomCollectionsUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Stream;

@Component
public class ObjectResolver implements HandlerArgumentResolver {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ObjectResolver.class);
    List<TypeConverter> converters;

    @Autowire
    public ObjectResolver(@Beans(TypeConverter.class) List<TypeConverter> converters) {
        this.converters = converters;
    }

    @Override
    public Object resolve(WebHttpPair pair, Parameter parameter, RequestHandlerContainer handler) {
        Class<?> targetClass = getTargetClass(parameter);
        HttpServletRequest request = pair.getRequest();
        Object result;
        Map<Field, Object> objectMap = Stream.of(targetClass.getDeclaredFields())
                .collect(CustomCollectionsUtil.toMapOfNullables(field -> field, field -> getValue(request, field)));
        try {

            result = targetClass.getDeclaredConstructor().newInstance();
            Object finalResult = result;
            objectMap.forEach((field, value) -> setFieldValue(targetClass, finalResult, field, value));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void setFieldValue(Class<?> targetClass, Object finalResult, Field field, Object value) {
        try {
            Method method = targetClass.getDeclaredMethod("set" + StringUtils.fromUpper(field.getName()), field.getType());
            if (!ClassUtils.isAssignableValue(value, field.getType())) {
                value = resolveValue(field, value, method);
            }
            method.invoke(finalResult, value);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object resolveValue(Field field, Object value, Method method) {
        if (value instanceof String[]) {
            value = resolveCollectionValue(field, value);
        } else {
            Object tempValue = value;
            TypeConverter converter = getConverter(field.getType(),tempValue);
            value = converter.convertIfNecessary(value, method.getParameterTypes()[0]);
        }
        return value;
    }

    private Object resolveCollectionValue(Field field, Object value) {
        CollectionType collectionType = field.getAnnotation(CollectionType.class);
        Class<?> itemClass = collectionType.value();
        String[] values = (String[]) value;
        List<Object> converted = new ArrayList<>();
        for (String v : values) {
            if (!ClassUtils.isAssignableValue(v, itemClass)) {
                TypeConverter converter = getConverter(itemClass, v);
                converted.add(converter.convertIfNecessary(v, itemClass));
            } else {
                converted.add(v);
            }
        }
        if (ClassUtils.isAssignable(List.class, field.getType())) {
            value = converted;
        } else if (ClassUtils.isAssignable(Set.class, field.getType())) {
            value = new HashSet<>(converted);
        } else {
            throw new IllegalArgumentException(String.format("Not supported field type %s", field.getType()));
        }
        return value;
    }

    private TypeConverter getConverter(Class<?> itemClass, Object v) {
        return converters.stream()
                .filter(typeConverter -> typeConverter.isConversionAvailable(v, itemClass))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("Not find converter to value %s of type %s", v, itemClass)));
    }

    private Serializable getValue(HttpServletRequest request, Field field) {
        if (ClassUtils.isAssignable(field.getType(), Collection.class)) {
            return request.getParameterValues(field.getName());
        } else {
            return request.getParameter(field.getName());
        }
    }

    private Class<?> getTargetClass(Parameter parameter) {
        Class<?> targetClass;
        RequestObject autoResolve = parameter.getAnnotation(RequestObject.class);
        if (!autoResolve.value().equals(RequestObject.DEFAULT.class)) {
            targetClass = autoResolve.value();
        } else {
            if (!parameter.getType().isInterface()) {
                targetClass = parameter.getType();
            } else {
                throw new UnsupportedOperationException(String.format("Interface %s not supported in resolver %s", parameter.getType(), this.getClass()));
            }
        }
        return targetClass;
    }

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestObject.class);
    }
}
