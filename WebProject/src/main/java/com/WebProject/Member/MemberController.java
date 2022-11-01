package com.WebProject.Member;

import com.WebProject.jwt.AccountDetails;
import com.WebProject.jwt.TokenResponse;
import com.WebProject.jwt.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Join;

@Api(tags = {"회원관련 API"})
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    // 회원가입
    @ApiOperation(value = "회원가입 기능", notes = "회원가입 API",response = MemberResponse.class)
    @PostMapping("/join")
    public MemberResponse signUp(
            @ApiParam(value = "이메일, 패스워드, 이름, 생년월일, 핸드폰 번호", required = true)
            @RequestBody SignUpRequest signUpRequest
    ) {
        return memberService.signUp(signUpRequest);
    }

    // 로그인
    @ApiOperation(value = "로그인 기능", notes = "로그인 API", response = TokenResponse.class)
    @PostMapping("/login")
    public TokenResponse login(
            @ApiParam(value = "이메일, 패스워드", required = true)
            @RequestBody LoginRequest loginRequest
    ) throws JsonProcessingException {
        MemberResponse memberResponse = memberService.login(loginRequest);
        return jwtTokenProvider.createTokensByLogin(memberResponse);
    }

    @ApiOperation(value = "Access Token 재발급 기능", notes = "Access Token 재발급 API", response = TokenResponse.class)
    @GetMapping("/reissue")
    public TokenResponse reissue(
            @ApiParam(value = "헤더 Authorization에 bearer Access Token 형식", required = true)
            @AuthenticationPrincipal AccountDetails accountDetails
    ) throws JsonProcessingException {
        MemberResponse memberResponse = MemberResponse.of(accountDetails.getMember());
        return jwtTokenProvider.reissueAtk(memberResponse);
    }
    @ApiOperation(value = "Access Token 접근 TEST", notes = "Access Token 접근 TEST API")
    @GetMapping("/test")
    public String test() {
        return "good!";
    }
}
