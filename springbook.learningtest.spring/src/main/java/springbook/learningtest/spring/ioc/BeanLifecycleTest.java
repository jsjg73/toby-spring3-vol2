package springbook.learningtest.spring.ioc;

import static org.junit.Assert.assertTrue;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class BeanLifecycleTest {
	
	@Component
	static class BeanAAA {
		private boolean initialized = false;
		public boolean isInitialized() {
			return initialized;
		}
		public void setInitialized(boolean initialized) {
			this.initialized = initialized;
		}

		@PostConstruct
		public void init() {
			initialized=true;
		}
	}
	
	@Test
	public void postConstruct() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(BeanAAA.class);
		BeanAAA bean = ac.getBean(BeanAAA.class);
		assertTrue(bean.isInitialized());
	}
}
