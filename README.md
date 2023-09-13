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

이렇게 하면 인스턴스 생성과 접근 설정은 완료되었다.

처음엔 빌드된 .jar 파일을 가상 서버에 옮기는 방식을 git을 사용하여 옮겼었다. 그런데 매번 git을 사용하여 옮기기에는 단순반복작업이 계속되어서 해결책을 찾는 중에 '파일질라' 라는 프로그램을 알게되었다. 

파일질라는 FTP(File Transfer Protocol)로 이용자의 PC와 호스팅 서버 간 파일을 송수신하는 프로그램이며, 전송하는 방법이 드래그 앤 드롭으로 전송이 가능하여 가상 서버에 배포하는 작업이 더욱 수월해졌다.

가상서버에 있는 .jar 파일을 실행하기 위해서 가상서버에 원격으로 접속할 수 있는 방법으로는 텔넷/SSH 클라이언트 프로그램인 'Xshell'을 사용하였다.

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

이후에 CORS에 대해서 학습해보고 개발블로그에 기록하였다.

https://velog.io/@gcael/CORS

- ### Spring Security
JWT 인증 다음으로 정말 어려웠던 내용이었다. 처음에는 WebSecurityConfigurerAdapter 클래스를 상속을 받아서 구현을 하려고 했지만 해당 클래스가 Deprecated가 되어서 사용을 할 수가 없었다. 대신 Spring Security 공식문서에 SecurityFilterChain 클래스를 사용한 예시가 있어서 참고를 하여 구현을 해보았다.

```java
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable() // csrf disable
                .httpBasic().disable() // httpBasic disable
                .exceptionHandling() // 예외발생 핸들링
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 X
                .and()
                .authorizeRequests()
                .antMatchers("/member/join", "/member/login").permitAll() // 로그인, 회원가입만 허용
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // Preflight 요청 허용
                .anyRequest().authenticated() // 그 외에는 인증된 유저만 허용
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, memberDetailsService),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) ->         web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
```

- ### Redis
JWT 토큰 방식을 활용하면서 보안성을 더 높이기 위해 Refresh Token(이하 rtk)을 만들어 관리를 하기로 했다. rtk를 관리하는 장소로 Redis를 활용해보았다.

#### Redis 환경 설정
```java
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
```

RedisTemplate 클래스는 Spring 공식문서에 따르면 Redis 데이터 액세스 코드를 단순화하는 도우미 클래스입니다. RedisTemplate를 Bean으로 등록하고
키, 벨류에 대한 직렬화를 String으로 해줍니다. 그리고 setConnectionFactory은 jedis와 lettuce가 있는데 jedis의 여러 메소드가 deprecated가 되어서 여기서는 lettuce를 사용하였다.

#### Redis 
```java
@Component
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisDao(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValue(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }
    
    public boolean ExistToken(String email){
        return redisTemplate.opsForValue().get(email) != null ? true : false;
    }

    public String getValue(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
}
```

RedisService는 기본적으로 데이터 추가, 데이터 가져오기, 데이터 삭제, 데이터 유무 확인 메소드로 구성하였다.
처음에는 프로젝트를 진행하면서 JWT를 여러 개발블로그나 GIT을 참고하였는데 대부분 Redis를 사용하여 rtk를 관리하는 모습이 보였다. 진행 당시에는 나 또한 Redis를 사용하였지만 후에 Redis에 대해 학습을 하였고 내용을 개발블로그에 기록하였다.

Redis 학습 - https://velog.io/@gcael/Redis

- ### JWT 인증
이번 프로젝트에서는 로그인 인증에 대해 JWT 토큰이라는 기술을 사용해보았다. 기존 쿠키나 세션을 사용하는 방법이 JWT 토큰보다 비교적 간단하였지만 이번 기회에 JWT에 대해서 공부를 하기 위해서 선택하였다.
JWT 토큰을 사용하면서 쿠키와 세션에 대해서도 자세히 학습해보고 개발블로그에 기록하였다.

쿠키, 세션 학습 - https://velog.io/@gcael/%EC%BF%A0%ED%82%A4%EC%99%80-%EC%84%B8%EC%85%98

JWT 학습 - https://velog.io/@gcael/JWT-%EC%9E%85%EB%AC%B8

## 유지 보수를 진행하면서 배운 점

- ### 전체 코드 리팩토링(7월 24일 ~ 7월 25일)
프로젝트를 유지 보수하면서 제일 우선적으로 진행한 내용이다. 기존에는 REST API스러운 목표를 가지고 있었는데 시간이 지나고 보니 미흡했던점이 많았다. 이를 보완하기 위하여 Controller 코드를 변경하였고 전체 적인 구조에서 필요없는 부분을 수정하였다.

기존 코드
```java
    @PostMapping("/member/join")
    public MemberResponse join(
            @ApiParam(value = "이메일, 패스워드, 이름, 주민등록번호, 연락처", required = true)
            @RequestBody SignUpRequest signUpRequest) {
        return memberService.signUp(signUpRequest);
    }

    @PostMapping("/member/delete")
    public boolean delete(@AuthenticationPrincipal MemberDetails memberDetails){
            memberService.delete(memberDetails.getMember());
            log.info("회원 탈퇴 - [EMAIL]:{}", memberDetails.getMember().getEmail());
            return true;
    }

    @PutMapping("/member/update")
    public MemberResponse update(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ApiParam(value = "이메일, 이름, 주민등록번호, 연락처", required = true)
            @RequestBody UpdateRequest updateRequest){
            MemberResponse memberResponse = memberService.update(memberDetails.getMember().getEmail(), updateRequest);
            log.info("회원 수정 - [EMAIL]:{}", memberResponse.getEmail());
            return memberResponse;
    }
```

변경 코드
```java
    @PostMapping("/member")
    public MemberResponse join(
            @ApiParam(value = "이메일, 패스워드, 이름, 주민등록번호, 연락처", required = true)
            @RequestBody SignUpRequest signUpRequest) {
        return memberService.signUp(signUpRequest);
    }

    @DeleteMapping("/member")
    public boolean delete(@AuthenticationPrincipal MemberDetails memberDetails){
            memberService.delete(memberDetails.getMember());
            log.info("회원 탈퇴 - [EMAIL]:{}", memberDetails.getMember().getEmail());
            return true;
    }

    @PatchMapping("/member")
    public MemberResponse update(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ApiParam(value = "이메일, 이름, 주민등록번호, 연락처", required = true)
            @RequestBody UpdateRequest updateRequest){
            MemberResponse memberResponse = memberService.update(memberDetails.getMember().getEmail(), updateRequest);
            log.info("회원 수정 - [EMAIL]:{}", memberResponse.getEmail());
            return memberResponse;
```

등 REST API 리팩토링를 진행하였다.

- ### 검색 기능 퍼포먼스 향상 - 1 (9월 12일)
DB에 대한 학습을 하는 중 LIKE에 대한 글을 보게 되었다. 그리고 LIKE에 단점을 알게되었는데 검색 속도가 많이 느리다는 점이 있었다. 기존 프로젝트에서는 이름, 타입, 주소별 검색을 하였는데 오늘은 타입과 주소별 검색하였을 때 성능을 향상시키기 위하여 전문검색(Fulltext Search) 기술을 적용시켰다.

기존 코드
```java
    @Query(value = "select id, name, address, tell ,operating_time, type, representative_menu, lat,lon ,(select avg(value) from score where score.id = store.id) score from store where store.address like %:address% order by score desc", nativeQuery = true)
    List<Store> getListByAddress(@Param("address") String address);

    @Query(value = "select count(*) from store where store.address like %:address%", nativeQuery = true)
    Long getListByAddressCount(@Param("address") String address);

    @Query(value = "select id, name, address, tell ,operating_time, type, representative_menu, lat,lon ,(select avg(value) from score where score.id = store.id) score from store where store.type like %:type% order by score desc", nativeQuery = true)
    List<Store> getListByType(@Param("type") String type);

    @Query(value = "select count(*) from store where store.type like %:type%", nativeQuery = true)
    Long getListByTypeCount(@Param("type") String type);
```
변경 후 코드
```java
    @Query(value = "select id, name, address, tell ,operating_time, type, representative_menu, lat,lon ,(select avg(value) from score where score.id = store.id) score from store WHERE MATCH(address) AGAINST(:address in boolean mode) order by score desc", nativeQuery = true)
    List<Store> getListByAddress(@Param("address") String address);

    @Query(value = "select count(*) from store WHERE MATCH(address) AGAINST(:address in boolean mode)", nativeQuery = true)
    Long getListByAddressCount(@Param("address") String address);

    @Query(value = "select id, name, address, tell ,operating_time, type, representative_menu, lat,lon ,(select avg(value) from score where score.id = store.id) score from store WHERE MATCH(type) AGAINST(:type in boolean mode) order by score desc", nativeQuery = true)
    List<Store> getListByType(@Param("type") String type);

    @Query(value = "select count(*) from store WHERE MATCH(type) AGAINST(:type in boolean mode)", nativeQuery = true)
    Long getListByTypeCount(@Param("type") String type);
```

성능 비교는 Jmeter 프로그램을 사용하였다. 이번에는 테스트를 위해 간단한 조작만 익힌 후 진행을 하였으며, Jmeter에 대한 학습은 이후에 정리를 해보겠다.

요청 스펙은 다음과 같다.
![요청](https://github.com/min-seon-gyu/Toy_Project/assets/87053159/71f7965d-bfab-48fb-97fb-fbe6702bac01)

기존 코드 성능
![결과1](https://github.com/min-seon-gyu/Toy_Project/assets/87053159/f1aab85c-daed-4c34-8604-f1dc9ee7af82)
변경 후 코드 성능
![결과2](https://github.com/min-seon-gyu/Toy_Project/assets/87053159/56d4abf1-ed02-4d9d-a648-fb3e314870b8)

성능은 많이 올라갔지만 오류율이 검출되었다. 오류율을 해결하기 위해 학습을 하고 보완하는 글을 작성하겠다.

재 변경 후 코드 성능
![마지막결과](https://github.com/min-seon-gyu/Toy_Project/assets/87053159/17686212-fdd4-4ebc-92a6-ac53374717b7)

오류율에 대한 원인 분석으로는 Jmeter 프로그램에 있었다. 기존에 발생했던 오류는 모두 org.apache.http.conn.HttpHostConnectException 오류였다. 이는 Jmeter에 시작 지연 및 기간에 대한 설정값이 원인으로 설정값 변경 후에는 오류율이 0%를 달성했다. 완벽한 분석은 끝나지 않아 이 원인도 후에 정리를 남겨보겠다.

