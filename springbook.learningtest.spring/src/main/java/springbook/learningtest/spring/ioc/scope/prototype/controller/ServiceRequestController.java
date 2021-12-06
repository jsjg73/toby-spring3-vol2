package springbook.learningtest.spring.ioc.scope.prototype.controller;

import springbook.learningtest.spring.ioc.scope.prototype.dto.ServiceRequest;
import springbook.learningtest.spring.ioc.scope.prototype.service.ServiceRequestService;

public class ServiceRequestController {
	ServiceRequestService srvReqSrv;
	public void setSrvReqSrv(ServiceRequestService srvReqSrv) {
		this.srvReqSrv = srvReqSrv;
	}
	public void serviceRequestFormSubmit(/* HttpServletRequest request */ServiceRequest serviceRequest) {
		int id  =1;
		/*id = request.getParameter("id");*/
		serviceRequest.setCustomerByCustomerId(id);
		srvReqSrv.addNewServiceRequest(serviceRequest);
		
	}
}
