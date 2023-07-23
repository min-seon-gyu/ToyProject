package com.WebProject.Member;

import com.WebProject.jwt.Subject;
import com.WebProject.jwt.TokenResponse;
import com.WebProject.jwt.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Api(tags = {"회원관련 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    // 회원가입
    @ApiOperation(value = "회원가입 기능", notes = "회원가입 API",response = MemberResponse.class)
    @PostMapping("/member")
    public MemberResponse join(
            @ApiParam(value = "이메일, 패스워드, 이름, 주민등록번호, 연락처", required = true)
            @RequestBody SignUpRequest signUpRequest) {
        return memberService.signUp(signUpRequest);
    }

    // 로그인
    @ApiOperation(value = "로그인 기능", notes = "로그인 API", response = TokenResponse.class)
    @PostMapping("/member/login")
    public TokenResponse login(
            @ApiParam(value = "이메일, 패스워드", required = true)
            @RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        MemberResponse memberResponse = memberService.login(loginRequest);
        TokenResponse tokenResponse = jwtTokenProvider.createTokensByLogin(memberResponse);
        log.info("로그인 성공 - [EMAIL]:{}", loginRequest.getEmail());
        return tokenResponse;
    }

    @ApiOperation(value = "이메일 찾기 기능", notes = "이메일 찾기 API")
    @PostMapping("/member/findEmail")
    public String findEmail(
            @ApiParam(value = "이름, 주민등록번호", required = true)
            @RequestBody FindEmailRequest findEmailRequest){
        MemberResponse memberResponse = memberService.findEmail(findEmailRequest);
        return memberResponse.getEmail();
    }

    @ApiOperation(value = "비밀번호 찾기 기능", notes = "비밀번호 찾기 API")
    @PostMapping("/member/findPassword")
    public boolean findPassword(
            @ApiParam(value = "이메일, 이름, 주민등록번호", required = true)
            @RequestBody FindPasswordRequest findPasswordRequest){
        boolean isValid = memberService.findPassword(findPasswordRequest);
        log.info("비밀번호 찾기 - [Result]:{}", isValid);
        return isValid;
    }

    @ApiOperation(value = "로그아웃 기능", notes = "로그아웃 API")
    @GetMapping("/member/logout")
    public void logout(@AuthenticationPrincipal MemberDetails memberDetails){
        MemberResponse memberResponse = MemberResponse.of(memberDetails.getMember());
        if(jwtTokenProvider.deleteToken(memberResponse.getEmail())){
            log.info("로그아웃 - [Result]:True");
        }else{
            log.info("로그아웃 - [Result]:False");
        }
    }

    @ApiOperation(value = "회원 정보 기능", notes = "회원 정보 API")
    @GetMapping("/member")
    public MemberResponse info(@AuthenticationPrincipal MemberDetails memberDetails){
            MemberResponse memberResponse = MemberResponse.of(memberDetails.getMember());
            log.info("회원 정보 - [Email]:{}, [Name]:{}, [Number]:{}, [RRN]:{}", memberResponse.getEmail(), memberResponse.getName(), memberResponse.getNumber(), memberResponse.getRrn());
            return memberResponse;
    }

    @ApiOperation(value = "회원 탈퇴 기능", notes = "회원 탈퇴 API")
    @DeleteMapping("/member")
    public boolean delete(@AuthenticationPrincipal MemberDetails memberDetails){
            memberService.delete(memberDetails.getMember());
            log.info("회원 탈퇴 - [EMAIL]:{}", memberDetails.getMember().getEmail());
            return true;
    }

    @ApiOperation(value = "회원 수정 기능", notes = "회원 수정  API",response = MemberResponse.class)
    @PatchMapping("/member")
    public MemberResponse update(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ApiParam(value = "이메일, 이름, 주민등록번호, 연락처", required = true)
            @RequestBody UpdateRequest updateRequest){
            MemberResponse memberResponse = memberService.update(memberDetails.getMember().getEmail(), updateRequest);
            log.info("회원 수정 - [EMAIL]:{}", memberResponse.getEmail());
            return memberResponse;
    }

    @ApiOperation(value = "회원 비밀번호 수정 기능", notes = "회원 수정  API",response = MemberResponse.class)
    @PatchMapping("/member/pwUpdate")
    public MemberResponse pwUpdate(
            @ApiParam(value = "비밀번호", required = true)
            @RequestBody PasswordUpdateRequest PasswordUpdateRequest){
        MemberResponse memberResponse = memberService.passwordUpdate(PasswordUpdateRequest);
        log.info("회원 수정 - [EMAIL]:{}", PasswordUpdateRequest.getEmail());
        return memberResponse;
    }


    @ApiOperation(value = "Access Token 재발급 기능", notes = "Access Token 재발급 API", response = TokenResponse.class)
    @GetMapping("/member/reissue")
    public TokenResponse reissue(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) throws JsonProcessingException {
        MemberResponse memberResponse = MemberResponse.of(memberDetails.getMember());
        log.info("Access Token 재발급 - [Email]:{}", memberResponse.getEmail());
        return jwtTokenProvider.reissueAtk(memberResponse);
    }
}
