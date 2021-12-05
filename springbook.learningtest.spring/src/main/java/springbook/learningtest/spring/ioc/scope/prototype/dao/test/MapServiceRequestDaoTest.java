package springbook.learningtest.spring.ioc.scope.prototype.dao.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springbook.learningtest.spring.ioc.scope.prototype.dao.MapServiceRequestDao;
import springbook.learningtest.spring.ioc.scope.prototype.dto.ServiceRequest;

public class MapServiceRequestDaoTest {
	private ServiceRequest input;
	
	@Before
	public void setUp() {
		input = new ServiceRequest("001", "Harry¿« A/S ø‰√ª");
	}
	
	@Test
	public void addServiceRequest() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
		MapServiceRequestDao dao = ac.getBean("mapSrvReqDao", MapServiceRequestDao.class);
		assertThat(dao.count(), is(0));

		dao.add(input);
		assertThat(dao.count(), is(1));
	}

	@Configuration
	static class Config {
		@Bean
		public MapServiceRequestDao mapSrvReqDao() {
			MapServiceRequestDao dao = new MapServiceRequestDao();
			return dao;
		}
	}
}
