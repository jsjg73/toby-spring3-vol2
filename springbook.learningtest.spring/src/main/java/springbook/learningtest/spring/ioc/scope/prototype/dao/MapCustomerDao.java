package springbook.learningtest.spring.ioc.scope.prototype.dao;

import java.util.HashMap;
import java.util.Map;

import springbook.learningtest.spring.ioc.scope.prototype.dto.Customer;

public class MapCustomerDao implements CustomerDao{
	Map<Integer, Customer> register = new HashMap<>();
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
	
	@Override
	public int getCount() {
		return register.size();
	}
}


