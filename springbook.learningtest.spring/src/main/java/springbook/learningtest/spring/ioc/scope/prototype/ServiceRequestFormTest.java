package springbook.learningtest.spring.ioc.scope.prototype;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class ServiceRequestFormTest {
	@Test
	public void getCustomer() {
		ApplicationContext ac = new GenericXmlApplicationContext(new ClassPathResource("mapCtmDao.xml", getClass()));
		MapCustomerDao dao = ac.getBean("mapCtmDao", MapCustomerDao.class);
		assertThat(dao.getCustomer(1).getName(), is("harry")); 
		assertThat(dao.getCustomer(2).name, is("brown"));
	}
	
	@Test
	public void findCustomerByNO() {
		ApplicationContext ac = new GenericXmlApplicationContext(new ClassPathResource("mapCtmDao.xml", getClass()));
		MapCustomerDao dao = ac.getBean("mapCtmDao", MapCustomerDao.class);
		assertThat(dao.register.size(), is(2));
		
		Customer harry = dao.findCustomerByNo("001");
		assertThat(harry.getName(), is("harry"));
	}
	
	@Test
	public void addServiceRequest() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
		MapServiceRequestDao dao = ac.getBean("mapSrvReqDao", MapServiceRequestDao.class);
		assertThat(dao.count(), is(3));
		
		dao.add(new ServiceRequest("004",""));
		assertThat(dao.count(), is(4));
	}
	
	@Configuration
	static class Config{
		@Bean
		public MapServiceRequestDao mapSrvReqDao() {
			MapServiceRequestDao dao = new MapServiceRequestDao();
			dao.register.put("001", new ServiceRequest("001",""));
			dao.register.put("002", new ServiceRequest("002",""));
			dao.register.put("003", new ServiceRequest("003",""));
			return dao;
		}
	}
	
	static class ServiceRequest{
		Customer customer;
		String productNo;
		String descripton;
		public ServiceRequest(String productNo, String descripton) {
			super();
			this.productNo = productNo;
			this.descripton = descripton;
		}
		
	}
	
	static class Customer{
		int id;
		String no,name;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getNo() {
			return no;
		}
		public void setNo(String no) {
			this.no = no;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	interface CustomerDao{
		Customer findCustomerByNo(String customerNo);
		Customer getCustomer(int customerId);
	}
	static class MapCustomerDao implements CustomerDao{
		Map<Integer, Customer> register;
		public void setRegister(Map<Integer, Customer> register) {
			this.register = register;
		}
		@Override
		public Customer findCustomerByNo(String customerNo) {
			if(register != null) {
				for (Map.Entry<Integer, Customer> iterable_element : register.entrySet()) {
					if(iterable_element.getValue().getNo().equals(customerNo)){
						return iterable_element.getValue();
					}
				}
			}
			return null;
		}
		@Override
		public Customer getCustomer(int customerId) {
			return register.get(customerId);
		}
	}
	
	interface ServiceRequestDao{ void add(ServiceRequest rs); }
	static class MapServiceRequestDao implements ServiceRequestDao {
		Map<String, ServiceRequest> register = new HashMap<>();

		@Override
		public void add(ServiceRequest rs) {
			register.put(rs.productNo, rs);
		}
		
		public int count() {
			return register.size();
		}
	}
}
