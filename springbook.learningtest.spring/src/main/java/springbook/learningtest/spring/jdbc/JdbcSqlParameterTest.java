package springbook.learningtest.spring.jdbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class JdbcSqlParameterTest {
	EmbeddedDatabase db;
	SimpleJdbcTemplate simpleJdbcTemplate; // or use NamedParameterJdbcTemplate
	
	@Before
	public void setUp() {
		db = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:springbook/learningtest/spring/jdbc/test-member.sql")
				.build();
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(db);
	}
	
	@Test
	public void mapSqlPrameterSource() {
		MapSqlParameterSource params = new MapSqlParameterSource()
											.addValue("id", 1)
											.addValue("name", "jsjg73")
											.addValue("point", 3.5);
		assertAdd(params);
	}
	@Test
	public void beanPropertySqlParameterSource() {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(new Member(2, "teneloper73", 3.8f));
		String query ="INSERT INTO MEMBER (ID, NAME, POINT) VALUES (:id, :name, :point)";
		assertAdd(params);
	}
	
	private void assertAdd(SqlParameterSource params) {
		int before = count();
		assertThat(
			this.simpleJdbcTemplate.update("INSERT INTO MEMBER (ID, NAME, POINT) VALUES (:id, :name, :point)", params),
			is(1)
		);
		int after = count();
		assertThat(after-before, is(1));
		
	}
	private int count() {
		return this.simpleJdbcTemplate.queryForInt("SELECT COUNT(*) FROM MEMBER");	
	}
	
	@After
	public void tearDown() {
		db.shutdown();
	}
	
	static class Member{
		int id;
		String name;
		float point;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public float getPoint() {
			return point;
		}
		public void setPoint(float point) {
			this.point = point;
		}
		public Member(int id, String name, float point) {
			this.id = id;
			this.name = name;
			this.point = point;
		}
	}
}
