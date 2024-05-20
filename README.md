# COMMERCE Project
셀러와 구매자 사이의 중개를 위한 e-commerce 서비스 백엔드

# 프로젝트 구조
![project_structure](https://github.com/mingming-mentor/CodeReviewStudy/assets/80020777/5f5b13d5-99e3-437d-9f73-132010d5083d)

# ERD
![ERD](https://github.com/IM-GYURI/COMMERCE/assets/80020777/4d82c920-28b0-490e-9bc7-06c6bf8b0cc4)

# Tech Stack![commer](https://github.com/IM-GYURI/COMMERCE/assets/80020777/754060ad-0173-45a6-89a1-302172721407)

- Java 17
- Spring Boot 3.2.5
- Spring Web
- Spring Security
- Spring Validation
- Spring Data JPA
- MySql
- Redis
- JUnit
- Mockito
- (Thinking...) Elastic Search
- (Thinking...) QueryDSL
- (Thinking...) MailGun

# 프로젝트 기능 및 설계
## SELLER & CUSTOMER
### 회원 가입  
- [ ] 이메일(아이디), 비밀번호, 이름, 전화번호, 주소, 생년월일, 회원 가입 일자, 회원 정보 수정 일자
- [ ] 이메일 인증 후 로그인 가능

### 로그인
- [ ] 이메일(아이디), 비밀번호 입력

### 회원 정보 조회
- [ ] 본인만 조회 가능
- [ ] 이메일(아이디), 이름, 전화번호, 주소, 생년월일, 회원 가입 일자, 회원 정보 수정 일자
      
### 회원 정보 수정
- [ ] 본인만 수정 가능
- [ ] 비밀번호 수정
- [ ] 이름 수정
- [ ] 전화번호 수정
- [ ] 주소 수정
- [ ] 생년월일 수정

### 회원 탈퇴
- [ ] 본인만 탈퇴 가능
- [ ] 이메일(아이디), 비밀번호 입력

### 상품 검색 및 조회
- [ ] 일부만 검색해도 나오도록 함 (e.g. "카메" -> "카메라")
- [ ] 해당하는 상품 목록 조회 (가나다순, 높은 가격순, 낮은 가격순)
- [ ] 상품명, 상품 가격, 상품 사진, 평균 별점, 가장 최근 리뷰 1건

### 상품 조회 (전체)
- [ ] 상품 전체 조회 (가나다순, 높은 가격순, 낮은 가격순)
- [ ] 상품명, 상품 가격, 평균 별점, 가장 최근 리뷰 1건

### 상품 상세 조회
- [ ] 상품명, 상품 가격, 상품 설명, 평균 별점, 리뷰 목록

## SELLER
### 상품 등록
- [ ] 상품명, 상품 가격, 상품 설명 (Thinking... 상품 사진)

### 상품 수정
- [ ] 해당 상점을 소유한 SELLER만 수정 가능
- [ ] 상품명 수정
- [ ] 상품 가격 수정
- [ ] 상품 설명 수정

### 상품 삭제
- [ ] 해당 상점을 소유한 SELLER만 삭제 가능
- [ ] 해당 상품의 리뷰들도 전부 삭제

## CUSTOMER
### 장바구니 등록
- [ ] 해당 CUSTOMER만 등록 가능
- [ ] 상품 코드, 수량 등록

### 장바구니 조회
- [ ] 해당 CUSTOMER만 조회 가능
- [ ] 상품명, 개수, 총 가격 = 장바구니 단위
- [ ] 전체 장바구니 단위 조회, 전체 가격 조회

### 장바구니 수정
- [ ] 해당 CUSTOMER만 수정 가능
- [ ] 수량 조절

### 장바구니 삭제
- [ ] 해당 CUSTOMER만 삭제 가능
- [ ] 장바구니에 담긴 상품 삭제 (하나씩)
- [ ] 장바구니에 담긴 상품 전체 삭제

### 결제
- [ ] 포인트 충전
- [ ] 결제 시 포인트 차감
<br>

#### [Notion 정리본 이동](https://www.notion.so/e-commerce-project-d41eca6aadb34316b3cc6cb516754cb4?pvs=4)
