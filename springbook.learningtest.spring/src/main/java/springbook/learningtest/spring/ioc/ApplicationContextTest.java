package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;

import springbook.learningtest.spring.ioc.bean.Hello;

public class ApplicationContextTest {
	
	@Test
	public void registerBean(){
		StaticApplicationContext ac = new StaticApplicationContext();
		ac.registerSingleton("hello1", Hello.class);
		
		Hello hello1 = (Hello) ac.getBean("hello1");
		assertThat(hello1, is(notNullValue()) );
		
		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");
		ac.registerBeanDefinition("hello2", helloDef);
		
	}
}
