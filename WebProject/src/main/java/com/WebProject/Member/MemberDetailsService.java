package com.WebProject.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = em.find(Member.class, username);
        if(member == null) new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        return new MemberDetails(member);
    }
}