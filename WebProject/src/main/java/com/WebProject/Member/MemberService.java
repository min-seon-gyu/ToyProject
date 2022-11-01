package com.WebProject.Member;

import com.WebProject.exception.BadRequestException;
import io.netty.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public MemberResponse signUp(SignUpRequest signUpRequest) {
        boolean isExist = memberRepository
                .existsByEmail(signUpRequest.getEmail());
        if (isExist) throw new BadRequestException("이미 존재하는 이메일입니다.");
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        Member member = Member.builder()
                .email(signUpRequest.getEmail())
                .password(encodedPassword)
                .name(signUpRequest.getName())
                .birth(signUpRequest.getBirth())
                .number(signUpRequest.getNumber())
                .build();

        member = memberRepository.save(member);
        return MemberResponse.of(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse login(LoginRequest loginRequest) {
        Member Member = memberRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadRequestException("아이디 혹은 비밀번호를 확인하세요."));

        boolean matches = passwordEncoder.matches(
                loginRequest.getPassword(),
                Member.getPassword());
        if (!matches) throw new BadRequestException("아이디 혹은 비밀번호를 확인하세요.");

        return MemberResponse.of(Member);
    }

}
