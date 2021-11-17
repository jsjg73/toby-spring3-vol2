### IoC와 DI
***
#### POJO 클래스와 설정 메타정보
- **StaticApplicationContext**    
	.registerSingleton()   
	.registerBeanDefinition()   
	.getBean()   
- **BeanDefinition / RootBeanDefinition**    
	.getPropertyValues()   
	.addPropertyValue();
- **RuntimeBeanReference**
***
#### IoC 컨테이너의 종류와 사용법
- **StaticApplicationContext**   
    - 코드를 통해 빈 메타정보를 등록할 때 사용.
    - 학습 테스트에서 주로 이용함.
    -     "스프링 IoC 컨테이너는 파일 포맷이나 리소스 종류에 독립적이며, 오브젝트로 표현되는 순수한 메타정보를 사용한다."
- **GenericApplicationcontext**
    - XML파일과 같은 외부의 리소스로 빈 설정 메타정보를 리더를 통해 읽어들일 수 있음
    - 빈 설정정보 리더(BeanDefinitionReader 인터페이스의 구현 오브젝트)가 필요함.   
ex) XmlBeanDefinitionReader
- **GenericXmlApplicationContext**
    - XmlBeanDefinitionReader를 내장함. xml 파일을 읽고 refresh까지 실행함.
- **WebApplicationContext**
    - 웹 환경에서 필요한 기능이 추가됨.
    - EX) XmlWebApplicationContext, AnnotationConfigWebApplicationContext
    - 웹 환경에서 동작하는 구조와 순서
    	1. 클라이언트의 요청 ->
    	2. 서블릿 컨테이너가 서블릿을 동작시킴 ->
    	3. 서블릿은 **웹 어플리케이션 컨텍스트**에서 어플리케이션 기동 역할을 하는 빈을 요청 ->
    	4. 빈에서 지정된 메소드 호출하여 애플리케이션 기능이 시작됨
    - 세 번째 동작인 (c)에서 서블릿이 어떻게 웹 애플리케이션 컨텍스트를 알고 있는가?
      - 스프링이 제공하는 서블릿 :: **DispatcherServlet**   
        애플리케이션 컨텍스트 생성, 설정 메타 정보 초기화, 클라이언트 요청에 따라 적절한 빈 선택(조회) 기능을 제공함.
