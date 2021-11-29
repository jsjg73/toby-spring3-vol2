package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import springbook.learningtest.spring.ioc.property.ExternalResource;
import springbook.learningtest.spring.ioc.property.Hello;

public class PropertyConfigTest {
	private String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()))+"/";
	
	@Test
	public void value() {
		GenericApplicationContext ac = new GenericXmlApplicationContext(basePath+"propertyContext.xml");
		Hello hello = ac.getBean(Hello.class);
		hello.print();
		
		assertThat(ac.getBean("printer").toString(), is("Hello Everyone"));
	}
	
	@Test
	public void valueForExternalResource() {
		GenericApplicationContext ac = new GenericXmlApplicationContext(basePath+"externalResourceContext.xml");
		ExternalResource er = ac.getBean(ExternalResource.class);
		
		String os = ac.getBean(Environment.class).getProperty("os.name");
		
		assertThat(er.print(), is(os+" jsjg73"));
	}
}
