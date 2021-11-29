package springbook.learningtest.spring.ioc.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import springbook.learningtest.spring.ioc.bean.Printer;

public class Hello {
	
	String name;
	@Autowired
	Printer printer;
	public Hello() {};
	public Hello(String name, Printer printer) {
		super();
		this.name = name;
		this.printer = printer;
	}

	public String sayHello() {
		return "Hello " + name;
	}

	public void print() {
		this.printer.print(sayHello());
	}
	
	@Value("Everyone")
	public void setName(String name) {
		this.name = name;
	}

	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
}
