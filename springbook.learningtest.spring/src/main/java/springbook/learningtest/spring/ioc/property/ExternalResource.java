package springbook.learningtest.spring.ioc.property;

import org.springframework.beans.factory.annotation.Value;

public class ExternalResource {
	@Value("#{systemProperties['os.name']}")
	String os;
	@Value("${database.username}")
	String username;
	
	public String print() {
		return os+" "+username;
	}

}
