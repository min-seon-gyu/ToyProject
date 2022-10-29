package com.WebProject.controller;

import com.WebProject.entity.Member;
import com.WebProject.provider.JwtTokenProvider;
import com.WebProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    // 회원가입
    @PostMapping("/join")
    public Member join(@RequestBody Map<String, String> member) {
        return memberRepository.save(Member.builder()
                .email(member.get("email"))
                        .birth((member.get("birth")))
                        .name(member.get("name"))
                        .number((member.get("number")))
                .password(passwordEncoder.encode(member.get("password")))
                .roles(Collections.singletonList("ROLE_ADMIN")) // 최초 가입시 USER 로 설정
                .build());
    }

    // 로그인
    @PostMapping("/login")
    public LoginData login(@RequestBody Map<String, String> user) {

        Member member = memberRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return new LoginData(member, jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
    }
}
