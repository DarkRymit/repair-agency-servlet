package com.epam.finalproject.framework.beans.factory.config;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

public class BeansReference {

    private final Class<?> clazz;

    private final Set<Class<? extends Annotation>> qualifiers;


    public BeansReference(Class<?> clazz, Set<Class<? extends Annotation>> qualifiers) {
        this.clazz = clazz;
        this.qualifiers = qualifiers;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public Set<Class<? extends Annotation>> getQualifiers() {
        return this.qualifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeansReference that = (BeansReference) o;

        if (!Objects.equals(clazz, that.clazz)) return false;
        return Objects.equals(qualifiers, that.qualifiers);
    }

    @Override
    public int hashCode() {
        int result = clazz != null ? clazz.hashCode() : 0;
        result = 31 * result + (qualifiers != null ? qualifiers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BeansReference{" +
                "clazz=" + clazz +
                ", qualifiers=" + qualifiers +
                '}';
    }
}
