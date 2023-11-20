## Spring Boot를 이용한 간단한 CRUD게시판

- Type : Gradle
- Java Version : 1.8
- Gradle Version : 7.5
- Spring Boot Versoin : 2.1.7 RELEASE
- Use : Security, DevTools, Lombok, JPA(hibernate), H2, Mustache, Bootstrap, fontawesome (icon)

### 2023/11/10 변경사항

- add message properties
- add form validation

### 2023/11/15 변경사항

- add Exception process and page
- add Social Login page (oAuth2 Google Login only)
- add UserSession Annotation (Use HandlerMethodArgumentResolver)

### 2023/11/17 변경사항

- add Social Login page (oAuth2 Naver Login)
- add alert/confirm modal callback process

### 2023/11/20 변경사항

- add Social Login page (oAuth2 Kakao Login)
- charge jdk version (1.8.0_66 -> 1.8.0_391)
- ※ To use Kakao oAuth2 login, you need to use JDK higher than the 1.8.0_101 version