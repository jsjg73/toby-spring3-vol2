package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import javax.inject.Provider;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

public class DependencyLookUpTest {

	static abstract class ControllerA {
		abstract public PrototypeBean getPrototypeBean();
	}
	
	@Component()
	@Scope("prototype")
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
	
	@Test
	public void DLbyProvider() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(Client.class, PrototypeBean.class);
		Client client = ac.getBean(Client.class);
		
		PrototypeBean bean1 = client.get();
		PrototypeBean bean2 = client.get();
		
		assertThat(bean1.name, is("hello"));
		assertThat(bean1, is(not(bean2)));
		
	}
	static class Client{
		@Autowired Provider<PrototypeBean> provider;
		
		public PrototypeBean get() {
			return provider.get();
		}
	}
}
