package springbook.learningtest.spring31.ioc.scan;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springbook.learningtest.spring31.ioc.scan.other.DaoMarker;

public class ComponentScanTest {
	@Test
	public void nameGenerator() {
		// MyBeanNameGenerator는 빈 이름 뒤에 '_jsjg73'을 추가하는 역할.
		ApplicationContext ac = new AnnotationConfigApplicationContext(ConfigCustomNameGenerator.class, ConfigDefaultNameGenerator.class);
		String[] beanNames = ac.getBeanNamesForType(ServiceCustom.class);
		
		assertThat(ac.getBean("serviceCustom_MyBeanNameGenerator"), notNullValue());
		
		assertThat(ac.getBean("serviceDefault"), notNullValue());
	}
	
	@Configuration
	@ComponentScan(basePackageClasses = ServiceMarker.class, nameGenerator = MyBeanNameGenerator.class)
	public static class ConfigCustomNameGenerator{
	}
	@Configuration
	@ComponentScan(basePackageClasses = DaoMarker.class)
	public static class ConfigDefaultNameGenerator{
	}
}
