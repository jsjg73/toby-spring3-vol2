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
    - AnnotationConfigApplicationContext 컨텍스트 클래스에 내장한 빈 스캐너에 의해 빈 자동등록
    - **자동 저장 대상 빈 조건**
        1. 빈 스캐너에 지정된 패키지 하위에 있어야함
	2. 스테레오 타입의 어노테이션(@Componenet)이 붙어있어야함.
    - 전체 어플리케이션의 빈 구성을 파악하기 힘들고, 빈에 상세한 메타정보를 지정할 수 없는 한계가 있다.
    - 빈 스캐닝 기술은 생산성이 높으므로 개발 중에 사용될 수 있다.
    - 운영 시점에서는 다시 XML 형태를 선택하는 것이 좋은 전략이다.
- **자바 코드에 의한 빈 등록: @Configuration 클래스의 @Bean 메소드**
    - 기술 서비스 빈이나 컨테이너 설정 빈등은 스테레오 타입 애노테이션이 없을 수 있으므로, 자바 코드를 이용한 설정 메타정보로 만든다. 이 방식을 웹 어플리케이션에 적용하려면, 루트 컨텍스트와 서블릿 컨텍스트의 컨텍스트 클래스를 AnnotationConfigWebApplicationContext 클래스로 변경해줘야한다. 물론 스캔 대상 패키지도 지정해줘야한다.
    - XML 태그 방식의 빈 설정의 유용한 점은 aop, tx 등 전용 태그를 지원하는 것이다. 자바 코드 방식에서도 대응 되는 빈을 직접 @Configuration 클래스 안에 작성해 주면 기능은 사용할 수 있다. 다만 편리함은 줄어든다. 전용 태그에 대응되는 @Configuration 클래스 설정 방식은 스프링 3.1 부터 지원된다.
    - @Configuation 클래스는 CGLIB를 요구한다. 스프링은 @Bean 메소드 호출을 CGLIB proxy를 통해 처리한다. 그리고 @Bean메소드는 항상  동일한 인스턴스를 반환한다.
- **자바 코드에 의한 빈 등록: 일반 빈 클래스의 @Bean 메소드**
#### 빈 의존관계 설정 방법
- ***XML: <constructor-arg>***
     - name, type, index를 사용해서 DI 할 수 있음
- ***XML: 자동와이어링***
     - \<bean id=... class=... autowire="byName"\>
     - byName, byType 등
     - xml 문서를 간소화 시켜줌
- ***애노테이션: @Resource***
     - 수정자와 필드에 적용할 수 있다.
     - 클래스가 빈으로 등록된 후에 후처리기에 의해서 @Resource를 처리한다.
     - 즉, @Resource를 가진 클래스의 DI가 이뤄지기 위해선 먼저 클래스가 빈으로 등록되어야한다.
     - \<context:annotation-config /\>
     - \<context:component-scan base-pakage="..." /\>

### 프로토타입과 스코프
- 싱글톤 : 애플리케이션 컨텍스트마다 빈의 오브젝트는 한 개만 만들어진다. <u>여러 스레드가 공유해서 사용하므로 상태 값을 인스턴스 변수에 두고 사용할 수 없다.</u>
- 싱글톤이 아닌 빈 :   
    1. 프로토타입 빈
    2. 스코프 빈 
       - '스코프' : 존재할 수 있는 범위. 싱글톤과 프로토 타입도 스코프의 한 종류이지만 이 두 가지는 나머지 스코프들과 성격이 크게 다르므로 구분해서 기억하는 것이 좋다. 
       - ex) '요청 스코프'는 하나의 요청이 끝날때 까지만 존재한다. 반면 '싱글톤 스코프'는 컨테이너와 생명주기가 같다.

#### 프로토타입   
 - ***프로토타입 빈의 생명주기와 종속성***   
    - 프로토타입 빈은 컨테이너에 DI된 이후에 컨테이너의 영향력에서 벗어난다. 프로토타입 빈의 생명주기는 프로토타입 빈이 사용되는 오브젝트(DI 받은 오브젝트)에 의해 결정된다. 
 - ***프로토타입 빈의 용도***
    - 코드에서 new로 오브젝트를 생성하는 것을 대신한다.
    - 컨테이너가 오브젝트를 만들고 초기화해야하는 경우가 간혹있다. DI 때문이다.
    - 사용자의 요청마다 새로운 오브젝트가 필요하면서, 다른 빈을 주입받아야할 때 유용하다.
 - ***Dl 전략들***  
      1. 기본적인 방법
         - 컨테이너 빈에 직접 getBean(..) 호출한다. 비즈니스 코드에 스프링 API가 직접 등장하는 단점이 있다.
      2. ObjectFactory, ObjectFactoryCreatingFactoryBean
      3. ServiceLocatorFactoryBean
      4. 메소드 주입
         - 빈 설정
            ```XML
            <bean id="controllerA" class = "...ControllerA">
                <lookup-method name= "getPrototypeBean" bean="prototypeBean"/>
            </bean>

            <bean id="prototypeBean" class="...PrototypeBean" scope="prototype"/>
            ```
         - Java code
            ``` JAVA
            static abstract class ControllerA {
                abstract public PrototypeBean getPrototypeBean();
            }
            
            static class PrototypeBean{String name = "hello";}
            ```
      5. Provider<T>  
         - JavaEE 6 이후 등장한 Provider 인터페이스. @Inject와 함께 JSR-330(DIJ)에 추가된 표준 인터페이스.
         - @Autowire, @Resource 혹은 @Inject로 Provider 인터페이스를 지정하면, 스프링이 자동으로 오브젝트 생성 주입.
         - 예,
           ``` Java
           @Inject Provider<PrototypeBean> prototypeProvider;

           public void usingPrototypeBean(){
               PrototypeBean bean = this.prototypeProvider.get();
               ...
           }
           ```
#### 스코프
- 요청, 세션, 글로벌세션 그리고 애플리케이션. 네 가지 스코프 제공

- ***요청 스코프***
    - 요청 스코프 빈은 하나의 웹 요청 안에서 만들어지고, 해당 요청이 끝날 때 제거.
    - DTO나 도메인 오브젝트와 성격이 비슷하다. 그렇기 때문에 요청 스코프 빈을 사용할 이유가 없긴하다.
    - 용도 : 파라미터로 전달하지 않고, 참조가 필요한 경우. ex) 보안 프레임워크에서 현재 요청에 관련된 권한 정보를 '요청 스코프 빈'으로 저장하고, 필요한 빈에서 참조하도록함.
    - 장단점 : 전역 변수처럼 이용되어 코드 이해가 어려워질 수 있으니 조심할 것.

- ***세션 스코프, 글로벌 세션 스코프***
    - HTTP 세션과 같은 존재 범위를 같는 빈.
    - 브라우저를 닫거나 세션 타임이 종료될 때까지 유지된다. 그러므로 **로그인 정보**나 **사용자별 선택옵션** 등을 유지하기에 적당하다.
- ***애플리케이션 스코프***
    - 서블릿 컨텍스트에 저장되는 빈 오브젝트.
    - 서블릿 컨텍스트는 웹 어플리케이션마다 만들어진다.
    - 웹 어플리케이션마다 스프링의 애플리케이션도 만들어진다.
    - 멀티스레드 환경에서 안전하도록 만들어야한다.

- ***스코프 빈 사용법***
    - 프로토타입 빈과 마찬가지로 DL방식을 사용한다.
    - 또는, 스프링이 제공하는 특별한 방식의 DI(프록시)를 사용할 수도 있다. 
    - 
       ``` JAVA
      @Scope(value= "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
      public class LoginUser {...
      ```
      ``` Java
      public class LoginService {
          @Autowired LoginUser loginUser;

          public void login(Login login){
              //로그인 처리
              this.loginUser.setLoginId(...); // 세션이 다르면 다른 오브젝트의 메소드가 호출된다!!
              ...
          }
      }
      ```
      **주의** : LoginService 클래스의 loginUser 필드는 @Autowired로 마치 싱글톤 빈이 주입된것 처럼 보일 수 있다. loginUser가 싱글톤이라면 setLoginId(...)가 호출되는 것은 문제가 있는 코드다. 하지만 세션 스코프 빈이기 때문에 정상적인 코드다.
    - 아래는 동일한 구조를 xml로 표현한 것이다.
      ``` xml
        <bean id="loginUser" class="...LoginUser" scope="session">
            <aop:scoped-proxy proxy-target-class="true"/>
        </bean>
      ```
- Q : **프로토 타입 빈과 스코프 빈의 공통점 및 차이점**  
  A :    
  **공통점** - 하나 이상의 빈 오브젝트가 생성된다.   
  **차이점** - 프로토 타입 빈의 생명 주기는 빈이 주입되는 오브젝트에 의해 결정된다. 반면에 스코프 빈의 생명주기는 여전히 스프링이 관리한다.
- Q : **왜 싱글톤 빈에 스코프 빈을 주입할 수 없을까?**   
  A : 싱글톤 빈이 참조하는 빈들을 주입하는 시점과 관련있다. 어플리케이션 컨텍스를 초기화할 때 빈 생성과 DI가 이뤄진다. 초기화 시점에는 스코프 빈을 생성할 수 없다.(웹 요청 등이 발생해야한다.) 그렇기 때문에 스코프 빈을 주입하도록 설정된 싱글폰 빈은 초기화시 에러를 일으키게 된다.
        
       
        
         

