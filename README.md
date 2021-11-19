### IoC와 DI
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
***
***
### IoC/DI를 위한 빈 설정 메타정보 작성
IoC 컨테이너는 BeanDefinition을 참고하여 Bean 생성.   
리더 혹은 생성기를 통해 여러 형태의 소스(xml, 애노테이션 그리고 자바 코드)에서 BeanDefinition을 만든다.
***
#### 빈 설정 메타정보
- "BeanDefinition에는 빈의 이름이나 아이디는 포함되지 않는다." 왜냐하면 설정 메타정보가 같지만 이름이 다른 여러 개의 빈 오브젝트를 만들 수 있기 때문이다.   
- IoC컨테이너에 이 BeanDefinition 정보가 등록될 때 이름을 부여한다.
- **빈 설정 메타정보 항목**
    - BeanClassName (필수)
    - scope
    - lazyInit
    - primary
    - autowireMode
    - initMethod
    - propertyValues
    - 등등
***
#### 빈 등록 방법
- 빈 메타정보는 주로 XML문서, 프로퍼티 파일 혹은 소스코드 어노테이션과 같은 외부 리소스로 작성된다.
- 리더 혹은 변환기를 통해 애플리케이션 컨텍스트(ac)가 사용할 수 있도록 변환된다.
- **스프링에서 자주 사용되는 5가지 빈 등록 방법**
    1. XML: <bean> 태그
    2. XML: 네임스페이스와 전용 태그
    3. 자동인식을 이용한 빈 등록: 스테레오타입 애노테이션과 빈 스캐너
    4. 자바 코드에 의한 빈 등록: @Configuration 클래스의 @Bean 메소드
    5. 자바 코드에 의한 빈 등록: 일반 빈 클래스의 @Bean 메소드
- **XML: <bean> 태그**
    - BeanDefinition을 코드에서 직접 만드는 방법을 제외하면, XML을 사용하는 것이 모든 종류의 빈 설정 메타정보 항목을 지정할 수 있는 유일한 방법이다.
    - 실행 전에는 타입 안전성을 보장할 수 없다.
- **XML: 네임스페이스와 전용 태그**
    - 전용 태그
      - [Appendix D. Extensible XML authoring](https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/extensible-xml.html, "커스텀 XML 공식 레퍼런스") 참고
      - 커스터 XML bean definition parser를 작성하고 IoC 컨테이너에 건네준다.
      - XML Schema를 사용해야한다.(XML Schema 설정이 익숙치 않다면 [Appendix C. XML Schema-based configuration](https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/xsd-config.html)를 참고하자.)
- **자동인식을 이용한 빈 등록: 스테레오타입 애노테이션과 빈 스캐너**
- **자바 코드에 의한 빈 등록: @Configuration 클래스의 @Bean 메소드**
    - 기술 서비스 빈이나 컨테이너 설정 빈등은 스테레오 타입 애노테이션이 없을 수 있으므로, 자바 코드를 이용한 설정 메타정보로 만든다. 이 방식을 웹 어플리케이션에 적용하려면, 루트 컨텍스트와 서블릿 컨텍스트의 컨텍스트 클래스를 AnnotationConfigWebApplicationContext 클래스로 변경해줘야한다. 물론 스캔 대상 패키지도 지정해줘야한다.
    - XML 태그 방식의 빈 설정의 유용한 점은 aop, tx 등 전용 태그를 지원하는 것이다. 자바 코드 방식에서도 대응 되는 빈을 직접 @Configuration 클래스 안에 작성해 주면 기능은 사용할 수 있다. 다만 편리함은 줄어든다. 전용 태그에 대응되는 @Configuration 클래스 설정 방식은 스프링 3.1 부터 지원된다.
- **자바 코드에 의한 빈 등록: 일반 빈 클래스의 @Bean 메소드**
