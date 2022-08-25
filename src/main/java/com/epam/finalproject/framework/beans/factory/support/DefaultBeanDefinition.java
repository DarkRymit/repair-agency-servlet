package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.framework.beans.factory.config.BeanDefinition;
import com.epam.finalproject.framework.beans.factory.config.BeanScope;
import com.epam.finalproject.framework.beans.factory.config.ConstructArguments;
import com.epam.finalproject.framework.beans.factory.config.PropertyValues;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class DefaultBeanDefinition implements BeanDefinition {

    String beanName;

    Class<?> beanClass;

    Set<Class<?>> beanQualifiers;

    String scope;

    String configBeanName;

    String factoryMethod;

    Supplier<?> supplier;

    ConstructArguments constructArguments;

    PropertyValues propertyValues;

    String initMethodName;

    String destroyMethodName;

    public DefaultBeanDefinition(String beanName, Class<?> beanClass, Set<Class<?>> beanQualifiers, String scope,
            String configBeanName, String factoryMethod, Supplier<?> supplier, ConstructArguments constructArguments,
            PropertyValues propertyValues, String initMethodName, String destroyMethodName) {
        this.beanName = beanName;
        this.beanClass = beanClass;
        this.beanQualifiers = beanQualifiers;
        this.scope = scope;
        this.configBeanName = configBeanName;
        this.factoryMethod = factoryMethod;
        this.supplier = supplier;
        this.constructArguments = constructArguments;
        this.propertyValues = propertyValues;
        this.initMethodName = initMethodName;
        this.destroyMethodName = destroyMethodName;
    }

    private static Set<Class<?>> $default$beanQualifiers() {
        return new HashSet<>();
    }

    private static ConstructArguments $default$constructArguments() {
        return new ConstructArguments();
    }

    private static PropertyValues $default$propertyValues() {
        return new PropertyValues();
    }

    public static DefaultBeanDefinitionBuilder builder() {
        return new DefaultBeanDefinitionBuilder();
    }

    @Override
    public boolean isSingleton() {
        return BeanScope.SINGLETON.name().equals(scope);
    }

    @Override
    public boolean isPrototype() {
        return BeanScope.PROTOTYPE.name().equals(scope);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultBeanDefinition that = (DefaultBeanDefinition) o;
        return Objects.equals(beanName, that.beanName) && Objects.equals(beanClass,
                that.beanClass) && Objects.equals(beanQualifiers,
                that.beanQualifiers) && Objects.equals(scope, that.scope) && Objects.equals(
                configBeanName, that.configBeanName) && Objects.equals(factoryMethod,
                that.factoryMethod) && Objects.equals(supplier, that.supplier) && Objects.equals(
                constructArguments, that.constructArguments) && Objects.equals(propertyValues,
                that.propertyValues) && Objects.equals(initMethodName,
                that.initMethodName) && Objects.equals(destroyMethodName, that.destroyMethodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanName, beanClass, beanQualifiers, scope, configBeanName, factoryMethod, supplier,
                constructArguments, propertyValues, initMethodName, destroyMethodName);
    }

    @Override
    public String toString() {
        return "DefaultBeanDefinition{" +
                "beanName='" + beanName + '\'' +
                ", beanClass=" + beanClass +
                ", beanQualifiers=" + beanQualifiers +
                ", scope='" + scope + '\'' +
                ", configBeanName='" + configBeanName + '\'' +
                ", factoryMethod='" + factoryMethod + '\'' +
                ", supplier=" + supplier +
                ", constructArguments=" + constructArguments +
                ", propertyValues=" + propertyValues +
                ", initMethodName='" + initMethodName + '\'' +
                ", destroyMethodName='" + destroyMethodName + '\'' +
                '}';
    }

    public String getBeanName() {
        return this.beanName;
    }

    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    public Set<Class<?>> getBeanQualifiers() {
        return this.beanQualifiers;
    }

    public String getScope() {
        return this.scope;
    }

    public String getConfigBeanName() {
        return this.configBeanName;
    }

    public String getFactoryMethod() {
        return this.factoryMethod;
    }

    public Supplier<?> getSupplier() {
        return this.supplier;
    }

    public ConstructArguments getConstructArguments() {
        return this.constructArguments;
    }

    public PropertyValues getPropertyValues() {
        return this.propertyValues;
    }

    public String getInitMethodName() {
        return this.initMethodName;
    }

    public String getDestroyMethodName() {
        return this.destroyMethodName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public void setBeanQualifiers(Set<Class<?>> beanQualifiers) {
        this.beanQualifiers = beanQualifiers;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setConfigBeanName(String configBeanName) {
        this.configBeanName = configBeanName;
    }

    public void setFactoryMethod(String factoryMethod) {
        this.factoryMethod = factoryMethod;
    }

    public void setSupplier(Supplier<?> supplier) {
        this.supplier = supplier;
    }

    public void setConstructArguments(ConstructArguments constructArguments) {
        this.constructArguments = constructArguments;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public static class DefaultBeanDefinitionBuilder {
        private String beanName;
        private Class<?> beanClass;
        private Set<Class<?>> beanQualifiers$value;
        private boolean beanQualifiers$set;
        private String scope;
        private String configBeanName;
        private String factoryMethod;
        private Supplier<?> supplier;
        private ConstructArguments constructArguments$value;
        private boolean constructArguments$set;
        private PropertyValues propertyValues$value;
        private boolean propertyValues$set;
        private String initMethodName;
        private String destroyMethodName;

        DefaultBeanDefinitionBuilder() {
        }

        public DefaultBeanDefinitionBuilder beanName(String beanName) {
            this.beanName = beanName;
            return this;
        }

        public DefaultBeanDefinitionBuilder beanClass(Class<?> beanClass) {
            this.beanClass = beanClass;
            return this;
        }

        public DefaultBeanDefinitionBuilder beanQualifiers(Set<Class<?>> beanQualifiers) {
            this.beanQualifiers$value = beanQualifiers;
            this.beanQualifiers$set = true;
            return this;
        }

        public DefaultBeanDefinitionBuilder scope(String scope) {
            this.scope = scope;
            return this;
        }

        public DefaultBeanDefinitionBuilder configBeanName(String configBeanName) {
            this.configBeanName = configBeanName;
            return this;
        }

        public DefaultBeanDefinitionBuilder factoryMethod(String factoryMethod) {
            this.factoryMethod = factoryMethod;
            return this;
        }

        public DefaultBeanDefinitionBuilder supplier(Supplier<?> supplier) {
            this.supplier = supplier;
            return this;
        }

        public DefaultBeanDefinitionBuilder constructArguments(
                ConstructArguments constructArguments) {
            this.constructArguments$value = constructArguments;
            this.constructArguments$set = true;
            return this;
        }

        public DefaultBeanDefinitionBuilder propertyValues(PropertyValues propertyValues) {
            this.propertyValues$value = propertyValues;
            this.propertyValues$set = true;
            return this;
        }

        public DefaultBeanDefinitionBuilder initMethodName(String initMethodName) {
            this.initMethodName = initMethodName;
            return this;
        }

        public DefaultBeanDefinitionBuilder destroyMethodName(String destroyMethodName) {
            this.destroyMethodName = destroyMethodName;
            return this;
        }

        public DefaultBeanDefinition build() {
            Set<Class<?>> beanQualifiers$value = this.beanQualifiers$value;
            if (!this.beanQualifiers$set) {
                beanQualifiers$value = DefaultBeanDefinition.$default$beanQualifiers();
            }
            ConstructArguments constructArguments$value = this.constructArguments$value;
            if (!this.constructArguments$set) {
                constructArguments$value = DefaultBeanDefinition.$default$constructArguments();
            }
            PropertyValues propertyValues$value = this.propertyValues$value;
            if (!this.propertyValues$set) {
                propertyValues$value = DefaultBeanDefinition.$default$propertyValues();
            }
            return new DefaultBeanDefinition(beanName, beanClass, beanQualifiers$value, scope, configBeanName,
                    factoryMethod, supplier, constructArguments$value, propertyValues$value, initMethodName,
                    destroyMethodName);
        }

        public String toString() {
            return "DefaultBeanDefinition.DefaultBeanDefinitionBuilder(beanName=" + this.beanName + ", beanClass=" + this.beanClass + ", beanQualifiers$value=" + this.beanQualifiers$value + ", scope=" + this.scope + ", configBeanName=" + this.configBeanName + ", factoryMethod=" + this.factoryMethod + ", supplier=" + this.supplier + ", constructArguments$value=" + this.constructArguments$value + ", propertyValues$value=" + this.propertyValues$value + ", initMethodName=" + this.initMethodName + ", destroyMethodName=" + this.destroyMethodName + ")";
        }
    }
}
