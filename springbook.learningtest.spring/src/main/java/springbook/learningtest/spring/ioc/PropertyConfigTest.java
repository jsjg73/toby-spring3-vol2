package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import springbook.learningtest.spring.ioc.property.ExternalResource;
import springbook.learningtest.spring.ioc.property.TagHello;

public class PropertyConfigTest {
	private String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()))+"/";
	
	@Test
	public void value() {
		GenericApplicationContext ac = new GenericXmlApplicationContext(basePath+"propertyContext.xml");
		TagHello hello = ac.getBean(TagHello.class);
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
	
	@Test
	public void valueInjection() { // sample code
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
				BeanSp.class, ConfigSP.class, DatabasePropertyPlaceHolder.class);
		BeanSp bean = ac.getBean(BeanSp.class);
		assertThat(bean.os, is("Windows 10"));
		assertThat(bean.username, is("jsjg73"));
		
		assertThat(bean.hello.username, is("jsjg73"));
	}
	static class ConfigSP{
		@Bean
		public Hello hello(@Value("${database.username}") String username) {
			Hello hello = new Hello();
			hello.username = username;
			return hello;
		}
	}
	static class BeanSp{
		@Value("#{systemProperties['os.name']}") String os;
		@Value("${database.username}") String username;
		@Autowired Hello hello;
	}
	static class Hello{ String username; }
	
	static class DatabasePropertyPlaceHolder extends PropertyPlaceholderConfigurer{
		public DatabasePropertyPlaceHolder() {
			this.setLocation(new ClassPathResource("database.properties", getClass()));
		}
	}
	
	
	@Test
	public void importResource() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ConfigA.class);
		
		BeanSp bean = ac.getBean(BeanSp.class);
		
		assertThat(bean.os, is("Windows 10"));
		assertThat(bean.username, is("jsjg73"));
	}
	@ImportResource("classpath:springbook/learningtest/spring/ioc/propertiesBeanFactoryPostProcessor.xml")
	@Configuration
	static class ConfigA{
		@Bean
		public BeanSp beanSp() {
			return new BeanSp();
		}
		@Bean
		public Hello hello() {
			return new Hello();
		}
	}
	
	@Test
	public void collectionInject() {
		ApplicationContext ac = new GenericXmlApplicationContext(new ClassPathResource("collection.xml", getClass()));
		BeanC bean = ac.getBean(BeanC.class);
		
		assertThat(bean.nameList.size(), is(3));
		assertThat(bean.nameList.get(0), is("Spring"));
		assertThat(bean.nameList.get(1), is("IoC"));
		assertThat(bean.nameList.get(2), is("DI"));
		
		assertThat(bean.nameSet.size(), is(3));
		
		assertThat(bean.ages.get("Kim"), is(30));
		assertThat(bean.ages.get("Lee"), is(35));
		assertThat(bean.ages.get("Ahn"), is(40));
		
		assertThat((String)bean.settings.get("username"), is("Spring"));
		assertThat((String)bean.settings.get("password"), is("Book"));
		
		assertThat(bean.beans.size(), is(2));
	}
	
	static class BeanC{
		List<String> nameList;
		Set<String> nameSet;
		Map<String, Integer> ages;
		Properties settings;
		List beans;
		public void setNameList(List<String> nameList) {
			this.nameList = nameList;
		}
		public void setNameSet(Set<String> nameSet) {
			this.nameSet = nameSet;
		}
		public void setAges(Map<String, Integer> ages) {
			this.ages = ages;
		}
		public void setSettings(Properties settings) {
			this.settings = settings;
		}
		public void setBeans(List beans) {
			this.beans = beans;
		}
	}
}
