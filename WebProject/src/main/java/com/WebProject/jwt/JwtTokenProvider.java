package com.WebProject.jwt;

import com.WebProject.Dao.RedisDao;
import com.WebProject.Member.MemberResponse;
import com.WebProject.exception.ForbiddenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final ObjectMapper objectMapper;
    private final RedisDao redisDao;
    @Value("${spring.jwt.key")
    private String key;
    @Value("${spring.jwt.live.atk}")
    private Long atkLive;

    @Value("${spring.jwt.live.rtk}")
    private Long rtkLive;


    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    // JWT 토큰 생성
    public TokenResponse createTokensByLogin(MemberResponse memberResponse) throws JsonProcessingException {
        Subject atkSubject = Subject.atk(
                memberResponse.getAccountId(),
                memberResponse.getEmail(),
                memberResponse.getName(),
                new Date().getTime());
        Subject rtkSubject = Subject.rtk(
                memberResponse.getAccountId(),
                memberResponse.getEmail(),
                memberResponse.getName(),
                new Date().getTime());
        String atk = createToken(atkSubject, atkLive);
        String rtk = createToken(rtkSubject, rtkLive);
        redisDao.setValues(memberResponse.getEmail(), rtk, Duration.ofMillis(rtkLive));
        return new TokenResponse(atk, rtk);
    }

    public boolean existToken(String key) throws JsonProcessingException{
        return redisDao.ExistToken(key);
    }

    public boolean deleteToken(String atk, String email, Long time) throws JsonProcessingException{
        if(redisDao.ExistToken(email)){
            redisDao.deleteValues(email);
            long leftTime = atkLive - (new Date().getTime() - time);
            redisDao.setValues(atk, "logout", Duration.ofMillis(leftTime));
            return true;
        }
        return false;
    }

    private String createToken(Subject subject, Long tokenLive) throws JsonProcessingException {
        String subjectStr = objectMapper.writeValueAsString(subject);
        Claims claims = Jwts.claims()
                .setSubject(subjectStr);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenLive))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Subject getSubject(String atk) throws JsonProcessingException {
        String subjectStr = Jwts.parser().setSigningKey(key).parseClaimsJws(atk).getBody().getSubject();
        return objectMapper.readValue(subjectStr, Subject.class);
    }

    public TokenResponse reissueAtk(MemberResponse memberResponse) throws JsonProcessingException {
        String rtkInRedis = redisDao.getValues(memberResponse.getEmail());
        if (Objects.isNull(rtkInRedis)) throw new ForbiddenException("인증 정보가 만료되었습니다.");
        Subject atkSubject = Subject.atk(
                memberResponse.getAccountId(),
                memberResponse.getEmail(),
                memberResponse.getName(),
                new Date().getTime());
        String atk = createToken(atkSubject, atkLive);
        return new TokenResponse(atk, null);
    }

}