package springbook.learningtest.spring.ioc.scope.prototype.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.learningtest.spring.ioc.scope.prototype.CustomerDao;
import springbook.learningtest.spring.ioc.scope.prototype.dto.Customer;

public class EmbeddedDbCustomerDao implements CustomerDao {
	JdbcTemplate jdbc;
	public void setDataSource(DataSource datasource) {
		jdbc = new JdbcTemplate(datasource);
	}
	@Override
	public Customer findCustomerByNo(String customerNo) {
		return jdbc.queryForObject("select id, no, name from customer where no = ?", customerMapper, customerNo);
	}

	@Override
	public Customer getCustomer(int customerId) {
		return jdbc.queryForObject	("select id, no, name from customer where id = ?", customerMapper, customerId);
	}

	@Override
	public int getCount() {
		return jdbc.queryForInt("select count(*) from customer");
	}
	
	private RowMapper<Customer> customerMapper = new RowMapper<Customer>() {
		
		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setId(rs.getInt("id"));
			customer.setNo(rs.getString("no"));
			customer.setName(rs.getString("name"));
			return customer;
		}
	}; 
}
