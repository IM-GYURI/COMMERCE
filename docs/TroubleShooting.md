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

```
  @Override
  public String getPassword() {
    return password;
  }
```

### 주소를 한글로 입력 시 오류 발생

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