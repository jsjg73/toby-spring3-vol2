package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import springbook.learningtest.spring.ioc.bean.AnnotatedHello;
import springbook.learningtest.spring.ioc.bean.Hello;
import springbook.learningtest.spring.ioc.bean.Printer;
import springbook.learningtest.spring.ioc.bean.StringPrinter;

public class ApplicationContextTest {
	private String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()))+"/";
	
	@Test
	public void registerBean(){
		StaticApplicationContext ac = new StaticApplicationContext();
		ac.registerSingleton("hello1", Hello.class);
		
		Hello hello1 = (Hello) ac.getBean("hello1");
		assertThat(hello1, is(notNullValue()) );
		
		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");
		ac.registerBeanDefinition("hello2", helloDef);
		
		Hello hello2 = (Hello) ac.getBean("hello2");
		assertThat(hello2.sayHello(), is("Hello Spring"));

		assertThat(hello1, is(not(hello2)));
		
		assertThat(ac.getBeanFactory().getBeanDefinitionCount(), is(2));
	}
	
	@Test
	public void registerBeanWithDependency() {
		StaticApplicationContext ac = new StaticApplicationContext();
		
		ac.registerSingleton("printer", StringPrinter.class);
		
		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");
		helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));
		
		ac.registerBeanDefinition("hello", helloDef);
		
		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();
		
		assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
	}
	
	@Test
	public void genericApplicationContext() {
		GenericApplicationContext ac = new GenericApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
		reader.loadBeanDefinitions("springbook/learningtest/spring/ioc/genericApplicationContext.xml");
		ac.refresh();
		
		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();
		
		assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
	}
	
	@Test
	public void genericXmlApplicationContext() {
		GenericXmlApplicationContext ac = new GenericXmlApplicationContext(
				"springbook/learningtest/spring/ioc/genericApplicationContext.xml");
		
		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();
		
		assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
	}
	
	@Test
	public void contextHierachy() {
		ApplicationContext parent = new GenericXmlApplicationContext(basePath+"parentContext.xml");
		
		GenericApplicationContext child = new GenericApplicationContext(parent);

		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
		reader.loadBeanDefinitions(basePath+"childContext.xml");
		child.refresh();
		
		Printer printer = child.getBean("printer", Printer.class);
		assertThat(printer, is(notNullValue()));
		
		Hello hello = child.getBean("hello", Hello.class);
		assertThat(hello, is(notNullValue()));
		
		hello.print();
		assertThat(printer.toString(), is("Hello Child"));
	}
	
	@Test
	public void simpleBeanScanning() {
		ApplicationContext ac = new AnnotationConfigApplicationContext("springbook.learningtest.spring.ioc.bean");
		
		AnnotatedHello hello = ac.getBean(AnnotatedHello.class);
		
		assertThat(hello, is(notNullValue()));
	}
	
	@Test
	public void scanningBeanWithXML() {
		GenericXmlApplicationContext ac = new GenericXmlApplicationContext(basePath+"filteredScanningContext.xml");
		
		assertThat(ac.getBean(AnnotatedHello.class), is(notNullValue()));
		assertThat(ac.getBean(Hello.class), is(notNullValue()));
	}
	
	@Test(expected = NoSuchBeanDefinitionException.class)
	public void failScanningBeanWithWorngBasePackage() {
		GenericXmlApplicationContext ac = new GenericXmlApplicationContext(basePath+"filteredScanningContext.xml");
		
		assertThat(ac.getBean(UndetecedHello.class), nullValue());
	}
	
	@Test
	public void configurationBean() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
		assertThat(ac.getBean("annotatedHello", AnnotatedHello.class), is(notNullValue()));
	}
	
	@Test
	public void configurationMethosReturnSameInstance() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
		
		AnnotatedHelloConfig config = ac.getBean(AnnotatedHelloConfig.class);
		assertThat(config, is(not(nullValue())));
		
		assertThat(config.annotatedHello(), is(sameInstance(config.annotatedHello())));
	}
}
