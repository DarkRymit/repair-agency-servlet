package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.framework.beans.factory.StringTypeConverter;
import com.epam.finalproject.framework.beans.factory.TypeConverter;
import com.epam.finalproject.framework.beans.factory.config.*;
import com.epam.finalproject.framework.beans.factory.exception.BeansException;
import com.epam.finalproject.framework.beans.factory.exception.ExistBeanDefinitionException;
import com.epam.finalproject.framework.beans.factory.exception.NoSuchBeanDefinitionException;
import com.epam.finalproject.framework.util.ClassUtils;
import com.epam.finalproject.framework.util.CustomCollectionsUtil;
import org.slf4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.epam.finalproject.framework.beans.factory.util.BeanUtils.beanDefinitionComparator;

public class ManualConfigurableBeanFactory extends AbstractBeanFactory {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ManualConfigurableBeanFactory.class);
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    Map<String, BeanDefinition> definitionMap = new ConcurrentHashMap<>();


    @Override
    protected  <T> T doGetBean(String name, Class<T> type, Set<Class<? extends Annotation>> qualifiers) throws BeansException {
        if (name == null && type == null && qualifiers == null) {
            throw new IllegalArgumentException("Name and type and qualifiers both null");
        }
        log.trace("Call doGetBean with name {} and type {} and qualifiers {}", name, type, qualifiers);
        BeanDefinition definition = null;
        if (name != null) {
            BeanDefinition foundDefinition = getBeanDefinition(name);
            if (definitionHasQualifiers(foundDefinition, qualifiers)) {
                definition = foundDefinition;
            }
        } else if (type != null) {
            definition = definitionMap.values()
                    .stream()
                    .filter(beanDefinition -> type.isAssignableFrom(beanDefinition.getBeanClass()))
                    .filter(beanDefinition -> definitionHasQualifiers(beanDefinition, qualifiers))
                    .collect(CustomCollectionsUtil.toOneOrEmpty())
                    .orElseThrow(() -> new NoSuchBeanDefinitionException(String.format("For name %s type %s qualifiers %s", null, type, qualifiers)));
        } else {
            definition = definitionMap.values()
                    .stream()
                    .filter(beanDefinition -> definitionHasQualifiers(beanDefinition, qualifiers))
                    .collect(CustomCollectionsUtil.toOneOrEmpty())
                    .orElseThrow();
        }
        if (definition == null) {
            throw new NoSuchBeanDefinitionException(String.format("Not found for {name: %s, type: %s , qualifiers: %s}", name, type, qualifiers));
        }
        log.trace("doGetBean result definition {} ", definition);
        return getBeanByDefinition(definition, type);
    }

    @Override
    protected  <T> List<T> doGetBeans(Class<T> type, Set<Class<? extends Annotation>> qualifiers) throws BeansException {
        return doGetBeanDefinitions(type, qualifiers).stream()
                .map(definition -> getBeanByDefinition(definition, type))
                .collect(Collectors.toList());
    }


    private boolean definitionHasQualifiers(BeanDefinition definition, Set<Class<? extends Annotation>> setOfQualifiers) {
        if (setOfQualifiers == null) return true;
        return definition.getBeanQualifiers().containsAll(setOfQualifiers);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        BeanDefinition definition = definitionMap.get(name);
        if (definition == null) {
            throw new NoSuchBeanDefinitionException("No bean named " + name + " is defined");
        }
        return definition;
    }

    @Override
    protected Object setupBean(BeanDefinition definition) {
        log.trace("Call setupBean for {}", definition.getBeanName());
        Object bean = doCreateBean(definition);
        bean = initializeBean(bean, definition);
        return bean;
    }


    private Object initializeBean(Object bean, BeanDefinition definition) {
        try {
            log.trace("Call initializeBean for definition {}", definition);
            for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                bean = beanPostProcessor.postProcessBeforeInitialization(bean, definition.getBeanName());
            }

            if (definition.getInitMethodName() != null) {
                Method method = definition.getBeanClass().getMethod(definition.getInitMethodName());
                method.invoke(bean);
            }

            for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                bean = beanPostProcessor.postProcessAfterInitialization(bean, definition.getBeanName());
            }

            return bean;
        } catch (ReflectiveOperationException e) {
            throw new BeansException(String.format("Cant initializeBean by def %s",definition),e);
        }
    }

    private Object doCreateBean(BeanDefinition definition) {
        Object bean;

        if (definition.getSupplier() != null) {
            bean = definition.getSupplier().get();
        } else {
            List<Object> args = new ArrayList<>();
            ConstructArguments argumentValues = definition.getConstructArguments();
            BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
            for (ConstructValueHolder holder : argumentValues.getArgumentValues()) {
                Object value = valueResolver.resolveValueIfNecessary(holder.getValue());
                if (holder.getClazz() != null) {
                    value = new StringTypeConverter().convertIfNecessary(value, holder.getClazz());
                }
                args.add(value);
            }

            if (definition.getFactoryMethod() != null) {
                bean = constructUsingFactoryMethod(definition, args);
            } else {
                bean = constructUsingConstructor(definition, args);
            }
        }

        applyPropertyValues(bean, definition);
        return bean;
    }

    private Object constructUsingFactoryMethod(BeanDefinition definition, List<Object> args) {
        try {
            Object targetCall = null;
            Class<?> clazz = definition.getBeanClass();
            if (definition.getConfigBeanName() != null) {
                targetCall = getBean(definition.getConfigBeanName());
                if (targetCall == null) {
                    throw new BeansException(String.format("Cant assign value by factory method by def %s",definition));
                }
                clazz = targetCall.getClass();
            }
            Method factoryMethod = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.getName().equals(definition.getFactoryMethod()))
                    .collect(CustomCollectionsUtil.toOneOrEmpty())
                    .orElseThrow(NoSuchMethodException::new);
            factoryMethod.setAccessible(true);
            Object result = factoryMethod.invoke(targetCall, args.toArray());
            if (!ClassUtils.isAssignableValue(result, definition.getBeanClass())) {
                throw new BeansException(String.format("Cant assign value by factory method by def %s",definition));
            }
            return result;
        } catch (Exception e) {
            throw new BeansException(String.format("Exception for def %s",definition),e);
        }
    }

    private Object constructUsingConstructor(BeanDefinition definition, List<Object> args) {
        Object bean;

        Constructor<?> constructor = ConstructorResolver.resolveConstructor(definition, args);

        if (constructor.getParameterCount() == 0) {
            try {
                bean = constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new BeansException("create bean for " + definition.getBeanName() + " failed", e);
            }
        } else {
            bean = constructUsingArgsConstructor(definition, constructor, args);
        }
        return bean;
    }

    private Object constructUsingArgsConstructor(BeanDefinition definition, Constructor<?> constructor, List<Object> args) {
        try {
            return constructor.newInstance(args.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("create bean for " + definition.getBeanName() + " failed", e);
        }
    }

    private void applyPropertyValues(Object bean, BeanDefinition definition) {
        if (definition.hasPropertyValues()) {
            try {
                for (PropertyValueHolder holder : definition.getPropertyValues().getValueHolders()) {
                    BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
                    Object value = resolver.resolveValueIfNecessary(holder.getValue());
                    holder.setValue(value);
                    assignValueToBean(bean, holder);
                }
            } catch (ReflectiveOperationException e) {
                throw new BeansException("Bean " + definition.getBeanName() + " cant apply property due exception " + e);
            }
        }
    }

    private void assignValueToBean(Object bean, PropertyValueHolder holder) throws ReflectiveOperationException {

        InjectPropertyRule rule = holder.getRule();

        switch (rule) {
            case SETTER_ONLY:
                assignValueToField(bean, holder);
                break;
            case FIELD_ONLY:
                assignValueToBeanUsingSetter(bean, holder);
                break;
            case SETTER_FIELD:
                try {
                    assignValueToBeanUsingSetter(bean, holder);
                } catch (NoSuchMethodException e) {
                    assignValueToField(bean, holder);
                }
                break;
            case FIELD_SETTER:
                try {
                    assignValueToField(bean, holder);
                } catch (NoSuchFieldException e) {
                    assignValueToBeanUsingSetter(bean, holder);
                }
                break;
        }

    }

    private void assignValueToField(Object bean, PropertyValueHolder holder) throws IllegalAccessException, NoSuchFieldException {
        String fieldName;
        if (holder.isMethodName()) {
            fieldName = BeanNameConverters.setterNameToFieldName(holder.getName());
        } else {
            fieldName = holder.getName();
        }
        Field field = bean.getClass().getDeclaredField(fieldName);
        TypeConverter converter = new StringTypeConverter();
        Object value = converter.convertIfNecessary(holder.getValue(), field.getType());
        holder.setValue(value);
        if (!ClassUtils.isAssignable(value.getClass(), field.getType())) {
            throw new IllegalArgumentException("Not right type  " + value.getClass() + " to " + field.getType());
        }
        field.setAccessible(true);
        field.set(bean, holder.getValue());
    }

    private void assignValueToBeanUsingSetter(Object bean, PropertyValueHolder holder) throws ReflectiveOperationException {
        Method declaredMethod = SetterMethodResolver.resolveSetter(bean, holder);
        TypeConverter converter = new StringTypeConverter();
        Object value = converter.convertIfNecessary(holder.getValue(), declaredMethod.getParameterTypes()[0]);
        holder.setValue(value);
        declaredMethod.setAccessible(true);
        declaredMethod.invoke(bean, holder.getValue());
    }

    @Override
    public boolean containsBean(String name) {
        return definitionMap.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getBeanDefinition(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return getBeanDefinition(name).isPrototype();
    }

    public ManualConfigurableBeanFactory registerBean(BeanDefinition definition) {
        if(definitionMap.containsKey(definition.getBeanName())){
            throw new ExistBeanDefinitionException(String.format("Definition name already exist by %s",definition));
        }
        definitionMap.put(definition.getBeanName(), definition);
        return this;
    }

    public ManualConfigurableBeanFactory registerBeanPostProcessor(BeanPostProcessor postProcessor) {
        beanPostProcessors.add(postProcessor);
        return this;
    }

    public void constructSingletons() {
        Set<BeanDefinition> singletonDefinitions = definitionMap.values()
                .stream()
                .filter(BeanDefinition::isSingleton)
                .collect(Collectors.toSet());
        for (BeanDefinition definition : singletonDefinitions) {
            if (getSingleton(definition.getBeanName()) == null) {
                log.trace("Setup singleton for {}", definition.getBeanName());
                registerSingleton(definition.getBeanName(), setupBean(definition));
            }
        }
    }


    @Override
    public List<BeanDefinition> getBeanDefinitions(Class<?> type, Set<Class<? extends Annotation>> qualifiers) {
        return doGetBeanDefinitions(type, qualifiers);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions(Set<Class<? extends Annotation>> qualifiers) {
        return doGetBeanDefinitions(null, qualifiers);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions(Class<?> type) {
        return doGetBeanDefinitions(type, null);
    }

    protected List<BeanDefinition> doGetBeanDefinitions(Class<?> type, Set<Class<? extends Annotation>> qualifiers) throws BeansException {
        List<BeanDefinition> definitions;

        if (type == null && (qualifiers == null || qualifiers.isEmpty())) {
            throw new IllegalArgumentException("Type null and qualifiers null or empty");
        }

        if (type != null) {
            definitions = definitionMap.values()
                    .stream()
                    .filter(definition -> type.isAssignableFrom(definition.getBeanClass()))
                    .filter(definition -> definitionHasQualifiers(definition, qualifiers))
                    .sorted(beanDefinitionComparator)
                    .collect(Collectors.toList());
        } else {
            definitions = definitionMap.values()
                    .stream()
                    .filter(definition -> definitionHasQualifiers(definition, qualifiers))
                    .sorted(beanDefinitionComparator)
                    .collect(Collectors.toList());
        }

        return definitions;
    }

}
