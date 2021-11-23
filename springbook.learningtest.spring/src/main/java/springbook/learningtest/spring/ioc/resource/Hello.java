package springbook.learningtest.spring.ioc.resource;

import javax.annotation.Resource;

public class Hello {
	private String name;
	private Printer printer;
	
	public void setName(String name) {
		this.name=name;
	}
	
	@Resource
	public void setPrinter(Printer printer) {
		this.printer=printer;
	}
	
	public void print() {
		printer.print(sayHello());
	}

	private String sayHello() {
		return "Hello "+name;
	}
}
