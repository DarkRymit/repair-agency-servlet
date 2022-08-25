package com.epam.finalproject.framework.scanner;

import org.reflections.Reflections;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.SubTypes;

public class ClassPathScanner {
    public Set<Class<?>> scan(String packageName, Predicate<Class<?>> predicate) {

        Reflections reflections = new Reflections(packageName, SubTypes.filterResultsBy(c -> true));
        return reflections.get(SubTypes.of(Object.class).asClass()).stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }
}
