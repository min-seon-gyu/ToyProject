package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }


    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("111");

        //when
        Long saveId = memberService.join(member);

        //then
        Member one = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(one.getName());
    }

    @Test
    public void check(){
        //given
        Member member1 = new Member();
        member1.setName("111");

        Member member2 = new Member();
        member2.setName("111");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

/*
        try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/



        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}