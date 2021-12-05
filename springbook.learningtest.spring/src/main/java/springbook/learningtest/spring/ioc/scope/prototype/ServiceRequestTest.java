package springbook.learningtest.spring.ioc.scope.prototype;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import springbook.learningtest.spring.ioc.scope.prototype.dao.CustomerDao;
import springbook.learningtest.spring.ioc.scope.prototype.dto.Customer;
import springbook.learningtest.spring.ioc.scope.prototype.dto.ServiceRequest;

public class ServiceRequestTest {

	ApplicationContext ac;
	Customer harry;
	@Before
	public void setUp() {
		ac = new AnnotationConfigApplicationContext(TestServiceRequestConfig.class);
		harry = ac.getBean(Customer.class);
		
	}
	
	@Test
	public void prototypeServiceRequest() {
		ServiceRequest sr1 = ac.getBean(ServiceRequest.class);
		ServiceRequest sr2 = ac.getBean(ServiceRequest.class);
		assertThat(sr1.getCustomer(), nullValue());
		assertThat(sr1.getProductNo(), is(sr2.getProductNo()));
		assertThat(ac.getBean(ServiceRequest.class), is(not(ac.getBean(ServiceRequest.class))));
	}
	
	
	@Test
	public void findCustomerByCustomerNo() {
		ServiceRequest sr = ac.getBean(ServiceRequest.class);
		sr.setCustomerByCustomerNo(harry.getNo());
		assertThat(sr.getCustomer().getId(), is(harry.getId()));
	}
	
	@Test
	public void findCustomerByCustomerId() {
		ServiceRequest sr = ac.getBean(ServiceRequest.class);
		sr.setCustomerByCustomerId(harry.getId());
		assertThat(sr.getCustomer().getId(), is(harry.getId()));
	}
	@Configuration
	static class TestServiceRequestConfig {
		@Bean
		@Scope("prototype")
		public ServiceRequest serviceRequest() {
			ServiceRequest sr = new ServiceRequest("001", "Harry¿« A/S ø‰√ª");
			sr.setCustomerDao(mockCustomerDao());
			return sr;
		}
		
		@Bean
		public MockCustomerDao mockCustomerDao() {
			MockCustomerDao dao = new MockCustomerDao();
			Customer harry = customer();
			dao.byNo.put(harry.getNo(), harry);
			dao.byId.put(harry.getId(), harry);
			return dao;
		}
		@Bean
		public Customer customer() {
			Customer harry = new Customer();
			harry.setId(1);
			harry.setNo("001");
			harry.setName("harry");
			return harry;
		}
	}
	
	static class MockCustomerDao implements CustomerDao{
		Map<String, Customer> byNo = new HashMap<>();
		Map<Integer, Customer> byId = new HashMap<>();
		
		@Override
		public Customer findCustomerByNo(String customerNo) {
			return byNo.get(customerNo);
		}

		@Override
		public Customer getCustomer(int customerId) {
			return byId.get(customerId);
		}

		@Override
		public int getCount() {
			return byId.size();
		}
	}
}
