package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.framework.beans.factory.BeanFactory;
import com.epam.finalproject.framework.beans.factory.StringTypeConverter;
import com.epam.finalproject.framework.beans.factory.TypeConverter;
import com.epam.finalproject.framework.beans.factory.config.PropertyValueHolder;
import com.epam.finalproject.framework.util.ClassUtils;
import com.epam.finalproject.framework.util.CustomCollectionsUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SetterMethodResolver {
    public static Method resolveSetter(Object bean, PropertyValueHolder holder) throws NoSuchMethodException {
        String setterName;
        if (holder.isMethodName()){
            setterName = holder.getName();
        }else {
            setterName = BeanNameConverters.fieldNameToSetterName(holder.getName());
        }
        List<Method> setterMethods = Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals(setterName))
                .filter(method -> method.getParameterCount() == 1)
                .collect(Collectors.toList());

        TypeConverter converter = new StringTypeConverter();

        Optional<Method> result = setterMethods.stream()
                .filter(method -> {
                    boolean isSatisfies = ClassUtils.isAssignableValue(holder.getValue(),method.getParameterTypes()[0]);
                    if (!isSatisfies){
                        isSatisfies = converter.isConversionAvailable(holder.getValue(), method.getParameterTypes()[0]);
                    }
                    return isSatisfies;
                })
                .collect(CustomCollectionsUtil.toOneOrEmpty());
        if (result.isEmpty()) {
            throw new NoSuchMethodException("No correct method found for name " + setterName);
        }
        return result.orElseThrow();
    }
}
