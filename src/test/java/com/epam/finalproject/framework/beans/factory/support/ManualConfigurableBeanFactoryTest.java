package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.beans.factory.config.*;
import com.epam.finalproject.framework.beans.factory.exception.BeansException;
import com.epam.finalproject.framework.beans.factory.exception.ExistBeanDefinitionException;
import com.epam.finalproject.framework.beans.factory.exception.NoSuchBeanDefinitionException;
import com.epam.finalproject.framework.beans.factory.support.target.AloneTestTargetObject;
import com.epam.finalproject.framework.beans.factory.support.target.TestTargetObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ManualConfigurableBeanFactoryTest {

    ManualConfigurableBeanFactory factory;

    TestTargetObject suppliedObject;

    @BeforeEach
    void setUp() {
        factory = new ManualConfigurableBeanFactory();
        suppliedObject = new TestTargetObject(1, "1");
        factory.registerBean(DefaultBeanDefinition.builder()
                .beanName("targetSuppliedObject")
                .scope(BeanScope.SINGLETON.name())
                .beanClass(TestTargetObject.class)
                .beanQualifiers(Set.of(Component.class))
                .supplier(() -> suppliedObject)
                .build());
        factory.registerBean(DefaultBeanDefinition.builder()
                .beanName("alone")
                .scope(BeanScope.SINGLETON.name())
                .beanClass(AloneTestTargetObject.class)
                .supplier(() -> new AloneTestTargetObject(0, "alone"))
                .build());
        Set<PropertyValueHolder> holders = Set.of(
                new PropertyValueHolder("setterField", InjectPropertyRule.SETTER_ONLY, false, 10),
                new PropertyValueHolder("setInjectField", true, "10"));
        ConstructArguments constructArguments = new ConstructArguments();
        constructArguments.add(new ConstructValueHolder(Integer.class,10));
        constructArguments.add(new ConstructValueHolder(String.class,"10"));
        factory.registerBean(DefaultBeanDefinition.builder()
                .beanName("targetCreatedObject")
                .scope(BeanScope.SINGLETON.name())
                .beanClass(TestTargetObject.class)
                .propertyValues(new PropertyValues(holders))
                .constructArguments(constructArguments)
                .build());
        factory.constructSingletons();
    }

    @Test
    void getBeanDefinitionShouldReturnCorrectDefinitionWhenByName() {
        BeanDefinition definition = factory.getBeanDefinition("targetSuppliedObject");
        assertEquals("targetSuppliedObject", definition.getBeanName());
        assertEquals(TestTargetObject.class, definition.getBeanClass());
        assertEquals(BeanScope.SINGLETON.name(), definition.getScope());
        assertNotNull(definition.getSupplier());
    }

    @Test
    void getBeanDefinitionShouldThrowNoSuchBeanDefinitionExceptionWhenNotRegistered() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> factory.getBeanDefinition("nonRegistered"));

    }

    @Test
    void containsBeanShouldReturnTrueWhenByNameRegisteredDefinition() {
        assertTrue(factory.containsBean("targetSuppliedObject"));
    }

    @Test
    void containsBeanShouldReturnFalseWhenByNameNotRegisteredDefinition() {
        assertFalse(factory.containsBean("nonRegistered"));
    }

    @Test
    void isSingletonShouldReturnTrueWhenByScopeSingleton() {
        assertTrue(factory.isSingleton("targetSuppliedObject"));
    }

    @Test
    void isPrototypeShouldReturnFalseWhenScopeSingleton() {
        assertFalse(factory.isPrototype("targetSuppliedObject"));
    }

    @Test
    void registerBeanShouldThrowExistBeanDefinitionExceptionWhenByName() {
        assertThrows(ExistBeanDefinitionException.class, () -> factory.registerBean(DefaultBeanDefinition.builder()
                .beanName("targetSuppliedObject")
                .scope(BeanScope.SINGLETON.name())
                .beanClass(TestTargetObject.class)
                .supplier(() -> new TestTargetObject(1, "1"))
                .build()));
    }

    @Test
    void registerBeanPostProcessorShouldRegisterWithoutExceptionWhenByName() {
        assertDoesNotThrow(() -> factory.registerBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
            }
        }));
    }

    @Test
    void constructSingletonsShouldConstructWithoutExceptionWhenEmpty() {
        assertDoesNotThrow(() -> new ManualConfigurableBeanFactory().constructSingletons());
    }

    @Test
    void getBeanDefinitionsShouldReturnListContainsDefinitionWhenByClass() {
        List<BeanDefinition> definitions = factory.getBeanDefinitions(TestTargetObject.class);
        assertFalse(definitions.isEmpty());
        assertEquals(1, definitions.stream()
                .filter(beanDefinition -> beanDefinition.getBeanName().equals("targetSuppliedObject"))
                .count());
    }

    @Test
    void getBeanDefinitionsShouldReturnListContainsDefinitionWhenByAnnotation() {
        List<BeanDefinition> definitions = factory.getBeanDefinitions(Set.of(Component.class));
        assertFalse(definitions.isEmpty());
        assertEquals(1, definitions.stream()
                .filter(beanDefinition -> beanDefinition.getBeanName().equals("targetSuppliedObject"))
                .count());
    }

    @Test
    void getBeanDefinitionsShouldReturnListContainsDefinitionWhenByClassAndAnnotation() {
        List<BeanDefinition> definitions = factory.getBeanDefinitions(TestTargetObject.class, Set.of(Component.class));
        assertFalse(definitions.isEmpty());
        assertEquals(1, definitions.stream()
                .filter(beanDefinition -> beanDefinition.getBeanName().equals("targetSuppliedObject"))
                .count());
    }

    @Test
    void getBeanShouldReturnSameBeanWhenSingleton() {
        assertSame(factory.getBean("targetSuppliedObject"),
                factory.getBean("targetSuppliedObject", TestTargetObject.class));
    }

    @Test
    void getBeanShouldReturnCorrectBeanWhenGetByName() {
        Object result = factory.getBean("targetSuppliedObject");
        assertNotNull(result);
        assertTrue(result instanceof TestTargetObject);
        assertEquals(1, ((TestTargetObject) result).getId());
    }

    @Test
    void getBeanShouldReturnCorrectBeanWhenGetByNameAndClass() {
        TestTargetObject result = factory.getBean("targetSuppliedObject", TestTargetObject.class);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("1", result.getName());
    }

    @Test
    void getBeanShouldReturnCorrectBeanWhenGetByClass() {
        AloneTestTargetObject result = factory.getBean(AloneTestTargetObject.class);
        assertNotNull(result);
        assertEquals(0, result.getId());
        assertEquals("alone", result.getName());
    }

    @Test
    void getBeansShouldReturnListWithEqualBeanWhenGetByClass() {
        List<TestTargetObject> beans = factory.getBeans(TestTargetObject.class);
        assertEquals(1, beans.stream().filter(testTargetObject -> testTargetObject.equals(suppliedObject)).count());
    }

    @Test
    void getBeansShouldReturnListWithEqualBeanWhenGetByAnnotations() {
        List<TestTargetObject> beans = factory.getBeans(Set.of(Component.class));
        assertEquals(1, beans.stream().filter(testTargetObject -> testTargetObject.equals(suppliedObject)).count());
    }

    @Test
    void getBeansShouldReturnListWithEqualBeanWhenGetByClassAndAnnotations() {
        List<TestTargetObject> beans = factory.getBeans(TestTargetObject.class, Set.of(Component.class));
        assertEquals(1, beans.stream().filter(testTargetObject -> testTargetObject.equals(suppliedObject)).count());
    }

    @Test
    void getSingletonShouldReturnEqualBeanWhenGetByName() {
        assertEquals(suppliedObject, factory.getSingleton("targetSuppliedObject"));
    }
}