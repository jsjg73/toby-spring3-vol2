package springbook.learningtest.spring.ibatis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import springbook.learningtest.spring.embeddeddb.MemberEmbeddedDB;
import springbook.learningtest.spring.jdbc.Member;

public class IbatisEmbeddedDbTest {
//	private EmbeddedDatabase db;
	
	@Test(expected = AssertionError.class)
	public void ibatis() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(IbatisConfig.class);
		IbatisDao dao = ac.getBean(IbatisDao.class);
		
		dao.deleteAll();
		dao.add(new Member(1,"jsjg73", 1.3));
		Member m = dao.select(1);
		assertThat(m.getId(), is(1));
		assertThat(m.getName(), is("jsjg73"));
		
		
//		부동소수점이 db에 저장될 때 주의사항
		assertTrue(m.getPoint()==1.3);
		
	}
	
	public static class IbatisDao{
		private SqlMapClientTemplate sqlMapClientTemplate;
		
		public void setSqlMapClient(SqlMapClient sqlMapClient) {
			this.sqlMapClientTemplate = new SqlMapClientTemplate(sqlMapClient);
		}
		public List<Member> selectAll() { return sqlMapClientTemplate.queryForList("findMembers"); }
		public Member select(int i) {
			return (Member)this.sqlMapClientTemplate.queryForObject("findMemberById", i);
			
		}
		public void deleteAll() {
			sqlMapClientTemplate.delete("deleteMemberAll");
		}
		public void add(Member m) {
			sqlMapClientTemplate.insert("insertMember", m);
		}
	}
	
	@Configuration
	public static class IbatisConfig{
		@Bean
		public IbatisDao ibatisDao(){
			IbatisDao dao = new IbatisDao();
			dao.setSqlMapClient(sqlMapClient());
			return dao;
		}

		@Bean
		public SqlMapClient sqlMapClient() {
			SqlMapClientFactoryBean factory = new SqlMapClientFactoryBean();
			factory.setDataSource(embeddedDB());
			factory.setConfigLocation(new ClassPathResource("SqlMapConfig.xml", getClass()));
			try {
				factory.afterPropertiesSet();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			SqlMapClient smc =(SqlMapClient)factory.getObject();
			System.out.println(smc);
			return smc;
		}
		
		public EmbeddedDatabase embeddedDB() {
			return MemberEmbeddedDB.getEmbeddedDatabase();
		}
	}
}
