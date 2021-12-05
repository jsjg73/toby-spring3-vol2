package springbook.learningtest.spring.ioc.scope.prototype.service;

import springbook.learningtest.spring.ioc.scope.prototype.dao.ServiceRequestDao;
import springbook.learningtest.spring.ioc.scope.prototype.dto.ServiceRequest;

public class ServiceRequestService {
	private ServiceRequestDao serviceRequestDao;
	private NotifyService notifyService;
	
	public void setServiceRequestDao(ServiceRequestDao sRDao) {
		this.serviceRequestDao = sRDao;
	}
	
	public void setNotifyService(NotifyService notifyService) {
		this.notifyService = notifyService;
	}
	public void addNewServiceRequest(ServiceRequest serviceRequest) {
		// a/s 요청하고
		// 완료 통보
		this.serviceRequestDao.add(serviceRequest);
		this.notifyService.notifyServiceRequestRegistration(serviceRequest);
	}
}
