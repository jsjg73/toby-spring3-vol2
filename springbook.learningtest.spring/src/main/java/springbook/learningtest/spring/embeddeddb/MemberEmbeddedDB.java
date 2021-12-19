package springbook.learningtest.spring.embeddeddb;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class MemberEmbeddedDB {
	public static EmbeddedDatabase getEmbeddedDatabase() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:springbook/learningtest/spring/embeddeddb/test-member.sql")
				.addScript("classpath:springbook/learningtest/spring/embeddeddb/test-data.sql")
				.build();
	}
}
