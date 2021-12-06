package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class DependencyLookUpTest {

	static abstract class ControllerA {
		abstract public PrototypeBean getPrototypeBean();
	}
	
	static class PrototypeBean{String name = "hello";}
	
	@Test
	public void DLbyMethodInjection() {
		ApplicationContext ac = new GenericXmlApplicationContext(new ClassPathResource("MethodInjection.xml",getClass()));
		ControllerA ctl = ac.getBean(ControllerA.class);
		
		PrototypeBean bean1 = ctl.getPrototypeBean();
		PrototypeBean bean2 = ctl.getPrototypeBean();
		
		assertThat(bean1.name, is("hello"));
		assertThat(bean1, is(not(bean2)));
	}
}
