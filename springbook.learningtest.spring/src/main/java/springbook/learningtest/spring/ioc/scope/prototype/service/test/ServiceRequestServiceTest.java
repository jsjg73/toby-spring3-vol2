package springbook.learningtest.spring.ioc.scope.prototype.service.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springbook.learningtest.spring.ioc.scope.prototype.dao.ServiceRequestDao;
import springbook.learningtest.spring.ioc.scope.prototype.dto.ServiceRequest;
import springbook.learningtest.spring.ioc.scope.prototype.service.NotifyService;
import springbook.learningtest.spring.ioc.scope.prototype.service.ServiceRequestService;

public class ServiceRequestServiceTest {
	private ServiceRequest input;

	@Before
	public void setUp() {
		input = new ServiceRequest("001", "Harry¿« A/S ø‰√ª");
	}

	@Test
	public void addNewServiceRequest() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestSrvReqSrvConfig.class);
		ServiceRequestService sr = ac.getBean("serviceRequestService", ServiceRequestService.class);
		sr.addNewServiceRequest(input);

		assertThat(ac.getBean(MockServiceRequestDao.class).list.get(0), is(input));
		assertThat(ac.getBean(MockNotifyService.class).list.get(0), is(input));
	}

	@Configuration
	static class TestSrvReqSrvConfig {
		@Bean
		public ServiceRequestService serviceRequestService() {
			ServiceRequestService srs = new ServiceRequestService();
			srs.setServiceRequestDao(mockServiceRequestDao());
			srs.setNotifyService(mockNotifyService());
			return srs;
		}

		@Bean
		public MockServiceRequestDao mockServiceRequestDao() {
			return new MockServiceRequestDao();
		}
		@Bean
		public MockNotifyService mockNotifyService() {
			return new MockNotifyService();
		}
	}

	static class MockServiceRequestDao implements ServiceRequestDao {
		List<ServiceRequest> list = new ArrayList<>();

		@Override
		public void add(ServiceRequest sr) {
			list.add(sr);
		}
	}

	static class MockNotifyService implements NotifyService {
		List<ServiceRequest> list = new ArrayList<ServiceRequest>();
		@Override
		public void notifyServiceRequestRegistration(ServiceRequest serviceRequest) {
			list.add(serviceRequest);
		}
	}
}
