# JDBC
## **스프링 JDBC 기술과 동작원리**

### 스프링의 JDBC접근 방법
- SimpleJdbcTemplate
- SimpleJdbcInsert, SimpleJdbcCall

### 스프링 JDBC가 해주는 작업
- Connection 열고 닫기
- Statement 준비, 닫기
- Statement 실행
- ResultSet 루프
- 예외처리와 변환
- **트랜잭션 처리**


## **SimpleJdbcTemplate**
- 실행, 조회, 배치
- 실행 : INSERT, UPDATE
- 조회 : SELECT
- 배치 : 여러 작업을 한 번에 수행.
- 스프링 3.1 이후부터 deprecated.
### SimpleJdbcTemplate 생성
- 멀티스레드 환경에서도 안전함.
  ``` java
  // DAO 클래스에서 SimpleJdbcTemplate를 사용하는 일반적인 구조
  public class Dao{
      SimpleJdbcTemplate jdbcTemplate;

      @Autowired
      public void setDataSource(Datasource dataSource){
          this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
      }
  }
  ```
  ### SQL 파라미터
  - 위치 치환자 : INSERT INTO MEMBER(ID NAME, POINT) VALUES(?, ?, ?);
  - 이름 치환자 : INSERT INTO MEMBER(ID NAME, POINT) VALUES(:id, :name, :point);
  #### Map/MapSqlParameterSource
  - 
