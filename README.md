# Gathering-Bot (취합 자동화 봇 - 개인 프로젝트)
현재 3버전 개발중

## Get Started

### 1. add db schema (문서 하단)
### 2. run YoonApplication (api)
### 3. run front folder to npm (front)


# 프로젝트 개요 및 목적

### * 시작 배경
 - 사내에서는 엑셀을 이용하여 많은 인원의 응답을 취합하기 때문에 취합담당자는 시간과 노력이 필요.    
 - 사내망에서 구글 설문조사, 구글 공유문서를 사용할 수 없음.  
 - 미응답자에게 다시 회신 요청메일을 보내야하는 불편함.
 - 응답하는 인원은 일을 미뤘다가 응답을 잊을 수도 있음.
 
  ex1) 코로나 확진자가 나온 분당제생병원에 방문하셨나요? -> O / X 의 단순한 응답임에도 많은 인원들의 응답을 취합하는데에는 시간이 필요.  
  ex2) 주간보고 요청 -> 1시간 후에 보내야지 미뤄두고 망각.
  
  
### * AS-IS 
   1. (취합 담당자) 응답 양식을 엑셀로 생성  
   2. (취합 담당자) 엑셀을 첨부하여 인원들에게 메일을 전송  
   3. (응답자들) 메일을 보고 첨부파일인 엑셀을 다운로드  
   4. (응답자들) 엑셀에서 본인 응답을 수정 후 저장  
   5. (응답자들) 취합담당자에게 엑셀을 첨부하여 메일 회신  
   6. (취합 담당자) 응답자1에게 온 메일을 열기 하여 복사, 붙여넣기  
   7. (취합 담당자) 응답자2에게 온 메일을 열기 하여 복사, 붙여넣기  
   8. (취합 담당자) 응답자3에게 온 메일을 열기 하여 복사, 붙여넣기  
                         ...  
N-1. (취합 담당자) 응답자N 에게 온 메일을 열기 하여 복사, 붙여넣기  
N. (취합 담당자) 취합된 엑셀을 확인
    
  (6 ~ N-1 과정은 충분히 자동화 할 수 있음)
  
---
![image](https://user-images.githubusercontent.com/61338764/85302978-da865000-b4e4-11ea-9b08-47dccacb6347.png)

### * 목적
 - 취합담당자의 시간을 절약하자.
 - 응답자가 회신메일을 보내는 시간을 절약하고 잊지 않도록 하자.
 - 취합 업무가 지연되지 않도록 하자.

### * 개발 내용
 - 취합 자동화 봇을 통해 회신 요청, 취합을 자동화 하자.
 - 취합담당자 입장: 취합 내용의 폼을 만들고 인원지정(아웃룩연동), 기한을 지정 -> 결과 확인
 - 응답자 입장: 메일에서 바로 응답, 미응답시 일정 시간 경과후 메일 재수신

### * TO-BE
 1. (취합 담당자) 웹에서 응답양식을 생성, 인원과 기한을 지정
 -  (취합 봇) 인원들에게 응답 양식을 메일로 전송
 2. (응답자들) 메일에서 바로 응답내용을 결정 후 전송
 -  (취합 봇) 응답 결과를 수집하여 저장, 정리
 3. (취합 담당자) 결과 확인 및 엑셀로 내보내기
 
 업무 자동화를 통한 간소화

### * 기대효과
- 취합 업무 시간 단축
- 다른 응답 업무 자동화로의 확장

---

# 개발 관련 문서

### * 개발 일정
 - 요구사항 설계, 검토
 - 아키텍처 설계
 - 데이터베이스 설계
 - 백엔드 개발
 - 프론트 개발
 - 배포(자동화)
 - 요구사항 반영

### * 개발 환경

![image](https://user-images.githubusercontent.com/61338764/85302929-c3476280-b4e4-11ea-9403-0f4e10bac303.png)


 - DB:MySQL
 - Language: Java8
 - Back-end : Spring Boot(v2.25), Hiberbate(JPA),  Gradle
 - Front-end : Vue.js
 - Tool: Intelij
 - CI/CD : Jenkins, Docker

### * 릴리즈 내용
 - v 1.0 : 목표 기능 전체 구현
 - v 2.x : 요구사항 반영
 
 ### DB schema
 
 ```mysql
 create schema userdb collate utf8mb4_0900_ai_ci;

create table hibernate_sequence
(
	next_val bigint null
);

create table member
(
	member_id bigint not null
		primary key,
	email varchar(100) not null,
	name varchar(30) not null,
	password varchar(300) not null,
	position varchar(255) null,
	team_name varchar(255) null,
	constraint UK_mbmcqelty0fbrvxp1q58dn57t
		unique (email)
);

create table member_roles
(
	member_member_id bigint not null,
	roles varchar(255) null,
	constraint FKruptm2dtwl95mfks4bnhv828k
		foreign key (member_member_id) references member (member_id)
);

create table sheet
(
	sheet_id bigint not null
		primary key,
	col_num int not null,
	content varchar(255) null,
	created_date datetime null,
	finished_date datetime null,
	question varchar(255) null,
	repeat_date datetime null,
	sheet_status varchar(255) null,
	sheet_type varchar(255) null,
	title varchar(255) null,
	member_id bigint null,
	constraint FK2fkeu8l62eg0vjprc3gsx8c59
		foreign key (member_id) references member (member_id)
);

create table member_sheet
(
	member_sheet_id bigint not null
		primary key,
	modified_date datetime null,
	request_status varchar(255) null,
	response varchar(255) null,
	response_date datetime null,
	member_id bigint null,
	sheet_id bigint null,
	constraint FKbqgwxg53w8qatqqhvajiu1ltv
		foreign key (sheet_id) references sheet (sheet_id),
	constraint FKfchbvtg79lm50bl3f7uf7lws3
		foreign key (member_id) references member (member_id)
);

create table sheetoxpom
(
	sheet_ox_id bigint not null
		primary key,
	content varchar(255) null
);


 ```

---

### Copyright / End User License
 created by sungjaeyoon  
 
### Contact Information

 sungjae.yoon@kt.com  

### Known Issues

### Troubleshooting

### Change Log

