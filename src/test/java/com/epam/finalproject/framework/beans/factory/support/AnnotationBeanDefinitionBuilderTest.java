package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.framework.beans.factory.config.BeanDefinition;
import com.epam.finalproject.framework.beans.factory.support.target.TestTargetObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationBeanDefinitionBuilderTest {

    @Test
    void buildSubBeans() {
    }

    @Test
    void buildShouldReturnCorrectDefinitionWhenBuildSimpleClass() {
        BeanDefinition definition = AnnotationBeanDefinitionBuilder.build(TestTargetObject.class);
        assertEquals("testObj",definition.getBeanName());
        assertEquals("SINGLETON",definition.getScope());
        assertEquals(TestTargetObject.class,definition.getBeanClass());
    }

    @Test
    void buildShouldReturnDefinitionForSuccessBuildWhenBuildSimpleClass() {
        ManualConfigurableBeanFactory factory = new ManualConfigurableBeanFactory();
        factory.registerBean(AnnotationBeanDefinitionBuilder.build(TestTargetObject.class));
        factory.constructSingletons();
        TestTargetObject object = factory.getBean("testObj",TestTargetObject.class);
        assertEquals(5, object.getId());
        assertEquals("Five", object.getName());
        assertEquals(55, object.getSetterField());
    }
}