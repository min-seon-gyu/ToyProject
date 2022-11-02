package com.WebProject.Member;

import com.WebProject.jwt.AccountDetails;
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
import javax.servlet.http.HttpServletRequest;

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
    public MemberResponse signUp(
            @ApiParam(value = "이메일, 패스워드, 이름, 주민등록번호, 연락처", required = true)
            @RequestBody SignUpRequest signUpRequest
    ) {
        log.info("회원가입 성공", memberService.signUp(signUpRequest));
        return memberService.signUp(signUpRequest);
    }

    // 로그인
    @ApiOperation(value = "로그인 기능", notes = "로그인 API", response = TokenResponse.class)
    @PostMapping("/member/login")
    public TokenResponse login(
            @ApiParam(value = "이메일, 패스워드", required = true)
            @RequestBody LoginRequest loginRequest
    ) throws JsonProcessingException {
        log.info("로그인 - [Email]:{}, [Password]:{}", loginRequest.getEmail(), loginRequest.getPassword());
        MemberResponse memberResponse = memberService.login(loginRequest);
        log.info("로그인 성공 - [ACCESS TOKEN]:{}, REFRESH TOKEN:{}", jwtTokenProvider.createTokensByLogin(memberResponse).getAtk(), jwtTokenProvider.createTokensByLogin(memberResponse).getRtk());
        return jwtTokenProvider.createTokensByLogin(memberResponse);
    }

    @ApiOperation(value = "이메일 찾기 기능", notes = "이메일 찾기 API")
    @PostMapping("/member/findEmail")
    public String findEmail(
            @ApiParam(value = "이름, 주민등록번호", required = true)
            @RequestBody FindEmailRequest findEmailRequest){
        log.info("이메일 찾기 - [Name]:{}, [RNN]:{}-{}", findEmailRequest.getName(), findEmailRequest.getFrontRrn(), findEmailRequest.getBackRrn());
        MemberResponse memberResponse = memberService.findEmail(findEmailRequest);
        log.info("이메일 찾기 성공 - [Email]:{}", memberResponse.getEmail());
        return memberResponse.getEmail();
    }

    @ApiOperation(value = "비밀번호 찾기 기능", notes = "비밀번호 찾기 API")
    @PostMapping("/member/findPassword")
    public boolean findPassword(
            @ApiParam(value = "이메일, 이름, 주민등록번호", required = true)
            @RequestBody FindPasswordRequest findPasswordRequest){
        log.info("비밀번호 찾기 - [Email]:{}, [Name]:{}, [RNN]:{}-{}", findPasswordRequest.getEmail(), findPasswordRequest.getName(), findPasswordRequest.getFrontRrn(), findPasswordRequest.getBackRrn());
        boolean isValid = memberService.findPassword(findPasswordRequest);
        log.info("비밀번호 찾기 - [Result]:{}", isValid);
        return isValid;
    }

    @ApiOperation(value = "로그아웃 기능", notes = "로그아웃 API")
    @GetMapping("/member/logout")
    public String logout(
            @ApiParam(value = "헤더 Authorization에 bearer Refresh Token 형식", required = true)
            HttpServletRequest request) throws JsonProcessingException {
        String atk = memberService.logout(request);
        log.info("로그아웃 - [ACCESS TOKEN]:{}", atk);
        if(atk != null){
            Subject subject = jwtTokenProvider.getSubject(atk);
            if(jwtTokenProvider.deleteToken(atk, subject.getEmail(), subject.getDate())){
                log.info("로그아웃 - [Result]:True");
                return "로그아웃에 성공하였습니다.";
            }
        }
        else{
            throw new NullPointerException();
        }
        log.info("로그아웃 - [Result]:False");
        return "로그아웃에 실패하였습니다.";
    }

    @ApiOperation(value = "Access Token 재발급 기능", notes = "Access Token 재발급 API", response = TokenResponse.class)
    @GetMapping("/member/reissue")
    public TokenResponse reissue(
            @ApiParam(value = "헤더 Authorization에 bearer Access Token 형식", required = true)
            @AuthenticationPrincipal AccountDetails accountDetails
    ) throws JsonProcessingException {
        MemberResponse memberResponse = MemberResponse.of(accountDetails.getMember());
        log.info("Access Token 재발급 - [Email]:{}", memberResponse.getEmail());
        return jwtTokenProvider.reissueAtk(memberResponse);
    }

    @ApiOperation(value = "Access Token 접근 TEST", notes = "Access Token 접근 TEST API")
    @GetMapping("/test")
    public String test() {
        return "good!";
    }
}
