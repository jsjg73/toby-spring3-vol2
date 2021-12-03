package springbook.learningtest.spring.ioc.scope.prototype;

import springbook.learningtest.spring.ioc.scope.prototype.dto.Customer;

public interface CustomerDao {
	public Customer findCustomerByNo(String customerNo);
	public Customer getCustomer(int customerId);
	public int getCount();
}
