package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import springbook.learningtest.spring.ioc.scan.Hello;

public class ContextTagTest {
	final String basepath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()))+"/";
	
	@Test(expected = NullPointerException.class)
	public void omissionContextTag() {
		ApplicationContext ac = new GenericXmlApplicationContext(basepath + "omissionContextTag.xml");
		SimpleConfig sc = ac.getBean(SimpleConfig.class);
		assertThat(sc, notNullValue());
		sc.hello.sayHello();
	}
	
	@Test
	public void includingContextTag() {
		ApplicationContext ac = new GenericXmlApplicationContext(basepath + "inclusionContextTag.xml");
		SimpleConfig sc = ac.getBean(SimpleConfig.class);
		assertThat(sc, notNullValue());
		assertThat(sc.hello, notNullValue());
		sc.hello.sayHello();
		
	}
	
	@Configuration
	public static class SimpleConfig {
		@Autowired(required = false) Hello hello;
		
		@Bean
		public Hello hello() {
			return new Hello();
		}
	}
}
