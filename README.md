# 대전광역시 음식점을 소개합니다.
- #### 프로젝트 기간 : 2022년 10월 - 2022년 12월
- #### 주제 : 대전광역시 음식점들을 소개
- #### 참여인원 : 2명
- #### 맡은 포지션 : 백엔드 서버 개발
- #### 사용 기술 : Java, Spring boot, MySQL, Redis, JWT

## 주요 기능
#### 1. 회원에 대한 CRUD 기능 구현
#### 2. 대전광역시 음식점 이름 및 구별 검색 기능
#### 3. 음식점 평점 주기 기능
#### 4. 음식점 댓글 기능

## 프로젝트를 진행하면서 배운 점

- ### 서버 배포 및 AWS
서버를 배포하는 경험도 쌓아보고 싶었고, 실제로 프로젝트를 진행하는 동안 데스크탑에서 하루종일 서버를 키고 있기에는 부담이 있어서 AWS 가상서버를 사용해 보았다.
AWS EC2 인스턴스를 생성 및 설정 과정은 다음과 같다.
1. AMI - Ubuntu Server 설정
2. 인스턴스유형 - t2.micro 설정
3. 키 페어 생성
4. 네트워크 설정 SSH 트래픽 허용에 내 IP 값 넣기
5. 탄력적 IP 할당 후 생성한 인스턴스에 연결

이렇게 하면 인스터스 생성과 접근 설정은 완료되었다.

- ### CORS
클라이언트에서 서버로 Mapping을 하였을 때 CORS 에러가 발생하였다. 원인을 알아보니 클라이언트에 있는 Origin과 서버에 있는 Origin이 달라서 생기는 문제였다. 그래서 서버에 접근하는 Origin에 대해서 처리를 해줘야 했다.

```java
@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
        ....
    }
    
    //CORS 정책 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
```

이후에 CORS에 대해서 공부해보고 개발블로그에 기록하였다.

https://velog.io/@gcael/CORS

- ### 시큐리티
JWT 인증 다음으로 정말 어려웠던 내용이었다.

- ### JWT 인증

- ### Redis


