package springbook.learningtest.spring.ioc.scope.prototype.dao.test;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import springbook.learningtest.spring.ioc.scope.prototype.dao.MapCustomerDao;

public class MapCustomerDaoTest extends CustomerDaoTest{
	
	@Override
	protected void initiateContext() {
		ac = new GenericXmlApplicationContext(new ClassPathResource("mapCtmDao.xml", getClass()));
		dao = ac.getBean("mapCtmDao", MapCustomerDao.class);
	}
}
