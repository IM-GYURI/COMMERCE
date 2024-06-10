# Trouble Shooting

프로젝트를 진행하며 발생한 문제점들과 해결 방법을 서술합니다.

### bcryptpasswordencoder : empty encoded password

PostMan에서 http://localhost:8080/customer/signin 를 POST할 경우 발생

Body : JSON - RAW

```
{
    "email": "kgulr0517@naver.com",
    "password": "qwer1234"
}
```

⭐ 해결

1. CustomerService의 signIn() 내에 `System.out.println(signInDto.email() + " " + signInDto.password())`
   를 호출한 결과 Body의 내용이 제대로 출력되는 것을 확인
2. CustomerEntity의 getPassword()가 null을 리턴하고 있는 것을 확인하고 아래와 같이 수정

```java

@Override
public String getPassword() {
  return password;
}
```

### DB : 주소를 한글로 입력 시 오류 발생

⭐ 해결

1. application.yml 내 spring-datasource-url에 UTF-8 설정 추가

```    
url: jdbc:mariadb://localhost:3306/commerce?characterEncoding=UTF-8
```

2. 해당 DB의 스키마를 UTF-8로 수정

### Java file outside of source root

IntelliJ에서 각 모듈 내의 Application이 실행되지 않는 오류 발생

⭐ 해결

1. File > Project Structure > Modeuls > 루트 디렉토리 클릭 > Mark as Source > OK
2. 각 모듈 내의 build.gradle을 gradle 연동

### 멀티 모듈 : Gradle이 build가 되지 않는 오류

⭐ 해결

1. Gradle 탭에서 루트 디렉토리인 commerce를 제외한 하위 디렉토리들 삭제
2. customer-api와 seller-api의 build.gradle에 `implementation project(':common')` 추가

### 멀티 모듈 : 프로젝트에서 Bean이 Resolve 되지 않는 오류

KeyGenerator를 common 모듈 아래로 옮긴 후 customer와 seller의 Service 클래스에서 import 시도하였더니 오토와이어 할 수 없다는 경고창이 뜸

⭐ 해결

1. customer-api와 seller-api에 Bean을 Resolve하는 과정에서 스캔 대상에 common 모듈이 포함되어 있지 않기 때문
2. 각 모듈에 BeanConfig 생성 후 @ComponentScan 어노테이션을 사용하여 common 모듈도 스캔 대상에 추가

```java
package zerobase.sellerapi.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("zerobase.common")
public class BeanConfig {

}
```

### customer-api : 회원가입 시 401 Unauthorized가 뜨는 오류

SecurityConfig의 authenticationFilter가 제대로 주입되지 않는다는 문구를 확인

⭐ 해결

1. 원래 customer-api의 BeanConfig에 아래와 같이 어노테이션을 작성해두었음

```java
@ComponentScan("zerobase.common")
```

2. 이를 CustomerApiApplication으로 그대로 옮김
3. 다음과 같이 수정해주니 해결

```java

@EnableJpaAuditing
@SpringBootApplication
@ComponentScan(basePackages = {"zerobase.common", "zerobase.customerapi"})
public class CustomerApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerApiApplication.class, args);
  }

}
```

4. 문제의 원인은 common 모듈만 스캔하고, 자기 자신을 스캔하지 못했기 때문!