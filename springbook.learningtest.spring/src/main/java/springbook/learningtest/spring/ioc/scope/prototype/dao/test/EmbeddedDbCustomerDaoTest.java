package springbook.learningtest.spring.ioc.scope.prototype.dao.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import springbook.learningtest.spring.ioc.scope.prototype.dao.CustomerDao;
import springbook.learningtest.spring.ioc.scope.prototype.dao.EmbeddedDbCustomerDao;

public class EmbeddedDbCustomerDaoTest extends CustomerDaoTest {
	@After
	public void tearDown() {
		ac.getBean(EmbeddedDatabase.class).shutdown();
	}

	@Override
	protected void initiateContext() {
		ac = new AnnotationConfigApplicationContext(Config.class);
		dao = ac.getBean(CustomerDao.class);
		assertThat(dao, notNullValue());
	}

	@Configuration
	static class Config {
		@Bean
		public EmbeddedDbCustomerDao embeddedDbCustomerDao() {
			EmbeddedDbCustomerDao dao = new EmbeddedDbCustomerDao();
			dao.setDataSource(datasource());
			return dao;
		}

		@Bean
		public EmbeddedDatabase datasource() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
					.addScript("classpath:springbook/learningtest/spring/ioc/scope/prototype/dao/test/customer-schema.sql")
					.addScript("classpath:springbook/learningtest/spring/ioc/scope/prototype/dao/test/customer-data.sql")
					.build();
		}
	}

}
