package springbook.learningtest.spring.ioc._import;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

public class ImportTest {
	
	@Test
	public void importResource() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigWithImportResource.class);
		assertThat(ac.getBean(ThridPartyAPI.class), notNullValue());
	}
	
	@Configuration
	@ImportResource("springbook/learningtest/spring/ioc/_import/thirdParty-context.xml")
	static class AppConfigWithImportResource {
		
	}
	static class ThridPartyAPI {}
}
