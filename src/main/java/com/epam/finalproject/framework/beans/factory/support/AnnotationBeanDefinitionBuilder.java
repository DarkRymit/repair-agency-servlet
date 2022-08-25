package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.framework.beans.annotation.*;
import com.epam.finalproject.framework.beans.factory.config.*;
import com.epam.finalproject.framework.util.ClassUtils;
import com.epam.finalproject.framework.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationBeanDefinitionBuilder {

    private AnnotationBeanDefinitionBuilder() {
    }

    public static List<BeanDefinition> buildSubBeans(BeanDefinition root) {
        return Arrays.stream(root.getBeanClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .map(method -> buildSubBean(root, method))
                .collect(Collectors.toList());
    }

    private static BeanDefinition buildSubBean(BeanDefinition root, Method method) {
        Bean beanAnnotation = method.getAnnotation(Bean.class);
        return DefaultBeanDefinition.builder()
                .beanName(getActualName(method, beanAnnotation))
                .scope(getScope(method.getAnnotation(Scope.class)))
                .beanClass(getSubBeanClass(method, beanAnnotation))
                .configBeanName(root.getBeanName())
                .factoryMethod(method.getName())
                .constructArguments(readConstructParameters(method.getParameters()))
                .build();

    }

    private static Class<?> getSubBeanClass(Method method, Bean beanAnnotation) {
        Class<?> clazz = beanAnnotation.clazz();
        if (clazz.equals(Bean.DEFAULT.class)) {
            Class<?> returnType = method.getReturnType();
            if (returnType.isInterface()) {
                throw new RuntimeException();
            }
            return returnType;
        }
        return clazz;
    }

    private static String getActualName(Method method, Bean beanAnnotation) {
        if (!StringUtils.hasText(beanAnnotation.value())) {
            return BeanNameConverters.normalizeName(method.getName());
        }
        return BeanNameConverters.normalizeName(beanAnnotation.value());
    }

    public static <T> BeanDefinition build(Class<T> target) {
        String factoryMethodName = null;
        Method factoryMethod = ClassUtils.getDistinctMethodAnnotatedWith(target, Factory.class);

        ConstructArguments constructArguments;

        if (factoryMethod != null) {
            factoryMethodName = factoryMethod.getName();
            constructArguments = readConstructParameters(factoryMethod.getParameters());
        } else {

            constructArguments = readConstructParameters(ConstructorResolver.simpleResolveForClass((Class<?>) target).getParameters());
        }

        Set<Class<?>> qualifiers = Arrays.stream(target.getAnnotations())
                .map(Annotation::annotationType)
                .filter(aClass -> aClass.isAnnotationPresent(Qualifier.class))
                .collect(Collectors.toSet());

        return DefaultBeanDefinition.builder()
                .beanName(getActualName(target))
                .scope(getScope(target.getAnnotation(Scope.class)))
                .beanClass(target)
                .beanQualifiers(qualifiers)
                .factoryMethod(factoryMethodName)
                .constructArguments(constructArguments)
                .propertyValues(readPropertyValuesField(target.getDeclaredFields()))
                .initMethodName(getMethodName(target, PostConstruct.class))
                .destroyMethodName(getMethodName(target, PreDestroy.class))
                .build();
    }

    private static String getMethodName(Class<?> target, Class<? extends Annotation> annotation) {
        Method method = ClassUtils.getDistinctMethodAnnotatedWith(target, annotation);
        if (method != null) {
            return method.getName();
        }
        return null;
    }

    private static String getScope(Scope scopeAnnotation) {
        if (scopeAnnotation == null || !StringUtils.hasText(scopeAnnotation.value())) {
            return BeanScope.SINGLETON.name();
        }
        return scopeAnnotation.value();
    }

    private static <T> String getActualName(Class<T> target) {
        Component nameAnnotation = target.getAnnotation(Component.class);
        if (nameAnnotation != null && StringUtils.hasText(nameAnnotation.value())) {
            return BeanNameConverters.normalizeName(nameAnnotation.value());
        }
        Configuration configuration = target.getAnnotation(Configuration.class);
        if (configuration != null && StringUtils.hasText(configuration.value())) {
            return BeanNameConverters.normalizeName(configuration.value());
        }
        return BeanNameConverters.normalizeName(target.getSimpleName());
    }

    private static ConstructArguments readConstructParameters(Parameter[] parameters) {
        ConstructArguments resultConstructArguments = new ConstructArguments();
        Arrays.stream(parameters).forEach(parameter -> resultConstructArguments.add(getConstructParameter(parameter)));
        return resultConstructArguments;
    }

    private static ConstructValueHolder getConstructParameter(Parameter parameter) {
        if (parameter.isAnnotationPresent(Value.class)) {
            return new ConstructValueHolder(parameter.getType(), parameter.getAnnotation(Value.class).value());
        }
        if (parameter.isAnnotationPresent(Named.class)) {
            return new ConstructValueHolder(parameter.getType(),
                    new BeanReference(parameter.getAnnotation(Named.class).value(), parameter.getType()));
        }
        if (parameter.isAnnotationPresent(Beans.class)) {
            BeansReference reference = getBeansReference(parameter.getAnnotation(Beans.class),
                    parameter.getAnnotations());
            return new ConstructValueHolder(parameter.getType(), reference);
        }
        if (parameter.isAnnotationPresent(BeanDefinitions.class)) {
            Set<Class<? extends Annotation>> result = Arrays.stream(parameter.getAnnotations())
                    .map(Annotation::annotationType)
                    .filter(aClass -> !aClass.equals(BeanDefinitions.class))
                    .collect(Collectors.toSet());
            return new ConstructValueHolder(parameter.getType(), new BeanDefinitionsReference(result));
        }
        return new ConstructValueHolder(parameter.getType(), new BeanReference(parameter.getType()));
    }

    private static BeansReference getBeansReference(Beans beans, Annotation[] rest) {
        Set<Class<? extends Annotation>> result = Arrays.stream(rest)
                .map(Annotation::annotationType)
                .filter(aClass -> !aClass.equals(Beans.class))
                .collect(Collectors.toSet());
        Class<?> targetClass = beans.value();
        if (ClassUtils.isAssignable(targetClass, Beans.DEFAULT.class)) {
            targetClass = null;
        }
        return new BeansReference(targetClass, result);
    }

    private static PropertyValues readPropertyValuesField(Field[] fields) {
        PropertyValues propertyValues = new PropertyValues();
        for (Field field : fields) {
            PropertyValueHolder holder = getPropertyValue(field);
            if (holder != null) {
                propertyValues.add(holder);
            }
        }
        return propertyValues;
    }

    private static PropertyValueHolder getPropertyValue(Field field) {
        if (field.isAnnotationPresent(Value.class)) {
            return new PropertyValueHolder(field.getName(), field.getAnnotation(Value.class).value());
        }
        if (field.isAnnotationPresent(Named.class)) {
            return new PropertyValueHolder(field.getName(),
                    new BeanReference(field.getAnnotation(Named.class).value(), field.getType()));

        }
        if (field.isAnnotationPresent(Beans.class)) {
            BeansReference reference = getBeansReference(field.getAnnotation(Beans.class), field.getAnnotations());
            return new PropertyValueHolder(field.getName(), reference);
        }
        if (field.isAnnotationPresent(BeanDefinitions.class)) {
            Set<Class<? extends Annotation>> result = Arrays.stream(field.getAnnotations())
                    .map(Annotation::annotationType)
                    .filter(aClass -> !aClass.equals(BeanDefinitions.class))
                    .collect(Collectors.toSet());
            return new PropertyValueHolder(field.getName(), new BeanDefinitionsReference(result));
        }
        if ((field.isAnnotationPresent(Autowire.class))) {
            return new PropertyValueHolder(field.getName(), new BeanReference(field.getType()));
        }
        return null;
    }

}
