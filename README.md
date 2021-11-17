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
