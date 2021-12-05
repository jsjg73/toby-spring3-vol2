package springbook.learningtest.spring.ioc.scope.prototype.controller.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springbook.learningtest.spring.ioc.scope.prototype.controller.ServiceRequestController;
import springbook.learningtest.spring.ioc.scope.prototype.dto.ServiceRequest;
import springbook.learningtest.spring.ioc.scope.prototype.service.ServiceRequestService;

public class ServiceRequestControllerTest {
	ServiceRequest input;
	@Before
	public void setUp() {
		input = new ServiceRequest("001", "Harry¿« A/S ø‰√ª");
	}
	
	@Test
	public void serviceRequestFormSubmit() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestSrvReqControllerConfig.class);
		ServiceRequestController controller = ac.getBean(ServiceRequestController.class);
		controller.serviceRequestFormSubmit(input);
		
		assertThat(input.getCustomer(), notNullValue());
		assertThat(input.getClass().getName(), is("Harry"));
	}
	
	@Configuration
	static class TestSrvReqControllerConfig {
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
	}
	private static class MockServiceRequestService extends ServiceRequestService{
		@Override
		public void addNewServiceRequest(ServiceRequest serviceRequest) {}
	}
}
