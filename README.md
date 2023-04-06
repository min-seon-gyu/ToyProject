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


이번 JWT 토큰을 사용하면서 쿠키와 세션에 대해서도 자세히 학습해보고 개발블로그에 기록하였다.

쿠키, 세션 학습 - https://velog.io/@gcael/%EC%BF%A0%ED%82%A4%EC%99%80-%EC%84%B8%EC%85%98




