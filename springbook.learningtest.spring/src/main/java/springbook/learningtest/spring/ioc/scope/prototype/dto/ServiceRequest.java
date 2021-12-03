package springbook.learningtest.spring.ioc.scope.prototype.dto;

public class ServiceRequest {
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
	
}
