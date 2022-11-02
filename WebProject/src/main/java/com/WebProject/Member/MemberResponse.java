package com.WebProject.Member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter

public class MemberResponse {
    @ApiModelProperty(example = "고유 ID")
    private final Long accountId;
    @ApiModelProperty(example = "이메일")
    private final String email;
    @ApiModelProperty(example = "이름")
    private String name;
    @ApiModelProperty(example = "주민등록번호")
    private String rrn;
    @ApiModelProperty(example = "연락처")
    private String number;


    private MemberResponse(Long accountId, String email, String name, String rrn, String number) {
        this.accountId = accountId;
        this.email = email;
        this.name = name;
        this.number = number;
        this.rrn = rrn;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getNumber(),
                member.getRrn(),
                member.getName());
    }
}
