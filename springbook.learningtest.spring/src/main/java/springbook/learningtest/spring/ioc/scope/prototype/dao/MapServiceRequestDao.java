package springbook.learningtest.spring.ioc.scope.prototype.dao;

import java.util.HashMap;
import java.util.Map;

import springbook.learningtest.spring.ioc.scope.prototype.ServiceRequestDao;
import springbook.learningtest.spring.ioc.scope.prototype.dto.ServiceRequest;

public class MapServiceRequestDao implements ServiceRequestDao {
	Map<String, ServiceRequest> register = new HashMap<>();
	
	@Override
	public void add(ServiceRequest rs) {
		register.put(rs.getProductNo(), rs);
	}

	public int count() {
		return register.size();
	}
}