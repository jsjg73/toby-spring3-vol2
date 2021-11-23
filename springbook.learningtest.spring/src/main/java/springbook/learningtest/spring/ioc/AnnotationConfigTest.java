package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
	
	interface Product{	public boolean available();	}
	@Qualifier("product")
	static class ProductA implements Product{ public boolean available() {return true;}}
	static class ProductB implements Product{ public boolean available() {return false;}}
	
	static class Client{
		@Autowired Set<Product> beanBSet;
		@Autowired List<Product> beanBList;
		@Autowired Map<String, Product> beanBMap;
		@Autowired Product[] beanBArray;
		@Autowired Collection<Product> beanBCollection;
		
		@Autowired @Qualifier("product") Product product;
	}
	
	@Test
	public void autowiredCollections() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Client.class, ProductA.class, ProductB.class);
		
		Client c = ac.getBean(Client.class);
		assertThat(c.beanBArray.length, is(2));
		assertThat(c.beanBCollection.size(), is(2));
		assertThat(c.beanBList.size(), is(2));
		assertThat(c.beanBMap.size(), is(2));
		assertThat(c.beanBMap.entrySet().size(), is(2));
		assertThat(c.beanBSet.size(), is(2));
	}
	
	@Test
	public void qualifier() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Client.class, ProductA.class, ProductB.class);
		assertTrue(ac.getBean(Client.class).product.available());
	}
}
