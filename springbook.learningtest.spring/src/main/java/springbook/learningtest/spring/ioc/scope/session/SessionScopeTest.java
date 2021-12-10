package springbook.learningtest.spring.ioc.scope.session;

import java.sql.Date;

import javax.inject.Provider;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

public class SessionScopeTest {
	
	
	static class LoginService {
//		@Autowired Provider<LoginUser> provider;
		@Autowired LoginUser loginUser;
		
		public LoginUser getUser() {
			return loginUser;
		}
	}
	
	//클래스 모드 스코프 프록시 설정
	@Scope(value= "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	static class LoginUser {
		String loginId;
		String name;
		Date loginTime;
		
		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}
	}

//	@Test(expected = IllegalStateException.class)
	@Test
	public void SessionScopeBeanDLbyProvider() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(LoginService.class, LoginUser.class);
		
		LoginService service = ac.getBean(LoginService.class);
		LoginUser user = ac.getBean(LoginUser.class);
	}
}
