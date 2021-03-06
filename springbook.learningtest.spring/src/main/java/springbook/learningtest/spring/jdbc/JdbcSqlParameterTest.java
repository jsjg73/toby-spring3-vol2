package springbook.learningtest.spring.jdbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.util.Assert;

import springbook.learningtest.spring.embeddeddb.MemberEmbeddedDB;

public class JdbcSqlParameterTest {
	EmbeddedDatabase db;
	SimpleJdbcTemplate simpleJdbcTemplate; // or use NamedParameterJdbcTemplate
	final String INSERT_WITH_NAME_PLACEHOLDER = "INSERT INTO MEMBER (ID, NAME, POINT) VALUES (:id, :name, :point)";
	final String INSERT_WITH_QUESTION_PLACEHOLDER = "INSERT INTO MEMBER (ID, NAME, POINT) VALUES (?, ?, ?)";
	
	@Before
	public void setUp() {
		db = MemberEmbeddedDB.getEmbeddedDatabase();
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(db);
		assertThat(count(), is(2));
	}
	
	@Test
	public void incorrectInsert() {
		Member m = new Member(3, "dobule", 2.3);
		SqlParameterSource mParams = new BeanPropertySqlParameterSource(m);
		this.simpleJdbcTemplate.update(INSERT_WITH_NAME_PLACEHOLDER, mParams);
		
		double point = this.simpleJdbcTemplate.queryForObject("SELECT POINT FROM MEMBER WHERE ID = :id", Double.class, mParams);
		System.out.println(point);
	}
	
	@Test
	public void batchInsert() {
		Member m1 = new Member(1, "jsjg73", 1.5);
		Member m2 = new Member(2, "teneloper", 3.5);
		
		int[] affected = this.simpleJdbcTemplate.batchUpdate(INSERT_WITH_NAME_PLACEHOLDER,
				new SqlParameterSource[] {
						new BeanPropertySqlParameterSource(m1),
						new BeanPropertySqlParameterSource(m2)
				});
		assertThat(affected[0], is(1));
		assertThat(affected[1], is(1));
	}
	
	@Test
	public void find() {
		List<Member> members = this.simpleJdbcTemplate.query("SELECT * FROM MEMBER", new BeanPropertyRowMapper<>(Member.class));
		assertThat(members.size(), is(2));
	}
	
	@Test(expected = IncorrectResultSizeDataAccessException.class)
	public void wrongResultSize() {
		this.simpleJdbcTemplate.queryForInt("SELECT ID FROM MEMBER");
	}
	@Test(expected = IncorrectResultSetColumnCountException.class)
	public void wrongResultSetColumCount() {
		this.simpleJdbcTemplate.queryForInt("SELECT ID, POINT FROM MEMBER WHERE ID = 990");
	}
	@Test
	public void SqlSearchMethod() {
		Member m1 = new Member(1, "jsjg73", 1.5);
		Member m2 = new Member(2, "teneloper", 3.5);
		
		SqlParameterSource m1Params = new BeanPropertySqlParameterSource(m1);
		SqlParameterSource m2Params = new BeanPropertySqlParameterSource(m2);
		
		
		this.simpleJdbcTemplate.update(INSERT_WITH_NAME_PLACEHOLDER, new BeanPropertySqlParameterSource(m1));
		this.simpleJdbcTemplate.update(INSERT_WITH_NAME_PLACEHOLDER, new BeanPropertySqlParameterSource(m2));
		
		double point = this.simpleJdbcTemplate.queryForObject("SELECT POINT FROM MEMBER WHERE ID = :id", Double.class,m1Params);
		int id = this.simpleJdbcTemplate.queryForInt("SELECT ID FROM MEMBER WHERE ID = :id", m1Params);
		String name = this.simpleJdbcTemplate.queryForObject("SELECT name FROM MEMBER WHERE ID = :id", String.class, m1Params);
		assertThat(id, is(m1.id));
		assertThat(name, is(m1.name));
		assertThat(point, is(m1.point));
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
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(new Member(2, "teneloper73", 3.8));
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
}
