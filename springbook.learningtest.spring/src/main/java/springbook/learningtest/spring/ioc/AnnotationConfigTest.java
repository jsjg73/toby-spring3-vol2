package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import springbook.learningtest.spring.ioc.resource.Hello;

public class AnnotationConfigTest {
	private String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()))+"/";
	
	@Test
	public void resource() {
		GenericXmlApplicationContext ac = new GenericXmlApplicationContext(basePath+"resource.xml");
		
		Hello hello = ac.getBean(Hello.class);
		hello.print();
		
		assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
	}
}
