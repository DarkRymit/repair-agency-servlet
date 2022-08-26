//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.epam.finalproject.framework.data;

import com.epam.finalproject.framework.data.util.LazyStreamable;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Streamable<T> extends Iterable<T>, Supplier<Stream<T>> {

    static <T> Streamable<T> empty() {
        return Collections::emptyIterator;
    }

    static <T> Streamable<T> of(Iterable<T> iterable) {
        return iterable::iterator;
    }

    static <T> Streamable<T> of(Supplier<? extends Stream<T>> supplier) {
        return LazyStreamable.of(supplier);
    }

    default Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    default <R> Streamable<R> map(Function<? super T, ? extends R> mapper) {
        return of(() -> this.stream().map(mapper));
    }

    default <R> Streamable<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return of(() -> this.stream().flatMap(mapper));
    }

    default Streamable<T> filter(Predicate<? super T> predicate) {
        return of(() -> this.stream().filter(predicate));
    }

    default boolean isEmpty() {
        return !this.iterator().hasNext();
    }

    default Streamable<T> and(Supplier<? extends Stream<? extends T>> stream) {
        return of(() -> Stream.concat(this.stream(), stream.get()));
    }

    default Streamable<T> and(Iterable<? extends T> iterable) {
        return of(() -> Stream.concat(this.stream(), StreamSupport.stream(iterable.spliterator(), false)));
    }

    default Streamable<T> and(Streamable<? extends T> streamable) {
        return this.and((Supplier<? extends Stream<? extends T>>)streamable);
    }

    default List<T> toList() {
        return this.stream().collect(Collectors.toUnmodifiableList());
    }

    default Set<T> toSet() {
        return this.stream().collect(Collectors.toUnmodifiableSet());
    }

    default Stream<T> get() {
        return this.stream();
    }

}
