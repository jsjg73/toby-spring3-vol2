package springbook.learningtest.spring.ioc.scope.prototype.controller.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import springbook.learningtest.spring.ioc.scope.prototype.controller.ServiceRequestController;
import springbook.learningtest.spring.ioc.scope.prototype.dao.CustomerDao;
import springbook.learningtest.spring.ioc.scope.prototype.dto.Customer;
import springbook.learningtest.spring.ioc.scope.prototype.dto.ServiceRequest;
import springbook.learningtest.spring.ioc.scope.prototype.service.ServiceRequestService;

public class ServiceRequestControllerTest {
	ServiceRequest input;
	ApplicationContext ac;
	@Before
	public void setUp() {
		ac = new AnnotationConfigApplicationContext(TestSrvReqControllerConfig.class);
//		ObjectFactory<ServiceRequest> serviceRequestFactory = ac.getBean(ObjectFactory.class);
//		input = serviceRequestFactory.getObject();
		input = ac.getBean(ServiceRequestFactory.class).getServiceFactory();
	}
	
	@Test
	public void serviceLocatorFactoryBeanUsingBeanName() {
		NameFactory nf = ac.getBean(NameFactory.class);
		ServiceA first1 = nf.getBean("first");
		ServiceA first2 = nf.getBean("first");
		ServiceA second = nf.getBean("second");
		
		assertThat(first1, notNullValue());
		assertThat(second, notNullValue());
		assertThat(first1, is(not(first2)));
	}
	interface NameFactory {
		ServiceA getBean(String name);
	}
	static class ServiceA{
		String name;
		public ServiceA(String name) {
			this.name = name;
		}
	}
	
	@Test
	public void serviceRequestFormSubmit() {
		ServiceRequestController controller = ac.getBean(ServiceRequestController.class);
		assertThat(input.getCustomer(), nullValue());
		controller.serviceRequestFormSubmit(input);
		
		assertThat(input.getCustomer(), notNullValue());
		assertThat(input.getCustomer().getName(), is("harry"));
	}
	
	@Configuration
	static class TestSrvReqControllerConfig {
		@Bean
		public ObjectFactoryCreatingFactoryBean serviceRequestFactory() {
			ObjectFactoryCreatingFactoryBean factoryBean =
					new ObjectFactoryCreatingFactoryBean();
			factoryBean.setTargetBeanName("serviceRequest");
			return factoryBean;
		}
		
		@Bean
		@Scope("prototype")
		public ServiceRequest serviceRequest() {
			ServiceRequest sr = new ServiceRequest("001", "Harry의 A/S 요청");
			sr.setCustomerDao(mockCustomerDao());
			return sr;
		}
		@Bean
		public ServiceRequestController serviceRequestController() {
			ServiceRequestController srvReqCtrl = new ServiceRequestController();
			srvReqCtrl.setSrvReqSrv(mockServiceRequestService());
			return srvReqCtrl; 
		}
		@Bean
		public MockServiceRequestService mockServiceRequestService() {
			return new MockServiceRequestService();
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
		
		//ServiceRequest전용으로 만든 팩토리 빈
		@Bean
		public ServiceLocatorFactoryBean serviceLocatorFactoryBean() {
			ServiceLocatorFactoryBean slfb = new ServiceLocatorFactoryBean();
			slfb.setServiceLocatorInterface(ServiceRequestFactory.class);
			return slfb;
		}
		
		@Bean
		public ServiceLocatorFactoryBean nameFactoryBean() {
			ServiceLocatorFactoryBean sf = new ServiceLocatorFactoryBean();
			sf.setServiceLocatorInterface(NameFactory.class);
			return sf;
		}
		@Bean
		@Scope("prototype")
		public ServiceA first() {
			return new ServiceA("first");
		}
		
		@Bean
		@Scope("prototype")
		public ServiceA second() {
			return new ServiceA("second");
		}
	}
	
	private static class MockServiceRequestService extends ServiceRequestService{
		@Override
		public void addNewServiceRequest(ServiceRequest serviceRequest) {}
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
	
	public interface ServiceRequestFactory{
		ServiceRequest getServiceFactory();
	}
}
