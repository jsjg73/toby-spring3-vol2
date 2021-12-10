package springbook.learningtest.spring.ioc.scope.session;

import java.sql.Date;

import javax.inject.Provider;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SessionScopeTest {
	
	
	static class LoginService {
		@Autowired Provider<LoginUser> provider;
		public LoginUser getUser() {
			return provider.get();
		}
	}

	@Scope("session")
	static class LoginUser {
		String loginId;
		String name;
		Date loginTime;
	}

	@Test(expected = IllegalStateException.class)
	public void SessionScopeBeanDLbyProvider() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(LoginService.class, LoginUser.class);
		
		LoginService service = ac.getBean(LoginService.class);
		LoginUser user = service.getUser();
	}
}
