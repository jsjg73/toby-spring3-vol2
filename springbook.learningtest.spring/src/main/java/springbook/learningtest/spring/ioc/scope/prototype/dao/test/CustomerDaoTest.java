package springbook.learningtest.spring.ioc.scope.prototype.dao.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import springbook.learningtest.spring.ioc.scope.prototype.dao.CustomerDao;
import springbook.learningtest.spring.ioc.scope.prototype.dto.Customer;

public abstract class CustomerDaoTest {
	ApplicationContext ac;
	CustomerDao dao;
	@Before
	public void setUp() {
		initiateContext();
	}
	
	protected abstract void initiateContext();

	@Test
	public void getCustomer() {
		assertThat(dao.getCustomer(1).getName(), is("harry")); 
		assertThat(dao.getCustomer(2).getName(), is("brown"));
	}
	
	@Test
	public void findCustomerByNO() {
		Customer harry = dao.findCustomerByNo("001");
		assertThat(harry.getName(), is("harry"));
	}
	
	@Test
	public void getCoutn() {
		assertThat(dao.getCount(), is(2));
	}
}
