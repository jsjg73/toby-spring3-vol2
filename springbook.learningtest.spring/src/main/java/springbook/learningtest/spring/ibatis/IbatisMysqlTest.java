package springbook.learningtest.spring.ibatis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Qualifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ibatis.sqlmap.client.SqlMapClient;

import springbook.learningtest.spring.jdbc.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class IbatisMysqlTest {
	@Autowired IbatisDao dao;
	
	@Test
	public void ibatis() {
		dao.deleteAll();
		Member m1 =new Member(1, "jsjg73", 1.40215);
		dao.insert(m1);
		Member m2 = dao.select(m1.getId());
		assertThat(m1.getId(), is(1));
		assertThat(m1.getName(), is("jsjg73"));
		assertThat(m1.getPoint(), is(1.40215));
		
		List<Member> ms = dao.selectAll();
		assertThat(ms.size(), is(1));
	}
	
	public static class IbatisDao{
		SqlMapClientTemplate sqlMapClientTemplate;
		
		public void setSqlMapClient(SqlMapClient sqlMapClient) {
			this.sqlMapClientTemplate = new SqlMapClientTemplate(sqlMapClient);
		}

		public List<Member> selectAll() {
			return this.sqlMapClientTemplate.queryForList("findMembers");
		}

		public Member select(int id) {
			return (Member)this.sqlMapClientTemplate.queryForObject("findMemberById", id);
		}

		public void insert(Member m) {
			this.sqlMapClientTemplate.insert("insertMember", m);
		}

		public void deleteAll() {
			this.sqlMapClientTemplate.delete("deleteMemberAll");
		}
	}
}
