package springbook.learningtest.spring.embeddeddb;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

public class EmbeddedDbTest {
	
	@Test
	public void gracefulExit() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
		SimpleJdbcTemplate template = new SimpleJdbcTemplate(ac.getBean(EmbeddedDatabase.class));
		int count = template.queryForInt("select count(*) from member");
		assertThat(count, is(2));
		ac.getBean(EmbeddedDatabase.class).shutdown();
	}
	
	static class MyEmbeddedDatabase implements EmbeddedDatabase{

		private EmbeddedDatabase db = MemberEmbeddedDB.getEmbeddedDatabase();
		
		@Override
		public Connection getConnection() throws SQLException {
			return db.getConnection();
		}

		@Override
		public Connection getConnection(String username, String password) throws SQLException {
			return db.getConnection(username, password);
		}

		@Override
		public PrintWriter getLogWriter() throws SQLException {
			return db.getLogWriter();
		}

		@Override
		public int getLoginTimeout() throws SQLException {
			// TODO Auto-generated method stub
			return db.getLoginTimeout();
		}

		@Override
		public Logger getParentLogger() throws SQLFeatureNotSupportedException {
			// TODO Auto-generated method stub
			return db.getParentLogger();
		}

		@Override
		public void setLogWriter(PrintWriter out) throws SQLException {
			db.setLogWriter(out);
		}

		@Override
		public void setLoginTimeout(int seconds) throws SQLException {
			db.setLoginTimeout(seconds);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return db.isWrapperFor(iface);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return db.unwrap(iface);
		}

		@Override
		public void shutdown() {
			System.out.println("arrive - teveloper73");
			db.shutdown();
		}
	}

	@Configuration
	static class Config{
		@Bean(destroyMethod = "shutdown")
		public EmbeddedDatabase db() {
			return new MyEmbeddedDatabase();
		}
	}
}
