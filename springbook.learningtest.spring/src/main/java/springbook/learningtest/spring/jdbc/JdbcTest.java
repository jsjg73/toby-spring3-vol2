package springbook.learningtest.spring.jdbc;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class JdbcTest {
	
	@Test
	public void simpleJdbcTemplateTest() {
		ApplicationContext ac = new GenericXmlApplicationContext("classpath:test-embeddedDb-context.xml");
		assertThat(ac.getBean(DataSource.class), notNullValue());
		
		MemberDao dao = ac.getBean(MemberDao.class);
		assertThat(dao.simpleJdbcTemplate, notNullValue());
	}
	
	static class MemberDao{
		SimpleJdbcTemplate simpleJdbcTemplate;
		
		@Autowired
		public void setDataSource(DataSource dataSource) {
			this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource); 
		}
	}
}
