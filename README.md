> *forked from https://github.com/Danjinae/Frontend*

> 스마트한 아파트 관리 서비스 '단지네'의 Web Server (Frontend) 관리 리포지토리입니다.

<h1>2021 Brain 팀 프로젝트: 단지네</h1>

<image width="80%" src="https://user-images.githubusercontent.com/71220342/155305516-44651a71-74e2-4570-9054-c62663d110af.jpg"></image>


# 내손 안의 아파트: 단지네
 > 개발 배경: 한국의 스마트폰 및 인터넷 사용률은 95%가 넘고, 가구의 50%이상이 아파트에 거주한다. 하지만 아파트 공지사항을 엘레베이터 게시판에 붙이고, 민원을 전화로 처리하는 등 대부분의 아파트 관리 업무는 아날로그로 이루어 진다. 또한 대부분의 아파트 커뮤니티들은 입주민 인증절차도 부족하고, 체계적으로 관리되지 못하고 있다.

'단지네'는 아파트 통합관리 및 커뮤니티 서비스이다. 관리사무소는 단지네 웹 서비스를 통해 단지를 등록하고, 관리비 청구, 차량 관리 등 다양한 아파트 단지내 관리 업무를 처리한다. 입주민들은 단지네 앱을 통해 관리서비스를 확인하고 처리하며, 입주민들만의 안전한 커뮤니티를 이용한다.

## Environment
### Web Server (Frontend)
 - spring boot 2 - java
 - jdk 11
 - html, css, js
 - bootstrap 5.1

### WAS (Backend)
 - spring boot 2
 - jdk 11
 - firebase
 - mariaDB

### Android Application
 - Kotlin
 - Android Studio

### CICD
 - Jenkins
 

## Before Start

### WAS (Backend)
src/main/resources/application.properties
```
#DataBase Setting
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

#JWT secret
spring.jwt.secret=

#Iamport secret
spring.iamport.restapi= 
spring.iamport.restapisecret= 
```

## Build
### WAS (Backend) & Web Server (Frontend)
```
./gradlew clean build
```

## Run
```
java -jar {Path To Project Root}/build/libs/{web OR danjinae}-0.0.1-SNAPSHOT.jar
```

## Author
김영원([@yw7148](https://github.com/yw7148))
이현지([@guswl197](https://github.com/guswl197))
유호진([@hojin180](https://github.com/hojin180))
최은지([@chldmswl0203](https://github.com/chldmswl0203))
