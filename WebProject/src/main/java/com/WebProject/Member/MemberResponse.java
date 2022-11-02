package com.WebProject.Member;

import lombok.Getter;

@Getter

public class MemberResponse {
    private final Long accountId;

    private final String email;

    private String name;

    private String birth;

    private String number;


    private MemberResponse(Long accountId, String email, String name, String birth, String number) {
        this.accountId = accountId;
        this.email = email;
        this.name = name;
        this.number = number;
        this.birth = birth;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getNumber(),
                member.getRRN(),
                member.getName());
    }
}
