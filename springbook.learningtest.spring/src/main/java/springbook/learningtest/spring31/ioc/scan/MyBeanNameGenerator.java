package springbook.learningtest.spring31.ioc.scan;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class MyBeanNameGenerator extends AnnotationBeanNameGenerator{
	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		return super.generateBeanName(definition, registry)+"_MyBeanNameGenerator";
	}
}