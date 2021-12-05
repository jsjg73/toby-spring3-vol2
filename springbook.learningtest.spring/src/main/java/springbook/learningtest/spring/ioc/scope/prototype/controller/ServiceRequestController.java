package springbook.learningtest.spring.ioc.scope.prototype.controller;

import springbook.learningtest.spring.ioc.scope.prototype.dto.ServiceRequest;
import springbook.learningtest.spring.ioc.scope.prototype.service.ServiceRequestService;

public class ServiceRequestController {
	ServiceRequestService srvReqSrv;
	public void setSrvReqSrv(ServiceRequestService srvReqSrv) {
		this.srvReqSrv = srvReqSrv;
	}
	public void serviceRequestFormSubmit(/* HttpServletRequest request */ServiceRequest serviceRequest) {
		srvReqSrv.addNewServiceRequest(serviceRequest);
	}
}
