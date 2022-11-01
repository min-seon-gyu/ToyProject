package com.WebProject.jwt;

import com.WebProject.Member.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class AccountDetails extends User {

    private final Member member;

    public AccountDetails(Member member) {
        super(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
        this.member = member;
    }
}
