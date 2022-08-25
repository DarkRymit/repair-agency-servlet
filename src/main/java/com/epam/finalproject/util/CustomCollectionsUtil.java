package com.epam.finalproject.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public interface CustomCollectionsUtil {
    static <T, K, U> Collector<T, ?, Map<K, U>> toMapOfNullables(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            Map<K, U> map = new LinkedHashMap<>();
            list.forEach(item -> {
                K key = keyMapper.apply(item);
                U value = valueMapper.apply(item);
                if (map.containsKey(key)) {
                    throw new IllegalStateException(String.format("Duplicate key %s (attempted merging values %s and %s)", key, map.get(key), value));
                }
                map.put(key, value);
            });
            return map;
        });
    }
}
