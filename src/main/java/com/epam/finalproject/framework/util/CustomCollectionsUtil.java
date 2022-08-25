package com.epam.finalproject.framework.util;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public interface CustomCollectionsUtil {

    static <T> Collector<T, ?, Optional<T>> toOneOrEmpty() {
        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            if (list.isEmpty()){
                return Optional.empty();
            }
            if (list.size() != 1) {
                throw new IllegalStateException("Too many" + list.toString());
            }
            return Optional.of(list.get(0));
        });
    }
}
