package springbook.learningtest.spring.jdbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class SimpleJdbcInsertTest {
	EmbeddedDatabase db;
	SimpleJdbcTemplate simpleJdbcTemplate;
	Member m1,m2;
	@Before
	public void setUp() {
		db = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:springbook/learningtest/spring/jdbc/test-member.sql")
				.addScript("classpath:springbook/learningtest/spring/jdbc/test-data.sql")
				.build();
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(db);
		m1 = new Member(1, "jsjg73", 1.5);
		m2 = new Member(2, "teneloper", 3.5);
	}
	@Test
	public void execute() {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(db).withTableName("member"); 
		insert.execute(new BeanPropertySqlParameterSource(m1));
		
		SqlParameterSource m1Params = new BeanPropertySqlParameterSource(m1);
		
		double point = this.simpleJdbcTemplate.queryForObject("SELECT POINT FROM MEMBER WHERE ID = :id", Double.class,m1Params);
		int id = this.simpleJdbcTemplate.queryForInt("SELECT ID FROM MEMBER WHERE ID = :id", m1Params);
		String name = this.simpleJdbcTemplate.queryForObject("SELECT name FROM MEMBER WHERE ID = :id", String.class, m1Params);
		assertThat(id, is(m1.id));
		assertThat(name, is(m1.name));
		assertThat(point, is(m1.point));
	}
	
	@Test
	public void executeAndReturnKey() {
		SimpleJdbcInsert registerInsert = new SimpleJdbcInsert(db)
												.withTableName("register")
												.usingGeneratedKeyColumns("id");
		int key = registerInsert.executeAndReturnKey(new MapSqlParameterSource("name","Spring")).intValue();
		assertThat(key, is(0));
	}
	
	@After
	public void tearDown() {
		db.shutdown();
	}
}
