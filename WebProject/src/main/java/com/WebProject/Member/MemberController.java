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
    @PostMapping("/member/join")
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
    public String logout(
            @RequestHeader(value = "Authorization") String header
    ) throws JsonProcessingException {
        if (!Objects.isNull(header)) {
            String atk = header.substring(7);
            Subject subject = jwtTokenProvider.getSubject(atk);
            if(jwtTokenProvider.deleteToken(atk, subject.getEmail(), subject.getDate())){
                log.info("로그아웃 - [Result]:True");
                return "로그아웃에 성공하였습니다.";
            }
        }
        else{
            log.info("로그아웃 - [Result]:False");
            throw new NullPointerException();
        }
        return "로그아웃에 실패하였습니다.";
    }

    @ApiOperation(value = "회원 정보 기능", notes = "회원 정보 API")
    @GetMapping("/member/info")
    public MemberResponse info(
            @AuthenticationPrincipal MemberDetails memberDetails){
            MemberResponse memberResponse = MemberResponse.of(memberDetails.getMember());
            log.info("회원 정보 - [Email]:{}, [Name]:{}, [Number]:{}, [RRN]:{}", memberResponse.getEmail(), memberResponse.getName(), memberResponse.getNumber(), memberResponse.getRrn());
            return memberResponse;
    }

    @ApiOperation(value = "회원 탈퇴 기능", notes = "회원 탈퇴 API")
    @GetMapping("/member/delete")
    public boolean delete(
            @AuthenticationPrincipal MemberDetails memberDetails){
            memberService.delete(memberDetails.getMember());
            log.info("회원 탈퇴 - [EMAIL]:{}", memberDetails.getMember().getEmail());
            return true;
    }

    @ApiOperation(value = "회원 수정 기능", notes = "회원 수정  API",response = MemberResponse.class)
    @PutMapping("/member/update")
    public MemberResponse update(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ApiParam(value = "이메일, 이름, 주민등록번호, 연락처", required = true)
            @RequestBody UpdateRequest updateRequest){
            MemberResponse memberResponse = memberService.update(memberDetails.getMember().getEmail(), updateRequest);
            log.info("회원 수정 - [EMAIL]:{}", memberResponse.getEmail());
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

    @ApiOperation(value = "전체 회원 정보 기능", notes = "전체 회원 정보 API")
    @GetMapping("/member/findAll")
    public List<Member> findAll() {
        List<Member> result = memberService.findAll();
        log.info("전체 회원 : {}", result);
        return result;
    }

    @ApiOperation(value = "전체 회원 수 기능", notes = "전체 회원 수 API")
    @GetMapping("/member/totalCount")
    public Long totalCount() {
        Long result = memberService.totalCount();
        log.info("전체 회원 수 : {}", result);
        return result;
    }

}
