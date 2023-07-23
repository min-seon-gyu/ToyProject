package com.WebProject.Member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class MemberResponse {

    @ApiModelProperty(example = "이메일")
    private final String email;
    @ApiModelProperty(example = "이름")
    private String name;
    @ApiModelProperty(example = "주민등록번호")
    private String rrn;
    @ApiModelProperty(example = "연락처")
    private String number;


    public static MemberResponse of(Member member) {
        return new MemberResponse(
                member.getEmail(),
                member.getName(),
                member.getRrn(),
                member.getNumber());
    }
}
