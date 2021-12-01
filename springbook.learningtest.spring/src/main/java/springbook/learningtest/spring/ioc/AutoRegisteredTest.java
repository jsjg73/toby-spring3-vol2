package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ResourceLoader;

public class AutoRegisteredTest {
	@Test
	public void autoRegitster() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(BeanA.class);
		
		BeanA bean = ac.getBean(BeanA.class);
		
		assertThat(bean.applicationContext, sameInstance(ac));
	}
	
	static class BeanA {
		@Autowired ApplicationContext applicationContext;
		@Autowired ResourceLoader resourceLoader;
	}
}
