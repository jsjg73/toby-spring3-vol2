package springbook.learningtest.spring.ioc.scope.prototype.dto;

import springbook.learningtest.spring.ioc.scope.prototype.dao.CustomerDao;

public class ServiceRequest {
	private CustomerDao customerDao;
	
	Customer customer;
	String productNo;
	String descripton;

	public ServiceRequest(String productNo, String descripton) {
		super();
		this.productNo = productNo;
		this.descripton = descripton;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getDescripton() {
		return descripton;
	}

	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}

	public void setCustomerDao(CustomerDao dao) {
		this.customerDao = dao;
	}

	public void setCustomerByCustomerNo(String customerNo) {
		customer = this.customerDao.findCustomerByNo(customerNo);
	}

	public void setCustomerByCustomerId(int id) {
		customer = this.customerDao.getCustomer(id);		
	}
	
}
