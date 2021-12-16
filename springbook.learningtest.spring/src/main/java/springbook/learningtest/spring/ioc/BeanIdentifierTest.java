package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import springbook.learningtest.spring.ioc.bean.Hello;

public class BeanIdentifierTest {
	@Test
	public void multipleNamesBean() {
		ApplicationContext ac = new GenericXmlApplicationContext(new ClassPathResource("identifier.xml",getClass()));
		Hello hi = ac.getBean("hi",Hello.class);
		Hello hello = ac.getBean("/hello",Hello.class);
		Hello 반가워 = ac.getBean("반가워",Hello.class);
		
		assertThat(hi, notNullValue());
		assertThat(hello, notNullValue());
		assertThat(반가워, notNullValue());
		
		assertThat(hi, sameInstance(hello));
		assertThat(hi, sameInstance(반가워));
		
		assertThat(ac.getBean("hello_alias",Hello.class), notNullValue());
	}
	
	
}
