package springbook.learningtest.spring.ioc.scan;

import javax.annotation.PostConstruct;

public class Hello {
	@PostConstruct
	public void init() {
		System.out.println("Init");
	}
	
	public void sayHello() {}
}
