package springbook.learningtest.spring.ioc.scope.prototype.service;

import springbook.learningtest.spring.ioc.scope.prototype.dto.ServiceRequest;

public interface NotifyService {
	public void notifyServiceRequestRegistration(ServiceRequest serviceRequest);
}
